package net.bewitchmentplus.client.model.entity.living;

import net.bewitchmentplus.common.entity.living.CleaverEntity;
import net.bewitchmentplus.common.entity.living.DrudenEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Arm;

// Made with Blockbench 3.7.5
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports

public class CleaverEntityModel <T extends CleaverEntity> extends BipedEntityModel<T> implements ModelWithArms, ModelWithHead {
	private final ModelPart body;
	private final ModelPart loincloth;
	private final ModelPart loincloth02;
	private final ModelPart loinclothBack;
	private final ModelPart bipedLeftArm;
	private final ModelPart bipedRightArm;
	private final ModelPart bipedLeftLeg;
	private final ModelPart lLeg02;
	private final ModelPart lLeg03;
	private final ModelPart lHoof;
	private final ModelPart bipedRightLeg;
	private final ModelPart rLeg02;
	private final ModelPart rLeg03;
	private final ModelPart rHoof;
	private final ModelPart head;
	private final ModelPart upperJaw;
	private final ModelPart upperTeethL;
	private final ModelPart upperTeethR;
	private final ModelPart snout;
	private final ModelPart lowerJaw;
	private final ModelPart lowerTeethL;
	private final ModelPart lowerTeethR;
	private final ModelPart lowerTeethM;
	private final ModelPart lEar01;
	private final ModelPart lEar02;
	private final ModelPart rEar01;
	private final ModelPart rEar02;
	private final ModelPart lHorn01;
	private final ModelPart lHorn02;
	private final ModelPart lHorn03;
	private final ModelPart lHorn04;
	private final ModelPart rHorn01;
	private final ModelPart rHorn02;
	private final ModelPart rHorn03;
	private final ModelPart rHorn04;

	public CleaverEntityModel() {
		super(1, 0, 64, 64);
		textureWidth = 64;
		textureHeight = 64;
		body = new ModelPart(this);
		body.setPivot(0.0F, -5.7F, 0.0F);
		body.setTextureOffset(16, 16).addCuboid(-4.0F, 0.0F, -2.0F, 8.0F, 13.0F, 4.0F, 0.0F, false);

		loincloth = new ModelPart(this);
		loincloth.setPivot(0.0F, 10.6F, -1.5F);
		body.addChild(loincloth);
		setRotationAngle(loincloth, -0.3491F, 0.0F, 0.0F);
		loincloth.setTextureOffset(48, 34).addCuboid(-3.0F, 0.0F, -0.5F, 6.0F, 8.0F, 1.0F, 0.0F, false);

		loincloth02 = new ModelPart(this);
		loincloth02.setPivot(0.0F, 7.7F, 0.0F);
		loincloth.addChild(loincloth02);
		setRotationAngle(loincloth02, 0.3491F, 0.0F, 0.0F);
		loincloth02.setTextureOffset(48, 43).addCuboid(-3.0F, 0.05F, -0.55F, 6.0F, 6.0F, 1.0F, 0.0F, false);

		loinclothBack = new ModelPart(this);
		loinclothBack.setPivot(0.0F, 10.6F, 1.6F);
		body.addChild(loinclothBack);
		loinclothBack.setTextureOffset(48, 51).addCuboid(-3.5F, 0.05F, -0.25F, 7.0F, 6.0F, 1.0F, 0.0F, false);

		bipedLeftArm = new ModelPart(this);
		bipedLeftArm.setPivot(5.0F, -3.7F, 0.0F);
		setRotationAngle(bipedLeftArm, 0.0F, 0.0F, -0.1F);
		bipedLeftArm.setTextureOffset(40, 16).addCuboid(-1.0F, -2.0F, -2.0F, 4.0F, 14.0F, 4.0F, 0.0F, false);

		bipedRightArm = new ModelPart(this);
		bipedRightArm.setPivot(-5.0F, -3.7F, 0.0F);
		setRotationAngle(bipedRightArm, 0.0F, 0.0F, 0.1F);
		bipedRightArm.setTextureOffset(40, 16).addCuboid(-3.0F, -2.0F, -2.0F, 4.0F, 14.0F, 4.0F, 0.0F, true);

		bipedLeftLeg = new ModelPart(this);
		bipedLeftLeg.setPivot(2.1F, 6.7F, 0.1F);
		setRotationAngle(bipedLeftLeg, -0.3491F, 0.0F, -0.1047F);
		bipedLeftLeg.setTextureOffset(0, 16).addCuboid(-1.8F, -0.9F, -2.0F, 4.0F, 9.0F, 4.0F, 0.0F, true);

		lLeg02 = new ModelPart(this);
		lLeg02.setPivot(0.3F, 6.7F, -0.7F);
		bipedLeftLeg.addChild(lLeg02);
		setRotationAngle(lLeg02, 0.7679F, 0.0F, 0.1047F);
		lLeg02.setTextureOffset(24, 33).addCuboid(-1.5F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F, 0.0F, true);

		lLeg03 = new ModelPart(this);
		lLeg03.setPivot(0.0F, 5.4F, 0.0F);
		lLeg02.addChild(lLeg03);
		setRotationAngle(lLeg03, -0.4363F, 0.0F, 0.0F);
		lLeg03.setTextureOffset(24, 43).addCuboid(-1.49F, -0.4F, -1.5F, 3.0F, 6.0F, 3.0F, 0.0F, true);

		lHoof = new ModelPart(this);
		lHoof.setPivot(0.0F, 5.2F, -0.7F);
		lLeg03.addChild(lHoof);
		lHoof.setTextureOffset(25, 52).addCuboid(-1.5F, -0.7F, -1.9F, 3.0F, 2.0F, 4.0F, 0.0F, true);

		bipedRightLeg = new ModelPart(this);
		bipedRightLeg.setPivot(-2.0F, 6.7F, 0.1F);
		setRotationAngle(bipedRightLeg, -0.3491F, 0.0F, 0.1047F);
		bipedRightLeg.setTextureOffset(0, 16).addCuboid(-2.2F, -0.9F, -2.0F, 4.0F, 9.0F, 4.0F, 0.0F, true);

		rLeg02 = new ModelPart(this);
		rLeg02.setPivot(-0.3F, 6.7F, -0.7F);
		bipedRightLeg.addChild(rLeg02);
		setRotationAngle(rLeg02, 0.7679F, 0.0F, -0.1047F);
		rLeg02.setTextureOffset(24, 33).addCuboid(-1.5F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F, 0.0F, true);

		rLeg03 = new ModelPart(this);
		rLeg03.setPivot(0.0F, 5.4F, 0.0F);
		rLeg02.addChild(rLeg03);
		setRotationAngle(rLeg03, -0.4363F, 0.0F, 0.0F);
		rLeg03.setTextureOffset(24, 43).addCuboid(-1.51F, -0.4F, -1.5F, 3.0F, 6.0F, 3.0F, 0.0F, true);

		rHoof = new ModelPart(this);
		rHoof.setPivot(0.0F, 5.2F, -0.7F);
		rLeg03.addChild(rHoof);
		rHoof.setTextureOffset(25, 52).addCuboid(-1.5F, -0.7F, -1.9F, 3.0F, 2.0F, 4.0F, 0.0F, true);

		head = new ModelPart(this);
		head.setPivot(0.0F, -5.7F, 0.0F);
		head.setTextureOffset(0, 0).addCuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);

		upperJaw = new ModelPart(this);
		upperJaw.setPivot(0.0F, -3.8F, -4.1F);
		head.addChild(upperJaw);
		upperJaw.setTextureOffset(0, 46).addCuboid(-3.0F, -0.65F, -4.7F, 6.0F, 3.0F, 5.0F, 0.0F, false);

		upperTeethL = new ModelPart(this);
		upperTeethL.setPivot(2.0F, 2.2F, -2.8F);
		upperJaw.addChild(upperTeethL);
		upperTeethL.setTextureOffset(52, 58).addCuboid(-1.2F, 0.0F, -1.7F, 2.0F, 2.0F, 4.0F, 0.0F, false);

		upperTeethR = new ModelPart(this);
		upperTeethR.setPivot(-2.0F, 2.2F, -2.8F);
		upperJaw.addChild(upperTeethR);
		upperTeethR.setTextureOffset(52, 58).addCuboid(-0.8F, 0.0F, -1.7F, 2.0F, 2.0F, 4.0F, 0.0F, true);

		snout = new ModelPart(this);
		snout.setPivot(0.0F, -5.0F, -2.3F);
		head.addChild(snout);
		setRotationAngle(snout, 0.3491F, 0.0F, 0.0F);
		snout.setTextureOffset(0, 35).addCuboid(-2.5F, -2.05F, -6.4F, 5.0F, 3.0F, 6.0F, 0.0F, false);

		lowerJaw = new ModelPart(this);
		lowerJaw.setPivot(0.0F, -0.9F, -3.5F);
		head.addChild(lowerJaw);
		setRotationAngle(lowerJaw, -0.0349F, 0.0F, 0.0F);
		lowerJaw.setTextureOffset(0, 55).addCuboid(-2.5F, -0.5F, -5.0F, 5.0F, 1.0F, 6.0F, 0.0F, false);

		lowerTeethL = new ModelPart(this);
		lowerTeethL.setPivot(-2.2F, -0.4F, -3.5F);
		lowerJaw.addChild(lowerTeethL);
		lowerTeethL.setTextureOffset(24, 58).addCuboid(-0.2F, -2.0F, -1.4F, 1.0F, 2.0F, 4.0F, 0.0F, false);

		lowerTeethR = new ModelPart(this);
		lowerTeethR.setPivot(2.2F, -0.4F, -3.5F);
		lowerJaw.addChild(lowerTeethR);
		lowerTeethR.setTextureOffset(24, 58).addCuboid(-0.8F, -2.0F, -1.4F, 1.0F, 2.0F, 4.0F, 0.0F, false);

		lowerTeethM = new ModelPart(this);
		lowerTeethM.setPivot(0.0F, -0.4F, -3.5F);
		lowerJaw.addChild(lowerTeethM);
		lowerTeethM.setTextureOffset(35, 61).addCuboid(-1.5F, -1.1F, -1.4F, 3.0F, 1.0F, 0.0F, 0.0F, false);

		lEar01 = new ModelPart(this);
		lEar01.setPivot(3.4F, -5.5F, 0.0F);
		head.addChild(lEar01);
		setRotationAngle(lEar01, -0.6981F, 0.0873F, 0.3491F);
		lEar01.setTextureOffset(47, 0).addCuboid(0.0F, -0.5F, -1.5F, 5.0F, 1.0F, 3.0F, 0.0F, false);

		lEar02 = new ModelPart(this);
		lEar02.setPivot(0.0F, -0.9F, 0.0F);
		lEar01.addChild(lEar02);
		setRotationAngle(lEar02, 0.0F, 0.0F, 0.2276F);
		lEar02.setTextureOffset(48, 5).addCuboid(0.0F, -0.5F, -1.0F, 4.0F, 1.0F, 2.0F, 0.0F, false);

		rEar01 = new ModelPart(this);
		rEar01.setPivot(-3.4F, -5.5F, 0.0F);
		head.addChild(rEar01);
		setRotationAngle(rEar01, -0.6981F, 0.0873F, -0.3491F);
		rEar01.setTextureOffset(47, 0).addCuboid(-5.0F, -0.5F, -1.5F, 5.0F, 1.0F, 3.0F, 0.0F, true);

		rEar02 = new ModelPart(this);
		rEar02.setPivot(0.0F, -0.9F, 0.0F);
		rEar01.addChild(rEar02);
		setRotationAngle(rEar02, 0.0F, 0.0F, -0.2276F);
		rEar02.setTextureOffset(48, 5).addCuboid(-4.0F, -0.5F, -1.0F, 4.0F, 1.0F, 2.0F, 0.0F, true);

		lHorn01 = new ModelPart(this);
		lHorn01.setPivot(2.8F, -6.8F, 2.0F);
		head.addChild(lHorn01);
		setRotationAngle(lHorn01, 0.0873F, 0.0F, 0.7854F);
		lHorn01.setTextureOffset(37, 37).addCuboid(-1.0F, -2.5F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, true);

		lHorn02 = new ModelPart(this);
		lHorn02.setPivot(0.1F, -1.9F, 0.2F);
		lHorn01.addChild(lHorn02);
		setRotationAngle(lHorn02, 0.2793F, 0.0F, -0.2618F);
		lHorn02.setTextureOffset(39, 38).addCuboid(-0.4F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F, 0.2F, true);

		lHorn03 = new ModelPart(this);
		lHorn03.setPivot(0.0F, -2.7F, 0.0F);
		lHorn02.addChild(lHorn03);
		setRotationAngle(lHorn03, 0.1396F, 0.0F, -0.3491F);
		lHorn03.setTextureOffset(38, 43).addCuboid(-0.3F, -2.9F, -0.5F, 1.0F, 3.0F, 1.0F, 0.0F, true);

		lHorn04 = new ModelPart(this);
		lHorn04.setPivot(0.0F, -2.3F, 0.0F);
		lHorn03.addChild(lHorn04);
		setRotationAngle(lHorn04, 0.1396F, 0.0F, -0.3491F);
		lHorn04.setTextureOffset(43, 43).addCuboid(-0.2F, -2.4F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, true);

		rHorn01 = new ModelPart(this);
		rHorn01.setPivot(-2.8F, -6.8F, 2.0F);
		head.addChild(rHorn01);
		setRotationAngle(rHorn01, 0.0873F, 0.0F, -0.7854F);
		rHorn01.setTextureOffset(37, 37).addCuboid(-1.0F, -2.5F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, true);

		rHorn02 = new ModelPart(this);
		rHorn02.setPivot(-0.1F, -1.9F, 0.2F);
		rHorn01.addChild(rHorn02);
		setRotationAngle(rHorn02, 0.2793F, 0.0F, 0.2618F);
		rHorn02.setTextureOffset(39, 38).addCuboid(-0.6F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F, 0.2F, true);

		rHorn03 = new ModelPart(this);
		rHorn03.setPivot(0.0F, -2.7F, 0.0F);
		rHorn02.addChild(rHorn03);
		setRotationAngle(rHorn03, 0.1396F, 0.0F, 0.3491F);
		rHorn03.setTextureOffset(38, 43).addCuboid(-0.7F, -2.9F, -0.5F, 1.0F, 3.0F, 1.0F, 0.0F, true);

		rHorn04 = new ModelPart(this);
		rHorn04.setPivot(0.0F, -2.3F, 0.0F);
		rHorn03.addChild(rHorn04);
		setRotationAngle(rHorn04, 0.1396F, 0.0F, 0.3491F);
		rHorn04.setTextureOffset(43, 43).addCuboid(-0.9F, -2.4F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, true);
	}

	@Override
	public void render(MatrixStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {

		body.render(matrixStack, buffer, packedLight, packedOverlay);
		bipedLeftArm.render(matrixStack, buffer, packedLight, packedOverlay);
		bipedRightArm.render(matrixStack, buffer, packedLight, packedOverlay);
		bipedLeftLeg.render(matrixStack, buffer, packedLight, packedOverlay);
		bipedRightLeg.render(matrixStack, buffer, packedLight, packedOverlay);
		head.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelPart bone, float x, float y, float z) {
		bone.pitch = x;
		bone.yaw = y;
		bone.roll = z;
	}

	protected ModelPart getArm(Arm arm) {
		return arm == Arm.LEFT ? this.bipedLeftArm : this.bipedRightArm;
	}

	@Override
	public void setArmAngle(Arm arm, MatrixStack matrices) {
		super.setArmAngle(arm, matrices);
		matrices.translate(0, 0.05, 0);
	}

	@Override
	public ModelPart getHead() {
		return head;
	}
}
