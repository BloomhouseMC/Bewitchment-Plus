package net.bewitchmentplus.common.registry;

import net.bewitchmentplus.BewitchmentPlus;
import net.bewitchmentplus.common.items.ThyrsusItem;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

import static net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings.copyOf;

public class BWPObjects {
	private static final Map<Block, Identifier> BLOCKS = new LinkedHashMap<>();
	private static final Map<Item, Identifier> ITEMS = new LinkedHashMap<>();

	public static final Item DRUDEN_SPAWN_EGG = create("druden_spawn_egg", new SpawnEggItem(BWPEntityTypes.DRUDEN, 0x006400, 0x989898, gen()));
	public static final Item BLACK_DOG_SPAWN_EGG = create("black_dog_spawn_egg", new SpawnEggItem(BWPEntityTypes.BLACK_DOG, 0x000000, 0x000000, gen()));
	public static final Item CAMBION_SPAWN_EGG = create("cambion_spawn_egg", new SpawnEggItem(BWPEntityTypes.CAMBION, 0x000000, 0x000000, gen()));

	public static final Item THYRSUS_ITEM = create("thyrsus", new ThyrsusItem(ToolMaterials.IRON, 1, 2, gen()));

	public static final Block WHITE_WITCH_WOOL = create("white_witch_wool", new Block(copyOf(Blocks.WHITE_WOOL)), true);
	public static final Block ORANGE_WITCH_WOOL = create("orange_witch_wool", new Block(copyOf(Blocks.WHITE_WOOL)), true);
	public static final Block MAGENTA_WITCH_WOOL = create("magenta_witch_wool", new Block(copyOf(Blocks.WHITE_WOOL)), true);
	public static final Block LIGHT_BLUE_WITCH_WOOL = create("light_blue_witch_wool", new Block(copyOf(Blocks.WHITE_WOOL)), true);
	public static final Block YELLOW_WITCH_WOOL = create("yellow_witch_wool", new Block(copyOf(Blocks.WHITE_WOOL)), true);
	public static final Block LIME_WITCH_WOOL = create("lime_witch_wool", new Block(copyOf(Blocks.WHITE_WOOL)), true);
	public static final Block GRAY_WITCH_WOOL = create("gray_witch_wool", new Block(copyOf(Blocks.WHITE_WOOL)), true);
	public static final Block PINK_WITCH_WOOL = create("pink_witch_wool", new Block(copyOf(Blocks.WHITE_WOOL)), true);
	public static final Block LIGHT_GRAY_WITCH_WOOL = create("light_gray_witch_wool", new Block(copyOf(Blocks.WHITE_WOOL)), true);
	public static final Block CYAN_WITCH_WOOL = create("cyan_witch_wool", new Block(copyOf(Blocks.WHITE_WOOL)), true);
	public static final Block PURPLE_WITCH_WOOL = create("purple_witch_wool", new Block(copyOf(Blocks.WHITE_WOOL)), true);
	public static final Block BLUE_WITCH_WOOL = create("blue_witch_wool", new Block(copyOf(Blocks.WHITE_WOOL)), true);
	public static final Block BROWN_WITCH_WOOL = create("brown_witch_wool", new Block(copyOf(Blocks.WHITE_WOOL)), true);
	public static final Block GREEN_WITCH_WOOL = create("green_witch_wool", new Block(copyOf(Blocks.WHITE_WOOL)), true);
	public static final Block RED_WITCH_WOOL = create("red_witch_wool", new Block(copyOf(Blocks.WHITE_WOOL)), true);
	public static final Block BLACK_WITCH_WOOL = create("black_witch_wool", new Block(copyOf(Blocks.WHITE_WOOL)), true);

	private static <T extends Block> T create(String name, T block, boolean createItem) {
		BLOCKS.put(block, new Identifier(BewitchmentPlus.MODID, name));
		if (createItem) {
			ITEMS.put(new BlockItem(block, gen()), BLOCKS.get(block));
		}
		return block;
	}

	private static <T extends Item> T create(String name, T item) {
		ITEMS.put(item, new Identifier(BewitchmentPlus.MODID, name));
		return item;
	}

	private static Item.Settings gen() {
		return new Item.Settings().group(BewitchmentPlus.BEWITCHMENT_PLUS_MOBS_GROUP);
	}

	public static void init() {
		BLOCKS.keySet().forEach(block -> Registry.register(Registry.BLOCK, BLOCKS.get(block), block));
		ITEMS.keySet().forEach(item -> Registry.register(Registry.ITEM, ITEMS.get(item), item));
	}
}