package dev.mrsterner.bewitchmentplus.common.registry;

import dev.mrsterner.bewitchmentplus.common.curse.HalfLifeCurse;
import moriyashiine.bewitchment.api.registry.Curse;
import moriyashiine.bewitchment.common.Bewitchment;
import moriyashiine.bewitchment.common.registry.BWRegistries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public class BWPCurses {
    private static final Map<Curse, Identifier> CURSES = new LinkedHashMap<>();

    public static final Curse HALF_LIFE = create("half_life", new HalfLifeCurse(Curse.Type.GREATER));

    private static <T extends Curse> T create(String name, T curse) {
        CURSES.put(curse, new Identifier(Bewitchment.MOD_ID, name));
        return curse;
    }

    public static void init() {
        CURSES.keySet().forEach(curse -> Registry.register(BWRegistries.CURSE, CURSES.get(curse), curse));
    }
}
