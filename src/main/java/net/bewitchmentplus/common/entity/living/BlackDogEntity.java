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
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.StructureFeature;

import java.util.Random;

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

	public static boolean spawnRestriction(EntityType<BlackDogEntity> type, ServerWorldAccess serverWorldAccess, SpawnReason spawnReason, BlockPos pos, Random random) {
		ServerWorld world = serverWorldAccess.toServerWorld();

		if (BewitchmentPlus.config.blackDogBiomeCategories.contains(world.getBiome(pos).getCategory().getName())) {
			return true;
		}

		if (BewitchmentPlus.config.blackDogStructureSpawn) {
			int maxDistanceToStructure = 16;

			BlockPos outpost = world.locateStructure(StructureFeature.PILLAGER_OUTPOST, pos, 1, false);
			if (outpost != null && withinDistance(outpost, pos, maxDistanceToStructure)) {
				return true;
			}

			BlockPos village = world.locateStructure(StructureFeature.VILLAGE, pos, 1, false);
			if (village != null && withinDistance(village, pos, maxDistanceToStructure)) {
				return true;
			}
		}

		return false;
	}

	public static boolean withinDistance(BlockPos a, BlockPos b, int distance) {
		double ax = a.getX();
		double az = a.getZ();

		double bx = b.getX();
		double bz = b.getZ();

		bx -= ax;
		bz -= az;

		return bx * bx + bz * bz <= distance * distance;
	}

	@Override
	public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
		return false;
	}

	@Override
	public void tick() {
		super.tick();
		if (world.isThundering())
			this.applyStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 100, 0, true, true));
		this.applyStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100, 0, true, true));
		if (!world.isClient && !hasCustomName() && world.isDay() && !world.isRaining() && world.isSkyVisibleAllowingSea(getBlockPos())) {
			PlayerStream.watching(this).forEach(playerEntity -> SpawnSmokeParticlesPacket.send(playerEntity, this));
			remove();
		}
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
