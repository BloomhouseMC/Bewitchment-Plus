package dev.mrsterner.bewitchmentplus.common.curse;

import dev.mrsterner.bewitchmentplus.common.registry.BWPCurses;
import dev.mrsterner.bewitchmentplus.common.registry.BWPStatusEffects;
import moriyashiine.bewitchment.api.registry.Curse;
import moriyashiine.bewitchment.common.registry.BWComponents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;

public class HalfLifeCurse extends Curse {
    public HalfLifeCurse(Type type) {
        super(type);
    }

    @Override
    public void tick(LivingEntity target) {
        BWComponents.CURSES_COMPONENT.maybeGet(target).ifPresent(cursesComponent -> {
            for (Curse.Instance instance : cursesComponent.getCurses()) {
                if(cursesComponent.hasCurse(BWPCurses.HALF_LIFE)){
                    if(instance.duration > 20 * 2){
                        target.addStatusEffect(new StatusEffectInstance(BWPStatusEffects.HALF_LIFE, 20 * 12, 0, false, false, true));
                    }
                }
            }
        });
    }
}
