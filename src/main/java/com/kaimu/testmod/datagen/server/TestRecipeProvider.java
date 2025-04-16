package com.kaimu.testmod.datagen.server;

import com.kaimu.testmod.registry.TestBlocks;
import com.kaimu.testmod.registry.TestItems;
import com.kaimu.testmod.tag.TestTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class TestRecipeProvider extends RecipeProvider {
    public TestRecipeProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    // オリハルコンインゴットを生成できるアイテムのリスト
    private static final List<ItemLike> ORIHALCON_SMELTABLE =
            List.of(TestItems.RAW_ORIHALCON.get(),
                    TestBlocks.ORIHALCON_ORE.get(),
                    TestBlocks.DEEPSLATE_ORIHALCON_ORE.get());

    @Override
    protected void buildRecipes(RecipeOutput pRecipeOutput) {
        // 9個の塩で塩ブロックを作成 & 塩ブロックを塩に変換
        nineBlockStorageRecipes(
                pRecipeOutput,
                RecipeCategory.MISC,
                TestItems.SALT.get(),
                RecipeCategory.BUILDING_BLOCKS,
                TestBlocks.SALT_BLOCK.get()
        );
        //オリハルコン
        nineBlockStorageRecipes(
                pRecipeOutput,
                RecipeCategory.MISC,
                TestItems.ORIHALCON_INGOT.get(),
                RecipeCategory.BUILDING_BLOCKS,
                TestBlocks.ORIHALCON_BLOCK.get()
        );
        //オリハルコン原石
        nineBlockStorageRecipes(
                pRecipeOutput,
                RecipeCategory.MISC,
                TestItems.RAW_ORIHALCON.get(),
                RecipeCategory.BUILDING_BLOCKS,
                TestBlocks.RAW_ORIHALCON_BLOCK.get()
        );

        woodFromLogs(pRecipeOutput, TestBlocks.CURSED_WOOD.get(),
                TestBlocks.CURSED_LOG.get());
        woodFromLogs(pRecipeOutput, TestBlocks.STRIPPED_CURSED_WOOD.get(),
                TestBlocks.STRIPPED_CURSED_LOG.get());

        planksFromLog(pRecipeOutput,
                TestBlocks.CURSED_PLANKS.get(),
                TestTags.Items.CURSED_LOG, 4);
        slab(pRecipeOutput, RecipeCategory.BUILDING_BLOCKS,
                TestBlocks.CURSED_SLAB.get(),
                TestBlocks.CURSED_PLANKS.get());
        stairs(pRecipeOutput,
                TestBlocks.CURSED_STAIRS.get(),
                TestBlocks.CURSED_PLANKS.get());
        fence(pRecipeOutput,
                TestBlocks.CURSED_FENCE.get(),
                TestBlocks.CURSED_PLANKS.get());
        fenceGate(pRecipeOutput,
                TestBlocks.CURSED_FENCE_GATE.get(),
                TestBlocks.CURSED_PLANKS.get());
        door(pRecipeOutput,
                TestBlocks.CURSED_DOOR.get(),
                TestBlocks.CURSED_PLANKS.get());
        trapdoor(pRecipeOutput,
                TestBlocks.CURSED_TRAPDOOR.get(),
                TestBlocks.CURSED_PLANKS.get());
        button(pRecipeOutput,
                TestBlocks.CURSED_BUTTON.get(),
                TestBlocks.CURSED_PLANKS.get());
        pressurePlate(pRecipeOutput,
                TestBlocks.CURSED_PRESSURE_PLATE.get(),
                TestBlocks.CURSED_PLANKS.get());

        // 青くなったジャガイモを焼くと普通のジャガイモになるレシピ（ベイクドポテトと同じ経験値・時間）
        SimpleCookingRecipeBuilder.smelting(
                        Ingredient.of(Items.POISONOUS_POTATO), // 青くなったジャガイモ
                        RecipeCategory.FOOD,
                        Items.POTATO, // 焼くと普通のジャガイモになる
                        0.35F, // 経験値（ベイクドポテトと同じ）
                        200 // 燃焼時間（ベイクドポテトと同じ）
                ).unlockedBy("has_poisonous_potato", has(Items.POISONOUS_POTATO))
                .save(pRecipeOutput);

        oreSmelting(pRecipeOutput, ORIHALCON_SMELTABLE, RecipeCategory.MISC,
                TestItems.ORIHALCON_INGOT.get(),
                1.0f, 200, "orihalcon");
        oreBlasting(pRecipeOutput, ORIHALCON_SMELTABLE, RecipeCategory.MISC,
                TestItems.ORIHALCON_INGOT.get(),
                1.0f, 100, "orihalcon");


        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, TestBlocks.DEEP_STORAGE_BLOCK.get())
                .pattern("GGG")
                .pattern("GEG")
                .pattern("GGG")
                .define('G', Items.GLASS)
                .define('E', Items.ENDER_EYE)
                .unlockedBy("has_ender_eye", has(Items.ENDER_EYE))
                .save(pRecipeOutput);



    }

    protected static void fourBlockStorageRecipes(
            RecipeOutput pRecipeOutput,
            RecipeCategory pUnpackedCategory,
            ItemLike pUnpacked,
            RecipeCategory pPackedCategory,
            ItemLike pPacked
    ) {
        // ブロック -> 素材4個
        ShapelessRecipeBuilder
                .shapeless(pUnpackedCategory, pUnpacked, 4)
                .requires(pPacked) // 必要素材
                .unlockedBy(getHasName(pPacked), has(pPacked)) // 実績条件
                .save(pRecipeOutput);

        // 素材4個 -> ブロック
        ShapedRecipeBuilder
                .shaped(pPackedCategory, pPacked)
                .define('#', pUnpacked) // パターン定義
                .pattern("##")
                .pattern("##")
                .unlockedBy(getHasName(pUnpacked), has(pUnpacked)) // 実績条件
                .save(pRecipeOutput);
    }

    protected static void nineBlockStorageRecipes(
            RecipeOutput pRecipeOutput,
            RecipeCategory pUnpackedCategory,
            ItemLike pUnpacked,
            RecipeCategory pPackedCategory,
            ItemLike pPacked
    ) {
        // ブロック -> 素材9個
        ShapelessRecipeBuilder
                .shapeless(pUnpackedCategory, pUnpacked, 9)
                .requires(pPacked) // 必要素材
                .unlockedBy(getHasName(pPacked), has(pPacked)) // 実績条件
                .save(pRecipeOutput);

        // 素材9個 -> ブロック
        ShapedRecipeBuilder
                .shaped(pPackedCategory, pPacked)
                .define('#', pUnpacked) // パターン定義
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .unlockedBy(getHasName(pUnpacked), has(pUnpacked)) // 実績条件
                .save(pRecipeOutput);
    }



    private static void stairs(RecipeOutput pRecipeOutput, ItemLike pResult, ItemLike pIngredient) {
        stairBuilder(pResult, Ingredient.of(pIngredient))
                .unlockedBy(getHasName(pIngredient), has(pIngredient))
                .save(pRecipeOutput);
    }
    private static void fence(RecipeOutput pRecipeOutput, ItemLike pResult, ItemLike pIngredient) {
        fenceBuilder(pResult, Ingredient.of(pIngredient))
                .unlockedBy(getHasName(pIngredient), has(pIngredient))
                .save(pRecipeOutput);
    }
    private static void fenceGate(RecipeOutput pRecipeOutput, ItemLike pResult,
                                  ItemLike pIngredient) {
        fenceGateBuilder(pResult, Ingredient.of(pIngredient))
                .unlockedBy(getHasName(pIngredient), has(pIngredient))
                .save(pRecipeOutput);
    }
    private static void door(RecipeOutput pRecipeOutput, ItemLike pResult, ItemLike pIngredient) {
        doorBuilder(pResult, Ingredient.of(pIngredient))
                .unlockedBy(getHasName(pIngredient), has(pIngredient))
                .save(pRecipeOutput);
    }
    private static void trapdoor(RecipeOutput pRecipeOutput, ItemLike pResult,
                                 ItemLike pIngredient) {
        trapdoorBuilder(pResult, Ingredient.of(pIngredient))
                .unlockedBy(getHasName(pIngredient), has(pIngredient))
                .save(pRecipeOutput);
    }
    private static void button(RecipeOutput pRecipeOutput, ItemLike pResult, ItemLike pIngredient) {
        buttonBuilder(pResult, Ingredient.of(pIngredient))
                .unlockedBy(getHasName(pIngredient), has(pIngredient))
                .save(pRecipeOutput);
    }
}
