package com.kaimu.testmod.datagen.server;

import com.kaimu.testmod.TestMod;
import com.kaimu.testmod.enchantment.TestEnchantments;
import com.kaimu.testmod.worldgen.TestPlacement;
import com.kaimu.testmod.worldgen.biome.TestBiomeModifiers;
import com.kaimu.testmod.worldgen.features.TestFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class TestBuiltinRegistriesProvider extends DatapackBuiltinEntriesProvider {
    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.ENCHANTMENT, TestEnchantments::bootstrap)
            .add(Registries.CONFIGURED_FEATURE, TestFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, TestPlacement::bootstrap)
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, TestBiomeModifiers::bootstrap);

    public TestBuiltinRegistriesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(TestMod.MOD_ID));
    }

}
