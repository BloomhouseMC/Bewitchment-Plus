package net.bewitchmentplus.common.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

import java.util.Random;

public class ThyrsusItem extends SwordItem {

	public ThyrsusItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
		super(toolMaterial, attackDamage, attackSpeed, settings);
	}

	@Override
	public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
		Random random = new Random();
		if (entity instanceof AnimalEntity) {
			AnimalEntity animalEntity = (AnimalEntity) entity;
			if (animalEntity.isAlive()) {
				animalEntity.setLoveTicks(5000);
				stack.damage(6, random, null);
				user.playSound(SoundEvents.BLOCK_BAMBOO_HIT, 1, 1);
			}
			if (entity instanceof TameableEntity) {
				TameableEntity tameableEntity = (TameableEntity) entity;
				if (!tameableEntity.isTamed()) {
					tameableEntity.setOwnerUuid(user.getUuid());
					tameableEntity.setTamed(true);
					stack.damage(12, random, null);
					user.playSound(SoundEvents.BLOCK_BAMBOO_HIT, 1, 1);
				}
			}
			if (entity instanceof HorseBaseEntity) {
				HorseBaseEntity horseBaseEntity = (HorseBaseEntity) entity;
				if (!horseBaseEntity.isTame()) {
					horseBaseEntity.setOwnerUuid(user.getUuid());
					horseBaseEntity.setTame(true);
					stack.damage(12, random, null);
					user.playSound(SoundEvents.BLOCK_BAMBOO_HIT, 1, 1);
				}
			}
		}
		return ActionResult.SUCCESS;
	}
}
