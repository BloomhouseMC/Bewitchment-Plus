package dev.mrsterner.bewitchmentplus.common.world.feature;

import com.mojang.serialization.Codec;
import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.common.utils.WorldgenHelper;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class YewTreeFeature extends Feature<ProbabilityConfig> {
    public YewTreeFeature(Codec<ProbabilityConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<ProbabilityConfig> context) {
        return WorldgenHelper.generateNbtFeature(new Identifier(BewitchmentPlus.MODID, "yew_tree_0"), context.getWorld(), context.getOrigin(), BewitchmentPlus.config.world.yewTreeWeight);
    }
}
