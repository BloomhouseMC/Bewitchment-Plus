package dev.mrsterner.bewitchmentplus.common.registry;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.common.ritualfunction.EffigyRitualFunction;
import dev.mrsterner.bewitchmentplus.common.ritualfunction.SpectralFamiliarRitualFunction;
import moriyashiine.bewitchment.api.registry.RitualFunction;
import moriyashiine.bewitchment.common.entity.living.util.BWTameableEntity;
import moriyashiine.bewitchment.common.registry.BWRegistries;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

public class BWPRitualFunctions {
    //private static final Map<RitualFunction, Identifier> RITUAL_FUNCTIONS = new LinkedHashMap<>();


    public static final RitualFunction SUMMON_SPECTRAL_FAMILIAR = new SpectralFamiliarRitualFunction(ParticleTypes.HAPPY_VILLAGER, livingEntity -> livingEntity instanceof BWTameableEntity);
    public static final RitualFunction MAKE_EFFIGY = new EffigyRitualFunction(ParticleTypes.HAPPY_VILLAGER, null);

    public static <T> T register(Registry<? super T> registry, String name, T entry) {
        return Registry.register(registry, new Identifier(BewitchmentPlus.MODID, name), entry);
    }
    /*
    private static <T extends RitualFunction> T register(String name, T ritualFunction) {
        RITUAL_FUNCTIONS.put(ritualFunction, new Identifier(BewitchmentPlus.MODID, name));
        return ritualFunction;
    }

     */

    public static void init() {
        register(BWRegistries.RITUAL_FUNCTIONS, "spectral_familiar", SUMMON_SPECTRAL_FAMILIAR);
        register(BWRegistries.RITUAL_FUNCTIONS, "make_effigy", MAKE_EFFIGY);
        //RITUAL_FUNCTIONS.keySet().forEach(contract -> Registry.register(BWRegistries.RITUAL_FUNCTIONS, RITUAL_FUNCTIONS.get(contract), contract));
    }
}
