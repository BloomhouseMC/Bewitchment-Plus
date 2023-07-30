package dev.mrsterner.bewitchmentplus.common.item;

import dev.mrsterner.bewitchmentplus.client.renderer.LeshonSkullArmorRenderer;
import dev.mrsterner.bewitchmentplus.client.renderer.LeshonSkullItemRenderer;
import dev.mrsterner.bewitchmentplus.common.registry.BWPStatusEffects;
import dev.mrsterner.bewitchmentplus.common.registry.BWPTransformations;
import moriyashiine.bewitchment.common.registry.BWComponents;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class LeshonSkullItem extends ArmorItem implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);
    private final int SKULL_BREAKER_MAX = 20;
    private int skullBreaker = SKULL_BREAKER_MAX;
    public LeshonSkullItem(Settings settings) {
        super(SKULL, Type.HELMET, settings.maxDamage(100));
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

        @Override
        public int getDurability(Type type) {
            return ArmorMaterials.CHAIN.getDurability(type);
        }

        @Override
        public int getProtection(Type type) {
            return ArmorMaterials.CHAIN.getProtection(type);
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

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private GeoArmorRenderer<?> renderer;
            private GeoItemRenderer<?> itemRenderer;

            @Override
            public BuiltinModelItemRenderer getCustomRenderer() {
                if(this.itemRenderer == null) {
                    this.itemRenderer = new LeshonSkullItemRenderer();
                }

                return this.itemRenderer;
            }

            @Override
            public BipedEntityModel<LivingEntity> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, BipedEntityModel<LivingEntity> original) {
                if(this.renderer == null) {
                    this.renderer = new LeshonSkullArmorRenderer();
                }

                this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);

                return this.renderer;
            }
        });
    }

    @Override
    public Supplier<Object> getRenderProvider() {
        return renderProvider;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", this::play));
    }

    private PlayState play(AnimationState<LeshonSkullItem> state) {
        state.getController().setAnimation(RawAnimation.begin().thenLoop("animation.skull.idle"));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
