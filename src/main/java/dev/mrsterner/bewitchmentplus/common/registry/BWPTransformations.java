package dev.mrsterner.bewitchmentplus.common.registry;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.common.transformation.LeshonTransformation;
import moriyashiine.bewitchment.api.registry.Transformation;
import moriyashiine.bewitchment.common.registry.BWComponents;
import moriyashiine.bewitchment.common.registry.BWRegistries;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BWPTransformations {
    public static final Transformation LESHON = new LeshonTransformation();

    public static void init(){
        register(BWRegistries.TRANSFORMATIONS, "leshon", LESHON);

    }

    public static boolean isLeshon(PlayerEntity entity, boolean isTreeBoi){
        return BWComponents.TRANSFORMATION_COMPONENT.get(entity).getTransformation() == LESHON && (!isTreeBoi || BWComponents.TRANSFORMATION_COMPONENT.get(entity).isAlternateForm());
    }

    public static <T> T register(Registry<? super T> registry, String name, T entry) {
        return Registry.register(registry, new Identifier(BewitchmentPlus.MODID, name), entry);
    }

}
