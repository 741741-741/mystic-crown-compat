package com._741741.mysticcrowncompat.mixin;

import com._741741.mysticcrowncompat.mixin.CuriosMixinUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import twilightforest.entity.monster.LoyalZombie;
import twilightforest.init.TFEntities;
import twilightforest.init.TFItems;
import twilightforest.init.TFSounds;
import twilightforest.item.ZombieWandItem;
import twilightforest.util.TFItemStackUtils;

@Mixin(ZombieWandItem.class)
public class ZombieWandItemMixin {
    @Overwrite
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.getDamageValue() == stack.getMaxDamage() && !player.getAbilities().instabuild) {
            return InteractionResultHolder.fail(stack);
        }
        if (!level.isClientSide()) {
            BlockHitResult result = getPlayerPOVHitResult(level, player, ClipContext.Fluid.NONE);
            if (result.getType() != HitResult.Type.MISS) {
                LoyalZombie zombie = TFEntities.LOYAL_ZOMBIE.get().create(level);
                zombie.moveTo(result.getLocation());
                if (!level.noCollision(zombie, zombie.getBoundingBox())) {
                    return InteractionResultHolder.pass(stack);
                }
                zombie.spawnAnim();
                zombie.setTame(true, false);
                zombie.setOwnerUUID(player.getUUID());
                zombie.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1200, 1));
                boolean hasCrown = player.getItemBySlot(EquipmentSlot.HEAD).is(TFItems.MYSTIC_CROWN);
                if (!hasCrown) {
                    ItemStack crownInCurios = CuriosMixinUtil.getMysticCrownFromCuriosIfPresent(player);
                    hasCrown = !crownInCurios.isEmpty();
                }
                if (hasCrown && level.getRandom().nextFloat() <= 0.1f) {
                    zombie.setBaby(true);
                }
                level.addFreshEntity(zombie);
                level.gameEvent(player, net.minecraft.world.level.gameevent.GameEvent.ENTITY_PLACE, result.getBlockPos());
                if (!player.getAbilities().instabuild) {
                    TFItemStackUtils.hurtButDontBreak(stack, 1, (net.minecraft.server.level.ServerLevel) level, player);
                }
                zombie.playSound(TFSounds.ZOMBIE_SCEPTER_USE.get(), 1.0F, 1.0F);
            }
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }
    private BlockHitResult getPlayerPOVHitResult(Level level, Player player, ClipContext.Fluid fluid) {
        return net.minecraft.world.entity.player.Player.getPlayerPOVHitResult(level, player, fluid);
    }
}
