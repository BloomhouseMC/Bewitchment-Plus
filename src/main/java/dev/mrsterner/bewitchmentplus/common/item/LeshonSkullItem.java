package dev.mrsterner.bewitchmentplus.common.item;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;

public class LeshonSkullItem extends ArmorItem {
    public LeshonSkullItem(Settings settings) {
        super(SKULL, EquipmentSlot.HEAD, settings.maxDamage(100));
    }




    public static final ArmorMaterial SKULL = new ArmorMaterial() {
        public int getDurability(EquipmentSlot slot) {
            return ArmorMaterials.CHAIN.getDurability(slot);
        }

        public int getProtectionAmount(EquipmentSlot slot) {
            return ArmorMaterials.CHAIN.getProtectionAmount(slot);
        }

        public int getEnchantability() {
            return ArmorMaterials.GOLD.getEnchantability();
        }

        public SoundEvent getEquipSound() {
            return ArmorMaterials.LEATHER.getEquipSound();
        }

        public Ingredient getRepairIngredient() {
            return null;
        }

        public String getName() {
            return "skull";
        }

        public float getToughness() {
            return ArmorMaterials.CHAIN.getToughness();
        }

        public float getKnockbackResistance() {
            return ArmorMaterials.CHAIN.getKnockbackResistance();
        }
    };
}
