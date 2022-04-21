package dev.mrsterner.bewitchmentplus.common.entity.ai;

import dev.mrsterner.bewitchmentplus.common.entity.PhoenixEntity;
import net.minecraft.entity.ai.FuzzyTargeting;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import static dev.mrsterner.bewitchmentplus.common.entity.PhoenixEntity.FLYING;

public class PhoenixFlyGoal extends WanderAroundGoal {
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

    public PhoenixFlyGoal(PhoenixEntity phoenixEntity, double d) {
        this(phoenixEntity, d, 0.001F);
    }

    public PhoenixFlyGoal(PhoenixEntity mob, double speed, float probability) {
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