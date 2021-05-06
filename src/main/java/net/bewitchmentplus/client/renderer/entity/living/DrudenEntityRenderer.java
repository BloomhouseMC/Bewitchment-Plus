package net.bewitchmentplus.client.renderer.entity.living;

import moriyashiine.bewitchment.common.entity.living.util.BWHostileEntity;
import net.bewitchmentplus.BewitchmentPlus;
import net.bewitchmentplus.client.model.entity.living.DrudenEntityModel;
import net.bewitchmentplus.common.entity.living.DrudenEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class DrudenEntityRenderer extends MobEntityRenderer<DrudenEntity, DrudenEntityModel<DrudenEntity>> {
    private static Identifier[] TEXTURES;
    private static Identifier[] EYES;

    public DrudenEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new DrudenEntityModel<>(), 0.5f);
        addFeature(new HeldItemFeatureRenderer<>(this));
        addFeature(new FeatureRenderer<DrudenEntity, DrudenEntityModel<DrudenEntity>>(this) {
            @Override
            public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, DrudenEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
                if (EYES == null) {
                    EYES = new Identifier[1];
                    EYES[0] = new Identifier(BewitchmentPlus.MODID, "textures/entity/living/druden/druden_eyes.png");
                }
                VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(EYES[0]));
                getContextModel().render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1);
            }
        });
    }

    @Override
    public Identifier getTexture(DrudenEntity entity) {
        if (TEXTURES == null) {
            int variants = entity.getVariants();
            TEXTURES = new Identifier[variants];
            for (int i = 0; i < variants; i++) {
                TEXTURES[i] = new Identifier(BewitchmentPlus.MODID, "textures/entity/living/druden/" + i + ".png");
            }
        }
        return TEXTURES[entity.getDataTracker().get(BWHostileEntity.VARIANT)];
    }
}
