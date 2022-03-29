package dev.mrsterner.bewitchmentplus.client.renderer.entity;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.client.model.entity.LilimEntityModel;
import dev.mrsterner.bewitchmentplus.common.entity.LilimEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class LilimEntityRenderer extends BipedEntityRenderer<LilimEntity, BipedEntityModel<LilimEntity>> {

	public LilimEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new LilimEntityModel(context.getPart(LilimEntityModel.LILIM_MODEL_LAYER)), 0.5f);
	}

	@Override
	public Identifier getTexture(LilimEntity entity) {
		return new Identifier(BewitchmentPlus.MODID, "textures/entity/lilim/0.png");
	}

	@Override
	public void render(LilimEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
		matrixStack.translate(0,0.5F,0);
		super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
	}
}
