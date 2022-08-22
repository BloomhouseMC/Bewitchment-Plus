package dev.mrsterner.bewitchmentplus.common.registry;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.common.entity.BlackDogEntity;
import dev.mrsterner.bewitchmentplus.common.entity.CambionEntity;
import dev.mrsterner.bewitchmentplus.common.world.feature.LotusTreeFeature;
import dev.mrsterner.bewitchmentplus.common.world.feature.YewTreeFeature;
import moriyashiine.bewitchment.mixin.SpawnRestrictionAccessor;
import net.fabricmc.fabric.api.biome.v1.*;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.tag.BiomeTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.ClampedIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.function.Predicate;

import static net.minecraft.world.gen.feature.VegetationPlacedFeatures.modifiers;


public class BWPWorldGenerators extends ConfiguredFeatures{
    public static final Feature<ProbabilityConfig> YEW_TREE_FEATURE = registerFeature("yew_tree_feature",
            new YewTreeFeature(ProbabilityConfig.CODEC));

    public static final RegistryEntry<ConfiguredFeature<ProbabilityConfig, ?>> YEW_ON_GRASS = ConfiguredFeatures.register("yew_on_grass", YEW_TREE_FEATURE,
            new ProbabilityConfig(0.0F));

    public static final Feature<ProbabilityConfig> LOTUS_TREE_FEATURE = registerFeature("lotus_tree_feature", new LotusTreeFeature(ProbabilityConfig.CODEC));
    public static final RegistryEntry<ConfiguredFeature<ProbabilityConfig, ?>> LOTUS_TREE_IN_JUNGEL = ConfiguredFeatures.register("lotus_in_jungle", LOTUS_TREE_FEATURE,
            new ProbabilityConfig(0.0F));

    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> PATCH_BLOODROOT = ConfiguredFeatures.register("flower_bloodroot", Feature.FLOWER,
            new RandomPatchFeatureConfig(64, 6, 2, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                    new SimpleBlockFeatureConfig(BlockStateProvider.of(BWPObjects.BLOODROOT)))));

    public static final RegistryEntry<PlacedFeature> PATCH_BLOODROOT_DESERT = PlacedFeatures.register("patch_bloodroot_desert",
            PATCH_BLOODROOT,
            RarityFilterPlacementModifier.of(2),
            SquarePlacementModifier.of(),
            CountPlacementModifier.of(ClampedIntProvider.create(UniformIntProvider.create(-3, 1), 0, 1)),
            PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());

    public static final RegistryEntry<PlacedFeature> YEW_LIGHT = PlacedFeatures.register("yew_light",
            YEW_ON_GRASS,
            RarityFilterPlacementModifier.of(4),
            SquarePlacementModifier.of(),
            PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
            BiomePlacementModifier.of());

    public static final RegistryEntry<PlacedFeature> LOTUS_HEAVY = PlacedFeatures.register("lotus_multiple",
        LOTUS_TREE_IN_JUNGEL, modifiers(PlacedFeatures.createCountExtraModifier(4, 0.1F, 1)));

    private static <C extends FeatureConfig, F extends Feature<C>> F registerFeature(String name, F feature) {
        return Registry.register(Registry.FEATURE, name, feature);
    }


    public static void init() {
        BiomeModification worldGen = BiomeModifications.create(new Identifier(BewitchmentPlus.MODID, "world_features"));
        worldGen.add(ModificationPhase.ADDITIONS, BiomeSelectors.tag(ConventionalBiomeTags.EXTREME_HILLS), context -> context.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, YEW_LIGHT.value()));
        worldGen.add(ModificationPhase.ADDITIONS, BiomeSelectors.tag(ConventionalBiomeTags.MESA).or( BiomeSelectors.tag(ConventionalBiomeTags.DESERT)), context -> context.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, PATCH_BLOODROOT_DESERT.value()));
        worldGen.add(ModificationPhase.ADDITIONS, BiomeSelectors.tag(ConventionalBiomeTags.JUNGLE), context -> context.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, LOTUS_HEAVY.value()));


        if (registerEntitySpawn(BWPEntityTypes.CAMBION, foundInOverworld().and(context -> BewitchmentPlus.config.entities.cambionBiomeCategories.contains(context.getBiomeRegistryEntry().value().toString())), BewitchmentPlus.config.entities.cambionWeight, BewitchmentPlus.config.entities.cambionMinGroupCount, BewitchmentPlus.config.entities.cambionMaxGroupCount)) {
            SpawnRestrictionAccessor.callRegister(BWPEntityTypes.CAMBION, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CambionEntity::canMobSpawn);
        }
        if (registerEntitySpawn(BWPEntityTypes.BLACK_DOG, foundInOverworld(), BewitchmentPlus.config.entities.blackDogWeight, BewitchmentPlus.config.entities.blackDogMinGroupCount, BewitchmentPlus.config.entities.blackDogMaxGroupCount)) {
            SpawnRestrictionAccessor.callRegister(BWPEntityTypes.BLACK_DOG, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BlackDogEntity::canMobSpawn);
        }
        //StructureFeatureAccessor.callRegister(BewitchmentPlus.MODID + ":yew_tree_house", new YewTreeHouseStructure(), GenerationStep.Feature.SURFACE_STRUCTURES);

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
