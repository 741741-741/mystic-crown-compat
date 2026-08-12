package com._741741.mysticcrowncompat.compat;

import com._741741.mysticcrowncompat.MysticCrownCompat;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
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

@EventBusSubscriber(modid = MysticCrownCompat.MODID)
public class CuriosCompat {
    private static final Logger LOGGER = LoggerFactory.getLogger(CuriosCompat.class);

    @SubscribeEvent
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
                    String identifier = slotContext.identifier();
                    LOGGER.info("Mystic Crown Compat: canEquip called with identifier: '{}'", identifier);
                    return true;
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
