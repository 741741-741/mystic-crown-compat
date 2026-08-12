package com._741741.mysticcrowncompat.event;

import com._741741.mysticcrowncompat.MysticCrownCompat;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.neoforge.event.AddPackFindersEvent;

public class CommonEventHandler {
    public static void handleAddPackFindersEvent(AddPackFindersEvent event) {
        if (event.getPackType() != PackType.SERVER_DATA) return;
        event.addPackFinders(
            ResourceLocation.fromNamespaceAndPath(MysticCrownCompat.MODID, "curios_data"),
            PackType.SERVER_DATA,
            Component.literal("Mystic Crown Compat Curios Data"),
            PackSource.BUILT_IN,
            true,
            Pack.Position.TOP
        );
    }
}
