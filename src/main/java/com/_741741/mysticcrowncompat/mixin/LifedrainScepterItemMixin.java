package com._741741.mysticcrowncompat.mixin;

import com._741741.mysticcrowncompat.compat.CuriosMixinUtil;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import twilightforest.item.LifedrainScepterItem;

/**
 * 吸血权杖扩展:
 * <ol>
 *   <li>包装 onUseTick 中对目标造成的伤害:佩戴神秘王冠(头部或首饰栏)时伤害翻倍(1 -> 2);</li>
 *   <li>包装 onUseTick 中的 getItemBySlot(HEAD) 调用,
 *       使首饰栏中的神秘王冠也能为吸血权杖提供免耐久增益。</li>
 * </ol>
 */
@Mixin(LifedrainScepterItem.class)
public class LifedrainScepterItemMixin {
    @WrapOperation(
        method = "onUseTick",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/LivingEntity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z",
            ordinal = 0
        )
    )
    private static boolean mysticcrowncompat$hurt(LivingEntity target, DamageSource source, float amount, Operation<Boolean> original) {
        // 吸血权杖的伤害源 direct entity 即使用者(LivingEntity)
        if (source.getDirectEntity() instanceof LivingEntity user && CuriosMixinUtil.isWearingMysticCrown(user)) {
            return original.call(target, source, amount * 2.0F);
        }
        return original.call(target, source, amount);
    }

    @WrapOperation(
        method = "onUseTick",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/player/Player;getItemBySlot(Lnet/minecraft/world/entity/EquipmentSlot;)Lnet/minecraft/world/item/ItemStack;"
        )
    )
    private static ItemStack mysticcrowncompat$getItemBySlot(Player player, EquipmentSlot slot, Operation<ItemStack> original) {
        return CuriosMixinUtil.wrapGetItemBySlot(player, slot, original);
    }
}
