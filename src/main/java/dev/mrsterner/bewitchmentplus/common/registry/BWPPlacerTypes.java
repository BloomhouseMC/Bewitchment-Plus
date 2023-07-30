package dev.mrsterner.bewitchmentplus.common.registry;

import com.mojang.serialization.Codec;
import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.common.world.YewFoilagePlacer;
import dev.mrsterner.bewitchmentplus.common.world.YewTrunkPlacer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

public class BWPPlacerTypes {
    public static final TrunkPlacerType<YewTrunkPlacer> YEW_TRUNK_PLACER = registerT("yew_trunk_placer", YewTrunkPlacer.CODEC);
    public static final FoliagePlacerType<YewFoilagePlacer> YEW_FOLIAGE_PLACER = registerF("yew_foliage_placer", YewFoilagePlacer.CODEC);

    private static <P extends FoliagePlacer> FoliagePlacerType<P> registerF(String id, Codec<P> codec) {
        return Registry.register(Registries.FOLIAGE_PLACER_TYPE, BewitchmentPlus.id(id), new FoliagePlacerType<>(codec));
    }

    private static <P extends TrunkPlacer> TrunkPlacerType<P> registerT(String id, Codec<P> codec) {
        return Registry.register(Registries.TRUNK_PLACER_TYPE, BewitchmentPlus.id(id), new TrunkPlacerType<>(codec));
    }

    public static void init(){

    }
}
