package dev.mrsterner.bewitchmentplus.common.registry;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.common.world.generator.tree.YewTreeFeature;
import dev.mrsterner.bewitchmentplus.common.world.structure.YewTreeHouseStructure;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.structure.v1.FabricStructureBuilder;
import net.minecraft.structure.PlainsVillageData;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.chunk.StructureConfig;
import net.minecraft.world.gen.decorator.BiomePlacementModifier;
import net.minecraft.world.gen.decorator.RarityFilterPlacementModifier;
import net.minecraft.world.gen.decorator.SquarePlacementModifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public class BWPWorldGenerators extends ConfiguredFeatures{
    public static final ConfiguredFeature<?,?> CONFIGURED_WOLFSBANE = Feature.FLOWER.configure(new RandomPatchFeatureConfig(64, 6, 2, () -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(BlockStateProvider.of(BWPObjects.BLOODROOT))).withInAirFilter()));
    public static final PlacedFeature BLOODROOT_FEATURE = PlacedFeatures.register("bloodroot_feature", CONFIGURED_WOLFSBANE.withPlacement(RarityFilterPlacementModifier.of(32), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of()));


    private static final Feature<DefaultFeatureConfig> FEATURE_BIG_YEW_TREE = new YewTreeFeature(DefaultFeatureConfig.CODEC);
    public static final ConfiguredFeature<?, ?> CONFIGURED_FEATURE_BIG_YEW_TREE = FEATURE_BIG_YEW_TREE.configure(DefaultFeatureConfig.INSTANCE);
    public static final RegistryKey<ConfiguredFeature<?, ?>> KEY_CONFIGURED_FEATURE_BIG_YEW_TREE = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, new Identifier(BewitchmentPlus.MODID, "trees_big_yew"));
    public static final PlacedFeature PLACED_BIG_YEW_TREE = CONFIGURED_FEATURE_BIG_YEW_TREE.withPlacement(VegetationPlacedFeatures.modifiers(PlacedFeatures.createCountExtraModifier(1, 0.1f, 1)));
    public static final RegistryKey<PlacedFeature> KEY_PLACED_BIG_YEW_TREE = RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier(BewitchmentPlus.MODID, "trees_big_yew"));


    public static StructureFeature<StructurePoolFeatureConfig> YEW_TREE_HOUSE = new YewTreeHouseStructure(StructurePoolFeatureConfig.CODEC);
    public static ConfiguredStructureFeature<?, ?> CONFIGURED_YEW_TREE_HOUSE = YEW_TREE_HOUSE.configure(new StructurePoolFeatureConfig(() -> PlainsVillageData.STRUCTURE_POOLS, 0));

    public static void init() {
        //Yew Tree
        Registry.register(Registry.FEATURE, new Identifier(BewitchmentPlus.MODID, "big_yew_tree"), FEATURE_BIG_YEW_TREE);
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, KEY_CONFIGURED_FEATURE_BIG_YEW_TREE.getValue(), CONFIGURED_FEATURE_BIG_YEW_TREE);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, KEY_PLACED_BIG_YEW_TREE.getValue(), PLACED_BIG_YEW_TREE);
        BiomeModifications.addFeature(BiomeSelectors.categories(Biome.Category.MOUNTAIN, Biome.Category.EXTREME_HILLS), GenerationStep.Feature.VEGETAL_DECORATION, KEY_PLACED_BIG_YEW_TREE);

        //Yew Treehouse
        Registry<ConfiguredStructureFeature<?, ?>> registry = BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE;
        Registry.register(registry, new Identifier(BewitchmentPlus.MODID, "configured_yew_tree_house"), CONFIGURED_YEW_TREE_HOUSE);
        //BiomeModifications.addStructure(BiomeSelectors.all(), RegistryKey.of(Registry.CONFIGURED_STRUCTURE_FEATURE_KEY, BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE.getId(CONFIGURED_YEW_TREE_HOUSE)));
        BiomeModifications.create(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE.getId(CONFIGURED_YEW_TREE_HOUSE)).add(ModificationPhase.ADDITIONS, BiomeSelectors.all(), biomeModificationContext -> {

        });
        FabricStructureBuilder.create(new Identifier(BewitchmentPlus.MODID, "yew_tree_house"), YEW_TREE_HOUSE).step(GenerationStep.Feature.SURFACE_STRUCTURES).defaultConfig(new StructureConfig(10, 5, 399117345)).adjustsSurface().register();
    }
}
