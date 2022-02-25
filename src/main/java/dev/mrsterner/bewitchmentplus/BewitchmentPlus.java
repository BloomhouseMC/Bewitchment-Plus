package dev.mrsterner.bewitchmentplus;

import dev.mrsterner.bewitchmentplus.common.BWPConfig;
import dev.mrsterner.bewitchmentplus.common.registry.BWPBlockEntityTypes;
import dev.mrsterner.bewitchmentplus.common.registry.BWPEntitySpawns;
import dev.mrsterner.bewitchmentplus.common.registry.BWPEntityTypes;
import dev.mrsterner.bewitchmentplus.common.registry.BWPObjects;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;


public class BewitchmentPlus implements ModInitializer {
	public static final String MODID = "bwplus";
	public static final ItemGroup BEWITCHMENT_PLUS_GROUP = FabricItemGroupBuilder.build(new Identifier(MODID, MODID), () -> new ItemStack(Items.ANDESITE));
	public static BWPConfig config;

	@Override
	public void onInitialize() {
		AutoConfig.register(BWPConfig.class, GsonConfigSerializer::new);
		config = AutoConfig.getConfigHolder(BWPConfig.class).getConfig();

		BWPEntityTypes.init();
		BWPObjects.init();
		BWPBlockEntityTypes.init();
		BWPEntitySpawns.init();
	}
}
