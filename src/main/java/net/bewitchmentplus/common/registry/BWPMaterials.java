package net.bewitchmentplus.common.registry;

import moriyashiine.bewitchment.common.registry.BWObjects;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;

public class BWPMaterials {
	public static final ArmorMaterial SILVER_ARMOR = new ArmorMaterial() {
		@Override
		public int getDurability(EquipmentSlot slot) {
			return ArmorMaterials.IRON.getDurability(slot);
		}

		@Override
		public int getProtectionAmount(EquipmentSlot slot) {
			return ArmorMaterials.IRON.getProtectionAmount(slot);
		}

		@Override
		public int getEnchantability() {
			return ArmorMaterials.GOLD.getEnchantability();
		}

		@Override
		public SoundEvent getEquipSound() {
			return ArmorMaterials.GOLD.getEquipSound();
		}

		@Override
		public Ingredient getRepairIngredient() {
			return Ingredient.ofItems(BWObjects.SILVER_INGOT);
		}

		@Override
		public String getName() {
			return "silver";
		}

		@Override
		public float getToughness() {
			return ArmorMaterials.IRON.getToughness();
		}

		@Override
		public float getKnockbackResistance() {
			return ArmorMaterials.IRON.getKnockbackResistance();
		}
	};
}
