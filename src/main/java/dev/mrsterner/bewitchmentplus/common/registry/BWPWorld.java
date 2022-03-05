package dev.mrsterner.bewitchmentplus.common.registry;

import net.minecraft.world.gen.decorator.BiomePlacementModifier;
import net.minecraft.world.gen.decorator.RarityFilterPlacementModifier;
import net.minecraft.world.gen.decorator.SquarePlacementModifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public class BWPWorld {
    public static final ConfiguredFeature<?,?> CONFIGURED_WOLFSBANE = Feature.FLOWER.configure(new RandomPatchFeatureConfig(
    64, //Chance, higher = more
    6, //spread XZ
    2, //Spread Y
    () -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(BlockStateProvider.of(BWPObjects.BLOODROOT))).withInAirFilter())
    );

    public static final PlacedFeature BLOODROOT_FEATURE = PlacedFeatures.register("bloodroot_feature", CONFIGURED_WOLFSBANE.withPlacement(RarityFilterPlacementModifier.of(32), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of()));

}
