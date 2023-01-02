package dev.mrsterner.bewitchmentplus.common.ritualfunction;

import dev.mrsterner.bewitchmentplus.common.entity.LeshonEntity;
import dev.mrsterner.bewitchmentplus.common.entity.NifflerEntity;
import dev.mrsterner.bewitchmentplus.common.entity.PhoenixEntity;
import dev.mrsterner.bewitchmentplus.common.entity.UnicornEntity;
import dev.mrsterner.bewitchmentplus.common.registry.BWPEntityTypes;
import dev.mrsterner.bewitchmentplus.common.registry.BWPTags;
import moriyashiine.bewitchment.api.registry.RitualFunction;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.particle.ParticleType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.Registry;

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
        Random random = world.getRandom();
        //int i = random.nextInt(2);//TODO
        int i = 1;
        switch (i) {
            case 0 -> {
                NifflerEntity nifflerEntity = BWPEntityTypes.NIFFLER.create(world);
                if (nifflerEntity != null) {
                    nifflerEntity.initialize(world, world.getLocalDifficulty(effectivePos), SpawnReason.EVENT, null, null);
                    nifflerEntity.updatePositionAndAngles(effectivePos.getX() + 0.5, effectivePos.getY(), effectivePos.getZ() + 0.5, world.random.nextFloat() * 360, 0);
                    world.spawnEntity(nifflerEntity);
                }
            }
            case 1 -> {
                UnicornEntity unicornEntity = BWPEntityTypes.UNICORN.create(world);
                if (unicornEntity != null) {
                    unicornEntity.initialize(world, world.getLocalDifficulty(effectivePos), SpawnReason.EVENT, null, null);
                    unicornEntity.updatePositionAndAngles(effectivePos.getX() + 0.5, effectivePos.getY(), effectivePos.getZ() + 0.5, world.random.nextFloat() * 360, 0);
                    world.spawnEntity(unicornEntity);
                }
            }
            case 2 -> {
                PhoenixEntity phoenixEntity = BWPEntityTypes.PHOENIX.create(world);
                if (phoenixEntity != null) {
                    phoenixEntity.initialize(world, world.getLocalDifficulty(effectivePos), SpawnReason.EVENT, null, null);
                    phoenixEntity.updatePositionAndAngles(effectivePos.getX() + 0.5, effectivePos.getY(), effectivePos.getZ() + 0.5, world.random.nextFloat() * 360, 0);
                    world.spawnEntity(phoenixEntity);
                }
            }
        }

        /*
        EntityType<?> entityType = Registry.ENTITY_TYPE.getEntryList(BWPTags.SPECTRAL_FAMILIAR).flatMap(entity -> entity.getRandom(world.random)).map((entry) -> (entry.value())).orElse(null);
        if(entityType != null){
            PassiveEntity entity2 = (PassiveEntity)entityType.create(world);
            entity2.initialize(world, world.getLocalDifficulty(effectivePos), SpawnReason.EVENT, null, null);
            entity2.updatePositionAndAngles(effectivePos.getX() + 0.5, effectivePos.getY(), effectivePos.getZ() + 0.5, world.random.nextFloat() * 360, 0);
            world.spawnEntity(entity2);
        }

         */
        super.start(world, glyphPos, effectivePos, inventory, catFamiliar);
    }
}
