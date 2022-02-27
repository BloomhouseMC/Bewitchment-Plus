package dev.mrsterner.bewitchmentplus.common.entity.ai;

import dev.mrsterner.bewitchmentplus.common.entity.NifflerEntity;
import net.minecraft.entity.ai.goal.Goal;

import static dev.mrsterner.bewitchmentplus.common.entity.NifflerEntity.SLEEPING;

public class NifflerWakeGoal extends Goal {
    private NifflerEntity niffler;

    public NifflerWakeGoal(NifflerEntity niffler){
        this.niffler = niffler;
    }

    @Override
    public void start() {
        niffler.getDataTracker().set(SLEEPING, false);
    }

    @Override
    public boolean canStart() {
        return !niffler.world.isNight();
    }

    @Override
    public boolean shouldContinue() {
        return !niffler.world.isNight();
    }
}
