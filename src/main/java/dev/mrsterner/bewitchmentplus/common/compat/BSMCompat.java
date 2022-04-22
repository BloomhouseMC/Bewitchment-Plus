package dev.mrsterner.bewitchmentplus.common.compat;

import dev.mrsterner.besmirchment.common.registry.BSMTransformations;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public class BSMCompat {
    public static boolean isBesmirchmentLoaded(LivingEntity mob){
        boolean isLich =  mob instanceof PlayerEntity player && (BSMTransformations.isLich(player, true) || BSMTransformations.isLich(player, false));//TODO test if this doesnt crash if besmirchment isnt loaded
        boolean isWerepyre =  mob instanceof PlayerEntity player && (BSMTransformations.isWerepyre(player, true) || BSMTransformations.isWerepyre(player, false));
        return isLich || isWerepyre;
    }
}
