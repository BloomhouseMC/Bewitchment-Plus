package dev.mrsterner.bewitchmentplus.client.renderlayer;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.client.shader.BWPShader;
import dev.mrsterner.bewitchmentplus.mixin.client.RenderLayerAccessor;
import net.minecraft.client.render.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.Matrix4f;

import java.util.function.Function;

public class BWPRenderLayers extends RenderLayer {
    public BWPRenderLayers(String name, VertexFormat vertexFormat, VertexFormat.DrawMode drawMode, int expectedBufferSize, boolean hasCrumbling, boolean translucent, Runnable startAction, Runnable endAction) {
        super(name, vertexFormat, drawMode, expectedBufferSize, hasCrumbling, translucent, startAction, endAction);
    }

    private static RenderLayer makeLayer(String name, VertexFormat format, VertexFormat.DrawMode mode, int bufSize, boolean hasCrumbling, boolean sortOnUpload, RenderLayer.MultiPhaseParameters glState) {
        return RenderLayerAccessor.of(name, format, mode, bufSize, hasCrumbling, sortOnUpload, glState);
    }

    public static void renderLayer(Identifier base, Matrix4f matrix4f, VertexConsumerProvider vertexConsumers, float sizeX, float sizeY, int light, int overlay, float[] rgba) {
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RUNE_LAYER.apply(base));

        vertexConsumer.vertex(matrix4f, 0, 0, sizeY).color(rgba[0], rgba[1], rgba[2], rgba[3])
                .texture(0, 1).light(light).overlay(overlay).normal(0, 1, 0).next();
        vertexConsumer.vertex(matrix4f, sizeX, 0, sizeY).color(rgba[0], rgba[1], rgba[2], rgba[3])
                .texture(1, 1).light(light).overlay(overlay).normal(0, 1, 0).next();
        vertexConsumer.vertex(matrix4f, sizeX, 0, 0).color(rgba[0], rgba[1], rgba[2], rgba[3])
                .texture(1, 0).light(light).overlay(overlay).normal(0, 1, 0).next();
        vertexConsumer.vertex(matrix4f, 0, 0, 0).color(rgba[0], rgba[1], rgba[2], rgba[3])
                .texture(0, 0).light(light).overlay(overlay).normal(0, 1, 0).next();
    }

    public static final Function<Identifier, RenderLayer> RUNE_LAYER = Util.memoize(texture -> {
        RenderLayer.MultiPhaseParameters glState = RenderLayer.MultiPhaseParameters.builder()
                .shader(new RenderPhase.Shader(BWPShader::rune))
                .texture(new RenderPhase.Texture(texture, false, false))
                .transparency(RenderLayer.TRANSLUCENT_TRANSPARENCY)
                .cull(DISABLE_CULLING)
                .lightmap(ENABLE_LIGHTMAP)
                .build(true);
        return makeLayer(BewitchmentPlus.MODID + "bw_rune", VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL, VertexFormat.DrawMode.QUADS, 256, true, true, glState);
    });

    public static final Function<Identifier, RenderLayer> SHADOW = Util.memoize(texture -> {
        RenderLayer.MultiPhaseParameters glState = RenderLayer.MultiPhaseParameters.builder()
                .shader(new RenderPhase.Shader(BWPShader::shadow))
                .texture(new RenderPhase.Texture(texture, true, false))
                .transparency(TRANSLUCENT_TRANSPARENCY)
                .cull(DISABLE_CULLING)
                .lightmap(RenderLayer.ENABLE_LIGHTMAP)
                .overlay(RenderLayer.ENABLE_OVERLAY_COLOR)
                .build(true);
        return makeLayer(BewitchmentPlus.MODID + "bw_shadow", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, VertexFormat.DrawMode.QUADS, 256, true, true, glState);
    });
}
