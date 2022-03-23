package dev.mrsterner.bewitchmentplus.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.common.entity.UnicornEntity;
import dev.mrsterner.bewitchmentplus.common.registry.BWPStatusEffects;
import moriyashiine.bewitchment.api.BewitchmentAPI;
import moriyashiine.bewitchment.common.Bewitchment;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
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
    private static final Identifier HALF_LIFE_HEARTS = new Identifier(BewitchmentPlus.MODID, "textures/gui/half_life_hearts.png");

    @Unique
    private static final Identifier EMPTY_TEXTURE = new Identifier(Bewitchment.MODID, "textures/gui/empty.png");

    @Unique
    private boolean boundSpecialBackground;

    @Unique
    private StatusEffectInstance renderedEffect;

    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    private int scaledHeight;

    @Shadow
    private int scaledWidth;

    @Shadow protected abstract PlayerEntity getCameraPlayer();

    @Inject(method = "renderStatusBars", at = @At(value = "INVOKE", shift = At.Shift.AFTER, ordinal = 3, target = "Lnet/minecraft/client/MinecraftClient;getProfiler()Lnet/minecraft/util/profiler/Profiler;"))
    private void renderPre(MatrixStack matrices, CallbackInfo callbackInfo) {
        PlayerEntity player = getCameraPlayer();
        if (BewitchmentAPI.isVampire(player, true)) {
            RenderSystem.setShaderTexture(0, HALF_LIFE_HEARTS);
            if (player.isInSneakingPose() && client.targetedEntity instanceof UnicornEntity livingEntity) {
                drawHalfLife(matrices, livingEntity, scaledWidth / 2 + 13, scaledHeight / 2 + 9);
            }
            RenderSystem.setShaderTexture(0, GUI_ICONS_TEXTURE);
        }
    }

    private void drawHalfLife(MatrixStack matrices, LivingEntity entity, int x, int y) {
        for(int i = 0; i < (int)entity.getHealth()/4; i++) {
            drawTexture(matrices, (x - i * 8), y, 0,  0, 9, 9);
        }
    }

    @ModifyVariable(method = "renderStatusEffectOverlay", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIIIII)V"))
    private StatusEffectInstance drawCustomBackground(StatusEffectInstance effect) {
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

    @Inject(method = "drawHeart", at = @At("HEAD"), cancellable = true)
    private void drawCustomHeart(MatrixStack matrices, InGameHud.HeartType type, int x, int y, int v, boolean blinking, boolean halfHeart, CallbackInfo ci) {
        if (type == InGameHud.HeartType.NORMAL && MinecraftClient.getInstance().cameraEntity instanceof PlayerEntity player && player.hasStatusEffect(BWPStatusEffects.HALF_LIFE)) {
            RenderSystem.setShaderTexture(0, HALF_LIFE_HEARTS);
            drawTexture(matrices, x, y, halfHeart ? 9 : 0, v, 9, 9);
            RenderSystem.setShaderTexture(0, GUI_ICONS_TEXTURE);
            ci.cancel();
        }
    }
}