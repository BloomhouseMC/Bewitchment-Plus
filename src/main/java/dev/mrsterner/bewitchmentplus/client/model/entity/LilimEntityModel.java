package dev.mrsterner.bewitchmentplus.client.model.entity;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.common.entity.LilimEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class LilimEntityModel extends BipedEntityModel<LilimEntity>{
public static final EntityModelLayer LILIM_MODEL_LAYER = new EntityModelLayer(new Identifier(BewitchmentPlus.MODID, "lilim"), "main");
	private final ModelPart body;
	private final ModelPart left_arm;
	private final ModelPart right_arm;
	private final ModelPart left_leg;
	private final ModelPart right_leg;
	private final ModelPart head;
	public final ModelPart hat;

	public LilimEntityModel(ModelPart root) {
		super(root);
		this.hat = root.getChild(EntityModelPartNames.HAT);
		this.body = root.getChild("body");
		this.left_arm = root.getChild("left_arm");
		this.right_arm = root.getChild("right_arm");
		this.left_leg = root.getChild("left_leg");
		this.right_leg = root.getChild("right_leg");
		this.head = root.getChild("head");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData data = new ModelData();
		ModelPartData root = data.getRoot();
		root.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create().uv(32, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F).add(0.5F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		ModelPartData body = root.addChild("body", ModelPartBuilder.create().uv(19, 17).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 6.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -7.7F, 0.0F));
		ModelPartData boobs = body.addChild("boobs", ModelPartBuilder.create().uv(0, 57).cuboid(-3.5F, 0.0F, -2.0F, 7.0F, 4.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 1.9F, -0.3F, -0.6981F, 0.0F, 0.0F));
		boobs.addChild("boobHair", ModelPartBuilder.create().uv(0, 52).cuboid(-3.5F, 0.15F, -0.13F, 7.0F, 4.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 3.8F, -2.0F, 0.6981F, 0.0F, 0.0F));
		ModelPartData stomach = body.addChild("stomach", ModelPartBuilder.create().uv(19, 27).cuboid(-3.5F, 0.0F, -2.0F, 7.0F, 8.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 6.0F, 0.0F));
		stomach.addChild("frontLoincloth", ModelPartBuilder.create().uv(85, 0).cuboid(-4.5F, 0.0F, -0.6F, 9.0F, 9.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 6.3F, -1.6F, -0.2182F, 0.0F, 0.0F));
		ModelPartData backLoinclothTop = stomach.addChild("backLoinclothTop", ModelPartBuilder.create().uv(86, 14).cuboid(-4.0F, 0.0F, -0.5F, 8.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 6.6F, 1.05F, 0.3491F, 0.0F, 0.0F));
		backLoinclothTop.addChild("backLoinclothLower", ModelPartBuilder.create().uv(86, 20).cuboid(-4.0F, 0.0F, -0.5F, 8.0F, 9.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 1.5F, 0.1F, -0.3142F, 0.0F, 0.0F));
		stomach.addChild("lLoinCloth", ModelPartBuilder.create().uv(108, 10).cuboid(0.0F, 0.0F, -2.5F, 2.0F, 10.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(2.2F, 6.5F, 0.3F, -0.0698F, 0.0F, -0.1396F));
		stomach.addChild("rLoinCloth", ModelPartBuilder.create().uv(108, 10).mirrored().cuboid(-2.0F, 0.0F, -2.5F, 2.0F, 10.0F, 5.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-2.2F, 6.5F, 0.3F, -0.0698F, 0.0F, 0.1396F));
		ModelPartData lWing01 = body.addChild("lWing01", ModelPartBuilder.create().uv(26, 39).mirrored().cuboid(-1.0F, -1.5F, 0.0F, 2.0F, 3.0F, 7.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(2.5F, 3.2F, 1.4F, 0.2618F, 0.5236F, 0.0F));
		ModelPartData lWing02 = lWing01.addChild("lWing02", ModelPartBuilder.create().uv(38, 52).mirrored().cuboid(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 9.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.1F, 0.2F, 6.4F, 1.1781F, 0.0F, 0.0F));
		ModelPartData lWing03 = lWing02.addChild("lWing03", ModelPartBuilder.create().uv(29, 54).mirrored().cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.1F, -0.5F, 8.1F, -0.3403F, 0.0F, 0.0F));
		ModelPartData lWing04 = lWing03.addChild("lWing04", ModelPartBuilder.create().uv(22, 47).mirrored().cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 15.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 7.7F, 0.0F, -0.7854F, 0.0F, 0.0F));
		lWing03.addChild("lWingMembrane03", ModelPartBuilder.create().uv(61, 13).mirrored().cuboid(0.01F, -3.0F, -25.5F, 0.0F, 24.0F, 27.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 2.4F, -0.4F));
		lWing02.addChild("lWingMembrane02", ModelPartBuilder.create().uv(61, 12).mirrored().cuboid(0.0F, -2.5F, -2.5F, 0.0F, 16.0F, 12.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.5F, 0.0F, -1.3177F, 0.0F, 0.0F));
		lWing01.addChild("lWingMembrane01", ModelPartBuilder.create().uv(61, 2).mirrored().cuboid(-0.01F, -0.2F, -1.7F, 0.0F, 11.0F, 11.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, -1.1F, 0.0F, -0.3491F, 0.0F, 0.0F));
		ModelPartData rWing01 = body.addChild("rWing01", ModelPartBuilder.create().uv(26, 39).cuboid(-1.0F, -1.5F, 0.0F, 2.0F, 3.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(-2.5F, 3.2F, 1.4F, 0.2618F, -0.5236F, 0.0F));
		ModelPartData rWing02 = rWing01.addChild("rWing02", ModelPartBuilder.create().uv(38, 52).cuboid(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(-0.1F, 0.2F, 6.4F, 1.1781F, 0.0F, 0.0F));
		ModelPartData rWing03 = rWing02.addChild("rWing03", ModelPartBuilder.create().uv(29, 54).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-0.1F, -0.5F, 8.1F, -0.3403F, 0.0F, 0.0F));
		rWing03.addChild("rWing04", ModelPartBuilder.create().uv(22, 47).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 15.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 7.7F, 0.0F, -0.7854F, 0.0F, 0.0F));
		rWing03.addChild("rWingMembrane03", ModelPartBuilder.create().uv(61, 13).cuboid(-0.01F, -3.0F, -25.5F, 0.0F, 24.0F, 27.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 2.4F, -0.4F));
		rWing02.addChild("rWingMembrane02", ModelPartBuilder.create().uv(61, 12).cuboid(0.0F, -2.5F, -2.5F, 0.0F, 16.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.5F, 0.0F, -1.3177F, 0.0F, 0.0F));
		rWing01.addChild("rWingMembrane01", ModelPartBuilder.create().uv(61, 2).cuboid(0.01F, -0.2F, -1.7F, 0.0F, 11.0F, 11.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.1F, 0.0F, -0.3491F, 0.0F, 0.0F));
		root.addChild("left_arm", ModelPartBuilder.create().uv(44, 14).cuboid(-1.0F, -2.0F, -2.0F, 3.0F, 14.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(5.0F, -5.7F, 0.0F, 0.0F, 0.0F, -0.1F));
		root.addChild("right_arm", ModelPartBuilder.create().uv(44, 14).mirrored().cuboid(-2.0F, -2.0F, -2.0F, 3.0F, 14.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-5.0F, -5.7F, 0.0F, 0.0F, 0.0F, 0.1F));
		ModelPartData left_leg = root.addChild("left_leg", ModelPartBuilder.create().uv(0, 17).mirrored().cuboid(-2.0F, -1.3F, -2.5F, 4.0F, 10.0F, 5.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(2.1F, 6.3F, 0.1F, -0.1745F, 0.0F, -0.1047F));
		ModelPartData lLeg02 = left_leg.addChild("lLeg02", ModelPartBuilder.create().uv(0, 32).mirrored().cuboid(-1.5F, 0.0F, -2.0F, 3.0F, 6.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 7.9F, -0.4F, 0.4014F, 0.0F, 0.1047F));
		ModelPartData lLeg03 = lLeg02.addChild("lLeg03", ModelPartBuilder.create().uv(0, 42).mirrored().cuboid(-1.0F, -0.3F, -1.5F, 2.0F, 5.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 5.9F, 0.2F, -0.2094F, 0.0F, 0.0F));
		lLeg03.addChild("lTalon01", ModelPartBuilder.create().uv(12, 42).mirrored().cuboid(-0.5F, -0.5F, -4.0F, 1.0F, 2.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.5F, 3.5F, 0.0F, 0.0349F, -0.3142F, 0.0F));
		lLeg03.addChild("lTalon0", ModelPartBuilder.create().uv(12, 42).mirrored().cuboid(-0.5F, -0.5F, -4.0F, 1.0F, 2.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 3.3F, 0.0F, 0.0349F, 0.0F, 0.0F));
		lLeg03.addChild("lTalon03", ModelPartBuilder.create().uv(12, 42).mirrored().cuboid(-0.5F, -0.5F, -4.0F, 1.0F, 2.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-0.5F, 3.5F, 0.0F, 0.0349F, 0.3142F, 0.0F));
		ModelPartData right_leg = root.addChild("right_leg", ModelPartBuilder.create().uv(0, 17).mirrored().cuboid(-2.0F, -1.3F, -2.5F, 4.0F, 10.0F, 5.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-2.1F, 6.3F, 0.1F, -0.1745F, 0.0F, 0.1047F));
		ModelPartData rLeg02 = right_leg.addChild("rLeg02", ModelPartBuilder.create().uv(0, 32).mirrored().cuboid(-1.5F, 0.0F, -2.0F, 3.0F, 6.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 7.9F, -0.4F, 0.4014F, 0.0F, -0.1047F));
		ModelPartData rLeg03 = rLeg02.addChild("rLeg03", ModelPartBuilder.create().uv(0, 42).mirrored().cuboid(-1.0F, -0.3F, -1.5F, 2.0F, 5.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 5.9F, 0.2F, -0.2094F, 0.0F, 0.0F));
		rLeg03.addChild("rTalon01", ModelPartBuilder.create().uv(12, 42).mirrored().cuboid(-0.5F, -0.5F, -4.0F, 1.0F, 2.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-0.5F, 3.5F, 0.0F, 0.0349F, 0.3142F, 0.0F));
		rLeg03.addChild("rTalon0", ModelPartBuilder.create().uv(12, 42).mirrored().cuboid(-0.5F, -0.5F, -4.0F, 1.0F, 2.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 3.3F, 0.0F, 0.0349F, 0.0F, 0.0F));
		rLeg03.addChild("rTalon03", ModelPartBuilder.create().uv(12, 42).mirrored().cuboid(-0.5F, -0.5F, -4.0F, 1.0F, 2.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.5F, 3.5F, 0.0F, 0.0349F, -0.3142F, 0.0F));
		ModelPartData head = root.addChild("head", ModelPartBuilder.create().uv(0, 1).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -7.7F, 0.0F));
		head.addChild("lHair", ModelPartBuilder.create().uv(54, 0).mirrored().cuboid(0.0F, 0.0F, -1.5F, 2.0F, 10.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(2.4F, -6.3F, 1.1F, 0.3491F, 0.0F, -0.3142F));
		head.addChild("rHair", ModelPartBuilder.create().uv(54, 0).mirrored().cuboid(-2.0F, 0.0F, -1.5F, 2.0F, 10.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-2.4F, -6.3F, 1.1F, 0.3491F, 0.0F, 0.3142F));
		ModelPartData lHorn01 = head.addChild("lHorn01", ModelPartBuilder.create().uv(109, 0).mirrored().cuboid(-1.5F, -2.0F, -1.5F, 3.0F, 2.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(2.9F, -6.9F, -1.3F, 0.0F, 0.0F, 0.7854F));
		ModelPartData lHorn02 = lHorn01.addChild("lHorn02", ModelPartBuilder.create().uv(109, 5).mirrored().cuboid(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, -1.1F, 0.0F, 0.0F, 0.0F, -0.2618F));
		ModelPartData lHorn03 = lHorn02.addChild("lHorn03", ModelPartBuilder.create().uv(118, 5).mirrored().cuboid(-1.0F, -2.0F, -0.99F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, -2.5F, 0.0F, 0.0F, 0.0F, -0.4189F));
		ModelPartData lHorn04a = lHorn03.addChild("lHorn04a", ModelPartBuilder.create().uv(0, 4).mirrored().cuboid(-0.4F, -3.0F, -0.7F, 1.0F, 3.0F, 1.0F, new Dilation(0.15F)).mirrored(false), ModelTransform.of(0.2F, -1.5F, 0.0F, -0.1047F, 0.0F, -0.4014F));
		lHorn04a.addChild("lHorn05", ModelPartBuilder.create().uv(0, 0).mirrored().cuboid(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, -2.8F, 0.0F, 0.0524F, 0.0F, 0.2269F));
		ModelPartData rHorn01 = head.addChild("rHorn01", ModelPartBuilder.create().uv(109, 0).mirrored().cuboid(-1.5F, -2.0F, -1.5F, 3.0F, 2.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-2.9F, -6.9F, -1.3F, 0.0F, 0.0F, -0.7854F));
		ModelPartData rHorn02 = rHorn01.addChild("rHorn02", ModelPartBuilder.create().uv(109, 5).mirrored().cuboid(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, -1.1F, 0.0F, 0.0F, 0.0F, 0.2618F));
		ModelPartData rHorn03 = rHorn02.addChild("rHorn03", ModelPartBuilder.create().uv(118, 5).mirrored().cuboid(-1.0F, -2.0F, -0.99F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, -2.5F, 0.0F, 0.0F, 0.0F, 0.4189F));
		ModelPartData rHorn04a = rHorn03.addChild("rHorn04a", ModelPartBuilder.create().uv(0, 4).mirrored().cuboid(-0.6F, -3.0F, -0.7F, 1.0F, 3.0F, 1.0F, new Dilation(0.15F)).mirrored(false), ModelTransform.of(-0.2F, -1.5F, 0.0F, -0.1047F, 0.0F, 0.4014F));
		rHorn04a.addChild("rHorn05", ModelPartBuilder.create().uv(0, 0).mirrored().cuboid(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, -2.8F, 0.0F, 0.0524F, 0.0F, -0.2269F));
		ModelPartData HAIR = head.addChild("HAIR", ModelPartBuilder.create().uv(32, 0).cuboid(-4.0F, 6.0F, 0.25F, 8.0F, 11.0F, 1.0F, new Dilation(0.1F)), ModelTransform.pivot(0.0F, -8.3F, 3.0F));
		HAIR.addChild("Base_r1", ModelPartBuilder.create().uv(104, 25).cuboid(-5.0F, -2.0F, -1.25F, 6.0F, 10.0F, 6.0F, new Dilation(0.1F)), ModelTransform.of(0.0F, 3.0F, -4.25F, 0.6109F, 0.7854F, 0.4363F));

		return TexturedModelData.of(data, 128, 64);
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

}