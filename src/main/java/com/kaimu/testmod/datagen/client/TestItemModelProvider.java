package com.kaimu.testmod.datagen.client;

import com.kaimu.testmod.TestMod;
import com.kaimu.testmod.registry.TestBlocks;
import com.kaimu.testmod.registry.TestItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TestItemModelProvider extends ItemModelProvider {

    public TestItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, TestMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(TestItems.BUTTER.get());
        basicItem(TestItems.SALT.get());
        basicItem(TestItems.RAW_ORIHALCON.get());
        basicItem(TestItems.ORIHALCON_INGOT.get());
        basicItem(TestItems.BUTTERED_POTATO.get());
        basicItem(TestItems.RUM.get());

        itemWithBlock(TestBlocks.CURSED_SLAB);
        itemWithBlock(TestBlocks.CURSED_STAIRS);
        itemWithBlock(TestBlocks.CURSED_FENCE_GATE);
        itemWithBlock(TestBlocks.CURSED_PRESSURE_PLATE);
        basicItem(TestBlocks.CURSED_DOOR.get().asItem());
        trapdoor(TestBlocks.CURSED_TRAPDOOR);
        fence(TestBlocks.CURSED_FENCE,
                TestBlocks.CURSED_PLANKS);
        button(TestBlocks.CURSED_BUTTON,
                TestBlocks.CURSED_PLANKS);
        sapling(TestBlocks.CURSED_SAPLING);

        itemWithBlock(TestBlocks.DEEP_STORAGE_BLOCK);

    }

    public void itemWithBlock(RegistryObject<Block> block) {
        this.getBuilder(ForgeRegistries.BLOCKS.getKey(block.get()).getPath())
                .parent(new ModelFile.UncheckedModelFile(
                        TestMod.MOD_ID + ":block/" +
                                ForgeRegistries.BLOCKS.getKey(block.get()).getPath()));
    }
    public void trapdoor(RegistryObject<Block> block) {
        this.getBuilder(ForgeRegistries.BLOCKS.getKey(block.get()).getPath())
                .parent(new ModelFile.UncheckedModelFile(
                        TestMod.MOD_ID + ":block/" +
                                ForgeRegistries.BLOCKS.getKey(block.get()).getPath() + "_bottom"));
    }
    public void fence(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/fence_inventory"))
                .texture("texture", modLoc("block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    public void button(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/button_inventory"))
                .texture("texture", modLoc("block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    private void sapling(RegistryObject<Block> block) {
        this.withExistingParent(block.getId().getPath(),
                        ResourceLocation.tryParse("item/generated")) // 修正: tryParse() を使用
                .texture("layer0",
                        ResourceLocation.tryParse(TestMod.MOD_ID + ":block/" + block.getId().getPath())); // 修正: tryParse() を使用
    }



}
