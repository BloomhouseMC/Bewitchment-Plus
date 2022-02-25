package dev.mrsterner.bewitchmentplus.common.registry;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class BWPTags {
    public static final Tag<Item> GOBLET_LIQUIDS = TagFactory.ITEM.create(new Identifier(BewitchmentPlus.MODID, "goblet_liquids"));
}
