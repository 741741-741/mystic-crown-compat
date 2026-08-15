package com._741741.mysticcrowncompat.mixin;

import com._741741.mysticcrowncompat.compat.CuriosMixinUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import twilightforest.entity.monster.LoyalZombie;
import twilightforest.init.TFEntities;
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
            BlockHitResult result = Item.getPlayerPOVHitResult(level, player, ClipContext.Fluid.NONE);
            if (result.getType() != HitResult.Type.MISS) {
                LoyalZombie zombie = TFEntities.LOYAL_ZOMBIE.get().create(level);
                zombie.moveTo(result.getLocation());
                if (!level.noCollision(zombie, zombie.getBoundingBox())) {
                    return InteractionResultHolder.pass(stack);
                }
                zombie.spawnAnim();
                zombie.setTame(true, false);
                zombie.setOwnerUUID(player.getUUID());
                boolean hasCrown = CuriosMixinUtil.isWearingMysticCrown(player);
                // 佩戴王冠时力量2持续 3 分钟(3600 tick),否则保持原版 60 秒
                zombie.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, hasCrown ? 3600 : 1200, 1));
                if (hasCrown && level.getRandom().nextFloat() <= 0.1f) {
                    zombie.setBaby(true);
                }
                // 佩戴神秘王冠时:小僵尸装备全套皮革装备和一把击退2的木棍;普通僵尸 50% 概率随机装备一件皮革装备
                if (hasCrown) {
                    // 标记王冠召唤的僵尸,使其攻击力变为 4 点(见 LoyalZombieMixin)
                    zombie.getPersistentData().putBoolean(CuriosMixinUtil.CROWN_SUMMONED_TAG, true);
                    if (zombie.isBaby()) {
                        zombie.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.LEATHER_HELMET));
                        zombie.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.LEATHER_CHESTPLATE));
                        zombie.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Items.LEATHER_LEGGINGS));
                        zombie.setItemSlot(EquipmentSlot.FEET, new ItemStack(Items.LEATHER_BOOTS));
                        ItemStack knockbackStick = new ItemStack(Items.STICK);
                        knockbackStick.enchant(level.holderOrThrow(Enchantments.KNOCKBACK), 2);
                        zombie.setItemSlot(EquipmentSlot.MAINHAND, knockbackStick);
                    } else if (level.getRandom().nextFloat() < 0.5f) {
                        EquipmentSlot[] armorSlots = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
                        EquipmentSlot randomSlot = armorSlots[level.getRandom().nextInt(armorSlots.length)];
                        Item armorItem = switch (randomSlot) {
                            case HEAD -> Items.LEATHER_HELMET;
                            case CHEST -> Items.LEATHER_CHESTPLATE;
                            case LEGS -> Items.LEATHER_LEGGINGS;
                            case FEET -> Items.LEATHER_BOOTS;
                            default -> Items.AIR;
                        };
                        zombie.setItemSlot(randomSlot, new ItemStack(armorItem));
                    }
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
}
