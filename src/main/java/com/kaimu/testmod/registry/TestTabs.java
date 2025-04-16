package com.kaimu.testmod.registry;

import com.kaimu.testmod.TestMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class TestTabs {
    //レジストリを作成
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TestMod.MOD_ID);
    //タブを作成
    public static final RegistryObject<CreativeModeTab> TEST_TAB =
            TABS.register("test_tab", () -> CreativeModeTab.builder()
                    //タブのタイトルを設定
                    .title(Component.translatable("creativetabs.test_tab"))
                    //タブのアイコンを追加
                    .icon(TestItems.ORIHALCON_INGOT.get()::getDefaultInstance)
                    //タブにアイテムを追加(必須)
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(TestItems.SALT.get());
                        pOutput.accept(TestItems.BUTTER.get());
                        pOutput.accept(TestItems.RAW_ORIHALCON.get());
                        pOutput.accept(TestItems.ORIHALCON_INGOT.get());
                        pOutput.accept(TestItems.BUTTERED_POTATO.get());
                        pOutput.accept(TestItems.RUM.get());



                        pOutput.accept(TestBlocks.SALT_BLOCK.get());
                        pOutput.accept(TestBlocks.ORIHALCON_BLOCK.get());
                        pOutput.accept(TestBlocks.RAW_ORIHALCON_BLOCK.get());
                        pOutput.accept(TestBlocks.ORIHALCON_ORE.get());
                        pOutput.accept(TestBlocks.DEEPSLATE_ORIHALCON_ORE.get());

                        pOutput.accept(TestBlocks.CURSED_LOG.get());
                        pOutput.accept(TestBlocks.STRIPPED_CURSED_LOG.get());
                        pOutput.accept(TestBlocks.CURSED_WOOD.get());
                        pOutput.accept(TestBlocks.STRIPPED_CURSED_WOOD.get());
                        pOutput.accept(TestBlocks.CURSED_LEAVES.get());

                        pOutput.accept(TestBlocks.CURSED_PLANKS.get());
                        pOutput.accept(TestBlocks.CURSED_SLAB.get());
                        pOutput.accept(TestBlocks.CURSED_STAIRS.get());
                        pOutput.accept(TestBlocks.CURSED_FENCE.get());
                        pOutput.accept(TestBlocks.CURSED_FENCE_GATE.get());
                        pOutput.accept(TestBlocks.CURSED_DOOR.get());
                        pOutput.accept(TestBlocks.CURSED_TRAPDOOR.get());
                        pOutput.accept(TestBlocks.CURSED_BUTTON.get());
                        pOutput.accept(TestBlocks.CURSED_PRESSURE_PLATE.get());
                        pOutput.accept(TestBlocks.CURSED_SAPLING.get());

                        pOutput.accept(TestBlocks.DEEP_STORAGE_BLOCK.get());
                    })
                    //検索バーを追加
                    .withSearchBar()
                    .build());


    public static void register(IEventBus eventBus){
        TABS.register(eventBus);
    }

}
