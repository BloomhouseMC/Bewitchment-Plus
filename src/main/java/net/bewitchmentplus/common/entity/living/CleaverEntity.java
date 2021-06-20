package net.bewitchmentplus.common.entity.living;

import moriyashiine.bewitchment.api.BewitchmentAPI;
import moriyashiine.bewitchment.common.entity.living.util.BWHostileEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class CleaverEntity extends BWHostileEntity {
	protected CleaverEntity(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
	}

	public EntityGroup getGroup() {
		return BewitchmentAPI.DEMON;
	}

	@Override
	public boolean isFireImmune() {
		return true;
	}

	@Override
	public boolean canUsePortals() {
		return true;
	}

	@Override
	public boolean tryAttack(Entity target) {
		boolean flag = super.tryAttack(target);
		if (flag && target instanceof LivingEntity) {
			swingHand(Hand.MAIN_HAND);
		}
		return flag;
	}

	@Override
	protected boolean hasShiny() {
		return true;
	}

	@Override
	public int getVariants() {
		return 0;
	}
}
