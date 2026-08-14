package com._741741.mysticcrowncompat.event;

import com._741741.mysticcrowncompat.MysticCrownCompat;
import com._741741.mysticcrowncompat.MysticCrownCompatConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.neoforge.event.AddPackFindersEvent;

public class CommonEventHandler {
    /**
     * 根据 config 动态注册 Curios 物品 tag 数据包,使神秘王冠可放入 head 槽。
     * 修改 config 后需要重启游戏才能生效。
     */
    public static void handleAddPackFindersEvent(AddPackFindersEvent event) {
        if (event.getPackType() != PackType.SERVER_DATA) return;
        if (!MysticCrownCompatConfig.enableCuriosIntegration) return;

        event.addPackFinders(
            ResourceLocation.fromNamespaceAndPath(MysticCrownCompat.MODID, "mysticcrowncompat_pack/curios_tags"),
            PackType.SERVER_DATA,
            Component.literal("Mystic Crown Compat: Curios Tags"),
            PackSource.BUILT_IN,
            true,
            Pack.Position.TOP
        );
    }
}
