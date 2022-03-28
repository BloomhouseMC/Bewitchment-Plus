package dev.mrsterner.bewitchmentplus.common.network.packet;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.common.registry.BWPObjects;
import dev.mrsterner.bewitchmentplus.common.registry.BWPTransformations;
import io.netty.buffer.Unpooled;
import moriyashiine.bewitchment.client.network.packet.SpawnSmokeParticlesPacket;
import moriyashiine.bewitchment.common.registry.BWComponents;
import moriyashiine.bewitchment.common.registry.BWEntityTypes;
import moriyashiine.bewitchment.common.registry.BWScaleTypes;
import moriyashiine.bewitchment.common.registry.BWSoundEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import virtuoel.pehkui.api.ScaleData;



public class TransformationLeshonPacket {
    private static final float WEREWOLF_WIDTH = BWEntityTypes.WEREWOLF.getWidth() / EntityType.PLAYER.getWidth();
    private static final float WEREWOLF_HEIGHT = BWEntityTypes.WEREWOLF.getHeight() / EntityType.PLAYER.getHeight();


    public static final Identifier ID = new Identifier(BewitchmentPlus.MODID, "transformation_leshon");
    public static void send() {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        ClientPlayNetworking.send(ID, buf);
    }

    public static void handle(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler network, PacketByteBuf buf, PacketSender sender) {
        server.execute(() -> {
            if (canUseAbility(player)) {
                useAbility(player, false);
            }
        });
    }

    private static boolean canUseAbility(PlayerEntity player) {
        return player.getEquippedStack(EquipmentSlot.HEAD).getItem().equals(BWPObjects.LESHON_SKULL.asItem());
    }

    public static void useAbility(PlayerEntity player, boolean forced) {
        BWComponents.TRANSFORMATION_COMPONENT.maybeGet(player).ifPresent(transformationComponent -> {
            World world = player.world;
            boolean isAlternateForm = transformationComponent.isAlternateForm();
            ScaleData width = BWScaleTypes.MODIFY_WIDTH_TYPE.getScaleData(player);
            ScaleData height = BWScaleTypes.MODIFY_HEIGHT_TYPE.getScaleData(player);
            if (transformationComponent.getTransformation() == BWPTransformations.LESHON && forced) {
                PlayerLookup.tracking(player).forEach(trackingPlayer -> SpawnSmokeParticlesPacket.send(trackingPlayer, player));
                SpawnSmokeParticlesPacket.send(player, player);
                world.playSound(null, player.getBlockPos(), BWSoundEvents.ENTITY_GENERIC_TRANSFORM, player.getSoundCategory(), 1, 1);
                transformationComponent.setAlternateForm(!isAlternateForm);
                if (isAlternateForm) {
                    width.setScale(width.getBaseScale() / WEREWOLF_WIDTH);
                    height.setScale(height.getBaseScale() / WEREWOLF_HEIGHT);
                } else {
                    width.setScale(width.getBaseScale() * WEREWOLF_WIDTH);
                    height.setScale(height.getBaseScale() * WEREWOLF_HEIGHT);
                }
            }
        });
    }
}
