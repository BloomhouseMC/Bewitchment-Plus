package dev.mrsterner.bewitchmentplus.client.renderer.entity;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.api.Magical;
import dev.mrsterner.bewitchmentplus.client.model.entity.UnicornEntityModel;
import dev.mrsterner.bewitchmentplus.common.entity.PhoenixEntity;
import dev.mrsterner.bewitchmentplus.common.entity.UnicornEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib3.core.util.Color;

public class UnicornEntityRenderer extends MobEntityRenderer<UnicornEntity, UnicornEntityModel<UnicornEntity>> {
    public UnicornEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new UnicornEntityModel<>(context.getPart(UnicornEntityModel.UNICORN_MODEL_LAYER)), 0.5f);
    }


    @Override
    public void render(UnicornEntity livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        this.model.handSwingProgress = this.getHandSwingProgress(livingEntity, g);
        float h = MathHelper.lerpAngleDegrees(g, livingEntity.prevBodyYaw, livingEntity.bodyYaw);
        float k = MathHelper.lerpAngleDegrees(g, livingEntity.prevHeadYaw, livingEntity.headYaw) - h;
        float m = MathHelper.lerp(g, livingEntity.prevPitch, livingEntity.getPitch());
        float l = this.getAnimationProgress(livingEntity, g);
        this.setupTransforms(livingEntity, matrixStack, l, h, g);
        matrixStack.scale(-1.0F, -1.0F, 1.0F);
        matrixStack.scale(2,2,2);
        this.scale(livingEntity, matrixStack, g);
        matrixStack.translate(0.0D, -1.5010000467300415D * 0.95D, 0.0D);
        float n = 0.0F;
        float o = 0.0F;
        if (!livingEntity.hasVehicle() && livingEntity.isAlive()) {
            n = MathHelper.lerp(g, livingEntity.lastLimbDistance, livingEntity.limbDistance);
            o = livingEntity.limbAngle - livingEntity.limbDistance * (1.0F - g);
            if (n > 1.0F) {
                n = 1.0F;
            }
        }

        this.model.animateModel(livingEntity, o, n, g);
        this.model.setAngles(livingEntity, o, n, l, k, m);
        RenderLayer renderLayer = RenderLayer.getEntityTranslucent(new Identifier(BewitchmentPlus.MODID, "textures/entity/unicorn/0.png"));
        if (renderLayer != null) {
            VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(renderLayer);
            int p = getOverlay(livingEntity, this.getAnimationCounter(livingEntity, g));
            this.model.render(matrixStack, vertexConsumer, i, p, 1.0F, 1.0F, 1.0F, MinecraftClient.getInstance().getCameraEntity() instanceof AbstractClientPlayerEntity playerEntity && Magical.of(playerEntity).get().hasMagical() ? 1 : 0.1F);
        }
        matrixStack.pop();
    }

    @Override
    public Identifier getTexture(UnicornEntity entity) {
        return new Identifier(BewitchmentPlus.MODID, "textures/entity/unicorn/0.png");
    }
}
