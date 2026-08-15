package com._741741.mysticcrowncompat.compat;

import com._741741.mysticcrowncompat.MysticCrownCompatConfig;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import twilightforest.init.TFItems;

public class CuriosMixinUtil {
    /** 标记王冠召唤的僵尸,用于攻击力等增益判断(写入实体 persistentData) */
    public static final String CROWN_SUMMONED_TAG = "mysticcrowncompat:crown_summoned";

    public static ItemStack getMysticCrownFromCuriosIfPresent(LivingEntity entity) {
        if (!CompatManager.CURIOS_LOADED || !MysticCrownCompatConfig.enableCuriosIntegration) {
            return ItemStack.EMPTY;
        }
        return CuriosCompat.findMysticCrownInCurios(entity).orElse(ItemStack.EMPTY);
    }

    /**
     * 判断实体是否佩戴神秘王冠:头部装备位或首饰栏(head 槽)均可。
     */
    public static boolean isWearingMysticCrown(LivingEntity entity) {
        if (entity.getItemBySlot(EquipmentSlot.HEAD).is(TFItems.MYSTIC_CROWN)) {
            return true;
        }
        return !getMysticCrownFromCuriosIfPresent(entity).isEmpty();
    }

    /**
     * 包装 LivingEntity.getItemBySlot:查询 HEAD 槽时,若首饰栏中有神秘王冠则优先返回它,
     * 使暮色森林中所有通过 getItemBySlot(HEAD) 判断王冠增益的逻辑
     * (吸血权杖、护盾权杖等)对首饰栏王冠同样生效。
     */
    public static ItemStack wrapGetItemBySlot(LivingEntity entity, EquipmentSlot slot, Operation<ItemStack> original) {
        if (slot == EquipmentSlot.HEAD && CompatManager.CURIOS_LOADED && MysticCrownCompatConfig.enableCuriosIntegration) {
            ItemStack curiosStack = CuriosCompat.findMysticCrownInCurios(entity).orElse(ItemStack.EMPTY);
            if (!curiosStack.isEmpty()) return curiosStack;
        }
        return original.call(entity, slot);
    }
}
