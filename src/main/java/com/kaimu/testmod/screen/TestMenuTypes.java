package com.kaimu.testmod.screen;

import com.kaimu.testmod.TestMod;
import com.kaimu.testmod.screen.custom.DeepStorageMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class TestMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, TestMod.MOD_ID);

    public static final RegistryObject<MenuType<DeepStorageMenu>> DEEP_STORAGE_MENU =
            MENUS.register("deep_storage_name", () -> IForgeMenuType.create(DeepStorageMenu::new));


    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}