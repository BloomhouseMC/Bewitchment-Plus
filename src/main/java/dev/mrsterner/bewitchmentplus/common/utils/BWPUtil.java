package dev.mrsterner.bewitchmentplus.common.utils;

import dev.mrsterner.bewitchmentplus.common.block.blockentity.YewLogBlockEntity;
import dev.mrsterner.bewitchmentplus.common.entity.LeshonEntity;
import dev.mrsterner.bewitchmentplus.common.interfaces.Magical;
import dev.mrsterner.bewitchmentplus.common.registry.BWPCriterion;
import dev.mrsterner.bewitchmentplus.common.registry.BWPEntityTypes;
import dev.mrsterner.bewitchmentplus.common.registry.BWPTransformations;
import dev.mrsterner.bewitchmentplus.common.world.BWPWorldState;
import moriyashiine.bewitchment.api.component.TransformationComponent;
import moriyashiine.bewitchment.common.registry.BWComponents;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Pair;

public class BWPUtil {
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

    public static Pair<ServerWorld, YewLogBlockEntity> getPossibleHomeStead(LivingEntity player) {
        if (player.world instanceof ServerWorld) {
            for (ServerWorld serverWorld : player.world.getServer().getWorlds()) {
                BWPWorldState worldState = BWPWorldState.get(serverWorld);
                if (BWPWorldState.homeStead.containsKey(player.getUuid())) {
                    BlockEntity entity = serverWorld.getBlockEntity(BWPWorldState.homeStead.get(player.getUuid()));
                    if (entity instanceof YewLogBlockEntity) {
                        return new Pair<>(serverWorld, (YewLogBlockEntity) entity);
                    }
                    worldState.removeHomeStead(player.getUuid());
                }
            }
        }
        return null;
    }
}
