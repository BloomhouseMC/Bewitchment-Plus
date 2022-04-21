package dev.mrsterner.bewitchmentplus.client.renderer;

import dev.mrsterner.bewitchmentplus.client.model.LeshonSkullModel;
import dev.mrsterner.bewitchmentplus.common.item.LeshonSkullItem;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class LeshonSkullItemRenderer extends GeoItemRenderer<LeshonSkullItem> {
    public LeshonSkullItemRenderer() {
        super(new LeshonSkullModel());
    }
}
