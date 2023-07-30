package dev.mrsterner.bewitchmentplus.common.registry;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.common.ritualfunction.BindSpectralFamiliarRitualFunction;
import dev.mrsterner.bewitchmentplus.common.ritualfunction.EffigyRitualFunction;
import dev.mrsterner.bewitchmentplus.common.ritualfunction.SpectralFamiliarRitualFunction;
import moriyashiine.bewitchment.api.registry.RitualFunction;
import moriyashiine.bewitchment.common.registry.BWRegistries;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class BWPRitualFunctions {
    public static final RitualFunction SUMMON_SPECTRAL_FAMILIAR = new SpectralFamiliarRitualFunction(ParticleTypes.HAPPY_VILLAGER, livingEntity -> livingEntity.getType().isIn(BWPTags.SACRIFICES));
    public static final RitualFunction MAKE_EFFIGY = new EffigyRitualFunction(ParticleTypes.HAPPY_VILLAGER, null);
    public static final RitualFunction SUMMON_DEATH = new BindSpectralFamiliarRitualFunction(ParticleTypes.SOUL, null);

    public static <T> T register(Registry<? super T> registry, String name, T entry) {
        return Registry.register(registry, new Identifier(BewitchmentPlus.MODID, name), entry);
    }


    public static void init() {
        register(BWRegistries.RITUAL_FUNCTION, "spectral_familiar", SUMMON_SPECTRAL_FAMILIAR);
        register(BWRegistries.RITUAL_FUNCTION, "make_effigy", MAKE_EFFIGY);
    }
}
