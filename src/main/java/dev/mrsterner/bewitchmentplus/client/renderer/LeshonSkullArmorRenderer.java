package dev.mrsterner.bewitchmentplus.client.renderer;

import dev.mrsterner.bewitchmentplus.client.model.LeshonSkullModel;
import dev.mrsterner.bewitchmentplus.common.item.LeshonSkullItem;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class LeshonSkullArmorRenderer extends GeoArmorRenderer<LeshonSkullItem> {
    public LeshonSkullArmorRenderer() {
        super(new LeshonSkullModel());
        this.headBone = "armorHead";
    }
}
