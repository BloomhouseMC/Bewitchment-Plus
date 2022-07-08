package dev.mrsterner.bewitchmentplus.client.model;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.common.block.blockentity.MoonflowerBlockEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MoonflowerModel extends AnimatedGeoModel<MoonflowerBlockEntity> {

    @Override
    public Identifier getAnimationResource(MoonflowerBlockEntity entity) {
        return new Identifier(BewitchmentPlus.MODID, "animations/moonflower.animation.json");
    }

    @Override
    public Identifier getModelResource(MoonflowerBlockEntity animatable) {
        return new Identifier(BewitchmentPlus.MODID, "geo/moonflower.geo.json");
    }

    @Override
    public Identifier getTextureResource(MoonflowerBlockEntity entity) {
        return new Identifier(BewitchmentPlus.MODID, "textures/block/moonflower.png");
    }
}