package dev.mrsterner.bewitchmentplus.mixin.common;

import dev.mrsterner.bewitchmentplus.common.entity.YewBroomEntity;
import moriyashiine.bewitchment.api.BewitchmentAPI;
import moriyashiine.bewitchment.common.component.entity.BroomUserComponent;
import moriyashiine.bewitchment.common.network.packet.TogglePressingForwardPacket;
import moriyashiine.bewitchment.common.registry.BWComponents;
import moriyashiine.bewitchment.common.registry.BWEntityTypes;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TogglePressingForwardPacket.class)
public abstract class TogglePressingForwardPacketMixin {
    @Inject(method = "handle", at = @At(value = "HEAD"), cancellable = true)
    private static void handleYewBroom(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler network, PacketByteBuf buf, PacketSender sender, CallbackInfo ci){
        boolean pressingForward = buf.readBoolean();
        if (pressingForward && BewitchmentAPI.getFamiliar(player) != BWEntityTypes.OWL && !(player.getVehicle() instanceof YewBroomEntity)) {
            if (!BewitchmentAPI.drainMagic(player, 1, true)) {
                return;
            }

            if (player.age % 60 == 0) {
                BewitchmentAPI.drainMagic(player, 1, false);
            }
        }
        BWComponents.BROOM_USER_COMPONENT.get(player).setPressingForward(pressingForward);
        ci.cancel();
    }
}
