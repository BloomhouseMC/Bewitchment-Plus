package dev.mrsterner.bewitchmentplus.common.curse;

import dev.mrsterner.bewitchmentplus.common.registry.BWPStatusEffects;
import moriyashiine.bewitchment.api.registry.Curse;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;

public class HalfLifeCurse extends Curse {
    public HalfLifeCurse(Type type) {
        super(type);
    }

    @Override
    public void tick(LivingEntity target) {
        target.addStatusEffect(new StatusEffectInstance(BWPStatusEffects.HALF_LIFE, 20 * 20, 0, false, false, true));
    }
}
