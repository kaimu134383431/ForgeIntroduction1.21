package com.kaimu.testmod.worldgen.features;

import com.kaimu.testmod.TestMod;
import com.kaimu.testmod.registry.TestBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;
import java.util.Objects;

public class TestFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> ORIHALCON_ORE_KEY =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    Objects.requireNonNull(ResourceLocation.tryParse(TestMod.MOD_ID + ":orihalcon_ore")));

    public static final ResourceKey<ConfiguredFeature<?, ?>> CURSED_TREE_KEY =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    Objects.requireNonNull(ResourceLocation.tryParse(TestMod.MOD_ID + ":cursed_tree")));


    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest stone = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslate = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        List<OreConfiguration.TargetBlockState> orihalconOres = List.of(
                OreConfiguration.target(stone, TestBlocks.ORIHALCON_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslate, TestBlocks.DEEPSLATE_ORIHALCON_ORE.get().defaultBlockState())
        );

        // 鉱石の特徴を登録
        FeatureUtils.register(
                context,
                ORIHALCON_ORE_KEY,
                Feature.ORE,
                new OreConfiguration(orihalconOres, 9) // 鉱石サイズ9
        );

        FeatureUtils.register(
                context,
                CURSED_TREE_KEY,
                Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(TestBlocks.CURSED_LOG.get()), // 幹の種類 (暗い色合いの木)
                        new ForkingTrunkPlacer(4, 2, 4), // 少し曲がりくねった幹
                        BlockStateProvider.simple(TestBlocks.CURSED_LEAVES.get()), // 葉の種類（枯れた色の葉）
                        new BlobFoliagePlacer(ConstantInt.of(3), ConstantInt.of(1), 3), // 少ない葉と不規則な配置
                        new TwoLayersFeatureSize(1, 0, 1)) // より圧迫感を与える空間サイズ
                        .build()
        );

    }
}
