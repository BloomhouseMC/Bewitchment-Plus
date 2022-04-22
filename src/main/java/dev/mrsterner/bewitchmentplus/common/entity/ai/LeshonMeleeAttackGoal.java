package dev.mrsterner.bewitchmentplus.common.entity.ai;

import dev.mrsterner.bewitchmentplus.common.entity.LeshonEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.mob.PathAwareEntity;

import java.util.EnumSet;

public class LeshonMeleeAttackGoal extends MeleeAttackGoal {
    protected final LeshonEntity mob;
    private double moveSpeedAmp = 1;
    private int attackTime = -1;

    public LeshonMeleeAttackGoal(LeshonEntity mob, double speed, boolean pauseWhenMobIdle) {
        super(mob, speed, pauseWhenMobIdle);
        this.mob = mob;
        this.moveSpeedAmp = speed;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
    }


    public boolean canStart() {
        return this.mob.getTarget() != null;
    }

    public boolean shouldContinue() {
        return this.canStart();
    }

    public void start() {
        super.start();
        this.mob.setAttacking(true);
    }

    public void stop() {
        super.stop();
        this.mob.setAttacking(false);
        this.mob.setAttackingState(false);
        this.attackTime = -1;
    }

    public void tick() {
        LivingEntity livingentity = this.mob.getTarget();
        if (livingentity != null) {
            boolean inLineOfSight = this.mob.getVisibilityCache().canSee(livingentity);
            this.attackTime++;
            this.mob.lookAtEntity(livingentity, 30.0F, 30.0F);
            double d0 = this.mob.squaredDistanceTo(livingentity.getX(), livingentity.getY(), livingentity.getZ());
            double d1 = livingentity.getWidth() * 2.5F * livingentity.getWidth() * 2.5F + livingentity.getWidth();
            if (inLineOfSight) {
                if (this.mob.distanceTo(livingentity) >= 3.0D) {
                    this.mob.getNavigation().startMovingTo(livingentity, this.moveSpeedAmp);
                    this.attackTime = -5;
                } else {
                    if (this.attackTime == 4) {
                        this.mob.getNavigation().startMovingTo(livingentity, this.moveSpeedAmp);
                        if (d0 <= d1) {
                            this.mob.tryAttack(livingentity);
                            this.mob.setAttackingState(true);
                        }
                        livingentity.timeUntilRegen = 0;
                    }
                    if (this.attackTime == 8) {
                        this.attackTime = -5;
                        this.mob.setAttackingState(false);
                    }
                }
            }
        }
    }
}