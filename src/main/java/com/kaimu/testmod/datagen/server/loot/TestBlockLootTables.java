package com.kaimu.testmod.datagen.server.loot;

import com.kaimu.testmod.registry.TestBlocks;
import com.kaimu.testmod.registry.TestItems;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.core.HolderLookup;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.HashMap;
public class TestBlockLootTables extends BlockLootSubProvider {
    public TestBlockLootTables(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), new HashMap<>(), registries);
    }

    @Override
    protected void generate() {
        // 複数のブロックに対してLootTableを生成する
        this.addLootTableForBlock(TestBlocks.SALT_BLOCK.get());
        this.addLootTableForBlock(TestBlocks.ORIHALCON_BLOCK.get());  // 他のブロックを追加
        this.addLootTableForBlock(TestBlocks.RAW_ORIHALCON_BLOCK.get());

        this.add(TestBlocks.ORIHALCON_ORE.get(),
                block -> this.createOreDrop(block, TestItems.RAW_ORIHALCON.get()));
        this.add(TestBlocks.DEEPSLATE_ORIHALCON_ORE.get(),
                block -> this.createOreDrop(block, TestItems.RAW_ORIHALCON.get()));

        this.dropSelf(TestBlocks.CURSED_LOG.get());
        this.dropSelf(TestBlocks.STRIPPED_CURSED_LOG.get());
        this.dropSelf(TestBlocks.CURSED_WOOD.get());
        this.dropSelf(TestBlocks.STRIPPED_CURSED_WOOD.get());
        this.add(TestBlocks.CURSED_LEAVES.get(),
                block -> this.createLeavesDrops(block, TestBlocks.CURSED_SAPLING.get(),
                        NORMAL_LEAVES_SAPLING_CHANCES));

        this.dropSelf(TestBlocks.CURSED_PLANKS.get());
        this.dropSelf(TestBlocks.CURSED_STAIRS.get());
        this.dropSelf(TestBlocks.CURSED_FENCE.get());
        this.dropSelf(TestBlocks.CURSED_FENCE_GATE.get());
        this.dropSelf(TestBlocks.CURSED_TRAPDOOR.get());
        this.dropSelf(TestBlocks.CURSED_BUTTON.get());
        this.dropSelf(TestBlocks.CURSED_PRESSURE_PLATE.get());
        this.add(TestBlocks.CURSED_SLAB.get(),
                createSlabItemTable(TestBlocks.CURSED_SLAB.get()));
        this.add(TestBlocks.CURSED_DOOR.get(),
                createDoorTable(TestBlocks.CURSED_DOOR.get()));
        this.dropSelf(TestBlocks.CURSED_SAPLING.get());

        this.dropSelf(TestBlocks.DEEP_STORAGE_BLOCK.get());

    }

    // ブロックに対するLootTableを生成するメソッド
    private void addLootTableForBlock(Block block) {
        this.add(block, (b) ->
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(block)
                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))));
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        // 対応するすべてのブロックを返す
        return Set.of(
                TestBlocks.SALT_BLOCK.get(),
                TestBlocks.ORIHALCON_BLOCK.get(),
                TestBlocks.RAW_ORIHALCON_BLOCK.get(),
                TestBlocks.ORIHALCON_ORE.get(),
                TestBlocks.DEEPSLATE_ORIHALCON_ORE.get(),
                TestBlocks.CURSED_LOG.get(),
                TestBlocks.STRIPPED_CURSED_LOG.get(),
                TestBlocks.CURSED_WOOD.get(),
                TestBlocks.STRIPPED_CURSED_WOOD.get(),
                TestBlocks.CURSED_LEAVES.get(),
                TestBlocks.CURSED_PLANKS.get(),
                TestBlocks.CURSED_SLAB.get(),
                TestBlocks.CURSED_STAIRS.get(),
                TestBlocks.CURSED_FENCE.get(),
                TestBlocks.CURSED_FENCE_GATE.get(),
                TestBlocks.CURSED_DOOR.get(),
                TestBlocks.CURSED_TRAPDOOR.get(),
                TestBlocks.CURSED_BUTTON.get(),
                TestBlocks.CURSED_PRESSURE_PLATE.get(),
                TestBlocks.CURSED_SAPLING.get(),
                TestBlocks.DEEP_STORAGE_BLOCK.get()

        ); // 他のブロックもここに追加
    }
}