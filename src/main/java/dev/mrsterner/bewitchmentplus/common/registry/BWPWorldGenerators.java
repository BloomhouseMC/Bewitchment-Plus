package dev.mrsterner.bewitchmentplus.common.registry;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.common.world.feature.YewTreeFeature;
import dev.mrsterner.bewitchmentplus.common.world.structure.YewTreeHouseStructure;
import dev.mrsterner.bewitchmentplus.mixin.common.StructureFeatureAccessor;
import net.minecraft.util.math.intprovider.ClampedIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;


public class BWPWorldGenerators extends ConfiguredFeatures{
    public static final Feature<ProbabilityConfig> YEW_TREE_FEATURE = registerFeature("yew_tree_feature", new YewTreeFeature(ProbabilityConfig.CODEC));
    public static final RegistryEntry<ConfiguredFeature<ProbabilityConfig, ?>> YEW_ON_GRASS = ConfiguredFeatures.register("yew_on_grass", YEW_TREE_FEATURE, new ProbabilityConfig(0.0F));

    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> PATCH_BLOODROOT = ConfiguredFeatures.register(
    "flower_bloodroot",
    Feature.FLOWER,
        new RandomPatchFeatureConfig(
          64, 6, 2,
            PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
            new SimpleBlockFeatureConfig(BlockStateProvider.of(BWPObjects.BLOODROOT)))));


    public static final RegistryEntry<PlacedFeature> PATCH_BLOODROOT_DESERT = PlacedFeatures.register(
    "patch_bloodroot_desert",
        PATCH_BLOODROOT,
            RarityFilterPlacementModifier.of(2),
            SquarePlacementModifier.of(),
            CountPlacementModifier.of(ClampedIntProvider.create(UniformIntProvider.create(-3, 1), 0, 1)),
            PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
            BiomePlacementModifier.of());

    public static final RegistryEntry<PlacedFeature> YEW_LIGHT = PlacedFeatures.register(
        "yew_light",
        YEW_ON_GRASS,
            RarityFilterPlacementModifier.of(4),
            SquarePlacementModifier.of(),
            PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
            BiomePlacementModifier.of());

    private static <C extends FeatureConfig, F extends Feature<C>> F registerFeature(String name, F feature) {
        return Registry.register(Registry.FEATURE, name, feature);
    }

    public static StructureFeature<?> YEW_TREE_HOUSE = new YewTreeHouseStructure();

    public static void init() {
        StructureFeatureAccessor.callRegister(BewitchmentPlus.MODID + ":yew_tree_house", YEW_TREE_HOUSE, GenerationStep.Feature.SURFACE_STRUCTURES);
    }
}
