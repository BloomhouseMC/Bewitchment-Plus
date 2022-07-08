package dev.mrsterner.bewitchmentplus.client.model.entity;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.common.entity.DeathEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DeathEntityModel extends AnimatedGeoModel<DeathEntity> {

    @Override
    public Identifier getAnimationResource(DeathEntity entity) {
        return new Identifier(BewitchmentPlus.MODID, "animations/death.animation.json");
    }

    @Override
    public Identifier getModelResource(DeathEntity entity) {
        return new Identifier(BewitchmentPlus.MODID, "geo/death.geo.json");
    }

    @Override
    public Identifier getTextureResource(DeathEntity entity) {
        return new Identifier(BewitchmentPlus.MODID, "textures/entity/death.png");
    }
}