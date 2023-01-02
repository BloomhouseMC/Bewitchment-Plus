package dev.mrsterner.bewitchmentplus.common.registry;

import net.minecraft.item.FoodComponent;

public class BWPFoodComponents {
    public static final FoodComponent DRAGONFRUIT = new FoodComponent.Builder().hunger(3).saturationModifier(0.6f).alwaysEdible().build();
    public static final FoodComponent BLOODROOT = new FoodComponent.Builder().hunger(4).saturationModifier(0.6f).alwaysEdible().build();
}
