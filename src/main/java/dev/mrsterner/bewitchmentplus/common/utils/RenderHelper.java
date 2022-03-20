package dev.mrsterner.bewitchmentplus.common.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.renderer.v1.mesh.Mesh;
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.api.renderer.v1.model.ModelHelper;
import net.minecraft.client.render.*;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vector4f;

import java.util.List;

import static dev.mrsterner.bewitchmentplus.client.renderlayer.BWPRenderLayers.RUNE_LAYER;

public class RenderHelper {
    public static final int HONEY_COLOR = 0xff9500;
    public static final int BLOOD_COLOR = 0xff0000;
    public static final int UNICORN_BLOOD_COLOR = 0xd4fdff;
    public static final int WATER_COLOR = 0x3f76e4;

    public static Vector4f intToRGB(int i){
        float r = ((i >> 16) & 0xff) / 255.0f;
        float g = ((i >>  8) & 0xff) / 255.0f;
        float b = ((i      ) & 0xff) / 255.0f;
        float a = ((i >> 24) & 0xff) / 255.0f;

        return new Vector4f(r,g,b,a);
    }

    public static int getColor(int... colors) {
        int r = 0;
        int g = 0;
        int b = 0;
        int total = 1;
        for(int i = 1; i < colors.length; i++) {
            if(colors[i] >= 0) {
                r += (colors[i] >> 16) & 0xFF;
                g += (colors[i] >> 8) & 0xFF;
                b += (colors[i]) & 0xFF;
                total++;
            }
        }
        if(total > 1) {
            r += ((colors[0] >> 16) & 0xFF) / 2;
            g += ((colors[0] >> 8) & 0xFF) / 2;
            b += ((colors[0]) & 0xFF) / 2;
        }else {
            r += ((colors[0] >> 16) & 0xFF);
            g += ((colors[0] >> 8) & 0xFF);
            b += ((colors[0]) & 0xFF);
        }
        r /= total;
        g /= total;
        b /= total;
        int color = r;
        color = (color << 8) + g;
        color = (color << 8) + b;
        return color;
    }

    public static void emitFluidFace(QuadEmitter emitter, Sprite sprite, int color, Direction direction, float height, float depth, float EDGE_SIZE, float INNER_SIZE) {
        emitter.square(direction, EDGE_SIZE, EDGE_SIZE, (1f - EDGE_SIZE), (EDGE_SIZE + (height * INNER_SIZE)), EDGE_SIZE + (depth * INNER_SIZE));
        emitter.spriteBake(0, sprite, MutableQuadView.BAKE_ROTATE_NONE);
        emitter.spriteColor(0, color, color, color, color);
        emitter.sprite(0, 0, sprite.getMinU() + EDGE_SIZE * (sprite.getMaxU() - sprite.getMinU()), sprite.getMinV() + (1f - (EDGE_SIZE + (height * INNER_SIZE))) * (sprite.getMaxV() - sprite.getMinV()));
        emitter.sprite(1, 0, sprite.getMinU() + EDGE_SIZE * (sprite.getMaxU() - sprite.getMinU()), sprite.getMinV() + (1f - EDGE_SIZE) * (sprite.getMaxV() - sprite.getMinV()));
        emitter.sprite(2, 0, sprite.getMinU() + (1f - EDGE_SIZE) * (sprite.getMaxU() - sprite.getMinU()), sprite.getMinV() + (1f - EDGE_SIZE) * (sprite.getMaxV() - sprite.getMinV()));
        emitter.sprite(3, 0, sprite.getMinU() + (1f - EDGE_SIZE) * (sprite.getMaxU() - sprite.getMinU()), sprite.getMinV() + (1f - (EDGE_SIZE + (height * INNER_SIZE))) * (sprite.getMaxV() - sprite.getMinV()));
        emitter.emit();
    }

    public static void renderMesh(Mesh mesh, MatrixStack matrices, VertexConsumer consumer, int light, int overlay) {
        for (List<BakedQuad> bakedQuads : ModelHelper.toQuadLists(mesh)) {
            for (BakedQuad bq : bakedQuads) {
                consumer.quad(matrices.peek(), bq, new float[]{1f, 1f, 1f, 1f}, 1f, 1f, 1f, new int[]{light, light, light, light}, overlay, true);
            }
        }
    }

    private static void add(VertexConsumer renderer, MatrixStack stack, float x, float y, float z, float u, float v, int color, int light, int overlay, float alpha) {
        renderer.vertex(stack.peek().getPositionMatrix(), x, y, z)
        .color((((color >> 16) & 0xFF) / 255F), (((color >> 8) & 0xFF) / 255F), (color & 0xFF) / 255F, alpha)
        .texture(u, v)
        .light(light)
        .normal(1, 0, 0)
        .next();
    }

    public static void drawTexture(VertexConsumer renderer, MatrixStack matrices, Sprite sprite, int color, int light, int overlay, float alpha) {
        add(renderer, matrices, 0, 1, 0, sprite.getMinU(), sprite.getMinV(), color, light, overlay, alpha);
        add(renderer, matrices, 1, 1, 0, sprite.getMaxU(), sprite.getMinV(), color, light, overlay, alpha);
        add(renderer, matrices, 1, 1, 1, sprite.getMaxU(), sprite.getMaxV(), color, light, overlay, alpha);
        add(renderer, matrices, 0, 1, 1, sprite.getMinU(), sprite.getMaxV(), color, light, overlay, alpha);
        add(renderer, matrices, 0, 1, 1, sprite.getMinU(), sprite.getMaxV(), color, light, overlay, alpha);
        add(renderer, matrices, 1, 1, 1, sprite.getMaxU(), sprite.getMaxV(), color, light, overlay, alpha);
        add(renderer, matrices, 1, 1, 0, sprite.getMaxU(), sprite.getMinV(), color, light, overlay, alpha);
        add(renderer, matrices, 0, 1, 0, sprite.getMinU(), sprite.getMinV(), color, light, overlay, alpha);
    }

    public static void drawTexture(VertexConsumer renderer, MatrixStack matrices, Sprite sprite, int color, int light, int overlay, float alpha, boolean inverted) {
        if(inverted) {
            drawTexture(renderer, matrices, sprite, color, light, overlay, alpha, true);
        }else{
            add(renderer, matrices, 1, 1, 0, sprite.getMinU(), sprite.getMinV(), color, light, overlay, alpha);
            add(renderer, matrices, 0, 1, 0, sprite.getMaxU(), sprite.getMinV(), color, light, overlay, alpha);
            add(renderer, matrices, 0, 1, 1, sprite.getMaxU(), sprite.getMaxV(), color, light, overlay, alpha);
            add(renderer, matrices, 1, 1, 1, sprite.getMinU(), sprite.getMaxV(), color, light, overlay, alpha);
            add(renderer, matrices, 1, 1, 1, sprite.getMinU(), sprite.getMaxV(), color, light, overlay, alpha);
            add(renderer, matrices, 0, 1, 1, sprite.getMaxU(), sprite.getMaxV(), color, light, overlay, alpha);
            add(renderer, matrices, 0, 1, 0, sprite.getMaxU(), sprite.getMinV(), color, light, overlay, alpha);
            add(renderer, matrices, 1, 1, 0, sprite.getMinU(), sprite.getMinV(), color, light, overlay, alpha);
        }
    }

    public static void drawTexture(VertexConsumer renderer, MatrixStack matrices, Sprite sprite, float minU, float minV, float maxU, float maxV, int color, int light, int overlay, float alpha) {
        add(renderer, matrices, 0, 1, 0, minU, minV, color, light, overlay, alpha);
        add(renderer, matrices, 1, 1, 0, maxU, minV, color, light, overlay, alpha);
        add(renderer, matrices, 1, 1, 1, maxU, maxV, color, light, overlay, alpha);
        add(renderer, matrices, 0, 1, 1, minU, maxV, color, light, overlay, alpha);
        add(renderer, matrices, 0, 1, 1, minU, maxV, color, light, overlay, alpha);
        add(renderer, matrices, 1, 1, 1, maxU, maxV, color, light, overlay, alpha);
        add(renderer, matrices, 1, 1, 0, maxU, minV, color, light, overlay, alpha);
        add(renderer, matrices, 0, 1, 0, minU, minV, color, light, overlay, alpha);
    }

    public static void drawTexture(VertexConsumer renderer, MatrixStack matrices, Identifier sprite, int color, int light, int overlay, float alpha) {
        RenderSystem.enableDepthTest();
        RenderSystem.setShaderTexture(0, sprite);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder builder = tessellator.getBuffer();
        builder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL);
        add(builder, matrices, 0, 1, 0, 0.125F, 0.25F, color, light, overlay, alpha);
        add(builder, matrices, 1, 1, 0, 0.25F, 0.25F, color, light, overlay, alpha);
        add(builder, matrices, 1, 1, 1, 0.25F, 0.5F, color, light, overlay, alpha);
        add(builder, matrices, 0, 1, 1, 0.125F, 0.5F, color, light, overlay, alpha);

        add(builder, matrices, 0, 1, 1, 0.125F, 0.5F, color, light, overlay, alpha);
        add(builder, matrices, 1, 1, 1, 0.25F, 0.5F, color, light, overlay, alpha);
        add(builder, matrices, 1, 1, 0, 0.25F, 0.25F, color, light, overlay, alpha);
        add(builder, matrices, 0, 1, 0, 0.125F, 0.25F, color, light, overlay, alpha);
        tessellator.draw();
    }

    public static void renderLayer(Identifier base, Matrix4f matrix4f, VertexConsumerProvider vertexConsumers, float sizeX, float sizeY, int light, int overlay, float[] rgba) {
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
