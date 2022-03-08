package dev.mrsterner.bewitchmentplus.common.registry;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.mixin.common.SimpleBlockStateProviderMixin;
import net.fabricmc.fabric.api.biome.v1.BiomeModification;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.RarityFilterPlacementModifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.FeatureSize;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.SpruceFoliagePlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

public class BWPWorldGenerators {
    private static final FeatureSize EMPTY_SIZE = new TwoLayersFeatureSize(0, 0, 0);

    public static final ConfiguredFeature<TreeFeatureConfig, ?> YEW_TREE = Feature.TREE.configure(new TreeFeatureConfig.Builder(SimpleBlockStateProviderMixin.callInit(BWPObjects.YEW_LOG.getDefaultState()), new StraightTrunkPlacer(12, 0, 1),
    SimpleBlockStateProviderMixin.callInit(BWPObjects.YEW_LEAVES.getDefaultState()), new SpruceFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(0), ConstantIntProvider.create(1)), EMPTY_SIZE).ignoreVines().build());

    public static final PlacedFeature YEW_TREE_WITH_CHANCE = YEW_TREE.withPlacement(VegetationPlacedFeatures.modifiersWithWouldSurvive(RarityFilterPlacementModifier.of(10), BWPObjects.YEW_SAPLING));


    public static void init() {
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(BewitchmentPlus.MODID, "yew_tree"), YEW_TREE);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(BewitchmentPlus.MODID, "yew_tree"), YEW_TREE_WITH_CHANCE);
        BiomeModification worldGen = BiomeModifications.create(new Identifier(BewitchmentPlus.MODID, "world_features"));
        worldGen.add(ModificationPhase.ADDITIONS, BiomeSelectors.categories(Biome.Category.FOREST), context -> context.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, YEW_TREE_WITH_CHANCE));

    }
}
