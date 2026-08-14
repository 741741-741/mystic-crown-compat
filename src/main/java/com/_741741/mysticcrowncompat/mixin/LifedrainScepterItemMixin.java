package com._741741.mysticcrowncompat.mixin;

import com._741741.mysticcrowncompat.compat.CuriosMixinUtil;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import twilightforest.item.LifedrainScepterItem;

/**
 * 包装 LifedrainScepterItem.onUseTick 中的 getItemBySlot(HEAD) 调用,
 * 使首饰栏中的神秘王冠也能为吸血权杖提供免耐久增益。
 */
@Mixin(LifedrainScepterItem.class)
public class LifedrainScepterItemMixin {
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
