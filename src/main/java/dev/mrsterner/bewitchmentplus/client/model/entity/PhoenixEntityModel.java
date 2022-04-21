package dev.mrsterner.bewitchmentplus.client.model.entity;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.common.entity.PhoenixEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PhoenixEntityModel extends AnimatedGeoModel<PhoenixEntity> {

    @Override
    public Identifier getAnimationFileLocation(PhoenixEntity entity) {
        return new Identifier(BewitchmentPlus.MODID, "animations/phoenix.animation.json");
    }

    @Override
    public Identifier getModelLocation(PhoenixEntity entity) {
        return new Identifier(BewitchmentPlus.MODID, "geo/phoenix.geo.json");
    }

    @Override
    public Identifier getTextureLocation(PhoenixEntity entity) {
        return new Identifier(BewitchmentPlus.MODID, "textures/entity/phoenix/0.png");
    }
}