package dev.mrsterner.bewitchmentplus.common.entity.ai;

import dev.mrsterner.bewitchmentplus.common.entity.LeshonEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;

import java.util.EnumSet;

public class LeshonMeleeAttackGoal extends MeleeAttackGoal {
    protected final LeshonEntity mob;

    public LeshonMeleeAttackGoal(LeshonEntity mob, double speed, boolean pauseWhenMobIdle) {
        super(mob, speed, pauseWhenMobIdle);
        this.mob = mob;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
    }

    @Override
    public void start() {
        super.start();
        this.mob.setAttacking(true);
        this.mob.setAttackingState(true);
    }

    @Override
    public void stop() {
        super.stop();
        this.mob.setAttacking(false);
        this.mob.setAttackingState(false);
    }
}