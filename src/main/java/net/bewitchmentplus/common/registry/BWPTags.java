package net.bewitchmentplus.common.registry;

import net.bewitchmentplus.BewitchmentPlus;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

//TODO: Something
public class BWPTags {

	public static final Tag<Item> CAMBION_TRADE = TagRegistry.item(new Identifier(BewitchmentPlus.MODID, "cambion_trade"));

}
