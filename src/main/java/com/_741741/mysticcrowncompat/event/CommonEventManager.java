package com._741741.mysticcrowncompat.event;

import com._741741.mysticcrowncompat.MysticCrownCompatConfig;
import com._741741.mysticcrowncompat.compat.CompatManager;
import com._741741.mysticcrowncompat.compat.CuriosCompat;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;

public class CommonEventManager {
    public static void init(IEventBus modBus) {
        modBus.addListener(MysticCrownCompatConfig::onLoad);
        modBus.addListener(CommonEventHandler::handleAddPackFindersEvent);

        // 游戏事件总线(非 mod 总线)
        NeoForge.EVENT_BUS.addListener(CommonEventHandler::handleLivingDamagePre);

        if (CompatManager.CURIOS_LOADED) {
            modBus.addListener(CuriosCompat::registerCapabilities);
        }
    }
}
