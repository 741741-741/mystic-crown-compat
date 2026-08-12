package com._741741.mysticcrowncompat.compat;

import com._741741.mysticcrowncompat.MysticCrownCompat;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import twilightforest.init.TFItems;

@EventBusSubscriber(modid = MysticCrownCompat.MODID, bus = EventBusSubscriber.Bus.MOD)
public class CuriosCompat {

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
                    return slotContext.identifier().equals("head");
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
    }
}
