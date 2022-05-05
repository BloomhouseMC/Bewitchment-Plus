package dev.mrsterner.bewitchmentplus.common.ritualfunction;

import moriyashiine.bewitchment.api.registry.RitualFunction;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.particle.ParticleType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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
    }

    @Override
    public void tick(World world, BlockPos glyphPos, BlockPos effectivePos, boolean catFamiliar) {
        super.tick(world, glyphPos, effectivePos, catFamiliar);
        //TODO summon death or player wearing deaths attire, BUT MAKE IT CONFIGURABLE
    }
}
