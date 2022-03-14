package dev.mrsterner.bewitchmentplus.client.renderer.entity;

import dev.mrsterner.bewitchmentplus.client.model.entity.LeshonEntityModel;
import dev.mrsterner.bewitchmentplus.common.entity.LeshonEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class LeshonEntityRenderer extends GeoEntityRenderer<LeshonEntity> {
    public LeshonEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new LeshonEntityModel());
        this.shadowRadius = 2F;
    }

    @Override
    public RenderLayer getRenderType(LeshonEntity animatable, float partialTicks, MatrixStack stack, @Nullable VertexConsumerProvider renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(this.getTextureLocation(animatable));
    }

    @Override
    public void renderRecursively(GeoBone bone, MatrixStack stack, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (bone.getName().equals("rightItem") && !mainHand.isEmpty()) {
            if(!MinecraftClient.getInstance().options.getPerspective().isFirstPerson()) {
                stack.push();
                stack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-75));
                stack.translate(0.4D, -0.2D, 0.9D);
                stack.scale(1.0f, 1.0f, 1.0f);
                MinecraftClient.getInstance().getItemRenderer().renderItem(mainHand, ModelTransformation.Mode.THIRD_PERSON_RIGHT_HAND, packedLightIn, packedOverlayIn, stack, rtb,0);
                stack.pop();
                bufferIn = rtb.getBuffer(RenderLayer.getEntityTranslucent(whTexture));
            }
        }else if (bone.getName().equals("leftItem") && !offHand.isEmpty()) {
            if (!MinecraftClient.getInstance().options.getPerspective().isFirstPerson()) {
                stack.push();
                stack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-165));
                stack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180));
                stack.translate(0.4D, -1.2D, -0.2D);
                stack.scale(1.0f, 1.0f, 1.0f);
                MinecraftClient.getInstance().getItemRenderer().renderItem(offHand, ModelTransformation.Mode.THIRD_PERSON_LEFT_HAND, packedLightIn, packedOverlayIn, stack, rtb,0);
                stack.pop();
                bufferIn = rtb.getBuffer(RenderLayer.getEntityTranslucent(whTexture));
            }
        }
        super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}
