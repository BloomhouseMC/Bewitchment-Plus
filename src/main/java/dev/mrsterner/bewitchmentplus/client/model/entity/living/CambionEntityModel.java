package dev.mrsterner.bewitchmentplus.client.model.entity.living;

import dev.mrsterner.bewitchmentplus.common.entity.CambionEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;

@Environment(EnvType.CLIENT)
public class CambionEntityModel extends BipedEntityModel<CambionEntity> {
	private final ModelPart body;
	private final ModelPart left_arm;
	private final ModelPart right_arm;
	private final ModelPart left_leg;
	private final ModelPart right_leg;
	private final ModelPart head;

	public CambionEntityModel(ModelPart root) {
		super(root);
		this.body = root.getChild("body");
		this.left_arm = root.getChild("left_arm");
		this.right_arm = root.getChild("right_arm");
		this.left_leg = root.getChild("left_leg");
		this.right_leg = root.getChild("right_leg");
		this.head = root.getChild("head");

	}

	public static TexturedModelData getTexturedModelDataMale() {
			ModelData data = new ModelData();
			ModelPartData root = data.getRoot();
		root.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.NONE);
		ModelPartData body = root.addChild("body", ModelPartBuilder.create().uv(16, 16).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 13.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -2.0F, 0.0F));
		ModelPartData left_arm = root.addChild("left_arm", ModelPartBuilder.create().uv(40, 16).cuboid(-1.0F, -2.0F, -2.0F, 4.0F, 14.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(5.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1F));
		ModelPartData lClaws = left_arm.addChild("lClaws", ModelPartBuilder.create().uv(0, 34).cuboid(-1.1F, 0.0F, -2.1F, 2.0F, 3.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(2.4F, 11.5F, 0.0F, 0.0F, 0.0F, 0.2793F));
		ModelPartData right_arm = root.addChild("right_arm", ModelPartBuilder.create().uv(40, 16).mirrored(true).cuboid(-3.0F, -2.0F, -2.0F, 4.0F, 14.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-5.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1F));
		ModelPartData rClaws = right_arm.addChild("rClaws", ModelPartBuilder.create().uv(0, 34).mirrored(true).cuboid(-1.1F, 0.0F, -2.1F, 2.0F, 3.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-2.4F, 11.5F, 0.0F, 0.0F, 0.0F, -0.2793F));
		ModelPartData left_leg = root.addChild("left_leg", ModelPartBuilder.create().uv(0, 16).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 13.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(1.9F, 11.0F, 0.0F));
		ModelPartData right_leg = root.addChild("right_leg", ModelPartBuilder.create().uv(0, 16).mirrored(true).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 13.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-1.9F, 11.0F, 0.0F));
		ModelPartData head = root.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -2.0F, 0.0F));
		ModelPartData snout = head.addChild("snout", ModelPartBuilder.create().uv(21, 35).cuboid(-1.5F, -1.0F, -2.0F, 3.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.5F, -3.9F, 0.4189F, 0.0F, 0.0F));
		ModelPartData upperJaw = head.addChild("upperJaw", ModelPartBuilder.create().uv(21, 41).cuboid(-2.0F, -1.0F, -1.7F, 4.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -1.4F, -4.0F));
		ModelPartData lowerJaw = head.addChild("lowerJaw", ModelPartBuilder.create().uv(21, 46).cuboid(-1.5F, -0.5F, -1.6F, 3.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -0.4F, -4.0F));
		ModelPartData lUpperHorn01 = head.addChild("lUpperHorn01", ModelPartBuilder.create().uv(24, 0).mirrored(true).cuboid(-1.0F, -1.0F, -1.8F, 2.0F, 2.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(2.0F, -8.7F, -0.5F, 0.8727F, 0.1745F, 0.0F));
		ModelPartData lUpperHorn02 = lUpperHorn01.addChild("lUpperHorn02", ModelPartBuilder.create().uv(33, 7).mirrored(true).cuboid(-0.5F, -0.6F, -0.5F, 1.0F, 1.0F, 3.0F, new Dilation(0.2F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.9F, -0.3142F, 0.2618F, 0.0F));
		ModelPartData lUpperHorn03 = lUpperHorn02.addChild("lUpperHorn03", ModelPartBuilder.create().uv(42, 7).mirrored(true).cuboid(-0.5F, -0.5F, -0.4F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 2.4F, -0.1745F, 0.1745F, 0.0F));
		ModelPartData rUpperHorn01 = head.addChild("rUpperHorn01", ModelPartBuilder.create().uv(24, 0).mirrored(true).cuboid(-1.0F, -1.0F, -1.8F, 2.0F, 2.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-2.0F, -8.7F, -0.5F, 0.8727F, -0.1745F, 0.0F));
		ModelPartData rUpperHorn02 = rUpperHorn01.addChild("rUpperHorn02", ModelPartBuilder.create().uv(33, 7).mirrored(true).cuboid(-0.5F, -0.6F, -0.5F, 1.0F, 1.0F, 3.0F, new Dilation(0.2F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.9F, -0.3142F, -0.2618F, 0.0F));
		ModelPartData rUpperHorn03 = rUpperHorn02.addChild("rUpperHorn03", ModelPartBuilder.create().uv(42, 7).mirrored(true).cuboid(-0.5F, -0.5F, -0.4F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 2.4F, -0.1745F, -0.1745F, 0.0F));
		ModelPartData rHorn01 = head.addChild("rHorn01", ModelPartBuilder.create().uv(0, 0).mirrored(true).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-2.9F, -7.3F, 0.6F, -0.6109F, 0.0F, -1.309F));
		ModelPartData rHorn02 = rHorn01.addChild("rHorn02", ModelPartBuilder.create().uv(0, 4).mirrored(true).cuboid(-0.4F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.2F)).mirrored(false), ModelTransform.of(0.1F, -1.5F, -0.1F, -0.2618F, 0.0F, -0.4014F));
		ModelPartData rHorn03 = rHorn02.addChild("rHorn03", ModelPartBuilder.create().uv(0, 4).mirrored(true).cuboid(-0.2F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.1F)).mirrored(false), ModelTransform.of(0.0F, -1.6F, 0.0F, -0.1745F, 0.0F, -0.4363F));
		ModelPartData rHorn04 = rHorn03.addChild("rHorn04", ModelPartBuilder.create().uv(49, 0).mirrored(true).cuboid(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, -1.7F, 0.0F, 0.0524F, 0.0F, -0.1396F));
		ModelPartData rHorn05 = rHorn04.addChild("rHorn05", ModelPartBuilder.create().uv(43, 0).mirrored(true).cuboid(-0.5F, -2.1F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, -2.7F, 0.0F, 0.0524F, 0.0F, 0.3142F));
		ModelPartData lHorn01 = head.addChild("lHorn01", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(2.9F, -7.3F, 0.6F, -0.6109F, 0.0F, 1.309F));
		ModelPartData lHorn02 = lHorn01.addChild("lHorn02", ModelPartBuilder.create().uv(0, 4).cuboid(-0.6F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.2F)), ModelTransform.of(-0.1F, -1.5F, -0.1F, -0.2618F, 0.0F, 0.4014F));
		ModelPartData lHorn03 = lHorn02.addChild("lHorn03", ModelPartBuilder.create().uv(0, 4).cuboid(-0.8F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.1F)), ModelTransform.of(0.0F, -1.6F, 0.0F, -0.1745F, 0.0F, 0.4363F));
		ModelPartData lHorn04 = lHorn03.addChild("lHorn04", ModelPartBuilder.create().uv(49, 0).cuboid(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.7F, 0.0F, 0.0524F, 0.0F, 0.1396F));
		ModelPartData lHorn05 = lHorn04.addChild("lHorn05", ModelPartBuilder.create().uv(43, 0).cuboid(-0.5F, -2.1F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.7F, 0.0F, 0.0524F, 0.0F, -0.3142F));
		ModelPartData hair01 = head.addChild("hair01", ModelPartBuilder.create().uv(40, 36).cuboid(-3.99F, 0.0F, -0.1F, 8.0F, 8.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -7.2F, 3.2F, 0.3491F, 0.0F, 0.0F));
		ModelPartData hair00 = head.addChild("hair00", ModelPartBuilder.create().uv(38, 46).cuboid(-4.01F, 0.0F, -1.0F, 8.0F, 11.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -5.5F, 3.2F, 0.1396F, 0.0F, 0.0F));
		return TexturedModelData.of(data, 64, 64);
	}
	public static TexturedModelData getTexturedModelDataFemale() {
		ModelData data = new ModelData();
		ModelPartData root = data.getRoot();
		root.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.NONE);
		ModelPartData body = root.addChild("body", ModelPartBuilder.create().uv(16, 16).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 13.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -2.0F, 0.0F));
		ModelPartData skirtFront = body.addChild("skirtFront", ModelPartBuilder.create().uv(0, 42).cuboid(-4.5F, -0.1F, -0.5F, 9.0F, 9.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 12.9F, -1.7F));
		ModelPartData skirtBack = body.addChild("skirtBack", ModelPartBuilder.create().uv(0, 53).cuboid(-4.5F, -0.1F, -0.5F, 9.0F, 9.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 12.9F, 0.6F));
		ModelPartData left_arm = root.addChild("left_arm", ModelPartBuilder.create().uv(40, 16).cuboid(-1.0F, -2.0F, -2.0F, 3.0F, 14.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(5.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1F));
		ModelPartData lClaws = left_arm.addChild("lClaws", ModelPartBuilder.create().uv(0, 34).cuboid(-1.1F, 0.0F, -2.1F, 2.0F, 3.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(1.4F, 11.5F, 0.0F, 0.0F, 0.0F, 0.2793F));
		ModelPartData right_arm = root.addChild("right_arm", ModelPartBuilder.create().uv(40, 16).mirrored(true).cuboid(-2.0F, -2.0F, -2.0F, 3.0F, 14.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-5.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1F));
		ModelPartData rClaws = right_arm.addChild("rClaws", ModelPartBuilder.create().uv(0, 34).mirrored(true).cuboid(-1.1F, 0.0F, -2.1F, 2.0F, 3.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-1.15F, 11.5F, 0.0F, 0.0F, 0.0F, -0.2793F));
		ModelPartData left_leg = root.addChild("left_leg", ModelPartBuilder.create().uv(0, 16).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 13.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(1.9F, 11.0F, 0.0F));
		ModelPartData right_leg = root.addChild("right_leg", ModelPartBuilder.create().uv(0, 16).mirrored(true).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 13.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-1.9F, 11.0F, 0.0F));
		ModelPartData head = root.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -2.0F, 0.0F));
		ModelPartData snout = head.addChild("snout", ModelPartBuilder.create().uv(21, 35).cuboid(-1.5F, -1.0F, -2.0F, 3.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.5F, -3.9F, 0.4189F, 0.0F, 0.0F));
		ModelPartData upperJaw = head.addChild("upperJaw", ModelPartBuilder.create().uv(23, 41).cuboid(-2.0F, -1.0F, -1.7F, 4.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -1.4F, -4.0F));
		ModelPartData lowerJaw = head.addChild("lowerJaw", ModelPartBuilder.create().uv(23, 46).cuboid(-1.5F, -0.5F, -1.6F, 3.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -0.4F, -4.0F));
		ModelPartData lUpperHorn01 = head.addChild("lUpperHorn01", ModelPartBuilder.create().uv(24, 0).mirrored(true).cuboid(-1.0F, -1.0F, -1.8F, 2.0F, 2.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(2.0F, -8.7F, -0.5F, 0.8727F, 0.1745F, 0.0F));
		ModelPartData lUpperHorn02 = lUpperHorn01.addChild("lUpperHorn02", ModelPartBuilder.create().uv(33, 7).mirrored(true).cuboid(-0.5F, -0.6F, -0.5F, 1.0F, 1.0F, 3.0F, new Dilation(0.2F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.9F, -0.3142F, 0.2618F, 0.0F));
		ModelPartData lUpperHorn03 = lUpperHorn02.addChild("lUpperHorn03", ModelPartBuilder.create().uv(42, 7).mirrored(true).cuboid(-0.5F, -0.5F, -0.4F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 2.4F, -0.1745F, 0.1745F, 0.0F));
		ModelPartData rUpperHorn01 = head.addChild("rUpperHorn01", ModelPartBuilder.create().uv(24, 0).mirrored(true).cuboid(-1.0F, -1.0F, -1.8F, 2.0F, 2.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-2.0F, -8.7F, -0.5F, 0.8727F, -0.1745F, 0.0F));
		ModelPartData rUpperHorn02 = rUpperHorn01.addChild("rUpperHorn02", ModelPartBuilder.create().uv(33, 7).mirrored(true).cuboid(-0.5F, -0.6F, -0.5F, 1.0F, 1.0F, 3.0F, new Dilation(0.2F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.9F, -0.3142F, -0.2618F, 0.0F));
		ModelPartData rUpperHorn03 = rUpperHorn02.addChild("rUpperHorn03", ModelPartBuilder.create().uv(42, 7).mirrored(true).cuboid(-0.5F, -0.5F, -0.4F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 2.4F, -0.1745F, -0.1745F, 0.0F));
		ModelPartData rHorn01 = head.addChild("rHorn01", ModelPartBuilder.create().uv(0, 0).mirrored(true).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-2.9F, -7.3F, 0.6F, -0.6109F, 0.0F, -1.309F));
		ModelPartData rHorn02 = rHorn01.addChild("rHorn02", ModelPartBuilder.create().uv(0, 4).mirrored(true).cuboid(-0.4F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.2F)).mirrored(false), ModelTransform.of(0.1F, -1.5F, -0.1F, -0.2618F, 0.0F, -0.4014F));
		ModelPartData rHorn03 = rHorn02.addChild("rHorn03", ModelPartBuilder.create().uv(0, 4).mirrored(true).cuboid(-0.2F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.1F)).mirrored(false), ModelTransform.of(0.0F, -1.6F, 0.0F, -0.1745F, 0.0F, -0.4363F));
		ModelPartData rHorn04 = rHorn03.addChild("rHorn04", ModelPartBuilder.create().uv(49, 0).mirrored(true).cuboid(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, -1.7F, 0.0F, 0.0524F, 0.0F, -0.1396F));
		ModelPartData rHorn05 = rHorn04.addChild("rHorn05", ModelPartBuilder.create().uv(43, 0).mirrored(true).cuboid(-0.5F, -2.1F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, -2.7F, 0.0F, 0.0524F, 0.0F, 0.3142F));
		ModelPartData lHorn01 = head.addChild("lHorn01", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(2.9F, -7.3F, 0.6F, -0.6109F, 0.0F, 1.309F));
		ModelPartData lHorn02 = lHorn01.addChild("lHorn02", ModelPartBuilder.create().uv(0, 4).cuboid(-0.6F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.2F)), ModelTransform.of(-0.1F, -1.5F, -0.1F, -0.2618F, 0.0F, 0.4014F));
		ModelPartData lHorn03 = lHorn02.addChild("lHorn03", ModelPartBuilder.create().uv(0, 4).cuboid(-0.8F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.1F)), ModelTransform.of(0.0F, -1.6F, 0.0F, -0.1745F, 0.0F, 0.4363F));
		ModelPartData lHorn04 = lHorn03.addChild("lHorn04", ModelPartBuilder.create().uv(49, 0).cuboid(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.7F, 0.0F, 0.0524F, 0.0F, 0.1396F));
		ModelPartData lHorn05 = lHorn04.addChild("lHorn05", ModelPartBuilder.create().uv(43, 0).cuboid(-0.5F, -2.1F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.7F, 0.0F, 0.0524F, 0.0F, -0.3142F));
		ModelPartData hair01 = head.addChild("hair01", ModelPartBuilder.create().uv(40, 36).cuboid(-3.99F, 0.0F, -0.1F, 8.0F, 8.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -7.2F, 3.2F, 0.3491F, 0.0F, 0.0F));
		ModelPartData hair00 = head.addChild("hair00", ModelPartBuilder.create().uv(38, 46).cuboid(-4.01F, 0.0F, -1.0F, 8.0F, 11.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -5.5F, 3.2F, 0.1396F, 0.0F, 0.0F));
		return TexturedModelData.of(data, 64, 64);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		body.render(matrices, vertices, light, overlay);
		left_arm.render(matrices, vertices, light, overlay);
		right_arm.render(matrices, vertices, light, overlay);
		left_leg.render(matrices, vertices, light, overlay);
		right_leg.render(matrices, vertices, light, overlay);
		head.render(matrices, vertices, light, overlay);
	}

	public void setRotateAngle(ModelPart ModelRenderer, float x, float y, float z) {
			ModelRenderer.pitch = x;
			ModelRenderer.yaw = y;
			ModelRenderer.roll = z;
		}
}
