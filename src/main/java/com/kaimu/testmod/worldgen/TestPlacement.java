package com.kaimu.testmod.worldgen;

import com.kaimu.testmod.TestMod;
import com.kaimu.testmod.worldgen.features.TestFeatures;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.Objects;

public class TestPlacement {
    public static final ResourceKey<PlacedFeature> ORE_ORIHALCON =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    Objects.requireNonNull(ResourceLocation.tryParse(TestMod.MOD_ID + ":ore_orihalcon")));

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        // `ConfiguredFeature` を取得
        var oreFeature = configuredFeatures.getOrThrow(TestFeatures.ORIHALCON_ORE_KEY);

        // `PlacedFeature` を登録
        PlacementUtils.register(
                context,
                ORE_ORIHALCON,
                oreFeature,
                CountPlacement.of(7), // 鉱石の生成頻度
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(16)),
                BiomeFilter.biome()
        );
    }
}
