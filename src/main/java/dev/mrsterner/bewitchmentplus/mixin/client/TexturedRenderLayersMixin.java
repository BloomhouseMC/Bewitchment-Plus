package dev.mrsterner.bewitchmentplus.mixin.client;

import dev.mrsterner.bewitchmentplus.common.block.yew.YewChestBlockEntity;
import dev.mrsterner.bewitchmentplus.common.registry.BWPSpriteIdentifiers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Consumer;

import static dev.mrsterner.bewitchmentplus.common.registry.BWPSpriteIdentifiers.*;

@Environment(EnvType.CLIENT)
@Mixin(TexturedRenderLayers.class)
public class TexturedRenderLayersMixin {
    @Inject(method = "addDefaultTextures", at = @At("HEAD"))
    private static void injectCustomTextures(Consumer<SpriteIdentifier> consumer, CallbackInfo info) {
        for (SpriteIdentifier identifier : BWPSpriteIdentifiers.INSTANCE.getIdentifiers()) {
            consumer.accept(identifier);
        }
    }

    @Inject(method = "getChestTexture(Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/block/enums/ChestType;Z)Lnet/minecraft/client/util/SpriteIdentifier;", at = @At("HEAD"), cancellable = true)
    private static void getChestTexture(BlockEntity blockEntity, ChestType type, boolean christmas, CallbackInfoReturnable<SpriteIdentifier> callbackInfo) {
        if (blockEntity instanceof YewChestBlockEntity bwChest) {
            switch (type) {
                case SINGLE -> callbackInfo.setReturnValue(bwChest.trapped ? TRAPPED_YEW_CHEST : YEW_CHEST);
                case LEFT -> callbackInfo.setReturnValue(bwChest.trapped ? TRAPPED_YEW_CHEST_LEFT : YEW_CHEST_LEFT);
                case RIGHT -> callbackInfo.setReturnValue(bwChest.trapped ? TRAPPED_YEW_CHEST_RIGHT : YEW_CHEST_RIGHT);
            }
        }
    }
}
