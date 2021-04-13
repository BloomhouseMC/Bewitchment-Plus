package net.bewitchmentplus.common.entity.living;

import moriyashiine.bewitchment.client.network.packet.SpawnSmokeParticlesPacket;
import moriyashiine.bewitchment.common.entity.living.util.BWHostileEntity;
import net.bewitchmentplus.BewitchmentPlus;
import net.fabricmc.fabric.api.server.PlayerStream;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.StructureFeature;

@SuppressWarnings("ALL")
public class BlackDogEntity extends BWHostileEntity {

	public BlackDogEntity(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
		this.setPathfindingPenalty(PathNodeType.DANGER_OTHER, 0.0F);
		this.setPathfindingPenalty(PathNodeType.DAMAGE_OTHER, 0.0F);
		this.setPathfindingPenalty(PathNodeType.DANGER_CACTUS, 0.0F);
		this.setPathfindingPenalty(PathNodeType.DAMAGE_CACTUS, 0.0F);
		experiencePoints = 5;
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 10.00D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0D).add(EntityAttributes.GENERIC_ARMOR, 2.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.35D).add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.35D).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0D);
	}

	@Override
	public void tick() {
		super.tick();
		if (!world.isClient && !hasCustomName() && world.isDay() && !world.isRaining() && world.isSkyVisibleAllowingSea(getBlockPos())) {
			PlayerStream.watching(this).forEach(playerEntity -> SpawnSmokeParticlesPacket.send(playerEntity, this));
			remove();
		}
	}

	@Override
	public boolean canSpawn(WorldAccess world, SpawnReason spawnReason) {
		boolean flag = super.canSpawn(world, spawnReason);
		if (flag && (spawnReason == SpawnReason.SPAWNER || spawnReason == SpawnReason.STRUCTURE || spawnReason == SpawnReason.MOB_SUMMONED || spawnReason == SpawnReason.SPAWN_EGG || spawnReason == SpawnReason.COMMAND || spawnReason == SpawnReason.DISPENSER || spawnReason == SpawnReason.NATURAL)) {
			return true;
		}
		if (world instanceof ServerWorld && BewitchmentPlus.config.blackDogStructureSpawn) {
			BlockPos nearestVillage = ((ServerWorld) world).locateStructure(StructureFeature.VILLAGE, getBlockPos(), 3, false);
			BlockPos nearestPillagerOutpost = ((ServerWorld) world).locateStructure(StructureFeature.PILLAGER_OUTPOST, getBlockPos(), 3, false);
			return (nearestVillage != null && Math.sqrt(nearestVillage.getSquaredDistance(getBlockPos())) < 128) || (nearestPillagerOutpost != null && Math.sqrt(nearestPillagerOutpost.getSquaredDistance(getBlockPos())) < 128);
		}
		return false;
	}

	public EntityGroup getGroup() {
		return EntityGroup.UNDEAD;
	}

	protected boolean hasShiny() {
		return true;
	}

	@Override
	protected void initGoals() {
		goalSelector.add(0, new SwimGoal(this));
		goalSelector.add(1, new MeleeAttackGoal(this, 1, true));
		goalSelector.add(2, new WanderAroundFarGoal(this, 1));
		goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8));
		goalSelector.add(3, new LookAroundGoal(this));
		targetSelector.add(0, new RevengeGoal(this));
		targetSelector.add(1, new FollowTargetGoal<>(this, LivingEntity.class, 10, true, false, entity -> entity instanceof PlayerEntity || entity instanceof SheepEntity || entity instanceof MerchantEntity || entity.getGroup() == EntityGroup.ILLAGER));
	}

	@Override
	public int getVariants() {
		return 5;
	}
}
