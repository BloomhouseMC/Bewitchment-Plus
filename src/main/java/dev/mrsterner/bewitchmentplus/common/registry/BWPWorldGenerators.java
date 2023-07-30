package dev.mrsterner.bewitchmentplus.common.registry;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.common.BWPConfig;
import dev.mrsterner.bewitchmentplus.common.entity.BlackDogEntity;
import dev.mrsterner.bewitchmentplus.common.entity.CambionEntity;
import dev.mrsterner.bewitchmentplus.common.entity.LeshonEntity;
import net.fabricmc.fabric.api.biome.v1.*;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.*;

import java.util.function.Predicate;


public class BWPWorldGenerators {

    public static final RegistryKey<ConfiguredFeature<?,?>> YEW = ConfiguredFeatures.of(BewitchmentPlus.MODID + ":yew");
    public static final RegistryKey<PlacedFeature> TREE_YEW = PlacedFeatures.of(BewitchmentPlus.MODID + ":tree_yew");


    public static void init() {
        BiomeModifications.addFeature(BiomeSelectors.tag(ConventionalBiomeTags.EXTREME_HILLS), GenerationStep.Feature.VEGETAL_DECORATION, TREE_YEW);


        if (registerEntitySpawn(BWPEntityTypes.CAMBION, foundInOverworld().and(context -> BWPConfig.cambionBiomeCategories.contains(context.getBiomeRegistryEntry().value().toString())), BWPConfig.cambionWeight, BWPConfig.cambionMinGroupCount, BWPConfig.cambionMaxGroupCount)) {
            SpawnRestriction.register(BWPEntityTypes.CAMBION, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CambionEntity::canMobSpawn);
        }
        if (registerEntitySpawn(BWPEntityTypes.BLACK_DOG, foundInOverworld(), BWPConfig.blackDogWeight, BWPConfig.blackDogMinGroupCount, BWPConfig.blackDogMaxGroupCount)) {
            SpawnRestriction.register(BWPEntityTypes.BLACK_DOG, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BlackDogEntity::canMobSpawn);
        }
        if (registerEntitySpawn(BWPEntityTypes.LESHON, BiomeSelectors.foundInOverworld().and(BiomeSelectors.tag(ConventionalBiomeTags.MOUNTAIN).or(BiomeSelectors.tag(ConventionalBiomeTags.EXTREME_HILLS))), 2, 1, 1)) {
            SpawnRestriction.register(BWPEntityTypes.LESHON, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, LeshonEntity::canSpawnInDark);
        }
    }

    private static boolean registerEntitySpawn(EntityType<?> type, Predicate<BiomeSelectionContext> predicate, int weight, int minGroupSize, int maxGroupSize) {
        if (weight < 0) {
            throw new UnsupportedOperationException("Could not register entity type " + type.getTranslationKey() + ": weight " + weight + " cannot be negative.");
        } else if (minGroupSize < 0) {
            throw new UnsupportedOperationException("Could not register entity type " + type.getTranslationKey() + ": minGroupSize " + minGroupSize + " cannot be negative.");
        } else if (maxGroupSize < 0) {
            throw new UnsupportedOperationException("Could not register entity type " + type.getTranslationKey() + ": maxGroupSize " + maxGroupSize + " cannot be negative.");
        } else if (minGroupSize > maxGroupSize) {
            throw new UnsupportedOperationException("Could not register entity type " + type.getTranslationKey() + ": minGroupSize " + minGroupSize + " cannot be greater than maxGroupSize " + maxGroupSize + ".");
        } else if (weight == 0 || minGroupSize == 0) {
            return false;
        }
        BiomeModifications.addSpawn(predicate, type.getSpawnGroup(), type, weight, minGroupSize, maxGroupSize);
        return true;
    }

    private static Predicate<BiomeSelectionContext> foundInOverworld() {
        return context -> {
            RegistryEntry<Biome> biome = context.getBiomeRegistryEntry();
            return biome.isIn(ConventionalBiomeTags.IN_OVERWORLD);
        };
    }
}
