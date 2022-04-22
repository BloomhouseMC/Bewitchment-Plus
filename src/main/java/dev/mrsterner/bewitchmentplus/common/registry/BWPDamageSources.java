package dev.mrsterner.bewitchmentplus.common.registry;

import net.minecraft.entity.damage.DamageSource;

public class BWPDamageSources {
    public static final DamageSource LEECH = new LeechDamageSource("leech");

    private static class LeechDamageSource extends DamageSource {

        public LeechDamageSource(String name) {
            super(name);
        }
    }
}
