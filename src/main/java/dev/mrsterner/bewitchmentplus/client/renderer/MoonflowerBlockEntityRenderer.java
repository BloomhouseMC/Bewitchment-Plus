package dev.mrsterner.bewitchmentplus.client.renderer;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.common.block.blockentity.MoonflowerBlockEntity;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class MoonflowerBlockEntityRenderer extends GeoBlockRenderer<MoonflowerBlockEntity> {
    public MoonflowerBlockEntityRenderer() {
        super(new DefaultedBlockGeoModel<>(BewitchmentPlus.id("moonflower")));
    }
}