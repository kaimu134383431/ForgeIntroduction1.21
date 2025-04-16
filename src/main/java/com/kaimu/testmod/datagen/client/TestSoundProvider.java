package com.kaimu.testmod.datagen.client;

import com.kaimu.testmod.TestMod;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinitionsProvider;

public class TestSoundProvider extends SoundDefinitionsProvider {

    public TestSoundProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, TestMod.MOD_ID, helper);
    }

    @Override
    public void registerSounds() {

    }
}
