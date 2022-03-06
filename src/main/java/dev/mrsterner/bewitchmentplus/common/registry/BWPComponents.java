package dev.mrsterner.bewitchmentplus.common.registry;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.common.component.EffigyComponent;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class BWPComponents implements EntityComponentInitializer {
    public static final ComponentKey<EffigyComponent> EFFIGY_COMPONENT = ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(BewitchmentPlus.MODID, "effigy"), EffigyComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.beginRegistration(PlayerEntity.class, EFFIGY_COMPONENT).impl(EffigyComponent.class).end(EffigyComponent::new);
    }


}
