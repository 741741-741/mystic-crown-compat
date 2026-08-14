package com._741741.mysticcrowncompat.compat;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICurio;
import twilightforest.init.TFItems;

import java.util.Optional;

/**
 * 为神秘王冠注册 Curios 物品能力,并提供首饰栏查找工具。
 * 通过 CommonEventManager 手动注册到 mod bus,避免与 @EventBusSubscriber 重复注册。
 */
public class CuriosCompat {
    private static final Logger LOGGER = LoggerFactory.getLogger(CuriosCompat.class);

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        LOGGER.info("Mystic Crown Compat: Registering Mystic Crown as Curios item!");
        event.registerItem(
            CuriosCapability.ITEM,
            (stack, context) -> new ICurio() {
                @Override
                public ItemStack getStack() {
                    return stack;
                }

                @Override
                public boolean canEquip(SlotContext slotContext) {
                    return "head".equals(slotContext.identifier());
                }

                @Override
                public SoundInfo getEquipSound(SlotContext slotContext) {
                    return new SoundInfo(SoundEvents.ARMOR_EQUIP_GENERIC.value(), 1.0f, 1.0f);
                }

                @Override
                public boolean canEquipFromUse(SlotContext slotContext) {
                    return canEquip(slotContext);
                }
            },
            TFItems.MYSTIC_CROWN.get()
        );
        LOGGER.info("Mystic Crown Compat: Mystic Crown registered successfully!");
    }

    public static Optional<ItemStack> findMysticCrownInCurios(LivingEntity entity) {
        if (!CompatManager.CURIOS_LOADED) {
            return Optional.empty();
        }
        return CuriosApi.getCuriosInventory(entity)
            .flatMap(handler -> handler.findFirstCurio(
                stack -> stack.getItem() == TFItems.MYSTIC_CROWN.get()
            ))
            .map(SlotResult::stack);
    }
}
