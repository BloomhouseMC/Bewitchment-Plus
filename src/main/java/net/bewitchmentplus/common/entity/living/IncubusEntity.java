package net.bewitchmentplus.common.entity.living;

import moriyashiine.bewitchment.api.BewitchmentAPI;
import moriyashiine.bewitchment.common.entity.living.util.BWHostileEntity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.World;

public class IncubusEntity extends BWHostileEntity {

	public IncubusEntity(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
	}

	public EntityGroup getGroup() {
		return BewitchmentAPI.DEMON;
	}

	protected boolean hasShiny() {
		return true;
	}

	@Override
	public boolean canUsePortals() {
		return true;
	}

	@Override
	public int getVariants() {
		return 0;
	}
}
