package dev.mrsterner.bewitchmentplus;

import dev.mrsterner.bewitchmentplus.common.BWPConfig;
import dev.mrsterner.bewitchmentplus.common.block.blockentity.MimicChestBlockEntity;
import dev.mrsterner.bewitchmentplus.common.entity.EffigyEntity;
import dev.mrsterner.bewitchmentplus.common.entity.UnicornEntity;
import dev.mrsterner.bewitchmentplus.common.item.GobletBlockItem;
import dev.mrsterner.bewitchmentplus.common.network.packet.TransformationLeshonPacket;
import dev.mrsterner.bewitchmentplus.common.registry.*;
import dev.mrsterner.bewitchmentplus.common.utils.RenderHelper;
import dev.mrsterner.bewitchmentplus.common.world.BWPWorldState;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import moriyashiine.bewitchment.api.BewitchmentAPI;
import moriyashiine.bewitchment.api.component.BloodComponent;
import moriyashiine.bewitchment.api.event.BloodSuckEvents;
import moriyashiine.bewitchment.common.item.AthameItem;
import moriyashiine.bewitchment.common.item.TaglockItem;
import moriyashiine.bewitchment.common.registry.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static net.minecraft.block.ChestBlock.CHEST_TYPE;
import static net.minecraft.block.ChestBlock.FACING;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class BewitchmentPlus implements ModInitializer {
	public static final String MODID = "bwplus";
	public static final ItemGroup BEWITCHMENT_PLUS_GROUP = FabricItemGroupBuilder.build(new Identifier(MODID, MODID), () -> new ItemStack(BWPObjects.SILVER_GOBLET));
	public static BWPConfig config;
	public static final Logger LOGGER = LoggerFactory.getLogger("Bewitchment Plus");

	@Override
	public void onInitialize() {
		AutoConfig.register(BWPConfig.class, GsonConfigSerializer::new);
		config = AutoConfig.getConfigHolder(BWPConfig.class).getConfig();
		ServerPlayNetworking.registerGlobalReceiver(TransformationLeshonPacket.ID, TransformationLeshonPacket::handle);

		BWPEntityTypes.init();
		BWPObjects.init();
		BWPBlockEntityTypes.init();
		BWPEntitySpawns.init();
		BWPCriterion.init();
		BWPRitualFunctions.init();
		BWPStatusEffects.init();
		BWPTransformations.init();
		BWPCurses.init();
		BWPSounds.init();
		BWPWorldGenerators.init();

		UseBlockCallback.EVENT.register(this::createMoonflower);
		UseBlockCallback.EVENT.register(this::createMimic);
		UseItemCallback.EVENT.register(this::gobletFillWithAthame);
		UseEntityCallback.EVENT.register(this::succUnicorn);


		//TODO remove this when effigy is fully implemented
		UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			if(!world.isClient && player.getStackInHand(hand).getItem() instanceof TaglockItem && entity instanceof EffigyEntity effigyEntity){
				ItemStack tagLock = player.getStackInHand(hand);
				UUID ownerUUID = TaglockItem.getTaglockUUID(tagLock);
				PlayerEntity playerEntity = world.getPlayerByUuid(ownerUUID);
				if(!BWPComponents.EFFIGY_COMPONENT.get(playerEntity).getHasEffigy()){
					BWPComponents.EFFIGY_COMPONENT.get(playerEntity).setEffigy(effigyEntity.getUuid());
					BWPComponents.EFFIGY_COMPONENT.get(playerEntity).setHasEffigy(true);
					effigyEntity.playSound(SoundEvents.BLOCK_ROOTS_BREAK, 3F, 1);
					player.getStackInHand(hand).decrement(1);
					return ActionResult.SUCCESS;
				}
			}
			return ActionResult.PASS;
		});

	}
	private ActionResult succUnicorn(PlayerEntity player, World world, Hand hand, Entity entity, HitResult hitResult) {
		if (entity instanceof UnicornEntity livingEntity && hand == Hand.MAIN_HAND && player.isSneaking() && entity.isAlive() && BewitchmentAPI.isVampire(player, true) && player.getStackInHand(hand).isEmpty()) {
			int toGive = (int) livingEntity.getHealth() / 4;
			toGive = BloodSuckEvents.BLOOD_AMOUNT.invoker().onBloodSuck(player, livingEntity, toGive);
			if (toGive > 0) {
				BloodComponent playerBloodComponent = BWComponents.BLOOD_COMPONENT.get(player);
				if (playerBloodComponent.fillBlood(toGive, true)) {
					if (!world.isClient && livingEntity.hurtTime == 0) {
						BloodSuckEvents.ON_BLOOD_SUCK.invoker().onBloodSuck(player, livingEntity, toGive);
						world.playSound(null, player.getBlockPos(), SoundEvents.ITEM_HONEY_BOTTLE_DRINK, player.getSoundCategory(), 1, 0.5f);
						playerBloodComponent.fillBlood(toGive, false);
						player.addStatusEffect(new StatusEffectInstance(BWPStatusEffects.HALF_LIFE, 20 * 10, 1, false, false, true));
						entity.damage(BWDamageSources.VAMPIRE, 2);
					}
					return ActionResult.PASS;
				}
			}
		}
		return ActionResult.PASS;
	}

	/**
	 * Converts a potted plant to the MoonlightFlower with the moonlight infusion
	 * @param player
	 * @param world
	 * @param hand
	 * @param blockHitResult
	 * @return
	 */
	private ActionResult createMoonflower(PlayerEntity player, World world, Hand hand, BlockHitResult blockHitResult) {
		if(player.getStackInHand(hand).getItem().equals(BWPObjects.MOONLIGHT_INFUSION)){
			if(world.getBlockState(blockHitResult.getBlockPos()).getBlock() instanceof FlowerPotBlock flowerPotBlock && flowerPotBlock.getContent().asItem() != ItemStack.EMPTY.getItem()){
				world.setBlockState(blockHitResult.getBlockPos(), BWPObjects.MOONFLOWER.getDefaultState());
				world.playSound(null, blockHitResult.getBlockPos(),SoundEvents.BLOCK_FIRE_EXTINGUISH,SoundCategory.BLOCKS,1,1);
				for(int x = -1; x < 2; x++){
					for(int y = -1; y < 2; y++){
						for(int z = -1; z < 2; z++){
							world.addParticle(ParticleTypes.END_ROD, blockHitResult.getBlockPos().getX() + 0.5 + x * 4/16F + world.getRandom().nextGaussian()/4,blockHitResult.getBlockPos().getY() + 0.5 + y * 4/16F + world.getRandom().nextGaussian()/4, blockHitResult.getBlockPos().getZ() + 0.5 + z * 4/16F + world.getRandom().nextGaussian()/4, 0, 0, 0);
						}
					}
				}

				return ActionResult.SUCCESS;
			}
		}
		return ActionResult.PASS;
	}

	/**
	 * Checks if a chest is really a mimic and then transfers its inventory to the new mimic if the player interacting isnt the owner of the mimic
	 * @param player
	 * @param world
	 * @param hand
	 * @param blockHitResult
	 * @return
	 */
	private ActionResult createMimic(PlayerEntity player, World world, Hand hand, BlockHitResult blockHitResult) {
		BlockEntity blockEntity = world.getBlockEntity(blockHitResult.getBlockPos());
		if(blockEntity instanceof ChestBlockEntity && !world.isClient()){
			BWPWorldState worldState = BWPWorldState.get(world);
			BlockPos blockPos = blockHitResult.getBlockPos();
			for (int i = worldState.mimicChestsPair.size() - 1; i >= 0; i--) {
				if(worldState.mimicChestsPair.get(i).getRight().equals(blockPos.asLong())){
					if(player.getUuid().equals(worldState.mimicChestsPair.get(i).getLeft())){
						return ActionResult.PASS;
					}else{
						DefaultedList<ItemStack> temporaryMimicInventory = DefaultedList.ofSize(27, ItemStack.EMPTY);
						BlockState blockState = world.getBlockState(blockPos);
						boolean single = blockState.get(CHEST_TYPE) == ChestType.SINGLE;
						if(single){
							Inventory chestInventory = ChestBlock.getInventory((ChestBlock)blockState.getBlock(), blockState, world, blockPos, true);
							for(int j = 0; j < chestInventory.size(); j++){
								temporaryMimicInventory.set(j, chestInventory.getStack(j));
								chestInventory.setStack(j, ItemStack.EMPTY);
							}
							BlockState newLeechChestBlockState = BWPObjects.MIMIC_CHEST.getDefaultState().with(Properties.HORIZONTAL_FACING, blockState.get(FACING));
							world.setBlockState(blockPos, newLeechChestBlockState);
							MimicChestBlockEntity leechChestBlockEntity = (MimicChestBlockEntity) world.getBlockEntity(blockPos);
							leechChestBlockEntity.getInventoryChest();
							for(int k = 0; k < temporaryMimicInventory.size(); k++){
								leechChestBlockEntity.getInventoryChest().set(k, temporaryMimicInventory.get(k));
							}
							temporaryMimicInventory.clear();
							return ActionResult.SUCCESS;
						}
					}
				}
			}
		}
		return ActionResult.PASS;
	}


	/**
	 * Allows you to cut yourself with athame in off-hand to fill it with blood
	 * @param player
	 * @param world
	 * @param hand
	 * @return
	 */
	public TypedActionResult<ItemStack> gobletFillWithAthame(PlayerEntity player, World world, Hand hand){
		if (!world.isClient() && player.getMainHandStack().getItem() instanceof AthameItem) {
			if (player.getOffHandStack().getItem() instanceof GobletBlockItem) {
				if(!player.getOffHandStack().hasNbt()){
					world.playSound(null, player.getBlockPos(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.PLAYERS, 1, 0.5f);
					NbtCompound compound = new NbtCompound();
					var slots = DefaultedList.ofSize(1, BWObjects.BOTTLE_OF_BLOOD.getDefaultStack());
					Inventories.writeNbt(compound, slots);
					compound.putInt("Color", RenderHelper.BLOOD_COLOR);
					compound.putBoolean("VampireBlood", BWComponents.TRANSFORMATION_COMPONENT.get(player).getTransformation() == BWTransformations.VAMPIRE);
					compound.put("Goblet", player.getOffHandStack().getItem().getDefaultStack().writeNbt(new NbtCompound()));
					player.getOffHandStack().getOrCreateNbt().put("BlockEntityTag", compound);
					player.damage(DamageSource.player(player), BWComponents.TRANSFORMATION_COMPONENT.get(player).getTransformation() == BWTransformations.VAMPIRE ? player.getHealth() - 1 : 4);
					return TypedActionResult.consume(player.getMainHandStack());
				}
			}
		}
		return TypedActionResult.pass(player.getMainHandStack());
	}
}
