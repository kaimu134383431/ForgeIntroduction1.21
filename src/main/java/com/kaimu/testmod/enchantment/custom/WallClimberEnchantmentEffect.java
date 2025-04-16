package com.kaimu.testmod.enchantment.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public record WallClimberEnchantmentEffect() implements EnchantmentEntityEffect {
    public static final MapCodec<WallClimberEnchantmentEffect> CODEC =
            MapCodec.unit(WallClimberEnchantmentEffect::new);

    @Override
    public void apply(ServerLevel level, int enchantmentLevel, EnchantedItemInUse item, Entity entity, Vec3 origin) {
        if (!(entity instanceof LivingEntity living)) return;

        if (isCollidingWithWall(living) && isMovingForward(living)) {
            //System.out.println("WallClimber effect ticked!");

            // 一瞬だけ浮遊効果を与える（1tickだけ）
            int duration = 25; // tick 単位。1だと発火タイミングで飛び損ねる可能性あるので2が安全
            int amplifier = enchantmentLevel - 1; // 1レベルでLevitation I、2レベルでII...

            living.addEffect(new MobEffectInstance(MobEffects.LEVITATION, duration, amplifier, false, false, false));

            // 擬似的に落下ダメージを無効化
            living.fallDistance = 0.0F;
        }
    }


    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }

    private boolean isCollidingWithWall(LivingEntity entity) {
        Vec3 look = entity.getLookAngle().normalize();
        double step = 0.05; // ほんのちょっと前へ

        // 足元のAABB（下半分）
        var lowerBox = entity.getBoundingBox().move(look.x * step, 0, look.z * step);

        // 目線の高さのAABB（上半分）
        double eyeHeight = entity.getEyeHeight();
        var upperBox = entity.getBoundingBox()
                .move(0, eyeHeight, 0) // 目線の高さに移動
                .move(look.x * step, 0, look.z * step); // 前方にスライド

        // どちらかが壁に当たっていれば true
        return !entity.level().noCollision(entity, lowerBox) || !entity.level().noCollision(entity, upperBox);
    }



    private boolean isMovingForward(LivingEntity entity) {
        Vec3 look = entity.getLookAngle().normalize();
        Vec3 move = entity.getDeltaMovement().normalize();

        return look.dot(move) > 0.7;
    }
}
