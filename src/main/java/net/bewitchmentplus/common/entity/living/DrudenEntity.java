package net.bewitchmentplus.common.entity.living;

import moriyashiine.bewitchment.api.BewitchmentAPI;
import moriyashiine.bewitchment.common.entity.living.util.BWHostileEntity;
import net.bewitchmentplus.common.registry.BWPObjects;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Fertilizable;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameRules;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

@SuppressWarnings("ALL")
public class DrudenEntity extends BWHostileEntity {
	public int attackTick = 0;

	public DrudenEntity(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
		this.setPathfindingPenalty(PathNodeType.DANGER_OTHER, 0.0F);
		this.setPathfindingPenalty(PathNodeType.DAMAGE_OTHER, 0.0F);
		this.setPathfindingPenalty(PathNodeType.DANGER_CACTUS, 0.0F);
		this.setPathfindingPenalty(PathNodeType.DAMAGE_CACTUS, 0.0F);
		experiencePoints = 5;
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 25.00D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6.0D).add(EntityAttributes.GENERIC_ARMOR, 4.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.35D).add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16.0D);
	}

	public static int getVariantsStatic() {
		return 9;
	}

	public boolean canHaveStatusEffect(StatusEffectInstance effect) {
		return effect.getEffectType() == StatusEffects.SLOWNESS ? false : super.canHaveStatusEffect(effect);
	}

	@Override
	public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
		return false;
	}

	public void tick() {
		super.tick();
		if (this.isOnFire())
			this.applyDamage(DamageSource.ON_FIRE, 6);
	}

	@Override
	public void baseTick() {
		super.baseTick();
		BlockPos pos = new BlockPos.Mutable(this.getX(), this.getY(), this.getZ());
		BlockState blockState = world.getBlockState(pos);


		if (!this.isAttacking()) {
			if (blockState.getBlock() instanceof Fertilizable) {
				if (world instanceof ServerWorld) {
					Fertilizable fertilizable = (Fertilizable) blockState.getBlock();
					if (fertilizable.isFertilizable(world, pos, blockState, world.isClient)) {
						if (world instanceof ServerWorld) {
							if (fertilizable.canGrow(world, world.random, pos, blockState)) {
								fertilizable.grow((ServerWorld) world, world.random, pos, blockState);
							}
						}
					}
				}
			}
		}
		if (this.isAttacking()) {
			if (!this.world.isClient) {
				int i = MathHelper.floor(this.getX());
				int j = MathHelper.floor(this.getY());
				int k = MathHelper.floor(this.getZ());

				if (!this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
					return;
				}

				blockState = Blocks.SWEET_BERRY_BUSH.getDefaultState();

				for (int l = 0; l < 4; ++l) {
					i = MathHelper.floor(this.getX() + (double) ((float) (l % 2 * 2 - 1) * 0.25F));
					j = MathHelper.floor(this.getY());
					k = MathHelper.floor(this.getZ() + (double) ((float) (l / 2 % 2 * 2 - 1) * 0.25F));
					BlockPos blockPos = new BlockPos(i, j, k);
					if (this.world.getBlockState(blockPos).isAir() && blockState.canPlaceAt(this.world, blockPos)) {
						this.world.setBlockState(blockPos, blockState);
					}
				}
			}
		}
	}

	@Override
	public boolean tryAttack(Entity target) {
		boolean flag = super.tryAttack(target);
		Random rand = new Random();
		int i = rand.nextInt(100);
		if (i <= 10) {
			toggleAttack(false);
			if (target instanceof LivingEntity) {
				((LivingEntity) target).addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 100));
			}
		}
		return flag;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.BLOCK_BAMBOO_BREAK;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.BLOCK_BAMBOO_HIT;
	}

	public void toggleAttack(boolean attacking) {
		if (attacking) {
			attackTick = 11;
			world.sendEntityStatus(this, (byte) 4);
		} else {
			attackTick = 2;
			world.sendEntityStatus(this, (byte) 5);
		}
	}

	@Override
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable CompoundTag entityTag) {
		EntityData data = super.initialize(world, difficulty, spawnReason, entityData, entityTag);
		Random rand = new Random();
		int i = rand.nextInt(6);
		if (i == 3) {
			this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(BWPObjects.THYRSUS_ITEM));
			if (dataTracker.get(VARIANT) != 0) {
				switch (world.getBiome(getBlockPos()).getCategory()) {
					case FOREST:
						dataTracker.set(VARIANT, random.nextInt(getVariants() - 4) + 4);
						break;
					case TAIGA:
						dataTracker.set(VARIANT, random.nextInt(getVariants() - 5) + 5);
						break;
					case ICY:
						dataTracker.set(VARIANT, random.nextInt(getVariants() - 5) + 5);
						break;
					case SWAMP:
						dataTracker.set(VARIANT, random.nextInt(getVariants() - 5) + 5);
						break;
					default:
						dataTracker.set(VARIANT, random.nextInt(getVariants() - 1) + 1);
						break;
				}
			}
			return data;
		}
		return data;
	}

	@Override
	protected void initGoals() {
		goalSelector.add(0, new SwimGoal(this));
		goalSelector.add(1, new MeleeAttackGoal(this, 1, true));
		goalSelector.add(2, new WanderAroundFarGoal(this, 1));
		goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8));
		goalSelector.add(3, new LookAroundGoal(this));
		targetSelector.add(0, new RevengeGoal(this));
		targetSelector.add(1, new FollowTargetGoal<>(this, LivingEntity.class, 10, true, false, entity -> entity instanceof PlayerEntity || entity instanceof MerchantEntity || entity.getGroup() == EntityGroup.ILLAGER));
	}

	@Override
	protected boolean hasShiny() {
		return true;
	}

	public EntityGroup getGroup() {
		return BewitchmentAPI.DEMON;
	}

	@Override
	public int getVariants() {
		return getVariantsStatic();
	}
}
