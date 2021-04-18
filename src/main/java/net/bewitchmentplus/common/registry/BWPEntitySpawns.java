package net.bewitchmentplus.common.registry;

import net.bewitchmentplus.BewitchmentPlus;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.size.FeatureSize;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;

import java.util.LinkedHashMap;
import java.util.Map;

public class BWPEntitySpawns {
	private static final Map<ConfiguredFeature<?, ?>, Identifier> CONFIGURED_FEATURES = new LinkedHashMap<>();

	private static final FeatureSize EMPTY_SIZE = new TwoLayersFeatureSize(0, 0, 0);

	private static <T extends FeatureConfig> ConfiguredFeature<T, ?> create(String name, ConfiguredFeature<T, ?> configuredFeature) {
		CONFIGURED_FEATURES.put(configuredFeature, new Identifier(BewitchmentPlus.MODID, name));
		return configuredFeature;
	}

	public static void init() {
		CONFIGURED_FEATURES.keySet().forEach(configuredFeature -> Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, CONFIGURED_FEATURES.get(configuredFeature), configuredFeature));

		BiomeModifications.addSpawn(BiomeSelectors.foundInOverworld(), BWPEntityTypes.BLACK_DOG.getSpawnGroup(), BWPEntityTypes.BLACK_DOG, BewitchmentPlus.config.blackDogWeight, BewitchmentPlus.config.blackDogMinGroupCount, BewitchmentPlus.config.blackDogMaxGroupCount);
		BiomeModifications.addSpawn(BiomeSelectors.foundInOverworld().and(context -> BewitchmentPlus.config.drudenBiomeCategories.contains(context.getBiome().getCategory().getName())), BWPEntityTypes.DRUDEN.getSpawnGroup(), BWPEntityTypes.DRUDEN, BewitchmentPlus.config.drudenWeight, BewitchmentPlus.config.drudenMinGroupCount, BewitchmentPlus.config.drudenMaxGroupCount);
	}
}
