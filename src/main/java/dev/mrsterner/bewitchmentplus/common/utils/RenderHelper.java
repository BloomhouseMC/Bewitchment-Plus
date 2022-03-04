package dev.mrsterner.bewitchmentplus.common.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.*;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class RenderHelper {
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
}
