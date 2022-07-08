package dev.mrsterner.bewitchmentplus.client.renderer;

import dev.mrsterner.bewitchmentplus.client.model.MoonflowerModel;
import dev.mrsterner.bewitchmentplus.common.block.blockentity.MoonflowerBlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class MoonflowerBlockEntityRenderer extends GeoBlockRenderer<MoonflowerBlockEntity> {
    public MoonflowerBlockEntityRenderer() {
        super(new MoonflowerModel());
    }

    @Override
    public RenderLayer getRenderType(MoonflowerBlockEntity animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
    }
}