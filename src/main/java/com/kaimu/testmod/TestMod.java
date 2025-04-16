package com.kaimu.testmod;

import com.kaimu.testmod.block.entity.TestBlockEntities;
import com.kaimu.testmod.block.entity.renderer.DeepStorageBlockEntityRenderer;
import com.kaimu.testmod.screen.custom.DeepStorageScreen;
import com.kaimu.testmod.datagen.server.loot.TestLootModifiers;
import com.kaimu.testmod.enchantment.TestEnchantmentEffects;
import com.kaimu.testmod.enchantment.TestEnchantments;
import com.kaimu.testmod.event.BlockBreakEventHandler;
import com.kaimu.testmod.event.DewSweeperDamageEventHandler;
import com.kaimu.testmod.registry.TestBlocks;
import com.kaimu.testmod.registry.TestItems;
import com.kaimu.testmod.registry.TestTabs;
import com.kaimu.testmod.screen.TestMenuTypes;
import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TestMod.MOD_ID)
public class TestMod
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "testmod";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public TestMod(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        //レジストリをイベントバスに登録
        TestItems.register(modEventBus);
        TestTabs.register(modEventBus);
        TestBlocks.register(modEventBus);
        TestLootModifiers.register(modEventBus);
        TestEnchantmentEffects.register(modEventBus);
        TestMenuTypes.register(modEventBus);
        TestBlockEntities.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(BlockBreakEventHandler.class);
        MinecraftForge.EVENT_BUS.register(DewSweeperDamageEventHandler.class);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS){
            event.accept(TestItems.SALT);
            event.accept(TestItems.BUTTER);
            event.accept(TestItems.ORIHALCON_INGOT);
            event.accept(TestItems.RAW_ORIHALCON);

        }

        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            event.accept(TestBlocks.CURSED_LOG);
            event.accept(TestBlocks.CURSED_WOOD);
            event.accept(TestBlocks.STRIPPED_CURSED_LOG);
            event.accept(TestBlocks.STRIPPED_CURSED_WOOD);
            event.accept(TestBlocks.CURSED_LEAVES);
            event.accept(TestBlocks.ORIHALCON_ORE);
            event.accept(TestBlocks.DEEPSLATE_ORIHALCON_ORE);
        }

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            MenuScreens.register(TestMenuTypes.DEEP_STORAGE_MENU.get(), DeepStorageScreen::new);
        }


        @SubscribeEvent
        public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(TestBlockEntities.DEEP_STORAGE_BE.get(), DeepStorageBlockEntityRenderer::new);
        }
    }


}
