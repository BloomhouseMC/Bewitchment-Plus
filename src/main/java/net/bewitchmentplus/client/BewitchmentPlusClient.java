package net.bewitchmentplus.client;

import net.bewitchmentplus.client.renderer.entity.living.DrudenEntityRenderer;
import net.bewitchmentplus.common.registry.BWPEntityTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;

public class BewitchmentPlusClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.INSTANCE.register(BWPEntityTypes.DRUDEN, (entityRenderDispatcher, context) -> new DrudenEntityRenderer(entityRenderDispatcher));
	}
}