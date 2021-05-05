package net.bewitchmentplus.common;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import net.bewitchmentplus.BewitchmentPlus;
import net.minecraft.world.biome.Biome;

import java.util.Arrays;
import java.util.List;

@Config(name = BewitchmentPlus.MODID)
public class BWPConfig implements ConfigData {
	public final List<String> drudenBiomeCategories = Arrays.asList(Biome.Category.TAIGA.getName(), Biome.Category.FOREST.getName(), Biome.Category.SWAMP.getName());
	public final int drudenWeight = 8;
	public final int drudenMinGroupCount = 0;
	public final int drudenMaxGroupCount = 2;

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
	public boolean cambionStructureSpawn = true;

	@ConfigEntry.Gui.RequiresRestart
	public boolean bafometyrStructureSpawn = true;

	@ConfigEntry.Gui.RequiresRestart
	public boolean vanillaMaterialsBewitchmentIdols = true;

	@ConfigEntry.Gui.RequiresRestart
	public boolean vanillaMaterialsBewitchmentStatues = true;

	@ConfigEntry.Gui.RequiresRestart
	public boolean bewitchmentSectFleeceBlocks = true;
}