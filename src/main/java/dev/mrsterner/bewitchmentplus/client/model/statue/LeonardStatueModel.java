package dev.mrsterner.bewitchmentplus.client.model.statue;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

public class LeonardStatueModel<T extends Entity> extends EntityModel<T> {
    public static final EntityModelLayer LEONARD_STATUE_MODEL_LAYER = new EntityModelLayer(new Identifier(BewitchmentPlus.MODID, "statue_leonard"), "main");
    private final ModelPart body;
    private final ModelPart plith01;
    private final ModelPart wandShaft;

    public LeonardStatueModel(ModelPart root) {
        this.body = root.getChild("body");
        this.plith01 = root.getChild("plith01");
        this.wandShaft = root.getChild("wandShaft");

    }

    public static TexturedModelData create(){
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();
        ModelPartData body = root.addChild("body", ModelPartBuilder.create().uv(20, 16).cuboid(-4.5F, 0.0F, -2.5F, 9.0F, 7.0F, 5.0F), ModelTransform.of(-2.0F, -16.1F, 1.5F, 0.0F, 0.0F, 0.0F));
        ModelPartData leftArm = body.addChild("leftArm", ModelPartBuilder.create().uv(49, 19).mirrored(true).cuboid(-2.0F, -2.0F, -2.0F, 3.0F, 8.0F, 4.0F), ModelTransform.of(-5.5F, 1.8F, 0.0F, 0.0F, 0.0F, 0.3491F));
        leftArm.addChild("lShoulderPoof", ModelPartBuilder.create().uv(46, 40).mirrored(true).cuboid(-2.0F, -2.5F, -2.5F, 4.0F, 5.0F, 5.0F), ModelTransform.of(-0.4F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0F));
        ModelPartData leftArm01 = leftArm.addChild("leftArm01", ModelPartBuilder.create().uv(49, 27).mirrored(true).cuboid(-1.5F, 0.0F, -2.0F, 3.0F, 8.0F, 4.0F), ModelTransform.of(-1.0F, 4.9F, 0.1F, 0.0F, 0.0F, -0.8727F));
        leftArm01.addChild("lClaws", ModelPartBuilder.create().uv(0, 45).mirrored(true).cuboid(-0.9F, 0.0F, -2.1F, 2.0F, 3.0F, 4.0F), ModelTransform.of(-0.8F, 7.4F, -0.1F, 0.0F, 0.0F, 0.1745F));
        leftArm01.addChild("lSleeve", ModelPartBuilder.create().uv(46, 9).mirrored(true).cuboid(-1.48F, -2.5F, -2.0F, 3.0F, 5.0F, 5.0F), ModelTransform.of(0.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F));
        ModelPartData rightArm = body.addChild("rightArm", ModelPartBuilder.create().uv(49, 19).cuboid(-1.0F, -2.0F, -2.0F, 3.0F, 16.0F, 4.0F), ModelTransform.of(5.5F, 1.8F, 0.0F, -0.576F, -1.0472F, -0.5236F));
        rightArm.addChild("rClaws", ModelPartBuilder.create().uv(0, 45).cuboid(-0.9F, 0.0F, -2.1F, 2.0F, 3.0F, 4.0F), ModelTransform.of(1.4F, 12.9F, -0.1F, 0.0F, 0.0F, 0.2793F));
        rightArm.addChild("rShoulderPoof", ModelPartBuilder.create().uv(46, 40).cuboid(-2.0F, -2.5F, -2.5F, 4.0F, 5.0F, 5.0F), ModelTransform.of(0.4F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0F));
        rightArm.addChild("rSleeve", ModelPartBuilder.create().uv(46, 9).cuboid(-1.52F, -2.5F, -2.0F, 3.0F, 5.0F, 5.0F), ModelTransform.of(0.5F, 9.5F, 2.0F, 0.0F, 0.0F, 0.0F));
        body.addChild("leftPec", ModelPartBuilder.create().uv(14, 42).cuboid(-2.5F, -2.5F, -3.0F, 5.0F, 5.0F, 3.0F), ModelTransform.of(-2.2F, 3.1F, -0.1F, 0.0F, 0.0873F, -0.0873F));
        body.addChild("rightPec", ModelPartBuilder.create().uv(14, 42).cuboid(-2.5F, -2.5F, -3.0F, 5.0F, 5.0F, 3.0F), ModelTransform.of(2.2F, 3.1F, -0.1F, 0.0F, -0.0873F, 0.0873F));
        ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(1, 2).cuboid(-3.5F, -7.0F, -3.5F, 7.0F, 7.0F, 7.0F), ModelTransform.of(0.0F, 0.0F, -0.2F, 0.0F, 0.0F, 0.0F));
        head.addChild("snout", ModelPartBuilder.create().uv(29, 2).cuboid(-2.0F, -1.9F, -5.1F, 4.0F, 3.0F, 5.0F), ModelTransform.of(0.0F, -4.6F, -2.5F, 0.5236F, 0.0F, 0.0F));
        head.addChild("jawUpper00", ModelPartBuilder.create().uv(43, 0).cuboid(-2.5F, -1.0F, -5.0F, 5.0F, 2.0F, 5.0F), ModelTransform.of(0.0F, -2.5F, -2.2F, 0.0698F, 0.0F, 0.0F));
        ModelPartData jawLower = head.addChild("jawLower", ModelPartBuilder.create().uv(30, 10).cuboid(-2.0F, -0.5F, -4.0F, 4.0F, 1.0F, 4.0F), ModelTransform.of(0.0F, -1.0F, -3.0F, -0.0349F, 0.0F, 0.0F));
        jawLower.addChild("beard", ModelPartBuilder.create().uv(0, 53).cuboid(-1.5F, 0.0F, -1.0F, 3.0F, 4.0F, 4.0F), ModelTransform.of(0.0F, 0.3F, -2.4F, -0.0349F, 0.0F, 0.0F));
        head.addChild("leftEar", ModelPartBuilder.create().uv(37, 29).mirrored(true).cuboid(-4.0F, -0.5F, -1.0F, 4.0F, 1.0F, 2.0F), ModelTransform.of(-2.6F, -5.0F, 0.8F, -0.5236F, 0.0F, 0.5236F));
        head.addChild("rightEar", ModelPartBuilder.create().uv(37, 29).cuboid(0.0F, -0.5F, -1.0F, 4.0F, 1.0F, 2.0F), ModelTransform.of(2.6F, -5.0F, 0.8F, -0.5236F, 0.0F, -0.5236F));
        ModelPartData lHorn01 = head.addChild("lHorn01", ModelPartBuilder.create().uv(25, 0).mirrored(true).cuboid(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F), ModelTransform.of(-2.0F, -6.0F, -0.2F, -0.6109F, 0.0F, -0.5236F));
        ModelPartData lHorn02 = lHorn01.addChild("lHorn02", ModelPartBuilder.create().uv(25, 1).mirrored(true).cuboid(-1.0F, -2.9F, -1.0F, 2.0F, 3.0F, 2.0F), ModelTransform.of(0.0F, -2.7F, 0.0F, -0.3142F, 0.0F, -0.2094F));
        ModelPartData lHorn03a = lHorn02.addChild("lHorn03a", ModelPartBuilder.create().uv(0, 1).mirrored(true).cuboid(-0.2F, -2.8F, -0.8F, 1.0F, 3.0F, 1.0F), ModelTransform.of(0.0F, -2.6F, 0.0F, -0.3142F, 0.0F, 0.0F));
        lHorn03a.addChild("lHorn03b", ModelPartBuilder.create().uv(0, 1).mirrored(true).cuboid(-0.8F, -2.8F, -0.8F, 1.0F, 3.0F, 1.0F), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        lHorn03a.addChild("lHorn03c", ModelPartBuilder.create().uv(0, 1).mirrored(true).cuboid(-0.2F, -2.8F, -0.2F, 1.0F, 3.0F, 1.0F), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        lHorn03a.addChild("lHorn03d", ModelPartBuilder.create().uv(0, 1).mirrored(true).cuboid(-0.8F, -2.8F, -0.2F, 1.0F, 3.0F, 1.0F), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        lHorn03a.addChild("lHorn04", ModelPartBuilder.create().mirrored(true).cuboid(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F), ModelTransform.of(0.0F, -2.7F, 0.0F, 0.4014F, 0.0F, 0.0F));
        ModelPartData rHorn01 = head.addChild("rHorn01", ModelPartBuilder.create().uv(25, 0).cuboid(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F), ModelTransform.of(2.0F, -6.0F, -0.2F, -0.6109F, 0.0F, 0.5236F));
        ModelPartData rHorn02 = rHorn01.addChild("rHorn02", ModelPartBuilder.create().uv(25, 1).cuboid(-1.0F, -2.9F, -1.0F, 2.0F, 3.0F, 2.0F), ModelTransform.of(0.0F, -2.7F, 0.0F, -0.3142F, 0.0F, 0.2094F));
        ModelPartData rHorn03a = rHorn02.addChild("rHorn03a", ModelPartBuilder.create().uv(0, 1).cuboid(-0.2F, -2.8F, -0.8F, 1.0F, 3.0F, 1.0F), ModelTransform.of(0.0F, -2.6F, 0.0F, -0.3142F, 0.0F, 0.0F));
        rHorn03a.addChild("rHorn03b", ModelPartBuilder.create().uv(0, 1).cuboid(-0.8F, -2.8F, -0.8F, 1.0F, 3.0F, 1.0F), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        rHorn03a.addChild("rHorn03c", ModelPartBuilder.create().uv(0, 1).cuboid(-0.2F, -2.8F, -0.2F, 1.0F, 3.0F, 1.0F), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        rHorn03a.addChild("rHorn03d", ModelPartBuilder.create().uv(0, 1).cuboid(-0.8F, -2.8F, -0.2F, 1.0F, 3.0F, 1.0F), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        rHorn03a.addChild("rHorn04", ModelPartBuilder.create().cuboid(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F), ModelTransform.of(0.0F, -2.7F, 0.0F, 0.4014F, 0.0F, 0.0F));
        ModelPartData mHorn01 = body.addChild("mHorn01", ModelPartBuilder.create().uv(25, 1).cuboid(-1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F), ModelTransform.of(0.0F, -6.0F, 0.4F, -0.8727F, 0.0F, 0.0F));
        ModelPartData mHorn02 = mHorn01.addChild("mHorn02", ModelPartBuilder.create().uv(25, 0).cuboid(-1.0F, -2.9F, -1.0F, 2.0F, 3.0F, 2.0F), ModelTransform.of(0.0F, -3.7F, 0.0F, -0.4538F, 0.0F, 0.0F));
        ModelPartData mHorn03a = mHorn02.addChild("mHorn03a", ModelPartBuilder.create().uv(0, 1).cuboid(-0.2F, -2.8F, -0.8F, 1.0F, 4.0F, 1.0F), ModelTransform.of(0.0F, -3.8F, 0.0F, -0.3142F, 0.0F, 0.0F));
        mHorn03a.addChild("mHorn03b", ModelPartBuilder.create().uv(0, 1).cuboid(-0.8F, -2.8F, -0.8F, 1.0F, 4.0F, 1.0F), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        mHorn03a.addChild("mHorn03c", ModelPartBuilder.create().uv(0, 1).cuboid(-0.2F, -2.8F, -0.2F, 1.0F, 4.0F, 1.0F), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        mHorn03a.addChild("mHorn03d", ModelPartBuilder.create().uv(0, 1).cuboid(-0.8F, -2.8F, -0.2F, 1.0F, 4.0F, 1.0F), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        mHorn03a.addChild("mHorn04", ModelPartBuilder.create().cuboid(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F), ModelTransform.of(0.0F, -2.7F, 0.0F, 0.2618F, 0.0F, 0.0F));
        ModelPartData stomach = body.addChild("stomach", ModelPartBuilder.create().uv(17, 29).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 8.0F, 4.0F), ModelTransform.of(0.0F, 6.7F, 0.0F, 0.0F, 0.0F, 0.0F));
        ModelPartData bipedLeftLeg = stomach.addChild("bipedLeftLeg", ModelPartBuilder.create().uv(0, 16).mirrored(true).cuboid(-2.1F, 0.0F, -2.5F, 4.0F, 10.0F, 5.0F), ModelTransform.of(-2.1F, 7.2F, 0.1F, -0.0698F, 0.0F, 0.0349F));
        ModelPartData leftLeg01 = bipedLeftLeg.addChild("leftLeg01", ModelPartBuilder.create().uv(0, 31).mirrored(true).cuboid(-2.0F, -0.1F, -2.0F, 4.0F, 10.0F, 4.0F), ModelTransform.of(0.0F, 9.7F, 0.0F, 0.0698F, 0.0F, -0.0349F));
        leftLeg01.addChild("lFlipper01", ModelPartBuilder.create().uv(30, 42).mirrored(true).cuboid(-2.0F, 0.0F, -3.0F, 3.0F, 1.0F, 5.0F), ModelTransform.of(-0.3F, 8.8F, -1.7F, 0.0F, 0.1745F, -0.0349F));
        leftLeg01.addChild("lFlipper02", ModelPartBuilder.create().uv(28, 48).mirrored(true).cuboid(-0.9F, 0.0F, -3.0F, 2.0F, 1.0F, 5.0F), ModelTransform.of(0.9F, 8.8F, -1.7F, 0.0F, -0.1396F, 0.0349F));
        ModelPartData rightLeg00 = stomach.addChild("rightLeg00", ModelPartBuilder.create().uv(0, 16).cuboid(-1.9F, 0.0F, -2.5F, 4.0F, 10.0F, 5.0F).uv(0, 31).cuboid(-2.0F, -0.1F, -2.0F, 4.0F, 10.0F, 4.0F), ModelTransform.of(0.0F, 9.7F, 0.0F, 0.3491F, -0.2793F, -0.1396F));
        rightLeg00.addChild("rFlipper01", ModelPartBuilder.create().uv(30, 42).cuboid(-1.0F, 0.0F, -3.0F, 3.0F, 1.0F, 5.0F), ModelTransform.of(0.3F, 8.8F, -1.7F, 0.2269F, -0.1745F, 0.0349F));
        rightLeg00.addChild("rFlipper02", ModelPartBuilder.create().uv(28, 48).cuboid(-1.1F, 0.0F, -3.0F, 2.0F, 1.0F, 5.0F), ModelTransform.of(-0.9F, 8.8F, -1.7F, 0.2269F, 0.1396F, -0.0349F));
        stomach.addChild("frontLoincloth00", ModelPartBuilder.create().uv(16, 54).cuboid(-4.5F, 0.0F, -0.5F, 9.0F, 8.0F, 2.0F), ModelTransform.of(0.0F, 6.5F, -2.0F, -0.4189F, 0.0F, 0.0F));
        stomach.addChild("backLoincloth00", ModelPartBuilder.create().uv(39, 51).cuboid(-4.5F, 0.0F, -1.5F, 9.0F, 8.0F, 3.0F), ModelTransform.of(0.0F, 6.5F, 1.1F, 0.0F, 0.0F, 0.0F));
        ModelPartData wandShaft = root.addChild("wandShaft", ModelPartBuilder.create().uv(68, 44).cuboid(-1.5F, 0.0F, -1.0F, 3.0F, 17.0F, 2.0F), ModelTransform.of(17.6F, -14.2F, -6.5F, 0.576F, 0.0F, 0.3491F));
        wandShaft.addChild("spikesBase", ModelPartBuilder.create().uv(70, 45).cuboid(-1.0F, -2.0F, -0.5F, 2.0F, 2.0F, 1.0F), ModelTransform.of(0.0F, 0.4F, 0.0F, 0.0F, 0.0F, 0.0F));
        ModelPartData bottomSpikeBase = wandShaft.addChild("bottomSpikeBase", ModelPartBuilder.create().uv(70, 45).cuboid(-1.0F, 0.0F, -0.5F, 2.0F, 3.0F, 1.0F), ModelTransform.of(0.0F, 17.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        bottomSpikeBase.addChild("bottomSpikeTip", ModelPartBuilder.create().uv(71, 45).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F), ModelTransform.of(0.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        ModelPartData spikesPlate = wandShaft.addChild("spikesPlate", ModelPartBuilder.create().uv(78, 52).cuboid(-2.0F, -1.0F, -1.0F, 4.0F, 1.0F, 2.0F), ModelTransform.of(0.0F, -1.2F, 0.0F, 0.0F, 0.0F, 0.0F));
        spikesPlate.addChild("rightSpike", ModelPartBuilder.create().uv(80, 46).cuboid(-0.5F, -4.0F, -0.5F, 1.0F, 4.0F, 1.0F), ModelTransform.of(1.3F, -1.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        spikesPlate.addChild("middleSpike", ModelPartBuilder.create().uv(80, 45).cuboid(-0.5F, -5.0F, -0.5F, 1.0F, 5.0F, 1.0F), ModelTransform.of(0.0F, -1.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        spikesPlate.addChild("leftSpike", ModelPartBuilder.create().uv(80, 46).cuboid(-0.5F, -4.0F, -0.5F, 1.0F, 4.0F, 1.0F), ModelTransform.of(-1.3F, -1.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        spikesPlate.addChild("spikesPlateLeftMain", ModelPartBuilder.create().uv(81, 52).cuboid(-1.0F, -1.0F, -1.0F, 1.0F, 1.0F, 2.0F), ModelTransform.of(-1.8F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3927F));
        spikesPlate.addChild("spikesPlateRightMain", ModelPartBuilder.create().uv(81, 52).cuboid(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 2.0F), ModelTransform.of(1.8F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3927F));
        spikesPlate.addChild("spikesPlateLeftLower", ModelPartBuilder.create().uv(83, 52).cuboid(-1.0F, -0.2F, -0.5F, 1.0F, 2.0F, 1.0F), ModelTransform.of(-1.7F, -0.5F, 0.0F, 0.0F, 0.0F, 0.7854F));
        spikesPlate.addChild("spikesPlateRightLower", ModelPartBuilder.create().uv(83, 52).cuboid(0.0F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F), ModelTransform.of(1.6F, -0.5F, 0.0F, 0.0F, 0.0F, -0.7854F));
        ModelPartData plith01 = root.addChild("plith01", ModelPartBuilder.create().uv(60, 0).cuboid(-8.5F, 0.0F, -8.5F, 17.0F, 5.0F, 17.0F), ModelTransform.of(0.0F, 19.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        plith01.addChild("plith02", ModelPartBuilder.create().uv(68, 25).cuboid(-7.5F, -2.7F, -7.5F, 15.0F, 3.0F, 15.0F), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(data, 128,64);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        body.render(matrices, vertices, light, overlay);
        plith01.render(matrices, vertices, light, overlay);
        wandShaft.render(matrices, vertices, light, overlay);
    }
}
