package com._741741.mysticcrowncompat.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import twilightforest.item.TwilightWandItem;

@Mixin(TwilightWandItem.class)
public class TwilightWandItemMixin {

    @WrapOperation(
        method = "use",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getItemBySlot(Lnet/minecraft/world/entity/EquipmentSlot;)Lnet/minecraft/world/item/ItemStack;")
    )
    private ItemStack wrapGetItemBySlot(Player player, EquipmentSlot slot, Operation<ItemStack> original) {
        if (slot == EquipmentSlot.HEAD) {
            ItemStack crown = CuriosMixinUtil.getMysticCrownFromCuriosIfPresent(player);
            if (!crown.isEmpty()) {
                return crown;
            }
        }
        return original.call(player, slot);
    }
}
