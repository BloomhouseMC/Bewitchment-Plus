package dev.mrsterner.bewitchmentplus.client.model.entity;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.common.entity.LeshonEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class LeshonEntityModel extends AnimatedGeoModel<LeshonEntity> {
    private Identifier currentTexture = new Identifier(BewitchmentPlus.MODID, "textures/entity/leshon/leshon.png");

    public void setCurrentTexture(Identifier currentTexture) {
        this.currentTexture = currentTexture;
    }

    @Override
    public Identifier getModelLocation(LeshonEntity object) {
        return new Identifier(BewitchmentPlus.MODID, "geo/leshon.geo.json");
    }

    @Override
    public Identifier getTextureLocation(LeshonEntity object) {
        return currentTexture;
    }

    @Override
    public Identifier getAnimationFileLocation(LeshonEntity animatable) {
        return new Identifier(BewitchmentPlus.MODID, "animations/leshon.animation.json");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setLivingAnimations(LeshonEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        if (head != null) {
            head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
            head.setRotationZ(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }
    }
}
