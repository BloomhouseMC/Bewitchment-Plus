package dev.mrsterner.bewitchmentplus.mixin;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexFormat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(RenderLayer.class)
public interface RenderLayerAccessor {
    @Invoker("of")
    static RenderLayer.MultiPhase of(@SuppressWarnings("unused") String name, @SuppressWarnings("unused") VertexFormat vertexFormat, @SuppressWarnings("unused") VertexFormat.DrawMode drawMode, @SuppressWarnings("unused") int expectedBufferSize, @SuppressWarnings("unused") boolean hasCrumbling, @SuppressWarnings("unused") boolean translucent, @SuppressWarnings("unused") RenderLayer.MultiPhaseParameters phases) {
        throw new IllegalStateException("Mixin not transformed");
    }
}