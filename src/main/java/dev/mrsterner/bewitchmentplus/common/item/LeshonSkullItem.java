package dev.mrsterner.bewitchmentplus.common.item;

import dev.mrsterner.bewitchmentplus.common.registry.BWPTransformations;
import moriyashiine.bewitchment.api.BewitchmentAPI;
import moriyashiine.bewitchment.common.registry.BWComponents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;

public class LeshonSkullItem extends ArmorItem {
    private final int SKULL_BREAKER_MAX = 20;
    private int skullBreaker = SKULL_BREAKER_MAX;
    public LeshonSkullItem(Settings settings) {
        super(SKULL, EquipmentSlot.HEAD, settings.maxDamage(100));
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(!world.isClient() && entity instanceof PlayerEntity player && BWComponents.TRANSFORMATION_COMPONENT.get(player).getTransformation() == BWPTransformations.LESHON){
            if(player.getEquippedStack(EquipmentSlot.HEAD) == stack){
                skullBreaker++;
                if(skullBreaker > SKULL_BREAKER_MAX){
                    if(!stack.getOrCreateNbt().getBoolean("Broken") && stack.getDamage() > 1 && BewitchmentAPI.drainMagic(player, 1, true)){
                        BewitchmentAPI.drainMagic(player, 1, false);//TODO maybe change from ME to Homestead status effect binding the leshon to a yew tree
                        stack.setDamage(stack.getDamage() - 1);
                    }else if(stack.getDamage() < stack.getMaxDamage() - 1){
                        stack.damage(1, player, consumedPlayer -> consumedPlayer.sendEquipmentBreakStatus(EquipmentSlot.HEAD));
                    }
                    if(stack.getDamage() == stack.getMaxDamage() - 1){
                        stack.getOrCreateNbt().putBoolean("Broken", true);
                    }
                    skullBreaker = 0;
                }

            }
            if(stack.getOrCreateNbt().getBoolean("Broken") && BewitchmentAPI.drainMagic(player, 1, true)){
                BewitchmentAPI.drainMagic(player, 1, false);
                stack.setDamage(stack.getDamage() - 1);
                if(stack.getDamage() == 0){
                    stack.getOrCreateNbt().putBoolean("Broken", false);
                }
            }
        }

        super.inventoryTick(stack, world, entity, slot, selected);
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
