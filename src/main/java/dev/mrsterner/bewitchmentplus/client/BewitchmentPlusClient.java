package dev.mrsterner.bewitchmentplus.client;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.client.model.entity.living.BlackDogEntityModel;
import dev.mrsterner.bewitchmentplus.client.model.entity.living.CambionEntityModel;
import dev.mrsterner.bewitchmentplus.client.renderer.entity.living.BlackDogEntityRenderer;
import dev.mrsterner.bewitchmentplus.client.renderer.entity.living.CambionEntityRenderer;
import dev.mrsterner.bewitchmentplus.common.registry.BWPEntityTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class BewitchmentPlusClient implements ClientModInitializer {
	public static final EntityModelLayer BLACKDOG_MODEL_LAYER = new EntityModelLayer(new Identifier(BewitchmentPlus.MODID, "blackdog"), "blackdog");
	public static final EntityModelLayer MALE_CAMBION_MODEL_LAYER = new EntityModelLayer(new Identifier(BewitchmentPlus.MODID, "male_cambion"), "male_cambion");
	public static final EntityModelLayer FEMALE_CAMBION_MODEL_LAYER = new EntityModelLayer(new Identifier(BewitchmentPlus.MODID, "female_cambion"), "female_cambion");

	@Override
	public void onInitializeClient() {

		EntityModelLayerRegistry.registerModelLayer(BLACKDOG_MODEL_LAYER, BlackDogEntityModel::getTexturedModelData);
		EntityRendererRegistry.INSTANCE.register(BWPEntityTypes.BLACK_DOG, BlackDogEntityRenderer::new);

		EntityModelLayerRegistry.registerModelLayer(MALE_CAMBION_MODEL_LAYER, CambionEntityModel::getTexturedModelDataMale);
		EntityModelLayerRegistry.registerModelLayer(FEMALE_CAMBION_MODEL_LAYER, CambionEntityModel::getTexturedModelDataFemale);
		EntityRendererRegistry.INSTANCE.register(BWPEntityTypes.CAMBION, CambionEntityRenderer::new);


	}
}