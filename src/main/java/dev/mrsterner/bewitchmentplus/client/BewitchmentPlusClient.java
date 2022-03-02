package dev.mrsterner.bewitchmentplus.client;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.client.model.entity.BlackDogEntityModel;
import dev.mrsterner.bewitchmentplus.client.model.entity.CambionEntityModel;
import dev.mrsterner.bewitchmentplus.client.renderer.GobletBlockItemRenderer;
import dev.mrsterner.bewitchmentplus.client.renderer.entity.BlackDogEntityRenderer;
import dev.mrsterner.bewitchmentplus.client.renderer.entity.CambionEntityRenderer;
import dev.mrsterner.bewitchmentplus.client.renderer.entity.NifflerEntityRenderer;
import dev.mrsterner.bewitchmentplus.common.registry.BWPBlockEntityTypes;
import dev.mrsterner.bewitchmentplus.common.registry.BWPEntityTypes;
import dev.mrsterner.bewitchmentplus.common.registry.BWPObjects;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.*;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;


public class BewitchmentPlusClient implements ClientModInitializer {
	public static final EntityModelLayer BLACKDOG_MODEL_LAYER = new EntityModelLayer(new Identifier(BewitchmentPlus.MODID, "blackdog"), "blackdog");
	public static final EntityModelLayer MALE_CAMBION_MODEL_LAYER = new EntityModelLayer(new Identifier(BewitchmentPlus.MODID, "male_cambion"), "male_cambion");
	public static final EntityModelLayer FEMALE_CAMBION_MODEL_LAYER = new EntityModelLayer(new Identifier(BewitchmentPlus.MODID, "female_cambion"), "female_cambion");
	private final BuiltinItemRendererRegistry.DynamicItemRenderer renderer = new GobletBlockItemRenderer();
	@Override
	public void onInitializeClient() {
		ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
			registry.register(new Identifier(BewitchmentPlus.MODID, "block/honey_fluid"));
		}));

		BuiltinItemRendererRegistry.INSTANCE.register(BWPObjects.SILVER_GOBLET, renderer);
		BuiltinItemRendererRegistry.INSTANCE.register(BWPObjects.GOLD_GOBLET, renderer);
		BuiltinItemRendererRegistry.INSTANCE.register(BWPObjects.NETHERITE_GOBLET, renderer);
		BlockEntityRendererRegistry.register(BWPBlockEntityTypes.GOBLET, ctx -> new GobletBlockItemRenderer());
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(GobletBlockItemRenderer.BLOOD);
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(GobletBlockItemRenderer.HONEY);
		EntityRendererRegistry.register(BWPEntityTypes.NIFFLER, NifflerEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(BLACKDOG_MODEL_LAYER, BlackDogEntityModel::getTexturedModelData);
		EntityRendererRegistry.register(BWPEntityTypes.BLACK_DOG, BlackDogEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(MALE_CAMBION_MODEL_LAYER, CambionEntityModel::getTexturedModelDataMale);
		EntityModelLayerRegistry.registerModelLayer(FEMALE_CAMBION_MODEL_LAYER, CambionEntityModel::getTexturedModelDataFemale);
		EntityRendererRegistry.register(BWPEntityTypes.CAMBION, CambionEntityRenderer::new);

	}
}