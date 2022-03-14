package dev.mrsterner.bewitchmentplus.client.renderer.entity;

import moriyashiine.bewitchment.common.entity.living.util.BWHostileEntity;
import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.client.model.entity.CambionEntityModel;
import dev.mrsterner.bewitchmentplus.common.entity.CambionEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.UseAction;

@Environment(EnvType.CLIENT)
public class CambionEntityRenderer extends BipedEntityRenderer<CambionEntity, BipedEntityModel<CambionEntity>> {
	private static Identifier[] MALE_TEXTURES, FEMALE_TEXTURES;

	private final BipedEntityModel MALE_MODEL, FEMALE_MODEL;

	public CambionEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new CambionEntityModel(context.getPart(CambionEntityModel.MALE_CAMBION_MODEL_LAYER)), 0.5f);
		this.addFeature(new ArmorFeatureRenderer<>(this, new BipedEntityModel(context.getPart(EntityModelLayers.PLAYER_INNER_ARMOR)), new BipedEntityModel(context.getPart(EntityModelLayers.PLAYER_OUTER_ARMOR))));
		MALE_MODEL = model;
		FEMALE_MODEL = new CambionEntityModel(context.getPart(CambionEntityModel.FEMALE_CAMBION_MODEL_LAYER));
	}

	@Override
	public Identifier getTexture(CambionEntity entity) {
		if (MALE_TEXTURES == null) {
			int variants = entity.getVariants();
			MALE_TEXTURES = new Identifier[variants];
			FEMALE_TEXTURES = new Identifier[variants];
			for (int i = 0; i < variants; i++) {
				MALE_TEXTURES[i] = new Identifier(BewitchmentPlus.MODID, "textures/entity/cambion/male_" + i + ".png");
				FEMALE_TEXTURES[i] = new Identifier(BewitchmentPlus.MODID, "textures/entity/cambion/female_" + i + ".png");
			}
		}
		int variant = entity.getDataTracker().get(BWHostileEntity.VARIANT);
		return entity.getDataTracker().get(CambionEntity.MALE) ? MALE_TEXTURES[variant] : FEMALE_TEXTURES[variant];
	}

	protected void scale(VillagerEntity villagerEntity, MatrixStack matrixStack, float f) {
		float g = 0.9375f;
		if (villagerEntity.isBaby()) {
			g = (float)((double)g * 0.5);
			this.shadowRadius = 0.25f;
		} else {
			this.shadowRadius = 0.5f;
		}
		matrixStack.scale(g, g, g);
	}

	@Override
	public void render(CambionEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, VertexConsumerProvider bufferIn, int packedLightIn) {
		model = entityIn.getDataTracker().get(CambionEntity.MALE) ? MALE_MODEL : FEMALE_MODEL;
		this.setModelVisibilities(entityIn);
		float g = 0.9375f;
		if (entityIn.isBaby()) {
			g = (float)((double)g * 0.5);
			this.shadowRadius = 0.25f;
		} else {
			this.shadowRadius = 0.5f;
		}
		matrixStackIn.scale(g, g, g);
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	private void setModelVisibilities(CambionEntity entityIn) {
		BipedEntityModel<CambionEntity> cambionEntityModel = this.getModel();
		ItemStack itemstack = entityIn.getMainHandStack();
		ItemStack itemstack1 = entityIn.getOffHandStack();
		cambionEntityModel.setVisible(true);
		BipedEntityModel.ArmPose bipedmodel$armpose = this.getArmPose(entityIn, itemstack, itemstack1,
		Hand.MAIN_HAND);
		BipedEntityModel.ArmPose bipedmodel$armpose1 = this.getArmPose(entityIn, itemstack, itemstack1,
		Hand.OFF_HAND);
		cambionEntityModel.sneaking = entityIn.isSneaking();
		if (entityIn.getMainArm() == Arm.RIGHT) {
			cambionEntityModel.rightArmPose = bipedmodel$armpose;
			cambionEntityModel.leftArmPose = bipedmodel$armpose1;
		} else {
			cambionEntityModel.rightArmPose = bipedmodel$armpose1;
			cambionEntityModel.leftArmPose = bipedmodel$armpose;
		}
	}

	private BipedEntityModel.ArmPose getArmPose(CambionEntity entityIn, ItemStack itemStackMain, ItemStack itemStackOff, Hand handIn) {
		BipedEntityModel.ArmPose bipedmodel$armpose = BipedEntityModel.ArmPose.EMPTY;
		ItemStack itemstack = handIn == Hand.MAIN_HAND ? itemStackMain : itemStackOff;
		if (!itemstack.isEmpty()) {
			bipedmodel$armpose = BipedEntityModel.ArmPose.ITEM;
			if (entityIn.getItemUseTimeLeft() > 0) {
				UseAction useaction = itemstack.getUseAction();
				switch (useaction) {
					case BOW:
						bipedmodel$armpose = BipedEntityModel.ArmPose.BOW_AND_ARROW;
						break;
					case SPEAR:
						bipedmodel$armpose = BipedEntityModel.ArmPose.THROW_SPEAR;
						break;
					case CROSSBOW:
						if (handIn == entityIn.getActiveHand()) {
							bipedmodel$armpose = BipedEntityModel.ArmPose.CROSSBOW_CHARGE;
						}
						break;
					default:
						bipedmodel$armpose = BipedEntityModel.ArmPose.EMPTY;
						break;
				}
			} else {
				boolean flag1 = itemStackMain.getItem() instanceof CrossbowItem;
				boolean flag2 = itemStackOff.getItem() instanceof CrossbowItem;
				if (flag1 && entityIn.isAttacking()) {
					bipedmodel$armpose = BipedEntityModel.ArmPose.CROSSBOW_HOLD;
				}

				if (flag2 && itemStackMain.getItem().getUseAction(itemStackMain) == UseAction.NONE
				&& entityIn.isAttacking()) {
					bipedmodel$armpose = BipedEntityModel.ArmPose.CROSSBOW_HOLD;
				}
			}
		}
		return bipedmodel$armpose;
	}
}
