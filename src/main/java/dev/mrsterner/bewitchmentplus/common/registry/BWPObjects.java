package dev.mrsterner.bewitchmentplus.common.registry;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.common.block.FleeceBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.CarpetBlock;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;


public class BWPObjects {
	private static final Map<Block, Identifier> BLOCKS = new LinkedHashMap<>();
	private static final Map<Item, Identifier> ITEMS = new LinkedHashMap<>();

	public static final Block WHITE_FLEECE = register("white_witch_wool", new FleeceBlock(DyeColor.WHITE, FabricBlockSettings.copyOf(Blocks.WHITE_WOOL), false), true);
	public static final Block ORANGE_FLEECE = register("orange_witch_wool", new FleeceBlock(DyeColor.ORANGE, FabricBlockSettings.copyOf(Blocks.WHITE_WOOL), false), true);
	public static final Block MAGENTA_FLEECE = register("magenta_witch_wool", new FleeceBlock(DyeColor.MAGENTA, FabricBlockSettings.copyOf(Blocks.WHITE_WOOL), false), true);
	public static final Block LIGHT_BLUE_FLEECE = register("light_blue_witch_wool", new FleeceBlock(DyeColor.LIGHT_BLUE, FabricBlockSettings.copyOf(Blocks.WHITE_WOOL), false), true);
	public static final Block YELLOW_FLEECE = register("yellow_witch_wool", new FleeceBlock(DyeColor.YELLOW, FabricBlockSettings.copyOf(Blocks.WHITE_WOOL), false), true);
	public static final Block LIME_FLEECE = register("lime_witch_wool", new FleeceBlock(DyeColor.LIME, FabricBlockSettings.copyOf(Blocks.WHITE_WOOL), false), true);
	public static final Block GRAY_FLEECE = register("gray_witch_wool", new FleeceBlock(DyeColor.GRAY, FabricBlockSettings.copyOf(Blocks.WHITE_WOOL), false), true);
	public static final Block PINK_FLEECE = register("pink_witch_wool", new FleeceBlock(DyeColor.PINK, FabricBlockSettings.copyOf(Blocks.WHITE_WOOL), false), true);
	public static final Block LIGHT_GRAY_FLEECE = register("light_gray_witch_wool", new FleeceBlock(DyeColor.LIGHT_GRAY, FabricBlockSettings.copyOf(Blocks.WHITE_WOOL), false), true);
	public static final Block CYAN_FLEECE = register("cyan_witch_wool", new FleeceBlock(DyeColor.CYAN, FabricBlockSettings.copyOf(Blocks.WHITE_WOOL), false), true);
	public static final Block PURPLE_FLEECE = register("purple_witch_wool", new FleeceBlock(DyeColor.PURPLE, FabricBlockSettings.copyOf(Blocks.WHITE_WOOL), false), true);
	public static final Block BLUE_FLEECE = register("blue_witch_wool", new FleeceBlock(DyeColor.BLUE, FabricBlockSettings.copyOf(Blocks.WHITE_WOOL), false), true);
	public static final Block BROWN_FLEECE = register("brown_witch_wool", new FleeceBlock(DyeColor.BROWN, FabricBlockSettings.copyOf(Blocks.WHITE_WOOL), false), true);
	public static final Block GREEN_FLEECE = register("green_witch_wool", new FleeceBlock(DyeColor.GREEN, FabricBlockSettings.copyOf(Blocks.WHITE_WOOL), false), true);
	public static final Block RED_FLEECE = register("red_witch_wool", new FleeceBlock(DyeColor.RED, FabricBlockSettings.copyOf(Blocks.WHITE_WOOL), false), true);
	public static final Block BLACK_FLEECE = register("black_witch_wool", new FleeceBlock(DyeColor.BLACK, FabricBlockSettings.copyOf(Blocks.WHITE_WOOL), false), true);

	public static final Block WHITE_FLEECE_CARPET = register("white_witch_wool_carpet", new FleeceBlock(DyeColor.WHITE, FabricBlockSettings.copyOf(Blocks.WHITE_WOOL), true), true);
	public static final Block ORANGE_FLEECE_CARPET = register("orange_witch_wool_carpet", new FleeceBlock(DyeColor.ORANGE, FabricBlockSettings.copyOf(Blocks.WHITE_WOOL), true), true);
	public static final Block MAGENTA_FLEECE_CARPET = register("magenta_witch_wool_carpet", new FleeceBlock(DyeColor.MAGENTA, FabricBlockSettings.copyOf(Blocks.WHITE_WOOL), true), true);
	public static final Block LIGHT_BLUE_FLEECE_CARPET = register("light_blue_witch_wool_carpet", new FleeceBlock(DyeColor.LIGHT_BLUE, FabricBlockSettings.copyOf(Blocks.WHITE_WOOL), true), true);
	public static final Block YELLOW_FLEECE_CARPET = register("yellow_witch_wool_carpet", new FleeceBlock(DyeColor.YELLOW, FabricBlockSettings.copyOf(Blocks.WHITE_WOOL), true), true);
	public static final Block LIME_FLEECE_CARPET = register("lime_witch_wool_carpet", new FleeceBlock(DyeColor.LIME, FabricBlockSettings.copyOf(Blocks.WHITE_WOOL), true), true);
	public static final Block GRAY_FLEECE_CARPET = register("gray_witch_wool_carpet", new FleeceBlock(DyeColor.GRAY, FabricBlockSettings.copyOf(Blocks.WHITE_WOOL), true), true);
	public static final Block PINK_FLEECE_CARPET = register("pink_witch_wool_carpet", new FleeceBlock(DyeColor.PINK, FabricBlockSettings.copyOf(Blocks.WHITE_WOOL), true), true);
	public static final Block LIGHT_GRAY_FLEECE_CARPET = register("light_gray_witch_wool_carpet", new FleeceBlock(DyeColor.LIGHT_GRAY, FabricBlockSettings.copyOf(Blocks.WHITE_WOOL), true), true);
	public static final Block CYAN_FLEECE_CARPET = register("cyan_witch_wool_carpet", new FleeceBlock(DyeColor.CYAN, FabricBlockSettings.copyOf(Blocks.WHITE_WOOL), true), true);
	public static final Block PURPLE_FLEECE_CARPET = register("purple_witch_wool_carpet", new FleeceBlock(DyeColor.PURPLE, FabricBlockSettings.copyOf(Blocks.WHITE_WOOL), true), true);
	public static final Block BLUE_FLEECE_CARPET = register("blue_witch_wool_carpet", new FleeceBlock(DyeColor.BLUE, FabricBlockSettings.copyOf(Blocks.WHITE_WOOL), true), true);
	public static final Block BROWN_FLEECE_CARPET = register("brown_witch_wool_carpet", new FleeceBlock(DyeColor.BROWN, FabricBlockSettings.copyOf(Blocks.WHITE_WOOL), true), true);
	public static final Block GREEN_FLEECE_CARPET = register("green_witch_wool_carpet", new FleeceBlock(DyeColor.GREEN, FabricBlockSettings.copyOf(Blocks.WHITE_WOOL), true), true);
	public static final Block RED_FLEECE_CARPET = register("red_witch_wool_carpet", new FleeceBlock(DyeColor.RED, FabricBlockSettings.copyOf(Blocks.WHITE_WOOL), true), true);
	public static final Block BLACK_FLEECE_CARPET = register("black_witch_wool_carpet", new FleeceBlock(DyeColor.BLACK, FabricBlockSettings.copyOf(Blocks.WHITE_WOOL), true), true);

	public static final Block RGB_FLEECE = register("rgb_witch_wool", new Block(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)), true);
	public static final Block RGB_FLEECE_CARPET = register("rgb_witch_wool_carpet", new CarpetBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)), true);

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