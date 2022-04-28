package dev.mrsterner.bewitchmentplus.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
