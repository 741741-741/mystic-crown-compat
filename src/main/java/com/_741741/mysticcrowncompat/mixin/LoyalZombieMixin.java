package com._741741.mysticcrowncompat.mixin;

import com._741741.mysticcrowncompat.compat.CuriosMixinUtil;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import twilightforest.entity.monster.LoyalZombie;

/**
 * 僵尸权杖扩展:包装 LoyalZombie.doHurtTarget 中对目标造成的伤害,
 * 若该僵尸是佩戴神秘王冠时召唤的(persistentData 中有王冠召唤标记),
 * 则攻击力固定为 4 点(原版硬编码 7 点)。
 */
@Mixin(LoyalZombie.class)
public class LoyalZombieMixin {
    @WrapOperation(
        method = "doHurtTarget",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"
        )
    )
    private static boolean mysticcrowncompat$hurt(Entity target, DamageSource source, float amount, Operation<Boolean> original) {
        // mobAttack(this) 的伤害源 direct entity 即攻击者(僵尸本身)
        if (source.getDirectEntity() instanceof LivingEntity attacker
            && attacker.getPersistentData().getBoolean(CuriosMixinUtil.CROWN_SUMMONED_TAG)) {
            return original.call(target, source, 4.0F);
        }
        return original.call(target, source, amount);
    }
}
