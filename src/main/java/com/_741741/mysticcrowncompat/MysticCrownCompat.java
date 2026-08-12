package com._741741.mysticcrowncompat;

import com._741741.mysticcrowncompat.event.CommonEventManager;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;

@Mod(MysticCrownCompat.MODID)
public class MysticCrownCompat {
    public static final String MODID = "mysticcrowncompat";

    public MysticCrownCompat(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, MysticCrownCompatConfig.SPEC);
        CommonEventManager.init(modEventBus, NeoForge.EVENT_BUS);
    }
}
