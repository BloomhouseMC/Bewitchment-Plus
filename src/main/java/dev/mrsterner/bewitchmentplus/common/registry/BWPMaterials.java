package dev.mrsterner.bewitchmentplus.common.registry;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;

public class BWPMaterials {
    public static final ArmorMaterial DEATH_ARMOR = new ArmorMaterial() {

        @Override
        public int getDurability(ArmorItem.Type type) {
            return ArmorMaterials.LEATHER.getDurability(type);
        }

        @Override
        public int getProtection(ArmorItem.Type type) {
            return ArmorMaterials.LEATHER.getProtection(type);
        }

        @Override
        public int getEnchantability() {
            return ArmorMaterials.LEATHER.getEnchantability();
        }

        @Override
        public SoundEvent getEquipSound() {
            return ArmorMaterials.LEATHER.getEquipSound();
        }

        @Override
        public Ingredient getRepairIngredient() {
            return null;
        }


        @Override
        public String getName() {
            return "death";
        }

        @Override
        public float getToughness() {
            return ArmorMaterials.LEATHER.getToughness();
        }

        @Override
        public float getKnockbackResistance() {
            return ArmorMaterials.LEATHER.getKnockbackResistance();
        }
    };
}
