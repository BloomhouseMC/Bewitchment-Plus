package dev.mrsterner.bewitchmentplus;

import dev.mrsterner.bewitchmentplus.common.BWPConfig;
import dev.mrsterner.bewitchmentplus.common.block.blockentity.MimicChestBlockEntity;
import dev.mrsterner.bewitchmentplus.common.item.GobletBlockItem;
import dev.mrsterner.bewitchmentplus.common.registry.*;
import dev.mrsterner.bewitchmentplus.common.world.BWPWorldState;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import moriyashiine.bewitchment.common.item.AthameItem;
import moriyashiine.bewitchment.common.misc.BWUtil;
import moriyashiine.bewitchment.common.registry.BWComponents;
import moriyashiine.bewitchment.common.registry.BWObjects;
import moriyashiine.bewitchment.common.registry.BWTransformations;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

import static net.minecraft.block.ChestBlock.CHEST_TYPE;
import static net.minecraft.block.ChestBlock.FACING;


public class BewitchmentPlus implements ModInitializer {
	public static final String MODID = "bwplus";
	public static final ItemGroup BEWITCHMENT_PLUS_GROUP = FabricItemGroupBuilder.build(new Identifier(MODID, MODID), () -> new ItemStack(BWPObjects.SILVER_GOBLET));
	public static BWPConfig config;

	@Override
	public void onInitialize() {
		AutoConfig.register(BWPConfig.class, GsonConfigSerializer::new);
		config = AutoConfig.getConfigHolder(BWPConfig.class).getConfig();

		BWPEntityTypes.init();
		BWPObjects.init();
		BWPBlockEntityTypes.init();
		BWPEntitySpawns.init();
		BWPCriterion.init();
		BWPRitualFunctions.init();
		BWPStatusEffects.init();

		UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
			if(player.getStackInHand(hand).getItem().equals(BWPObjects.MOONLIGHT_INFUSION)){
				if(world.getBlockState(hitResult.getBlockPos()).getBlock() instanceof FlowerPotBlock flowerPotBlock && flowerPotBlock.getContent() != null){
					world.setBlockState(hitResult.getBlockPos(), BWPObjects.MOONFLOWER.getDefaultState());
					world.playSound(null, hitResult.getBlockPos(),SoundEvents.BLOCK_FIRE_EXTINGUISH,SoundCategory.BLOCKS,1,1);
				}
			}
			return ActionResult.PASS;
		});

		UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
			BlockEntity blockEntity = world.getBlockEntity(hitResult.getBlockPos());
			if(blockEntity instanceof ChestBlockEntity && !world.isClient()){
				BWPWorldState worldState = BWPWorldState.get(world);
				BlockPos blockPos = hitResult.getBlockPos();
				for (int i = worldState.mimicChestsPair.size() - 1; i >= 0; i--) {
					if(worldState.mimicChestsPair.get(i).getRight().equals(blockPos.asLong())){
						if(player.getUuid().equals(worldState.mimicChestsPair.get(i).getLeft())){
							return ActionResult.PASS;
						}else{
							DefaultedList<ItemStack> temporatyLeechInventory = DefaultedList.ofSize(27, ItemStack.EMPTY);
							BlockState blockState = world.getBlockState(blockPos);
							boolean single = blockState.get(CHEST_TYPE) == ChestType.SINGLE;
							if(single){
								Inventory chestInventory = ChestBlock.getInventory((ChestBlock)blockState.getBlock(), blockState, world, blockPos, true);
								for(int j = 0; j < chestInventory.size(); j++){
									temporatyLeechInventory.set(j, chestInventory.getStack(j));
									chestInventory.setStack(j, ItemStack.EMPTY);
								}
								BlockState newLeechChestBlockState = BWPObjects.MIMIC_CHEST.getDefaultState().with(Properties.HORIZONTAL_FACING, blockState.get(FACING));
								world.setBlockState(blockPos, newLeechChestBlockState);
								MimicChestBlockEntity leechChestBlockEntity = (MimicChestBlockEntity) world.getBlockEntity(blockPos);
								leechChestBlockEntity.getInventoryChest();
								for(int k = 0; k < temporatyLeechInventory.size(); k++){
									leechChestBlockEntity.getInventoryChest().set(k, temporatyLeechInventory.get(k));
								}
								temporatyLeechInventory.clear();
								return ActionResult.SUCCESS;
							}
						}

					}
				}


			}
			return ActionResult.PASS;
		});



		UseItemCallback.EVENT.register((player, world, hand) -> {
			if (!world.isClient() && player.getMainHandStack().getItem() instanceof AthameItem) {
				if (player.getOffHandStack().getItem() instanceof GobletBlockItem) {
					if(!player.getOffHandStack().hasNbt()){
						world.playSound(null, player.getBlockPos(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.PLAYERS, 1, 0.5f);
						NbtCompound compound = new NbtCompound();
						var slots = DefaultedList.ofSize(1, BWObjects.BOTTLE_OF_BLOOD.getDefaultStack());
						Inventories.writeNbt(compound, slots);
						compound.putInt("Color", 0xff0000);
						compound.putBoolean("VampireBlood", BWComponents.TRANSFORMATION_COMPONENT.get(player).getTransformation() == BWTransformations.VAMPIRE);
						compound.put("Goblet", player.getOffHandStack().getItem().getDefaultStack().writeNbt(new NbtCompound()));
						player.getOffHandStack().getOrCreateNbt().put("BlockEntityTag", compound);
						player.damage(DamageSource.player(player), BWComponents.TRANSFORMATION_COMPONENT.get(player).getTransformation() == BWTransformations.VAMPIRE ? player.getHealth() - 1 : 4);
						return TypedActionResult.consume(player.getMainHandStack());
					}
				}
			}
			return TypedActionResult.pass(player.getMainHandStack());
		});

	}
}
