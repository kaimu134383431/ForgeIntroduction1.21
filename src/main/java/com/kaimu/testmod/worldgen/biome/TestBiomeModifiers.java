package com.kaimu.testmod.worldgen.biome;

import com.kaimu.testmod.TestMod;
import com.kaimu.testmod.worldgen.TestPlacement;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class TestBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_ORIHALCON_ORE =
            ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS,
                    Objects.requireNonNull(ResourceLocation.tryParse(TestMod.MOD_ID + ":add_orihalcon_ore")));

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);

        // `PlacedFeature` を取得
        var orePlacement = placedFeatures.getOrThrow(TestPlacement.ORE_ORIHALCON);

        context.register(ADD_ORIHALCON_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD), // バイオーム指定
                HolderSet.direct(orePlacement),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));
    }
}
