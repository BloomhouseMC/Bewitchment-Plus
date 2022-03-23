package dev.mrsterner.bewitchmentplus.common.registry;

import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.common.world.generator.tree.YewTreeFeature;
import dev.mrsterner.bewitchmentplus.common.world.structures.YewTreeStructure;
import dev.mrsterner.bewitchmentplus.mixin.common.StructureFeatureAccessor;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.tag.BiomeTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.RarityFilterPlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.NoiseThresholdBlockStateProvider;

import java.util.List;
import java.util.Map;

public class BWPWorldGenerators extends ConfiguredFeatures{
    private static final Map<StructureFeature<?>, GenerationStep.Feature> STRUCTURE_TO_GENERATION_STEP = Maps.newHashMap();

    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig,?>> CONFIGURED_BLOODROOT =
    ConfiguredFeatures.register("bloodroot", Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
    new SimpleBlockFeatureConfig(BlockStateProvider.of(BWPObjects.BLOODROOT.getDefaultState())), List.of(Blocks.GRASS_BLOCK)));


    public static final RegistryEntry<PlacedFeature> BLOODROOT_FEATURE = PlacedFeatures.register("bloodroot_feature", CONFIGURED_BLOODROOT);


    public static StructureFeature<?> YEW_TREE = new YewTreeStructure();
    //Feature.FLOWER.configure(new RandomPatchFeatureConfig(64, 6, 2, () -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(BlockStateProvider.of(BWPObjects.BLOODROOT))).withInAirFilter()));
    //public static final PlacedFeature BLOODROOT_FEATURE = PlacedFeatures.register("bloodroot_feature", CONFIGURED_BLOODROOT.withPlacement(RarityFilterPlacementModifier.of(32), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of()));



/*
    private static final Feature<DefaultFeatureConfig> FEATURE_BIG_YEW_TREE = new YewTreeFeature(DefaultFeatureConfig.CODEC);
    public static final ConfiguredFeature<?, ?> CONFIGURED_FEATURE_BIG_YEW_TREE = FEATURE_BIG_YEW_TREE.configure(DefaultFeatureConfig.INSTANCE);
    public static final RegistryKey<ConfiguredFeature<?, ?>> KEY_CONFIGURED_FEATURE_BIG_YEW_TREE = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, new Identifier(BewitchmentPlus.MODID, "trees_big_yew"));
    public static final PlacedFeature PLACED_BIG_YEW_TREE = CONFIGURED_FEATURE_BIG_YEW_TREE.withPlacement(VegetationPlacedFeatures.modifiers(PlacedFeatures.createCountExtraModifier(1, 0.01f, 1)));
    public static final RegistryKey<PlacedFeature> KEY_PLACED_BIG_YEW_TREE = RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier(BewitchmentPlus.MODID, "trees_big_yew"));


 */

    //public static StructureFeature<StructurePoolFeatureConfig> YEW_TREE_HOUSE = new YewTreeHouseStructure(StructurePoolFeatureConfig.CODEC);
    //public static ConfiguredStructureFeature<?, ?> CONFIGURED_YEW_TREE_HOUSE = YEW_TREE_HOUSE.configure(new StructurePoolFeatureConfig(() -> PlainsVillageData.STRUCTURE_POOLS, 0));



    public static void init() {
        //Yew Tree
        StructureFeatureAccessor.callRegister(BewitchmentPlus.MODID + ":yew_tree", YEW_TREE, GenerationStep.Feature.SURFACE_STRUCTURES);
        //Registry.register(Registry.FEATURE, new Identifier(BewitchmentPlus.MODID, "big_yew_tree"), FEATURE_BIG_YEW_TREE);
        //Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, KEY_CONFIGURED_FEATURE_BIG_YEW_TREE.getValue(), CONFIGURED_FEATURE_BIG_YEW_TREE);
        //Registry.register(BuiltinRegistries.PLACED_FEATURE, KEY_PLACED_BIG_YEW_TREE.getValue(), PLACED_BIG_YEW_TREE);
        //BiomeModifications.addFeature(BiomeSelectors.categories(Biome.Category.MOUNTAIN, Biome.Category.EXTREME_HILLS), GenerationStep.Feature.VEGETAL_DECORATION, KEY_PLACED_BIG_YEW_TREE);

        //Yew Treehouse
        //Registry<ConfiguredStructureFeature<?, ?>> registry = BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE;
        //Registry.register(registry, new Identifier(BewitchmentPlus.MODID, "configured_yew_tree_house"), CONFIGURED_YEW_TREE_HOUSE);
        //BiomeModifications.addStructure(BiomeSelectors.all(), RegistryKey.of(Registry.CONFIGURED_STRUCTURE_FEATURE_KEY, BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE.getId(CONFIGURED_YEW_TREE_HOUSE)));
        //BiomeModifications.create(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE.getId(CONFIGURED_YEW_TREE_HOUSE)).add(ModificationPhase.ADDITIONS, BiomeSelectors.all(), biomeModificationContext -> {

        //});
        //FabricStructureBuilder.create(new Identifier(BewitchmentPlus.MODID, "yew_tree_house"), YEW_TREE_HOUSE).step(GenerationStep.Feature.SURFACE_STRUCTURES).defaultConfig(new StructureConfig(10, 5, 399117345)).adjustsSurface().register();
    }
}
