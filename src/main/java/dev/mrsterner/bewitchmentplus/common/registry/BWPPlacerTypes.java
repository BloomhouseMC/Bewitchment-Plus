package dev.mrsterner.bewitchmentplus.common.registry;

import dev.mrsterner.bewitchmentplus.common.world.YewFoilagePlacer;
import dev.mrsterner.bewitchmentplus.common.world.YewTrunkPlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

public class BWPPlacerTypes {
    public static final TrunkPlacerType<YewTrunkPlacer> YEW_TRUNK_PLACER = TrunkPlacerType.register("yew_trunk_placer", YewTrunkPlacer.CODEC);
    public static final FoliagePlacerType<YewFoilagePlacer> YEW_FOLIAGE_PLACER = FoliagePlacerType.register("yew_foliage_placer", YewFoilagePlacer.CODEC);

    public static void init(){

    }
}
