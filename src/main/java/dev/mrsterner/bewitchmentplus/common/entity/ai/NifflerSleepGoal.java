package dev.mrsterner.bewitchmentplus.common.entity.ai;

import dev.mrsterner.bewitchmentplus.common.entity.NifflerEntity;
import net.minecraft.entity.ai.goal.Goal;

import static dev.mrsterner.bewitchmentplus.common.entity.NifflerEntity.SLEEPING;

public class NifflerSleepGoal extends Goal {
    private final NifflerEntity niffler;

    public NifflerSleepGoal(NifflerEntity niffler){
        this.niffler = niffler;
    }

    @Override
    public void start() {
        niffler.getDataTracker().set(SLEEPING, true);
    }

    @Override
    public boolean canStart() {
        return niffler.world.isNight();//TODO sleep in safe location
    }

    @Override
    public boolean shouldContinue() {
        return niffler.world.isNight();
    }
}
