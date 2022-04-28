package dev.mrsterner.bewitchmentplus.common.registry;

import com.mojang.serialization.Codec;
import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.client.particle.LifeDrainParticle;
import dev.mrsterner.bewitchmentplus.client.particle.LifeDrainParticleEffect;
import dev.mrsterner.bewitchmentplus.client.particle.LifeDrainTrailParticle;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BWPParticleTypes {
    public static DefaultParticleType LIFE_DRAIN;
    public static DefaultParticleType MILK_DRAIN;
    public static DefaultParticleType WITHER_DRAIN;
    public static ParticleType<LifeDrainParticleEffect> LIFE_DRAIN_TRAIL;


    public static void init() {
        LIFE_DRAIN = Registry.register(Registry.PARTICLE_TYPE, "bwplus:life_drain", FabricParticleTypes.simple(true));
        MILK_DRAIN = Registry.register(Registry.PARTICLE_TYPE, "bwplus:milk_drain", FabricParticleTypes.simple(true));
        WITHER_DRAIN = Registry.register(Registry.PARTICLE_TYPE, "bwplus:wither_drain", FabricParticleTypes.simple(true));
        ParticleFactoryRegistry.getInstance().register(BWPParticleTypes.LIFE_DRAIN, fabricSpriteProvider -> new LifeDrainParticle.Factory(fabricSpriteProvider, new Identifier(BewitchmentPlus.MODID, "textures/entity/life_drain.png"), 0.75f, 0.0f, 0.25f, 0.0f, -0.1f, -0.1f));
        ParticleFactoryRegistry.getInstance().register(BWPParticleTypes.MILK_DRAIN, fabricSpriteProvider -> new LifeDrainParticle.Factory(fabricSpriteProvider, new Identifier(BewitchmentPlus.MODID, "textures/entity/life_drain.png"), 1.0f, 1.0f, 1.0f, -0.01f, -0.01f, -0.01f));
        ParticleFactoryRegistry.getInstance().register(BWPParticleTypes.WITHER_DRAIN, fabricSpriteProvider -> new LifeDrainParticle.Factory(fabricSpriteProvider, new Identifier(BewitchmentPlus.MODID, "textures/entity/life_drain.png"), 0.0f, 0.0f, 0.0f, 0.01f, 0.0f, 0.01f));

        LIFE_DRAIN_TRAIL = Registry.register(Registry.PARTICLE_TYPE, "bwplus:life_drain_trail", new ParticleType<LifeDrainParticleEffect>(true, LifeDrainParticleEffect.PARAMETERS_FACTORY) {
            @Override
            public Codec<LifeDrainParticleEffect> getCodec() {
                return LifeDrainParticleEffect.CODEC;
            }
        });
        ParticleFactoryRegistry.getInstance().register(BWPParticleTypes.LIFE_DRAIN_TRAIL, LifeDrainTrailParticle.Factory::new);
    }
}
