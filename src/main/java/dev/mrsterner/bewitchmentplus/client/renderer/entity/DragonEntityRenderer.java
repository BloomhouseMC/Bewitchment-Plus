package dev.mrsterner.bewitchmentplus.client.renderer.entity;

import dev.mrsterner.bewitchmentplus.api.Magical;
import dev.mrsterner.bewitchmentplus.client.model.entity.DragonEntityModel;
import dev.mrsterner.bewitchmentplus.client.model.entity.EffigyEntityModel;
import dev.mrsterner.bewitchmentplus.common.entity.DragonEntity;
import dev.mrsterner.bewitchmentplus.common.entity.EffigyEntity;
import dev.mrsterner.bewitchmentplus.common.entity.NifflerEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.util.Color;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class DragonEntityRenderer extends GeoEntityRenderer<DragonEntity> {
    public DragonEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new DragonEntityModel());
    }

    @Override
    public Color getRenderColor(DragonEntity animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn) {
        return Color.ofRGBA(1.0F,1.0F,1.0F, MinecraftClient.getInstance().getCameraEntity() instanceof AbstractClientPlayerEntity playerEntity && Magical.of(playerEntity).get().hasMagical() ? 1 : 0.1F);
    }

    @Override
    public RenderLayer getRenderType(DragonEntity animatable, float partialTicks, MatrixStack stack, @Nullable VertexConsumerProvider renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(this.getTextureLocation(animatable));
    }
}