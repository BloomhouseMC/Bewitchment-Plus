package dev.mrsterner.bewitchmentplus;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.client.model.entity.BlackDogEntityModel;
import dev.mrsterner.bewitchmentplus.client.model.entity.CambionEntityModel;
import dev.mrsterner.bewitchmentplus.client.renderer.*;
import dev.mrsterner.bewitchmentplus.client.renderer.entity.*;
import dev.mrsterner.bewitchmentplus.common.block.blockentity.MimicChestBlockEntity;
import dev.mrsterner.bewitchmentplus.common.registry.BWPBlockEntityTypes;
import dev.mrsterner.bewitchmentplus.common.registry.BWPEntityTypes;
import dev.mrsterner.bewitchmentplus.common.registry.BWPObjects;
import dev.mrsterner.bewitchmentplus.common.registry.SpriteIdentifierRegistry;
import moriyashiine.bewitchment.common.block.entity.BWChestBlockEntity;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.*;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.ChestBlockEntityRenderer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

import static dev.mrsterner.bewitchmentplus.client.renderer.MimicBlockEntityRenderer.MIMIC_LAYER;


public class BewitchmentPlusClient implements ClientModInitializer {
	public static final EntityModelLayer BLACKDOG_MODEL_LAYER = new EntityModelLayer(new Identifier(BewitchmentPlus.MODID, "blackdog"), "blackdog");
	public static final EntityModelLayer MALE_CAMBION_MODEL_LAYER = new EntityModelLayer(new Identifier(BewitchmentPlus.MODID, "male_cambion"), "male_cambion");
	public static final EntityModelLayer FEMALE_CAMBION_MODEL_LAYER = new EntityModelLayer(new Identifier(BewitchmentPlus.MODID, "female_cambion"), "female_cambion");
	private final BuiltinItemRendererRegistry.DynamicItemRenderer renderer = new GobletBlockItemRenderer();

	@Override
	public void onInitializeClient() {
		ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> registry.register(new Identifier(BewitchmentPlus.MODID, "block/honey_fluid"))));
		EntityRendererRegistry.register(BWPEntityTypes.MUTANDIS_ENTITY_ENTITY_TYPE, FlyingItemEntityRenderer::new);
		BlockEntityRendererRegistry.register(BWPBlockEntityTypes.GOBLET, ctx -> new GobletBlockItemRenderer());
		BlockEntityRendererRegistry.register(BWPBlockEntityTypes.MOONFLOWER_BLOCK_ENTITY, ctx -> new MoonflowerBlockEntityRenderer());
		BlockEntityRendererRegistry.register(BWPBlockEntityTypes.MIMIC_CHEST_BLOCK_ENTITY, MimicBlockEntityRenderer::new);
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(GobletBlockItemRenderer.BLOOD);
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(GobletBlockItemRenderer.HONEY);
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(MimicBlockEntityRenderer.MIMIC_SPRITE);
		EntityRendererRegistry.register(BWPEntityTypes.NIFFLER, NifflerEntityRenderer::new);
		EntityRendererRegistry.register(BWPEntityTypes.LESHON, LeshonEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(BLACKDOG_MODEL_LAYER, BlackDogEntityModel::getTexturedModelData);
		EntityRendererRegistry.register(BWPEntityTypes.BLACK_DOG, BlackDogEntityRenderer::new);
		EntityRendererRegistry.register(BWPEntityTypes.EFFIGY, EffigyEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(MALE_CAMBION_MODEL_LAYER, CambionEntityModel::getTexturedModelDataMale);
		EntityModelLayerRegistry.registerModelLayer(FEMALE_CAMBION_MODEL_LAYER, CambionEntityModel::getTexturedModelDataFemale);
		EntityModelLayerRegistry.registerModelLayer(MIMIC_LAYER, MimicBlockEntityRenderer::getTexturedModelData);
		EntityRendererRegistry.register(BWPEntityTypes.CAMBION, CambionEntityRenderer::new);
		EntityRendererRegistry.register(BWPEntityTypes.RUNE, RuneEntityRenderer::new);
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), BWPObjects.PENTACLE, BWPObjects.BLOODROOT);
		GeoItemRenderer.registerItemRenderer(BWPObjects.DRAGONBLOOD_STAFF, new DragonbloodStaffRenderer());

		ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> view != null && pos != null ? BiomeColors.getFoliageColor(view, pos) : FoliageColors.getDefaultColor(), BWPObjects.BLOODROOT);

		BuiltinItemRendererRegistry.INSTANCE.register(BWPObjects.SILVER_GOBLET, renderer);
		BuiltinItemRendererRegistry.INSTANCE.register(BWPObjects.GOLD_GOBLET, renderer);
		BuiltinItemRendererRegistry.INSTANCE.register(BWPObjects.NETHERITE_GOBLET, renderer);
		BuiltinItemRendererRegistry.INSTANCE.register(BWPObjects.MIMIC_CHEST, (stack, mode, matrices, vertexConsumers, light, overlay) -> MinecraftClient.getInstance().getBlockEntityRenderDispatcher().renderEntity(new BWChestBlockEntity(BlockEntityType.CHEST, BlockPos.ORIGIN, Blocks.CHEST.getDefaultState(), BWChestBlockEntity.Type.CYPRESS, false), matrices, vertexConsumers, light, overlay));

		ClientTickEvents.END_CLIENT_TICK.register(ClientTickHandler::clientTickEnd);

	}

	public static final class ClientTickHandler {
		private ClientTickHandler() {
		}

		public static int ticksInGame = 0;
		public static float partialTicks = 0;
		public static float delta = 0;
		public static float total = 0;

		public static void calcDelta() {
			float oldTotal = total;
			total = ticksInGame + partialTicks;
			delta = total - oldTotal;
		}

		public static void renderTick(float renderTickTime) {
			partialTicks = renderTickTime;
		}

		public static void clientTickEnd(MinecraftClient mc) {
			if (!mc.isPaused()) {
				ticksInGame++;
				partialTicks = 0;
			}
			calcDelta();
		}
	}
}