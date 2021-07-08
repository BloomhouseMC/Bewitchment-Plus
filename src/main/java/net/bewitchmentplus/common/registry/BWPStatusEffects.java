package net.bewitchmentplus.common.registry;

import net.bewitchmentplus.BewitchmentPlus;
import net.bewitchmentplus.common.statuseffects.GrowthStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

public class BWPStatusEffects {
	private static final Map<StatusEffect, Identifier> STATUS_EFFECTS = new LinkedHashMap<>();

	public static final StatusEffect GROWTH = create("growth", new GrowthStatusEffect(StatusEffectType.BENEFICIAL, 0x71BC78));

	private static <T extends StatusEffect> T create(String name, T effect) {
		STATUS_EFFECTS.put(effect, new Identifier(BewitchmentPlus.MODID, name));
		return effect;
	}

	public static void init() {
		STATUS_EFFECTS.keySet().forEach(effect -> Registry.register(Registry.STATUS_EFFECT, STATUS_EFFECTS.get(effect), effect));
	}
}
