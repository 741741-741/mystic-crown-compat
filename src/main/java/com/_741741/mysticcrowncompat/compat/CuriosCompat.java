package com._741741.mysticcrowncompat.compat;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICurio;
import twilightforest.init.TFItems;

import java.util.Optional;

@EventBusSubscriber(modid = "mysticcrowncompat")
public class CuriosCompat {
    public static final String SLOT_HEAD = "head";

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerItem(
            CuriosCapability.ITEM,
            (stack, context) -> new ICurio() {
                @Override
                public ItemStack getStack() {
                    return stack;
                }

                @Override
                public boolean canEquip(SlotContext slotContext) {
                    return slotContext.identifier().equals(SLOT_HEAD);
                }

                @Override
                public SoundInfo getEquipSound(SlotContext slotContext) {
                    return new SoundInfo(SoundEvents.ARMOR_EQUIP_GENERIC.value(), 1.0F, 1.0F);
                }

                @Override
                public boolean canEquipFromUse(SlotContext slotContext) {
                    return canEquip(slotContext);
                }
            },
            TFItems.MYSTIC_CROWN.get()
        );
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
