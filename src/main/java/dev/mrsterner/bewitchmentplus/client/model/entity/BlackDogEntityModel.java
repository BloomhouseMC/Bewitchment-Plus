package dev.mrsterner.bewitchmentplus.client.model.entity;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.common.entity.BlackDogEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class BlackDogEntityModel<T extends BlackDogEntity> extends EntityModel<T> {
	public static final EntityModelLayer BLACKDOG_MODEL_LAYER = new EntityModelLayer(new Identifier(BewitchmentPlus.MODID, "blackdog"), "main");

	private final ModelPart lForeleg;
	private final ModelPart body;
	private final ModelPart neck;
	private final ModelPart head;
	private final ModelPart lHindleg;
	private final ModelPart rHindleg;
	private final ModelPart rForeleg;
	private final ModelPart tail;

	public BlackDogEntityModel(ModelPart root) {
		body = root.getChild("body");
		neck = body.getChild("neck");
		head = neck.getChild("head");
		tail = body.getChild("tail");
		lForeleg = root.getChild("lForeleg");
		lHindleg = root.getChild("lHindleg");
		rHindleg = root.getChild("rHindleg");
		rForeleg = root.getChild("rForeleg");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData data = new ModelData();
		ModelPartData root = data.getRoot();

		root.addChild("lHindleg", ModelPartBuilder.create().uv(0, 18).mirrored(true).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F), ModelTransform.of(1.5F, 16.0F, 6.0F, 0.0F, 0.0F, 0.0F));
		ModelPartData body = root.addChild("body", ModelPartBuilder.create().uv(18, 14).cuboid(-3.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F), ModelTransform.of(0.0F, 14.0F, 1.0F, 1.5708F, 0.0F, 0.0F));
		ModelPartData mane00 = body.addChild("mane00", ModelPartBuilder.create().uv(21, 0).cuboid(-4.0F, -3.5F, -3.01F, 8.0F, 6.0F, 7.0F), ModelTransform.of(0.0F, -4.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		mane00.addChild("mane02", ModelPartBuilder.create().uv(28, 48).cuboid(-3.5F, -1.0F, 0.0F, 7.0F, 2.0F, 7.0F), ModelTransform.of(0.0F, -1.0F, 2.7F, -1.2915F, 0.0F, 0.0F));
		body.addChild("tail", ModelPartBuilder.create().uv(9, 18).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 9.0F, 2.0F), ModelTransform.of(0.0F, 5.7F, 2.0F, -0.8727F, 0.0F, 0.0F));
		ModelPartData neck = body.addChild("neck", ModelPartBuilder.create().uv(0, 32).cuboid(-2.5F, -2.5F, -4.0F, 5.0F, 5.0F, 4.0F), ModelTransform.of(0.0F, -5.4F, 0.5F, -1.5708F, 0.0F, 0.0F));
		neck.addChild("mane01", ModelPartBuilder.create().uv(0, 48).cuboid(-3.0F, -1.0F, 0.0F, 6.0F, 2.0F, 7.0F, new Dilation(-0.01F, -0.01F, -0.01F)), ModelTransform.of(0.0F, -1.8F, -3.0F, 0.4363F, 0.0F, 0.0F));
		ModelPartData head = neck.addChild("head", ModelPartBuilder.create().cuboid(-3.0F, -3.0F, -4.0F, 6.0F, 6.0F, 4.0F), ModelTransform.of(0.0F, 0.0F, -2.9F, 0.0F, 0.0F, 0.0F));
		head.addChild("muzzle", ModelPartBuilder.create().uv(0, 10).cuboid(-1.5F, -1.0F, -3.0F, 3.0F, 2.0F, 3.0F), ModelTransform.of(0.0F, 0.7F, -3.9F, 0.0F, 0.0F, 0.0F));
		head.addChild("lowerJaw", ModelPartBuilder.create().uv(0, 43).cuboid(-1.5F, -0.4F, -3.0F, 3.0F, 1.0F, 3.0F), ModelTransform.of(0.0F, 2.0F, -3.8F, 0.0F, 0.0F, 0.0F));
		head.addChild("lEar", ModelPartBuilder.create().uv(39, 14).cuboid(-0.5F, -0.1F, -1.0F, 1.0F, 3.0F, 2.0F), ModelTransform.of(-2.9F, -2.9F, -2.0F, 0.0F, 0.0F, 0.3054F));
		head.addChild("rEar", ModelPartBuilder.create().uv(39, 14).cuboid(-0.5F, -0.1F, -1.0F, 1.0F, 3.0F, 2.0F), ModelTransform.of(2.9F, -2.9F, -2.0F, 0.0F, 0.0F, -0.3054F));
		root.addChild("lForeleg", ModelPartBuilder.create().uv(0, 18).mirrored(true).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F), ModelTransform.of(1.5F, 16.0F, -4.0F, 0.0F, 0.0F, 0.0F));
		root.addChild("rHindleg", ModelPartBuilder.create().uv(0, 18).mirrored(true).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F), ModelTransform.of(-1.5F, 16.0F, 6.0F, 0.0F, 0.0F, 0.0F));
		root.addChild("rForeleg", ModelPartBuilder.create().uv(0, 18).mirrored(true).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F), ModelTransform.of(-1.5F, 16.0F, -4.0F, 0.0F, 0.0F, 0.0F));
		return TexturedModelData.of(data, 64, 64);
	}

	@Override
	public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		head.pitch = (float) (headPitch * (Math.PI / 180f));
		head.yaw = (float) (headYaw * (Math.PI / 180f)) * 2 / 3f;
		lForeleg.pitch = MathHelper.cos(limbAngle * 2 / 3f) * 1.4f * limbDistance;
		rForeleg.pitch = MathHelper.cos((float) (limbAngle * 2 / 3f + Math.PI)) * 1.4f * limbDistance;
		lHindleg.pitch = MathHelper.cos((float) (limbAngle * 2 / 3f + Math.PI)) * 1.4f * limbDistance;
		rHindleg.pitch = MathHelper.cos(limbAngle * 2 / 3f) * 1.4f * limbDistance;
		tail.roll = (MathHelper.cos(limbAngle * 2 / 3f) * limbDistance) + MathHelper.sin((animationProgress + entity.getId()) * 1 / 8f) * 0.25f;
		boolean attacking = entity.attackTick > 0;
		if (attacking) {
			tail.pitch = 1;
		} else {
			tail.pitch = 0;
		}
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		body.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		lForeleg.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		rForeleg.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		lHindleg.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		rHindleg.render(matrices, vertices, light, overlay, red, green, blue, alpha);
	}

	private void setRotationAngle(ModelPart bone, float x, float y, float z) {
		bone.pitch = x;
		bone.yaw = y;
		bone.roll = z;
	}
}