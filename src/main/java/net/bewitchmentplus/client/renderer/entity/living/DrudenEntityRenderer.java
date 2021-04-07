package net.bewitchmentplus.client.renderer.entity.living;

import moriyashiine.bewitchment.common.entity.living.util.BWHostileEntity;
import net.bewitchmentplus.BewitchmentPlus;
import net.bewitchmentplus.client.model.entity.living.DrudenEntityModel;
import net.bewitchmentplus.common.entity.living.DrudenEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class DrudenEntityRenderer extends MobEntityRenderer<DrudenEntity, DrudenEntityModel<DrudenEntity>> {
	private static Identifier[] TEXTURES;

	public DrudenEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
		super(entityRenderDispatcher, new DrudenEntityModel<>(), 0.5f);
		addFeature(new HeldItemFeatureRenderer<>(this));
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
