package com.kaimu.testmod.event;

import com.kaimu.testmod.TestMod;
import com.kaimu.testmod.enchantment.TestEnchantments;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Optional;

@Mod.EventBusSubscriber(modid = TestMod.MOD_ID)
public class BlockBreakEventHandler {

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        // プレイヤーとレベルのチェック
        if (!(event.getPlayer() instanceof ServerPlayer player)) return;
        if (!(event.getLevel() instanceof ServerLevel level)) return;
        if (player.gameMode.getGameModeForPlayer() == GameType.SPECTATOR) return;

        // シフトキーが押されているかチェック
        if (!player.isShiftKeyDown()) return;

        ItemStack stack = player.getMainHandItem();

        // ResourceKey から Holder<Enchantment> を取得
        Optional<Holder.Reference<Enchantment>> holderOpt = level.registryAccess()
                .registryOrThrow(Registries.ENCHANTMENT)
                .getHolder(TestEnchantments.AREA_MINER);

        if (holderOpt.isEmpty()) return;

        Holder<Enchantment> enchantmentHolder = holderOpt.get();
        int enchantmentLevel = EnchantmentHelper.getItemEnchantmentLevel(enchantmentHolder, stack);

        if (enchantmentLevel <= 0) return;


        // 中心ブロックの位置
        BlockPos center = event.getPos();
        int radius = enchantmentLevel == 1 ? 1 : 2;

        // 中心ブロックが壊せるかチェック
        BlockState centerState = level.getBlockState(center);
        if (centerState.isAir() || centerState.getDestroySpeed(level, center) < 0 || centerState.getBlock() == Blocks.BEDROCK) {
            return;
        }

        // 範囲内のブロックを処理
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    // 中心ブロックはスキップ（通常の破壊で処理されるため）
                    if (dx == 0 && dy == 0 && dz == 0) continue;

                    BlockPos pos = center.offset(dx, dy, dz);
                    BlockState state = level.getBlockState(pos);

                    if (state.isAir() || state.getDestroySpeed(level, pos) < 0 || state.getBlock() == Blocks.BEDROCK) {
                        continue;
                    }

                    // 掘れるかチェック
                    if (!state.canHarvestBlock(level, pos, player)) continue;

                    // クリエイティブモードでない場合は耐久値を減らす
                    if (!player.getAbilities().instabuild) {
                        // 正しいツールかチェック
                        if (!stack.isCorrectToolForDrops(state)) {
                            continue;
                        }

                        // 耐久値を減らす（1ブロックにつき1ダメージ）
                        stack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);

                        // スタックが空になった（ツールが壊れた）場合は処理終了
                        if (stack.isEmpty()) {
                            return;
                        }
                    }

                    // 統計を更新
                    player.awardStat(Stats.BLOCK_MINED.get(state.getBlock()));

                    // ブロックを破壊する
                    BlockEntity blockEntity = state.hasBlockEntity() ? level.getBlockEntity(pos) : null;
                    Block.dropResources(state, level, pos, blockEntity, player, stack);
                    level.removeBlock(pos, false);
                    level.levelEvent(2001, pos, Block.getId(state));  // 破壊エフェクト
                }
            }
        }
    }
}