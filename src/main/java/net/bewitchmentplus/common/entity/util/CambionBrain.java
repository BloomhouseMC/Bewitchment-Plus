package net.bewitchmentplus.common.entity.util;

import net.bewitchmentplus.common.entity.living.CambionEntity;
import net.bewitchmentplus.common.registry.BWPTags;
import net.minecraft.entity.ai.TargetFinder;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.Tag;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

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

	private static void barterItem(CambionEntity cambion, ItemStack stack) {
		ItemStack itemStack = cambion.addItem(stack);
		dropBarteredItem(cambion, Collections.singletonList(itemStack));
	}

	private static void doBarter(CambionEntity cambion, List<ItemStack> list) {
		Optional<PlayerEntity> optional = cambion.getBrain().getOptionalMemory(MemoryModuleType.NEAREST_VISIBLE_PLAYER);
		if (optional.isPresent()) {
			dropBarteredItem(cambion, optional.get(), list);
		} else {
			dropBarteredItem(cambion, list);
		}

	}

	private static void drop(CambionEntity cambion, List<ItemStack> list, Vec3d vec3d) {
		if (!list.isEmpty()) {
			cambion.swingHand(Hand.OFF_HAND);
			Iterator var3 = list.iterator();

			while (var3.hasNext()) {
				ItemStack itemStack = (ItemStack) var3.next();
				LookTargetUtil.give(cambion, itemStack, vec3d.add(0.0D, 1.0D, 0.0D));
			}
		}

	}

	private static Vec3d findGround(CambionEntity cambion) {
		Vec3d vec3d = TargetFinder.findGroundTarget(cambion, 4, 2);
		return vec3d == null ? cambion.getPos() : vec3d;
	}

	private static void dropBarteredItem(CambionEntity cambion, List<ItemStack> list) {
		drop(cambion, list, findGround(cambion));
	}

	private static void dropBarteredItem(CambionEntity cambion, PlayerEntity player, List<ItemStack> list) {
		drop(cambion, list, player.getPos());
	}

	private static List<ItemStack> getBarteredItem(CambionEntity cambion) {
		LootTable lootTable = cambion.world.getServer().getLootManager().getTable(BWPLootTables.CAMBION_LOOT_TABLE);
		List<ItemStack> list = lootTable.generateLoot((new LootContext.Builder((ServerWorld) cambion.world)).parameter(LootContextParameters.THIS_ENTITY, cambion).random(cambion.world.random).build(LootContextTypes.BARTER));
		return list;
	}

	protected static boolean isValidCambionTag(Item item) {
		return item.isIn(BWPTags.CAMBION_LOVED);
	}

	protected static boolean isWillingToTrade(CambionEntity cambion, ItemStack nearbyItems) {
		return acceptsForBarter(nearbyItems.getItem());
	}
}
