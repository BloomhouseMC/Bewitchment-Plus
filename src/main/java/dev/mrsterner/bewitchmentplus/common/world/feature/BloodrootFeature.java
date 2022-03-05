package dev.mrsterner.bewitchmentplus.common.world.feature;

import com.mojang.serialization.Codec;
import dev.mrsterner.bewitchmentplus.common.registry.BWPObjects;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class BloodrootFeature extends Feature<DefaultFeatureConfig> {
    public BloodrootFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        BlockPos topPos = context.getWorld().getTopPosition(Heightmap.Type.WORLD_SURFACE, context.getOrigin());
        BlockPos belowPos = topPos.down();
        if (context.getWorld().getBlockState(belowPos).getBlock() == Blocks.GRASS_BLOCK)
            context.getWorld().setBlockState(topPos, BWPObjects.BLOODROOT.getDefaultState(), 3);
        return true;
    }
}