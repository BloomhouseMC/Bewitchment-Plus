package dev.mrsterner.bewitchmentplus.common.network.packet;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import io.netty.buffer.Unpooled;
import moriyashiine.bewitchment.common.registry.BWComponents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.Optional;
import java.util.UUID;

public class C2SBloodParticlePacket {
    public static final Identifier ID = new Identifier(BewitchmentPlus.MODID, "blood_heal");
    public static void send(UUID uuid, int entityId) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeUuid(uuid);
        buf.writeInt(entityId);

        ClientPlayNetworking.send(ID, buf);
    }

    public static void handle(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler network, PacketByteBuf buf, PacketSender sender) {
        UUID uuid = buf.readUuid();
        var entity = player.world.getEntityById(buf.readInt());

        server.execute(() -> {
            if(entity != null){
                if(entity instanceof CowEntity cowEntity){
                    BWComponents.BLOOD_COMPONENT.maybeGet(player.world.getPlayerByUuid(uuid)).ifPresent(bloodComponent -> {
                        bloodComponent.fillBlood(1, false);
                    });
                    player.world.getPlayerByUuid(uuid).clearStatusEffects();

                }else if(entity instanceof WitherSkeletonEntity witherSkeletonEntity){
                    (player.world.getPlayerByUuid(uuid)).addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 200), witherSkeletonEntity);
                }else{
                    BWComponents.BLOOD_COMPONENT.maybeGet(player.world.getPlayerByUuid(uuid)).ifPresent(bloodComponent -> {
                        bloodComponent.fillBlood(1, false);
                    });
                }
            }

        });
    }
}
