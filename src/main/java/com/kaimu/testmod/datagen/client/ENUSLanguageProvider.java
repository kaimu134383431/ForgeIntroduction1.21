package com.kaimu.testmod.datagen.client;

import com.kaimu.testmod.TestMod;
import com.kaimu.testmod.enchantment.TestEnchantments;
import com.kaimu.testmod.registry.TestBlocks;
import com.kaimu.testmod.registry.TestItems;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.Locale;

public class ENUSLanguageProvider extends LanguageProvider {

    public ENUSLanguageProvider(PackOutput output) {
        super(output, TestMod.MOD_ID, Locale.US.toString().toLowerCase());
    }

    @Override
    protected void addTranslations() {
        addItem(TestItems.BUTTER, "Butter");
        addItem(TestItems.SALT, "Salt");
        addItem(TestItems.RAW_ORIHALCON, "Raw Orihalcon");
        addItem(TestItems.ORIHALCON_INGOT, "Orihalcon Ingot");
        addItem(TestItems.BUTTERED_POTATO, "Buttered Potato");
        addItem(TestItems.RUM, "Rum");



        add("creativetabs.test_tab", "Test");

        addBlock(TestBlocks.SALT_BLOCK, "Salt Block");
        addBlock(TestBlocks.ORIHALCON_BLOCK, "Orihalcon Block");
        addBlock(TestBlocks.RAW_ORIHALCON_BLOCK, "Raw Orihalcon Block");
        addBlock(TestBlocks.ORIHALCON_ORE, "Orihalcon Ore");
        addBlock(TestBlocks.DEEPSLATE_ORIHALCON_ORE, "Deepslate Orihalcon Ore");

        addBlock(TestBlocks.CURSED_LOG, "Cursed Log");
        addBlock(TestBlocks.STRIPPED_CURSED_LOG, "Stripped Cursed Log");
        addBlock(TestBlocks.CURSED_WOOD, "Cursed Wood");
        addBlock(TestBlocks.STRIPPED_CURSED_WOOD, "Stripped Cursed Wood");
        addBlock(TestBlocks.CURSED_LEAVES, "Cursed Leaves");

        addBlock(TestBlocks.CURSED_PLANKS, "Cursed Planks");
        addBlock(TestBlocks.CURSED_SLAB, "Cursed Slab");
        addBlock(TestBlocks.CURSED_STAIRS, "Cursed Stairs");
        addBlock(TestBlocks.CURSED_FENCE, "Cursed Fence");
        addBlock(TestBlocks.CURSED_FENCE_GATE, "Cursed Fence Gate");
        addBlock(TestBlocks.CURSED_DOOR, "Cursed Door");
        addBlock(TestBlocks.CURSED_TRAPDOOR, "Cursed Trap Door");
        addBlock(TestBlocks.CURSED_BUTTON, "Cursed Button");
        addBlock(TestBlocks.CURSED_PRESSURE_PLATE, "Cursed Pressure Plate");
        addBlock(TestBlocks.CURSED_SAPLING, "Cursed Sapling");

        addBlock(TestBlocks.DEEP_STORAGE_BLOCK, "Deep Storage Block");

        add(TestEnchantments.LIGHTNING_STRIKER.location().toLanguageKey("enchantment"), "Lightning Striker");
        add(TestEnchantments.WALL_CLIMBER.location().toLanguageKey("enchantment"), "Wall Climber");
        add(TestEnchantments.AREA_MINER.location().toLanguageKey("enchantment"), "Area Miner");
        add(TestEnchantments.DEW_SWEEPER.location().toLanguageKey("enchantment"), "Dew Sweeper");

        //DeepStorageBlock警告メッセージ
        add("message.deepstorage.cannot_destroy_not_empty", "Cannot be destroyed due to items in storage. Please remove the contents first.");

    }
}
