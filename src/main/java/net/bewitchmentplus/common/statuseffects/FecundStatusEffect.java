package net.bewitchmentplus.common.statuseffects;

import moriyashiine.bewitchment.common.statuseffect.EmptyStatusEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.World;

public class FecundStatusEffect extends EmptyStatusEffect {
	public FecundStatusEffect(StatusEffectType type, int color) {
		super(type, color);
	}

	@Override
	public boolean isInstant() {
		return false;
	}

	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier) {
		return true;
	}

	@Override
	public void applyUpdateEffect(LivingEntity entity, int amplifier) {
		if (entity instanceof AnimalEntity) {
			if (entity.isAlive() && !((AnimalEntity) entity).isInLove()) {
				((AnimalEntity) entity).setLoveTicks(5000);
				((AnimalEntity) entity).isInLove();
			}
		}
	}
}
