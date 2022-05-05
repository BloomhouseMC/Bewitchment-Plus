package dev.mrsterner.bewitchmentplus.client.model.entity;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.common.entity.DeathEntity;
import dev.mrsterner.bewitchmentplus.common.entity.EffigyEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DeathEntityModel extends AnimatedGeoModel<DeathEntity> {

    @Override
    public Identifier getAnimationFileLocation(DeathEntity entity) {
        return new Identifier(BewitchmentPlus.MODID, "animations/death.animation.json");
    }

    @Override
    public Identifier getModelLocation(DeathEntity entity) {
        return new Identifier(BewitchmentPlus.MODID, "geo/death.geo.json");
    }

    @Override
    public Identifier getTextureLocation(DeathEntity entity) {
        return new Identifier(BewitchmentPlus.MODID, "textures/entity/death.png");
    }
}