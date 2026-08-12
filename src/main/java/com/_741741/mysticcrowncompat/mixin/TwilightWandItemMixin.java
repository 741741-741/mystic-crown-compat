package com._741741.mysticcrowncompat.mixin;

import com._741741.mysticcrowncompat.mixin.CuriosMixinUtil;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import twilightforest.item.TwilightWandItem;

@Mixin(TwilightWandItem.class)
public class TwilightWandItemMixin {

    @Redirect(
        method = "use",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getItemBySlot(Lnet/minecraft/world/entity/EquipmentSlot;)Lnet/minecraft/world/item/ItemStack;"),
        remap = false
    )
    private ItemStack redirectGetItemBySlot(Player player, EquipmentSlot slot) {
        if (slot == EquipmentSlot.HEAD) {
            ItemStack crown = CuriosMixinUtil.getMysticCrownFromCuriosIfPresent(player);
            if (!crown.isEmpty()) {
                return crown;
            }
        }
        return player.getItemBySlot(slot);
    }
}
