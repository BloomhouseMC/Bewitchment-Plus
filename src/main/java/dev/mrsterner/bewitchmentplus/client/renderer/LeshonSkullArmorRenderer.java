package dev.mrsterner.bewitchmentplus.client.renderer;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.common.item.LeshonSkullItem;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class LeshonSkullArmorRenderer extends GeoArmorRenderer<LeshonSkullItem> {
    public LeshonSkullArmorRenderer() {
        super(new DefaultedItemGeoModel<>(BewitchmentPlus.id("skull")));
    }
}
