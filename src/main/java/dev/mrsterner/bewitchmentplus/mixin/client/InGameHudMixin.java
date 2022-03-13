package dev.mrsterner.bewitchmentplus.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.common.registry.BWPStatusEffects;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public abstract class InGameHudMixin extends DrawableHelper {
    @Unique
    private static final Identifier BWPLUS_BACKGROUND_TEXTURE = new Identifier(BewitchmentPlus.MODID, "textures/gui/inventory.png");

    @Unique
    private boolean boundSpecialBackground;

    @Unique
    private StatusEffectInstance renderedEffect;

    @Shadow
    @Final
    private MinecraftClient client;

    @ModifyVariable(method = "renderStatusEffectOverlay", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIIIII)V"))
    private StatusEffectInstance customizeDrawnBackground(StatusEffectInstance effect) {
        if (effect.getEffectType() instanceof BWPStatusEffects.BWPStatusEffect) {
            assert this.client != null;
            RenderSystem.setShaderTexture(0, BWPLUS_BACKGROUND_TEXTURE);
            boundSpecialBackground = true;
        }
        renderedEffect = effect;
        return effect;
    }

    @Inject(method = "renderStatusEffectOverlay", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIIIII)V", shift = At.Shift.AFTER))
    private void restoreDrawnBackground(CallbackInfo ci) {
        if (boundSpecialBackground) {
            RenderSystem.setShaderTexture(0, HandledScreen.BACKGROUND_TEXTURE);
            boundSpecialBackground = false;
        }
    }
}