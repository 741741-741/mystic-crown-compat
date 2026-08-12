package com._741741.mysticcrowncompat.mixin;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "getItemBySlot", at = @At("RETURN"), cancellable = true)
    private void onGetItemBySlot(EquipmentSlot slot, CallbackInfoReturnable<ItemStack> cir) {
        if (slot == EquipmentSlot.HEAD) {
            LivingEntity self = (LivingEntity) (Object) this;
            ItemStack crown = CuriosMixinUtil.getMysticCrownFromCuriosIfPresent(self);
            if (!crown.isEmpty()) {
                cir.setReturnValue(crown);
            }
        }
    }
}
