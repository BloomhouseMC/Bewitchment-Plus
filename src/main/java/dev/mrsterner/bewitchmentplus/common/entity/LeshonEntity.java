package dev.mrsterner.bewitchmentplus.common.entity;

import dev.mrsterner.bewitchmentplus.common.entity.ai.LeshonMeleeAttackGoal;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

@SuppressWarnings("ALL")
public class LeshonEntity extends HostileEntity implements GeoEntity {
    public static final TrackedData<Boolean> ATTACKING = DataTracker.registerData(LeshonEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public Vec3d motionCalc = new Vec3d(0,0,0);
    public boolean isAttacking = false;

    public LeshonEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
        .add(EntityAttributes.GENERIC_MAX_HEALTH, 20)
        .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 10)
        .add(EntityAttributes.GENERIC_ARMOR, 20)
        .add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, 10)
        .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4);
    }

    @Override
    protected void initGoals() {
        goalSelector.add(0, new SwimGoal(this));
        goalSelector.add(1, new LeshonMeleeAttackGoal(this, 1, true));
        goalSelector.add(2, new WanderAroundFarGoal(this, 1));
        goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8));
        goalSelector.add(3, new LookAroundGoal(this));
        targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        targetSelector.add(1, new RevengeGoal(this));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ATTACKING, false);
    }

    public boolean getAttckingState() {
        return this.dataTracker.get(ATTACKING);
    }

    public void setAttackingState(boolean isAttacking) {
        this.dataTracker.set(ATTACKING, isAttacking);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController(this, "DevMovement", 2, this::devMovement));
    }

    private PlayState devMovement(AnimationState state) {
        boolean isMovingHorizontal = Math.sqrt(Math.pow(motionCalc.x, 2) + Math.pow(motionCalc.z, 2)) > 0.005;
        if (this.isSleeping()) {
            state.setAnimation(RawAnimation.begin().thenLoop("animation.leshon.quad.sleep"));
        }else if (this.getPose() == EntityPose.SWIMMING || this.touchingWater) {
            state.setAnimation(RawAnimation.begin().thenLoop("animation.leshin.standing.swim"));
        }else if (!this.isOnGround() && motionCalc.getY() < -0.6) {
            if (!this.isClimbing()) {
                state.setAnimation(RawAnimation.begin().thenLoop("animation.leshon.standing.fall"));
            }
        }else if(this.handSwinging){
            state.setAnimation(RawAnimation.begin().then("animation.leshon.standing.attack", Animation.LoopType.PLAY_ONCE));
        } else if (this.isSneaking()) {
            if (isMovingHorizontal) {
                if(this.forwardSpeed < 0){
                    state.setAnimation(RawAnimation.begin().thenLoop("animation.leshon.standing.sneakDev_back"));
                }else{
                    state.setAnimation(RawAnimation.begin().thenLoop("animation.leshon.standing.sneakDev"));
                }
            } else {
                state.setAnimation(RawAnimation.begin().thenLoop("animation.leshon.standing.sneak_idle"));
            }
        }else {
            if (this.isSprinting()) {
                state.setAnimation(RawAnimation.begin().thenLoop("animation.leshon.quad.runningDev"));
                if(this.handSwinging){
                    state.setAnimation(RawAnimation.begin().then("animation.leshon.quad.attack", Animation.LoopType.PLAY_ONCE));
                }
            }else if(this.forwardSpeed < 0){
                state.setAnimation(RawAnimation.begin().thenLoop("animation.leshon.standing.walk_back"));
            }else if (isMovingHorizontal || state.isMoving()) {
                state.setAnimation(RawAnimation.begin().thenLoop("animation.leshon.standing.walk"));
            }
        }
        if(state.getController().getCurrentAnimation() == null){
            state.setAnimation(RawAnimation.begin().thenLoop("animation.leshon.standing.idle"));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
