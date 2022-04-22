package dev.mrsterner.bewitchmentplus.mixin.common;

import dev.mrsterner.bewitchmentplus.common.registry.BWPTransformations;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.sensor.VillagerHostilesSensor;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VillagerHostilesSensor.class)
public class VillagerHostilesSensorMixin {

    @Inject(at = @At("HEAD"), method = "isHostile", cancellable = true)
    private void watchOut(LivingEntity entity, CallbackInfoReturnable<Boolean> cir){
        if (entity instanceof PlayerEntity player && BWPTransformations.isLeshon(player, false)) {
            cir.setReturnValue(true);
        }
    }



    @Inject(method = "isCloseEnoughForDanger", at = @At("HEAD"), cancellable = true)
    private void getNearestHostile(LivingEntity villager, LivingEntity target, CallbackInfoReturnable<Boolean> cir){
        if (target instanceof PlayerEntity player && BWPTransformations.isLeshon(player, false)) {
            cir.setReturnValue(target.squaredDistanceTo(villager) <= 12*12);
        }
    }
}