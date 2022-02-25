package dev.mrsterner.bewitchmentplus.client;

import com.terraformersmc.terraform.sign.SpriteIdentifierRegistry;
import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.client.model.entity.living.BlackDogEntityModel;
import dev.mrsterner.bewitchmentplus.client.model.entity.living.CambionEntityModel;
import dev.mrsterner.bewitchmentplus.client.renderer.entity.BlackDogEntityRenderer;
import dev.mrsterner.bewitchmentplus.client.renderer.entity.CambionEntityRenderer;
import dev.mrsterner.bewitchmentplus.client.renderer.entity.GobletBlockEntityRenderer;
import dev.mrsterner.bewitchmentplus.common.block.FleeceBlock;
import dev.mrsterner.bewitchmentplus.common.block.GobletBlock;
import dev.mrsterner.bewitchmentplus.common.registry.BWPBlockEntityTypes;
import dev.mrsterner.bewitchmentplus.common.registry.BWPEntityTypes;
import dev.mrsterner.bewitchmentplus.common.registry.BWPObjects;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.block.Block;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;

import static dev.mrsterner.bewitchmentplus.client.renderer.entity.GobletBlockEntityRenderer.HONEY_SPRITE;

public class BewitchmentPlusClient implements ClientModInitializer {
	public static final EntityModelLayer BLACKDOG_MODEL_LAYER = new EntityModelLayer(new Identifier(BewitchmentPlus.MODID, "blackdog"), "blackdog");
	public static final EntityModelLayer MALE_CAMBION_MODEL_LAYER = new EntityModelLayer(new Identifier(BewitchmentPlus.MODID, "male_cambion"), "male_cambion");
	public static final EntityModelLayer FEMALE_CAMBION_MODEL_LAYER = new EntityModelLayer(new Identifier(BewitchmentPlus.MODID, "female_cambion"), "female_cambion");

	@Override
	public void onInitializeClient() {
		ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
			registry.register(new Identifier(BewitchmentPlus.MODID, "block/honey_flow"));
		}));

		EntityModelLayerRegistry.registerModelLayer(BLACKDOG_MODEL_LAYER, BlackDogEntityModel::getTexturedModelData);
		EntityRendererRegistry.INSTANCE.register(BWPEntityTypes.BLACK_DOG, BlackDogEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(MALE_CAMBION_MODEL_LAYER, CambionEntityModel::getTexturedModelDataMale);
		EntityModelLayerRegistry.registerModelLayer(FEMALE_CAMBION_MODEL_LAYER, CambionEntityModel::getTexturedModelDataFemale);
		EntityRendererRegistry.INSTANCE.register(BWPEntityTypes.CAMBION, CambionEntityRenderer::new);
		BlockEntityRendererRegistry.register(BWPBlockEntityTypes.GOBLET, ctx -> new GobletBlockEntityRenderer());
		//ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> tintIndex == 1 ? ((FleeceBlock) state.getBlock()).getColor().getFireworkColor() : 0xffffff, BWPObjects.WHITE_FLEECE_CARPET, BWPObjects.ORANGE_FLEECE_CARPET, BWPObjects.MAGENTA_FLEECE_CARPET, BWPObjects.LIGHT_BLUE_FLEECE_CARPET, BWPObjects.YELLOW_FLEECE_CARPET, BWPObjects.LIME_FLEECE_CARPET, BWPObjects.PINK_FLEECE_CARPET, BWPObjects.GRAY_FLEECE_CARPET, BWPObjects.LIGHT_GRAY_FLEECE_CARPET, BWPObjects.CYAN_FLEECE_CARPET, BWPObjects.PURPLE_FLEECE_CARPET, BWPObjects.BLUE_FLEECE_CARPET, BWPObjects.BROWN_FLEECE_CARPET, BWPObjects.GREEN_FLEECE_CARPET, BWPObjects.RED_FLEECE_CARPET, BWPObjects.BLACK_FLEECE_CARPET);
		//ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex == 1 ? ((FleeceBlock) Block.getBlockFromItem(stack.getItem())).getColor().getFireworkColor() : 0xffffff, BWPObjects.WHITE_FLEECE_CARPET, BWPObjects.ORANGE_FLEECE_CARPET, BWPObjects.MAGENTA_FLEECE_CARPET, BWPObjects.LIGHT_BLUE_FLEECE_CARPET, BWPObjects.YELLOW_FLEECE_CARPET, BWPObjects.LIME_FLEECE_CARPET, BWPObjects.PINK_FLEECE_CARPET, BWPObjects.GRAY_FLEECE_CARPET, BWPObjects.LIGHT_GRAY_FLEECE_CARPET, BWPObjects.CYAN_FLEECE_CARPET, BWPObjects.PURPLE_FLEECE_CARPET, BWPObjects.BLUE_FLEECE_CARPET, BWPObjects.BROWN_FLEECE_CARPET, BWPObjects.GREEN_FLEECE_CARPET, BWPObjects.RED_FLEECE_CARPET, BWPObjects.BLACK_FLEECE_CARPET);
		ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> tintIndex == 1 ? ((FleeceBlock) state.getBlock()).getColor().getFireworkColor() : 0xffffff, BWPObjects.WHITE_FLEECE, BWPObjects.ORANGE_FLEECE, BWPObjects.MAGENTA_FLEECE, BWPObjects.LIGHT_BLUE_FLEECE, BWPObjects.YELLOW_FLEECE, BWPObjects.LIME_FLEECE, BWPObjects.PINK_FLEECE, BWPObjects.GRAY_FLEECE, BWPObjects.LIGHT_GRAY_FLEECE, BWPObjects.CYAN_FLEECE, BWPObjects.PURPLE_FLEECE, BWPObjects.BLUE_FLEECE, BWPObjects.BROWN_FLEECE, BWPObjects.GREEN_FLEECE, BWPObjects.RED_FLEECE, BWPObjects.BLACK_FLEECE);
		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex == 1 ? ((FleeceBlock) Block.getBlockFromItem(stack.getItem())).getColor().getFireworkColor() : 0xffffff, BWPObjects.WHITE_FLEECE, BWPObjects.ORANGE_FLEECE, BWPObjects.MAGENTA_FLEECE, BWPObjects.LIGHT_BLUE_FLEECE, BWPObjects.YELLOW_FLEECE, BWPObjects.LIME_FLEECE, BWPObjects.PINK_FLEECE, BWPObjects.GRAY_FLEECE, BWPObjects.LIGHT_GRAY_FLEECE, BWPObjects.CYAN_FLEECE, BWPObjects.PURPLE_FLEECE, BWPObjects.BLUE_FLEECE, BWPObjects.BROWN_FLEECE, BWPObjects.GREEN_FLEECE, BWPObjects.RED_FLEECE, BWPObjects.BLACK_FLEECE);
	}
}