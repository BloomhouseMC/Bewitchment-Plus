package dev.mrsterner.bewitchmentplus.client.renderer.entity;

import dev.mrsterner.bewitchmentplus.client.BewitchmentPlusClient;
import moriyashiine.bewitchment.common.entity.living.util.BWHostileEntity;
import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.client.model.entity.living.BlackDogEntityModel;
import dev.mrsterner.bewitchmentplus.common.entity.BlackDogEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class BlackDogEntityRenderer extends MobEntityRenderer<BlackDogEntity, BlackDogEntityModel<BlackDogEntity>> {
	private static Identifier[] TEXTURES;

	public BlackDogEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new BlackDogEntityModel<>(context.getPart(BewitchmentPlusClient.BLACKDOG_MODEL_LAYER)), 0.5f);
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
