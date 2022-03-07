package dev.mrsterner.bewitchmentplus.api;

import dev.mrsterner.bewitchmentplus.common.entity.LeshonEntity;
import dev.mrsterner.bewitchmentplus.common.registry.BWPCriterion;
import dev.mrsterner.bewitchmentplus.common.registry.BWPEntityTypes;
import dev.mrsterner.bewitchmentplus.common.registry.BWPTransformations;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public class BewitchmentPlusAPI {
    private static LeshonEntity entity;

    public static boolean grantMagical(PlayerEntity player) {
        if (player instanceof ServerPlayerEntity) {
            BWPCriterion.MAGICAL_CRITERION.trigger((ServerPlayerEntity) player);
            return true;
        }
        return false;
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
