package com.kaimu.testmod.event;

import com.kaimu.testmod.TestMod;
import com.kaimu.testmod.enchantment.TestEnchantments;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.Optional;

@Mod.EventBusSubscriber(modid = TestMod.MOD_ID) // modidはお使いのModのidに合わせてください
public class DewSweeperDamageEventHandler {

    // ダメージ発生時に呼ばれるメソッド
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        // Eventから攻撃者と対象者を取得
        if (!(event.getSource().getEntity() instanceof LivingEntity attacker)) {
            return;
        }

        LivingEntity target = event.getEntity();

        // プレイヤーの手持ちアイテムを取得（トライデント前提）
        ItemStack mainHandItem = attacker.getMainHandItem();
        if (!(mainHandItem.getItem() instanceof TridentItem)) {
            return;
        }

        // 露払い（DewSweeper）エンチャントのレベルを取得
        Optional<Holder.Reference<Enchantment>> holderOpt = attacker.level().registryAccess()
                .registryOrThrow(Registries.ENCHANTMENT)
                .getHolder(TestEnchantments.DEW_SWEEPER);

        if (holderOpt.isEmpty()) return;

        Holder<Enchantment> enchantmentHolder = holderOpt.get();
        int enchantmentLevel = EnchantmentHelper.getItemEnchantmentLevel(enchantmentHolder, mainHandItem);

        if (enchantmentLevel <= 0) {
            return;
        }

        // 条件（水中、雨天）をチェック
        if (target.isInWater() || isEntityInRain(target.level(), target)) {
            // 追加ダメージの計算（元々のダメージに加算する）
            float additionalDamage = 2.5F * enchantmentLevel;

            System.out.println(event.getAmount());
            System.out.println(additionalDamage);
            System.out.println(event.getAmount() + additionalDamage);

            // イベントのダメージに追加ダメージを直接加算
            event.setAmount(event.getAmount() + additionalDamage);


        }
    }

    // 雨に濡れているかのチェック
    private static boolean isEntityInRain(Level level, LivingEntity entity) {
        return level.isRainingAt(entity.blockPosition());
    }
}
