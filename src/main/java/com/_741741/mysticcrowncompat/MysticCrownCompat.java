package com._741741.mysticcrowncompat;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import com._741741.mysticcrowncompat.compat.CompatManager;

@Mod(MysticCrownCompat.MODID)
public class MysticCrownCompat {
    public static final String MODID = "mysticcrowncompat";

    public MysticCrownCompat(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, MysticCrownCompatConfig.SPEC);
        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        CompatManager.init();
    }
}
