package dev.mrsterner.bewitchmentplus.mixin.client;

import dev.mrsterner.bewitchmentplus.common.registry.BWPObjects;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(EntityRenderDispatcher.class)
public abstract class EntityRenderDispatcherMixin {

    @Inject(method = "renderFire", at = @At("HEAD"), cancellable = true)
    private void dontGetSprite(MatrixStack matrices, VertexConsumerProvider vertexConsumers, Entity entity, CallbackInfo ci){
        if(entity instanceof PlayerEntity player){
            if(player.getEquippedStack(EquipmentSlot.CHEST).getItem().equals(BWPObjects.DEATHS_ROBES)){
                ci.cancel();
            }
        }
    }
}