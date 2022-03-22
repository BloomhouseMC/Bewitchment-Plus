package dev.mrsterner.bewitchmentplus.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.common.registry.BWPStatusEffects;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(AbstractInventoryScreen.class)
public abstract class AbstractInventoryScreenMixin<T extends ScreenHandler> extends HandledScreen<T> {
    @Unique
    private boolean isBackgroundSwitched;
    @Unique
    private static final Identifier BWP_BACKGROUND_TEXTURE = new Identifier(BewitchmentPlus.MODID, "textures/gui/inventory.png");
    public AbstractInventoryScreenMixin(T container, PlayerInventory playerInventory, Text name) {
        super(container, playerInventory, name);
    }

    @ModifyArgs(method = "drawStatusEffectDescriptions", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/effect/StatusEffectUtil;durationToString(Lnet/minecraft/entity/effect/StatusEffectInstance;F)Ljava/lang/String;"))
    private void stopDrawingDurationWhenSpecialEffect(Args args){
        StatusEffectInstance statusEffectInstance = args.get(0);
        if(statusEffectInstance.getEffectType() instanceof BWPStatusEffects.BWPStatusEffect && statusEffectInstance.getAmplifier() == 0){
            args.set(1, 0.0F);
        }
    }

    @ModifyVariable(method = "drawStatusEffectBackgrounds", at = @At(value = "STORE"))
    private StatusEffectInstance customizeDrawnBackground(StatusEffectInstance effect) {
        if (effect.getEffectType() instanceof BWPStatusEffects.BWPStatusEffect) {
            assert client != null;
            RenderSystem.setShaderTexture(0, BWP_BACKGROUND_TEXTURE);
            isBackgroundSwitched = true;
        }
        return effect;
    }

    @Inject(method = "drawStatusEffectBackgrounds", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ingame/AbstractInventoryScreen;drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIIIII)V", shift = At.Shift.AFTER))
    private void restoreDrawnBackground(MatrixStack matrices, int x, int height, Iterable<StatusEffectInstance> statusEffects, boolean bl, CallbackInfo ci) {
        if (isBackgroundSwitched) {
            assert client != null;
            RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);
            isBackgroundSwitched = false;
        }
    }
}