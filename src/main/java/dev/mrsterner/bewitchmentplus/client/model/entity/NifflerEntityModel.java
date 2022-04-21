package dev.mrsterner.bewitchmentplus.client.model.entity;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.common.entity.NifflerEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import static dev.mrsterner.bewitchmentplus.common.entity.NifflerEntity.SLEEPING;

public class NifflerEntityModel extends AnimatedGeoModel<NifflerEntity> {

    @Override
    public Identifier getModelLocation(NifflerEntity object) {
        return new Identifier(BewitchmentPlus.MODID, "geo/niffler.geo.json");
    }

    @Override
    public Identifier getTextureLocation(NifflerEntity object) {
        return new Identifier(BewitchmentPlus.MODID, "textures/entity/niffler/0.png");
    }

    @Override
    public Identifier getAnimationFileLocation(NifflerEntity animatable) {
        return new Identifier(BewitchmentPlus.MODID, "animations/niffler.animation.json");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setLivingAnimations(NifflerEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        if (head != null && !entity.getDataTracker().get(SLEEPING)) {
            head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }
    }
}
