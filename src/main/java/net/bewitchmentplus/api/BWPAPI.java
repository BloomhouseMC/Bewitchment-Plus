package net.bewitchmentplus.api;

import net.bewitchmentplus.common.registry.BWPTags;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;

public class BWPAPI {
	public static boolean isHoldingSilver(LivingEntity livingEntity, Hand hand) {
		return BWPTags.SILVER_TOOLS.contains(livingEntity.getStackInHand(hand).getItem());
	}
}
