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

	@ConfigEntry.Gui.CollapsibleObject
	public Mechanics mechanics = new Mechanics();



	public static class Entities {
		public final List<String> blackDogBiomeCategories = Arrays.asList(Biome.Category.PLAINS.getName());
		public final int blackDogWeight = 8;
		public final int blackDogMinGroupCount = 0;
		public final int blackDogMaxGroupCount = 2;

		public final List<String> cambionBiomeCategories = Arrays.asList(Biome.Category.NETHER.getName(), Biome.Category.DESERT.getName());
		public final int cambionWeight = 12;
		public final int cambionMinGroupCount = 0;
		public final int cambionMaxGroupCount = 2;
	}


	public static class World {
		public final boolean blackDogStructureSpawn = true;
		public final boolean cambionVillageStructureSpawn = true;

		@ConfigEntry.Gui.Tooltip
		@ConfigEntry.BoundedDiscrete(max = 100, min = 1)
		public final int yewTreeWeight = 10;
		@ConfigEntry.Gui.Tooltip
		@ConfigEntry.BoundedDiscrete(max = 100, min = 1)
        public float lotusTreeWeight = 50;
    }

	public static class Mechanics {
		@ConfigEntry.Gui.Tooltip
		public final boolean allowGetVampBloodFromGobletAndAthame = false;
	}

}