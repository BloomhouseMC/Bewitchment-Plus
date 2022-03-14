package dev.mrsterner.bewitchmentplus.client.model.statue;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

public class BaphometStatueModel<T extends Entity> extends EntityModel<T> {
    public static final EntityModelLayer BAPHOMET_STATUE_MODEL_LAYER = new EntityModelLayer(new Identifier(BewitchmentPlus.MODID, "statue_leonard"), "main");
    private final ModelPart plith01;
    private final ModelPart bipedBody;

    public BaphometStatueModel(ModelPart root) {
        this.plith01 = root.getChild("plith01");
        this.bipedBody = root.getChild("bipedBody");
    }

    public static TexturedModelData create(){
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();
        ModelPartData plith01 = root.addChild("plith01",
        ModelPartBuilder.create().uv(60, 84).cuboid(-8.5F, 0.0F, -8.5F, 17.0F, 5.0F, 17.0F), ModelTransform.of(0.0F, 19.0F, 0.0F, 0.0F, 0.0F, 0.0F));plith01.addChild("plith02",
        ModelPartBuilder.create().uv(68, 64).cuboid(-7.5F, -2.7F, -7.5F, 15.0F, 3.0F, 15.0F), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        ModelPartData bipedBody = root.addChild("bipedBody", ModelPartBuilder.create().uv(19, 19).cuboid(-4.0F, 0.0F, -2.5F, 8.0F, 6.0F, 5.0F), ModelTransform.of(0.0F, 2.6F, 1.5F, 0.0F, 0.0F, 0.0F));
        ModelPartData stomach = bipedBody.addChild("stomach", ModelPartBuilder.create().uv(19, 31).cuboid(-3.5F, 0.0F, -2.0F, 7.0F, 7.0F, 4.0F).uv(20, 77).cuboid(-4.5F, 2.05F, -3.5F, 9.0F, 3.0F, 2.0F).uv(21, 68).cuboid(-4.0F, 0.05F, -3.5F, 8.0F, 2.0F, 2.0F), ModelTransform.of(0.0F, 5.6F, 0.0F, 0.0F, 0.0F, 0.0F));
        ModelPartData hips = stomach.addChild("hips", ModelPartBuilder.create().uv(16, 43).cuboid(-4.0F, 0.0F, -2.3F, 8.0F, 3.0F, 5.0F), ModelTransform.of(0.0F, 5.4F, 0.0F, 0.0F, 0.0F, 0.0F));
        ModelPartData loinclothF = hips.addChild("loinclothF", ModelPartBuilder.create().uv(65, 0).cuboid(-5.0F, 0.0F, -0.5F, 10.0F, 9.0F, 3.0F), ModelTransform.of(0.0F, -0.8F, -1.8F, -1.4835F, 0.0F, 0.0F));
        loinclothF.addChild("loinclothF02", ModelPartBuilder.create().uv(65, 13).cuboid(-4.5F, -0.25F, -0.33F, 9.0F, 4.0F, 1.0F), ModelTransform.of(0.0F, 8.8F, 0.0F, 0.9105F, 0.0F, 0.0F));
        bipedBody.addChild("boobs", ModelPartBuilder.create().uv(57, 42).cuboid(-3.5F, 0.0F, -2.0F, 7.0F, 3.0F, 3.0F), ModelTransform.of(0.0F, 1.9F, -0.9F, -0.6981F, 0.0F, 0.0F));
        ModelPartData bipedLeftArm = bipedBody.addChild("bipedLeftArm", ModelPartBuilder.create().uv(46, 19).mirrored(true).cuboid(-2.0F, -2.0F, -2.0F, 3.0F, 8.0F, 4.0F), ModelTransform.of(-5.0F, 1.9F, 1.0F, 0.0873F, 0.0F, 0.4538F));
        bipedLeftArm.addChild("lArm02", ModelPartBuilder.create().uv(61, 19).mirrored(true).cuboid(-1.5F, -0.3F, -2.0F, 3.0F, 9.0F, 4.0F), ModelTransform.of(-0.5F, 6.0F, 0.0F, -0.1745F, 0.0F, 0.2094F));
        ModelPartData bipedRightArm = bipedBody.addChild("bipedRightArm", ModelPartBuilder.create().uv(46, 19).cuboid(-1.0F, -2.0F, -2.0F, 3.0F, 8.0F, 4.0F), ModelTransform.of(5.0F, 1.9F, 1.0F, 0.0873F, 0.0F, -1.0472F));
        bipedRightArm.addChild("rArm02", ModelPartBuilder.create().uv(61, 19).cuboid(-1.5F, -1.1F, -2.0F, 3.0F, 9.0F, 4.0F), ModelTransform.of(0.5F, 5.2F, 0.0F, -0.1745F, 0.0F, -1.5359F));
        ModelPartData bipedLeftLeg = bipedBody.addChild("bipedLeftLeg", ModelPartBuilder.create().uv(0, 16).mirrored(true).cuboid(-2.0F, -1.6F, -2.9F, 4.0F, 11.0F, 5.0F), ModelTransform.of(-2.4F, 13.7F, 0.3F, -1.5708F, 0.6283F, 0.1396F));
        ModelPartData lLeg02 = bipedLeftLeg.addChild("lLeg02", ModelPartBuilder.create().uv(0, 32).mirrored(true).cuboid(-1.5F, 0.0F, -2.0F, 3.0F, 8.0F, 4.0F), ModelTransform.of(-0.5F, 7.4F, -1.7F, 0.5411F, 0.1396F, -1.501F));
        ModelPartData lLeg03 = lLeg02.addChild("lLeg03", ModelPartBuilder.create().uv(0, 44).mirrored(true).cuboid(-1.0F, 0.0F, -1.5F, 2.0F, 8.0F, 3.0F), ModelTransform.of(0.0F, 6.0F, 0.2F, -0.3643F, 0.2618F, -0.4014F));
        lLeg03.addChild("lHoof", ModelPartBuilder.create().uv(0, 55).mirrored(true).cuboid(-1.5F, -0.9F, -2.8F, 3.0F, 2.0F, 4.0F), ModelTransform.of(0.0F, 7.9F, 0.0F, 1.7453F, 0.0F, 0.0F));
        ModelPartData bipedRightLeg = bipedBody.addChild("bipedRightLeg", ModelPartBuilder.create().uv(0, 16).cuboid(-2.0F, -1.6F, -2.9F, 4.0F, 11.0F, 5.0F), ModelTransform.of(2.4F, 13.7F, 0.3F, -1.5708F, -0.6283F, -0.1396F));
        ModelPartData rLeg02 = bipedRightLeg.addChild("rLeg02", ModelPartBuilder.create().uv(0, 32).cuboid(-1.5F, 0.0F, -2.0F, 3.0F, 8.0F, 4.0F), ModelTransform.of(0.5F, 7.4F, -1.7F, 0.6981F, -0.1745F, 1.3963F));
        ModelPartData rLeg03 = rLeg02.addChild("rLeg03", ModelPartBuilder.create().uv(0, 44).cuboid(-1.0F, 0.0F, -1.5F, 2.0F, 8.0F, 3.0F), ModelTransform.of(0.0F, 6.0F, 0.2F, -0.5236F, 0.0F, 0.5463F));
        rLeg03.addChild("rHoof", ModelPartBuilder.create().uv(0, 55).cuboid(-1.5F, -0.9F, -2.8F, 3.0F, 2.0F, 4.0F), ModelTransform.of(0.0F, 7.9F, 0.0F, 1.7453F, 0.0F, 0.0F));
        ModelPartData lWing01 = bipedBody.addChild("lWing01", ModelPartBuilder.create().uv(43, 32).cuboid(-1.0F, -1.5F, 0.0F, 2.0F, 3.0F, 6.0F), ModelTransform.of(-1.8F, 2.8F, 1.4F, 0.6283F, -0.6458F, 0.0F));
        ModelPartData lWing02 = lWing01.addChild("lWing02", ModelPartBuilder.create().uv(43, 41).cuboid(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 9.0F), ModelTransform.of(-0.1F, 0.2F, 5.6F, 0.5236F, -0.1396F, 0.0F));
        ModelPartData lWing03 = lWing02.addChild("lWing03", ModelPartBuilder.create().uv(42, 53).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 9.0F, 2.0F), ModelTransform.of(-0.1F, 0.4F, 8.0F, -0.3491F, 0.0F, 0.0F));
        lWing03.addChild("lWing04", ModelPartBuilder.create().uv(51, 52).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 11.0F, 1.0F), ModelTransform.of(0.0F, 8.6F, 0.2F, -0.4712F, 0.0F, 0.0F));
        lWing03.addChild("lWingFeathers02", ModelPartBuilder.create().uv(0, 65).cuboid(-0.5F, 2.8F, -0.7F, 1.0F, 21.0F, 3.0F), ModelTransform.of(0.0F, 2.3F, 0.0F, -0.4363F, 0.0F, 0.0F));
        lWing03.addChild("lWingFeathers02b", ModelPartBuilder.create().uv(0, 65).cuboid(-0.5F, 0.7F, -3.6F, 1.0F, 21.0F, 3.0F), ModelTransform.of(0.0F, 2.3F, 0.0F, -0.4363F, 0.0F, 0.0F));
        lWing03.addChild("lWingFeathers02c", ModelPartBuilder.create().uv(0, 65).cuboid(-0.5F, 0.7F, -3.8F, 1.0F, 18.0F, 3.0F), ModelTransform.of(0.0F, 2.3F, -2.8F, -0.4363F, 0.0F, 0.0F));
        lWing02.addChild("lWingFeathers01", ModelPartBuilder.create().uv(78, 28).cuboid(-0.5F, -0.6F, -11.1F, 1.0F, 10.0F, 18.0F), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.1745F, 0.0F, 0.0F));
        ModelPartData rWing01 = bipedBody.addChild("rWing01", ModelPartBuilder.create().uv(43, 32).cuboid(-1.0F, -1.5F, 0.0F, 2.0F, 3.0F, 6.0F), ModelTransform.of(1.8F, 2.8F, 1.4F, 0.6283F, 0.6458F, 0.0F));
        ModelPartData rWing02 = rWing01.addChild("rWing02", ModelPartBuilder.create().uv(43, 41).cuboid(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 9.0F), ModelTransform.of(0.1F, 0.2F, 5.6F, 0.5236F, 0.1396F, 0.0F));
        ModelPartData rWing03 = rWing02.addChild("rWing03", ModelPartBuilder.create().uv(42, 53).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 9.0F, 2.0F), ModelTransform.of(0.1F, 0.4F, 8.0F, -0.3491F, 0.0F, 0.0F));
        rWing03.addChild("rWing04", ModelPartBuilder.create().uv(51, 52).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 11.0F, 1.0F), ModelTransform.of(0.0F, 8.6F, 0.2F, -0.4712F, 0.0F, 0.0F));
        rWing03.addChild("rWingFeathers02", ModelPartBuilder.create().uv(0, 65).cuboid(-1.0F, 2.8F, -0.7F, 1.0F, 21.0F, 3.0F), ModelTransform.of(0.5F, 2.3F, 0.0F, -0.4363F, 0.0F, 0.0F));
        rWing03.addChild("rWingFeathers02b", ModelPartBuilder.create().uv(0, 65).cuboid(-0.5F, 0.7F, -3.6F, 1.0F, 21.0F, 3.0F), ModelTransform.of(0.0F, 2.3F, 0.0F, -0.4363F, 0.0F, 0.0F));
        rWing03.addChild("rWingFeathers02c", ModelPartBuilder.create().uv(0, 65).cuboid(-0.5F, 0.7F, -3.8F, 1.0F, 18.0F, 3.0F), ModelTransform.of(0.0F, 2.3F, -2.8F, -0.4363F, 0.0F, 0.0F));
        rWing02.addChild("rWingFeathers01", ModelPartBuilder.create().uv(78, 28).cuboid(-0.5F, -0.6F, -11.1F, 1.0F, 10.0F, 18.0F), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.1745F, 0.0F, 0.0F));
        ModelPartData head = bipedBody.addChild("head", ModelPartBuilder.create().uv(1, 2).cuboid(-3.5F, -7.0F, -3.5F, 7.0F, 7.0F, 7.0F), ModelTransform.of(0.0F, 0.2F, -0.5F, 0.0F, 0.0F, 0.0F));
        ModelPartData rHorn01 = head.addChild("rHorn01", ModelPartBuilder.create().uv(23, 0).mirrored(true).cuboid(-1.0F, -2.7F, -1.0F, 2.0F, 4.0F, 2.0F), ModelTransform.of(-2.9F, -7.2F, -0.2F, -0.1745F, -0.1396F, -0.4014F));
        ModelPartData rHorn02 = rHorn01.addChild("rHorn02", ModelPartBuilder.create().mirrored(true).cuboid(-0.4F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.3F, 0.3F, 0.3F)), ModelTransform.of(0.0F, -2.2F, 0.0F, -0.1745F, 0.0F, -0.2618F));
        ModelPartData rHorn03 = rHorn02.addChild("rHorn03", ModelPartBuilder.create().uv(0, 4).mirrored(true).cuboid(-0.3F, -3.0F, -0.6F, 1.0F, 3.0F, 1.0F, new Dilation(0.15F, 0.15F, 0.15F)), ModelTransform.of(0.0F, -2.7F, 0.0F, -0.1047F, 0.0F, -0.1745F));
        ModelPartData rHorn04 = rHorn03.addChild("rHorn04", ModelPartBuilder.create().mirrored(true).cuboid(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F), ModelTransform.of(0.0F, -2.8F, 0.0F, 0.0524F, 0.0F, -0.1396F));
        rHorn04.addChild("rHorn05", ModelPartBuilder.create().mirrored(true).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F), ModelTransform.of(0.0F, -2.7F, 0.0F, 0.0524F, 0.0F, 0.1396F));
        ModelPartData lHorn01 = head.addChild("lHorn01", ModelPartBuilder.create().uv(23, 0).cuboid(-1.0F, -2.7F, -1.0F, 2.0F, 4.0F, 2.0F), ModelTransform.of(2.9F, -7.2F, -0.2F, -0.1745F, 0.1396F, 0.4014F));
        ModelPartData lHorn02 = lHorn01.addChild("lHorn02", ModelPartBuilder.create().cuboid(-0.6F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.3F, 0.3F, 0.3F)), ModelTransform.of(0.0F, -2.2F, 0.0F, -0.1745F, 0.0F, 0.2618F));
        ModelPartData lHorn03 = lHorn02.addChild("lHorn03", ModelPartBuilder.create().uv(0, 4).cuboid(-0.7F, -3.0F, -0.6F, 1.0F, 3.0F, 1.0F, new Dilation(0.15F, 0.15F, 0.15F)), ModelTransform.of(0.0F, -2.7F, 0.0F, -0.1047F, 0.0F, 0.1745F));
        ModelPartData lHorn04 = lHorn03.addChild("lHorn04", ModelPartBuilder.create().cuboid(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F), ModelTransform.of(0.0F, -2.8F, 0.0F, 0.0524F, 0.0F, 0.1396F));
        lHorn04.addChild("lHorn05", ModelPartBuilder.create().cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F), ModelTransform.of(0.0F, -2.7F, 0.0F, 0.0524F, 0.0F, -0.1396F));
        head.addChild("snout", ModelPartBuilder.create().uv(29, 2).cuboid(-2.0F, -1.9F, -5.1F, 4.0F, 3.0F, 5.0F), ModelTransform.of(0.0F, -4.6F, -2.5F, 0.5236F, 0.0F, 0.0F));
        head.addChild("jawUpper00", ModelPartBuilder.create().uv(43, 11).cuboid(-2.5F, -1.0F, -5.0F, 5.0F, 2.0F, 5.0F), ModelTransform.of(0.0F, -2.5F, -2.2F, 0.0698F, 0.0F, 0.0F));
        ModelPartData jawLower = head.addChild("jawLower", ModelPartBuilder.create().uv(48, 5).cuboid(-2.0F, -0.5F, -4.0F, 4.0F, 1.0F, 4.0F), ModelTransform.of(0.0F, -1.0F, -3.0F, -0.0349F, 0.0F, 0.0F));
        jawLower.addChild("beard", ModelPartBuilder.create().uv(18, 59).cuboid(-1.5F, 0.0F, -1.0F, 3.0F, 3.0F, 2.0F), ModelTransform.of(0.0F, 0.3F, -2.4F, -0.0349F, 0.0F, 0.0F));
        head.addChild("lEar", ModelPartBuilder.create().uv(48, 0).cuboid(0.0F, -0.5F, -1.0F, 4.0F, 1.0F, 2.0F), ModelTransform.of(2.6F, -6.0F, 0.8F, -0.3491F, 0.0F, 0.3142F));
        head.addChild("rEar", ModelPartBuilder.create().uv(48, 0).mirrored(true).cuboid(-4.0F, -0.5F, -1.0F, 4.0F, 1.0F, 2.0F), ModelTransform.of(-2.6F, -6.0F, 0.8F, -0.3491F, 0.0F, -0.3142F));
        ModelPartData torch00 = head.addChild("torch00", ModelPartBuilder.create().uv(117, 0).cuboid(-1.0F, -2.5F, -1.0F, 2.0F, 3.0F, 2.0F), ModelTransform.of(0.0F, -6.6F, -1.2F, 0.0F, -0.7854F, 0.0F));
        ModelPartData torch01a = torch00.addChild("torch01a", ModelPartBuilder.create().uv(117, 6).cuboid(-0.5F, -3.8F, -0.5F, 1.0F, 4.0F, 1.0F, new Dilation(0.25F, 0.25F, 0.25F)), ModelTransform.of(0.0F, -2.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        ModelPartData torch02a = torch01a.addChild("torch02a", ModelPartBuilder.create().uv(115, 12).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F), ModelTransform.of(0.0F, -3.6F, 0.0F, 0.0F, 0.0F, 0.0F));torch02a.addChild("torch03a", ModelPartBuilder.create().uv(122, 6).mirrored(true).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F), ModelTransform.of(-0.6F, -1.5F, -0.6F, 0.2443F, -0.1396F, -0.2793F));
        torch02a.addChild("torch03b", ModelPartBuilder.create().uv(122, 6).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F), ModelTransform.of(0.6F, -1.5F, -0.6F, 0.2443F, 0.2094F, 0.2793F));
        torch02a.addChild("torch03d", ModelPartBuilder.create().uv(122, 10).mirrored(true).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F), ModelTransform.of(-0.6F, -1.5F, 0.6F, -0.2443F, 0.2094F, -0.2793F));
        torch02a.addChild("torch03c", ModelPartBuilder.create().uv(122, 10).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F), ModelTransform.of(0.6F, -1.5F, 0.6F, -0.2443F, -0.1396F, 0.2793F));

        return TexturedModelData.of(data, 128,128);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        bipedBody.render(matrices, vertices, light, overlay);
        plith01.render(matrices, vertices, light, overlay);
    }
}
