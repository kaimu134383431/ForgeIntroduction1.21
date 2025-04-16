package com.kaimu.testmod.datagen.server;

import com.kaimu.testmod.TestMod;
import com.kaimu.testmod.registry.TestBlocks;
import com.kaimu.testmod.tag.TestTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class TestBlockTagsProvider extends BlockTagsProvider {
    public TestBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, TestMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(TestBlocks.SALT_BLOCK.get(),
                        TestBlocks.ORIHALCON_BLOCK.get(),
                        TestBlocks.RAW_ORIHALCON_BLOCK.get(),
                        TestBlocks.ORIHALCON_ORE.get(),
                        TestBlocks.DEEPSLATE_ORIHALCON_ORE.get());

        /*this.tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(TestBlocks.SALT_BLOCK.get());*/

        this.tag(Tags.Blocks.NEEDS_NETHERITE_TOOL)
                .add(TestBlocks.ORIHALCON_BLOCK.get(),
                        TestBlocks.RAW_ORIHALCON_BLOCK.get(),
                        TestBlocks.ORIHALCON_ORE.get(),
                        TestBlocks.DEEPSLATE_ORIHALCON_ORE.get());

        this.tag(TestTags.Blocks.CURSED_LOG)
                .add(TestBlocks.CURSED_LOG.get())
                .add(TestBlocks.STRIPPED_CURSED_LOG.get())
                .add(TestBlocks.CURSED_WOOD.get())
                .add(TestBlocks.STRIPPED_CURSED_WOOD.get());
        //原木を使うレシピや、地面かどうかの判定処理に使われる
        this.tag(BlockTags.LOGS)
                .add(TestBlocks.CURSED_LOG.get())
                .add(TestBlocks.STRIPPED_CURSED_LOG.get());
        //焼くと木炭になる
        this.tag(BlockTags.LOGS_THAT_BURN)
                .add(TestBlocks.CURSED_LOG.get())
                .add(TestBlocks.STRIPPED_CURSED_LOG.get())
                .add(TestBlocks.CURSED_WOOD.get())
                .add(TestBlocks.STRIPPED_CURSED_WOOD.get());
        //地面かどうかの判定に使われたり、ハサミで回収できるようになったりする
        this.tag(BlockTags.LEAVES)
                .add(TestBlocks.CURSED_LEAVES.get());

        this.tag(BlockTags.PLANKS).add(TestBlocks.CURSED_PLANKS.get());
        this.tag(BlockTags.SLABS).add(TestBlocks.CURSED_SLAB.get());
        this.tag(BlockTags.STAIRS).add(TestBlocks.CURSED_STAIRS.get());
        this.tag(BlockTags.FENCES).add(TestBlocks.CURSED_FENCE.get());
        this.tag(BlockTags.FENCE_GATES).add(TestBlocks.CURSED_FENCE_GATE.get());
        this.tag(BlockTags.DOORS).add(TestBlocks.CURSED_DOOR.get());
        this.tag(BlockTags.TRAPDOORS).add(TestBlocks.CURSED_TRAPDOOR.get());
        this.tag(BlockTags.BUTTONS).add(TestBlocks.CURSED_BUTTON.get());
        this.tag(BlockTags.PRESSURE_PLATES).add(TestBlocks.CURSED_PRESSURE_PLATE.get());
        
    }
}
