package dev.mrsterner.bewitchmentplus;

import dev.mrsterner.bewitchmentplus.common.BWPConfig;
import dev.mrsterner.bewitchmentplus.common.item.GobletBlockItem;
import dev.mrsterner.bewitchmentplus.common.registry.*;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import moriyashiine.bewitchment.common.item.AthameItem;
import moriyashiine.bewitchment.common.registry.BWComponents;
import moriyashiine.bewitchment.common.registry.BWObjects;
import moriyashiine.bewitchment.common.registry.BWTransformations;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.collection.DefaultedList;


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
