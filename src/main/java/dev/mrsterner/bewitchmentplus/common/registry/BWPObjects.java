package dev.mrsterner.bewitchmentplus.common.registry;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.common.block.*;
import dev.mrsterner.bewitchmentplus.common.block.yew.*;
import dev.mrsterner.bewitchmentplus.common.item.*;
import dev.mrsterner.bewitchmentplus.mixin.common.BlocksMixin;
import moriyashiine.bewitchment.api.item.BroomItem;
import moriyashiine.bewitchment.common.block.CoffinBlock;
import moriyashiine.bewitchment.common.registry.BWObjects;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.LinkedHashMap;
import java.util.Map;

import static moriyashiine.bewitchment.common.registry.BWObjects.RAW_SILVER_BLOCK;
import static net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings.copyOf;


public class BWPObjects {
	private static final Map<Block, Identifier> BLOCKS = new LinkedHashMap<>();
	private static final Map<Item, Identifier> ITEMS = new LinkedHashMap<>();

	public static Map<Item, Identifier> getItems(){
		return ITEMS;
	}

	//ITEMS
	public static final Item BLOODROOT_ITEM = register("bloodroot_item", new Item(gen()));
	public static final Item DRAGONFRUIT = register("dragonfruit", new Item(gen().food(BWPFoodComponents.DRAGONFRUIT)));
	public static final Item MUTANDIS = register("mutandis", new MutandisItem(gen()));
	public static final Item SOUL = register("soul", new Item(gen()));
	public static final Item MUTANDIS_BREW = register("mutandis_brew", new MutandisBrew(gen()));
	public static final Item UNICORN_BLOOD = register("unicorn_blood", new Item(gen()));
	public static final Item MOONLIGHT_INFUSION = register("moonlight_infusion", new Item(gen().recipeRemainder(Items.GLASS_BOTTLE)));
	public static final Item ENDER_INFUSION = register("ender_vial", new Item(gen().recipeRemainder(Items.GLASS_BOTTLE)));//0x70922d
	public static final Item MUSIC_DISC_PETALS = register("music_disc_petals", new BWPMusicDisc(7, BWPSounds.MUSIC_DISC_PETALS, new Item.Settings().group(ItemGroup.MISC).maxCount(1).rarity(Rarity.RARE)));
	public static final Item YEW_BROOM = register("yew_broom", new BroomItem(gen().maxCount(1), BWPEntityTypes.YEW_BROOM));
	public static final Item DRAGONBLOOD_STAFF = register("dragonblood_staff", new DragonbloodStaffItem(200, gen().maxCount(1).rarity(Rarity.RARE)));
	public static final Item VAMPIRE_KNIFE = register("vampire_knife", new VamireKnifeItem(ToolMaterials.IRON, 2, -3F,gen()));
	public static final Item LESHON_SKULL = register("leshon_skull", new LeshonSkullItem(gen()));
	// TODO public static final Item CROWN_OF_THE_FOREST = register("crown_of_the_forest", new CrownOfTheForestTrinketItem(gen().maxCount(1)));

	public static final Item DEATHS_HOOD = register("deaths_hood", new ArmorItem(BWPMaterials.DEATH_ARMOR, EquipmentSlot.HEAD, gen()));
	public static final Item DEATHS_ROBES = register("deaths_robes", new ArmorItem(BWPMaterials.DEATH_ARMOR, EquipmentSlot.CHEST, gen()));
	public static final Item DEATHS_FOOTWEAR = register("deaths_footwear", new ArmorItem(BWPMaterials.DEATH_ARMOR, EquipmentSlot.FEET, gen()));

	//BLOCKS
	public static final Block SILVER_GOBLET = registerGoblet("silver_goblet", new GobletBlock(copyOf(BWObjects.SILVER_BLOCK)), gen());
	public static final Block GOLD_GOBLET = registerGoblet("gold_goblet", new GobletBlock(copyOf(Blocks.GOLD_BLOCK)), gen());
	public static final Block NETHERITE_GOBLET = registerGoblet("netherite_goblet", new GobletBlock(copyOf(Blocks.NETHERITE_BLOCK)), gen().fireproof());
	public static final Block SILVER_STANDING_CANDELABRA = register("silver_standing_candelabra", new StandingCandelabraBlock(FabricBlockSettings.of(Material.METAL)), true);
	public static final Block GOLD_STANDING_CANDELABRA = register("gold_standing_candelabra", new StandingCandelabraBlock(FabricBlockSettings.of(Material.METAL)), true);
	public static final Block NETHERITE_STANDING_CANDELABRA = register("netherite_standing_candelabra", new StandingCandelabraBlock(FabricBlockSettings.copyOf(Blocks.NETHERITE_BLOCK)), false);
	public static final Item NETHERITE_STANDING_CANDELABRA_ITEM = register("netherite_standing_candelabra", new BlockItem(NETHERITE_STANDING_CANDELABRA,gen().fireproof()));

	public static final Block PENTACLE = register("pentacle", new PentacleBlock(copyOf(BWObjects.SILVER_BLOCK)), true, gen());
	public static final Block BLOODROOT = register("bloodroot", new BWPPlantBlock(FabricBlockSettings.of(Material.PLANT).sounds(BlockSoundGroup.BAMBOO_SAPLING).strength(0.3F).nonOpaque().breakInstantly().dynamicBounds(),0),false, gen());
	public static final Block MOONFLOWER = register("moonflower", new MoonflowerBlock(copyOf(Blocks.STONE)), false, gen());
	public static final Block MIMIC_CHEST = register("mimic_chest", new MimicChestBlock(FabricBlockSettings.of(Material.WOOD).strength(2.5F).sounds(BlockSoundGroup.WOOD)), true, gen());
	public static final Block EMBERGRASS = register("embergrass", new BWPPlantBlock(copyOf(Blocks.GRASS),8), true, gen());
	public static final Block LEECH_CHEST = register("leech_chest", new LeechChestBlock(FabricBlockSettings.of(Material.PLANT).strength(2.5F, 3.0F).sounds(BlockSoundGroup.MOSS_BLOCK)),
			false /*TODO leech enable when feature complete*/, gen());

	public static final Block LOCACACA_LEAVES = register("locacaca_leaves", new Block(copyOf(Blocks.GRASS).nonOpaque().breakInstantly()), false);
	public static final Block DRAGONFRUIT_BLOCK = register("dragonfruit_block", new DragonfruitBlock(copyOf(Blocks.MELON)), false);

	public static final Block UNICORN_BLOOD_PUDDLE = register("unicorn_blood_puddle", new UnicornPuddleBlock(FabricBlockSettings.of(Material.WATER)), false);
	public static final Block SILVER_BLOCK_STAIRS = register("silver_stairs", new BWPStairsBlock(BWObjects.SILVER_BLOCK,copyOf(Blocks.GOLD_BLOCK)) {
		@Override
		public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
			RAW_SILVER_BLOCK.onSteppedOn(world, pos, state, entity);
		}
	}, true);
	public static final Block SILVER_BLOCK_SLAB = register("silver_slab", new SlabBlock(copyOf(Blocks.GOLD_BLOCK)) {
		@Override
		public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
			RAW_SILVER_BLOCK.onSteppedOn(world, pos, state, entity);
		}
	}, true);
	public static final Block CUT_SILVER_BLOCK = register("cut_silver_block", new Block(copyOf(Blocks.GOLD_BLOCK)) {
		@Override
		public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
			RAW_SILVER_BLOCK.onSteppedOn(world, pos, state, entity);
		}
	}, true);
	public static final Block CUT_SILVER_BLOCK_STAIRS = register("cut_silver_stairs", new BWPStairsBlock(CUT_SILVER_BLOCK,copyOf(Blocks.GOLD_BLOCK)) {
		@Override
		public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
			RAW_SILVER_BLOCK.onSteppedOn(world, pos, state, entity);
		}
	}, true);
	public static final Block CUT_SILVER_BLOCK_SLAB = register("cut_silver_slab", new SlabBlock(copyOf(Blocks.GOLD_BLOCK)) {
		@Override
		public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
			RAW_SILVER_BLOCK.onSteppedOn(world, pos, state, entity);
		}
	}, true);

	public static final Block LOCACACA_LOG = register("locacaca_log", new PillarBlock(copyOf(Blocks.OAK_LOG)), false);
	public static final Block LOCACACA_BRANCH = register("locacaca_branch", new LotusBranchBlock(FabricBlockSettings.of(Material.WOOD)), false);

	public static final Block STRIPPED_YEW_LOG = register("stripped_yew_log", new PillarBlock(copyOf(Blocks.OAK_LOG)), true);
	public static final Block STRIPPED_YEW_WOOD = register("stripped_yew_wood", new PillarBlock(copyOf(STRIPPED_YEW_LOG)), true);
	public static final Block YEW_LOG = register("yew_log", new YewLogBlock(() -> STRIPPED_YEW_LOG, MapColor.BROWN, copyOf(STRIPPED_YEW_LOG)), true);
	public static final Block YEW_CUT_LOG = register("yew_cut_log", new YewCutLogBlock(() -> STRIPPED_YEW_LOG, MapColor.BROWN, copyOf(STRIPPED_YEW_LOG)), true);
	public static final Block YEW_WOOD = register("yew_wood", new YewLogBlock(() -> STRIPPED_YEW_WOOD, MapColor.BROWN, copyOf(STRIPPED_YEW_LOG)), true);
	public static final Block YEW_PLANKS = register("yew_planks", new Block(copyOf(Blocks.OAK_PLANKS)), true);
	public static final Block YEW_STAIRS = register("yew_stairs", new BWPStairsBlock(YEW_PLANKS, copyOf(Blocks.OAK_STAIRS)), true);
	public static final Block YEW_SLAB = register("yew_slab", new SlabBlock(copyOf(Blocks.OAK_SLAB)), true);
	public static final Block YEW_FENCE = register("yew_fence", new FenceBlock(copyOf(Blocks.OAK_FENCE)), true);
	public static final Block YEW_FENCE_GATE = register("yew_fence_gate", new FenceGateBlock(copyOf(Blocks.OAK_FENCE_GATE)), true);
	public static final Block YEW_LEAVES = register("yew_leaves", BlocksMixin.callCreateLeavesBlock(BlockSoundGroup.GRASS), true);
	public static final Block YEW_SAPLING = register("yew_sapling", new YewSaplingBlock(1, copyOf(Blocks.OAK_SAPLING)), true);
	public static final Block POTTED_YEW_SAPLING = register("potted_yew_sapling", new FlowerPotBlock(YEW_SAPLING, copyOf(Blocks.POTTED_OAK_SAPLING)), false);
	public static final Block YEW_PRESSURE_PLATE = register("yew_pressure_plate", new YewPressurePlateBlock(copyOf(Blocks.OAK_PRESSURE_PLATE)), true);
	public static final Block YEW_BUTTON = register("yew_button", new YewButtonBlock(copyOf(Blocks.OAK_BUTTON)), true);
	public static final Block YEW_TRAPDOOR = register("yew_trapdoor", new YewTrapdoorBlock(copyOf(Blocks.OAK_TRAPDOOR)), true);
	public static final Block YEW_DOOR = register("yew_door", new YewDoorBlock(copyOf(Blocks.OAK_DOOR)), false);
	public static final Item YEW_DOOR_ITEM = register("yew_door", new TallBlockItem(YEW_DOOR, gen()));
	public static final Block YEW_CHEST = register("yew_chest", new YewChestBlock(copyOf(Blocks.CHEST), () -> BWPBlockEntityTypes.YEW_CHEST_BLOCK_ENTITY, false), true);
	public static final Block TRAPPED_YEW_CHEST = register("trapped_yew_chest", new YewChestBlock(copyOf(Blocks.CHEST), () -> BWPBlockEntityTypes.YEW_CHEST_BLOCK_ENTITY, true), true);
	public static final Identifier YEW_SIGN_TEXTURE = new Identifier(BewitchmentPlus.MODID, "entity/sign/yew");
	public static final YewSignBlock YEW_SIGN = register("yew_sign", new YewSignBlock(YEW_SIGN_TEXTURE, copyOf(Blocks.OAK_SIGN)), false);
	public static final Block YEW_WALL_SIGN = register("yew_wall_sign", new YewWallSignBlock(YEW_SIGN_TEXTURE, copyOf(Blocks.OAK_WALL_SIGN)), false);
	public static final Item YEW_SIGN_ITEM = register("yew_sign", new SignItem(gen().maxCount(16), YEW_SIGN, YEW_WALL_SIGN));

	public static final Block WHITE_COFFIN = register("white_blackstone_coffin", new CoffinBlock(DyeColor.WHITE, copyOf(Blocks.BLACKSTONE).nonOpaque()), true);
	public static final Block ORANGE_COFFIN = register("orange_blackstone_coffin", new CoffinBlock(DyeColor.ORANGE, copyOf(Blocks.BLACKSTONE).nonOpaque()), true);
	public static final Block MAGENTA_COFFIN = register("magenta_blackstone_coffin", new CoffinBlock(DyeColor.MAGENTA, copyOf(Blocks.BLACKSTONE).nonOpaque()), true);
	public static final Block LIGHT_BLUE_COFFIN = register("light_blue_blackstone_coffin", new CoffinBlock(DyeColor.LIGHT_BLUE, copyOf(Blocks.BLACKSTONE).nonOpaque()), true);
	public static final Block YELLOW_COFFIN = register("yellow_blackstone_coffin", new CoffinBlock(DyeColor.YELLOW, copyOf(Blocks.BLACKSTONE).nonOpaque()), true);
	public static final Block LIME_COFFIN = register("lime_blackstone_coffin", new CoffinBlock(DyeColor.LIME, copyOf(Blocks.BLACKSTONE).nonOpaque()), true);
	public static final Block PINK_COFFIN = register("pink_blackstone_coffin", new CoffinBlock(DyeColor.PINK, copyOf(Blocks.BLACKSTONE).nonOpaque()), true);
	public static final Block GRAY_COFFIN = register("gray_blackstone_coffin", new CoffinBlock(DyeColor.GRAY, copyOf(Blocks.BLACKSTONE).nonOpaque()), true);
	public static final Block LIGHT_GRAY_COFFIN = register("light_gray_blackstone_coffin", new CoffinBlock(DyeColor.LIGHT_GRAY, copyOf(Blocks.BLACKSTONE).nonOpaque()), true);
	public static final Block CYAN_COFFIN = register("cyan_blackstone_coffin", new CoffinBlock(DyeColor.CYAN, copyOf(Blocks.BLACKSTONE).nonOpaque()), true);
	public static final Block PURPLE_COFFIN = register("purple_blackstone_coffin", new CoffinBlock(DyeColor.PURPLE, copyOf(Blocks.BLACKSTONE).nonOpaque()), true);
	public static final Block BLUE_COFFIN = register("blue_blackstone_coffin", new CoffinBlock(DyeColor.BLUE, copyOf(Blocks.BLACKSTONE).nonOpaque()), true);
	public static final Block BROWN_COFFIN = register("brown_blackstone_coffin", new CoffinBlock(DyeColor.BROWN, copyOf(Blocks.BLACKSTONE).nonOpaque()), true);
	public static final Block GREEN_COFFIN = register("green_blackstone_coffin", new CoffinBlock(DyeColor.GREEN, copyOf(Blocks.BLACKSTONE).nonOpaque()), true);
	public static final Block RED_COFFIN = register("red_blackstone_coffin", new CoffinBlock(DyeColor.RED, copyOf(Blocks.BLACKSTONE).nonOpaque()), true);
	public static final Block BLACK_COFFIN = register("black_blackstone_coffin", new CoffinBlock(DyeColor.BLACK, copyOf(Blocks.BLACKSTONE).nonOpaque()), true);

	public static final Block LILITH_STATUE_BLACKSTONE = registerStatue("statue_lilith_blackstone", new StatueBlock(FabricBlockSettings.of(Material.STONE).strength(2.5F).sounds(BlockSoundGroup.STONE)));
	public static final Block LILITH_STATUE_GOLD = registerStatue("statue_lilith_gold", new StatueBlock(FabricBlockSettings.of(Material.STONE).strength(2.5F).sounds(BlockSoundGroup.STONE)));
	public static final Block LILITH_STATUE_NETHERBRICK = registerStatue("statue_lilith_netherbrick", new StatueBlock(FabricBlockSettings.of(Material.STONE).strength(2.5F).sounds(BlockSoundGroup.STONE)));


	public static final Block HERNE_STATUE_BLACKSTONE = registerStatue("statue_herne_blackstone", new StatueBlock(FabricBlockSettings.of(Material.STONE).strength(2.5F).sounds(BlockSoundGroup.STONE)));
	public static final Block HERNE_STATUE_GOLD = registerStatue("statue_herne_gold", new StatueBlock(FabricBlockSettings.of(Material.STONE).strength(2.5F).sounds(BlockSoundGroup.STONE)));
	public static final Block HERNE_STATUE_NETHERBRICK = registerStatue("statue_herne_netherbrick", new StatueBlock(FabricBlockSettings.of(Material.STONE).strength(2.5F).sounds(BlockSoundGroup.STONE)));

	public static final Block LEONARD_STATUE_BLACKSTONE = registerStatue("statue_leonard_blackstone", new StatueBlock(FabricBlockSettings.of(Material.STONE).strength(2.5F).sounds(BlockSoundGroup.STONE)));
	public static final Block LEONARD_STATUE_GOLD = registerStatue("statue_leonard_gold", new StatueBlock(FabricBlockSettings.of(Material.STONE).strength(2.5F).sounds(BlockSoundGroup.STONE)));
	public static final Block LEONARD_STATUE_NETHERBRICK = registerStatue("statue_leonard_netherbrick", new StatueBlock(FabricBlockSettings.of(Material.STONE).strength(2.5F).sounds(BlockSoundGroup.STONE)));

	public static final Block BAPHOMET_STATUE_BLACKSTONE = registerStatue("statue_baphomet_blackstone", new StatueBlock(FabricBlockSettings.of(Material.STONE).strength(2.5F).sounds(BlockSoundGroup.STONE)));
	public static final Block BAPHOMET_STATUE_GOLD = registerStatue("statue_baphomet_gold", new StatueBlock(FabricBlockSettings.of(Material.STONE).strength(2.5F).sounds(BlockSoundGroup.STONE)));
	public static final Block BAPHOMET_STATUE_NETHERBRICK = registerStatue("statue_baphomet_netherbrick", new StatueBlock(FabricBlockSettings.of(Material.STONE).strength(2.5F).sounds(BlockSoundGroup.STONE)));

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

	public static final Block RGB_FLEECE = registerDecorative("rgb_witch_wool", new Block(copyOf(Blocks.WHITE_WOOL)), true);
	public static final Block RGB_FLEECE_CARPET = registerDecorative("rgb_witch_wool_carpet", new CarpetBlock(copyOf(Blocks.WHITE_WOOL)), true);

	//SPAWN EGGS
	public static final Item BLACK_DOG_SPAWN_EGG = register("black_dog_spawn_egg", new SpawnEggItem(BWPEntityTypes.BLACK_DOG, 0x000000, 0x343434, gen()));
	public static final Item NIFFLER_SPAWN_EGG = register("niffler_spawn_egg", new SpawnEggItem(BWPEntityTypes.NIFFLER, 0xFFFFFF, 0x8F95E6, gen()));
	public static final Item PHOENIX_SPAWN_EGG = register("phoenix_spawn_egg", new SpawnEggItem(BWPEntityTypes.PHOENIX, 0xFFFFFF, 0x09E6D0, gen()));
	public static final Item UNICORN_SPAWN_EGG = register("unicorn_spawn_egg", new SpawnEggItem(BWPEntityTypes.UNICORN, 0xFFFFFF, 0x0CE69F, gen()));
	public static final Item CAMBION_SPAWN_EGG = register("cambion_spawn_egg",new SpawnEggItem(BWPEntityTypes.CAMBION,  0xE34234, 0x09E6D0, gen()));


	public static FleeceBlock registerFleece(String id, DyeColor color, boolean carpet){
		var block = new FleeceBlock(color, copyOf(Blocks.WHITE_WOOL), carpet);
		registerDecorative(id, block, true);
		return block;
	}


	private static <T extends Block> T registerGoblet(String id, T block, Item.Settings settings) {
		BLOCKS.put(block, new Identifier(BewitchmentPlus.MODID, id));
		ITEMS.put(new GobletBlockItem(block, settings), BLOCKS.get(block));
		return block;
	}

	private static <T extends Block> T registerStatue(String id, T block) {
		BLOCKS.put(block, new Identifier(BewitchmentPlus.MODID, id));
		ITEMS.put(new StatueBlockItem(block, gen()), BLOCKS.get(block));
		return block;
	}

	private static <T extends Block> T registerDecorative(String name, T block, boolean createItem) {
		BLOCKS.put(block, new Identifier(BewitchmentPlus.MODID, name));
		if (createItem) {
			ITEMS.put(new BlockItem(block, new Item.Settings().group(ItemGroup.DECORATIONS)), BLOCKS.get(block));
		}
		return block;
	}

	private static <T extends Block> T register(String name, T block, boolean createItem, Item.Settings settings) {
		BLOCKS.put(block, new Identifier(BewitchmentPlus.MODID, name));
		if (createItem) {
			ITEMS.put(new BlockItem(block, settings), BLOCKS.get(block));
		}
		return block;
	}

	public static <T extends Block> T register(String name, T block, boolean createItem) {
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
		FuelRegistry fuelRegistry = FuelRegistry.INSTANCE;
		fuelRegistry.add(YEW_FENCE, 300);
		fuelRegistry.add(YEW_FENCE_GATE, 300);
		FlammableBlockRegistry flammableRegistry = FlammableBlockRegistry.getDefaultInstance();
		flammableRegistry.add(STRIPPED_YEW_LOG, 5, 5);
		flammableRegistry.add(STRIPPED_YEW_WOOD, 5, 5);
		flammableRegistry.add(YEW_LOG, 5, 5);
		flammableRegistry.add(YEW_WOOD, 5, 5);
		flammableRegistry.add(YEW_LEAVES, 30, 60);
		flammableRegistry.add(YEW_PLANKS, 5, 20);
		flammableRegistry.add(YEW_STAIRS, 5, 20);
		flammableRegistry.add(YEW_SLAB, 5, 20);
		flammableRegistry.add(YEW_FENCE, 5, 20);
		flammableRegistry.add(YEW_FENCE_GATE, 5, 20);
		CompostingChanceRegistry compostRegistry = CompostingChanceRegistry.INSTANCE;
		compostRegistry.add(YEW_LEAVES, 0.3f);
		compostRegistry.add(YEW_SAPLING, 0.3f);
	}
}