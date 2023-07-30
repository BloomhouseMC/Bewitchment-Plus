package dev.mrsterner.bewitchmentplus.client.renderer.entity;

import dev.mrsterner.bewitchmentplus.client.model.entity.EffigyEntityModel;
import dev.mrsterner.bewitchmentplus.common.entity.EffigyEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class EffigyEntityRenderer extends GeoEntityRenderer<EffigyEntity> {
    public EffigyEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new EffigyEntityModel());
    }

    @Override
    public RenderLayer getRenderType(EffigyEntity animatable, Identifier texture, VertexConsumerProvider bufferSource, float partialTick) {
        return RenderLayer.getEntityTranslucent(this.getTexture(animatable));
    }
}