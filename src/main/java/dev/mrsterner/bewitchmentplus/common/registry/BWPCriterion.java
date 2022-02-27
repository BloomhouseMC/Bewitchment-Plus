package dev.mrsterner.bewitchmentplus.common.registry;

import dev.mrsterner.bewitchmentplus.common.criterion.MagicalCriterion;
import net.fabricmc.fabric.api.object.builder.v1.advancement.CriterionRegistry;

public class BWPCriterion {
    public static final MagicalCriterion MAGICAL_CRITERION = new MagicalCriterion();

    public static void init(){
        CriterionRegistry.register(MAGICAL_CRITERION);
    }
}
