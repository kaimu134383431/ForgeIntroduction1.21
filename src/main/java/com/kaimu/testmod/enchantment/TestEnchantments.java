package com.kaimu.testmod.enchantment;

import com.kaimu.testmod.TestMod;
import com.kaimu.testmod.enchantment.custom.LightningStrikerEnchantmentEffect;
import com.kaimu.testmod.enchantment.custom.WallClimberEnchantmentEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentTarget;

public class TestEnchantments {
    public static final ResourceKey<Enchantment> LIGHTNING_STRIKER = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(TestMod.MOD_ID, "lightning_striker"));

    public static final ResourceKey<Enchantment> WALL_CLIMBER = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(TestMod.MOD_ID, "wall_climber"));

    public static final ResourceKey<Enchantment> AREA_MINER = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(TestMod.MOD_ID, "area_miner"));

    public static final ResourceKey<Enchantment> DEW_SWEEPER = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(TestMod.MOD_ID, "dew_sweeper"));


    public static void bootstrap(BootstrapContext<Enchantment> context){
        var enchantments = context.lookup(Registries.ENCHANTMENT);
        var items = context.lookup(Registries.ITEM);

        register(context, LIGHTNING_STRIKER, Enchantment.enchantment(Enchantment.definition(
                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                        items.getOrThrow(ItemTags.SWORD_ENCHANTABLE),
                        5,
                        2,
                        Enchantment.dynamicCost(5,8),
                        Enchantment.dynamicCost(25,8),
                        2,
                        EquipmentSlotGroup.MAINHAND))
                //.exclusiveWith(enchantments.getOrThrow(EnchantmentTags.DAMAGE_EXCLUSIVE))
                .withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER,
                        EnchantmentTarget.VICTIM, new LightningStrikerEnchantmentEffect()));

        register(context, WALL_CLIMBER, Enchantment.enchantment(Enchantment.definition(
                        items.getOrThrow(ItemTags.CHEST_ARMOR),  // チェストプレート専用
                        items.getOrThrow(ItemTags.CHEST_ARMOR),
                        3,
                        2,
                        Enchantment.dynamicCost(10, 10),
                        Enchantment.dynamicCost(40, 20),
                        1,
                        EquipmentSlotGroup.CHEST))
                .withEffect(
                        EnchantmentEffectComponents.TICK,
                        new WallClimberEnchantmentEffect()
                )
        );



        register(context, AREA_MINER, Enchantment.enchantment(Enchantment.definition(
                        items.getOrThrow(ItemTags.MINING_LOOT_ENCHANTABLE),
                        1,
                        2,
                        Enchantment.dynamicCost(30, 10),
                        Enchantment.dynamicCost(60, 20),
                        2,
                        EquipmentSlotGroup.MAINHAND))

        );

        register(context, DEW_SWEEPER,
                Enchantment.enchantment(Enchantment.definition(
                                items.getOrThrow(ItemTags.TRIDENT_ENCHANTABLE), // トライデント専用
                                items.getOrThrow(ItemTags.TRIDENT_ENCHANTABLE),
                                2,
                                5,
                                Enchantment.dynamicCost(1, 8),
                                Enchantment.dynamicCost(21, 8),
                                2, // treasure rarity
                                EquipmentSlotGroup.MAINHAND))
                        //.exclusiveWith(enchantments.getOrThrow(EnchantmentTags.DAMAGE_EXCLUSIVE))

        );





    }

    private static void register(BootstrapContext<Enchantment> registry, ResourceKey<Enchantment> key, Enchantment.Builder builder){
        registry.register(key, builder.build(key.location()));
    }
}
