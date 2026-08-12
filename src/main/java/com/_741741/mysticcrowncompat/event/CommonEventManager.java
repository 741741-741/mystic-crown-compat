package com._741741.mysticcrowncompat.event;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;

public class CommonEventManager {
    public static void init(IEventBus modBus, IEventBus gameBus) {
        modBus.addListener(CommonEventHandler::handleAddPackFindersEvent);
    }
}
