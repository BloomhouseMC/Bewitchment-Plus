package dev.mrsterner.bewitchmentplus.common.item;

import net.minecraft.item.MusicDiscItem;
import net.minecraft.sound.SoundEvent;

public class BWPMusicDisc extends MusicDiscItem {


    public BWPMusicDisc(int comparatorOutput, SoundEvent sound, Settings settings, int lengthInSeconds) {
        super(comparatorOutput, sound, settings, lengthInSeconds);
    }
}