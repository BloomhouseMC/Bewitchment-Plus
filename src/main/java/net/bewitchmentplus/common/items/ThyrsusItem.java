package net.bewitchmentplus.common.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public class ThyrsusItem extends SwordItem {

	public ThyrsusItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
		super(toolMaterial, attackDamage, attackSpeed, settings);
	}

	@Override
	public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
		if (entity instanceof AnimalEntity) {
			AnimalEntity animalEntity = (AnimalEntity) entity;
			if (animalEntity.isAlive()) {
				animalEntity.isReadyToBreed();
				animalEntity.getLoveTicks();
				animalEntity.setLoveTicks(5000);
				stack.setDamage(6);
				return ActionResult.SUCCESS;
			}
		}
		if (entity instanceof TameableEntity) {
			TameableEntity tameableEntity = (TameableEntity) entity;
			if (tameableEntity.isAlive()) {
				tameableEntity.setOwner(user);
				tameableEntity.setTamed(true);
				tameableEntity.isTamed();
				tameableEntity.setOwnerUuid(null);
				stack.setDamage(12);
				return ActionResult.SUCCESS;
			}
		} else return ActionResult.FAIL;
		return null;
	}
}
