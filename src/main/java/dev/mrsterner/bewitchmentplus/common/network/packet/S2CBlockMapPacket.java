package dev.mrsterner.bewitchmentplus.common.network.packet;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class S2CBlockMapPacket {
    public static final Identifier ID = new Identifier(BewitchmentPlus.MODID, "block_map");
    public static void send(LivingEntity entity) {
        PacketByteBuf data = new PacketByteBuf(Unpooled.buffer());
        data.writeInt(entity.getId());
        PlayerLookup.tracking(entity).forEach(p -> ServerPlayNetworking.send(p, ID, data));
        if (entity instanceof ServerPlayerEntity) {
            ServerPlayNetworking.send((ServerPlayerEntity) entity, ID, data);
        }
    }

    @Environment(EnvType.CLIENT)
    public static void handle(MinecraftClient client, ClientPlayNetworkHandler networkHandler, PacketByteBuf packetByteBuf, PacketSender sender) {
        if (client.world != null) {
            Entity entity = client.world.getEntityById(packetByteBuf.readInt());
            if (entity != null) {
                client.execute(() -> {

                });
            }
        }
    }

}
