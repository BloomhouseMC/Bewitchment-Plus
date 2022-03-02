package dev.mrsterner.bewitchmentplus.mixin;

import dev.mrsterner.bewitchmentplus.common.item.GobletBlockItem;
import moriyashiine.bewitchment.common.item.AthameItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    public PlayerEntityRendererMixin(EntityRendererFactory.Context ctx, PlayerEntityModel<AbstractClientPlayerEntity> model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }


    /**
     * When the player is holding athame and goblet, we use the Block pose
     * @param abstractClientPlayerEntity
     * @param hand
     * @param cir
     */
    @Inject(at = @At("HEAD"), method = "getArmPose", cancellable = true)
    @Environment(EnvType.CLIENT)
    private static void getArmPose(AbstractClientPlayerEntity abstractClientPlayerEntity, Hand hand, CallbackInfoReturnable<BipedEntityModel.ArmPose> cir) {
        if (abstractClientPlayerEntity.getMainHandStack().getItem() instanceof AthameItem) {
            if (abstractClientPlayerEntity.getOffHandStack().getItem() instanceof GobletBlockItem) {
                cir.setReturnValue(BipedEntityModel.ArmPose.BLOCK);
            }
        }
    }
}
