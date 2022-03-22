package dev.mrsterner.bewitchmentplus.mixin.common;

import dev.mrsterner.bewitchmentplus.common.registry.BWPStatusEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    public abstract boolean hasStatusEffect(StatusEffect effect);

    @Shadow
    @Nullable
    public abstract StatusEffectInstance getStatusEffect(StatusEffect effect);


    @ModifyVariable(method = "damage", at = @At("HEAD"))
    private float multiplyDamageForVulnerability(float amount) {
        if (this.hasStatusEffect(BWPStatusEffects.HALF_LIFE)) {
            return this.getStatusEffect(BWPStatusEffects.HALF_LIFE).getAmplifier() == 1 ? 0 : amount;
        }
        return amount;
    }

    @Inject(method = "heal", at = @At("HEAD"), cancellable = true)
    public void torporCancelHeal(float amount, CallbackInfo callbackInfo) {
        if (this.hasStatusEffect(BWPStatusEffects.HALF_LIFE)) {
            callbackInfo.cancel();
        }
    }
}
