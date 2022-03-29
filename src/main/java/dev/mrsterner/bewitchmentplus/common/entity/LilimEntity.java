package dev.mrsterner.bewitchmentplus.common.entity;

import moriyashiine.bewitchment.api.BewitchmentAPI;
import moriyashiine.bewitchment.common.entity.living.LeonardEntity;
import moriyashiine.bewitchment.common.entity.living.VampireEntity;
import moriyashiine.bewitchment.common.entity.living.util.BWHostileEntity;
import moriyashiine.bewitchment.common.registry.BWComponents;
import moriyashiine.bewitchment.common.registry.BWSoundEvents;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class LilimEntity extends BWHostileEntity {
	public static final TrackedData<Boolean> HAS_TARGET = DataTracker.registerData(VampireEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	private boolean onFireFromSun = false;

	public LilimEntity(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
	}

	public void tick() {
		super.tick();
		if (!this.world.isClient) {
			BWComponents.BLOOD_COMPONENT.maybeGet(this).ifPresent((bloodComponent) -> {
				if (this.getHealth() < this.getMaxHealth() && (this.age + this.getId()) % 40 == 0 && bloodComponent.getBlood() > 0) {
					this.heal(1.0F);
					if (this.random.nextFloat() < 0.25F) {
						bloodComponent.drainBlood(1, false);
					}
				}

			});
			if (!this.hasCustomName() && this.world.isDay() && !this.world.isRaining() && this.world.isSkyVisible(this.getBlockPos())) {
				this.setOnFireFor(8);
				this.onFireFromSun = true;
			}
		}

	}

	public void setTarget(@Nullable LivingEntity target) {
		super.setTarget(target);
		this.dataTracker.set(HAS_TARGET, target != null);
	}

	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		this.onFireFromSun = nbt.getBoolean("OnFireFromSun");
	}

	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putBoolean("OnFireFromSun", this.onFireFromSun);
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(0, new SwimGoal(this));
		this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0D, true));
		this.goalSelector.add(2, new WanderAroundFarGoal(this, 1.0D));
		this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.add(3, new LookAroundGoal(this));
		this.targetSelector.add(0, new RevengeGoal(this, new Class[0]));
		this.targetSelector.add(3, new ActiveTargetGoal(this, MerchantEntity.class, false));
		this.targetSelector.add(3, new ActiveTargetGoal(this, RaiderEntity.class, false));

	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createMobAttributes()
		.add(EntityAttributes.GENERIC_MAX_HEALTH, 20.00D)
		.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D)
		.add(EntityAttributes.GENERIC_ARMOR, 2.0D)
		.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3D)
		.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.35D)
		.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 24.0D);
	}

	@Nullable
	protected SoundEvent getAmbientSound() {
		return BWSoundEvents.ENTITY_LILITH_AMBIENT;
	}

	protected SoundEvent getHurtSound(DamageSource source) {
		return BWSoundEvents.ENTITY_LILITH_HURT;
	}

	protected SoundEvent getDeathSound() {
		return BWSoundEvents.ENTITY_LILITH_DEATH;
	}

	public EntityGroup getGroup() {
		return BewitchmentAPI.DEMON;
	}

	protected boolean hasShiny() {
		return false;
	}

	@Override
	public int getVariants() {
		return 0;
	}
}
