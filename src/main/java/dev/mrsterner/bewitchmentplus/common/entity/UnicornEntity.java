package dev.mrsterner.bewitchmentplus.common.entity;

import dev.mrsterner.bewitchmentplus.common.registry.BWPObjects;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;

public class UnicornEntity extends PassiveEntity {
    protected int soundTicks;
    private int angryTicks;
    public int tailWagTicks;
    private int eatingTicks;
    private int eatingGrassTicks;
    private float eatingGrassAnimationProgress;
    private float lastEatingGrassAnimationProgress;
    private float angryAnimationProgress;
    private float lastAngryAnimationProgress;
    private float eatingAnimationProgress;
    private float lastEatingAnimationProgress;
    private static final TrackedData<Byte> HORSE_FLAGS = DataTracker.registerData(UnicornEntity.class, TrackedDataHandlerRegistry.BYTE);

    public UnicornEntity(EntityType<? extends PassiveEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, 16.0F);
        this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, -1.0F);
    }

    public ItemStack getPickedResult(HitResult target) {
        return new ItemStack(BWPObjects.UNICORN_SPAWN_EGG.asItem());
    }
    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.2D));
        this.goalSelector.add(6, new WanderAroundFarGoal(this, 0.7D));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
        this.initCustomGoals();
    }

    protected void initCustomGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(3, new TemptGoal(this, 1.25D, Ingredient.ofItems(Items.GOLDEN_CARROT, Items.GOLDEN_APPLE, Items.ENCHANTED_GOLDEN_APPLE), false));
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return PassiveEntity.createMobAttributes()
        .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.00D)
        .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D)
        .add(EntityAttributes.GENERIC_ARMOR, 2.0D)
        .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3D)
        .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.35D)
        .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 24.0D);
    }
    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("EatingHaystack", this.isEatingGrass());
    }
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setEatingGrass(nbt.getBoolean("EatingHaystack"));
    }

    @Override
    public void onDeath(DamageSource source) {
        if(world.getBlockState(this.getBlockPos()).isAir()) world.setBlockState(this.getBlockPos(), BWPObjects.UNICORN_BLOOD_PUDDLE.getDefaultState());
        super.onDeath(source);
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        if (fallDistance > 1.0F) {
            this.playSound(SoundEvents.ENTITY_HORSE_LAND, 0.4F, 1.0F);
        }

        int i = this.computeFallDamage(fallDistance, damageMultiplier);
        if (i <= 0) {
            return false;
        } else {
            this.damage(damageSource, (float)i);
            if (this.hasPassengers()) {
                Iterator var5 = this.getPassengersDeep().iterator();

                while(var5.hasNext()) {
                    Entity entity = (Entity)var5.next();
                    entity.damage(damageSource, (float)i);
                }
            }

            this.playBlockFallSound();
            return true;
        }
    }

    @Override
    protected int computeFallDamage(float fallDistance, float damageMultiplier) {
        return MathHelper.ceil((fallDistance * 0.5F - 3.0F) * damageMultiplier);
    }


    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        return ActionResult.PASS;
    }

    @Nullable
    protected SoundEvent getEatSound() {
        return null;
    }

    @Nullable
    protected SoundEvent getDeathSound() {
        return null;
    }

    @Nullable
    protected SoundEvent getHurtSound(DamageSource source) {
        if (this.random.nextInt(3) == 0) {
            this.updateAnger();
        }

        return null;
    }

    @Nullable
    protected SoundEvent getAmbientSound() {
        if (this.random.nextInt(10) == 0 && !this.isImmobile()) {
            this.updateAnger();
        }

        return null;
    }

    @Nullable
    protected SoundEvent getAngrySound() {
        this.updateAnger();
        return null;
    }

    @Override
    protected boolean isImmobile() {
        return super.isImmobile() && this.hasPassengers() || this.isEatingGrass() || this.isAngry();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        if (!state.getMaterial().isLiquid()) {
            BlockState blockState = this.world.getBlockState(pos.up());
            BlockSoundGroup blockSoundGroup = state.getSoundGroup();
            if (blockState.isOf(Blocks.SNOW)) {
                blockSoundGroup = blockState.getSoundGroup();
            }

            if (blockSoundGroup == BlockSoundGroup.WOOD) {
                this.playSound(SoundEvents.ENTITY_HORSE_STEP_WOOD, blockSoundGroup.getVolume() * 0.15F, blockSoundGroup.getPitch());
            } else {
                this.playSound(SoundEvents.ENTITY_HORSE_STEP, blockSoundGroup.getVolume() * 0.15F, blockSoundGroup.getPitch());
            }

        }
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(HORSE_FLAGS, (byte)0);
    }


    protected boolean getHorseFlag(int bitmask) {
        return(this.dataTracker.get(HORSE_FLAGS) & bitmask) != 0;
    }

    protected void setHorseFlag(int bitmask, boolean flag) {
        byte b = this.dataTracker.get(HORSE_FLAGS);
        if (flag) {
            this.dataTracker.set(HORSE_FLAGS, (byte)(b | bitmask));
        } else {
            this.dataTracker.set(HORSE_FLAGS, (byte)(b & ~bitmask));
        }

    }


    private void updateAnger() {
        if (this.isLogicalSideForUpdatingMovement() || this.canMoveVoluntarily()) {
            this.angryTicks = 1;
            this.setAngry(true);
        }

    }

    private void wagTail() {
        this.tailWagTicks = 1;
    }

    public boolean eatsGrass() {
        return true;
    }

    public void setEatingGrass(boolean eatingGrass) {
        this.setHorseFlag(16, eatingGrass);
    }

    @Override
    public void tickMovement() {
        if (this.random.nextInt(200) == 0) {
            this.wagTail();
        }

        super.tickMovement();
        if (!this.world.isClient && this.isAlive()) {
            if (this.random.nextInt(900) == 0 && this.deathTime == 0) {
                this.heal(1.0F);
            }

            if (this.eatsGrass()) {
                if (!this.isEatingGrass() && !this.hasPassengers() && this.random.nextInt(300) == 0 && this.world.getBlockState(this.getBlockPos().down()).isOf(Blocks.GRASS_BLOCK)) {
                    this.setEatingGrass(true);
                }

                if (this.isEatingGrass() && ++this.eatingGrassTicks > 50) {
                    this.eatingGrassTicks = 0;
                    this.setEatingGrass(false);
                }
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.eatingTicks > 0 && ++this.eatingTicks > 30) {
            this.eatingTicks = 0;
            this.setHorseFlag(64, false);
        }

        if ((this.isLogicalSideForUpdatingMovement() || this.canMoveVoluntarily()) && this.angryTicks > 0 && ++this.angryTicks > 20) {
            this.angryTicks = 0;
            this.setAngry(false);
        }

        if (this.tailWagTicks > 0 && ++this.tailWagTicks > 8) {
            this.tailWagTicks = 0;
        }

        this.lastEatingGrassAnimationProgress = this.eatingGrassAnimationProgress;
        if (this.isEatingGrass()) {
            this.eatingGrassAnimationProgress += (1.0F - this.eatingGrassAnimationProgress) * 0.4F + 0.05F;
            if (this.eatingGrassAnimationProgress > 1.0F) {
                this.eatingGrassAnimationProgress = 1.0F;
            }
        } else {
            this.eatingGrassAnimationProgress += (0.0F - this.eatingGrassAnimationProgress) * 0.4F - 0.05F;
            if (this.eatingGrassAnimationProgress < 0.0F) {
                this.eatingGrassAnimationProgress = 0.0F;
            }
        }

        this.lastAngryAnimationProgress = this.angryAnimationProgress;
        if (this.isAngry()) {
            this.eatingGrassAnimationProgress = 0.0F;
            this.lastEatingGrassAnimationProgress = this.eatingGrassAnimationProgress;
            this.angryAnimationProgress += (1.0F - this.angryAnimationProgress) * 0.4F + 0.05F;
            if (this.angryAnimationProgress > 1.0F) {
                this.angryAnimationProgress = 1.0F;
            }
        } else {
            this.jumping = false;
            this.angryAnimationProgress += (0.8F * this.angryAnimationProgress * this.angryAnimationProgress * this.angryAnimationProgress - this.angryAnimationProgress) * 0.6F - 0.05F;
            if (this.angryAnimationProgress < 0.0F) {
                this.angryAnimationProgress = 0.0F;
            }
        }

        this.lastEatingAnimationProgress = this.eatingAnimationProgress;
        if (this.getHorseFlag(64)) {
            this.eatingAnimationProgress += (1.0F - this.eatingAnimationProgress) * 0.7F + 0.05F;
            if (this.eatingAnimationProgress > 1.0F) {
                this.eatingAnimationProgress = 1.0F;
            }
        } else {
            this.eatingAnimationProgress += (0.0F - this.eatingAnimationProgress) * 0.7F - 0.05F;
            if (this.eatingAnimationProgress < 0.0F) {
                this.eatingAnimationProgress = 0.0F;
            }
        }

    }
    public float getAngryAnimationProgress(float tickDelta) {
        return MathHelper.lerp(tickDelta, this.lastAngryAnimationProgress, this.angryAnimationProgress);
    }

    public float getEatingAnimationProgress(float tickDelta) {
        return MathHelper.lerp(tickDelta, this.lastEatingAnimationProgress, this.eatingAnimationProgress);
    }

    public float getEatingGrassAnimationProgress(float tickDelta) {
        return MathHelper.lerp(tickDelta, this.lastEatingGrassAnimationProgress, this.eatingGrassAnimationProgress);
    }
    public void setAngry(boolean angry) {
        if (angry) {
            this.setEatingGrass(false);
        }

        this.setHorseFlag(32, angry);
    }

    public boolean isEatingGrass() {
        return this.getHorseFlag(16);
    }

    public boolean isAngry() {
        return this.getHorseFlag(32);
    }

}
