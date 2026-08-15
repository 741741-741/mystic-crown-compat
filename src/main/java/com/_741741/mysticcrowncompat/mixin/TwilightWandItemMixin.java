package com._741741.mysticcrowncompat.mixin;

import com._741741.mysticcrowncompat.compat.CuriosMixinUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import twilightforest.entity.projectile.LichBomb;
import twilightforest.entity.projectile.TwilightWandBolt;
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
            boolean hasCrown = CuriosMixinUtil.isWearingMysticCrown(player);
            level.addFreshEntity(new TwilightWandBolt(level, player));
            // 佩戴神秘王冠时,10% 概率额外发射一发会爆炸的巫妖炸弹(巫妖战斗时使用的炸弹)
            if (hasCrown && level.getRandom().nextFloat() < 0.10f) {
                LichBomb lichBomb = new LichBomb(level, player);
                lichBomb.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 1.5F, 1.0F);
                level.addFreshEntity(lichBomb);
            }
            if (!player.getAbilities().instabuild && (!hasCrown || level.getRandom().nextFloat() > 0.05f)) {
                TFItemStackUtils.hurtButDontBreak(stack, 1, (net.minecraft.server.level.ServerLevel) level, player);
            }
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }
}
