package dev.mrsterner.bewitchmentplus.mixin.common;

import dev.mrsterner.bewitchmentplus.common.registry.BWPCurses;
import dev.mrsterner.bewitchmentplus.common.registry.BWPMaterials;
import dev.mrsterner.bewitchmentplus.common.registry.BWPObjects;
import dev.mrsterner.bewitchmentplus.common.registry.BWPStatusEffects;
import moriyashiine.bewitchment.api.registry.Curse;
import moriyashiine.bewitchment.common.misc.BWUtil;
import moriyashiine.bewitchment.common.registry.BWComponents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ArmorItem;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.MathHelper;
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


    @Shadow public abstract boolean canWalkOnFluid(FluidState fluidState);

    private boolean walkOnFluid = false;

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

    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void deathParticle(CallbackInfo ci){
        LivingEntity livingEntity = (LivingEntity)(Object)this;
        if(BWUtil.getArmorPieces(livingEntity, (stack) -> stack.getItem() instanceof ArmorItem && ((ArmorItem)stack.getItem()).getMaterial() == BWPMaterials.DEATH_ARMOR) == 3){
            float r1 = getWorld().random.nextFloat() * 360.0F;
            float mx = -MathHelper.cos(r1 / 180.0F * 3.1415927F) / 20.0F;
            float mz = MathHelper.sin(r1 / 180.0F * 3.1415927F) / 20.0F;
            getWorld().addParticle(ParticleTypes.SMOKE, true, livingEntity.getX(), livingEntity.getY() + 0.1D, livingEntity.getZ(), mx, 0.0D, mz);
            if(getWorld().getRandom().nextFloat() < 0.10F){
                getWorld().addParticle(ParticleTypes.SOUL, true, livingEntity.getX(), livingEntity.getY() + 0.1D, livingEntity.getZ(), mx, 0.0D, mz);
            }
        }
    }


    @Inject(method = "canWalkOnFluid", at = @At("RETURN"), cancellable = true)
    private void deathWalk(FluidState fluidState, CallbackInfoReturnable<Boolean> cir){
        if(walkOnFluid){
            cir.setReturnValue(true);
        }
    }
}
