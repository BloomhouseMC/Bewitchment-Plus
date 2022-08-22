package dev.mrsterner.bewitchmentplus.common;

import eu.midnightdust.lib.config.MidnightConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.world.biome.Biome;

import java.util.Arrays;
import java.util.List;

public class BWPConfig extends MidnightConfig {



	@Entry public static List<String> blackDogBiomeCategories = List.of(ConventionalBiomeTags.PLAINS.toString());
	@Entry public static int blackDogWeight = 8;
	@Entry	public static int blackDogMinGroupCount = 0;
	@Entry	public static int blackDogMaxGroupCount = 2;

	@Entry	public static List<String> cambionBiomeCategories = Arrays.asList(ConventionalBiomeTags.IN_NETHER.toString(), ConventionalBiomeTags.DESERT.toString());
	@Entry	public static int cambionWeight = 12;
	@Entry	public static int cambionMinGroupCount = 0;
	@Entry	public static int cambionMaxGroupCount = 2;


	@Entry	public static boolean blackDogStructureSpawn = true;
	@Entry	public static boolean cambionVillageStructureSpawn = true;
	@Entry	public static int yewTreeWeight = 10;
	@Entry  public static float lotusTreeWeight = 50;


	@Entry	public static boolean allowGetVampBloodFromGobletAndAthame = false;

	@Entry	public static boolean allowSummoningPlayersWithDeathGearInsteadOfDeath = false;


}