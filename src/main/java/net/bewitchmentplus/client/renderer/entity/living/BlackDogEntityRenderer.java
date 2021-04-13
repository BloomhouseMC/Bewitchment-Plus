package net.bewitchmentplus.client.renderer.entity.living;

import moriyashiine.bewitchment.common.entity.living.util.BWHostileEntity;
import moriyashiine.bewitchment.common.entity.living.util.BWTameableEntity;
import net.bewitchmentplus.BewitchmentPlus;
import net.bewitchmentplus.client.model.entity.living.BlackDogEntityModel;
import net.bewitchmentplus.common.entity.living.BlackDogEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class BlackDogEntityRenderer extends MobEntityRenderer<BlackDogEntity, BlackDogEntityModel<BlackDogEntity>> {
	private static Identifier[] TEXTURES;
	private static Identifier[] EYES;

	public BlackDogEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
		super(entityRenderDispatcher, new BlackDogEntityModel<>(), 0.5f);
	}

	@Override
	public Identifier getTexture(BlackDogEntity entity) {
		if (TEXTURES == null) {
			int variants = entity.getVariants();
			TEXTURES = new Identifier[variants];
			for (int i = 0; i < variants; i++) {
				TEXTURES[i] = new Identifier(BewitchmentPlus.MODID, "textures/entity/living/black_dog/" + i + ".png");
			}
		}
		return TEXTURES[entity.getDataTracker().get(BWHostileEntity.VARIANT)];
	}
}
