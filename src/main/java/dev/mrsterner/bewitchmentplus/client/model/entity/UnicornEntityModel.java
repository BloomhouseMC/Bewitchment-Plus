package dev.mrsterner.bewitchmentplus.client.model.entity;


import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.common.entity.UnicornEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.HorseEntityModel;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class UnicornEntityModel<T extends UnicornEntity> extends HorseEntityModel<T> {
	public static final EntityModelLayer UNICORN_MODEL_LAYER = new EntityModelLayer(new Identifier(BewitchmentPlus.MODID, "unicorn"), "main");

	public UnicornEntityModel(ModelPart modelPart) {
		super(modelPart);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = HorseEntityModel.getModelData(Dilation.NONE);
		ModelPartData head = modelData.getRoot().getChild("head_parts").getChild("head");
		head.addChild("horn", ModelPartBuilder.create().uv(24, 37).cuboid(-0.5F, -12.0F, -1.5F, 1.0F, 6.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -5.0F, 4.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	public void setAngles(T unicornEntity, float f, float g, float h, float i, float j) {
		super.setAngles(unicornEntity, f, g, h, i, j);
	}
}
