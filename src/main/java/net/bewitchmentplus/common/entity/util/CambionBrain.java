package net.bewitchmentplus.common.entity.util;

import net.bewitchmentplus.common.entity.living.CambionEntity;
import net.bewitchmentplus.common.registry.BWPTags;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.Hand;

import java.util.Collections;

public class CambionBrain {
	public static final Item BARTERING_ITEM;

	protected static Brain<?> create(CambionEntity cambion, Brain<CambionEntity> brain) {
		return brain;
	}

	private static boolean acceptsForBarter(Item item) {
		return item == BARTERING_ITEM;
	}

	protected static void consumeOffHandItem(CambionEntity cambion, boolean bl) {
		ItemStack itemStack = cambion.getStackInHand(Hand.OFF_HAND);
		cambion.setStackInHand(Hand.OFF_HAND, ItemStack.EMPTY);
		boolean bl2;
		if (cambion.isAdult()) {
			bl2 = acceptsForBarter(itemStack.getItem());
			if (bl && bl2) {
				doBarter(cambion, getBarteredItem(cambion));
			} else if (!bl2) {
				boolean bl3 = cambion.tryEquip(itemStack);
				if (!bl3) {
					barterItem(cambion, itemStack);
				}
			}
		} else {
			bl2 = cambion.tryEquip(itemStack);
			if (!bl2) {
				ItemStack itemStack2 = cambion.getMainHandStack();
				if (isValidCambionTag(itemStack2.getItem())) {
					barterItem(cambion, itemStack2);
				} else {
					doBarter(cambion, Collections.singletonList(itemStack2));
				}

				cambion.equipToMainHand(itemStack);
			}
		}

	}

	protected static boolean isValidCambionTag(Item item) {
		return item.isIn((Tag) BWPTags.CAMBION_LOVED);
	}

	protected static boolean isWillingToTrade(CambionEntity cambion, ItemStack nearbyItems) {
		return acceptsForBarter(nearbyItems.getItem());
	}
}
