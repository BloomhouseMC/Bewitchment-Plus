package dev.mrsterner.bewitchmentplus.client.renderer;

import dev.mrsterner.bewitchmentplus.client.model.DragonbloodStaffModel;
import dev.mrsterner.bewitchmentplus.common.item.DragonbloodStaffItem;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class DragonbloodStaffRenderer extends GeoItemRenderer<DragonbloodStaffItem> {
    public DragonbloodStaffRenderer() {
        super(new DragonbloodStaffModel());
    }

    @Override
    public RenderLayer getRenderType(DragonbloodStaffItem animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
    }
}