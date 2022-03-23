package dev.mrsterner.bewitchmentplus.common.registry;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.size.FeatureSize;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;

import java.util.LinkedHashMap;
import java.util.Map;

public class BWPEntitySpawns {
	private static final Map<ConfiguredFeature<?, ?>, Identifier> CONFIGURED_FEATURES = new LinkedHashMap<>();

	private static final FeatureSize EMPTY_SIZE = new TwoLayersFeatureSize(0, 0, 0);

	private static <T extends FeatureConfig> ConfiguredFeature<T, ?> register(String name, ConfiguredFeature<T, ?> configuredFeature) {
		CONFIGURED_FEATURES.put(configuredFeature, new Identifier(BewitchmentPlus.MODID, name));
		return configuredFeature;
	}

	public static void init() {
		CONFIGURED_FEATURES.keySet().forEach(configuredFeature -> Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, CONFIGURED_FEATURES.get(configuredFeature), configuredFeature));

		BiomeModifications.addSpawn(BiomeSelectors.foundInOverworld().and(context -> BewitchmentPlus.config.entities.cambionBiomeCategories.contains(Biome.getCategory(context.getBiomeRegistryEntry()).getName())), BWPEntityTypes.CAMBION.getSpawnGroup(), BWPEntityTypes.CAMBION, BewitchmentPlus.config.entities.cambionWeight, BewitchmentPlus.config.entities.cambionMinGroupCount, BewitchmentPlus.config.entities.cambionMaxGroupCount);
		BiomeModifications.addSpawn(BiomeSelectors.foundInOverworld(), BWPEntityTypes.BLACK_DOG.getSpawnGroup(), BWPEntityTypes.BLACK_DOG, BewitchmentPlus.config.entities.blackDogWeight, BewitchmentPlus.config.entities.blackDogMinGroupCount, BewitchmentPlus.config.entities.blackDogMaxGroupCount);
	}
}
