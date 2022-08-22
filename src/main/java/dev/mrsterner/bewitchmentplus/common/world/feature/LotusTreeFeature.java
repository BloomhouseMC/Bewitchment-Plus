package dev.mrsterner.bewitchmentplus.common.world.feature;

import com.mojang.serialization.Codec;
import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.common.BWPConfig;
import dev.mrsterner.bewitchmentplus.common.utils.WorldgenHelper;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class LotusTreeFeature extends Feature<ProbabilityConfig> {
    public LotusTreeFeature(Codec<ProbabilityConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<ProbabilityConfig> context) {
        return WorldgenHelper.generateNbtFeature(new Identifier(BewitchmentPlus.MODID, "lotus"), context.getWorld(), context.getOrigin(), BWPConfig.lotusTreeWeight);
    }
}
