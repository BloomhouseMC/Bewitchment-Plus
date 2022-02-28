package dev.mrsterner.bewitchmentplus.client.model;


import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

public class GobletItemModel<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new Identifier(BewitchmentPlus.MODID, "goblet"), "main");
	private final ModelPart group;

	public GobletItemModel(ModelPart root) {
		this.group = root.getChild("group");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData data = new ModelData();
		ModelPartData root = data.getRoot();

		ModelPartData group = root.addChild("group", ModelPartBuilder.create().uv(0, 0).cuboid(-2.5F, 4.0F, -2.5F, 5.0F, 1.0F, 5.0F, new Dilation(0.0F))
		.uv(11, 6).cuboid(1.5F, 0.0F, -2.5F, 1.0F, 4.0F, 5.0F, new Dilation(0.0F))
		.uv(0, 11).cuboid(-2.5F, 0.0F, -2.5F, 1.0F, 4.0F, 5.0F, new Dilation(0.0F))
		.uv(12, 15).cuboid(-1.5F, 0.0F, -2.5F, 3.0F, 4.0F, 1.0F, new Dilation(0.0F))
		.uv(15, 0).cuboid(-1.5F, 0.0F, 1.5F, 3.0F, 4.0F, 1.0F, new Dilation(0.0F))
		.uv(18, 5).cuboid(-1.0F, 5.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 6).cuboid(-2.0F, 7.0F, -2.0F, 4.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 16.0F, 0.0F));

		return TexturedModelData.of(data, 32, 32);
	}



	@Override
	public void render(MatrixStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		group.render(poseStack, buffer, packedLight, packedOverlay);
	}

	@Override
	public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

	}
}