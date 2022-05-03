package dev.mrsterner.bewitchmentplus.mixin.common;

import dev.mrsterner.bewitchmentplus.common.registry.BWPCurses;
import dev.mrsterner.bewitchmentplus.common.registry.BWPStatusEffects;
import moriyashiine.bewitchment.api.registry.Curse;
import moriyashiine.bewitchment.common.registry.BWComponents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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


    @Inject(method = "tryUseTotem", at = @At("HEAD"))
    private void tryUseTotem(DamageSource source, CallbackInfoReturnable<Boolean> callbackInfo) {
            LivingEntity livingEntity = (LivingEntity) (Object)this;
            if(livingEntity instanceof PlayerEntity player){
                BWComponents.CURSES_COMPONENT.maybeGet(player).ifPresent(cursesComponent -> {
                    if(cursesComponent.hasCurse(BWPCurses.HALF_LIFE)){
                        for (Curse.Instance instance : cursesComponent.getCurses()) {
                            cursesComponent.removeCurse(instance.curse);
                        }
                    }
                });
                if(player.hasStatusEffect(BWPStatusEffects.HALF_LIFE)){
                    player.clearStatusEffects();
                }
            }
    }
    @ModifyVariable(method = "damage", at = @At("HEAD"))
    private float halfLifeDamageHandler(float amount) {
        if (this.hasStatusEffect(BWPStatusEffects.HALF_LIFE)) {
            return this.getStatusEffect(BWPStatusEffects.HALF_LIFE).getAmplifier() == 1 ? 0 : amount;
        }
        return amount;
    }

    @Inject(method = "heal", at = @At("HEAD"), cancellable = true)
    public void halfLifeCancelHeal(float amount, CallbackInfo callbackInfo) {
        if (this.hasStatusEffect(BWPStatusEffects.HALF_LIFE)) {
            callbackInfo.cancel();
        }
    }
}
