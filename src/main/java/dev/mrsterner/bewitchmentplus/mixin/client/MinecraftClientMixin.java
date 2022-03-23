package dev.mrsterner.bewitchmentplus.mixin.client;

import dev.mrsterner.bewitchmentplus.BewitchmentPlusClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Shadow
    public abstract boolean isPaused();

    @Shadow
    private float pausedTickDelta;

    @Shadow
    public abstract float getTickDelta();

    @Shadow
    @Nullable
    public ClientPlayerEntity player;

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/GameRenderer;render(FJZ)V"))
    private void onFrameStart(boolean tick, CallbackInfo ci) {
        BewitchmentPlusClient.ClientTickHandler.renderTick(isPaused() ? pausedTickDelta : getTickDelta());
    }

    @ModifyVariable(method = "startIntegratedServer(Ljava/lang/String;Ljava/util/function/Function;Ljava/util/function/Function;ZLnet/minecraft/client/MinecraftClient$WorldLoadAction;)V", at = @At(value = "FIELD", target = "Lnet/minecraft/client/MinecraftClient$WorldLoadAction;NONE:Lnet/minecraft/client/MinecraftClient$WorldLoadAction;", ordinal = 0), ordinal = 2, index = 11, name = "bl2", require = 1)
    private boolean theDamnExperimentalSettings(boolean bl2) {
        return false;
    }
}