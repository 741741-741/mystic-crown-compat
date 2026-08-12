package com._741741.mysticcrowncompat.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @WrapOperation(
        method = "getItemBySlot",
        at = @At(
            value = "RETURN",
            ordinal = 0
        )
    )
    private ItemStack onGetItemBySlot(LivingEntity instance, EquipmentSlot slot, Operation<ItemStack> original) {
        // 先获取原返回值（可能是头盔槽的物品）
        ItemStack originalStack = original.call(instance, slot);
        // 只对 HEAD 槽且原返回值为空（或不是神秘王冠）时检查 Curios
        if (slot == EquipmentSlot.HEAD) {
            ItemStack crown = CuriosMixinUtil.getMysticCrownFromCuriosIfPresent(instance);
            if (crown != null && !crown.isEmpty()) {
                // 如果原返回值已经是神秘王冠，则不覆盖（避免无限循环）
                if (!originalStack.getItem().equals(crown.getItem())) {
                    return crown;
                }
            }
        }
        return originalStack;
    }
}
