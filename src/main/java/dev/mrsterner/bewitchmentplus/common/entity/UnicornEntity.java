package dev.mrsterner.bewitchmentplus.common.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class UnicornEntity extends HorseBaseEntity {

    public UnicornEntity(EntityType<? extends HorseBaseEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        goalSelector.add(0, new SwimGoal(this));
        goalSelector.add(1, new MeleeAttackGoal(this, 1, true));
        goalSelector.add(2, new WanderAroundFarGoal(this, 1));
        goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8));
        goalSelector.add(3, new LookAroundGoal(this));
        targetSelector.add(0, new RevengeGoal(this));
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

    @Override
    public boolean canBeSaddled() {
        return false;
    }

    @Override
    public void openInventory(PlayerEntity player) {

    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        return ActionResult.PASS;
    }

    @Override
    public ActionResult interactHorse(PlayerEntity player, ItemStack stack) {
        return ActionResult.PASS;
    }
}
