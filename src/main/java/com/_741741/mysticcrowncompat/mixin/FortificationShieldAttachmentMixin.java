package com._741741.mysticcrowncompat.mixin;

import com._741741.mysticcrowncompat.compat.CuriosMixinUtil;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import twilightforest.components.entity.FortificationShieldAttachment;

/**
 * 包装 FortificationShieldAttachment.checkLichCrownBonus 中的 getItemBySlot(HEAD) 调用,
 * 使首饰栏中的神秘王冠也能为护盾权杖提供护盾持续时间延长增益。
 */
@Mixin(FortificationShieldAttachment.class)
public class FortificationShieldAttachmentMixin {
    @WrapOperation(
        method = "checkLichCrownBonus",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/LivingEntity;getItemBySlot(Lnet/minecraft/world/entity/EquipmentSlot;)Lnet/minecraft/world/item/ItemStack;"
        )
    )
    private static ItemStack mysticcrowncompat$getItemBySlot(LivingEntity entity, EquipmentSlot slot, Operation<ItemStack> original) {
        return CuriosMixinUtil.wrapGetItemBySlot(entity, slot, original);
    }
}
