package net.bewitchmentplus.common.registry;

import net.bewitchmentplus.BewitchmentPlus;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class BWPTags {

	public static final Tag<Item> SILVER_ARMOR = TagRegistry.item(new Identifier(BewitchmentPlus.MODID, "silver_armor"));
	public static final Tag<Item> SILVER_TOOLS = TagRegistry.item(new Identifier(BewitchmentPlus.MODID, "silver_tools"));

}
