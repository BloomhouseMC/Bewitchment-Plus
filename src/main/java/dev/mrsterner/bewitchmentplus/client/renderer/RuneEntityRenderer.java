package dev.mrsterner.bewitchmentplus.client.renderer;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.client.BewitchmentPlusClient;
import dev.mrsterner.bewitchmentplus.client.shader.BWPShader;
import dev.mrsterner.bewitchmentplus.common.entity.RuneEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3f;
import org.checkerframework.checker.nullness.qual.NonNull;

import static dev.mrsterner.bewitchmentplus.common.utils.RenderHelper.renderLayer;

public class RuneEntityRenderer extends EntityRenderer<RuneEntity> {
    private final EntityRenderDispatcher dispatcher;
    public RuneEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.dispatcher = ctx.getRenderDispatcher();
    }

    /** This method is calling the method which renders the circle of runes. Additionally, provides the shader with
     * client tick for showing the instability property of the runes.
     * {@link Shader#getUniformOrDefault(java.lang.String)}
     *
     * @param entity the entity in charge of the rendering
     * @param yaw unused
     * @param tickDelta used to smoothen animations
     * @param matrices
     * @param provider
     * @param light
     */
    @Override
    public void render(@NonNull RuneEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider provider, int light) {
        if(entity.getExpansionTick() < 200){
            Shader shader = BWPShader.rune();
            double ticks = (BewitchmentPlusClient.ClientTickHandler.ticksInGame + tickDelta) * 0.5;
            double cycle = ticks/10 % 30;
            if (shader != null) {
                shader.getUniformOrDefault("Disfiguration").set((float) ((0.025F + cycle * ((1F - 0.15F) / 20F)) / 2F));
            }
            renderRing(true,10, ticks, entity, matrices, provider, light);
        }
    }

    /** this dynamically offsets the runes depending on amount of runes. Uses a special renderlayer to apply shader and texture.
     * selects one of each available rune textures with {@link RuneEntityRenderer#getTexture(int)} and renders over and over
     * again in a circle
     *
     * @param clockwise determines the direction of the individual runes y-axis spin to compensate for the circular movement
     * @param salt it only needs to be different each different call to make the rings nor sync
     * @param ticks client tick to progress animation
     * @param entity
     * @param matrices
     * @param provider
     * @param light
     */
    public void renderRing(boolean clockwise,int salt, double ticks, RuneEntity entity, MatrixStack matrices, VertexConsumerProvider provider, int light){
        matrices.push();
        matrices.translate(0.5, 1.5, 0.5);
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion((float) Math.sin(ticks/100) * salt));
        matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion((float) Math.cos(ticks/100) * salt));
        if (true) {
            float v = 1F / 8F;
            final float modifier = 6F;
            final float rotationModifier = 0.25F;
            final float radiusBase = 200.0F;
            final float radiusMod = 0.1F;
            int runes = (int) Math.floor(radiusBase/2);
            float offsetPerRune = 360.0F / runes;
            matrices.push();
            matrices.translate(-0.05F, -0.5F, 0F);
            matrices.scale(v, v, v);
            for (int i = 0; i < runes; i++) {
                float offset = offsetPerRune * i;
                float deg = (int) (ticks / rotationModifier % 360F + offset);
                float rad = deg * (float) Math.PI / 180F;
                float radiusX = (float) (radiusBase + (entity.getExpansion() ? entity.getExpansionTick()*10 : 0) + radiusMod * Math.sin(ticks / modifier));
                float radiusZ = (float) (radiusBase + (entity.getExpansion() ? entity.getExpansionTick()*10 : 0) + radiusMod * Math.cos(ticks / modifier));
                float x = (float) (radiusX * Math.cos(rad));
                float z = (float) (radiusZ * Math.sin(rad));
                matrices.push();
                matrices.translate((clockwise ? x : -x), 0, (clockwise ? z : -z));
                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((clockwise ? -deg : deg) + 100));
                matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90));
                matrices.scale(!entity.getExpansion() ? 20 : 20 + (float)entity.getExpansionTick(), !entity.getExpansion() ? 20 : 20 + (float)entity.getExpansionTick(), !entity.getExpansion() ? 20 : 20 + (float)entity.getExpansionTick());
                Matrix4f matrix4f = matrices.peek().getPositionMatrix();
                renderLayer(getTexture(i % 5 + 1), matrix4f, provider, 1,1,light, OverlayTexture.DEFAULT_UV, new float[]{1F, 1F, 1F, 1F});
                if(radiusBase % 80 >= 50){
                    //entity.world.addParticle(ParticleTypes.DRIPPING_HONEY, entity.getX()+(x/10),entity.getY(),entity.getZ()+(z/10),0,0,0);
                }
                matrices.pop();
            }
            matrices.pop();
        }
        matrices.pop();
    }

    /**
     *
     * @param j corresponds to a texture identifier
     * @return the texture selected with j
     */
    public Identifier getTexture(int j) {
        return new Identifier(BewitchmentPlus.MODID, "textures/block/rune_" + j + ".png");
    }

    /**
     * Makes the renderer render regardless of if the entity is on screen. we dont care about the camera anf frustum
     * since we always want to render, we always return true
     * @param entity
     * @param frustum
     * @param x
     * @param y
     * @param z
     * @return
     */
    @Override
    public boolean shouldRender(RuneEntity entity, Frustum frustum, double x, double y, double z) {
        return true;
    }

    @Override
    public Identifier getTexture(RuneEntity entity) {
        return null;
    }
 }
