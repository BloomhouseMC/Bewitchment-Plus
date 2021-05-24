package net.bewitchmentplus.client.renderer.entity.living;

import moriyashiine.bewitchment.common.entity.living.util.BWHostileEntity;
import net.bewitchmentplus.BewitchmentPlus;
import net.bewitchmentplus.client.model.entity.living.CambionEntityModel;
import net.bewitchmentplus.common.entity.living.CambionEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class CambionEntityRenderer extends MobEntityRenderer<CambionEntity, CambionEntityModel<CambionEntity>> {
	private static Identifier[] MALE_TEXTURES, FEMALE_TEXTURES;

	public CambionEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
		super(entityRenderDispatcher, new CambionEntityModel<>(), 0.5f);
		addFeature(new HeldItemFeatureRenderer<>(this));
		this.addFeature(new ArmorFeatureRenderer<>(this, new BipedEntityModel<>(0.5F), new BipedEntityModel<>(1F)));
	}

	@Override
	public Identifier getTexture(CambionEntity entity) {
		if (MALE_TEXTURES == null) {
			int variants = entity.getVariants();
			MALE_TEXTURES = new Identifier[variants];
			FEMALE_TEXTURES = new Identifier[variants];
			for (int i = 0; i < variants; i++) {
				MALE_TEXTURES[i] = new Identifier(BewitchmentPlus.MODID, "textures/entity/living/cambion/male_" + i + ".png");
				FEMALE_TEXTURES[i] = new Identifier(BewitchmentPlus.MODID, "textures/entity/living/cambion/female_" + i + ".png");
			}
		}
		int variant = entity.getDataTracker().get(BWHostileEntity.VARIANT);
		return entity.getDataTracker().get(CambionEntity.MALE) ? MALE_TEXTURES[variant] : FEMALE_TEXTURES[variant];
	}
}
