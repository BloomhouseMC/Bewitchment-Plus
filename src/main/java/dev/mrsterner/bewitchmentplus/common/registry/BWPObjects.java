package dev.mrsterner.bewitchmentplus.common.registry;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;


public class BWPObjects {
	private static final Map<Block, Identifier> BLOCKS = new LinkedHashMap<>();
	private static final Map<Item, Identifier> ITEMS = new LinkedHashMap<>();

	public static final Item BLACK_DOG_SPAWN_EGG = register("black_dog_spawn_egg", new SpawnEggItem(BWPEntityTypes.BLACK_DOG, 0x000000, 0x000000, gen()));
	public static final Item CAMBION_SPAWN_EGG = register("cambion_spawn_egg",new SpawnEggItem(BWPEntityTypes.CAMBION,  0xE34234, 0x343434, gen()));

	private static <T extends Block> T register(String name, T block, boolean createItem) {
		BLOCKS.put(block, new Identifier(BewitchmentPlus.MODID, name));
		if (createItem) {
			ITEMS.put(new BlockItem(block, gen()), BLOCKS.get(block));
		}
		return block;
	}

	private static <T extends Item> T register(String name, T item) {
		ITEMS.put(item, new Identifier(BewitchmentPlus.MODID, name));
		return item;
	}

	private static Item.Settings gen() {
		return new Item.Settings().group(BewitchmentPlus.BEWITCHMENT_PLUS_GROUP);
	}

	public static void init() {
		BLOCKS.keySet().forEach(block -> Registry.register(Registry.BLOCK, BLOCKS.get(block), block));
		ITEMS.keySet().forEach(item -> Registry.register(Registry.ITEM, ITEMS.get(item), item));
	}
}