package dev.mrsterner.bewitchmentplus.common.item;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CrownOfTheForestTrinketItem extends TrinketItem {
    public CrownOfTheForestTrinketItem(Item.Settings settings) {
        super(settings);
    }

    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
    }
}