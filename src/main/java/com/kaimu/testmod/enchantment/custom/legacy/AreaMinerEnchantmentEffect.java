package com.kaimu.testmod.enchantment.custom.legacy;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public record AreaMinerEnchantmentEffect() implements EnchantmentEntityEffect {
    public static final MapCodec<AreaMinerEnchantmentEffect> CODEC = MapCodec.unit(AreaMinerEnchantmentEffect::new);

    @Override
    public void apply(ServerLevel level, int enchantmentLevel, EnchantedItemInUse item, Entity user, Vec3 origin) {
        if (!user.isShiftKeyDown()) return;
        if (!(user instanceof ServerPlayer player)) return;
        if (player.gameMode.getGameModeForPlayer() == GameType.SPECTATOR) return;

        BlockPos center = BlockPos.containing(origin);
        int radius = enchantmentLevel == 1 ? 1 : 2;
        ItemStack stack = item.itemStack(); // EnchantedItemInUse からツールを取得

        // 中心ブロックが壊せるかチェック（元のブロック破壊イベントは通常通り処理される）
        BlockState centerState = level.getBlockState(center);
        if (centerState.isAir() || centerState.getDestroySpeed(level, center) < 0 || centerState.getBlock() == Blocks.BEDROCK) {
            return;
        }

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

                    // 掘れるかチェック（通常の左クリックと同様のチェック）
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

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}