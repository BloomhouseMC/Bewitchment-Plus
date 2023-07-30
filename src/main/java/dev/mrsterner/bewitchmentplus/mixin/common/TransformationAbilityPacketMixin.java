package dev.mrsterner.bewitchmentplus.mixin.common;

import dev.mrsterner.bewitchmentplus.common.registry.BWPTransformations;
import moriyashiine.bewitchment.client.packet.SpawnSmokeParticlesPacket;
import moriyashiine.bewitchment.common.packet.TransformationAbilityPacket;
import moriyashiine.bewitchment.common.registry.BWComponents;
import moriyashiine.bewitchment.common.registry.BWScaleTypes;
import moriyashiine.bewitchment.common.registry.BWSoundEvents;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import virtuoel.pehkui.api.ScaleData;

@Mixin(value = TransformationAbilityPacket.class, remap = false)
public class TransformationAbilityPacketMixin {


    @Shadow @Final private static float WEREWOLF_HEIGHT;

    @Shadow @Final private static float WEREWOLF_WIDTH;


    @Inject(method = "canUseAbility", at = @At("HEAD"), cancellable = true)
    private static void canUseAbility(PlayerEntity player, CallbackInfoReturnable<Boolean> cir){
        if (BWPTransformations.isLeshon(player, true)){
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "useAbility", at = @At(value = "HEAD"), cancellable = true)
    private static void useAbility(PlayerEntity player, boolean forced, CallbackInfo ci){
        if (BWComponents.TRANSFORMATION_COMPONENT.get(player).getTransformation() == BWPTransformations.LESHON && (forced)){
            World world = player.getWorld();
            if(world instanceof ServerWorld serverWorld){
                boolean isInAlternateForm = BWComponents.TRANSFORMATION_COMPONENT.get(player).isAlternateForm();
                ScaleData width = BWScaleTypes.MODIFY_WIDTH_TYPE.getScaleData(player);
                ScaleData height = BWScaleTypes.MODIFY_HEIGHT_TYPE.getScaleData(player);
                BWComponents.TRANSFORMATION_COMPONENT.get(player).setAlternateForm(!isInAlternateForm);
                world.playSound(null, player.getBlockPos(), BWSoundEvents.ENTITY_GENERIC_TRANSFORM, player.getSoundCategory(), 1.0F, 1.0F);
                PlayerLookup.tracking(player).forEach((foundPlayer) -> SpawnSmokeParticlesPacket.send(foundPlayer, player));
                SpawnSmokeParticlesPacket.send(player, player);
                if (isInAlternateForm) {
                    width.setScale(width.getScale() / WEREWOLF_WIDTH);
                    height.setScale(height.getScale() / WEREWOLF_HEIGHT);
                } else {
                    width.setScale(width.getScale() * WEREWOLF_WIDTH);
                    height.setScale(height.getScale() * WEREWOLF_HEIGHT);
                }
            }else{

            }
            ci.cancel();
        }
    }
}
