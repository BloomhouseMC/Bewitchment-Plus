package net.bewitchmentplus.common.entity.living;

import moriyashiine.bewitchment.api.BewitchmentAPI;
import net.minecraft.entity.EntityGroup;

public class RubezahlEntity {

	public EntityGroup getGroup() {
		return BewitchmentAPI.DEMON;
	}

	@Override
	protected boolean hasShiny() {
		return true;
	}
}
