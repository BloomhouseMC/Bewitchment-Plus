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

	@ConfigEntry.Gui.CollapsibleObject
	public Entities entities = new Entities();

	@ConfigEntry.Gui.CollapsibleObject
	public World world = new World();



	public static class Entities {
		public  List<String> blackDogBiomeCategories = Arrays.asList(Biome.Category.PLAINS.getName());
		public  int blackDogWeight = 8;
		public  int blackDogMinGroupCount = 0;
		public  int blackDogMaxGroupCount = 2;

		public  List<String> cambionBiomeCategories = Arrays.asList(Biome.Category.NETHER.getName(), Biome.Category.DESERT.getName());
		public  int cambionWeight = 12;
		public  int cambionMinGroupCount = 0;
		public  int cambionMaxGroupCount = 2;
	}


	public static class World {
		@ConfigEntry.Gui.RequiresRestart
		public boolean blackDogStructureSpawn = true;

		@ConfigEntry.Gui.RequiresRestart
		public boolean cambionVillageStructureSpawn = true;

		@ConfigEntry.Gui.RequiresRestart
		public boolean cambionBastionStructureSpawn = true;

		@ConfigEntry.Gui.RequiresRestart
		public boolean cambionNetherFortressStructureSpawn = true;
	}

}