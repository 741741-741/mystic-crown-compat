package com._741741.mysticcrowncompat.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @ModifyReturnValue(
        method = "getItemBySlot",
        at = @At("RETURN")
    )
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
