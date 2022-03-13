package dev.mrsterner.bewitchmentplus.api;

import dev.mrsterner.bewitchmentplus.common.entity.LeshonEntity;
import dev.mrsterner.bewitchmentplus.common.registry.BWPCriterion;
import dev.mrsterner.bewitchmentplus.common.registry.BWPEntityTypes;
import dev.mrsterner.bewitchmentplus.common.registry.BWPTransformations;
import moriyashiine.bewitchment.api.component.TransformationComponent;
import moriyashiine.bewitchment.common.registry.BWComponents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public class BewitchmentPlusAPI {
    private static LeshonEntity entity;

    public static boolean grantMagical(PlayerEntity player) {
        if (player instanceof ServerPlayerEntity) {
            BWPCriterion.MAGICAL_CRITERION.trigger((ServerPlayerEntity) player);
            ((Magical)player).setMagical(true);
            return true;
        }
        return false;
    }

    public static boolean isLeshon(Entity entity, boolean includeHumanForm) {
        if (entity instanceof PlayerEntity player) {
            TransformationComponent transformationComponent = BWComponents.TRANSFORMATION_COMPONENT.get(player);
            if (transformationComponent.getTransformation() == BWPTransformations.LESHON) {
                return includeHumanForm || transformationComponent.isAlternateForm();
            }
        }
        return entity instanceof LeshonEntity;
    }


    public static LeshonEntity getLeshon(PlayerEntity player) {
        if (BWPTransformations.isLeshon(player, false)) {
            if(entity == null) {
                entity = BWPEntityTypes.LESHON.create(player.world);
                assert entity != null;
            }
            return entity;
        }
        return null;
    }


}
