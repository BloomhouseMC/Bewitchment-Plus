package dev.mrsterner.bewitchmentplus.common.ritualfunction;

import dev.mrsterner.bewitchmentplus.common.registry.BWPEntityTypes;
import dev.mrsterner.bewitchmentplus.common.registry.BWPTags;
import moriyashiine.bewitchment.api.registry.RitualFunction;
import moriyashiine.bewitchment.common.entity.living.util.BWTameableEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.inventory.Inventory;
import net.minecraft.particle.ParticleType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.function.Predicate;

public class SpectralFamiliarRitualFunction extends RitualFunction {

    public SpectralFamiliarRitualFunction(ParticleType<?> startParticle, Predicate<LivingEntity> sacrifice) {
        super(startParticle, sacrifice);

    }

    @Override
    public boolean isValid(ServerWorld world, BlockPos pos, Inventory inventory) {
        return super.isValid(world, pos, inventory);
    }

    @Override
    public void start(ServerWorld world, BlockPos glyphPos, BlockPos effectivePos, Inventory inventory, boolean catFamiliar) {
        int rand = world.random.nextInt(BWPTags.SPECTRAL_FAMILIAR.values().size());
        var entity = switch (rand) {
            case 1 -> BWPEntityTypes.NIFFLER.create(world);
            case 2 -> BWPEntityTypes.PHOENIX.create(world);
            case 3 -> BWPEntityTypes.UNICORN.create(world);
            default -> null;
        };
        if (entity != null) {
            entity.initialize(world, world.getLocalDifficulty(effectivePos), SpawnReason.EVENT, null, null);
            entity.updatePositionAndAngles(effectivePos.getX() + 0.5, effectivePos.getY(), effectivePos.getZ() + 0.5, world.random.nextFloat() * 360, 0);
            world.spawnEntity(entity);
        }
        super.start(world, glyphPos, effectivePos, inventory, catFamiliar);
    }
}
