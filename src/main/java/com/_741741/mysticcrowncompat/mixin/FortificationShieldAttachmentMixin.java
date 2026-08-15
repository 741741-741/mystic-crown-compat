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
 * 护盾权杖扩展:
 * <ol>
 *   <li>包装 checkLichCrownBonus 调用:佩戴神秘王冠(头部或首饰栏)时返回 false,
 *       使临时护盾的计时器不再衰减,护盾不会随时间消耗(被攻击打破仍然有效);</li>
 *   <li>包装 FortificationShieldAttachment.checkLichCrownBonus 中的 getItemBySlot(HEAD) 调用,
 *       使首饰栏中的神秘王冠也能被识别。</li>
 * </ol>
 */
@Mixin(FortificationShieldAttachment.class)
public class FortificationShieldAttachmentMixin {
    @WrapOperation(
        method = "tick",
        at = @At(
            value = "INVOKE",
            target = "Ltwilightforest/components/entity/FortificationShieldAttachment;checkLichCrownBonus(Lnet/minecraft/world/entity/LivingEntity;)Z"
        )
    )
    private static boolean mysticcrowncompat$checkLichCrownBonus(FortificationShieldAttachment instance, LivingEntity entity, Operation<Boolean> original) {
        // 佩戴神秘王冠时跳过计时器衰减,护盾不会随时间消耗
        if (CuriosMixinUtil.isWearingMysticCrown(entity)) {
            return false;
        }
        return original.call(instance, entity);
    }

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
