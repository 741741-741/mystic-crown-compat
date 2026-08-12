package com._741741.mysticcrowncompat.mixin;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyReturnValue;

@Mixin(value = LivingEntity.class, priority = 1000)
public class LivingEntityMixin {

    @ModifyReturnValue(method = "getItemBySlot", at = @At("RETURN"), remap = false)
    private ItemStack onGetItemBySlot(ItemStack original, EquipmentSlot slot) {
        if (slot == EquipmentSlot.HEAD) {
            LivingEntity self = (LivingEntity) (Object) this;
            ItemStack crown = CuriosMixinUtil.getMysticCrownFromCuriosIfPresent(self);
            if (!crown.isEmpty()) {
                return crown;
            }
        }
        return original;
    }
}
