package dev.mrsterner.bewitchmentplus.mixin.client;

import dev.mrsterner.bewitchmentplus.client.renderlayer.BWPRenderLayers;
import dev.mrsterner.bewitchmentplus.common.entity.LeshonEntity;
import dev.mrsterner.bewitchmentplus.common.item.GobletBlockItem;
import dev.mrsterner.bewitchmentplus.common.registry.BWPMaterials;
import dev.mrsterner.bewitchmentplus.common.registry.BWPObjects;
import dev.mrsterner.bewitchmentplus.common.utils.BWPUtil;
import moriyashiine.bewitchment.common.item.AthameItem;
import moriyashiine.bewitchment.common.misc.BWUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    public PlayerEntityRendererMixin(EntityRendererFactory.Context ctx, PlayerEntityModel<AbstractClientPlayerEntity> model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }


    /**
     * This is where RenderEvents hooks. As well as disabling player rendering while player is leshon
     * @param player clientsided player entity
     * @param yaw
     * @param tickDelta
     * @param matrixStack
     * @param vertexConsumerProvider
     * @param light
     * @param callbackInfo
     */
    @Inject(method = "render(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At("HEAD"), cancellable = true)
    private void render(AbstractClientPlayerEntity player, float yaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, CallbackInfo callbackInfo) {
        LeshonEntity leshonEntity = BWPUtil.getLeshon(player);
        if(leshonEntity!=null){
            leshonEntity.age = player.age;
            leshonEntity.hurtTime = player.hurtTime;
            leshonEntity.maxHurtTime = Integer.MAX_VALUE;
            leshonEntity.limbDistance = player.limbDistance;
            leshonEntity.lastLimbDistance = player.lastLimbDistance;
            leshonEntity.limbAngle = player.limbAngle;
            leshonEntity.headYaw = player.headYaw;
            leshonEntity.prevHeadYaw = player.prevHeadYaw;
            leshonEntity.bodyYaw = player.bodyYaw;
            leshonEntity.prevBodyYaw = player.prevBodyYaw;
            leshonEntity.handSwinging = player.handSwinging;
            leshonEntity.handSwingTicks = player.handSwingTicks;
            leshonEntity.handSwingProgress = player.handSwingProgress;
            leshonEntity.lastHandSwingProgress = player.lastHandSwingProgress;
            leshonEntity.setPitch(player.getPitch());
            leshonEntity.prevPitch = player.prevPitch;
            leshonEntity.preferredHand = player.preferredHand;
            leshonEntity.setStackInHand(Hand.MAIN_HAND, player.getMainHandStack());
            leshonEntity.setStackInHand(Hand.OFF_HAND, player.getOffHandStack());
            leshonEntity.setCurrentHand(player.getActiveHand() == null ? Hand.MAIN_HAND : player.getActiveHand());
            leshonEntity.setSneaking(player.isSneaking());
            leshonEntity.motionCalc = new Vec3d(player.getX()-player.prevX, player.getY()-player.prevY,player.getZ()-player.prevZ);
            leshonEntity.isSneaking();
            leshonEntity.forwardSpeed=player.forwardSpeed;
            leshonEntity.setPose(player.getPose());
            leshonEntity.setSprinting(player.isSprinting());
            MinecraftClient.getInstance().getEntityRenderDispatcher().getRenderer(leshonEntity).render(leshonEntity, yaw, tickDelta, matrixStack, vertexConsumerProvider, light);
            callbackInfo.cancel();
        }


        if(BWUtil.getArmorPieces(player, (stack) -> stack.getItem() instanceof ArmorItem && ((ArmorItem)stack.getItem()).getMaterial() == BWPMaterials.DEATH_ARMOR) == 3){
            RenderLayer renderLayer = BWPRenderLayers.SHADOW.apply(this.getTexture(player));
            if (renderLayer != null) {
                VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(renderLayer);
                int o = getOverlay(player, this.getAnimationCounter(player, tickDelta));
                this.getModel().setVisible(true);
                matrixStack.push();
                matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0F - player.bodyYaw));
                matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(180.0F));
                matrixStack.translate(0,-1.4,0);
                matrixStack.scale(0.8f,0.8f,0.8f);
                this.getModel().hat.visible = false;
                this.getModel().jacket.visible = false;
                this.getModel().leftPants.visible = false;
                this.getModel().rightPants.visible = false;
                this.getModel().leftSleeve.visible = false;
                this.getModel().rightSleeve.visible = false;
                this.getModel().render(matrixStack, vertexConsumer, light, o, 1.0F, 1.0F, 1.0F, 1.0F);
                matrixStack.pop();

            }
        }
    }

    /**
     * When the player is holding athame and goblet, we use the Block pose to resemble cutting action
     * @param abstractClientPlayerEntity
     * @param hand
     * @param cir
     */
    @Inject(at = @At("HEAD"), method = "getArmPose", cancellable = true)
    @Environment(EnvType.CLIENT)
    private static void getArmPose(AbstractClientPlayerEntity abstractClientPlayerEntity, Hand hand, CallbackInfoReturnable<BipedEntityModel.ArmPose> cir) {
        if (abstractClientPlayerEntity.getMainHandStack().getItem() instanceof AthameItem) {
            if (abstractClientPlayerEntity.getOffHandStack().getItem() instanceof GobletBlockItem) {
                cir.setReturnValue(BipedEntityModel.ArmPose.BLOCK);
            }
        }
    }
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/PlayerEntityRenderer;getArmPose(Lnet/minecraft/client/network/AbstractClientPlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/client/render/entity/model/BipedEntityModel$ArmPose;"), method = "setModelPose")
    public void setModelPoseRedirect(AbstractClientPlayerEntity player, CallbackInfo ci) {
        PlayerEntityModel<AbstractClientPlayerEntity> playerEntityModel = this.getModel();
        if(player.getEquippedStack(EquipmentSlot.HEAD).getItem().equals(BWPObjects.LESHON_SKULL.asItem())){
            playerEntityModel.head.visible = false;
        }
        if(BWUtil.getArmorPieces(player, (stack) -> stack.getItem() instanceof ArmorItem && ((ArmorItem)stack.getItem()).getMaterial() == BWPMaterials.DEATH_ARMOR) == 3){
            playerEntityModel.setVisible(false);
        }
    }
}
