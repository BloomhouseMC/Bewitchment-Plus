package dev.mrsterner.bewitchmentplus.common.item;

import dev.mrsterner.bewitchmentplus.common.registry.BWPObjects;
import dev.mrsterner.bewitchmentplus.common.registry.BWPStatusEffects;
import moriyashiine.bewitchment.api.BewitchmentAPI;
import moriyashiine.bewitchment.common.Bewitchment;
import moriyashiine.bewitchment.common.registry.BWComponents;
import moriyashiine.bewitchment.common.registry.BWCurses;
import moriyashiine.bewitchment.common.registry.BWObjects;
import moriyashiine.bewitchment.common.registry.BWTransformations;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.*;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class GobletBlockItem extends BlockItem {
    public GobletBlockItem(Block block, Settings settings) {
        super(block, settings.maxCount(1));
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        var nbt = stack.getNbt();
        return nbt != null && nbt.contains("BlockEntityTag") ? UseAction.DRINK :UseAction.NONE;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 40;
    }

    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        super.finishUsing(stack, world, user);
        ItemStack itemStack = stack;
        var nbt = stack.getNbt();
        if (nbt != null && nbt.contains("BlockEntityTag")) {
            if (user instanceof ServerPlayerEntity serverPlayerEntity) {
                Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
                serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
            }
            NbtCompound nbtCompound = nbt.getCompound("BlockEntityTag");
            NbtCompound goblet = nbtCompound.getCompound("Goblet");
            if(user instanceof PlayerEntity player){
                BWComponents.TRANSFORMATION_COMPONENT.maybeGet(player).ifPresent(transformationComponent -> {
                    if(transformationComponent.getTransformation() == BWTransformations.HUMAN){
                        if(nbtCompound.getCompound("VampireBlood") != null && nbtCompound.getBoolean("VampireBlood") && (!Bewitchment.config.enableCurses || BWComponents.CURSES_COMPONENT.get(player).hasCurse(BWCurses.SUSCEPTIBILITY))){
                            transformationComponent.getTransformation().onRemoved(player);
                            transformationComponent.setTransformation(BWTransformations.VAMPIRE);
                            transformationComponent.getTransformation().onAdded(player);
                        }

                    }else if(BewitchmentAPI.isVampire(user, true)){
                        BWComponents.BLOOD_COMPONENT.get(user).fillBlood(20, false);
                    }
                });
            }
            if (goblet != null && !goblet.isEmpty()) {
                itemStack = ItemStack.fromNbt(goblet);
            }
            DefaultedList<ItemStack> slots = DefaultedList.ofSize(1, ItemStack.EMPTY);
            Inventories.readNbt(nbt.getCompound("BlockEntityTag"), slots);
            ItemStack slot = slots.get(0);
            if(!world.isClient()){
                if (slot.getItem() == Items.HONEY_BOTTLE) {
                    user.removeStatusEffect(StatusEffects.POISON);
                }else if (slot.getItem() == BWPObjects.UNICORN_BLOOD) {
                    user.addStatusEffect(new StatusEffectInstance(BWPStatusEffects.HALF_LIFE, 20 * 10, 1, false, false, true));
                }else if(slot.getItem() == Items.POTION){
                    Potion potion = PotionUtil.getPotion(slot);
                    user.addStatusEffect(new StatusEffectInstance(potion.getEffects().get(0)));
                }
            }
            stack.decrement(1);
        }
        if (!stack.isEmpty()) {
            if (user instanceof PlayerEntity && !((PlayerEntity) user).getAbilities().creativeMode) {
                PlayerEntity playerEntity = (PlayerEntity) user;
                if (!playerEntity.getInventory().insertStack(itemStack)) {
                    playerEntity.dropItem(itemStack, false);
                }
            }
        }
        return itemStack;
    }

    public SoundEvent getDrinkSound() {
        return SoundEvents.ITEM_HONEY_BOTTLE_DRINK;
    }

    public SoundEvent getEatSound() {
        return SoundEvents.ITEM_HONEY_BOTTLE_DRINK;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.isSneaking()) {
            return ItemUsage.consumeHeldItem(world, user, hand);
        }
        return TypedActionResult.pass(user.getStackInHand(hand));
    }


    @Environment(EnvType.CLIENT)
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (stack.hasNbt()) {
            if (stack.getNbt().contains("BlockEntityTag")) {
                var slots = DefaultedList.ofSize(1, ItemStack.EMPTY);
                Inventories.readNbt(stack.getNbt().getCompound("BlockEntityTag"), slots);
                boolean vamp = stack.getNbt().getCompound("BlockEntityTag").getBoolean("VampireBlood");
                if(slots.get(0).getItem().equals(Items.POTION)){
                    PotionUtil.buildTooltip(slots.get(0), tooltip, 1.0F);
                }else{
                    tooltip.add(new TranslatableText("liquid." + slots.get(0).toString().replace("1 ", ""))
                    .formatted(vamp ? Formatting.ITALIC : Formatting.DARK_RED)
                    .formatted(slots.get(0).getItem() == BWObjects.BOTTLE_OF_BLOOD ? Formatting.DARK_RED : slots.get(0).getItem() == Items.HONEY_BOTTLE ? Formatting.GOLD : Formatting.AQUA));
                }

            }
        }
    }


    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (context.getPlayer().isSneaking()) {
            return super.useOnBlock(context);
        }
        return ActionResult.PASS;
    }
}
