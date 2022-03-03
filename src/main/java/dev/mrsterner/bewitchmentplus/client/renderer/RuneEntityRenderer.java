package dev.mrsterner.bewitchmentplus.client.renderer;

import dev.mrsterner.bewitchmentplus.client.BewitchmentPlusClient;
import dev.mrsterner.bewitchmentplus.common.entity.RuneEntity;
import dev.mrsterner.bewitchmentplus.common.utils.RenderHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;
import software.bernie.geckolib3.core.util.Color;


public class RuneEntityRenderer extends EntityRenderer<RuneEntity> {
    private static final Sprite SPRITE = ((SpriteAtlasTexture) MinecraftClient.getInstance().getTextureManager().getTexture(new Identifier("minecraft", "textures/atlas/blocks.png"))).getSprite(new Identifier("block/water_still"));
    public RuneEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(RuneEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider provider, int light) {
        matrices.push();
        matrices.translate(0.5, 1.5, 0.5);
        if (true) {
            matrices.push();
            float v = 1F / 8F;
            int runes = 8;
            if (true) {
                if (runes > 0) {
                    double ticks = (BewitchmentPlusClient.ClientTickHandler.ticksInGame + tickDelta) * 0.5;
                    final float modifier = 6F;
                    final float rotationModifier = 0.25F;
                    final float radiusBase = (float) Math.exp(Math.sin(ticks/50)) * 50;
                    final float radiusMod = 0.1F;
                    float offsetPerRune = 360.0F / runes;
                    matrices.push();
                    matrices.translate(-0.05F, -0.5F, 0F);
                    matrices.scale(v, v, v);
                    for (int i = 0; i < runes; i++) {
                        float offset = offsetPerRune * i;
                        float deg = (int) (ticks / rotationModifier % 360F + offset);
                        float rad = deg * (float) Math.PI / 180F;
                        float radiusX = (float) (radiusBase + radiusMod * Math.sin(ticks / modifier));
                        float radiusZ = (float) (radiusBase + radiusMod * Math.cos(ticks / modifier));
                        float x = (float) (radiusX * Math.cos(rad));
                        float z = (float) (radiusZ * Math.sin(rad));
                        matrices.push();
                        matrices.translate(x, 0, z);
                        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-deg + 110));
                        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90));
                        matrices.scale(20,20,20);
                        float minU = SPRITE.getMinU();
                        float maxU = SPRITE.getMaxU();
                        float minV = SPRITE.getMinV();
                        float maxV = SPRITE.getMaxV();
                        RenderHelper.drawTexture(provider.getBuffer(RenderLayer.getSolid()), matrices, SPRITE, minU, minV, maxU, maxV, Color.ofRGB(1.0F,1.0F,1.0F).getColor(), light, OverlayTexture.DEFAULT_UV);
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
}
