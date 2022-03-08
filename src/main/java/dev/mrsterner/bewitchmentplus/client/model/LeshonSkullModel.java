package dev.mrsterner.bewitchmentplus.client.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class LeshonSkullModel<T extends Entity> extends EntityModel<T> {

    private final ModelPart headrot;

    public LeshonSkullModel(ModelPart root) {
        this.headrot = root.getChild("headrot");
    }


    public static TexturedModelData getTexturedModelData() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData headrot = root.addChild("headrot", ModelPartBuilder.create(), ModelTransform.of(0.0F, 20.0F, 7.0F, 1.5708F, 0.0F, 0.0F));
        ModelPartData head = headrot.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-3.5F, -7.0F, -3.0F, 7.0F, 7.0F, 7.0F, new Dilation(0.0F))
        .uv(0, 14).cuboid(-2.0F, -14.0F, -2.0F, 4.0F, 7.0F, 5.0F, new Dilation(0.0F))
        .uv(16, 27).cuboid(-2.0F, -12.0F, -3.0F, 4.0F, 6.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData cube_r1 = head.addChild("cube_r1", ModelPartBuilder.create().uv(0, 26).cuboid(-0.5F, -34.5F, 6.5F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-1.5F, 18.0F, -3.0F, 0.1745F, 0.0F, 0.0F));
        ModelPartData leftEar = head.addChild("leftEar", ModelPartBuilder.create(), ModelTransform.of(3.5F, -5.1F, 0.0F, -0.2618F, 0.1309F, 0.0F));
        ModelPartData bone21 = leftEar.addChild("bone21", ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, -0.5F, 3.0F));
        ModelPartData rightEar = head.addChild("rightEar", ModelPartBuilder.create(), ModelTransform.of(-3.5F, -5.1F, 0.0F, -0.2618F, -0.1309F, 0.0F));
        ModelPartData bone3 = rightEar.addChild("bone3", ModelPartBuilder.create(), ModelTransform.pivot(1.0F, -0.5F, 3.0F));
        ModelPartData jawUnder = head.addChild("jawUnder", ModelPartBuilder.create(), ModelTransform.of(0.0F, -3.0F, -3.0F, -1.2654F, 0.0F, 0.0F));
        ModelPartData cube_r2 = jawUnder.addChild("cube_r2", ModelPartBuilder.create().uv(18, 14).cuboid(-0.5F, -15.0F, 19.5F, 4.0F, 11.0F, 2.0F, new Dilation(-0.01F))
        .uv(21, 0).cuboid(-0.5F, -13.0F, 21.5F, 4.0F, 6.0F, 1.0F, new Dilation(-0.01F)), ModelTransform.of(-1.5F, 21.0F, 0.0F, 1.309F, 0.0F, 0.0F));
        ModelPartData lAntler01 = head.addChild("lAntler01", ModelPartBuilder.create().uv(32, 28).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(1.9F, -1.8F, 3.5F, -1.6111F, 0.5232F, -0.3692F));
        ModelPartData lAntler02 = lAntler01.addChild("lAntler02", ModelPartBuilder.create(), ModelTransform.of(0.0F, -1.6F, 0.0F, -0.2094F, 0.0F, 0.6109F));
        ModelPartData Box_r1 = lAntler02.addChild("Box_r1", ModelPartBuilder.create().uv(0, 34).cuboid(-0.6F, -2.9F, -1.0F, 1.0F, 4.0F, 1.0F, new Dilation(0.2F)), ModelTransform.of(0.1F, -0.6F, 0.5F, -0.0873F, 0.0F, -0.0436F));
        ModelPartData rightAntler02 = lAntler02.addChild("rightAntler02", ModelPartBuilder.create().uv(34, 0).cuboid(-0.5F, -4.0F, -0.5F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -3.1F, 0.0F, -0.6981F, 0.0F, -0.3142F));
        ModelPartData lAntler03a = rightAntler02.addChild("lAntler03a", ModelPartBuilder.create().uv(12, 34).cuboid(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -3.7F, 0.0F, -0.4538F, 0.0F, -0.0524F));
        ModelPartData lAntler03b = lAntler03a.addChild("lAntler03b", ModelPartBuilder.create().uv(4, 34).cuboid(-0.5F, -3.9F, -0.5F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.8F, 0.0F, -0.1047F, 0.0F, -0.2793F));
        ModelPartData lAntler03c = lAntler03b.addChild("lAntler03c", ModelPartBuilder.create().uv(16, 34).cuboid(-0.5F, -3.0F, -0.4F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -3.7F, 0.0F, 0.0F, 0.0F, -0.4363F));
        ModelPartData lAntler04a = rightAntler02.addChild("lAntler04a", ModelPartBuilder.create().uv(34, 8).cuboid(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-0.2F, -5.9F, 1.0F, 0.4538F, 0.0F, -0.8029F));
        ModelPartData lAntler04b = lAntler04a.addChild("lAntler04b", ModelPartBuilder.create().uv(20, 36).cuboid(-0.5F, -2.0F, -0.4F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.7F, 0.0F, 0.0F, 0.0F, -0.3142F));
        ModelPartData lAntler05a = rightAntler02.addChild("lAntler05a", ModelPartBuilder.create().uv(8, 34).cuboid(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-0.3F, -6.7F, 1.4F, -0.0349F, 0.0F, -0.5236F));
        ModelPartData lAntler05b = lAntler05a.addChild("lAntler05b", ModelPartBuilder.create().uv(35, 17).cuboid(-0.5F, -2.0F, -0.4F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.7F, 0.0F, 0.0F, 0.0F, -0.5236F));
        ModelPartData lAntler06a = lAntler02.addChild("lAntler06a", ModelPartBuilder.create().uv(26, 28).cuboid(-0.5F, -0.5F, -3.8F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.5F, -0.4F, -1.3963F, 0.6981F, -0.4363F));
        ModelPartData lAntler06b = lAntler06a.addChild("lAntler06b", ModelPartBuilder.create().uv(31, 33).cuboid(-0.5F, -0.6F, -3.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.1F, 0.0F, -3.4F, 0.0F, 0.576F, 0.0F));
        ModelPartData lAntler07a = lAntler01.addChild("lAntler07a", ModelPartBuilder.create().uv(28, 8).cuboid(-0.5F, -0.5F, -3.8F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.0F, -0.4F, -0.9076F, 0.5236F, 0.0F));
        ModelPartData lAntler07b = lAntler07a.addChild("lAntler07b", ModelPartBuilder.create().uv(23, 33).cuboid(-0.5F, -0.5F, -3.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.1F, 0.0F, -3.4F, 0.0F, 0.576F, 0.0F));
        ModelPartData rAntler01 = head.addChild("rAntler01", ModelPartBuilder.create().uv(30, 13).cuboid(2.4981F, -13.9078F, 8.7963F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(4.1F, -10.55F, -8.5F, -1.6057F, -0.5236F, 0.3491F));
        ModelPartData rAntler02 = rAntler01.addChild("rAntler02", ModelPartBuilder.create(), ModelTransform.of(0.0F, -1.6F, 0.0F, -0.2094F, 0.0F, -0.6109F));
        ModelPartData Box_r2 = rAntler02.addChild("Box_r2", ModelPartBuilder.create().uv(0, 0).cuboid(8.8669F, -13.5857F, 6.0669F, 1.0F, 4.0F, 1.0F, new Dilation(0.2F)), ModelTransform.of(-0.1F, -0.6F, 0.5F, -0.0873F, 0.0F, 0.0436F));
        ModelPartData rAntler03a = rAntler02.addChild("rAntler03a", ModelPartBuilder.create().uv(0, 14).cuboid(5.7497F, -18.4243F, -2.1976F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -3.1F, 0.0F, -0.6981F, 0.0F, 0.3142F));
        ModelPartData rAntler03b = rAntler03a.addChild("rAntler03b", ModelPartBuilder.create().uv(28, 8).cuboid(4.9862F, -15.4965F, -8.4837F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -3.7F, 0.0F, -0.4538F, 0.0F, 0.0524F));
        ModelPartData rAntler03c = rAntler03b.addChild("rAntler03c", ModelPartBuilder.create().uv(13, 14).cuboid(1.3292F, -16.516F, -9.8537F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.8F, 0.0F, -0.1047F, 0.0F, 0.2793F));
        ModelPartData rAntler03d = rAntler03c.addChild("rAntler03d", ModelPartBuilder.create().uv(26, 28).cuboid(-4.174F, -15.207F, -9.7537F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -3.7F, 0.0F, 0.0F, 0.0F, 0.4363F));
        ModelPartData rAntler04a = rAntler03a.addChild("rAntler04a", ModelPartBuilder.create().uv(12, 26).cuboid(-6.5346F, -16.7907F, 4.3374F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.2F, -5.9F, 1.0F, 0.4538F, 0.0F, 0.8029F));
        ModelPartData rAntler04b = rAntler04a.addChild("rAntler04b", ModelPartBuilder.create().uv(28, 33).cuboid(-10.5008F, -13.2509F, 4.4374F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.7F, 0.0F, 0.0F, 0.0F, 0.3142F));
        ModelPartData rAntler05a = rAntler03a.addChild("rAntler05a", ModelPartBuilder.create().uv(0, 26).cuboid(-2.2998F, -18.5479F, -2.7416F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.3F, -6.7F, 1.4F, -0.0349F, 0.0F, 0.5236F));
        ModelPartData rAntler05b = rAntler05a.addChild("rAntler05b", ModelPartBuilder.create().uv(3, 4).cuboid(-9.8326F, -14.565F, -2.6416F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.7F, 0.0F, 0.0F, 0.0F, 0.5236F));
        ModelPartData rAntler06a = rAntler02.addChild("rAntler06a", ModelPartBuilder.create().uv(28, 3).cuboid(8.2423F, -5.7483F, -15.8842F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.5F, -0.4F, -1.3963F, -0.6981F, 0.4363F));
        ModelPartData rAntler06b = rAntler06a.addChild("rAntler06b", ModelPartBuilder.create().uv(32, 21).cuboid(0.2503F, -5.8483F, -17.896F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-0.1F, 0.0F, -3.4F, 0.0F, -0.576F, 0.0F));
        ModelPartData rAntler07a = rAntler01.addChild("rAntler07a", ModelPartBuilder.create().uv(26, 23).cuboid(7.4276F, -13.1383F, -9.0372F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.0F, -0.4F, -0.9076F, -0.5236F, 0.0F));
        ModelPartData rAntler07b = rAntler07a.addChild("rAntler07b", ModelPartBuilder.create().uv(30, 17).cuboid(3.2962F, -13.1383F, -11.7099F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-0.1F, 0.0F, -3.4F, 0.0F, -0.576F, 0.0F));


        return TexturedModelData.of(data, 64, 64);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        headrot.render(matrices, vertices, light, overlay);
    }
}
