package dev.mrsterner.bewitchmentplus.client.model.entity;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.common.entity.LeshonEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class LeshonEntityModel extends DefaultedEntityGeoModel<LeshonEntity> {

    public LeshonEntityModel() {
        super(new Identifier(BewitchmentPlus.MODID, "leshon"), true);
    }

    @Override
    public Identifier getModelResource(LeshonEntity object) {
        return new Identifier(BewitchmentPlus.MODID, "geo/leshon.geo.json");
    }

    @Override
    public Identifier getTextureResource(LeshonEntity object) {
        return new Identifier(BewitchmentPlus.MODID, "textures/entity/leshon/leshon.png");
    }

    @Override
    public Identifier getAnimationResource(LeshonEntity animatable) {
        return new Identifier(BewitchmentPlus.MODID, "animations/leshon.animation.json");
    }
}
