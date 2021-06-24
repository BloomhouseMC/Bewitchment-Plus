// Made with Blockbench 3.7.5
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports

package net.bewitchmentplus.client.model.entity.living;

import net.bewitchmentplus.common.entity.living.DrudenEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;

public class DrudenEntityModel<T extends DrudenEntity> extends BipedEntityModel<T> implements ModelWithArms, ModelWithHead {
	private final ModelPart body;
	private final ModelPart boobs;
	private final ModelPart stomach;
	private final ModelPart bSkirt01;
	private final ModelPart bSkirt02;
	private final ModelPart lSkirt;
	private final ModelPart rSkirt;
	private final ModelPart bipedLeftArm;
	private final ModelPart lArmLeaves01;
	private final ModelPart lArmLeaves02;
	private final ModelPart lClaw01;
	private final ModelPart lClaw02;
	private final ModelPart lClaw03;
	private final ModelPart bipedRightArm;
	private final ModelPart rArmLeaves01;
	private final ModelPart rArmLeaves02;
	private final ModelPart rClaw01;
	private final ModelPart rClaw02;
	private final ModelPart rClaw03;
	private final ModelPart bipedLeftLeg;
	private final ModelPart lLeg02;
	private final ModelPart lLeg03;
	private final ModelPart lHoofClaw01a;
	private final ModelPart lHoofClaw01b;
	private final ModelPart lHoofClaw02a;
	private final ModelPart lHoofClaw02b;
	private final ModelPart bipedRightLeg;
	private final ModelPart rLeg02;
	private final ModelPart rLeg03;
	private final ModelPart rHoofClaw01a;
	private final ModelPart rHoofClaw01b;
	private final ModelPart rHoofClaw02a;
	private final ModelPart rHoofClaw02b;
	private final ModelPart head;
	private final ModelPart lAntler01;
	private final ModelPart lAntler02;
	private final ModelPart Box_r1;
	private final ModelPart rightAntler02;
	private final ModelPart lAntler03a;
	private final ModelPart lAntler03b;
	private final ModelPart lAntler03c;
	private final ModelPart lAntler04a;
	private final ModelPart lAntler04b;
	private final ModelPart lAntler05a;
	private final ModelPart lAntler05b;
	private final ModelPart lAntler06a;
	private final ModelPart lAntler06b;
	private final ModelPart lAntler07a;
	private final ModelPart lAntler07b;
	private final ModelPart rAntler01;
	private final ModelPart rAntler02;
	private final ModelPart Box_r2;
	private final ModelPart rAntler03a;
	private final ModelPart rAntler03b;
	private final ModelPart rAntler03c;
	private final ModelPart rAntler03d;
	private final ModelPart rAntler04a;
	private final ModelPart rAntler04b;
	private final ModelPart rAntler05a;
	private final ModelPart rAntler05b;
	private final ModelPart rAntler06a;
	private final ModelPart rAntler06b;
	private final ModelPart rAntler07a;
	private final ModelPart rAntler07b;
	private final ModelPart hood;
	private final ModelPart HAIR;
	private final ModelPart Base_r1;

	private boolean realArm = false;

	public DrudenEntityModel() {
		super(1, 0, 128, 64);
		textureWidth = 128;
		textureHeight = 64;
		body = new ModelPart(this);
		body.setPivot(0.0F, -9.7F, 0.0F);
		body.setTextureOffset(19, 17).addCuboid(-4.0F, 0.0F, -2.0F, 8.0F, 6.0F, 4.0F, 0.0F, false);

		boobs = new ModelPart(this);
		boobs.setPivot(0.0F, 1.9F, -0.3F);
		body.addChild(boobs);
		setRotation(boobs, -0.5585F, 0.0F, 0.0F);
		boobs.setTextureOffset(0, 57).addCuboid(-3.5F, 0.0F, -2.0F, 7.0F, 4.0F, 3.0F, 0.0F, false);

		stomach = new ModelPart(this);
		stomach.setPivot(0.0F, 6.0F, 0.0F);
		body.addChild(stomach);
		stomach.setTextureOffset(19, 27).addCuboid(-3.5F, 0.0F, -2.0F, 7.0F, 8.0F, 4.0F, 0.0F, false);

		bSkirt01 = new ModelPart(this);
		bSkirt01.setPivot(0.0F, 6.1F, 0.0F);
		stomach.addChild(bSkirt01);
		setRotation(bSkirt01, 0.4189F, 0.0F, 0.0F);
		bSkirt01.setTextureOffset(62, 22).addCuboid(-4.5F, 0.0F, 0.0F, 9.0F, 4.0F, 2.0F, 0.0F, false);

		bSkirt02 = new ModelPart(this);
		bSkirt02.setPivot(0.0F, 3.1F, 0.2F);
		bSkirt01.addChild(bSkirt02);
		setRotation(bSkirt02, -0.4189F, 0.0F, 0.0F);
		bSkirt02.setTextureOffset(62, 22).addCuboid(-4.5F, 0.0F, 0.0F, 9.0F, 11.0F, 2.0F, 0.0F, false);

		lSkirt = new ModelPart(this);
		lSkirt.setPivot(2.2F, 5.8F, 0.0F);
		stomach.addChild(lSkirt);
		setRotation(lSkirt, -0.0698F, 0.0F, -0.0873F);
		lSkirt.setTextureOffset(88, 22).addCuboid(0.0F, 0.0F, -2.5F, 2.0F, 14.0F, 5.0F, 0.0F, false);

		rSkirt = new ModelPart(this);
		rSkirt.setPivot(-2.2F, 5.8F, 0.0F);
		stomach.addChild(rSkirt);
		setRotation(rSkirt, -0.0698F, 0.0F, 0.0873F);
		rSkirt.setTextureOffset(88, 22).addCuboid(-1.9F, 0.0F, -2.5F, 2.0F, 14.0F, 5.0F, 0.0F, true);

		bipedLeftArm = new ModelPart(this);
		bipedLeftArm.setPivot(5.0F, -7.7F, 0.0F);
		setRotation(bipedLeftArm, 0.0F, 0.0F, -0.1F);
		bipedLeftArm.setTextureOffset(44, 14).addCuboid(-1.0F, -2.0F, -2.0F, 3.0F, 14.0F, 4.0F, 0.0F, false);

		lArmLeaves01 = new ModelPart(this);
		lArmLeaves01.setPivot(0.5F, 5.3F, 0.0F);
		bipedLeftArm.addChild(lArmLeaves01);
		lArmLeaves01.setTextureOffset(33, 39).addCuboid(-2.0F, 0.0F, -2.5F, 4.0F, 6.0F, 5.0F, 0.0F, false);

		lArmLeaves02 = new ModelPart(this);
		lArmLeaves02.setPivot(0.0F, 0.0F, 0.0F);
		lArmLeaves01.addChild(lArmLeaves02);
		lArmLeaves02.setTextureOffset(13, 39).addCuboid(-2.0F, -6.0F, -2.5F, 4.0F, 6.0F, 5.0F, 0.0F, false);

		lClaw01 = new ModelPart(this);
		lClaw01.setPivot(0.6F, 11.2F, -1.5F);
		bipedLeftArm.addChild(lClaw01);
		setRotation(lClaw01, -0.1047F, 0.0F, 0.2269F);
		lClaw01.setTextureOffset(0, 0).addCuboid(-0.2F, -1.5F, -0.5F, 2.0F, 5.0F, 1.0F, 0.0F, false);

		lClaw02 = new ModelPart(this);
		lClaw02.setPivot(0.6F, 11.2F, 0.0F);
		bipedLeftArm.addChild(lClaw02);
		setRotation(lClaw02, 0.0F, 0.0F, 0.2269F);
		lClaw02.setTextureOffset(0, 0).addCuboid(-0.2F, -1.2F, -0.5F, 2.0F, 5.0F, 1.0F, 0.0F, false);

		lClaw03 = new ModelPart(this);
		lClaw03.setPivot(0.6F, 11.2F, 1.5F);
		bipedLeftArm.addChild(lClaw03);
		setRotation(lClaw03, 0.1047F, 0.0F, 0.2269F);
		lClaw03.setTextureOffset(0, 0).addCuboid(-0.2F, -1.5F, -0.5F, 2.0F, 5.0F, 1.0F, 0.0F, false);

		bipedRightArm = new ModelPart(this);
		bipedRightArm.setPivot(-5.0F, -7.7F, 0.0F);
		setRotation(bipedRightArm, 0.0F, 0.0F, 0.1F);
		bipedRightArm.setTextureOffset(44, 14).addCuboid(-2.0F, -2.0F, -2.0F, 3.0F, 14.0F, 4.0F, 0.0F, true);

		rArmLeaves01 = new ModelPart(this);
		rArmLeaves01.setPivot(-0.5F, 5.3F, 0.0F);
		bipedRightArm.addChild(rArmLeaves01);
		rArmLeaves01.setTextureOffset(33, 39).addCuboid(-2.0F, 0.0F, -2.5F, 4.0F, 6.0F, 5.0F, 0.0F, true);

		rArmLeaves02 = new ModelPart(this);
		rArmLeaves02.setPivot(0.0F, 0.0F, 0.0F);
		rArmLeaves01.addChild(rArmLeaves02);
		rArmLeaves02.setTextureOffset(13, 39).addCuboid(-2.0F, -6.0F, -2.5F, 4.0F, 6.0F, 5.0F, 0.0F, true);

		rClaw01 = new ModelPart(this);
		rClaw01.setPivot(-0.6F, 11.2F, -1.5F);
		bipedRightArm.addChild(rClaw01);
		setRotation(rClaw01, -0.1047F, 0.0F, -0.2269F);
		rClaw01.setTextureOffset(0, 0).addCuboid(-1.8F, -1.5F, -0.5F, 2.0F, 5.0F, 1.0F, 0.0F, true);

		rClaw02 = new ModelPart(this);
		rClaw02.setPivot(-0.6F, 11.2F, 0.0F);
		bipedRightArm.addChild(rClaw02);
		setRotation(rClaw02, 0.0F, 0.0F, -0.2269F);
		rClaw02.setTextureOffset(0, 0).addCuboid(-1.8F, -1.2F, -0.5F, 2.0F, 5.0F, 1.0F, 0.0F, true);

		rClaw03 = new ModelPart(this);
		rClaw03.setPivot(-0.6F, 11.2F, 1.5F);
		bipedRightArm.addChild(rClaw03);
		setRotation(rClaw03, 0.1047F, 0.0F, -0.2269F);
		rClaw03.setTextureOffset(0, 0).addCuboid(-1.8F, -1.5F, -0.5F, 2.0F, 5.0F, 1.0F, 0.0F, true);

		bipedLeftLeg = new ModelPart(this);
		bipedLeftLeg.setPivot(2.1F, 4.3F, 0.1F);
		setRotation(bipedLeftLeg, -0.3491F, 0.0F, -0.1047F);
		bipedLeftLeg.setTextureOffset(0, 18).addCuboid(-2.0F, -1.0F, -2.5F, 4.0F, 9.0F, 5.0F, 0.0F, false);

		lLeg02 = new ModelPart(this);
		lLeg02.setPivot(0.0F, 5.7F, -0.4F);
		bipedLeftLeg.addChild(lLeg02);
		setRotation(lLeg02, 0.7418F, 0.0F, 0.1047F);
		lLeg02.setTextureOffset(0, 31).addCuboid(-1.5F, 0.0F, -2.7F, 3.0F, 7.0F, 4.0F, 0.0F, false);

		lLeg03 = new ModelPart(this);
		lLeg03.setPivot(0.0F, 5.2F, 0.2F);
		lLeg02.addChild(lLeg03);
		setRotation(lLeg03, -0.4189F, 0.0F, 0.0F);
		lLeg03.setTextureOffset(0, 42).addCuboid(-1.0F, 1.0F, -1.5F, 2.0F, 9.0F, 3.0F, 0.0F, false);

		lHoofClaw01a = new ModelPart(this);
		lHoofClaw01a.setPivot(0.5F, 7.4F, -1.3F);
		lLeg03.addChild(lHoofClaw01a);
		setRotation(lHoofClaw01a, 0.1745F, -0.1396F, -0.0349F);
		lHoofClaw01a.setTextureOffset(20, 57).addCuboid(-0.5F, 1.5F, -2.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);

		lHoofClaw01b = new ModelPart(this);
		lHoofClaw01b.setPivot(0.0F, 0.0F, -1.0F);
		lHoofClaw01a.addChild(lHoofClaw01b);
		setRotation(lHoofClaw01b, 0.3491F, 0.0F, 0.0F);
		lHoofClaw01b.setTextureOffset(27, 56).addCuboid(-0.49F, 0.9F, -1.6F, 1.0F, 1.0F, 3.0F, 0.0F, false);

		lHoofClaw02a = new ModelPart(this);
		lHoofClaw02a.setPivot(-0.5F, 7.4F, -1.3F);
		lLeg03.addChild(lHoofClaw02a);
		setRotation(lHoofClaw02a, 0.1745F, 0.0873F, 0.0349F);
		lHoofClaw02a.setTextureOffset(20, 57).addCuboid(-0.5F, 1.5F, -2.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);

		lHoofClaw02b = new ModelPart(this);
		lHoofClaw02b.setPivot(0.0F, 0.0F, -1.0F);
		lHoofClaw02a.addChild(lHoofClaw02b);
		setRotation(lHoofClaw02b, 0.3491F, 0.0F, 0.0F);
		lHoofClaw02b.setTextureOffset(27, 56).addCuboid(-0.49F, 0.9F, -1.6F, 1.0F, 1.0F, 3.0F, 0.0F, false);

		bipedRightLeg = new ModelPart(this);
		bipedRightLeg.setPivot(-2.1F, 4.3F, 0.1F);
		setRotation(bipedRightLeg, -0.3491F, 0.0F, 0.1047F);
		bipedRightLeg.setTextureOffset(0, 18).addCuboid(-2.0F, -1.0F, -2.5F, 4.0F, 9.0F, 5.0F, 0.0F, true);

		rLeg02 = new ModelPart(this);
		rLeg02.setPivot(0.0F, 5.7F, -0.4F);
		bipedRightLeg.addChild(rLeg02);
		setRotation(rLeg02, 0.7418F, 0.0F, -0.1047F);
		rLeg02.setTextureOffset(0, 31).addCuboid(-1.5F, 0.0F, -2.7F, 3.0F, 7.0F, 4.0F, 0.0F, true);

		rLeg03 = new ModelPart(this);
		rLeg03.setPivot(0.0F, 5.2F, 0.2F);
		rLeg02.addChild(rLeg03);
		setRotation(rLeg03, -0.4189F, 0.0F, 0.0F);
		rLeg03.setTextureOffset(0, 42).addCuboid(-1.0F, 1.0F, -1.5F, 2.0F, 9.0F, 3.0F, 0.0F, true);

		rHoofClaw01a = new ModelPart(this);
		rHoofClaw01a.setPivot(-0.5F, 7.4F, -1.3F);
		rLeg03.addChild(rHoofClaw01a);
		setRotation(rHoofClaw01a, 0.1745F, 0.1396F, 0.0349F);
		rHoofClaw01a.setTextureOffset(20, 57).addCuboid(-0.5F, 1.5F, -2.0F, 1.0F, 1.0F, 2.0F, 0.0F, true);

		rHoofClaw01b = new ModelPart(this);
		rHoofClaw01b.setPivot(0.0F, 0.0F, -1.0F);
		rHoofClaw01a.addChild(rHoofClaw01b);
		setRotation(rHoofClaw01b, 0.3491F, 0.0F, 0.0F);
		rHoofClaw01b.setTextureOffset(27, 56).addCuboid(-0.49F, 0.9F, -1.6F, 1.0F, 1.0F, 3.0F, 0.0F, true);

		rHoofClaw02a = new ModelPart(this);
		rHoofClaw02a.setPivot(0.5F, 7.4F, -1.3F);
		rLeg03.addChild(rHoofClaw02a);
		setRotation(rHoofClaw02a, 0.1745F, -0.0873F, -0.0349F);
		rHoofClaw02a.setTextureOffset(20, 57).addCuboid(-0.5F, 1.5F, -2.0F, 1.0F, 1.0F, 2.0F, 0.0F, true);

		rHoofClaw02b = new ModelPart(this);
		rHoofClaw02b.setPivot(0.0F, 0.0F, -1.0F);
		rHoofClaw02a.addChild(rHoofClaw02b);
		setRotation(rHoofClaw02b, 0.3491F, 0.0F, 0.0F);
		rHoofClaw02b.setTextureOffset(27, 56).addCuboid(-0.49F, 0.9F, -1.6F, 1.0F, 1.0F, 3.0F, 0.0F, true);

		head = new ModelPart(this);
		head.setPivot(0.0F, -9.7F, 0.0F);
		head.setTextureOffset(0, 1).addCuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);

		lAntler01 = new ModelPart(this);
		lAntler01.setPivot(1.9F, -7.1F, -0.5F);
		head.addChild(lAntler01);
		setRotation(lAntler01, -0.2094F, 0.0F, 0.3491F);
		lAntler01.setTextureOffset(49, 34).addCuboid(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, true);

		lAntler02 = new ModelPart(this);
		lAntler02.setPivot(0.0F, -1.6F, 0.0F);
		lAntler01.addChild(lAntler02);
		setRotation(lAntler02, -0.2094F, 0.0F, 0.6109F);


		Box_r1 = new ModelPart(this);
		Box_r1.setPivot(0.1F, -0.6F, 0.5F);
		lAntler02.addChild(Box_r1);
		setRotation(Box_r1, -0.0873F, 0.0F, -0.0436F);
		Box_r1.setTextureOffset(49, 34).addCuboid(-0.6F, -2.9F, -1.0F, 1.0F, 4.0F, 1.0F, 0.2F, true);

		rightAntler02 = new ModelPart(this);
		rightAntler02.setPivot(0.0F, -3.1F, 0.0F);
		lAntler02.addChild(rightAntler02);
		setRotation(rightAntler02, -0.6981F, 0.0F, -0.3142F);
		rightAntler02.setTextureOffset(49, 34).addCuboid(-0.5F, -4.0F, -0.5F, 1.0F, 4.0F, 1.0F, 0.0F, true);

		lAntler03a = new ModelPart(this);
		lAntler03a.setPivot(0.0F, -3.7F, 0.0F);
		rightAntler02.addChild(lAntler03a);
		setRotation(lAntler03a, -0.4538F, 0.0F, -0.0524F);
		lAntler03a.setTextureOffset(49, 34).addCuboid(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F, 0.0F, true);

		lAntler03b = new ModelPart(this);
		lAntler03b.setPivot(0.0F, -2.8F, 0.0F);
		lAntler03a.addChild(lAntler03b);
		setRotation(lAntler03b, -0.1047F, 0.0F, -0.2793F);
		lAntler03b.setTextureOffset(49, 34).addCuboid(-0.5F, -3.9F, -0.5F, 1.0F, 4.0F, 1.0F, 0.0F, true);

		lAntler03c = new ModelPart(this);
		lAntler03c.setPivot(0.0F, -3.7F, 0.0F);
		lAntler03b.addChild(lAntler03c);
		setRotation(lAntler03c, 0.0F, 0.0F, -0.4363F);
		lAntler03c.setTextureOffset(49, 34).addCuboid(-0.5F, -3.0F, -0.4F, 1.0F, 3.0F, 1.0F, 0.0F, true);

		lAntler04a = new ModelPart(this);
		lAntler04a.setPivot(-0.2F, -5.9F, 1.0F);
		rightAntler02.addChild(lAntler04a);
		setRotation(lAntler04a, 0.4538F, 0.0F, -0.8029F);
		lAntler04a.setTextureOffset(49, 34).addCuboid(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F, 0.0F, true);

		lAntler04b = new ModelPart(this);
		lAntler04b.setPivot(0.0F, -2.7F, 0.0F);
		lAntler04a.addChild(lAntler04b);
		setRotation(lAntler04b, 0.0F, 0.0F, -0.3142F);
		lAntler04b.setTextureOffset(49, 34).addCuboid(-0.5F, -2.0F, -0.4F, 1.0F, 2.0F, 1.0F, 0.0F, true);

		lAntler05a = new ModelPart(this);
		lAntler05a.setPivot(-0.3F, -6.7F, 1.4F);
		rightAntler02.addChild(lAntler05a);
		setRotation(lAntler05a, -0.0349F, 0.0F, -0.5236F);
		lAntler05a.setTextureOffset(49, 34).addCuboid(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F, 0.0F, true);

		lAntler05b = new ModelPart(this);
		lAntler05b.setPivot(0.0F, -2.7F, 0.0F);
		lAntler05a.addChild(lAntler05b);
		setRotation(lAntler05b, 0.0F, 0.0F, -0.5236F);
		lAntler05b.setTextureOffset(49, 34).addCuboid(-0.5F, -2.0F, -0.4F, 1.0F, 2.0F, 1.0F, 0.0F, true);

		lAntler06a = new ModelPart(this);
		lAntler06a.setPivot(0.0F, -2.5F, -0.4F);
		lAntler02.addChild(lAntler06a);
		setRotation(lAntler06a, -1.3963F, 0.6981F, -0.4363F);
		lAntler06a.setTextureOffset(49, 34).addCuboid(-0.5F, -0.5F, -3.8F, 1.0F, 1.0F, 4.0F, 0.0F, true);

		lAntler06b = new ModelPart(this);
		lAntler06b.setPivot(0.1F, 0.0F, -3.4F);
		lAntler06a.addChild(lAntler06b);
		setRotation(lAntler06b, 0.0F, 0.576F, 0.0F);
		lAntler06b.setTextureOffset(49, 34).addCuboid(-0.5F, -0.6F, -3.0F, 1.0F, 1.0F, 3.0F, 0.0F, true);

		lAntler07a = new ModelPart(this);
		lAntler07a.setPivot(0.0F, -2.0F, -0.4F);
		lAntler01.addChild(lAntler07a);
		setRotation(lAntler07a, -0.9076F, 0.5236F, 0.0F);
		lAntler07a.setTextureOffset(49, 34).addCuboid(-0.5F, -0.5F, -3.8F, 1.0F, 1.0F, 4.0F, 0.0F, true);

		lAntler07b = new ModelPart(this);
		lAntler07b.setPivot(0.1F, 0.0F, -3.4F);
		lAntler07a.addChild(lAntler07b);
		setRotation(lAntler07b, 0.0F, 0.576F, 0.0F);
		lAntler07b.setTextureOffset(49, 34).addCuboid(-0.5F, -0.5F, -3.0F, 1.0F, 1.0F, 3.0F, 0.0F, true);

		rAntler01 = new ModelPart(this);
		rAntler01.setPivot(-1.9F, -7.1F, -0.5F);
		head.addChild(rAntler01);
		setRotation(rAntler01, -0.2094F, 0.0F, -0.3491F);
		rAntler01.setTextureOffset(49, 34).addCuboid(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);

		rAntler02 = new ModelPart(this);
		rAntler02.setPivot(0.0F, -1.6F, 0.0F);
		rAntler01.addChild(rAntler02);
		setRotation(rAntler02, -0.2094F, 0.0F, -0.6109F);


		Box_r2 = new ModelPart(this);
		Box_r2.setPivot(-0.1F, -0.6F, 0.5F);
		rAntler02.addChild(Box_r2);
		setRotation(Box_r2, -0.0873F, 0.0F, 0.0436F);
		Box_r2.setTextureOffset(49, 34).addCuboid(-0.4F, -2.9F, -1.0F, 1.0F, 4.0F, 1.0F, 0.2F, false);

		rAntler03a = new ModelPart(this);
		rAntler03a.setPivot(0.0F, -3.1F, 0.0F);
		rAntler02.addChild(rAntler03a);
		setRotation(rAntler03a, -0.6981F, 0.0F, 0.3142F);
		rAntler03a.setTextureOffset(49, 34).addCuboid(-0.5F, -4.0F, -0.5F, 1.0F, 4.0F, 1.0F, 0.0F, false);

		rAntler03b = new ModelPart(this);
		rAntler03b.setPivot(0.0F, -3.7F, 0.0F);
		rAntler03a.addChild(rAntler03b);
		setRotation(rAntler03b, -0.4538F, 0.0F, 0.0524F);
		rAntler03b.setTextureOffset(49, 34).addCuboid(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F, 0.0F, false);

		rAntler03c = new ModelPart(this);
		rAntler03c.setPivot(0.0F, -2.8F, 0.0F);
		rAntler03b.addChild(rAntler03c);
		setRotation(rAntler03c, -0.1047F, 0.0F, 0.2793F);
		rAntler03c.setTextureOffset(49, 34).addCuboid(-0.5F, -3.9F, -0.5F, 1.0F, 4.0F, 1.0F, 0.0F, false);

		rAntler03d = new ModelPart(this);
		rAntler03d.setPivot(0.0F, -3.7F, 0.0F);
		rAntler03c.addChild(rAntler03d);
		setRotation(rAntler03d, 0.0F, 0.0F, 0.4363F);
		rAntler03d.setTextureOffset(49, 34).addCuboid(-0.5F, -3.0F, -0.4F, 1.0F, 3.0F, 1.0F, 0.0F, false);

		rAntler04a = new ModelPart(this);
		rAntler04a.setPivot(0.2F, -5.9F, 1.0F);
		rAntler03a.addChild(rAntler04a);
		setRotation(rAntler04a, 0.4538F, 0.0F, 0.8029F);
		rAntler04a.setTextureOffset(49, 34).addCuboid(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F, 0.0F, false);

		rAntler04b = new ModelPart(this);
		rAntler04b.setPivot(0.0F, -2.7F, 0.0F);
		rAntler04a.addChild(rAntler04b);
		setRotation(rAntler04b, 0.0F, 0.0F, 0.3142F);
		rAntler04b.setTextureOffset(49, 34).addCuboid(-0.5F, -2.0F, -0.4F, 1.0F, 2.0F, 1.0F, 0.0F, false);

		rAntler05a = new ModelPart(this);
		rAntler05a.setPivot(0.3F, -6.7F, 1.4F);
		rAntler03a.addChild(rAntler05a);
		setRotation(rAntler05a, -0.0349F, 0.0F, 0.5236F);
		rAntler05a.setTextureOffset(49, 34).addCuboid(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F, 0.0F, false);

		rAntler05b = new ModelPart(this);
		rAntler05b.setPivot(0.0F, -2.7F, 0.0F);
		rAntler05a.addChild(rAntler05b);
		setRotation(rAntler05b, 0.0F, 0.0F, 0.5236F);
		rAntler05b.setTextureOffset(49, 34).addCuboid(-0.5F, -2.0F, -0.4F, 1.0F, 2.0F, 1.0F, 0.0F, false);

		rAntler06a = new ModelPart(this);
		rAntler06a.setPivot(0.0F, -2.5F, -0.4F);
		rAntler02.addChild(rAntler06a);
		setRotation(rAntler06a, -1.3963F, -0.6981F, 0.4363F);
		rAntler06a.setTextureOffset(49, 34).addCuboid(-0.5F, -0.5F, -3.8F, 1.0F, 1.0F, 4.0F, 0.0F, false);

		rAntler06b = new ModelPart(this);
		rAntler06b.setPivot(-0.1F, 0.0F, -3.4F);
		rAntler06a.addChild(rAntler06b);
		setRotation(rAntler06b, 0.0F, -0.576F, 0.0F);
		rAntler06b.setTextureOffset(49, 34).addCuboid(-0.5F, -0.6F, -3.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);

		rAntler07a = new ModelPart(this);
		rAntler07a.setPivot(0.0F, -2.0F, -0.4F);
		rAntler01.addChild(rAntler07a);
		setRotation(rAntler07a, -0.9076F, -0.5236F, 0.0F);
		rAntler07a.setTextureOffset(49, 34).addCuboid(-0.5F, -0.5F, -3.8F, 1.0F, 1.0F, 4.0F, 0.0F, false);

		rAntler07b = new ModelPart(this);
		rAntler07b.setPivot(-0.1F, 0.0F, -3.4F);
		rAntler07a.addChild(rAntler07b);
		setRotation(rAntler07b, 0.0F, -0.576F, 0.0F);
		rAntler07b.setTextureOffset(49, 34).addCuboid(-0.5F, -0.5F, -3.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);

		hood = new ModelPart(this);
		hood.setPivot(0.0F, 0.0F, 0.0F);
		head.addChild(hood);
		hood.setTextureOffset(69, 0).addCuboid(-4.5F, -8.5F, -4.4F, 9.0F, 11.0F, 9.0F, 0.1F, false);

		HAIR = new ModelPart(this);
		HAIR.setPivot(0.0F, -7.3F, 2.0F);
		head.addChild(HAIR);
		HAIR.setTextureOffset(46, 1).addCuboid(-4.0F, 5.0F, 1.2F, 8.0F, 11.0F, 1.0F, 0.1F, false);

		Base_r1 = new ModelPart(this);
		Base_r1.setPivot(0.0F, 3.0F, -2.0F);
		HAIR.addChild(Base_r1);
		setRotation(Base_r1, 0.4363F, 0.7854F, 0.3054F);
		Base_r1.setTextureOffset(53, 46).addCuboid(-5.0F, -3.0F, -1.0F, 6.0F, 12.0F, 6.0F, 0.1F, false);
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

	@Override
	public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		realArm = false;
		super.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
		realArm = true;
		copyRotation(head, super.head);
		copyRotation(bipedLeftArm, super.leftArm);
		bipedLeftArm.roll -= 0.1309f;
		copyRotation(bipedRightArm, super.rightArm);
		bipedRightArm.roll += 0.1309f;
		copyRotation(bipedLeftLeg, super.leftLeg);
		bipedLeftLeg.pitch /= 2;
		bipedLeftLeg.pitch -= 0.2793f;
		bipedLeftLeg.roll -= 0.1047f;
		copyRotation(bipedRightLeg, super.rightLeg);
		bipedRightLeg.pitch /= 2;
		bipedRightLeg.pitch -= 0.2793f;
		bipedRightLeg.roll += 0.1047f;

		this.bipedRightArm.pitch = -MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 2.0F * limbDistance * 0.55F;
		this.bipedLeftArm.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 2.0F * limbDistance * 0.55F;
		float j = MathHelper.sin(entity.handSwingProgress * 3.1415927F);
		if (j > 0) {
			this.bipedRightArm.pitch = -j;
			if (entity.getDataTracker().get(DrudenEntity.SPEAR_LUNGE)) {
				this.bipedLeftArm.pitch = -j;
			}
		}
	}

	private void setRotation(ModelPart bone, float x, float y, float z) {
		bone.pitch = x;
		bone.yaw = y;
		bone.roll = z;
	}

	@Override
	public void setArmAngle(Arm arm, MatrixStack matrices) {
		super.setArmAngle(arm, matrices);
		matrices.translate(0, 0.20, 0);
	}

	protected ModelPart getArm(Arm arm) {
		return arm == Arm.LEFT ? this.bipedLeftArm : this.bipedRightArm;
	}

	private void copyRotation(ModelPart to, ModelPart from) {
		to.pitch = from.pitch;
		to.yaw = from.yaw;
		to.roll = from.roll;
	}
}