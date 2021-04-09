package net.bewitchmentplus.common.entity.living;

import net.minecraft.entity.EntityGroup;

public class VengefulGhostEntity {

	public EntityGroup getGroup() {
		return EntityGroup.UNDEAD;
	}


	protected boolean hasShiny() {
		return true;
	}
}
