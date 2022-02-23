package dev.mrsterner.bewitchmentplus.client.compat;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.mrsterner.bewitchmentplus.common.BWPConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;

@Environment(EnvType.CLIENT)
public class BWPlusModMenu implements ModMenuApi {
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return (ConfigScreenFactory<Screen>) screen -> AutoConfig.getConfigScreen(BWPConfig.class, screen).get();
	}
}
