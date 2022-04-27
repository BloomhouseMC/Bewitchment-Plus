package dev.mrsterner.bewitchmentplus.common.entity.navigation;

import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeNavigator;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FlyWalkEntityNavigation extends EntityNavigation {
    public FlyWalkEntityNavigation(MobEntity mob, World world) {
        super(mob, world);
    }

    @Override
    protected PathNodeNavigator createPathNodeNavigator(int range) {
        return null;
    }

    @Override
    protected Vec3d getPos() {
        return null;
    }

    @Override
    protected boolean isAtValidPosition() {
        return false;
    }
}
