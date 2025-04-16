package com.kaimu.testmod.datagen.server;

import com.kaimu.testmod.TestMod;
import com.kaimu.testmod.datagen.server.loot.AddItemModifier;
import com.kaimu.testmod.registry.TestItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class TestGlobalLootModifierProvider extends GlobalLootModifierProvider {
    public TestGlobalLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, TestMod.MOD_ID, registries);
    }

    @Override
    protected void start(HolderLookup.@NotNull Provider registries) {
        // 廃ポータルのチェスト
        add("orihalcon_ingot_from_ruined_portal", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(Objects.requireNonNull(ResourceLocation.tryParse("minecraft:chests/ruined_portal"))).build()
        }, TestItems.ORIHALCON_INGOT.get()));

        // ゾンビのドロップ
        add("orihalcon_ingot_from_zombie", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(Objects.requireNonNull(ResourceLocation.tryParse("minecraft:entities/zombie"))).build(),
                LootItemRandomChanceCondition.randomChance(0.5f).build()
        }, TestItems.ORIHALCON_INGOT.get()));

        /*// スニッファーの掘り出し物
        add("orihalcon_ingot_from_sniffer_digging", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(Objects.requireNonNull(ResourceLocation.tryParse("minecraft:gameplay/sniffer_digging"))).build()
        }, TestItems.ORIHALCON_INGOT.get()));*/
    }

}