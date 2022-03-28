package dev.mrsterner.bewitchmentplus.common.ritualfunction;

import dev.mrsterner.bewitchmentplus.common.entity.EffigyEntity;
import dev.mrsterner.bewitchmentplus.common.registry.BWPEntityTypes;
import moriyashiine.bewitchment.api.registry.RitualFunction;
import moriyashiine.bewitchment.common.misc.BWUtil;
import net.minecraft.block.Block;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.GrassBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.function.Predicate;

public class EffigyRitualFunction extends RitualFunction {
    private boolean found = false;
    public EffigyRitualFunction(ParticleType<?> startParticle, Predicate<LivingEntity> sacrifice) {
        super(startParticle, sacrifice);
    }

    @Override
    public boolean isValid(ServerWorld world, BlockPos pos, Inventory inventory) {
        return super.isValid(world, pos, inventory);
    }

    @Override
    public void tick(World world, BlockPos glyphPos, BlockPos effectivePos, boolean catFamiliar) {
        int radius = catFamiliar ? 9 : 3;
        if (world.getTime() % 20L == 0L && !found) {
            for (ArmorStandEntity armorStandEntity : world.getEntitiesByClass(ArmorStandEntity.class, (new Box(effectivePos)).expand(radius, 0.0D, radius), ArmorStandEntity::isAlive)) {
                EffigyEntity effigyEntity = BWPEntityTypes.EFFIGY.create(world);
                if (effigyEntity != null) {
                    effigyEntity.updatePositionAndAngles(armorStandEntity.getX() + 0.5, armorStandEntity.getY(), armorStandEntity.getZ() + 0.5, world.random.nextFloat() * 360, 0);
                    armorStandEntity.discard();
                    world.spawnEntity(effigyEntity);
                    found = true;
                    if(world.isClient()){
                        world.addParticle(ParticleTypes.HAPPY_VILLAGER, effigyEntity.getX() + MathHelper.nextDouble(world.random, (-radius), radius), effigyEntity.getY() + 0.5D, effigyEntity.getZ() + MathHelper.nextDouble(world.random, (-radius), radius), 0.0D, 0.0D, 0.0D);
                    }
                }
            }
        }
        super.tick(world, glyphPos, effectivePos, catFamiliar);
    }
}
