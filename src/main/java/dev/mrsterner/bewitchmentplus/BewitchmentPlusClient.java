package dev.mrsterner.bewitchmentplus;

import dev.mrsterner.bewitchmentplus.client.model.entity.UnicornEntityModel;
import dev.mrsterner.bewitchmentplus.client.model.entity.BlackDogEntityModel;
import dev.mrsterner.bewitchmentplus.client.model.entity.CambionEntityModel;
import dev.mrsterner.bewitchmentplus.client.renderer.*;
import dev.mrsterner.bewitchmentplus.client.renderer.entity.*;
import dev.mrsterner.bewitchmentplus.common.block.yew.YewChestBlockEntity;
import dev.mrsterner.bewitchmentplus.common.registry.BWPBlockEntityTypes;
import dev.mrsterner.bewitchmentplus.common.registry.BWPEntityTypes;
import dev.mrsterner.bewitchmentplus.common.registry.BWPObjects;
import dev.mrsterner.bewitchmentplus.common.utils.SpriteIdentifierRegistry;
import moriyashiine.bewitchment.common.block.entity.BWChestBlockEntity;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.*;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.ChestBlockEntityRenderer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;



public class BewitchmentPlusClient implements ClientModInitializer {
	private final BuiltinItemRendererRegistry.DynamicItemRenderer renderer = new GobletBlockItemRenderer();
	private final BuiltinItemRendererRegistry.DynamicItemRenderer statueRenderer = new StatueRenderer();

	@Override
	public void onInitializeClient() {
		ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> view != null && pos != null ? BiomeColors.getFoliageColor(view, pos) : FoliageColors.getDefaultColor(), BWPObjects.BLOODROOT, BWPObjects.EMBERGRASS);
		GeoItemRenderer.registerItemRenderer(BWPObjects.DRAGONBLOOD_STAFF, new DragonbloodStaffRenderer());
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), BWPObjects.GOLD_STANDING_CANDELABRA,BWPObjects.NETHERITE_STANDING_CANDELABRA, BWPObjects.SILVER_STANDING_CANDELABRA, BWPObjects.PENTACLE, BWPObjects.BLOODROOT, BWPObjects.EMBERGRASS, BWPObjects.YEW_SAPLING, BWPObjects.YEW_DOOR, BWPObjects.YEW_TRAPDOOR, BWPObjects.YEW_CUT_LOG);
		ClientTickEvents.END_CLIENT_TICK.register(ClientTickHandler::clientTickEnd);

		BlockEntityRendererRegistry.register(BWPBlockEntityTypes.GOBLET, ctx -> new GobletBlockItemRenderer());
		BlockEntityRendererRegistry.register(BWPBlockEntityTypes.STATUE_BLOCK_ENTITY, ctx -> new StatueRenderer());
		BlockEntityRendererRegistry.register(BWPBlockEntityTypes.MOONFLOWER_BLOCK_ENTITY, ctx -> new MoonflowerBlockEntityRenderer());
		BlockEntityRendererRegistry.register(BWPBlockEntityTypes.MIMIC_CHEST_BLOCK_ENTITY, MimicBlockEntityRenderer::new);
		BlockEntityRendererRegistry.register(BWPBlockEntityTypes.YEW_CHEST_BLOCK_ENTITY, ChestBlockEntityRenderer::new);
		BlockEntityRendererRegistry.register(BWPBlockEntityTypes.LEECH_CHEST_BLOCK_ENTITY, LeechChestBlockEntityRenderer::new);

		EntityModelLayerRegistry.registerModelLayer(CambionEntityModel.MALE_CAMBION_MODEL_LAYER, CambionEntityModel::getTexturedModelDataMale);
		EntityModelLayerRegistry.registerModelLayer(CambionEntityModel.FEMALE_CAMBION_MODEL_LAYER, CambionEntityModel::getTexturedModelDataFemale);
		EntityModelLayerRegistry.registerModelLayer(MimicBlockEntityRenderer.MIMIC_LAYER, MimicBlockEntityRenderer::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(BlackDogEntityModel.BLACKDOG_MODEL_LAYER, BlackDogEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(UnicornEntityModel.UNICORN_MODEL_LAYER, UnicornEntityModel::getTexturedModelData);

		EntityRendererRegistry.register(BWPEntityTypes.MUTANDIS_ENTITY_ENTITY_TYPE, FlyingItemEntityRenderer::new);
		EntityRendererRegistry.register(BWPEntityTypes.NIFFLER, NifflerEntityRenderer::new);
		EntityRendererRegistry.register(BWPEntityTypes.LESHON, LeshonEntityRenderer::new);
		EntityRendererRegistry.register(BWPEntityTypes.BLACK_DOG, BlackDogEntityRenderer::new);
		EntityRendererRegistry.register(BWPEntityTypes.UNICORN, UnicornEntityRenderer::new);
		EntityRendererRegistry.register(BWPEntityTypes.EFFIGY, EffigyEntityRenderer::new);
		EntityRendererRegistry.register(BWPEntityTypes.YEW_BROOM, YewBroomEntityRenderer::new);
		EntityRendererRegistry.register(BWPEntityTypes.CAMBION, CambionEntityRenderer::new);
		EntityRendererRegistry.register(BWPEntityTypes.RUNE, RuneEntityRenderer::new);
		EntityRendererRegistry.register(BWPEntityTypes.PHOENIX, PhoenixEntityRenderer::new);

		SpriteIdentifierRegistry.INSTANCE.addIdentifier(SpriteIdentifierRegistry.YEW_CHEST);
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(SpriteIdentifierRegistry.TRAPPED_YEW_CHEST);
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(SpriteIdentifierRegistry.YEW_CHEST_LEFT);
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(SpriteIdentifierRegistry.TRAPPED_YEW_CHEST_LEFT);
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(SpriteIdentifierRegistry.YEW_CHEST_RIGHT);
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(SpriteIdentifierRegistry.TRAPPED_YEW_CHEST_RIGHT);
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(GobletBlockItemRenderer.BLOOD);
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(GobletBlockItemRenderer.UNICORN);
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(GobletBlockItemRenderer.HONEY);
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(MimicBlockEntityRenderer.MIMIC_SPRITE);
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(SpriteIdentifierRegistry.LEECH_CHEST);

		BuiltinItemRendererRegistry.INSTANCE.register(BWPObjects.SILVER_GOBLET, renderer);
		BuiltinItemRendererRegistry.INSTANCE.register(BWPObjects.GOLD_GOBLET, renderer);
		BuiltinItemRendererRegistry.INSTANCE.register(BWPObjects.NETHERITE_GOBLET, renderer);
		BuiltinItemRendererRegistry.INSTANCE.register(BWPObjects.LESHON_SKULL, new LeshonSkullRenderer());
		BuiltinItemRendererRegistry.INSTANCE.register(BWPObjects.LILITH_STATUE_BLACKSTONE, statueRenderer);
		BuiltinItemRendererRegistry.INSTANCE.register(BWPObjects.LILITH_STATUE_GOLD, statueRenderer);
		BuiltinItemRendererRegistry.INSTANCE.register(BWPObjects.LILITH_STATUE_NETHERBRICK, statueRenderer);
		BuiltinItemRendererRegistry.INSTANCE.register(BWPObjects.HERNE_STATUE_BLACKSTONE, statueRenderer);
		BuiltinItemRendererRegistry.INSTANCE.register(BWPObjects.HERNE_STATUE_GOLD, statueRenderer);
		BuiltinItemRendererRegistry.INSTANCE.register(BWPObjects.HERNE_STATUE_NETHERBRICK, statueRenderer);
		BuiltinItemRendererRegistry.INSTANCE.register(BWPObjects.BAPHOMET_STATUE_BLACKSTONE, statueRenderer);
		BuiltinItemRendererRegistry.INSTANCE.register(BWPObjects.BAPHOMET_STATUE_GOLD, statueRenderer);
		BuiltinItemRendererRegistry.INSTANCE.register(BWPObjects.BAPHOMET_STATUE_NETHERBRICK, statueRenderer);
		BuiltinItemRendererRegistry.INSTANCE.register(BWPObjects.LEONARD_STATUE_BLACKSTONE, statueRenderer);
		BuiltinItemRendererRegistry.INSTANCE.register(BWPObjects.LEONARD_STATUE_GOLD, statueRenderer);
		BuiltinItemRendererRegistry.INSTANCE.register(BWPObjects.LEONARD_STATUE_NETHERBRICK, statueRenderer);
		BuiltinItemRendererRegistry.INSTANCE.register(BWPObjects.YEW_CHEST, (stack, mode, matrices, vertexConsumers, light, overlay) -> MinecraftClient.getInstance().getBlockEntityRenderDispatcher().renderEntity(new YewChestBlockEntity(BWPBlockEntityTypes.YEW_CHEST_BLOCK_ENTITY, BlockPos.ORIGIN, BWPObjects.YEW_CHEST.getDefaultState(), YewChestBlockEntity.Type.YEW, false), matrices, vertexConsumers, light, overlay));
		BuiltinItemRendererRegistry.INSTANCE.register(BWPObjects.TRAPPED_YEW_CHEST, (stack, mode, matrices, vertexConsumers, light, overlay) -> MinecraftClient.getInstance().getBlockEntityRenderDispatcher().renderEntity(new YewChestBlockEntity(BWPBlockEntityTypes.YEW_CHEST_BLOCK_ENTITY, BlockPos.ORIGIN, BWPObjects.YEW_CHEST.getDefaultState(), YewChestBlockEntity.Type.YEW, true), matrices, vertexConsumers, light, overlay));
		BuiltinItemRendererRegistry.INSTANCE.register(BWPObjects.MIMIC_CHEST, (stack, mode, matrices, vertexConsumers, light, overlay) -> MinecraftClient.getInstance().getBlockEntityRenderDispatcher().renderEntity(new BWChestBlockEntity(BlockEntityType.CHEST, BlockPos.ORIGIN, Blocks.CHEST.getDefaultState(), BWChestBlockEntity.Type.CYPRESS, false), matrices, vertexConsumers, light, overlay));



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