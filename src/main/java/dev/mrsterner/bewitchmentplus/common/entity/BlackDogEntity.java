package dev.mrsterner.bewitchmentplus.common.entity;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.common.registry.BWPObjects;
import moriyashiine.bewitchment.client.network.packet.SpawnSmokeParticlesPacket;
import moriyashiine.bewitchment.common.entity.living.util.BWHostileEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.server.PlayerStream;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.WitchEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.StructureFeature;

import java.util.Random;


public class BlackDogEntity extends BWHostileEntity {

	public int attackTick = 0;

	public BlackDogEntity(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
		this.setPathfindingPenalty(PathNodeType.DANGER_OTHER, 0.0F);
		this.setPathfindingPenalty(PathNodeType.DAMAGE_OTHER, 0.0F);
		this.setPathfindingPenalty(PathNodeType.DANGER_CACTUS, 0.0F);
		this.setPathfindingPenalty(PathNodeType.DAMAGE_CACTUS, 0.0F);
		experiencePoints = 5;
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createMobAttributes()
		.add(EntityAttributes.GENERIC_MAX_HEALTH, 10.00D)
		.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0D)
		.add(EntityAttributes.GENERIC_ARMOR, 2.0D)
		.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.35D)
		.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.35D)
		.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0D);
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

			BlockPos mansion = world.locateStructure(StructureFeature.MANSION, pos, 1, false);
			if (mansion != null && withinDistance(mansion, pos, maxDistanceToStructure)) {
				return true;
			}

			BlockPos village = world.locateStructure(StructureFeature.VILLAGE, pos, 1, false);
			return village != null && withinDistance(village, pos, maxDistanceToStructure);
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
	public boolean isFireImmune() {
		return true;
	}

	@Override
	public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource source) {
		return false;
	}

	@Override
	public void tick() {
		super.tick();
		if (attackTick > 0) {
			attackTick--;
		}
		if (world.isThundering())
			this.setStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 100, 0, true, true), null);
		this.setStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100, 0, true, true), null);
		if (!world.isClient && !hasCustomName() && world.isDay() && !world.isRaining() && world.isSkyVisibleAllowingSea(getBlockPos())) {
			PlayerStream.watching(this).forEach(playerEntity -> SpawnSmokeParticlesPacket.send(playerEntity, this));
			remove(RemovalReason.DISCARDED);
		}
	}

	public void toggleAttack(boolean attacking) {
		if (attacking) {
			attackTick = 40;
			world.sendEntityStatus(this, (byte) 4);
		} else {
			attackTick = 2;
			world.sendEntityStatus(this, (byte) 5);
		}
	}

	@Environment(EnvType.CLIENT)
	@Override
	public void handleStatus(byte id) {
		if (id == 4) {
			attackTick = 11;
		}
		if (id == 5) {
			attackTick = 2;
		} else {
			super.handleStatus(id);
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
		goalSelector.add(1, new MeleeAttackGoal(this, 1, true) {
			@Override
			public void start() {
				super.start();
				toggleAttack(true);
			}
		});
		goalSelector.add(2, new WanderAroundFarGoal(this, 1));
		goalSelector.add(2, new PounceAtTargetGoal(this, 0.25f) {
			@Override
			public void start() {
				super.start();
				toggleAttack(true);
			}
		});
		goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8));
		goalSelector.add(3, new LookAroundGoal(this));
		targetSelector.add(0, new RevengeGoal(this) {
			@Override
			public void start() {
				super.start();
				toggleAttack(true);
			}
		});
		targetSelector.add(1, new ActiveTargetGoal<>(this, LivingEntity.class, 10, true, false, entity -> entity instanceof PlayerEntity || entity instanceof SheepEntity || entity instanceof MerchantEntity || entity instanceof IllagerEntity || entity instanceof WitchEntity) {
			@Override
			public void start() {
				super.start();
				toggleAttack(true);
			}
		});
	}

	@Override
	public int getVariants() {
		return 5;
	}


	public ItemStack getPickedResult(HitResult target) {
		return new ItemStack(BWPObjects.BLACK_DOG_SPAWN_EGG.asItem());
	}

}
