package com._741741.mysticcrowncompat.compat;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import com._741741.mysticcrowncompat.MysticCrownCompatConfig;

public class CuriosMixinUtil {
    public static ItemStack getMysticCrownFromCuriosIfPresent(LivingEntity entity) {
        if (!CompatManager.CURIOS_LOADED || !MysticCrownCompatConfig.enableCuriosIntegration) {
            return ItemStack.EMPTY;
        }
        return CuriosCompat.findMysticCrownInCurios(entity).orElse(ItemStack.EMPTY);
    }
}
