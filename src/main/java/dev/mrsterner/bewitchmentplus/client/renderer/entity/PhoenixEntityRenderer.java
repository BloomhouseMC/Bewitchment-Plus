package dev.mrsterner.bewitchmentplus.client.renderer.entity;

import dev.mrsterner.bewitchmentplus.client.model.entity.PhoenixEntityModel;
import dev.mrsterner.bewitchmentplus.common.entity.PhoenixEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class PhoenixEntityRenderer extends GeoEntityRenderer<PhoenixEntity> {
    public PhoenixEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new PhoenixEntityModel());
    }

/*TODO add Magical
    @Override
    public Color getRenderColor(PhoenixEntity animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn) {
        return Color.ofRGBA(1.0F,1.0F,1.0F, MinecraftClient.getInstance().getCameraEntity() instanceof AbstractClientPlayerEntity playerEntity && Magical.of(playerEntity).get().hasMagical() ? 1 : 0.1F);
    }

 */


    @Override
    public RenderLayer getRenderType(PhoenixEntity animatable, float partialTicks, MatrixStack stack, @Nullable VertexConsumerProvider renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(this.getTextureLocation(animatable));
    }
}