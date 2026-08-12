package com._741741.mysticcrowncompat.event;

import com._741741.mysticcrowncompat.compat.CompatManager;
import com._741741.mysticcrowncompat.compat.CuriosCompat;
import net.neoforged.bus.api.IEventBus;

public class CommonEventManager {
    public static void init(IEventBus modBus) {
        if (CompatManager.CURIOS_LOADED) {
            modBus.addListener(CuriosCompat::registerCapabilities);
        }
        modBus.addListener(CommonEventHandler::handleAddPackFindersEvent);
    }
}
