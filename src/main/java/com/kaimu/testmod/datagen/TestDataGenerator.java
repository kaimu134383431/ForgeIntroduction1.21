package com.kaimu.testmod.datagen;

import com.kaimu.testmod.TestMod;
import com.kaimu.testmod.datagen.client.*;
import com.kaimu.testmod.datagen.server.*;
import com.kaimu.testmod.datagen.server.loot.TestLootTables;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = TestMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TestDataGenerator {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        //アイテムモデル
        generator.addProvider(event.includeClient(), new TestItemModelProvider(packOutput, existingFileHelper));
        //ブロックモデル
        generator.addProvider(event.includeClient(), new TestBlockStateProvider(packOutput, existingFileHelper));
        //言語ファイル
        generator.addProvider(event.includeClient(), new JAJPLanguageProvider(packOutput));
        generator.addProvider(event.includeClient(), new ENUSLanguageProvider(packOutput));
        //サウンド
        generator.addProvider(event.includeClient(), new TestSoundProvider(packOutput, existingFileHelper));

        //ブロックタグ
        var blockTagsProvider = generator.addProvider(event.includeServer(),
                new TestBlockTagsProvider(packOutput, lookupProvider, existingFileHelper));
        //アイテムタグ
        generator.addProvider(event.includeServer(),
            new TestItemTagsProvider(packOutput, lookupProvider,
                    blockTagsProvider.contentsGetter(), existingFileHelper));
        // エンチャントテーブル
        generator.addProvider(event.includeServer(),
                new TestEnchantmentTagsProvider(packOutput, lookupProvider, existingFileHelper));
        //ルートテーブル
        generator.addProvider(event.includeServer(), TestLootTables.create(packOutput, lookupProvider));
        //レシピ
        generator.addProvider(event.includeServer(), new TestRecipeProvider(packOutput, lookupProvider));
        //進捗
        generator.addProvider(event.includeServer(), new TestAdvancementsProvider(packOutput,lookupProvider, existingFileHelper));
        //GlobalLootModifier
        generator.addProvider(event.includeServer(), new TestGlobalLootModifierProvider(packOutput, lookupProvider));
        //BuiltinRegistries
        generator.addProvider(event.includeServer(), new TestBuiltinRegistriesProvider(packOutput, lookupProvider));



    }
}
