package dev.mrsterner.bewitchmentplus.common.entity;

import dev.mrsterner.bewitchmentplus.common.entity.ai.PhoenixRebirthGoal;
import dev.mrsterner.bewitchmentplus.common.registry.BWPObjects;
import moriyashiine.bewitchment.common.entity.living.util.BWTameableEntity;
import moriyashiine.bewitchment.common.registry.BWSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.FuzzyTargeting;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class PhoenixEntity extends BWTameableEntity implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);
    private static final TrackedData<Boolean> FLYING = DataTracker.registerData(PhoenixEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public PhoenixEntity(EntityType<? extends TameableEntity> type, World world) {
        super(type, world);
        this.moveControl = new FlightMoveControl(this, 180, false);
    }

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(FLYING, false);
        super.initDataTracker();
    }

    public ItemStack getPickedResult(HitResult target) {
        return new ItemStack(BWPObjects.PHOENIX_SPAWN_EGG.asItem());
    }
    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
        .add(EntityAttributes.GENERIC_MAX_HEALTH, 8.0D)
        .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1.0D)
        .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D)
        .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.6D);
    }


    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new PhoenixRebirthGoal(this));
        this.goalSelector.add(1, new SitGoal(this));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.add(3, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.add(4, new AnimalMateGoal(this, 1.0D));
        this.goalSelector.add(5, new PhoenixEntity.WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.targetSelector.add(0, new TrackOwnerAttackerGoal(this));
        this.targetSelector.add(1, new AttackWithOwnerGoal(this));
        this.targetSelector.add(2, new RevengeGoal(this));
        this.targetSelector.add(3, new UntamedActiveTargetGoal<>(this, AnimalEntity.class, false, (entity) -> entity instanceof ChickenEntity || entity instanceof RabbitEntity));
    }

    @Override
    protected boolean hasShiny() {
        return false;
    }

    protected void addFlapEffects() {
        this.playSound(BWSoundEvents.ENTITY_OWL_FLY, 0.15F, 1.0F);
    }

    protected boolean hasWings() {
        return true;
    }

    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
    }

    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world);
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanSwim(true);
        birdNavigation.setCanEnterOpenDoors(true);
        return birdNavigation;
    }



    @Override
    public int getVariants() {
        return 0;
    }

    @Override
    protected boolean isTamingItem(ItemStack itemStack) {
        return false;
    }



    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }



    private <E extends IAnimatable> PlayState basicMovement(AnimationEvent<E> event) {
        if(this.getDataTracker().get(FLYING)){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("phoenix_flying", true));
        }else{
            event.getController().setAnimation(new AnimationBuilder().addAnimation("phoenix_idle", true));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "BasicMovement", 0, this::basicMovement));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    public class WanderAroundFarGoal extends WanderAroundGoal {
        private PhoenixEntity phoenixEntity;
        public static final float CHANCE = 0.001F;
        protected final float probability;

        @Override
        public void start() {
            phoenixEntity.getDataTracker().set(FLYING, true);
            super.start();
        }

        @Override
        public void stop() {
            phoenixEntity.getDataTracker().set(FLYING, false);
            super.stop();
        }

        public WanderAroundFarGoal(PhoenixEntity pathAwareEntity, double d) {
            this(pathAwareEntity, d, 0.001F);
        }

        public WanderAroundFarGoal(PhoenixEntity mob, double speed, float probability) {
            super(mob, speed);
            this.probability = probability;
            this.phoenixEntity = mob;
        }

        @Nullable
        protected Vec3d getWanderTarget() {
            if (this.mob.isInsideWaterOrBubbleColumn()) {
                Vec3d vec3d = FuzzyTargeting.find(this.mob, 15, 7);
                return vec3d == null ? super.getWanderTarget() : vec3d;
            } else {
                return this.mob.getRandom().nextFloat() >= this.probability ? FuzzyTargeting.find(this.mob, 10, 7) : super.getWanderTarget();
            }
        }
    }
}
