package dev.mrsterner.bewitchmentplus.common.registry;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public class BWPSounds {
    private static final Map<SoundEvent, Identifier> SOUND_EVENTS = new LinkedHashMap<>();

    public static SoundEvent MUSIC_DISC_PETALS = register("music_disc.petals");

    private static SoundEvent register(String name) {
        Identifier id = new Identifier(BewitchmentPlus.MODID, name);
        SoundEvent soundEvent = SoundEvent.of(id);
        SOUND_EVENTS.put(soundEvent, id);
        return soundEvent;
    }

    public static void init() {
        SOUND_EVENTS.keySet().forEach(soundEvent -> Registry.register(Registries.SOUND_EVENT, SOUND_EVENTS.get(soundEvent), soundEvent));
    }
}