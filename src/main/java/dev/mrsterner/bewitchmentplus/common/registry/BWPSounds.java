package dev.mrsterner.bewitchmentplus.common.registry;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BWPSounds {

    public static SoundEvent MUSIC_DISC_PETALS = register("music_disc.petals");

    static SoundEvent register(String id) {
        SoundEvent sound = new SoundEvent(new Identifier(BewitchmentPlus.MODID, id));
        Registry.register(Registry.SOUND_EVENT, new Identifier(BewitchmentPlus.MODID, id), sound);
        return sound;
    }
}