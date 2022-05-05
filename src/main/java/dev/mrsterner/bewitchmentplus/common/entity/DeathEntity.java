package dev.mrsterner.bewitchmentplus.common.entity;

import dev.mrsterner.bewitchmentplus.common.entity.ai.PhoenixFlyGoal;
import dev.mrsterner.bewitchmentplus.common.entity.ai.PhoenixRebirthGoal;
import dev.mrsterner.bewitchmentplus.common.registry.BWPObjects;
import moriyashiine.bewitchment.common.entity.living.util.BWTameableEntity;
import moriyashiine.bewitchment.common.registry.BWSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.FuzzyTargeting;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Arm;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
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

import java.util.EnumSet;
import java.util.function.Predicate;

public class DeathEntity extends HostileEntity implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);
    private static final Predicate<LivingEntity> CAN_ATTACK_PREDICATE = entity -> entity.getGroup() != EntityGroup.UNDEAD && entity.isMobOrPlayer();
    private final DefaultedList<ItemStack> handItems = DefaultedList.ofSize(2, ItemStack.EMPTY);
    private final DefaultedList<ItemStack> armorItems = DefaultedList.ofSize(4, ItemStack.EMPTY);

    public DeathEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FlightMoveControl(this, 10, false);
    }




    @Override
    protected void initGoals() {
        super.initGoals();
        //this.goalSelector.add(1, new DeathHoverGoal(this, 1));
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1, true));//TODO make death sickle attack goal instead
        this.goalSelector.add(1, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(2, new LookAroundGoal(this));
        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new ActiveTargetGoal(this, LivingEntity.class, 0, false, false, CAN_ATTACK_PREDICATE));
    }

    @Override
    public Iterable<ItemStack> getArmorItems() {
        return this.armorItems;
    }

    @Override
    public ItemStack getEquippedStack(EquipmentSlot slot) {
        return switch (slot.getType()) {
            case HAND -> this.handItems.get(slot.getEntitySlotId());
            case ARMOR -> this.armorItems.get(slot.getEntitySlotId());
        };
    }

    @Override
    protected boolean isImmobile() {
        return true;
    }



    @Override
    public void equipStack(EquipmentSlot slot, ItemStack stack) {
        this.processEquippedStack(stack);
        switch (slot.getType()) {
            case HAND -> this.handItems.set(slot.getEntitySlotId(), stack);
            case ARMOR -> this.armorItems.set(slot.getEntitySlotId(), stack);
        }
    }

    @Override
    public Arm getMainArm() {
        return Arm.RIGHT;
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 1000.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, Double.MAX_VALUE);
    }

    private <E extends IAnimatable> PlayState basicMovement(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.death.idle", true));
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

    static class DeathHoverGoal extends FlyGoal {
        public DeathHoverGoal(PathAwareEntity pathAwareEntity, double d) {
            super(pathAwareEntity, d);
            this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.JUMP, Goal.Control.LOOK));
        }

    }
}