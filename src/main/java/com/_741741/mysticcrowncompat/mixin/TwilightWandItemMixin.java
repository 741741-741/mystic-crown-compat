package com._741741.mysticcrowncompat.mixin;

import com._741741.mysticcrowncompat.compat.CuriosMixinUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import twilightforest.entity.projectile.TwilightWandBolt;
import twilightforest.init.TFItems;
import twilightforest.init.TFSounds;
import twilightforest.item.TwilightWandItem;
import twilightforest.util.TFItemStackUtils;

@Mixin(TwilightWandItem.class)
public class TwilightWandItemMixin {
    @Overwrite
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.getDamageValue() == stack.getMaxDamage() && !player.getAbilities().instabuild) {
            return InteractionResultHolder.fail(player.getItemInHand(hand));
        }
        player.playSound(TFSounds.TWILIGHT_SCEPTER_USE.get(), 1.0F, (level.getRandom().nextFloat() - level.getRandom().nextFloat()) * 0.2F + 1.0F);
        if (!level.isClientSide()) {
            level.addFreshEntity(new TwilightWandBolt(level, player));
            boolean hasCrown = player.getItemBySlot(EquipmentSlot.HEAD).is(TFItems.MYSTIC_CROWN);
            if (!hasCrown) {
                ItemStack crownInCurios = CuriosMixinUtil.getMysticCrownFromCuriosIfPresent(player);
                hasCrown = !crownInCurios.isEmpty();
            }
            if (!player.getAbilities().instabuild && (!hasCrown || level.getRandom().nextFloat() > 0.05f)) {
                TFItemStackUtils.hurtButDontBreak(stack, 1, (net.minecraft.server.level.ServerLevel) level, player);
            }
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }
}
