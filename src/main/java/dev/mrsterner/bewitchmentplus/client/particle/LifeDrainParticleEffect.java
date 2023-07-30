package dev.mrsterner.bewitchmentplus.client.particle;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.mrsterner.bewitchmentplus.common.registry.BWPParticleTypes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;

import com.mojang.brigadier.StringReader;
import net.minecraft.registry.Registries;

import java.util.Locale;

//Derivative from Illuminations https://github.com/Ladysnake/Illuminations/blob/1.18/src/main/java/ladysnake/illuminations/client/particle/WispTrailParticleEffect.java
public record LifeDrainParticleEffect(float red, float green, float blue, float redEvolution, float greenEvolution, float blueEvolution) implements ParticleEffect {
    public static final Codec<LifeDrainParticleEffect> CODEC = RecordCodecBuilder.create((instance)
            -> instance.group(Codec.FLOAT.fieldOf("r").forGetter((lifeDrainTrailParticleEffect)
            -> lifeDrainTrailParticleEffect.red), Codec.FLOAT.fieldOf("g").forGetter((lifeDrainTrailParticleEffect)
            -> lifeDrainTrailParticleEffect.green), Codec.FLOAT.fieldOf("b").forGetter((lifeDrainTrailParticleEffect)
            -> lifeDrainTrailParticleEffect.blue), Codec.FLOAT.fieldOf("re").forGetter((lifeDrainTrailParticleEffect)
            -> lifeDrainTrailParticleEffect.redEvolution), Codec.FLOAT.fieldOf("ge").forGetter((lifeDrainTrailParticleEffect)
            -> lifeDrainTrailParticleEffect.greenEvolution), Codec.FLOAT.fieldOf("be").forGetter((lifeDrainTrailParticleEffect)
            -> lifeDrainTrailParticleEffect.blueEvolution)).apply(instance, LifeDrainParticleEffect::new));
    public static final ParticleEffect.Factory<LifeDrainParticleEffect> PARAMETERS_FACTORY = new ParticleEffect.Factory<>() {
        public LifeDrainParticleEffect read(ParticleType<LifeDrainParticleEffect> particleType, StringReader stringReader) throws CommandSyntaxException {
            stringReader.expect(' ');
            float r = (float) stringReader.readDouble();
            stringReader.expect(' ');
            float g = (float) stringReader.readDouble();
            stringReader.expect(' ');
            float b = (float) stringReader.readDouble();
            stringReader.expect(' ');
            float re = (float) stringReader.readDouble();
            stringReader.expect(' ');
            float ge = (float) stringReader.readDouble();
            stringReader.expect(' ');
            float be = (float) stringReader.readDouble();
            return new LifeDrainParticleEffect(r, g, b, re, ge, be);
        }

        public LifeDrainParticleEffect read(ParticleType<LifeDrainParticleEffect> particleType, PacketByteBuf packetByteBuf) {
            return new LifeDrainParticleEffect(packetByteBuf.readFloat(), packetByteBuf.readFloat(), packetByteBuf.readFloat(), packetByteBuf.readFloat(), packetByteBuf.readFloat(), packetByteBuf.readFloat());
        }
    };

    @Override
    public ParticleType<?> getType() {
        return BWPParticleTypes.LIFE_DRAIN_TRAIL;
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeFloat(this.red);
        buf.writeFloat(this.green);
        buf.writeFloat(this.blue);
        buf.writeFloat(this.redEvolution);
        buf.writeFloat(this.greenEvolution);
        buf.writeFloat(this.blueEvolution);
    }

    @Override
    public String asString() {
        return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %.2f %.2f %.2f", Registries.PARTICLE_TYPE.getId(this.getType()), this.red, this.green, this.blue, this.redEvolution, this.greenEvolution, this.blueEvolution);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public float red() {
        return this.red;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public float green() {
        return this.green;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public float blue() {
        return this.blue;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public float redEvolution() {
        return redEvolution;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public float greenEvolution() {
        return greenEvolution;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public float blueEvolution() {
        return blueEvolution;
    }
}
