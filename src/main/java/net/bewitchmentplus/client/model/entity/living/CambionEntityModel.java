package net.bewitchmentplus.client.model.entity.living;

import net.bewitchmentplus.common.entity.living.CambionEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;

//Credit to Moriyashiine for showing how this is done
@Environment(EnvType.CLIENT)
public class CambionEntityModel<T extends CambionEntity> extends BipedEntityModel<T> implements ModelWithArms, ModelWithHead {
	private final BipedEntityModel<T> male = new CambionEntityModel.Male();
	private final BipedEntityModel<T> female = new CambionEntityModel.Female();
	private final boolean realArm = false;
	private BipedEntityModel<T> model;

	public CambionEntityModel() {
		super(1);
	}

	@Override
	public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		super.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
		model = entity.getDataTracker().get(CambionEntity.MALE) ? this.male : female;
		model.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		model.render(matrices, vertices, light, overlay, red, green, blue, alpha);
	}

	@Override
	protected ModelPart getArm(Arm arm) {
		if (model == male) {
			return ((CambionEntityModel.Male) model).getArm(arm);
		} else if (model == female) {
			return ((CambionEntityModel.Female) model).getArm(arm);
		}
		return super.getArm(arm);
	}

	private void setRotation(ModelPart bone, float x, float y, float z) {
		bone.pitch = x;
		bone.yaw = y;
		bone.roll = z;
	}

	private void copyRotation(ModelPart to, ModelPart from) {
		to.pitch = from.pitch;
		to.yaw = from.yaw;
		to.roll = from.roll;
	}

	private class Male extends BipedEntityModel<T> implements ModelWithArms, ModelWithHead {
		private final ModelPart body;
		private final ModelPart bipedLeftArm;
		private final ModelPart lClaws;
		private final ModelPart bipedRightArm;
		private final ModelPart rClaws;
		private final ModelPart bipedLeftLeg;
		private final ModelPart bipedRightLeg;
		private final ModelPart head;
		private final ModelPart snout;
		private final ModelPart upperJaw;
		private final ModelPart lowerJaw;
		private final ModelPart lUpperHorn01;
		private final ModelPart lUpperHorn02;
		private final ModelPart lUpperHorn03;
		private final ModelPart rUpperHorn01;
		private final ModelPart rUpperHorn02;
		private final ModelPart rUpperHorn03;
		private final ModelPart rHorn01;
		private final ModelPart rHorn02;
		private final ModelPart rHorn03;
		private final ModelPart rHorn04;
		private final ModelPart rHorn05;
		private final ModelPart lHorn01;
		private final ModelPart lHorn02;
		private final ModelPart lHorn03;
		private final ModelPart lHorn04;
		private final ModelPart lHorn05;
		private final ModelPart hair01;
		private final ModelPart hair00;

		private boolean realArm = false;


		public Male() {
			super(1, 0, 64, 64);
			body = new ModelPart(this);
			body.setPivot(0.0F, -2.0F, 0.0F);
			body.setTextureOffset(16, 16).addCuboid(-4.0F, 0.0F, -2.0F, 8.0F, 13.0F, 4.0F, 0.0F, false);

			bipedLeftArm = new ModelPart(this);
			bipedLeftArm.setPivot(5.0F, 0.0F, 0.0F);
			setRotationAngle(bipedLeftArm, 0.0F, 0.0F, -0.1F);
			bipedLeftArm.setTextureOffset(40, 16).addCuboid(-1.0F, -2.0F, -2.0F, 4.0F, 14.0F, 4.0F, 0.0F, false);

			lClaws = new ModelPart(this);
			lClaws.setPivot(2.4F, 11.5F, 0.0F);
			bipedLeftArm.addChild(lClaws);
			setRotationAngle(lClaws, 0.0F, 0.0F, 0.2793F);
			lClaws.setTextureOffset(0, 34).addCuboid(-1.1F, 0.0F, -2.1F, 2.0F, 3.0F, 4.0F, 0.0F, false);

			bipedRightArm = new ModelPart(this);
			bipedRightArm.setPivot(-5.0F, 0.0F, 0.0F);
			setRotationAngle(bipedRightArm, 0.0F, 0.0F, 0.1F);
			bipedRightArm.setTextureOffset(40, 16).addCuboid(-3.0F, -2.0F, -2.0F, 4.0F, 14.0F, 4.0F, 0.0F, true);

			rClaws = new ModelPart(this);
			rClaws.setPivot(-2.4F, 11.5F, 0.0F);
			bipedRightArm.addChild(rClaws);
			setRotationAngle(rClaws, 0.0F, 0.0F, -0.2793F);
			rClaws.setTextureOffset(0, 34).addCuboid(-1.1F, 0.0F, -2.1F, 2.0F, 3.0F, 4.0F, 0.0F, true);

			bipedLeftLeg = new ModelPart(this);
			bipedLeftLeg.setPivot(1.9F, 11.0F, 0.0F);
			bipedLeftLeg.setTextureOffset(0, 16).addCuboid(-2.0F, 0.0F, -2.0F, 4.0F, 13.0F, 4.0F, 0.0F, false);

			bipedRightLeg = new ModelPart(this);
			bipedRightLeg.setPivot(-1.9F, 11.0F, 0.0F);
			bipedRightLeg.setTextureOffset(0, 16).addCuboid(-2.0F, 0.0F, -2.0F, 4.0F, 13.0F, 4.0F, 0.0F, true);

			head = new ModelPart(this);
			head.setPivot(0.0F, -2.0F, 0.0F);
			head.setTextureOffset(0, 0).addCuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);

			snout = new ModelPart(this);
			snout.setPivot(0.0F, -2.5F, -3.9F);
			head.addChild(snout);
			setRotationAngle(snout, 0.4189F, 0.0F, 0.0F);
			snout.setTextureOffset(21, 35).addCuboid(-1.5F, -1.0F, -2.0F, 3.0F, 2.0F, 3.0F, 0.0F, false);

			upperJaw = new ModelPart(this);
			upperJaw.setPivot(0.0F, -1.4F, -4.0F);
			head.addChild(upperJaw);
			upperJaw.setTextureOffset(21, 41).addCuboid(-2.0F, -1.0F, -1.7F, 4.0F, 2.0F, 2.0F, 0.0F, false);

			lowerJaw = new ModelPart(this);
			lowerJaw.setPivot(0.0F, -0.4F, -4.0F);
			head.addChild(lowerJaw);
			lowerJaw.setTextureOffset(21, 46).addCuboid(-1.5F, -0.5F, -1.6F, 3.0F, 1.0F, 2.0F, 0.0F, false);

			lUpperHorn01 = new ModelPart(this);
			lUpperHorn01.setPivot(2.0F, -8.7F, -0.5F);
			head.addChild(lUpperHorn01);
			setRotationAngle(lUpperHorn01, 0.8727F, 0.1745F, 0.0F);
			lUpperHorn01.setTextureOffset(24, 0).addCuboid(-1.0F, -1.0F, -1.8F, 2.0F, 2.0F, 3.0F, 0.0F, true);

			lUpperHorn02 = new ModelPart(this);
			lUpperHorn02.setPivot(0.0F, 0.0F, 0.9F);
			lUpperHorn01.addChild(lUpperHorn02);
			setRotationAngle(lUpperHorn02, -0.3142F, 0.2618F, 0.0F);
			lUpperHorn02.setTextureOffset(33, 7).addCuboid(-0.5F, -0.6F, -0.5F, 1.0F, 1.0F, 3.0F, 0.2F, true);

			lUpperHorn03 = new ModelPart(this);
			lUpperHorn03.setPivot(0.0F, 0.0F, 2.4F);
			lUpperHorn02.addChild(lUpperHorn03);
			setRotationAngle(lUpperHorn03, -0.1745F, 0.1745F, 0.0F);
			lUpperHorn03.setTextureOffset(42, 7).addCuboid(-0.5F, -0.5F, -0.4F, 1.0F, 1.0F, 3.0F, 0.0F, true);

			rUpperHorn01 = new ModelPart(this);
			rUpperHorn01.setPivot(-2.0F, -8.7F, -0.5F);
			head.addChild(rUpperHorn01);
			setRotationAngle(rUpperHorn01, 0.8727F, -0.1745F, 0.0F);
			rUpperHorn01.setTextureOffset(24, 0).addCuboid(-1.0F, -1.0F, -1.8F, 2.0F, 2.0F, 3.0F, 0.0F, true);

			rUpperHorn02 = new ModelPart(this);
			rUpperHorn02.setPivot(0.0F, 0.0F, 0.9F);
			rUpperHorn01.addChild(rUpperHorn02);
			setRotationAngle(rUpperHorn02, -0.3142F, -0.2618F, 0.0F);
			rUpperHorn02.setTextureOffset(33, 7).addCuboid(-0.5F, -0.6F, -0.5F, 1.0F, 1.0F, 3.0F, 0.2F, true);

			rUpperHorn03 = new ModelPart(this);
			rUpperHorn03.setPivot(0.0F, 0.0F, 2.4F);
			rUpperHorn02.addChild(rUpperHorn03);
			setRotationAngle(rUpperHorn03, -0.1745F, -0.1745F, 0.0F);
			rUpperHorn03.setTextureOffset(42, 7).addCuboid(-0.5F, -0.5F, -0.4F, 1.0F, 1.0F, 3.0F, 0.0F, true);

			rHorn01 = new ModelPart(this);
			rHorn01.setPivot(-2.9F, -7.3F, 0.6F);
			head.addChild(rHorn01);
			setRotationAngle(rHorn01, -0.6109F, 0.0F, -1.309F);
			rHorn01.setTextureOffset(0, 0).addCuboid(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, true);

			rHorn02 = new ModelPart(this);
			rHorn02.setPivot(0.1F, -1.5F, -0.1F);
			rHorn01.addChild(rHorn02);
			setRotationAngle(rHorn02, -0.2618F, 0.0F, -0.4014F);
			rHorn02.setTextureOffset(0, 4).addCuboid(-0.4F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.2F, true);

			rHorn03 = new ModelPart(this);
			rHorn03.setPivot(0.0F, -1.6F, 0.0F);
			rHorn02.addChild(rHorn03);
			setRotationAngle(rHorn03, -0.1745F, 0.0F, -0.4363F);
			rHorn03.setTextureOffset(0, 4).addCuboid(-0.2F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.1F, true);

			rHorn04 = new ModelPart(this);
			rHorn04.setPivot(0.0F, -1.7F, 0.0F);
			rHorn03.addChild(rHorn04);
			setRotationAngle(rHorn04, 0.0524F, 0.0F, -0.1396F);
			rHorn04.setTextureOffset(49, 0).addCuboid(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F, 0.0F, true);

			rHorn05 = new ModelPart(this);
			rHorn05.setPivot(0.0F, -2.7F, 0.0F);
			rHorn04.addChild(rHorn05);
			setRotationAngle(rHorn05, 0.0524F, 0.0F, 0.3142F);
			rHorn05.setTextureOffset(43, 0).addCuboid(-0.5F, -2.1F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, true);

			lHorn01 = new ModelPart(this);
			lHorn01.setPivot(2.9F, -7.3F, 0.6F);
			head.addChild(lHorn01);
			setRotationAngle(lHorn01, -0.6109F, 0.0F, 1.309F);
			lHorn01.setTextureOffset(0, 0).addCuboid(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);

			lHorn02 = new ModelPart(this);
			lHorn02.setPivot(-0.1F, -1.5F, -0.1F);
			lHorn01.addChild(lHorn02);
			setRotationAngle(lHorn02, -0.2618F, 0.0F, 0.4014F);
			lHorn02.setTextureOffset(0, 4).addCuboid(-0.6F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.2F, false);

			lHorn03 = new ModelPart(this);
			lHorn03.setPivot(0.0F, -1.6F, 0.0F);
			lHorn02.addChild(lHorn03);
			setRotationAngle(lHorn03, -0.1745F, 0.0F, 0.4363F);
			lHorn03.setTextureOffset(0, 4).addCuboid(-0.8F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.1F, false);

			lHorn04 = new ModelPart(this);
			lHorn04.setPivot(0.0F, -1.7F, 0.0F);
			lHorn03.addChild(lHorn04);
			setRotationAngle(lHorn04, 0.0524F, 0.0F, 0.1396F);
			lHorn04.setTextureOffset(49, 0).addCuboid(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F, 0.0F, false);

			lHorn05 = new ModelPart(this);
			lHorn05.setPivot(0.0F, -2.7F, 0.0F);
			lHorn04.addChild(lHorn05);
			setRotationAngle(lHorn05, 0.0524F, 0.0F, -0.3142F);
			lHorn05.setTextureOffset(43, 0).addCuboid(-0.5F, -2.1F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);

			hair01 = new ModelPart(this);
			hair01.setPivot(0.0F, -7.2F, 3.2F);
			head.addChild(hair01);
			setRotationAngle(hair01, 0.3491F, 0.0F, 0.0F);
			hair01.setTextureOffset(40, 36).addCuboid(-3.99F, 0.0F, -0.1F, 8.0F, 8.0F, 1.0F, 0.0F, false);

			hair00 = new ModelPart(this);
			hair00.setPivot(0.0F, -5.5F, 3.2F);
			head.addChild(hair00);
			setRotationAngle(hair00, 0.1396F, 0.0F, 0.0F);
			hair00.setTextureOffset(38, 46).addCuboid(-4.01F, 0.0F, -1.0F, 8.0F, 11.0F, 2.0F, 0.0F, false);
		}

		@Override
		public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {

			body.render(matrices, vertices, light, overlay, red, green, blue, alpha);
			bipedLeftArm.render(matrices, vertices, light, overlay, red, green, blue, alpha);
			bipedRightArm.render(matrices, vertices, light, overlay, red, green, blue, alpha);
			bipedLeftLeg.render(matrices, vertices, light, overlay, red, green, blue, alpha);
			bipedRightLeg.render(matrices, vertices, light, overlay, red, green, blue, alpha);
			head.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		}

		public void setRotationAngle(ModelPart bone, float x, float y, float z) {
			bone.pitch = x;
			bone.yaw = y;
			bone.roll = z;
		}

		@Override
		public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
			realArm = false;
			super.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
			realArm = true;
			copyRotation(head, super.head);
			copyRotation(body, super.torso);
			copyRotation(bipedLeftArm, super.leftArm);
			copyRotation(bipedRightArm, super.rightArm);
			copyRotation(bipedLeftLeg, super.leftLeg);
			copyRotation(bipedRightLeg, super.rightLeg);

			this.bipedRightArm.pitch = -MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 2.0F * limbDistance * 0.55F;
			this.bipedLeftArm.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 2.0F * limbDistance * 0.55F;
			float j = MathHelper.sin(entity.handSwingProgress * 3.1415927F);
			if (j > 0) {
				this.bipedRightArm.pitch = -j;
				if (entity.getDataTracker().get(CambionEntity.PUNCH)) {
					this.bipedLeftArm.pitch = -j;
				}
			}
		}

		@Override
		protected ModelPart getArm(Arm arm) {
			return realArm ? (arm == Arm.LEFT ? bipedLeftArm : bipedRightArm) : super.getArm(arm);
		}
	}

	private class Female extends BipedEntityModel<T> implements ModelWithArms, ModelWithHead {
		private final ModelPart body;
		private final ModelPart skirtFront;
		private final ModelPart skirtBack;
		private final ModelPart bipedLeftArm;
		private final ModelPart lClaws;
		private final ModelPart bipedRightArm;
		private final ModelPart rClaws;
		private final ModelPart bipedLeftLeg;
		private final ModelPart bipedRightLeg;
		private final ModelPart head;
		private final ModelPart snout;
		private final ModelPart upperJaw;
		private final ModelPart lowerJaw;
		private final ModelPart lUpperHorn01;
		private final ModelPart lUpperHorn02;
		private final ModelPart lUpperHorn03;
		private final ModelPart rUpperHorn01;
		private final ModelPart rUpperHorn02;
		private final ModelPart rUpperHorn03;
		private final ModelPart rHorn01;
		private final ModelPart rHorn02;
		private final ModelPart rHorn03;
		private final ModelPart rHorn04;
		private final ModelPart rHorn05;
		private final ModelPart lHorn01;
		private final ModelPart lHorn02;
		private final ModelPart lHorn03;
		private final ModelPart lHorn04;
		private final ModelPart lHorn05;
		private final ModelPart hair01;
		private final ModelPart hair00;

		private boolean realArm = false;

		public Female() {
			super(1, 0, 64, 64);
			body = new ModelPart(this);
			body.setPivot(0.0F, -2.0F, 0.0F);
			body.setTextureOffset(16, 16).addCuboid(-4.0F, 0.0F, -2.0F, 8.0F, 13.0F, 4.0F, 0.0F, false);

			skirtFront = new ModelPart(this);
			skirtFront.setPivot(0.0F, 12.9F, -1.7F);
			body.addChild(skirtFront);
			skirtFront.setTextureOffset(0, 42).addCuboid(-4.5F, -0.1F, -0.5F, 9.0F, 9.0F, 2.0F, 0.0F, false);

			skirtBack = new ModelPart(this);
			skirtBack.setPivot(0.0F, 12.9F, 0.6F);
			body.addChild(skirtBack);
			skirtBack.setTextureOffset(0, 53).addCuboid(-4.5F, -0.1F, -0.5F, 9.0F, 9.0F, 2.0F, 0.0F, false);

			bipedLeftArm = new ModelPart(this);
			bipedLeftArm.setPivot(5.0F, 0.0F, 0.0F);
			setRotationAngle(bipedLeftArm, 0.0F, 0.0F, -0.1F);
			bipedLeftArm.setTextureOffset(40, 16).addCuboid(-1.0F, -2.0F, -2.0F, 3.0F, 14.0F, 4.0F, 0.0F, false);

			lClaws = new ModelPart(this);
			lClaws.setPivot(1.4F, 11.5F, 0.0F);
			bipedLeftArm.addChild(lClaws);
			setRotationAngle(lClaws, 0.0F, 0.0F, 0.2793F);
			lClaws.setTextureOffset(0, 34).addCuboid(-1.1F, 0.0F, -2.1F, 2.0F, 3.0F, 4.0F, 0.0F, false);

			bipedRightArm = new ModelPart(this);
			bipedRightArm.setPivot(-5.0F, 0.0F, 0.0F);
			setRotationAngle(bipedRightArm, 0.0F, 0.0F, 0.1F);
			bipedRightArm.setTextureOffset(40, 16).addCuboid(-2.0F, -2.0F, -2.0F, 3.0F, 14.0F, 4.0F, 0.0F, true);

			rClaws = new ModelPart(this);
			rClaws.setPivot(-1.15F, 11.5F, 0.0F);
			bipedRightArm.addChild(rClaws);
			setRotationAngle(rClaws, 0.0F, 0.0F, -0.2793F);
			rClaws.setTextureOffset(0, 34).addCuboid(-1.1F, 0.0F, -2.1F, 2.0F, 3.0F, 4.0F, 0.0F, true);

			bipedLeftLeg = new ModelPart(this);
			bipedLeftLeg.setPivot(1.9F, 11.0F, 0.0F);
			bipedLeftLeg.setTextureOffset(0, 16).addCuboid(-2.0F, 0.0F, -2.0F, 4.0F, 13.0F, 4.0F, 0.0F, false);

			bipedRightLeg = new ModelPart(this);
			bipedRightLeg.setPivot(-1.9F, 11.0F, 0.0F);
			bipedRightLeg.setTextureOffset(0, 16).addCuboid(-2.0F, 0.0F, -2.0F, 4.0F, 13.0F, 4.0F, 0.0F, true);

			head = new ModelPart(this);
			head.setPivot(0.0F, -2.0F, 0.0F);
			head.setTextureOffset(0, 0).addCuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);

			snout = new ModelPart(this);
			snout.setPivot(0.0F, -2.5F, -3.9F);
			head.addChild(snout);
			setRotationAngle(snout, 0.4189F, 0.0F, 0.0F);
			snout.setTextureOffset(21, 35).addCuboid(-1.5F, -1.0F, -2.0F, 3.0F, 2.0F, 3.0F, 0.0F, false);

			upperJaw = new ModelPart(this);
			upperJaw.setPivot(0.0F, -1.4F, -4.0F);
			head.addChild(upperJaw);
			upperJaw.setTextureOffset(23, 41).addCuboid(-2.0F, -1.0F, -1.7F, 4.0F, 2.0F, 2.0F, 0.0F, false);

			lowerJaw = new ModelPart(this);
			lowerJaw.setPivot(0.0F, -0.4F, -4.0F);
			head.addChild(lowerJaw);
			lowerJaw.setTextureOffset(23, 46).addCuboid(-1.5F, -0.5F, -1.6F, 3.0F, 1.0F, 2.0F, 0.0F, false);

			lUpperHorn01 = new ModelPart(this);
			lUpperHorn01.setPivot(2.0F, -8.7F, -0.5F);
			head.addChild(lUpperHorn01);
			setRotationAngle(lUpperHorn01, 0.8727F, 0.1745F, 0.0F);
			lUpperHorn01.setTextureOffset(24, 0).addCuboid(-1.0F, -1.0F, -1.8F, 2.0F, 2.0F, 3.0F, 0.0F, true);

			lUpperHorn02 = new ModelPart(this);
			lUpperHorn02.setPivot(0.0F, 0.0F, 0.9F);
			lUpperHorn01.addChild(lUpperHorn02);
			setRotationAngle(lUpperHorn02, -0.3142F, 0.2618F, 0.0F);
			lUpperHorn02.setTextureOffset(33, 7).addCuboid(-0.5F, -0.6F, -0.5F, 1.0F, 1.0F, 3.0F, 0.2F, true);

			lUpperHorn03 = new ModelPart(this);
			lUpperHorn03.setPivot(0.0F, 0.0F, 2.4F);
			lUpperHorn02.addChild(lUpperHorn03);
			setRotationAngle(lUpperHorn03, -0.1745F, 0.1745F, 0.0F);
			lUpperHorn03.setTextureOffset(42, 7).addCuboid(-0.5F, -0.5F, -0.4F, 1.0F, 1.0F, 3.0F, 0.0F, true);

			rUpperHorn01 = new ModelPart(this);
			rUpperHorn01.setPivot(-2.0F, -8.7F, -0.5F);
			head.addChild(rUpperHorn01);
			setRotationAngle(rUpperHorn01, 0.8727F, -0.1745F, 0.0F);
			rUpperHorn01.setTextureOffset(24, 0).addCuboid(-1.0F, -1.0F, -1.8F, 2.0F, 2.0F, 3.0F, 0.0F, true);

			rUpperHorn02 = new ModelPart(this);
			rUpperHorn02.setPivot(0.0F, 0.0F, 0.9F);
			rUpperHorn01.addChild(rUpperHorn02);
			setRotationAngle(rUpperHorn02, -0.3142F, -0.2618F, 0.0F);
			rUpperHorn02.setTextureOffset(33, 7).addCuboid(-0.5F, -0.6F, -0.5F, 1.0F, 1.0F, 3.0F, 0.2F, true);

			rUpperHorn03 = new ModelPart(this);
			rUpperHorn03.setPivot(0.0F, 0.0F, 2.4F);
			rUpperHorn02.addChild(rUpperHorn03);
			setRotationAngle(rUpperHorn03, -0.1745F, -0.1745F, 0.0F);
			rUpperHorn03.setTextureOffset(42, 7).addCuboid(-0.5F, -0.5F, -0.4F, 1.0F, 1.0F, 3.0F, 0.0F, true);

			rHorn01 = new ModelPart(this);
			rHorn01.setPivot(-2.9F, -7.3F, 0.6F);
			head.addChild(rHorn01);
			setRotationAngle(rHorn01, -0.6109F, 0.0F, -1.309F);
			rHorn01.setTextureOffset(0, 0).addCuboid(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, true);

			rHorn02 = new ModelPart(this);
			rHorn02.setPivot(0.1F, -1.5F, -0.1F);
			rHorn01.addChild(rHorn02);
			setRotationAngle(rHorn02, -0.2618F, 0.0F, -0.4014F);
			rHorn02.setTextureOffset(0, 4).addCuboid(-0.4F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.2F, true);

			rHorn03 = new ModelPart(this);
			rHorn03.setPivot(0.0F, -1.6F, 0.0F);
			rHorn02.addChild(rHorn03);
			setRotationAngle(rHorn03, -0.1745F, 0.0F, -0.4363F);
			rHorn03.setTextureOffset(0, 4).addCuboid(-0.2F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.1F, true);

			rHorn04 = new ModelPart(this);
			rHorn04.setPivot(0.0F, -1.7F, 0.0F);
			rHorn03.addChild(rHorn04);
			setRotationAngle(rHorn04, 0.0524F, 0.0F, -0.1396F);
			rHorn04.setTextureOffset(49, 0).addCuboid(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F, 0.0F, true);

			rHorn05 = new ModelPart(this);
			rHorn05.setPivot(0.0F, -2.7F, 0.0F);
			rHorn04.addChild(rHorn05);
			setRotationAngle(rHorn05, 0.0524F, 0.0F, 0.3142F);
			rHorn05.setTextureOffset(43, 0).addCuboid(-0.5F, -2.1F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, true);

			lHorn01 = new ModelPart(this);
			lHorn01.setPivot(2.9F, -7.3F, 0.6F);
			head.addChild(lHorn01);
			setRotationAngle(lHorn01, -0.6109F, 0.0F, 1.309F);
			lHorn01.setTextureOffset(0, 0).addCuboid(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);

			lHorn02 = new ModelPart(this);
			lHorn02.setPivot(-0.1F, -1.5F, -0.1F);
			lHorn01.addChild(lHorn02);
			setRotationAngle(lHorn02, -0.2618F, 0.0F, 0.4014F);
			lHorn02.setTextureOffset(0, 4).addCuboid(-0.6F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.2F, false);

			lHorn03 = new ModelPart(this);
			lHorn03.setPivot(0.0F, -1.6F, 0.0F);
			lHorn02.addChild(lHorn03);
			setRotationAngle(lHorn03, -0.1745F, 0.0F, 0.4363F);
			lHorn03.setTextureOffset(0, 4).addCuboid(-0.8F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.1F, false);

			lHorn04 = new ModelPart(this);
			lHorn04.setPivot(0.0F, -1.7F, 0.0F);
			lHorn03.addChild(lHorn04);
			setRotationAngle(lHorn04, 0.0524F, 0.0F, 0.1396F);
			lHorn04.setTextureOffset(49, 0).addCuboid(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F, 0.0F, false);

			lHorn05 = new ModelPart(this);
			lHorn05.setPivot(0.0F, -2.7F, 0.0F);
			lHorn04.addChild(lHorn05);
			setRotationAngle(lHorn05, 0.0524F, 0.0F, -0.3142F);
			lHorn05.setTextureOffset(43, 0).addCuboid(-0.5F, -2.1F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);

			hair01 = new ModelPart(this);
			hair01.setPivot(0.0F, -7.2F, 3.2F);
			head.addChild(hair01);
			setRotationAngle(hair01, 0.3491F, 0.0F, 0.0F);
			hair01.setTextureOffset(40, 36).addCuboid(-3.99F, 0.0F, -0.1F, 8.0F, 8.0F, 1.0F, 0.0F, false);

			hair00 = new ModelPart(this);
			hair00.setPivot(0.0F, -5.5F, 3.2F);
			head.addChild(hair00);
			setRotationAngle(hair00, 0.1396F, 0.0F, 0.0F);
			hair00.setTextureOffset(38, 46).addCuboid(-4.01F, 0.0F, -1.0F, 8.0F, 11.0F, 2.0F, 0.0F, false);
		}

		@Override
		public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {

			body.render(matrices, vertices, light, overlay, red, green, blue, alpha);
			bipedLeftArm.render(matrices, vertices, light, overlay, red, green, blue, alpha);
			bipedRightArm.render(matrices, vertices, light, overlay, red, green, blue, alpha);
			bipedLeftLeg.render(matrices, vertices, light, overlay, red, green, blue, alpha);
			bipedRightLeg.render(matrices, vertices, light, overlay, red, green, blue, alpha);
			head.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		}

		@Override
		public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
			realArm = false;
			super.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
			realArm = true;
			copyRotation(head, super.head);
			copyRotation(body, super.torso);
			copyRotation(bipedLeftArm, super.leftArm);
			copyRotation(bipedRightArm, super.rightArm);
			copyRotation(bipedLeftLeg, super.leftLeg);
			copyRotation(bipedRightLeg, super.rightLeg);

			this.bipedRightArm.pitch = -MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 2.0F * limbDistance * 0.55F;
			this.bipedLeftArm.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 2.0F * limbDistance * 0.55F;
			float j = MathHelper.sin(entity.handSwingProgress * 3.1415927F);
			if (j > 0) {
				this.bipedRightArm.pitch = -j;
				if (entity.getDataTracker().get(CambionEntity.PUNCH)) {
					this.bipedLeftArm.pitch = -j;
				}
			}
		}

		public void setRotationAngle(ModelPart bone, float x, float y, float z) {
			bone.pitch = x;
			bone.yaw = y;
			bone.roll = z;
		}

		@Override
		protected ModelPart getArm(Arm arm) {
			return realArm ? (arm == Arm.LEFT ? bipedLeftArm : bipedRightArm) : super.getArm(arm);
		}
	}
}
