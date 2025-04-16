/*package com.kaimu.testmod.enchantment.custom.legacy;

import com.mojang.serialization.MapCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraft.world.damagesource.DamageSource;

import java.lang.reflect.Field;

public record DewSweeperEnchantmentEffect() implements EnchantmentEntityEffect {
    public static final MapCodec<DewSweeperEnchantmentEffect> CODEC = MapCodec.unit(DewSweeperEnchantmentEffect::new);

    @Override
    public void apply(ServerLevel pLevel, int pEnchantmentLevel, EnchantedItemInUse pItem, Entity pEntity, Vec3 pOrigin) {
        // Reflectionを使用してownerにアクセス
        LivingEntity attacker = null;
        try {
            Field ownerField = EnchantedItemInUse.class.getDeclaredField("owner");
            ownerField.setAccessible(true);  // privateフィールドにアクセスするための設定
            attacker = (LivingEntity) ownerField.get(pItem);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return; // エラーが発生した場合は処理を中止
        }

        // 攻撃者がnullでないか確認
        if (attacker == null) return;

        // 攻撃対象がLivingEntityであることを確認し、攻撃者と異なることを確認
        if (!(pEntity instanceof LivingEntity target) || pEntity == attacker) return;

        // トライデントを持っているか確認
        ItemStack heldItem = pItem.itemStack();
        if (!(heldItem.getItem() instanceof TridentItem)) return;

        // 水中か、雨に濡れているかのチェック
        if (target.isInWater() || isEntityInRain(pLevel, target)) {
            // 追加ダメージ計算 (元のダメージは自動的に適用されるため、ここでは追加ダメージのみを計算)
            float extraDamage = 2.5F * pEnchantmentLevel; // エンチャントレベルに基づくダメージ増加

            // 適切なダメージソースを使用
            DamageSource damageSource;
            if (attacker instanceof Player player) {
                damageSource = pLevel.damageSources().trident(pEntity, player);
            } else {
                damageSource = pLevel.damageSources().indirectMagic(pEntity, attacker);
            }

            // ターゲットに追加ダメージを与える
            target.hurt(damageSource, extraDamage);

            // デバッグ
            System.out.println("露払いエンチャント効果: 追加" + extraDamage + "ダメージを " + target.getName().getString() + " に与えましたiiii");
        }
    }

    // 雨に濡れているかをチェックするメソッド
    private boolean isEntityInRain(Level level, Entity entity) {
        return level.isRainingAt(entity.blockPosition());
    }

    @Override
    public MapCodec<DewSweeperEnchantmentEffect> codec() {
        return CODEC;
    }
}
*/