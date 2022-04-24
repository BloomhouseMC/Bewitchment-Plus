package dev.mrsterner.bewitchmentplus.common.registry;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.mixin.common.LootContextTypesAccessor;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextType;
import net.minecraft.util.Identifier;

public class BWPLootTables {

    public static final LootContextType SLOT = LootContextTypesAccessor.guardvillager$register("bwp:slot", (context) -> {
        context.allow(LootContextParameters.THIS_ENTITY);
    });

    public static final Identifier CAMBION_MAIN_HAND = new Identifier(BewitchmentPlus.MODID, "entities/cambion/cambion_main_hand");
    public static final Identifier CAMBION_OFF_HAND = new Identifier(BewitchmentPlus.MODID, "entities/cambion/cambion_off_hand");
    public static final Identifier CAMBION_HELMET = new Identifier(BewitchmentPlus.MODID, "entities/cambion/cambion_helmet");
    public static final Identifier CAMBION_CHESTPLATE = new Identifier(BewitchmentPlus.MODID, "entities/cambion/cambion_chestplate");
    public static final Identifier CAMBION_LEGGINGS = new Identifier(BewitchmentPlus.MODID, "entities/cambion/cambion_leggings");
    public static final Identifier CAMBION_BOOTS = new Identifier(BewitchmentPlus.MODID, "entities/cambion/cambion_boots");
}
