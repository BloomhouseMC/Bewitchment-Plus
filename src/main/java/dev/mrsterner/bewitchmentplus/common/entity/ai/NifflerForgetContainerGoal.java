package dev.mrsterner.bewitchmentplus.common.entity.ai;

import dev.mrsterner.bewitchmentplus.common.entity.NifflerEntity;
import net.minecraft.entity.ai.goal.Goal;

public class NifflerForgetContainerGoal extends Goal {
    public final NifflerEntity niffler;

    public NifflerForgetContainerGoal(NifflerEntity niffler) {
        this.niffler = niffler;
    }

    @Override
    public boolean canStart() {
        return niffler.blocksChecked.size() >= 8; //TODO more conditions
    }

    @Override
    public void start() {
        this.niffler.blocksChecked.clear();
        super.start();
    }
}
