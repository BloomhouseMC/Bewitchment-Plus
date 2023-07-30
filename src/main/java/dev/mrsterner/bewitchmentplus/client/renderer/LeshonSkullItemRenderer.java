package dev.mrsterner.bewitchmentplus.client.renderer;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.common.item.LeshonSkullItem;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class LeshonSkullItemRenderer extends GeoItemRenderer<LeshonSkullItem> {
    public LeshonSkullItemRenderer() {
        super(new DefaultedItemGeoModel<>(BewitchmentPlus.id("skull")));
    }
}
