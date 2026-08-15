package com._741741.mysticcrowncompat.event;

import com._741741.mysticcrowncompat.MysticCrownCompat;
import com._741741.mysticcrowncompat.MysticCrownCompatConfig;
import com._741741.mysticcrowncompat.compat.CuriosMixinUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import twilightforest.init.TFDamageTypes;

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

    /**
     * 装备神秘王冠(头部或首饰栏)时免疫巫妖炸弹(LichBomb)的爆炸伤害。
     * LICH_BOMB 伤害类型仅由巫妖炸弹产生,因此不会误伤其他伤害来源。
     */
    public static void handleLivingDamagePre(LivingDamageEvent.Pre event) {
        if (event.getSource().is(TFDamageTypes.LICH_BOMB) && CuriosMixinUtil.isWearingMysticCrown(event.getEntity())) {
            event.setNewDamage(0.0F);
        }
    }
}
