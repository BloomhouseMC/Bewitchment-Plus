package dev.mrsterner.bewitchmentplus.client;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.client.model.entity.BlackDogEntityModel;
import dev.mrsterner.bewitchmentplus.client.model.entity.CambionEntityModel;
import dev.mrsterner.bewitchmentplus.client.renderer.GobletBlockItemRenderer;
import dev.mrsterner.bewitchmentplus.client.renderer.entity.BlackDogEntityRenderer;
import dev.mrsterner.bewitchmentplus.client.renderer.entity.CambionEntityRenderer;
import dev.mrsterner.bewitchmentplus.client.renderer.entity.NifflerEntityRenderer;
import dev.mrsterner.bewitchmentplus.common.block.FleeceBlock;
import dev.mrsterner.bewitchmentplus.common.registry.BWPBlockEntityTypes;
import dev.mrsterner.bewitchmentplus.common.registry.BWPEntityTypes;
import dev.mrsterner.bewitchmentplus.common.registry.BWPObjects;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.*;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.block.Block;
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
			registry.register(new Identifier(BewitchmentPlus.MODID, "block/honey_flow"));
		}));

		BuiltinItemRendererRegistry.INSTANCE.register(BWPObjects.SILVER_GOBLET, renderer);
		BuiltinItemRendererRegistry.INSTANCE.register(BWPObjects.COPPER_GOBLET, renderer);
		BuiltinItemRendererRegistry.INSTANCE.register(BWPObjects.GOLD_GOBLET, renderer);
		BuiltinItemRendererRegistry.INSTANCE.register(BWPObjects.NETHERITE_GOBLET, renderer);
		BlockEntityRendererRegistry.register(BWPBlockEntityTypes.GOBLET, ctx -> new GobletBlockItemRenderer());
		/*
		BuiltinItemRendererRegistry.INSTANCE.register(BWPObjects.SILVER_GOBLET,
		(stack, mode, matrices, vertexConsumers, light, overlay) ->
		MinecraftClient.getInstance().getBlockEntityRenderDispatcher().renderEntity(new GobletBlockEntity(BlockPos.ORIGIN, BWPObjects.SILVER_GOBLET.getDefaultState()), matrices, vertexConsumers, light, overlay));


		 */
		EntityRendererRegistry.register(BWPEntityTypes.NIFFLER, NifflerEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(BLACKDOG_MODEL_LAYER, BlackDogEntityModel::getTexturedModelData);
		EntityRendererRegistry.register(BWPEntityTypes.BLACK_DOG, BlackDogEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(MALE_CAMBION_MODEL_LAYER, CambionEntityModel::getTexturedModelDataMale);
		EntityModelLayerRegistry.registerModelLayer(FEMALE_CAMBION_MODEL_LAYER, CambionEntityModel::getTexturedModelDataFemale);
		EntityRendererRegistry.register(BWPEntityTypes.CAMBION, CambionEntityRenderer::new);

		ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> tintIndex == 1 ? ((FleeceBlock) state.getBlock()).getColor().getFireworkColor() : 0xffffff, BWPObjects.WHITE_FLEECE_CARPET, BWPObjects.ORANGE_FLEECE_CARPET, BWPObjects.MAGENTA_FLEECE_CARPET, BWPObjects.LIGHT_BLUE_FLEECE_CARPET, BWPObjects.YELLOW_FLEECE_CARPET, BWPObjects.LIME_FLEECE_CARPET, BWPObjects.PINK_FLEECE_CARPET, BWPObjects.GRAY_FLEECE_CARPET, BWPObjects.LIGHT_GRAY_FLEECE_CARPET, BWPObjects.CYAN_FLEECE_CARPET, BWPObjects.PURPLE_FLEECE_CARPET, BWPObjects.BLUE_FLEECE_CARPET, BWPObjects.BROWN_FLEECE_CARPET, BWPObjects.GREEN_FLEECE_CARPET, BWPObjects.RED_FLEECE_CARPET, BWPObjects.BLACK_FLEECE_CARPET);
		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex == 1 ? ((FleeceBlock) Block.getBlockFromItem(stack.getItem())).getColor().getFireworkColor() : 0xffffff, BWPObjects.WHITE_FLEECE_CARPET, BWPObjects.ORANGE_FLEECE_CARPET, BWPObjects.MAGENTA_FLEECE_CARPET, BWPObjects.LIGHT_BLUE_FLEECE_CARPET, BWPObjects.YELLOW_FLEECE_CARPET, BWPObjects.LIME_FLEECE_CARPET, BWPObjects.PINK_FLEECE_CARPET, BWPObjects.GRAY_FLEECE_CARPET, BWPObjects.LIGHT_GRAY_FLEECE_CARPET, BWPObjects.CYAN_FLEECE_CARPET, BWPObjects.PURPLE_FLEECE_CARPET, BWPObjects.BLUE_FLEECE_CARPET, BWPObjects.BROWN_FLEECE_CARPET, BWPObjects.GREEN_FLEECE_CARPET, BWPObjects.RED_FLEECE_CARPET, BWPObjects.BLACK_FLEECE_CARPET);
		ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> tintIndex == 1 ? ((FleeceBlock) state.getBlock()).getColor().getFireworkColor() : 0xffffff, BWPObjects.WHITE_FLEECE, BWPObjects.ORANGE_FLEECE, BWPObjects.MAGENTA_FLEECE, BWPObjects.LIGHT_BLUE_FLEECE, BWPObjects.YELLOW_FLEECE, BWPObjects.LIME_FLEECE, BWPObjects.PINK_FLEECE, BWPObjects.GRAY_FLEECE, BWPObjects.LIGHT_GRAY_FLEECE, BWPObjects.CYAN_FLEECE, BWPObjects.PURPLE_FLEECE, BWPObjects.BLUE_FLEECE, BWPObjects.BROWN_FLEECE, BWPObjects.GREEN_FLEECE, BWPObjects.RED_FLEECE, BWPObjects.BLACK_FLEECE);
		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex == 1 ? ((FleeceBlock) Block.getBlockFromItem(stack.getItem())).getColor().getFireworkColor() : 0xffffff, BWPObjects.WHITE_FLEECE, BWPObjects.ORANGE_FLEECE, BWPObjects.MAGENTA_FLEECE, BWPObjects.LIGHT_BLUE_FLEECE, BWPObjects.YELLOW_FLEECE, BWPObjects.LIME_FLEECE, BWPObjects.PINK_FLEECE, BWPObjects.GRAY_FLEECE, BWPObjects.LIGHT_GRAY_FLEECE, BWPObjects.CYAN_FLEECE, BWPObjects.PURPLE_FLEECE, BWPObjects.BLUE_FLEECE, BWPObjects.BROWN_FLEECE, BWPObjects.GREEN_FLEECE, BWPObjects.RED_FLEECE, BWPObjects.BLACK_FLEECE);
	}
}