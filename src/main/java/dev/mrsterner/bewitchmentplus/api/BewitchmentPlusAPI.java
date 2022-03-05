package dev.mrsterner.bewitchmentplus.api;

import dev.mrsterner.bewitchmentplus.common.registry.BWPCriterion;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public class BewitchmentPlusAPI {

    public static boolean grantMagical(PlayerEntity player) {
        if (player instanceof ServerPlayerEntity) {
            BWPCriterion.MAGICAL_CRITERION.trigger((ServerPlayerEntity) player);
            return true;
        }
        return false;
    }


}
