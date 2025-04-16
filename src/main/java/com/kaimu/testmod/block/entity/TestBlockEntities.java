package com.kaimu.testmod.block.entity;

import com.kaimu.testmod.TestMod;
import com.kaimu.testmod.block.entity.custom.DeepStorageBlockEntity;
import com.kaimu.testmod.registry.TestBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TestBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, TestMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<DeepStorageBlockEntity>> DEEP_STORAGE_BE =
            BLOCK_ENTITIES.register("deep_storage_be", () -> BlockEntityType.Builder.of(
                    DeepStorageBlockEntity::new, TestBlocks.DEEP_STORAGE_BLOCK.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}