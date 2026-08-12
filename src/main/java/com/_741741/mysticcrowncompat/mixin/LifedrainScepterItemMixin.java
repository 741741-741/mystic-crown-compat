package com._741741.mysticcrowncompat.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import twilightforest.item.LifedrainScepterItem;

@Mixin(LifedrainScepterItem.class)
public class LifedrainScepterItemMixin {

    @WrapOperation(
        method = "onUseTick",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getItemBySlot(Lnet/minecraft/world/entity/EquipmentSlot;)Lnet/minecraft/world/item/ItemStack;")
    )
    private ItemStack wrapGetItemBySlot(LivingEntity living, EquipmentSlot slot, Operation<ItemStack> original) {
        if (slot == EquipmentSlot.HEAD) {
            ItemStack crown = CuriosMixinUtil.getMysticCrownFromCuriosIfPresent(living);
            if (!crown.isEmpty()) {
                return crown;
            }
        }
        return original.call(living, slot);
    }
}
