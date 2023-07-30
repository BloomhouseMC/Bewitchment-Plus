package dev.mrsterner.bewitchmentplus.client.renderer.entity;

import dev.mrsterner.bewitchmentplus.client.model.entity.LeshonEntityModel;
import dev.mrsterner.bewitchmentplus.common.entity.LeshonEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.DynamicGeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;

import static net.minecraft.client.render.entity.model.EntityModelPartNames.LEFT_HAND;
import static net.minecraft.client.render.entity.model.EntityModelPartNames.RIGHT_HAND;

public class LeshonEntityRenderer extends DynamicGeoEntityRenderer<LeshonEntity> {

    protected ItemStack mainHandItem;
    protected ItemStack offhandItem;

    public LeshonEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new LeshonEntityModel());
        this.shadowRadius = 2F;
        addRenderLayer(new BlockAndItemGeoLayer<>(this) {
            @Override
            protected ItemStack getStackForBone(GeoBone bone, LeshonEntity animatable) {
                return switch (bone.getName()) {
                    case LEFT_HAND -> animatable.isLeftHanded() ?
                            LeshonEntityRenderer.this.mainHandItem : LeshonEntityRenderer.this.offhandItem;
                    case RIGHT_HAND -> animatable.isLeftHanded() ?
                            LeshonEntityRenderer.this.offhandItem : LeshonEntityRenderer.this.mainHandItem;
                    default -> null;
                };
            }

            @Override
            protected ModelTransformationMode getTransformTypeForStack(GeoBone bone, ItemStack stack, LeshonEntity animatable) {
                return switch (bone.getName()) {
                    case LEFT_HAND, RIGHT_HAND -> ModelTransformationMode.THIRD_PERSON_RIGHT_HAND;
                    default -> ModelTransformationMode.NONE;
                };
            }

            @Override
            protected void renderStackForBone(MatrixStack poseStack, GeoBone bone, ItemStack stack, LeshonEntity animatable, VertexConsumerProvider bufferSource, float partialTick, int packedLight, int packedOverlay) {
                if (stack == LeshonEntityRenderer.this.mainHandItem) {
                    poseStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90f));

                    if (stack.getItem() instanceof ShieldItem)
                        poseStack.translate(0, 0.125, -0.25);
                }
                else if (stack == LeshonEntityRenderer.this.offhandItem) {
                    poseStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90f));

                    if (stack.getItem() instanceof ShieldItem) {
                        poseStack.translate(0, 0.125, 0.25);
                        poseStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
                    }
                }
                super.renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick, packedLight, packedOverlay);
            }
        });
    }

    @Override
    public RenderLayer getRenderType(LeshonEntity animatable, Identifier texture, VertexConsumerProvider bufferSource, float partialTick) {
        return RenderLayer.getEntityTranslucent(this.getTexture(animatable));
    }

    @Override
    public void preRender(MatrixStack poseStack, LeshonEntity animatable, BakedGeoModel model, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
        this.mainHandItem = animatable.getMainHandStack();
        this.offhandItem = animatable.getOffHandStack();
    }
}
