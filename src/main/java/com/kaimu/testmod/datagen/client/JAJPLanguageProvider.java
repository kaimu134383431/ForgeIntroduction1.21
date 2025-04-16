package com.kaimu.testmod.datagen.client;

import com.kaimu.testmod.TestMod;
import com.kaimu.testmod.enchantment.TestEnchantments;
import com.kaimu.testmod.registry.TestBlocks;
import com.kaimu.testmod.registry.TestItems;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.Locale;

public class JAJPLanguageProvider extends LanguageProvider {

    public JAJPLanguageProvider(PackOutput output) {
        super(output, TestMod.MOD_ID, Locale.JAPAN.toString().toLowerCase());
    }

    @Override
    protected void addTranslations() {
        addItem(TestItems.BUTTER, "バター");
        addItem(TestItems.SALT, "塩");
        addItem(TestItems.RAW_ORIHALCON, "オリハルコンの原石");
        addItem(TestItems.ORIHALCON_INGOT, "オリハルコンインゴット");
        addItem(TestItems.BUTTERED_POTATO, "じゃがバター");
        addItem(TestItems.RUM, "ラム酒");



        add("creativetabs.test_tab", "テスト");

        addBlock(TestBlocks.SALT_BLOCK, "塩ブロック");
        addBlock(TestBlocks.ORIHALCON_BLOCK, "オリハルコンブロック");
        addBlock(TestBlocks.RAW_ORIHALCON_BLOCK, "オリハルコンの原石ブロック");
        addBlock(TestBlocks.ORIHALCON_ORE, "オリハルコン鉱石");
        addBlock(TestBlocks.DEEPSLATE_ORIHALCON_ORE, "深層オリハルコン鉱石");

        addBlock(TestBlocks.CURSED_LOG, "呪われた原木");
        addBlock(TestBlocks.STRIPPED_CURSED_LOG, "樹皮を剥いだ呪われた原木");
        addBlock(TestBlocks.CURSED_WOOD, "呪われた木");
        addBlock(TestBlocks.STRIPPED_CURSED_WOOD, "樹皮を剥いだ呪われた木");
        addBlock(TestBlocks.CURSED_LEAVES, "呪われた葉");

        addBlock(TestBlocks.CURSED_PLANKS, "呪われた板材");
        addBlock(TestBlocks.CURSED_SLAB, "呪われたハーフブロック");
        addBlock(TestBlocks.CURSED_STAIRS, "呪われた階段");
        addBlock(TestBlocks.CURSED_FENCE, "呪われたフェンス");
        addBlock(TestBlocks.CURSED_FENCE_GATE, "呪われたフェンスゲート");
        addBlock(TestBlocks.CURSED_DOOR, "呪われたドア");
        addBlock(TestBlocks.CURSED_TRAPDOOR, "呪われたトラップドア");
        addBlock(TestBlocks.CURSED_BUTTON, "呪われたボタン");
        addBlock(TestBlocks.CURSED_PRESSURE_PLATE, "呪われた感圧板");
        addBlock(TestBlocks.CURSED_SAPLING, "呪われた苗木");

        addBlock(TestBlocks.DEEP_STORAGE_BLOCK, "ディープストレージ");

        add(TestEnchantments.LIGHTNING_STRIKER.location().toLanguageKey("enchantment"), "雷撃");
        add(TestEnchantments.WALL_CLIMBER.location().toLanguageKey("enchantment"), "登攀");
        add(TestEnchantments.AREA_MINER.location().toLanguageKey("enchantment"), "範囲採掘");
        add(TestEnchantments.DEW_SWEEPER.location().toLanguageKey("enchantment"), "露払い");

        //DeepStorageBlock警告メッセージ
        add("message.deepstorage.cannot_destroy_not_empty", "収納中のアイテムがあるため破壊できません。先に中身を取り出してください。");

    }
}
