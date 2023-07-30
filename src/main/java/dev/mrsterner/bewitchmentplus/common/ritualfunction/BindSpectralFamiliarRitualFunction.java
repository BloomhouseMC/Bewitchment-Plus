package dev.mrsterner.bewitchmentplus.common.ritualfunction;

import dev.mrsterner.bewitchmentplus.common.BWPConfig;
import dev.mrsterner.bewitchmentplus.common.world.BWPWorldState;
import moriyashiine.bewitchment.api.registry.RitualFunction;
import moriyashiine.bewitchment.common.misc.BWUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.particle.ParticleType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

public class BindSpectralFamiliarRitualFunction extends RitualFunction {
    public BindSpectralFamiliarRitualFunction(ParticleType<?> startParticle, Predicate<LivingEntity> sacrifice) {
        super(startParticle, sacrifice);
    }

    @Override
    public boolean isValid(ServerWorld world, BlockPos pos, Inventory inventory) {
        return super.isValid(world, pos, inventory);
    }

    @Override
    public void start(ServerWorld world, BlockPos glyphPos, BlockPos effectivePos, Inventory inventory, boolean catFamiliar) {
        super.start(world, glyphPos, effectivePos, inventory, catFamiliar);
        if(BWPConfig.allowSummoningPlayersWithDeathGearInsteadOfDeath){
            BWPWorldState worldState = BWPWorldState.get(world);
            Optional<Pair<UUID, Boolean>> uuidBooleanPair = worldState.deathPlayer.stream().findAny();
            if(uuidBooleanPair.isPresent()){
                PlayerEntity playerEntity = world.getPlayerByUuid(uuidBooleanPair.get().getLeft());
                if(playerEntity != null){
                    BWUtil.teleport(playerEntity, effectivePos.getX() + 0.5, effectivePos.getY(), effectivePos.getZ() + 0.5, true);
                }
            }else{
                summonDeath(world, effectivePos);
            }
        }else{
            summonDeath(world, effectivePos);
        }
    }

    public void summonDeath(World world, BlockPos effectivePos){
        /*
        DeathEntity deathEntity = BWPEntityTypes.DEATH.create(world);
        if (deathEntity != null) {
            deathEntity.updatePositionAndAngles(effectivePos.getX() + 0.5, effectivePos.getY(), effectivePos.getZ() + 0.5, world.random.nextFloat() * 360, 0);
            world.spawnEntity(deathEntity);
        }

         */
    }
}
