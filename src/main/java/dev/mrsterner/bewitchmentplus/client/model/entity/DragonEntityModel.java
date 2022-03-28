package dev.mrsterner.bewitchmentplus.client.model.entity;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.common.entity.DragonEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DragonEntityModel extends AnimatedGeoModel<DragonEntity> {

    @Override
    public Identifier getAnimationFileLocation(DragonEntity entity) {
        return new Identifier(BewitchmentPlus.MODID, "animations/dragon.animation.json");
    }

    @Override
    public Identifier getModelLocation(DragonEntity entity) {
        return new Identifier(BewitchmentPlus.MODID, "geo/dragon.geo.json");
    }

    @Override
    public Identifier getTextureLocation(DragonEntity entity) {
        return new Identifier(BewitchmentPlus.MODID, "textures/entity/dragon/dragon.png");
    }
}