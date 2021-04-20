package net.bewitchmentplus;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.bewitchmentplus.common.BWPConfig;
import net.bewitchmentplus.common.registry.BWPEntitySpawns;
import net.bewitchmentplus.common.registry.BWPEntityTypes;
import net.bewitchmentplus.common.registry.BWPObjects;
import net.bewitchmentplus.common.registry.BWPStatusEffects;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//R DZH SVIV
//HFMXLMFIV11
//HKRMLHZFIFH111
//MLGOVTZOGVMWVI
//OVHLERPP
//WZGFIZ
//GSILFTS GSVHV ORMVH R WL WVXIVV
//GSZG YB NVIVOB KFGGRMT NB MZNV RM
//NB RMUOFVMXV TILDH, ZMW GSFH, R YVXLNV RNNLIGZO
//XBYVIMVGRX DVY


//GL HLNV, R ZN YFG Z HSZWV
//YFG SVIV, R VCVIG KLDVI LEVI GSVN
//R NZWV Z XZHGOV SVIV LM GSRH SROO
//ZMW SZEV KFG NB UOZT RM GSV TILFMW
//GSRH RH NB MVD DLIOW


//HSLFOW GSV GIVHKZHHVIH LU GSV LOW DLIOW
//ZIIREV SVIV, YVZIRMT SLHGRORGRVH
//GSVB DROO YV NVG DRGS DIZGS

public class BewitchmentPlus implements ModInitializer {
	public static final String MODID = "bwplus";
	public static final Logger logger = LogManager.getLogger(MODID);
	public static final ItemGroup BEWITCHMENT_PLUS_MOBS_GROUP = FabricItemGroupBuilder.build(new Identifier(MODID, MODID), () -> new ItemStack(BWPObjects.THYRSUS_ITEM));
	public static BWPConfig config;

	@Override
	public void onInitialize() {

		AutoConfig.register(BWPConfig.class, GsonConfigSerializer::new);
		config = AutoConfig.getConfigHolder(BWPConfig.class).getConfig();
		logger.info("Remember when I told you how my");
		logger.info("Kin is different in some ways?");

		logger.info("It's a fact, she is exactly that!");
		logger.info("A harbinger of death from the world of witchcraft,");
		logger.info("And she's feeding them cakes and her ale to this innocent boy,");
		logger.info("And her magic brings dismay!");

		logger.info("I hear her in the wind, the bane of our town");
		logger.info("Come with me, father, I'm to expose a heathen");

		BWPEntityTypes.init();
		BWPObjects.init();
		BWPEntitySpawns.init();
		BWPStatusEffects.init();
	}
}
