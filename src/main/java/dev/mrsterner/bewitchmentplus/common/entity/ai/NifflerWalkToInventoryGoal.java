package dev.mrsterner.bewitchmentplus.common.entity.ai;

import dev.mrsterner.bewitchmentplus.common.entity.NifflerEntity;
import net.minecraft.entity.ai.goal.Goal;

public class NifflerWalkToInventoryGoal extends Goal {
    private final NifflerEntity niffler;

    public NifflerWalkToInventoryGoal(NifflerEntity niffler){
        this.niffler = niffler;
    }


    @Override
    public boolean canStart() {
        return false;
    }
}
