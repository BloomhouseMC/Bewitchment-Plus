package dev.mrsterner.bewitchmentplus.common.entity.ai;

import dev.mrsterner.bewitchmentplus.common.entity.PhoenixEntity;
import dev.mrsterner.bewitchmentplus.common.registry.BWPEntityTypes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PhoenixRebirthGoal extends Goal {
    private final PhoenixEntity phoenixEntity;
    private int rebirthTimer = -20 * 4;

    public PhoenixRebirthGoal(PhoenixEntity phoenixEntity) {
        this.phoenixEntity = phoenixEntity;
    }

    @Override
    public boolean canStart() {
        return false; //TODO BewitchmentAPI.getMoonPhase(phoenixEntity.world) == 0;
    }

    @Override
    public boolean shouldContinue() {
        return canStart();
    }

    @Override
    public void stop() {
        PhoenixEntity newPhoenix = BWPEntityTypes.PHOENIX.create(phoenixEntity.world);
        newPhoenix.setBaby(true);
        newPhoenix.copyPositionAndRotation(phoenixEntity);
        newPhoenix.refreshPositionAndAngles(phoenixEntity.getX(), phoenixEntity.getY(), phoenixEntity.getZ(), phoenixEntity.getYaw(), phoenixEntity.getPitch());
        newPhoenix.setPersistent();
        phoenixEntity.getWorld().spawnEntity(newPhoenix);
        phoenixEntity.discard();
        super.stop();
    }

    @Override
    public void tick() {
        BlockPos pos = phoenixEntity.getBlockPos();
        World world = phoenixEntity.getWorld();
        world.addParticle(ParticleTypes.FLAME,
        pos.getX() + 0.5 + world.getRandom().nextGaussian()/4,
        pos.getY() + 0.5 + world.getRandom().nextGaussian()/4,
        pos.getZ() + 0.5 + world.getRandom().nextGaussian()/4,
        0, 0, 0);
        if(rebirthTimer >= 0){
            //TODO do the thing
            rebirthTimer = 0;
        }else{
            rebirthTimer++;
        }
        super.tick();
    }

    @Override
    public void start() {
        phoenixEntity.setFireTicks(20 * 4);
        super.start();
    }
}
