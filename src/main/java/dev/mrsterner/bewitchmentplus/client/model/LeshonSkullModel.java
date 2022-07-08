package dev.mrsterner.bewitchmentplus.client.model;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.common.item.LeshonSkullItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class LeshonSkullModel extends AnimatedGeoModel<LeshonSkullItem> {
    @Override
    public Identifier getModelResource(LeshonSkullItem object) {
        return new Identifier(BewitchmentPlus.MODID, "geo/skull.geo.json");
    }

    @Override
    public Identifier getTextureResource(LeshonSkullItem object) {
        return new Identifier(BewitchmentPlus.MODID, "textures/entity/skull.png");
    }

    @Override
    public Identifier getAnimationResource(LeshonSkullItem animatable) {
        return new Identifier(BewitchmentPlus.MODID, "animations/skull.animation.json");
    }
}
