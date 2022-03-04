package dev.mrsterner.bewitchmentplus.client.renderer;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.client.BewitchmentPlusClient;
import dev.mrsterner.bewitchmentplus.client.shader.BWPShader;
import dev.mrsterner.bewitchmentplus.common.entity.RuneEntity;
import dev.mrsterner.bewitchmentplus.common.utils.RenderHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.EndPortalBlockEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3f;

import java.util.function.Function;

import static dev.mrsterner.bewitchmentplus.client.renderlayer.BWPRenderLayers.RUNE_LAYER;
import static net.minecraft.screen.PlayerScreenHandler.BLOCK_ATLAS_TEXTURE;


public class RuneEntityRenderer extends EntityRenderer<RuneEntity> {
    //private static final Sprite SPRITE = ((SpriteAtlasTexture) MinecraftClient.getInstance().getTextureManager().getTexture(new Identifier("minecraft", "textures/atlas/blocks.png"))).getSprite(new Identifier("block/water_still"));
    public static final SpriteIdentifier RUNE_SPRITE = new SpriteIdentifier(BLOCK_ATLAS_TEXTURE, new Identifier(BewitchmentPlus.MODID, "block/pentacle"));


    public RuneEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(RuneEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider provider, int light) {
        double ticks = (BewitchmentPlusClient.ClientTickHandler.ticksInGame + tickDelta) * 0.5;
        matrices.push();
        matrices.translate(0.5, 1.5, 0.5);
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion((float) Math.sin(ticks/100) * 10));
        matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion((float) Math.cos(ticks/100) * 10));
        if (true) {
            matrices.push();
            float v = 1F / 8F;
            int runes = 16;
            if (true) {
                if (runes > 0) {
                    final float modifier = 6F;
                    final float rotationModifier = 0.25F;
                    final float radiusBase = (float) Math.exp(Math.sin(ticks/100)) * 100;
                    final float radiusMod = 0.1F;
                    float offsetPerRune = 360.0F / runes;
                    matrices.push();
                    matrices.translate(-0.05F, -0.5F, 0F);
                    matrices.scale(v, v, v);
                    for (int i = 0; i < runes; i++) {
                        float offset = offsetPerRune * i;
                        float offsetEase = MathHelper.lerp(tickDelta, offset, offset + offsetPerRune);
                        float deg = (int) (ticks / rotationModifier % 360F + offset);
                        float rad = deg * (float) Math.PI / 180F;
                        float radiusX = (float) (radiusBase + radiusMod * Math.sin(ticks / modifier));
                        float radiusZ = (float) (radiusBase + radiusMod * Math.cos(ticks / modifier));
                        float x = (float) (radiusX * Math.cos(rad));
                        float z = (float) (radiusZ * Math.sin(rad));
                        matrices.push();
                        matrices.translate(x, 0, z);
                        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-deg + 100));
                        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90));
                        matrices.scale(20,20,20);
                        float minU = RUNE_SPRITE.getSprite().getMinU();
                        float maxU = RUNE_SPRITE.getSprite().getMaxU();
                        float minV = RUNE_SPRITE.getSprite().getMinV();
                        float maxV = RUNE_SPRITE.getSprite().getMaxV();
                        Matrix4f matrix4f = matrices.peek().getPositionMatrix();
                        renderPortalLayer(new Identifier(BewitchmentPlus.MODID, "textures/block/pentacle_mask.png"),matrix4f, provider, 3,3,light, OverlayTexture.DEFAULT_UV, new float[]{1F, 1F, 0.2F, 1F});
                        //RenderHelper.drawTexture(provider.getBuffer(RenderLayer.getEndGateway()), matrices, RUNE.getSprite() ,minU, minV, maxU, maxV, Color.ofRGB(1.0F,1.0F,1.0F).getColor(), light, OverlayTexture.DEFAULT_UV, 1.0F);
                        if(radiusBase % 80 >= 50){
                            entity.world.addParticle(ParticleTypes.HAPPY_VILLAGER, entity.getX()+(x/10),entity.getY(),entity.getZ()+(z/10),0,0,0);
                        }
                        matrices.pop();
                    }
                    matrices.pop();
                }
            }
            matrices.pop();
        }
        matrices.pop();
    }

    @Override
    public boolean shouldRender(RuneEntity entity, Frustum frustum, double x, double y, double z) {
        return true;
    }

    @Override
    public Identifier getTexture(RuneEntity entity) {
        return null;
    }

    public static void renderPortalLayer(Identifier base, Matrix4f matrix4f, VertexConsumerProvider vertexConsumers, float sizeX, float sizeY, int light, int overlay, float[] rgba) {
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(getPortalEffect(base));

        vertexConsumer.vertex(matrix4f, 0, 0, sizeY).color(rgba[0], rgba[1], rgba[2], rgba[3])
        .texture(0, 1).light(light).overlay(overlay).normal(0, 1, 0).next();
        vertexConsumer.vertex(matrix4f, sizeX, 0, sizeY).color(rgba[0], rgba[1], rgba[2], rgba[3])
        .texture(1, 1).light(light).overlay(overlay).normal(0, 1, 0).next();
        vertexConsumer.vertex(matrix4f, sizeX, 0, 0).color(rgba[0], rgba[1], rgba[2], rgba[3])
        .texture(1, 0).light(light).overlay(overlay).normal(0, 1, 0).next();
        vertexConsumer.vertex(matrix4f, 0, 0, 0).color(rgba[0], rgba[1], rgba[2], rgba[3])
        .texture(0, 0).light(light).overlay(overlay).normal(0, 1, 0).next();
    }

    public static RenderLayer getPortalEffect(Identifier texture) {
        return RUNE_LAYER.apply(texture);
    }
}
