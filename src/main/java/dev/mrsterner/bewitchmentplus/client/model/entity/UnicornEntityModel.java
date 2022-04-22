package dev.mrsterner.bewitchmentplus.client.model.entity;


import com.google.common.collect.ImmutableList;
import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.common.entity.UnicornEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.HorseEntityModel;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class UnicornEntityModel<T extends UnicornEntity> extends AnimalModel<T> {
	public static final EntityModelLayer UNICORN_MODEL_LAYER = new EntityModelLayer(new Identifier(BewitchmentPlus.MODID, "unicorn"), "main");
	protected final ModelPart body;
	protected final ModelPart head;
	private final ModelPart rightHindLeg;
	private final ModelPart leftHindLeg;
	private final ModelPart rightFrontLeg;
	private final ModelPart leftFrontLeg;
	private final ModelPart rightHindBabyLeg;
	private final ModelPart leftHindBabyLeg;
	private final ModelPart rightFrontBabyLeg;
	private final ModelPart leftFrontBabyLeg;
	private final ModelPart tail;

	public UnicornEntityModel(ModelPart root) {
		super(true, 16.2F, 1.36F, 2.7272F, 2.0F, 20.0F);
		this.body = root.getChild("body");
		this.head = root.getChild("head_parts");
		this.rightHindLeg = root.getChild("right_hind_leg");
		this.leftHindLeg = root.getChild("left_hind_leg");
		this.rightFrontLeg = root.getChild("right_front_leg");
		this.leftFrontLeg = root.getChild("left_front_leg");
		this.rightHindBabyLeg = root.getChild("right_hind_baby_leg");
		this.leftHindBabyLeg = root.getChild("left_hind_baby_leg");
		this.rightFrontBabyLeg = root.getChild("right_front_baby_leg");
		this.leftFrontBabyLeg = root.getChild("left_front_baby_leg");
		this.tail = this.body.getChild("tail");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = HorseEntityModel.getModelData(Dilation.NONE);
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData modelPartData2 = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 32).cuboid(-5.0F, -8.0F, -17.0F, 10.0F, 10.0F, 22.0F, new Dilation(0.05F)), ModelTransform.pivot(0.0F, 11.0F, 5.0F));
		ModelPartData modelPartData3 = modelPartData.addChild("head_parts", ModelPartBuilder.create().uv(0, 35).cuboid(-2.05F, -6.0F, -2.0F, 4.0F, 12.0F, 7.0F), ModelTransform.of(0.0F, 4.0F, -12.0F, 0.5235988F, 0.0F, 0.0F));
		ModelPartData modelPartData4 = modelPartData3.addChild("head", ModelPartBuilder.create().uv(0, 13).cuboid(-3.0F, -11.0F, -2.0F, 6.0F, 5.0F, 7.0F, new Dilation(0.0F)), ModelTransform.NONE);
		modelPartData3.addChild("mane", ModelPartBuilder.create().uv(56, 36).cuboid(-1.0F, -11.0F, 5.01F, 2.0F, 16.0F, 2.0F, new Dilation(0.0F)), ModelTransform.NONE);
		modelPartData3.addChild("upper_mouth", ModelPartBuilder.create().uv(0, 25).cuboid(-2.0F, -11.0F, -7.0F, 4.0F, 5.0F, 5.0F, new Dilation(0.0F)), ModelTransform.NONE);
		modelPartData.addChild("left_hind_leg", ModelPartBuilder.create().uv(48, 21).mirrored().cuboid(-3.0F, -1.01F, -1.0F, 4.0F, 11.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(4.0F, 14.0F, 7.0F));
		modelPartData.addChild("right_hind_leg", ModelPartBuilder.create().uv(48, 21).cuboid(-1.0F, -1.01F, -1.0F, 4.0F, 11.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.0F, 14.0F, 7.0F));
		modelPartData.addChild("left_front_leg", ModelPartBuilder.create().uv(48, 21).mirrored().cuboid(-3.0F, -1.01F, -1.9F, 4.0F, 11.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(4.0F, 14.0F, -12.0F));
		modelPartData.addChild("right_front_leg", ModelPartBuilder.create().uv(48, 21).cuboid(-1.0F, -1.01F, -1.9F, 4.0F, 11.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.0F, 14.0F, -12.0F));
		Dilation dilation2 = new Dilation(0.0F).add(0.0F, 5.5F, 0.0F);
		modelPartData.addChild("left_hind_baby_leg", ModelPartBuilder.create().uv(48, 21).mirrored().cuboid(-3.0F, -1.01F, -1.0F, 4.0F, 11.0F, 4.0F, dilation2), ModelTransform.pivot(4.0F, 14.0F, 7.0F));
		modelPartData.addChild("right_hind_baby_leg", ModelPartBuilder.create().uv(48, 21).cuboid(-1.0F, -1.01F, -1.0F, 4.0F, 11.0F, 4.0F, dilation2), ModelTransform.pivot(-4.0F, 14.0F, 7.0F));
		modelPartData.addChild("left_front_baby_leg", ModelPartBuilder.create().uv(48, 21).mirrored().cuboid(-3.0F, -1.01F, -1.9F, 4.0F, 11.0F, 4.0F, dilation2), ModelTransform.pivot(4.0F, 14.0F, -12.0F));
		modelPartData.addChild("right_front_baby_leg", ModelPartBuilder.create().uv(48, 21).cuboid(-1.0F, -1.01F, -1.9F, 4.0F, 11.0F, 4.0F, dilation2), ModelTransform.pivot(-4.0F, 14.0F, -12.0F));
		modelPartData2.addChild("tail", ModelPartBuilder.create().uv(42, 36).cuboid(-1.5F, 0.0F, 0.0F, 3.0F, 14.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -5.0F, 2.0F, 0.5235988F, 0.0F, 0.0F));
		modelPartData4.addChild("left_ear", ModelPartBuilder.create().uv(19, 16).cuboid(0.55F, -13.0F, 4.0F, 2.0F, 3.0F, 1.0F, new Dilation(-0.001F)), ModelTransform.NONE);
		modelPartData4.addChild("right_ear", ModelPartBuilder.create().uv(19, 16).cuboid(-2.55F, -13.0F, 4.0F, 2.0F, 3.0F, 1.0F, new Dilation(-0.001F)), ModelTransform.NONE);


		ModelPartData head = modelData.getRoot().getChild("head_parts").getChild("head");
		ModelPartData neck = modelData.getRoot().getChild("head_parts");
		head.addChild("horn", ModelPartBuilder.create().uv(0, 0).cuboid(-0.5F, -12.0F, -2.5F, 1.0F, 6.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -5.0F, 4.0F));
		neck.addChild("mane", ModelPartBuilder.create().uv(0, 64).cuboid(-1.0F, -11.0F, 5.01F, 2.0F, 16.0F, 4.0F, new Dilation(0.0F)), ModelTransform.NONE);
		return TexturedModelData.of(modelData, 128, 128);
	}

	public void animateModel(T horseBaseEntity, float f, float g, float h) {
		super.animateModel(horseBaseEntity, f, g, h);
		float i = MathHelper.lerpAngle(horseBaseEntity.prevBodyYaw, horseBaseEntity.bodyYaw, h);
		float j = MathHelper.lerpAngle(horseBaseEntity.prevHeadYaw, horseBaseEntity.headYaw, h);
		float k = MathHelper.lerp(h, horseBaseEntity.prevPitch, horseBaseEntity.getPitch());
		float l = j - i;
		float m = k * 0.017453292F;
		if (l > 20.0F) {
			l = 20.0F;
		}

		if (l < -20.0F) {
			l = -20.0F;
		}

		if (g > 0.2F) {
			m += MathHelper.cos(f * 0.4F) * 0.15F * g;
		}

		float n = horseBaseEntity.getEatingGrassAnimationProgress(h);
		float o = horseBaseEntity.getAngryAnimationProgress(h);
		float p = 1.0F - o;
		float q = horseBaseEntity.getEatingAnimationProgress(h);
		boolean bl = horseBaseEntity.tailWagTicks != 0;
		float r = (float)horseBaseEntity.age + h;
		this.head.pivotY = 4.0F;
		this.head.pivotZ = -12.0F;
		this.body.pitch = 0.0F;
		this.head.pitch = 0.5235988F + m;
		this.head.yaw = l * 0.017453292F;
		float s = horseBaseEntity.isTouchingWater() ? 0.2F : 1.0F;
		float t = MathHelper.cos(s * f * 0.6662F + 3.1415927F);
		float u = t * 0.8F * g;
		float v = (1.0F - Math.max(o, n)) * (0.5235988F + m + q * MathHelper.sin(r) * 0.05F);
		this.head.pitch = o * (0.2617994F + m) + n * (2.1816616F + MathHelper.sin(r) * 0.05F) + v;
		this.head.yaw = o * l * 0.017453292F + (1.0F - Math.max(o, n)) * this.head.yaw;
		this.head.pivotY = o * -4.0F + n * 11.0F + (1.0F - Math.max(o, n)) * this.head.pivotY;
		this.head.pivotZ = o * -4.0F + n * -12.0F + (1.0F - Math.max(o, n)) * this.head.pivotZ;
		this.body.pitch = o * -0.7853982F + p * this.body.pitch;
		float w = 0.2617994F * o;
		float x = MathHelper.cos(r * 0.6F + 3.1415927F);
		this.leftFrontLeg.pivotY = 2.0F * o + 14.0F * p;
		this.leftFrontLeg.pivotZ = -6.0F * o - 10.0F * p;
		this.rightFrontLeg.pivotY = this.leftFrontLeg.pivotY;
		this.rightFrontLeg.pivotZ = this.leftFrontLeg.pivotZ;
		float y = (-1.0471976F + x) * o + u * p;
		float z = (-1.0471976F - x) * o - u * p;
		this.leftHindLeg.pitch = w - t * 0.5F * g * p;
		this.rightHindLeg.pitch = w + t * 0.5F * g * p;
		this.leftFrontLeg.pitch = y;
		this.rightFrontLeg.pitch = z;
		this.tail.pitch = 0.5235988F + g * 0.75F;
		this.tail.pivotY = -5.0F + g;
		this.tail.pivotZ = 2.0F + g * 2.0F;
		if (bl) {
			this.tail.yaw = MathHelper.cos(r * 0.7F);
		} else {
			this.tail.yaw = 0.0F;
		}

		this.rightHindBabyLeg.pivotY = this.rightHindLeg.pivotY;
		this.rightHindBabyLeg.pivotZ = this.rightHindLeg.pivotZ;
		this.rightHindBabyLeg.pitch = this.rightHindLeg.pitch;
		this.leftHindBabyLeg.pivotY = this.leftHindLeg.pivotY;
		this.leftHindBabyLeg.pivotZ = this.leftHindLeg.pivotZ;
		this.leftHindBabyLeg.pitch = this.leftHindLeg.pitch;
		this.rightFrontBabyLeg.pivotY = this.rightFrontLeg.pivotY;
		this.rightFrontBabyLeg.pivotZ = this.rightFrontLeg.pivotZ;
		this.rightFrontBabyLeg.pitch = this.rightFrontLeg.pitch;
		this.leftFrontBabyLeg.pivotY = this.leftFrontLeg.pivotY;
		this.leftFrontBabyLeg.pivotZ = this.leftFrontLeg.pivotZ;
		this.leftFrontBabyLeg.pitch = this.leftFrontLeg.pitch;
		boolean bl2 = horseBaseEntity.isBaby();
		this.rightHindLeg.visible = !bl2;
		this.leftHindLeg.visible = !bl2;
		this.rightFrontLeg.visible = !bl2;
		this.leftFrontLeg.visible = !bl2;
		this.rightHindBabyLeg.visible = bl2;
		this.leftHindBabyLeg.visible = bl2;
		this.rightFrontBabyLeg.visible = bl2;
		this.leftFrontBabyLeg.visible = bl2;
		this.body.pivotY = bl2 ? 10.8F : 0.0F;
	}

	@Override
	public Iterable<ModelPart> getHeadParts() {
		return ImmutableList.of(this.head);
	}

	@Override
	protected Iterable<ModelPart> getBodyParts() {
		return ImmutableList.of(this.body, this.rightHindLeg, this.leftHindLeg, this.rightFrontLeg, this.leftFrontLeg, this.rightHindBabyLeg, this.leftHindBabyLeg, this.rightFrontBabyLeg, this.leftFrontBabyLeg);
	}

	@Override
	public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		this.body.pivotY = 11.0F;
	}
}
