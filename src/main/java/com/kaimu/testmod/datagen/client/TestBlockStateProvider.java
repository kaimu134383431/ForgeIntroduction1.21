package com.kaimu.testmod.datagen.client;

import com.kaimu.testmod.TestMod;
import com.kaimu.testmod.registry.TestBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TestBlockStateProvider extends BlockStateProvider {
    public TestBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, TestMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlockWithItem(TestBlocks.SALT_BLOCK);
        simpleBlockWithItem(TestBlocks.ORIHALCON_BLOCK);
        simpleBlockWithItem(TestBlocks.RAW_ORIHALCON_BLOCK);
        simpleBlockWithItem(TestBlocks.ORIHALCON_ORE);
        simpleBlockWithItem(TestBlocks.DEEPSLATE_ORIHALCON_ORE);

        logBlock((RotatedPillarBlock) TestBlocks.CURSED_LOG.get());
        logBlock((RotatedPillarBlock) TestBlocks.STRIPPED_CURSED_LOG.get());
        axisBlock((RotatedPillarBlock) TestBlocks.CURSED_WOOD.get(),
                blockTexture(TestBlocks.CURSED_LOG.get()),
                blockTexture(TestBlocks.CURSED_LOG.get()));
        axisBlock((RotatedPillarBlock) TestBlocks.STRIPPED_CURSED_WOOD.get(),
                blockTexture(TestBlocks.STRIPPED_CURSED_LOG.get()),
                blockTexture(TestBlocks.STRIPPED_CURSED_LOG.get()));

        item(TestBlocks.CURSED_LOG);
        item(TestBlocks.STRIPPED_CURSED_LOG);
        item(TestBlocks.CURSED_WOOD);
        item(TestBlocks.STRIPPED_CURSED_WOOD);

        cursedLeaves(TestBlocks.CURSED_LEAVES);

        simpleBlockWithItem(TestBlocks.CURSED_PLANKS);
        slabBlock((SlabBlock) TestBlocks.CURSED_SLAB.get(),
                // 二つ重ねたときのテクスチャ
                blockTexture(TestBlocks.CURSED_PLANKS.get()),
                // 単体のテクスチャ
                blockTexture(TestBlocks.CURSED_PLANKS.get()));
        stairsBlock((StairBlock) TestBlocks.CURSED_STAIRS.get(),
                blockTexture(TestBlocks.CURSED_PLANKS.get()));
        fenceBlock((FenceBlock) TestBlocks.CURSED_FENCE.get(),
                blockTexture(TestBlocks.CURSED_PLANKS.get()));
        fenceGateBlock((FenceGateBlock) TestBlocks.CURSED_FENCE_GATE.get(),
                blockTexture(TestBlocks.CURSED_PLANKS.get()));
        doorBlockWithRenderType((DoorBlock) TestBlocks.CURSED_DOOR.get(),
                modLoc("block/cursed_door_bottom"),
                modLoc("block/cursed_door_top"),
                "cutout");
        trapdoorBlockWithRenderType((TrapDoorBlock)
                        TestBlocks.CURSED_TRAPDOOR.get(),
                modLoc("block/cursed_trapdoor"), true,
                "cutout");
        buttonBlock((ButtonBlock) TestBlocks.CURSED_BUTTON.get(),
                blockTexture(TestBlocks.CURSED_PLANKS.get()));
        pressurePlateBlock((PressurePlateBlock)
                        TestBlocks.CURSED_PRESSURE_PLATE.get(),
                blockTexture(TestBlocks.CURSED_PLANKS.get()));
        sapling(TestBlocks.CURSED_SAPLING);

        deepStorageGen(TestBlocks.DEEP_STORAGE_BLOCK,
                TestMod.MOD_ID + ":block/deep_storage_side",
                TestMod.MOD_ID + ":block/deep_storage_bottom",
                TestMod.MOD_ID + ":block/deep_storage_top");


    }

    private void simpleBlockWithItem(RegistryObject<Block> block){
        simpleBlockWithItem(block.get(), cubeAll(block.get()));
    }

    // ブロック用のアイテムモデルを作成
    private void item(RegistryObject<Block> block) {
        simpleBlockItem(block.get(), new ModelFile.UncheckedModelFile(
                TestMod.MOD_ID + ":block/" +
                        ForgeRegistries.BLOCKS.getKey(block.get()).getPath()
        ));
    }

    // 呪われた葉ブロック
    private void cursedLeaves(RegistryObject<Block> block) {
        simpleBlockWithItem(block.get(), models().cubeTop(
                ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
                TextureMapping.getBlockTexture(block.get(), "_side"),
                TextureMapping.getBlockTexture(block.get(), "_top")
        ));
    }

    // 普通の葉ブロック
    private void simpleLeaves(RegistryObject<Block> block) {
        simpleBlockWithItem(block.get(), models()
                .singleTexture(
                        ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
                        TextureMapping.getBlockTexture(Blocks.OAK_LEAVES), // ここを修正
                        "all", blockTexture(block.get()))
                .renderType("cutout"));
    }

    private void sapling(RegistryObject<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get(),
                models().cross(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(),
                        blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }

    private void deepStorageGen(RegistryObject<Block> block, String side, String bottom, String top) {
        BlockModelBuilder model = models().cubeBottomTop(
                ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
                mcLoc(side),
                mcLoc(bottom),
                mcLoc(top)
        );

        simpleBlockWithItem(block.get(), model);

        model.renderType("translucent"); // ここでrender_type追加

    }


}
