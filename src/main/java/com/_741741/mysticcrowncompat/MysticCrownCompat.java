package com._741741.mysticcrowncompat;

import com._741741.mysticcrowncompat.compat.CuriosCompat;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

@Mod(MysticCrownCompat.MODID)
public class MysticCrownCompat {
    public static final String MODID = "mysticcrowncompat";

    public MysticCrownCompat(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, MysticCrownCompatConfig.SPEC);
        modEventBus.addListener(this::commonSetup);
        // 注册 Curios 能力
        modEventBus.addListener(CuriosCompat::registerCapabilities);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        CompatManager.init();
    }
}
