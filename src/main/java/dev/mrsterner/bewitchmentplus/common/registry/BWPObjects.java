package dev.mrsterner.bewitchmentplus.common.registry;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.common.block.*;
import dev.mrsterner.bewitchmentplus.common.item.*;
import moriyashiine.bewitchment.common.registry.BWObjects;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.CarpetBlock;
import net.minecraft.block.Material;
import net.minecraft.item.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

import static net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings.copyOf;


public class BWPObjects {
	private static final Map<Block, Identifier> BLOCKS = new LinkedHashMap<>();
	private static final Map<Item, Identifier> ITEMS = new LinkedHashMap<>();

	//ITEMS
	public static final Item MOONLIGHT_INFUSION = register("moonlight_infusion", new Item(gen().recipeRemainder(Items.GLASS_BOTTLE)));
	public static final Item ENDER_INFUSION = register("ender_vial", new Item(gen().recipeRemainder(Items.GLASS_BOTTLE)));//0x70922d
	public static final Item DRAGONBLOOD_STAFF = register("dragonblood_staff", new DragonbloodStaffItem(200, gen().maxCount(1).rarity(Rarity.RARE)));
	public static final Item BLOODROOT_ITEM = register("bloodroot_item", new Item(gen()));
	public static final Item MUTANDIS = register("mutandis", new MutandisItem(gen()));
	public static final Item MUTANDIS_BREW = register("mutandis_brew", new MutandisBrew(gen()));
	public static final Item BLACK_DOG_SPAWN_EGG = register("black_dog_spawn_egg", new SpawnEggItem(BWPEntityTypes.BLACK_DOG, 0x000000, 0x343434, new Item.Settings().group(ItemGroup.MISC)));
	public static final Item CAMBION_SPAWN_EGG = register("cambion_spawn_egg",new SpawnEggItem(BWPEntityTypes.CAMBION,  0xE34234, 0x343434, new Item.Settings().group(ItemGroup.MISC)));
	public static final Item SOUL = register("soul", new Item(gen()));
	public static final Item MUSIC_DISC_PETALS = register("music_disc_petals", new BWPMusicDisc(7, BWPSounds.MUSIC_DISC_PETALS, gen().maxCount(1).rarity(Rarity.RARE)));

	//BLOCKS
	public static final Block SILVER_GOBLET = registerGoblet("silver_goblet", new GobletBlock(copyOf(BWObjects.SILVER_BLOCK)));
	public static final Block GOLD_GOBLET = registerGoblet("gold_goblet", new GobletBlock(copyOf(Blocks.GOLD_BLOCK)));
	public static final Block NETHERITE_GOBLET = registerGoblet("netherite_goblet", new GobletBlock(copyOf(Blocks.NETHERITE_BLOCK)));
	public static final Block PENTACLE = register("pentacle", new PentacleBlock(copyOf(BWObjects.SILVER_BLOCK)), true);
	public static final Block BLOODROOT = register("bloodroot", new BWPPlantBlock(FabricBlockSettings.of(Material.PLANT).sounds(BlockSoundGroup.BAMBOO_SAPLING).strength(0.3F).nonOpaque().breakInstantly().dynamicBounds()), false);
	public static final Block MOONFLOWER = register("moonflower", new MoonflowerBlock(copyOf(Blocks.STONE)), true);
	public static final Block MIMIC_CHEST = register("mimic_chest", new MimicChestBlock(FabricBlockSettings.of(Material.PLANT).strength(2.5F).sounds(BlockSoundGroup.MOSS_BLOCK)), true);


	public static final Block WHITE_FLEECE = registerFleece("white_witch_wool", DyeColor.WHITE, false);
	public static final Block ORANGE_FLEECE = registerFleece("orange_witch_wool", DyeColor.ORANGE, false);
	public static final Block MAGENTA_FLEECE = registerFleece("magenta_witch_wool", DyeColor.MAGENTA, false);
	public static final Block LIGHT_BLUE_FLEECE = registerFleece("light_blue_witch_wool", DyeColor.LIGHT_BLUE, false);
	public static final Block YELLOW_FLEECE = registerFleece("yellow_witch_wool", DyeColor.YELLOW, false);
	public static final Block LIME_FLEECE = registerFleece("lime_witch_wool", DyeColor.LIME, false);
	public static final Block GRAY_FLEECE = registerFleece("gray_witch_wool", DyeColor.GRAY, false);
	public static final Block PINK_FLEECE = registerFleece("pink_witch_wool", DyeColor.PINK, false);
	public static final Block LIGHT_GRAY_FLEECE = registerFleece("light_gray_witch_wool", DyeColor.LIGHT_GRAY, false);
	public static final Block CYAN_FLEECE = registerFleece("cyan_witch_wool", DyeColor.CYAN, false);
	public static final Block PURPLE_FLEECE = registerFleece("purple_witch_wool", DyeColor.PURPLE, false);
	public static final Block BLUE_FLEECE = registerFleece("blue_witch_wool", DyeColor.BLUE, false);
	public static final Block BROWN_FLEECE = registerFleece("brown_witch_wool", DyeColor.BROWN, false);
	public static final Block GREEN_FLEECE = registerFleece("green_witch_wool", DyeColor.GREEN, false);
	public static final Block RED_FLEECE = registerFleece("red_witch_wool", DyeColor.RED, false);
	public static final Block BLACK_FLEECE = registerFleece("black_witch_wool", DyeColor.BLACK, false);

	public static final Block WHITE_FLEECE_CARPET = registerFleece("white_witch_wool_carpet", DyeColor.WHITE, true);
	public static final Block ORANGE_FLEECE_CARPET = registerFleece("orange_witch_wool_carpet", DyeColor.ORANGE,  true);
	public static final Block MAGENTA_FLEECE_CARPET = registerFleece("magenta_witch_wool_carpet", DyeColor.MAGENTA,  true);
	public static final Block LIGHT_BLUE_FLEECE_CARPET = registerFleece("light_blue_witch_wool_carpet", DyeColor.LIGHT_BLUE,  true);
	public static final Block YELLOW_FLEECE_CARPET = registerFleece("yellow_witch_wool_carpet", DyeColor.YELLOW,true);
	public static final Block LIME_FLEECE_CARPET = registerFleece("lime_witch_wool_carpet", DyeColor.LIME,  true);
	public static final Block GRAY_FLEECE_CARPET = registerFleece("gray_witch_wool_carpet", DyeColor.GRAY,true);
	public static final Block PINK_FLEECE_CARPET = registerFleece("pink_witch_wool_carpet", DyeColor.PINK,  true);
	public static final Block LIGHT_GRAY_FLEECE_CARPET = registerFleece("light_gray_witch_wool_carpet",DyeColor.LIGHT_GRAY, true);
	public static final Block CYAN_FLEECE_CARPET = registerFleece("cyan_witch_wool_carpet", DyeColor.CYAN,  true);
	public static final Block PURPLE_FLEECE_CARPET = registerFleece("purple_witch_wool_carpet", DyeColor.PURPLE, true);
	public static final Block BLUE_FLEECE_CARPET = registerFleece("blue_witch_wool_carpet", DyeColor.BLUE,  true);
	public static final Block BROWN_FLEECE_CARPET = registerFleece("brown_witch_wool_carpet", DyeColor.BROWN,  true);
	public static final Block GREEN_FLEECE_CARPET = registerFleece("green_witch_wool_carpet", DyeColor.GREEN, true);
	public static final Block RED_FLEECE_CARPET = registerFleece("red_witch_wool_carpet", DyeColor.RED,  true);
	public static final Block BLACK_FLEECE_CARPET = registerFleece("black_witch_wool_carpet", DyeColor.BLACK, true);

	public static final Block RGB_FLEECE = register("rgb_witch_wool", new Block(copyOf(Blocks.WHITE_WOOL)), true);
	public static final Block RGB_FLEECE_CARPET = register("rgb_witch_wool_carpet", new CarpetBlock(copyOf(Blocks.WHITE_WOOL)), true);


	public static FleeceBlock registerFleece(String id, DyeColor color, boolean carpet){
		var block = new FleeceBlock(color, copyOf(Blocks.WHITE_WOOL), carpet);
		register(id, block, true);
		return block;
	}


	private static <T extends Block> T registerGoblet(String id, T block) {
		BLOCKS.put(block, new Identifier(BewitchmentPlus.MODID, id));
		ITEMS.put(new GobletBlockItem(block, gen()), BLOCKS.get(block));
		return block;
	}

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