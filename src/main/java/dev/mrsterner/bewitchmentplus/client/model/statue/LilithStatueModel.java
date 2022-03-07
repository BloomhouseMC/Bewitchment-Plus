package dev.mrsterner.bewitchmentplus.client.model.statue;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

public class LilithStatueModel<T extends Entity> extends EntityModel<T> {


    public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new Identifier(BewitchmentPlus.MODID, "statue_lilith"), "main");
    private final ModelPart body;
    private final ModelPart plith01;

    public LilithStatueModel(ModelPart root) {
        this.body = root.getChild("body");
        this.plith01 = root.getChild("plith01");
    }

    public static TexturedModelData create(){
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData body = root.addChild("body",
        ModelPartBuilder.create().uv(20, 16).cuboid(-4.0F, 0.0F, -2.5F, 8.0F, 6.0F, 5.0F),
        ModelTransform.of(0.0F, -20.0F, 1.2F, 0.0F, 0.0F, 0.0F));
        ModelPartData stomach = body.addChild("stomach",
        ModelPartBuilder.create().uv(19, 28).cuboid(-3.5F, 0.0F, -2.5F, 7.0F, 6.0F, 5.0F),
        ModelTransform.of(0.0F, 6.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        ModelPartData hips = stomach.addChild("hips",
        ModelPartBuilder.create().uv(19, 39).cuboid(-4.0F, 0.0F, -2.5F, 8.0F, 3.0F, 5.0F),
        ModelTransform.of(0.0F, 5.9F, 0.0F, 0.0F, 0.0F, 0.0F));
        ModelPartData leftLeg00 = hips.addChild("leftLeg00",
        ModelPartBuilder.create().uv(0, 16).mirrored(true).cuboid(-2.5F, -1.0F, -2.5F, 5.0F, 12.0F, 5.0F),
        ModelTransform.of(-2.3F, 2.0F, 0.0F, -0.0698F, 0.0F, 0.0349F));
        ModelPartData leftLeg01 = leftLeg00.addChild("leftLeg01",
        ModelPartBuilder.create().uv(0, 34).mirrored(true).cuboid(-2.0F, -1.2F, -2.0F, 4.0F, 12.0F, 4.0F),
        ModelTransform.of(0.0F, 11.5F, -0.2F, 0.0873F, 0.0F, -0.0349F));leftLeg01.addChild("lTalon01",
        ModelPartBuilder.create().uv(0, 52).mirrored(true).cuboid(-0.5F, -0.5F, -4.0F, 1.0F, 2.0F, 4.0F),
        ModelTransform.of(-1.1F, 9.7F, -1.4F, 0.0349F, 0.1745F, 0.0F));leftLeg01.addChild("lTalon02",
        ModelPartBuilder.create().uv(0, 52).mirrored(true).cuboid(-0.5F, -0.5F, -4.0F, 1.0F, 2.0F, 4.0F),
        ModelTransform.of(0.0F, 9.7F, -1.7F, 0.0349F, 0.0F, 0.0F));leftLeg01.addChild("lTalon03",
        ModelPartBuilder.create().uv(0, 52).mirrored(true).cuboid(-0.5F, -0.5F, -4.0F, 1.0F, 2.0F, 4.0F),
        ModelTransform.of(1.1F, 9.7F, -1.4F, 0.0349F, -0.1745F, 0.0F));leftLeg01.addChild("lTalon04",
        ModelPartBuilder.create().uv(0, 58).mirrored(true).cuboid(-0.5F, -0.5F, 0.0F, 1.0F, 2.0F, 4.0F),
        ModelTransform.of(0.0F, 9.7F, 1.6F, -0.0349F, 0.0F, 0.0F));
        ModelPartData rightLeg00 = hips.addChild("rightLeg00",
        ModelPartBuilder.create().uv(0, 16).cuboid(-2.5F, -1.0F, -2.5F, 5.0F, 12.0F, 5.0F),
        ModelTransform.of(2.3F, 2.0F, 0.0F, -0.0698F, 0.0F, -0.0349F));
        ModelPartData rightLeg01 = rightLeg00.addChild("rightLeg01",
        ModelPartBuilder.create().uv(0, 34).cuboid(-2.0F, -1.2F, -2.0F, 4.0F, 12.0F, 4.0F),
        ModelTransform.of(0.0F, 11.5F, -0.2F, 0.0873F, 0.0F, 0.0349F));rightLeg01.addChild("rTalon01",
        ModelPartBuilder.create().uv(0, 52).cuboid(-0.5F, -0.5F, -4.0F, 1.0F, 2.0F, 4.0F),
        ModelTransform.of(1.1F, 9.7F, -1.4F, 0.0349F, -0.1745F, 0.0F));rightLeg01.addChild("rTalon02",
        ModelPartBuilder.create().uv(0, 52).cuboid(-0.5F, -0.5F, -4.0F, 1.0F, 2.0F, 4.0F),
        ModelTransform.of(0.0F, 9.7F, -1.7F, 0.0349F, 0.0F, 0.0F));rightLeg01.addChild("rTalon03",
        ModelPartBuilder.create().uv(0, 52).cuboid(-0.5F, -0.5F, -4.0F, 1.0F, 2.0F, 4.0F),
        ModelTransform.of(-1.1F, 9.7F, -1.4F, 0.0349F, 0.1745F, 0.0F));rightLeg01.addChild("rTalon04",
        ModelPartBuilder.create().uv(0, 58).cuboid(-0.5F, -0.5F, 0.0F, 1.0F, 2.0F, 4.0F),
        ModelTransform.of(0.0F, 9.7F, 1.6F, -0.0349F, 0.0F, 0.0F));hips.addChild("frontLoincloth",
        ModelPartBuilder.create().uv(73, 45).cuboid(-5.6F, 0.0F, 0.0F, 10.0F, 14.0F, 5.0F),
        ModelTransform.of(0.0F, 0.0F, -2.7F, -0.0698F, 0.0F, 0.0F));hips.addChild("backLoincloth",
        ModelPartBuilder.create().uv(103, 45).cuboid(-5.5F, 0.0F, -1.0F, 9.0F, 12.0F, 2.0F),
        ModelTransform.of(0.0F, 0.1F, 1.8F, 0.0F, 0.0F, 0.0F));
        ModelPartData boobs = body.addChild("boobs",
        ModelPartBuilder.create().uv(19, 48).cuboid(-3.5F, 0.0F, -2.0F, 7.0F, 4.0F, 4.0F),
        ModelTransform.of(0.0F, 1.9F, -0.8F, -0.6981F, 0.0F, 0.0F));boobs.addChild("boobWrap",
        ModelPartBuilder.create().uv(20, 59).cuboid(-3.5F, 0.27F, 0.15F, 7.0F, 0.0F, 4.0F),
        ModelTransform.of(0.0F, 3.7F, -1.9F, -0.8727F, 0.0F, 0.0F));
        ModelPartData head = body.addChild("head",
        ModelPartBuilder.create().cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F),
        ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));head.addChild("mhair01",
        ModelPartBuilder.create().uv(57, 0).cuboid(-3.5F, -1.0F, 0.0F, 7.0F, 10.0F, 1.0F),
        ModelTransform.of(0.0F, -6.6F, 3.7F, 0.5236F, 0.0F, 0.0F));head.addChild("mhair02",
        ModelPartBuilder.create().uv(75, 0).cuboid(-4.0F, -1.0F, -1.0F, 8.0F, 11.0F, 2.0F),
        ModelTransform.of(0.0F, -4.0F, 3.7F, 0.2618F, 0.0F, 0.0F));
        ModelPartData lHorn01 = head.addChild("lHorn01",
        ModelPartBuilder.create().uv(109, 0).mirrored(true).cuboid(-1.5F, -4.0F, -1.5F, 3.0F, 4.0F, 3.0F),
        ModelTransform.of(-2.3F, -6.9F, -1.3F, -0.2618F, 0.0F, -0.5236F));
        ModelPartData lHorn02 = lHorn01.addChild("lHorn02",
        ModelPartBuilder.create().uv(109, 7).mirrored(true).cuboid(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F),
        ModelTransform.of(0.0F, -3.5F, 0.0F, -0.4538F, 0.0F, 0.1745F));
        ModelPartData lHorn03 = lHorn02.addChild("lHorn03",
        ModelPartBuilder.create().uv(118, 7).mirrored(true).cuboid(-1.01F, -3.0F, -0.99F, 2.0F, 3.0F, 2.0F),
        ModelTransform.of(0.0F, -2.5F, 0.0F, -0.4363F, 0.0F, 0.0F));
        ModelPartData lHorn04a = lHorn03.addChild("lHorn04a",
        ModelPartBuilder.create().mirrored(true).cuboid(-0.3F, -3.0F, -0.7F, 1.0F, 3.0F, 1.0F),
        ModelTransform.of(-0.2F, -2.7F, -0.3F, -0.6981F, 0.0F, 0.4014F));lHorn04a.addChild("lHorn04b",
        ModelPartBuilder.create().mirrored(true).cuboid(-0.7F, -3.0F, -0.7F, 1.0F, 3.0F, 1.0F),
        ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));lHorn04a.addChild("lHorn04c",
        ModelPartBuilder.create().mirrored(true).cuboid(-0.3F, -3.0F, -0.3F, 1.0F, 3.0F, 1.0F),
        ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        ModelPartData lHorn04d = lHorn04a.addChild("lHorn04d",
        ModelPartBuilder.create().mirrored(true).cuboid(-0.7F, -3.0F, -0.3F, 1.0F, 3.0F, 1.0F),
        ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        ModelPartData lHorn05 = lHorn04d.addChild("lHorn05",
        ModelPartBuilder.create().uv(0, 4).mirrored(true).cuboid(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F),
        ModelTransform.of(0.0F, -2.8F, 0.0F, -0.4363F, 0.0F, -0.2269F));lHorn05.addChild("lHorn06",
        ModelPartBuilder.create().mirrored(true).cuboid(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F),
        ModelTransform.of(0.0F, -2.7F, 0.0F, 0.4887F, 0.0F, -0.2269F));
        ModelPartData rHorn01 = head.addChild("rHorn01",
        ModelPartBuilder.create().uv(109, 0).cuboid(-1.5F, -4.0F, -1.5F, 3.0F, 4.0F, 3.0F),
        ModelTransform.of(2.3F, -6.9F, -1.3F, -0.2618F, 0.0F, 0.5236F));
        ModelPartData rHorn02 = rHorn01.addChild("rHorn02",
        ModelPartBuilder.create().uv(109, 7).cuboid(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F),
        ModelTransform.of(0.0F, -3.5F, 0.0F, -0.4538F, 0.0F, -0.1745F));
        ModelPartData rHorn03 = rHorn02.addChild("rHorn03",
        ModelPartBuilder.create().uv(118, 7).cuboid(-0.99F, -3.0F, -0.99F, 2.0F, 3.0F, 2.0F),
        ModelTransform.of(0.0F, -2.5F, 0.0F, -0.4363F, 0.0F, 0.0F));
        ModelPartData rHorn04a = rHorn03.addChild("rHorn04a",
        ModelPartBuilder.create().cuboid(-0.3F, -3.0F, -0.7F, 1.0F, 3.0F, 1.0F),
        ModelTransform.of(0.2F, -2.7F, 0.3F, -0.6981F, 0.0F, -0.4014F));rHorn04a.addChild("rHorn04b",
        ModelPartBuilder.create().cuboid(-0.7F, -3.0F, -0.7F, 1.0F, 3.0F, 1.0F),
        ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));rHorn04a.addChild("rHorn04c",
        ModelPartBuilder.create().cuboid(-0.3F, -3.0F, -0.3F, 1.0F, 3.0F, 1.0F),
        ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        ModelPartData rHorn04d = rHorn04a.addChild("rHorn04d",
        ModelPartBuilder.create().cuboid(-0.7F, -3.0F, -0.3F, 1.0F, 3.0F, 1.0F),
        ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        ModelPartData rHorn05 = rHorn04d.addChild("rHorn05",
        ModelPartBuilder.create().uv(0, 4).cuboid(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F),
        ModelTransform.of(0.0F, -2.8F, 0.0F, -0.4363F, 0.0F, 0.2269F));rHorn05.addChild("rHorn06",
        ModelPartBuilder.create().cuboid(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F),
        ModelTransform.of(0.0F, -2.7F, 0.0F, 0.4887F, 0.0F, 0.2269F));head.addChild("lHair",
        ModelPartBuilder.create().uv(45, 0).cuboid(-2.0F, 0.0F, -1.5F, 2.0F, 10.0F, 3.0F),
        ModelTransform.of(-2.4F, -6.3F, 1.1F, 0.3491F, 0.0F, 0.3142F));head.addChild("rHair",
        ModelPartBuilder.create().uv(45, 0).cuboid(0.0F, 0.0F, -1.5F, 2.0F, 10.0F, 3.0F),
        ModelTransform.of(2.4F, -6.3F, 1.1F, 0.3491F, 0.0F, -0.3142F));
        ModelPartData crown00 = head.addChild("crown00",
        ModelPartBuilder.create().uv(27, 0).cuboid(-3.0F, -2.5F, 0.0F, 6.0F, 3.0F, 1.0F),
        ModelTransform.of(0.0F, -8.0F, -3.5F, 0.0F, 0.0F, 0.0F));
        ModelPartData crownL01 = crown00.addChild("crownL01",
        ModelPartBuilder.create().uv(27, 0).cuboid(-1.8F, -1.5F, -0.49F, 2.0F, 3.0F, 1.0F),
        ModelTransform.of(-2.3F, -1.0F, 0.5F, 0.0F, 0.0F, 0.6981F));
        ModelPartData crownL02 = crownL01.addChild("crownL02",
        ModelPartBuilder.create().uv(27, 0).cuboid(-2.3F, -1.0F, -0.5F, 3.0F, 2.0F, 1.0F),
        ModelTransform.of(-1.5F, -0.1F, 0.1F, 0.0F, 0.0F, 0.4189F));
        ModelPartData crownL03 = crownL02.addChild("crownL03",
        ModelPartBuilder.create().uv(27, 0).cuboid(-2.3F, -1.0F, -0.49F, 3.0F, 2.0F, 1.0F),
        ModelTransform.of(-2.2F, -0.2F, 0.0F, 0.0F, 0.0F, 0.6109F));
        ModelPartData crownL04 = crownL03.addChild("crownL04",
        ModelPartBuilder.create().uv(27, 0).cuboid(-2.9F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F),
        ModelTransform.of(-2.0F, -0.3F, 0.0F, 0.0F, 0.0F, 0.4363F));crownL04.addChild("crownL05",
        ModelPartBuilder.create().uv(27, 0).cuboid(-2.3F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F),
        ModelTransform.of(-0.1F, 0.6F, 0.0F, 0.0F, 0.0F, 0.2094F));
        ModelPartData crownR01 = crown00.addChild("crownR01",
        ModelPartBuilder.create().uv(27, 0).cuboid(-0.2F, -1.5F, -0.49F, 2.0F, 3.0F, 1.0F),
        ModelTransform.of(2.3F, -1.0F, 0.5F, 0.0F, 0.0F, -0.6981F));
        ModelPartData crownR02 = crownR01.addChild("crownR02",
        ModelPartBuilder.create().uv(27, 0).cuboid(-0.7F, -1.0F, -0.5F, 3.0F, 2.0F, 1.0F),
        ModelTransform.of(1.5F, -0.1F, 0.1F, 0.0F, 0.0F, -0.4189F));
        ModelPartData crownR03 = crownR02.addChild("crownR03",
        ModelPartBuilder.create().uv(27, 0).cuboid(-0.7F, -1.0F, -0.49F, 3.0F, 2.0F, 1.0F),
        ModelTransform.of(2.2F, -0.2F, 0.0F, 0.0F, 0.0F, -0.6109F));
        ModelPartData crownR04 = crownR03.addChild("crownR04",
        ModelPartBuilder.create().uv(27, 0).cuboid(-0.1F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F),
        ModelTransform.of(2.0F, -0.3F, 0.0F, 0.0F, 0.0F, -0.4363F));crownR04.addChild("crownR05",
        ModelPartBuilder.create().uv(27, 0).cuboid(-0.7F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F),
        ModelTransform.of(0.1F, 0.6F, 0.0F, 0.0F, 0.0F, -0.2094F));
        ModelPartData leftArm = body.addChild("leftArm",
        ModelPartBuilder.create().uv(46, 16).mirrored(true).cuboid(-2.0F, -2.0F, -2.0F, 3.0F, 16.0F, 4.0F),
        ModelTransform.of(-4.5F, 1.9F, 0.0F, 0.0F, 0.7854F, 0.6981F));leftArm.addChild("lClaw01",
        ModelPartBuilder.create().uv(11, 53).mirrored(true).cuboid(-2.0F, 0.0F, -0.5F, 2.0F, 5.0F, 1.0F),
        ModelTransform.of(-0.6F, 11.2F, -1.6F, 0.0F, 0.0F, -0.1745F));leftArm.addChild("lClaw02",
        ModelPartBuilder.create().uv(11, 53).mirrored(true).cuboid(-2.0F, 0.0F, -0.5F, 2.0F, 5.0F, 1.0F),
        ModelTransform.of(-0.6F, 11.8F, -0.5F, 0.0F, 0.0F, -0.1222F));leftArm.addChild("lClaw03",
        ModelPartBuilder.create().uv(11, 53).mirrored(true).cuboid(-2.0F, 0.0F, -0.5F, 2.0F, 5.0F, 1.0F),
        ModelTransform.of(-0.6F, 11.8F, 0.6F, 0.0F, 0.0F, -0.1222F));leftArm.addChild("lClaw04",
        ModelPartBuilder.create().uv(11, 53).mirrored(true).cuboid(-2.0F, 0.0F, -0.5F, 2.0F, 5.0F, 1.0F),
        ModelTransform.of(-0.6F, 11.2F, 1.6F, 0.0F, 0.0F, -0.1745F));
        ModelPartData rightArm = body.addChild("rightArm",
        ModelPartBuilder.create().uv(46, 16).cuboid(-1.0F, -2.0F, -2.0F, 3.0F, 16.0F, 4.0F),
        ModelTransform.of(4.5F, 1.9F, 0.0F, 0.0F, -0.7854F, -0.6981F));rightArm.addChild("rClaw01",
        ModelPartBuilder.create().uv(11, 53).cuboid(0.0F, 0.0F, -0.5F, 2.0F, 5.0F, 1.0F),
        ModelTransform.of(0.6F, 11.2F, -1.6F, 0.0F, 0.0F, 0.1745F));rightArm.addChild("rClaw02",
        ModelPartBuilder.create().uv(11, 53).cuboid(0.0F, 0.0F, -0.5F, 2.0F, 5.0F, 1.0F),
        ModelTransform.of(0.6F, 11.8F, -0.5F, 0.0F, 0.0F, 0.1222F));rightArm.addChild("rClaw03",
        ModelPartBuilder.create().uv(11, 53).cuboid(0.0F, 0.0F, -0.5F, 2.0F, 5.0F, 1.0F),
        ModelTransform.of(0.6F, 11.8F, 0.6F, 0.0F, 0.0F, 0.1222F));rightArm.addChild("rClaw04",
        ModelPartBuilder.create().uv(11, 53).cuboid(0.0F, 0.0F, -0.5F, 2.0F, 5.0F, 1.0F),
        ModelTransform.of(0.6F, 11.2F, 1.6F, 0.0F, 0.0F, 0.1745F));
        ModelPartData lWing01 = body.addChild("lWing01",
        ModelPartBuilder.create().uv(60, 14).cuboid(-1.0F, -1.5F, 0.0F, 2.0F, 3.0F, 6.0F),
        ModelTransform.of(-1.8F, 3.0F, 1.4F, 0.733F, -0.6458F, 0.0F));
        ModelPartData lWing02 = lWing01.addChild("lWing02",
        ModelPartBuilder.create().uv(60, 25).cuboid(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 9.0F), ModelTransform.of(-0.1F, 0.2F, 5.6F, -1.5708F, -0.1396F, 0.1396F));
        ModelPartData lWing03 = lWing02.addChild("lWing03",
        ModelPartBuilder.create().uv(42, 53).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 9.0F, 2.0F),
        ModelTransform.of(-0.1F, 0.4F, 8.0F, 1.2566F, 0.0F, 0.0F));lWing03.addChild("lWing04",
        ModelPartBuilder.create().uv(51, 52).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 11.0F, 1.0F),
        ModelTransform.of(0.0F, 8.6F, 0.2F, -0.4712F, 0.0F, 0.0F));lWing03.addChild("lWingFeathers02",
        ModelPartBuilder.create().uv(0, 65).cuboid(-0.5F, 1.4F, -0.7F, 1.0F, 22.0F, 3.0F),
        ModelTransform.of(0.0F, 2.3F, 0.0F, -0.4363F, 0.0F, 0.0F));lWing03.addChild("lWingFeathers02b",
        ModelPartBuilder.create().uv(0, 65).cuboid(-0.5F, -2.3F, -3.6F, 1.0F, 24.0F, 3.0F),
        ModelTransform.of(0.0F, 2.3F, 0.0F, -0.4363F, 0.0F, 0.0F));lWing03.addChild("lWingFeathers02c",
        ModelPartBuilder.create().uv(0, 65).cuboid(-0.5F, -5.2F, -3.8F, 1.0F, 23.0F, 3.0F),
        ModelTransform.of(0.0F, 2.3F, -2.8F, -0.4363F, 0.0F, 0.0F));lWing03.addChild("lWingFeathers02d",
        ModelPartBuilder.create().uv(0, 65).cuboid(-0.5F, -6.1F, -6.7F, 1.0F, 22.0F, 3.0F),
        ModelTransform.of(0.0F, 2.3F, -2.8F, -0.4363F, 0.0F, 0.0F));lWing02.addChild("lWingFeathers01",
        ModelPartBuilder.create().uv(14, 62).cuboid(-0.5F, -0.6F, 0.0F, 1.0F, 10.0F, 8.0F),
        ModelTransform.of(0.0F, 0.0F, 0.0F, -0.1745F, 0.0F, 0.0F));
        ModelPartData rWing01 = body.addChild("rWing01",
        ModelPartBuilder.create().uv(60, 14).cuboid(-1.0F, -1.5F, 0.0F, 2.0F, 3.0F, 6.0F),
        ModelTransform.of(1.8F, 3.0F, 1.4F, 0.733F, 0.6458F, 0.0F));
        ModelPartData rWing02 = rWing01.addChild("rWing02",
        ModelPartBuilder.create().uv(60, 25).cuboid(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 9.0F),
        ModelTransform.of(0.1F, 0.2F, 5.6F, -1.5708F, 0.1396F, -0.1396F));
        ModelPartData rWing03 = rWing02.addChild("rWing03",
        ModelPartBuilder.create().uv(42, 53).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 9.0F, 2.0F),
        ModelTransform.of(0.1F, 0.4F, 8.0F, 1.2566F, 0.0F, 0.0F));rWing03.addChild("rWing04",
        ModelPartBuilder.create().uv(51, 52).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 11.0F, 1.0F),
        ModelTransform.of(0.0F, 8.6F, 0.2F, -0.4712F, 0.0F, 0.0F));rWing03.addChild("rWingFeathers02",
        ModelPartBuilder.create().uv(0, 65).cuboid(-0.5F, 1.4F, -0.7F, 1.0F, 22.0F, 3.0F),
        ModelTransform.of(0.0F, 2.3F, 0.0F, -0.4363F, 0.0F, 0.0F));rWing03.addChild("rWingFeathers02b",
        ModelPartBuilder.create().uv(0, 65).cuboid(-0.5F, -2.3F, -3.6F, 1.0F, 24.0F, 3.0F),
        ModelTransform.of(0.0F, 2.3F, 0.0F, -0.4363F, 0.0F, 0.0F));rWing03.addChild("rWingFeathers02c",
        ModelPartBuilder.create().uv(0, 65).cuboid(-0.5F, -5.2F, -3.8F, 1.0F, 23.0F, 3.0F),
        ModelTransform.of(0.0F, 2.3F, -2.8F, -0.4363F, 0.0F, 0.0F));rWing03.addChild("rWingFeathers02d",
        ModelPartBuilder.create().uv(0, 65).cuboid(-0.5F, -6.1F, -6.7F, 1.0F, 22.0F, 3.0F),
        ModelTransform.of(0.0F, 2.3F, -2.8F, -0.4363F, 0.0F, 0.0F));rWing02.addChild("rWingFeathers01",
        ModelPartBuilder.create().uv(14, 62).cuboid(-0.5F, -0.6F, 0.0F, 1.0F, 10.0F, 8.0F),
        ModelTransform.of(0.0F, 0.0F, 0.0F, -0.1745F, 0.0F, 0.0F));
        ModelPartData plith01 = root.addChild("plith01", ModelPartBuilder.create().uv(60, 84).cuboid(-8.5F, 0.0F, -8.5F, 17.0F, 5.0F, 17.0F), ModelTransform.of(0.0F, 19.0F, 0.0F, 0.0F, 0.0F, 0.0F));plith01.addChild("plith02", ModelPartBuilder.create().uv(68, 64).cuboid(-7.5F, -2.7F, -7.5F, 15.0F, 3.0F, 15.0F), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        return TexturedModelData.of(data, 128,128);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        body.render(matrices, vertices, light, overlay);
        plith01.render(matrices, vertices, light, overlay);
    }
}
