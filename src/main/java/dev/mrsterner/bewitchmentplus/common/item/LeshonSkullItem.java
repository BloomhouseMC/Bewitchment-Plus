package dev.mrsterner.bewitchmentplus.common.item;

import dev.mrsterner.bewitchmentplus.common.registry.BWPStatusEffects;
import dev.mrsterner.bewitchmentplus.common.registry.BWPTransformations;
import moriyashiine.bewitchment.common.registry.BWComponents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class LeshonSkullItem extends ArmorItem implements IAnimatable {
    private AnimationFactory factory = new AnimationFactory(this);
    private final int SKULL_BREAKER_MAX = 20;
    private int skullBreaker = SKULL_BREAKER_MAX;
    public LeshonSkullItem(Settings settings) {
        super(SKULL, EquipmentSlot.HEAD, settings.maxDamage(100));
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(!world.isClient() && entity instanceof PlayerEntity player){
            if(player.getEquippedStack(EquipmentSlot.HEAD) == stack){
                if(BWComponents.TRANSFORMATION_COMPONENT.get(player).getTransformation() == BWPTransformations.LESHON){
                    skullBreaker++;
                    if(skullBreaker > SKULL_BREAKER_MAX){
                        if(!stack.getOrCreateNbt().getBoolean("Broken") && player.hasStatusEffect(BWPStatusEffects.HOMESTEAD)){
                            while (stack.getDamage() > 1) {
                                stack.setDamage(stack.getDamage() - 1);
                            }
                        }else if(stack.getDamage() < stack.getMaxDamage() - 1){
                            stack.damage(1, player, consumedPlayer -> consumedPlayer.sendEquipmentBreakStatus(EquipmentSlot.HEAD));
                        }
                        if(stack.getDamage() == stack.getMaxDamage() - 1){
                            stack.getOrCreateNbt().putBoolean("Broken", true);
                        }
                        skullBreaker = 0;
                    }
                }else if(stack.getOrCreateNbt().getBoolean("Broken") && player.hasStatusEffect(BWPStatusEffects.HOMESTEAD)){
                    stack.setDamage(stack.getDamage() - 1);
                    if(stack.getDamage() == 0){
                        stack.getOrCreateNbt().putBoolean("Broken", false);
                    }
                }
            }
            if(stack.getOrCreateNbt().getBoolean("Broken") && player.hasStatusEffect(BWPStatusEffects.HOMESTEAD)){
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

    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.skull.idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "controller", 20, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
