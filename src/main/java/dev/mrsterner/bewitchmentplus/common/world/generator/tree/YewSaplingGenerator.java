package dev.mrsterner.bewitchmentplus.common.world.generator.tree;

import dev.mrsterner.bewitchmentplus.common.registry.BWPWorldGenerators;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class YewSaplingGenerator extends SaplingGenerator {
    @Nullable
    @Override
    protected ConfiguredFeature<?, ?> getTreeFeature(Random random, boolean bees) {
        return BWPWorldGenerators.CONFIGURED_FEATURE_BIG_YEW_TREE;
    }
}