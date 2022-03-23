package dev.mrsterner.bewitchmentplus.common.registry;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.common.world.structures.YewTreeStructure;
import dev.mrsterner.bewitchmentplus.mixin.common.StructureFeatureAccessor;
import net.minecraft.util.math.intprovider.ClampedIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;


public class BWPWorldGenerators extends ConfiguredFeatures{

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


    public static StructureFeature<?> YEW_TREE = new YewTreeStructure();

    public static void init() {
        StructureFeatureAccessor.callRegister(BewitchmentPlus.MODID + ":yew_tree", YEW_TREE, GenerationStep.Feature.SURFACE_STRUCTURES);
    }
}
