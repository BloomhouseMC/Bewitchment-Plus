package dev.mrsterner.bewitchmentplus.common.entity;

import moriyashiine.bewitchment.api.entity.BroomEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class YewBroomEntity extends BroomEntity {
    public YewBroomEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    protected float getSpeed() {
        return 0.25f;
    }
}
