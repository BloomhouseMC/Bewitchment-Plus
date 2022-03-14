package dev.mrsterner.bewitchmentplus.mixin.common;

import dev.mrsterner.bewitchmentplus.common.registry.BWPCurses;
import moriyashiine.bewitchment.common.registry.BWComponents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MilkBucketItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MilkBucketItem.class)
public class MilkBucketItemMixin {
    @Inject(method = "finishUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;decrement(I)V", shift = At.Shift.AFTER), cancellable = true)
    private void stopMilkWhenHalfLife(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir){
        if(BWComponents.CURSES_COMPONENT.get(user).hasCurse(BWPCurses.HALF_LIFE)){
            cir.setReturnValue(stack.isEmpty() ? new ItemStack(Items.BUCKET) : stack);
        }
    }
}
