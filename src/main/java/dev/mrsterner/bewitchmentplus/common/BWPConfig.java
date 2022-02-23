package dev.mrsterner.bewitchmentplus.common;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import net.minecraft.world.biome.Biome;

import java.util.Arrays;
import java.util.List;

@Config(name = BewitchmentPlus.MODID)
public class BWPConfig implements ConfigData {

	public final List<String> blackDogBiomeCategories = Arrays.asList(Biome.Category.PLAINS.getName());
	public final int blackDogWeight = 8;
	public final int blackDogMinGroupCount = 0;
	public final int blackDogMaxGroupCount = 2;

	public final List<String> cambionBiomeCategories = Arrays.asList(Biome.Category.NETHER.getName(), Biome.Category.DESERT.getName());
	public final int cambionWeight = 12;
	public final int cambionMinGroupCount = 0;
	public final int cambionMaxGroupCount = 2;

	@ConfigEntry.Gui.RequiresRestart
	public boolean blackDogStructureSpawn = true;

	@ConfigEntry.Gui.RequiresRestart
	public boolean cambionVillageStructureSpawn = true;

	@ConfigEntry.Gui.RequiresRestart
	public boolean cambionBastionStructureSpawn = true;

	@ConfigEntry.Gui.RequiresRestart
	public boolean cambionNetherFortressStructureSpawn = true;

}