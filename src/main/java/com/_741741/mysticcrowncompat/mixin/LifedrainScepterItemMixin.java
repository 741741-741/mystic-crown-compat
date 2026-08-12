package com._741741.mysticcrowncompat.mixin;

import com._741741.mysticcrowncompat.compat.CuriosMixinUtil;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import twilightforest.item.LifedrainScepterItem;

@Mixin(LifedrainScepterItem.class)
public class LifedrainScepterItemMixin {

    @Redirect(
        method = "onUseTick",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getItemBySlot(Lnet/minecraft/world/entity/EquipmentSlot;)Lnet/minecraft/world/item/ItemStack;"),
        remap = false
    )
    private ItemStack redirectGetItemBySlot(LivingEntity living, EquipmentSlot slot) {
        if (slot == EquipmentSlot.HEAD) {
            ItemStack crown = CuriosMixinUtil.getMysticCrownFromCuriosIfPresent(living);
            if (!crown.isEmpty()) {
                return crown;
            }
        }
        return living.getItemBySlot(slot);
    }
}
