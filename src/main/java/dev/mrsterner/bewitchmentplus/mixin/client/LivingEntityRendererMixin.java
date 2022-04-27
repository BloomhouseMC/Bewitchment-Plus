package dev.mrsterner.bewitchmentplus.mixin.client;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketsApi;
import dev.mrsterner.bewitchmentplus.common.interfaces.CrownOfForest;
import dev.mrsterner.bewitchmentplus.common.registry.BWPObjects;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.List;

@Environment(EnvType.CLIENT)
@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> {

    @Inject(method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At("HEAD"))
    private void renderTree(T livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        /*
        if (livingEntity instanceof PlayerEntity player && player.isSneaking()) {
            List<Pair<SlotReference, ItemStack>> component = TrinketsApi.getTrinketComponent(player).get().getEquipped(BWPObjects.CROWN_OF_THE_FOREST);
            if (!component.isEmpty()) {
                BlockPos origin = player.getBlockPos();
                matrixStack.push();
                matrixStack.translate(-0.5, 0, -0.5);
                CrownOfForest.of(player).ifPresent(crownOfForest -> {
                    HashMap<BlockPos, BlockState> structureMap = crownOfForest.getParsedMap();
                    System.out.println("StructureMap: "+structureMap);
                    for (int j = 0; j < structureMap.size(); j++) {
                        BlockPos blockPos = (BlockPos) structureMap.keySet().toArray()[j];
                        BlockState blockState = structureMap.get(blockPos);
                        BlockPos normalizedBlockPos = origin.subtract(new Vec3i(blockPos.getX(), blockPos.getY(), blockPos.getZ()));
                        matrixStack.translate(normalizedBlockPos.getX(), normalizedBlockPos.getY(), normalizedBlockPos.getZ());
                        MinecraftClient.getInstance().getBlockRenderManager().renderBlockAsEntity(blockState, matrixStack, vertexConsumerProvider, i, OverlayTexture.DEFAULT_UV);
                    }
                });
                matrixStack.pop();
            }



        }

         */


    }
}
