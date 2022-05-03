package dev.mrsterner.bewitchmentplus.common.network.packet;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.common.registry.BWPParticleTypes;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class S2CBloodParticlesPacket {
    public static final Identifier ID = new Identifier(BewitchmentPlus.MODID, "spawn_blood_particles");

    public static void send(PlayerEntity player, Entity entity) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeInt(entity.getId());
        ServerPlayNetworking.send((ServerPlayerEntity) player, ID, buf);
    }

    public static void handle(MinecraftClient client, ClientPlayNetworkHandler network, PacketByteBuf buf, PacketSender sender) {
        int id = buf.readInt();
        client.execute(() -> {
            ClientWorld world = client.world;
            if (world != null) {
                Entity entity = world.getEntityById(id);
                if (entity != null) {
                    BlockPos pos = entity.getBlockPos();
                    if(entity instanceof CowEntity){
                        world.addParticle(BWPParticleTypes.MILK_DRAIN, true, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.1, 0f, 0f, 0f);
                        world.addParticle(BWPParticleTypes.LIFE_DRAIN, true, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0f, 0f, 0f);
                    }else if(entity instanceof WitherSkeletonEntity){
                        world.addParticle(BWPParticleTypes.WITHER_DRAIN, true, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0f, 0f, 0f);
                    }else{
                        world.addParticle(BWPParticleTypes.LIFE_DRAIN, true, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0f, 0f, 0f);
                    }

                }
            }
        });
    }
}