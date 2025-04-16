package com.kaimu.testmod.datagen.server;

import com.kaimu.testmod.TestMod;
import com.kaimu.testmod.enchantment.TestEnchantments;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class TestEnchantmentTagsProvider extends TagsProvider<Enchantment> {

    public TestEnchantmentTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, Registries.ENCHANTMENT, lookupProvider, "minecraft", existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // エンチャントテーブルタグにカスタムエンチャントを追加
        tag(EnchantmentTags.ENCHANTMENT_TABLE)
                .addOptional(TagEntry.element(TestEnchantments.LIGHTNING_STRIKER.location()).getId())
                .addOptional(TagEntry.element(TestEnchantments.WALL_CLIMBER.location()).getId())
                .addOptional(TagEntry.element(TestEnchantments.AREA_MINER.location()).getId())
                .addOptional(TagEntry.element(TestEnchantments.DEW_SWEEPER.location()).getId());
    }

    // エンチャントタグの定義
    public static class EnchantmentTags {
        // エンチャントテーブルで利用可能なエンチャント
        public static final TagKey<Enchantment> ENCHANTMENT_TABLE =
                TagKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath("minecraft", "in_enchanting_table"));
    }
}