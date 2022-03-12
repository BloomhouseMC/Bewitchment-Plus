package dev.mrsterner.bewitchmentplus.common.registry;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.common.world.generator.tree.YewTreeFeature;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.*;

public class BWPWorldGenerators extends ConfiguredFeatures{

    private static final Feature<DefaultFeatureConfig> FEATURE_BIG_YEW_TREE = new YewTreeFeature(DefaultFeatureConfig.CODEC);
    public static final ConfiguredFeature<?, ?> CONFIGURED_FEATURE_BIG_YEW_TREE = FEATURE_BIG_YEW_TREE.configure(DefaultFeatureConfig.INSTANCE);
    public static final RegistryKey<ConfiguredFeature<?, ?>> KEY_CONFIGURED_FEATURE_BIG_YEW_TREE = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, new Identifier(BewitchmentPlus.MODID, "trees_big_yew"));
    public static final PlacedFeature PLACED_BIG_YEW_TREE = CONFIGURED_FEATURE_BIG_YEW_TREE.withPlacement(VegetationPlacedFeatures.modifiers(PlacedFeatures.createCountExtraModifier(1, 0.01f, 1)));
    public static final RegistryKey<PlacedFeature> KEY_PLACED_BIG_YEW_TREE = RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier(BewitchmentPlus.MODID, "trees_big_yew"));

    public static void init() {
        Registry.register(Registry.FEATURE, new Identifier(BewitchmentPlus.MODID, "big_yew_tree"), FEATURE_BIG_YEW_TREE);
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, KEY_CONFIGURED_FEATURE_BIG_YEW_TREE.getValue(), CONFIGURED_FEATURE_BIG_YEW_TREE);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, KEY_PLACED_BIG_YEW_TREE.getValue(), PLACED_BIG_YEW_TREE);
        BiomeModifications.addFeature(BiomeSelectors.all(), GenerationStep.Feature.VEGETAL_DECORATION, KEY_PLACED_BIG_YEW_TREE);
    }
}
