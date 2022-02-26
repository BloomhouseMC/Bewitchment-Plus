package dev.mrsterner.bewitchmentplus.client.renderer.entity;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.common.block.blockentity.GobletBlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.world.World;

import static dev.mrsterner.bewitchmentplus.common.block.GobletBlock.LIQUID_STATE;
import static net.minecraft.screen.PlayerScreenHandler.BLOCK_ATLAS_TEXTURE;

public class GobletBlockEntityRenderer implements BlockEntityRenderer<GobletBlockEntity> {
    public static final SpriteIdentifier WATER_SPRITE = new SpriteIdentifier(BLOCK_ATLAS_TEXTURE, new Identifier("block/water_still"));
    public static final SpriteIdentifier HONEY_SPRITE = new SpriteIdentifier(BLOCK_ATLAS_TEXTURE, new Identifier(BewitchmentPlus.MODID, "block/honey_flow"));

    @Override
    public void render(GobletBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        World world = entity.getWorld();
        if (world != null) {
            int liquid_state = entity.getCachedState().get(LIQUID_STATE);
            if(liquid_state!=0){
                matrices.push();
                matrices.translate(0,0.45,0);
                renderWater(entity, matrices, vertexConsumers.getBuffer(RenderLayer.getTranslucent()), light, overlay, liquid_state != 2 ? WATER_SPRITE.getSprite() : HONEY_SPRITE.getSprite());
                matrices.translate(0,0.005,0);
                renderWater(entity, matrices, vertexConsumers.getBuffer(RenderLayer.getTranslucent()), light, overlay, liquid_state != 2 ? WATER_SPRITE.getSprite() : HONEY_SPRITE.getSprite());
                matrices.pop();
            }
        }
    }

    private void renderWater(GobletBlockEntity entity, MatrixStack matrices, VertexConsumer buffer, int light, int overlay, Sprite sprite) {
        matrices.push();
        Matrix4f mat = matrices.peek().getPositionMatrix();
        float sizeFactor = 0.4f;
        float maxV = (sprite.getMaxV() - sprite.getMinV()) * sizeFactor;
        int red = (entity.color >> 16) & 0xff;
        int green = (entity.color >> 8) & 0xff;
        int blue = entity.color & 0xff;
        buffer.vertex(mat, sizeFactor, 0, 1 - sizeFactor).color(red, green, blue, 255).texture(sprite.getMinU(), sprite.getMinV() + maxV).light(light).overlay(overlay).normal(1, 1, 1).next();
        buffer.vertex(mat, 1 - sizeFactor, 0, 1 - sizeFactor).color(red, green, blue, 255).texture(sprite.getMaxU(), sprite.getMinV() + maxV).light(light).overlay(overlay).normal(1, 1, 1).next();
        buffer.vertex(mat, 1 - sizeFactor, 0, sizeFactor).color(red, green, blue, 255).texture(sprite.getMaxU(), sprite.getMaxV()).light(light).overlay(overlay).normal(1, 1, 1).next();
        buffer.vertex(mat, sizeFactor, 0, sizeFactor).color(red, green, blue, 255).texture(sprite.getMinU(), sprite.getMaxV()).light(light).overlay(overlay).normal(1, 1, 1).next();
        matrices.pop();
    }
}
