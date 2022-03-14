package dev.mrsterner.bewitchmentplus.client.model.statue;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

public class HerneStatueModel<T extends Entity> extends EntityModel<T> {
    public static final EntityModelLayer HERNE_STATUE_MODEL_LAYER = new EntityModelLayer(new Identifier(BewitchmentPlus.MODID, "statue_herne"), "main");
    private final ModelPart chest;
    private final ModelPart plith01;

    public HerneStatueModel(ModelPart root) {
        this.chest = root.getChild("chest");
        this.plith01 = root.getChild("plith01");
    }

    public static TexturedModelData create(){
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();
        ModelPartData chest = root.addChild("chest",
        ModelPartBuilder.create().uv(23, 15).cuboid(-4.9F, -8.0F, -3.5F, 10.0F, 8.0F, 7.0F), ModelTransform.of(0.0F, -12.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        chest.addChild("leftPec", ModelPartBuilder.create().uv(19, 50).cuboid(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 3.0F), ModelTransform.of(-2.6F, -4.8F, -1.2F, 0.0F, 0.1047F, -0.0873F));
        chest.addChild("rightPec", ModelPartBuilder.create().uv(19, 50).cuboid(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 3.0F), ModelTransform.of(2.6F, -4.8F, -1.2F, 0.0F, -0.1047F, 0.0873F));
        ModelPartData BipedLeftArm = chest.addChild("BipedLeftArm", ModelPartBuilder.create().uv(51, 31).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 21.0F, 1.0F), ModelTransform.of(-5.5F, -6.3F, 0.0F, -0.9599F, 0.0F, 0.1396F));
        ModelPartData lArm01 = BipedLeftArm.addChild("lArm01", ModelPartBuilder.create().uv(59, 13).mirrored(true).cuboid(-2.0F, -2.0F, -2.5F, 4.0F, 12.0F, 5.0F), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.3491F, -0.1745F, 0.0F));
        lArm01.addChild("lArm02", ModelPartBuilder.create().uv(60, 32).mirrored(true).cuboid(-1.5F, -1.0F, -2.0F, 3.0F, 13.0F, 4.0F), ModelTransform.of(0.0F, 9.5F, 0.0F, -1.4835F, 0.0F, 0.0F));
        lArm01.addChild("lMantle", ModelPartBuilder.create().uv(69, 45).mirrored(true).cuboid(-2.5F, -2.0F, -3.0F, 5.0F, 7.0F, 6.0F), ModelTransform.of(-0.1F, -0.3F, 0.0F, 0.0F, 0.0F, 0.0F));
        ModelPartData BipedRightArm = chest.addChild("BipedRightArm", ModelPartBuilder.create().uv(51, 31).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 21.0F, 1.0F), ModelTransform.of(5.5F, -6.3F, 0.0F, -0.1396F, 0.0F, -0.1396F));
        ModelPartData rArm01 = BipedRightArm.addChild("rArm01", ModelPartBuilder.create().uv(59, 13).cuboid(-2.0F, -2.0F, -2.5F, 4.0F, 12.0F, 5.0F), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.0349F, 0.0F, 0.0F));
        rArm01.addChild("rArm02", ModelPartBuilder.create().uv(60, 32).cuboid(-1.5F, -1.0F, -2.0F, 3.0F, 13.0F, 4.0F), ModelTransform.of(0.0F, 9.5F, 0.0F, -0.5236F, 0.0F, 0.0F));
        rArm01.addChild("rMantle", ModelPartBuilder.create().uv(69, 45).cuboid(-2.5F, -2.0F, -3.0F, 5.0F, 7.0F, 6.0F), ModelTransform.of(-0.1F, -0.3F, 0.0F, 0.0F, 0.0F, 0.0F));
        ModelPartData stomach = chest.addChild("stomach", ModelPartBuilder.create().uv(19, 31).cuboid(-4.5F, -6.5F, -3.0F, 9.0F, 10.0F, 6.0F), ModelTransform.of(0.0F, 6.2F, 0.5F, 0.0F, 0.0F, 0.0F));
        ModelPartData leftLeg00 = stomach.addChild("leftLeg00", ModelPartBuilder.create().uv(0, 16).mirrored(true).cuboid(-2.5F, -0.7F, -3.0F, 5.0F, 12.0F, 6.0F), ModelTransform.of(-2.5F, 3.0F, 0.1F, -0.7156F, 0.4189F, 0.1047F));
        ModelPartData leftLeg01 = leftLeg00.addChild("leftLeg01", ModelPartBuilder.create().uv(0, 34).mirrored(true).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 8.0F, 4.0F), ModelTransform.of(0.0F, 9.5F, -0.7F, 1.309F, 0.0F, -0.0873F));
        ModelPartData leftLeg02 = leftLeg01.addChild("leftLeg02", ModelPartBuilder.create().uv(0, 47).mirrored(true).cuboid(-1.5F, 0.0F, -1.5F, 3.0F, 9.0F, 3.0F), ModelTransform.of(0.0F, 6.5F, 1.3F, -0.9599F, 0.0F, 0.0F));
        ModelPartData lHoofClaw01a = leftLeg02.addChild("lHoofClaw01a", ModelPartBuilder.create().uv(9, 60).mirrored(true).cuboid(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F), ModelTransform.of(-0.7F, 8.4F, -1.4F, 0.4189F, 0.1047F, 0.0F));
        lHoofClaw01a.addChild("lHoofClaw01b", ModelPartBuilder.create().uv(0, 59).mirrored(true).cuboid(-0.51F, -1.1F, -1.2F, 1.0F, 1.0F, 3.0F), ModelTransform.of(0.0F, 0.0F, -0.8F, 0.3491F, 0.0F, 0.0F));
        ModelPartData lHoofClaw02a = leftLeg02.addChild("lHoofClaw02a", ModelPartBuilder.create().uv(9, 60).mirrored(true).cuboid(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F), ModelTransform.of(0.6F, 8.4F, -1.4F, 0.4189F, -0.0524F, 0.0F));
        lHoofClaw02a.addChild("lHoofClaw02b", ModelPartBuilder.create().uv(0, 59).mirrored(true).cuboid(-0.51F, -1.1F, -1.2F, 1.0F, 1.0F, 3.0F), ModelTransform.of(0.0F, 0.0F, -0.8F, 0.3491F, 0.0F, 0.0F));
        ModelPartData rightLeg00 = stomach.addChild("rightLeg00", ModelPartBuilder.create().uv(0, 16).cuboid(-2.5F, -0.7F, -3.0F, 5.0F, 12.0F, 6.0F), ModelTransform.of(2.5F, 3.0F, 0.1F, 0.0873F, 0.0F, -0.1047F));
        ModelPartData rightLeg01 = rightLeg00.addChild("rightLeg01", ModelPartBuilder.create().uv(0, 34).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 8.0F, 4.0F), ModelTransform.of(0.0F, 9.5F, -0.7F, 1.1694F, 0.0F, -0.1047F));
        ModelPartData rightLeg02 = rightLeg01.addChild("rightLeg02", ModelPartBuilder.create().uv(0, 47).cuboid(-1.5F, 0.0F, -1.5F, 3.0F, 9.0F, 3.0F), ModelTransform.of(0.0F, 6.5F, 0.8F, -1.309F, 0.0F, 0.1745F));
        ModelPartData rHoofClaw01a = rightLeg02.addChild("rHoofClaw01a", ModelPartBuilder.create().uv(9, 60).cuboid(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F), ModelTransform.of(0.7F, 8.4F, -1.4F, 0.0524F, -0.1047F, 0.0F));
        rHoofClaw01a.addChild("rHoofClaw01b", ModelPartBuilder.create().uv(0, 59).cuboid(-0.49F, -1.1F, -1.2F, 1.0F, 1.0F, 3.0F), ModelTransform.of(0.0F, 0.0F, -0.8F, 0.3491F, 0.0F, 0.0F));
        ModelPartData rHoofClaw02a = rightLeg02.addChild("rHoofClaw02a", ModelPartBuilder.create().uv(9, 60).cuboid(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F), ModelTransform.of(-0.6F, 8.4F, -1.4F, 0.0524F, 0.0524F, 0.0F));
        rHoofClaw02a.addChild("rHoofClaw02b", ModelPartBuilder.create().uv(0, 59).cuboid(-0.49F, -1.1F, -1.2F, 1.0F, 1.0F, 3.0F), ModelTransform.of(0.0F, 0.0F, -0.8F, 0.3491F, 0.0F, 0.0F));
        stomach.addChild("moss01", ModelPartBuilder.create().uv(92, 45).cuboid(-5.0F, 0.0F, -0.5F, 10.0F, 9.0F, 1.0F), ModelTransform.of(0.0F, 1.4F, -2.5F, -0.3665F, 0.0F, 0.0F));
        stomach.addChild("moss02", ModelPartBuilder.create().uv(39, 53).cuboid(-5.0F, 0.0F, -0.5F, 10.0F, 9.0F, 1.0F), ModelTransform.of(0.0F, 1.4F, 2.8F, 0.0F, 0.0F, 0.0F));
        stomach.addChild("mossL", ModelPartBuilder.create().uv(111, 48).cuboid(-0.5F, -0.7F, -3.5F, 1.0F, 9.0F, 7.0F), ModelTransform.of(-4.7F, 2.1F, -0.1F, -0.1745F, 0.0F, 0.1745F));
        stomach.addChild("mossR", ModelPartBuilder.create().uv(111, 48).cuboid(-0.5F, -0.7F, -3.5F, 1.0F, 9.0F, 7.0F), ModelTransform.of(4.7F, 2.1F, -0.1F, -0.1745F, 0.0F, -0.1745F));
        ModelPartData neck00 = chest.addChild("neck00", ModelPartBuilder.create().uv(51, 0).cuboid(-3.5F, -3.7F, -3.0F, 7.0F, 5.0F, 6.0F), ModelTransform.of(0.0F, -8.1F, -0.1F, 0.3491F, -0.2269F, 0.0F));
        ModelPartData neck01 = neck00.addChild("neck01", ModelPartBuilder.create().uv(28, 0).cuboid(-3.11F, -3.0F, -2.5F, 6.0F, 3.0F, 5.0F), ModelTransform.of(0.0F, -3.1F, 0.1F, -0.2618F, 0.0F, 0.0F));
        ModelPartData head = neck01.addChild("head", ModelPartBuilder.create().cuboid(-3.0F, -4.8F, -3.6F, 6.0F, 5.0F, 6.0F), ModelTransform.of(0.0F, -2.8F, 0.2F, -0.0524F, -0.2618F, 0.0F));
        head.addChild("snout", ModelPartBuilder.create().uv(78, 0).cuboid(-2.0F, -1.35F, -3.9F, 4.0F, 2.0F, 5.0F), ModelTransform.of(0.0F, -3.2F, -4.1F, 0.2793F, 0.0F, 0.0F));
        head.addChild("lowerJaw", ModelPartBuilder.create().uv(97, 10).cuboid(-2.0F, -0.4F, -3.6F, 4.0F, 1.0F, 4.0F), ModelTransform.of(0.0F, -0.5F, -3.9F, 0.0F, 0.0F, 0.0F));
        head.addChild("lEar", ModelPartBuilder.create().uv(20, 0).cuboid(-3.0F, -1.0F, -0.5F, 3.0F, 2.0F, 1.0F), ModelTransform.of(-2.3F, -3.4F, 0.9F, 0.1745F, 0.3491F, 0.4538F));
        head.addChild("rEar", ModelPartBuilder.create().uv(20, 0).cuboid(0.0F, -1.0F, -0.5F, 3.0F, 2.0F, 1.0F), ModelTransform.of(2.3F, -3.4F, 0.9F, 0.1745F, -0.3491F, -0.4538F));
        ModelPartData lAntler01 = head.addChild("lAntler01", ModelPartBuilder.create().uv(107, 0).cuboid(-1.0F, -2.3F, -1.0F, 2.0F, 3.0F, 2.0F), ModelTransform.of(-1.5F, -4.6F, -0.6F, -0.3491F, 0.0F, -0.2793F));
        ModelPartData lAntler02a = lAntler01.addChild("lAntler02a", ModelPartBuilder.create().uv(107, 0).cuboid(-0.8F, -0.5F, 0.0F, 1.0F, 1.0F, 6.0F), ModelTransform.of(0.2F, -1.7F, -0.3F, 0.7854F, -0.5236F, -0.1396F));
        ModelPartData lAntler02b = lAntler02a.addChild("lAntler02b", ModelPartBuilder.create().uv(107, 0).cuboid(-0.3F, -0.5F, 0.0F, 1.0F, 1.0F, 6.0F), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        ModelPartData lAntler03 = lAntler02b.addChild("lAntler03", ModelPartBuilder.create().uv(107, 0).cuboid(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 5.0F), ModelTransform.of(-0.2F, 0.0F, 5.4F, 0.6109F, 0.2618F, 0.0F));
        ModelPartData lAntler04 = lAntler03.addChild("lAntler04", ModelPartBuilder.create().uv(107, 0).cuboid(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 5.0F), ModelTransform.of(0.0F, 0.0F, 4.8F, -0.5236F, 0.0F, 0.0F));
        lAntler04.addChild("lAntler04b", ModelPartBuilder.create().uv(107, 0).cuboid(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 3.0F), ModelTransform.of(0.0F, 0.0F, 4.8F, 0.3491F, 0.2618F, 0.0F));
        ModelPartData lAntler07a = lAntler03.addChild("lAntler07a", ModelPartBuilder.create().uv(107, 0).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F), ModelTransform.of(0.0F, 0.1F, 1.0F, 0.8727F, 0.0F, 0.5934F));
        lAntler07a.addChild("lAntler07b", ModelPartBuilder.create().uv(107, 0).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F), ModelTransform.of(0.0F, 2.9F, 0.0F, 0.2793F, 0.0F, 0.0F));
        ModelPartData lAntler08a = lAntler03.addChild("lAntler08a", ModelPartBuilder.create().uv(107, 0).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F), ModelTransform.of(0.0F, 0.1F, 1.2F, 0.6981F, 0.0F, -0.3491F));
        lAntler08a.addChild("lAntler08b", ModelPartBuilder.create().uv(107, 0).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 5.0F, 1.0F), ModelTransform.of(0.0F, 2.9F, 0.0F, 0.2793F, 0.0F, -0.3491F));
        ModelPartData lAntler09a = lAntler03.addChild("lAntler09a", ModelPartBuilder.create().uv(107, 0).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 5.0F, 1.0F), ModelTransform.of(0.0F, -0.4F, -1.0F, 0.6109F, 0.1222F, -0.4363F));
        lAntler09a.addChild("lAntler09b", ModelPartBuilder.create().uv(107, 0).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F), ModelTransform.of(0.0F, 4.8F, 0.0F, 0.2793F, 0.0F, -0.3491F));
        ModelPartData lAntler05a = lAntler02a.addChild("lAntler05a", ModelPartBuilder.create().uv(107, 0).cuboid(-0.5F, -3.5F, -0.4F, 1.0F, 3.0F, 1.0F), ModelTransform.of(0.0F, 0.4F, 4.8F, -0.5236F, 0.0F, -0.3665F));
        ModelPartData lAntler05b = lAntler05a.addChild("lAntler05b", ModelPartBuilder.create().uv(107, 0).cuboid(-0.5F, -3.5F, -0.9F, 1.0F, 3.0F, 1.0F), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        ModelPartData lAntler05c = lAntler05b.addChild("lAntler05c", ModelPartBuilder.create().uv(107, 0).cuboid(-0.5F, -3.5F, -0.5F, 1.0F, 4.0F, 1.0F), ModelTransform.of(0.1F, -2.9F, -0.2F, 0.0F, 0.0F, 0.1745F));
        lAntler05c.addChild("lAntler05d", ModelPartBuilder.create().uv(107, 0).cuboid(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F), ModelTransform.of(-0.1F, -3.1F, 0.0F, 0.0F, 0.0F, 0.3491F));
        ModelPartData lAntler10a = lAntler02a.addChild("lAntler10a", ModelPartBuilder.create().uv(107, 0).cuboid(-0.5F, -3.5F, -0.5F, 1.0F, 4.0F, 1.0F), ModelTransform.of(-0.4F, -0.4F, 0.7F, 0.0F, 0.0F, -0.3665F));
        lAntler10a.addChild("lAntler10b", ModelPartBuilder.create().uv(107, 0).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F), ModelTransform.of(-0.1F, -3.1F, 0.0F, 0.0F, 0.0F, 0.3142F));
        ModelPartData lAntler11a = lAntler02a.addChild("lAntler11a", ModelPartBuilder.create().uv(107, 0).cuboid(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F), ModelTransform.of(-0.4F, 0.0F, -0.2F, 0.4363F, 0.0F, -0.4363F));
        lAntler11a.addChild("lAntler11b", ModelPartBuilder.create().uv(107, 0).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F), ModelTransform.of(-0.1F, -2.8F, 0.0F, 0.0F, 0.0F, 0.3142F));
        ModelPartData rAntler01 = head.addChild("rAntler01", ModelPartBuilder.create().uv(107, 0).cuboid(-1.0F, -2.2F, -1.0F, 2.0F, 3.0F, 2.0F), ModelTransform.of(1.5F, -4.7F, -0.6F, -0.3491F, 0.0F, 0.2793F));
        ModelPartData rAntler02a = rAntler01.addChild("rAntler02a", ModelPartBuilder.create().uv(107, 0).cuboid(-0.8F, -0.5F, 0.0F, 1.0F, 1.0F, 6.0F), ModelTransform.of(-0.2F, -1.7F, -0.3F, 0.7854F, 0.5236F, 0.1396F));
        ModelPartData rAntler02b = rAntler02a.addChild("rAntler02b", ModelPartBuilder.create().uv(107, 0).cuboid(-0.3F, -0.5F, 0.0F, 1.0F, 1.0F, 6.0F), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        ModelPartData rAntler03 = rAntler02b.addChild("rAntler03", ModelPartBuilder.create().uv(107, 0).cuboid(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 5.0F), ModelTransform.of(0.2F, 0.0F, 5.4F, 0.6109F, -0.2618F, 0.0F));
        ModelPartData rAntler04 = rAntler03.addChild("rAntler04", ModelPartBuilder.create().uv(107, 0).cuboid(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 5.0F), ModelTransform.of(0.0F, 0.0F, 4.8F, -0.5236F, 0.0F, 0.0F));
        rAntler04.addChild("rAntler04b", ModelPartBuilder.create().uv(107, 0).cuboid(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 3.0F), ModelTransform.of(0.0F, 0.0F, 4.8F, 0.3491F, -0.2618F, 0.0F));
        ModelPartData rAntler07a = rAntler03.addChild("rAntler07a", ModelPartBuilder.create().uv(107, 0).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F), ModelTransform.of(0.0F, 0.1F, 1.0F, 0.8727F, 0.0F, -0.5934F));
        rAntler07a.addChild("rAntler07b", ModelPartBuilder.create().uv(107, 0).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F), ModelTransform.of(0.0F, 2.9F, 0.0F, 0.2793F, 0.0F, 0.0F));
        ModelPartData rAntler08a = rAntler03.addChild("rAntler08a", ModelPartBuilder.create().uv(107, 0).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F), ModelTransform.of(0.0F, 0.1F, 1.2F, 0.6981F, 0.0F, 0.3491F));
        rAntler08a.addChild("rAntler08b", ModelPartBuilder.create().uv(107, 0).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 5.0F, 1.0F), ModelTransform.of(0.0F, 2.9F, 0.0F, 0.2793F, 0.0F, 0.3491F));
        ModelPartData rAntler09a = rAntler03.addChild("rAntler09a", ModelPartBuilder.create().uv(107, 0).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 5.0F, 1.0F), ModelTransform.of(0.0F, -0.4F, -1.0F, 0.6109F, -0.1222F, 0.4363F));
        rAntler09a.addChild("rAntler09b", ModelPartBuilder.create().uv(107, 0).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F), ModelTransform.of(0.0F, 4.8F, 0.0F, 0.2793F, 0.0F, 0.3491F));
        ModelPartData rAntler05a = rAntler02a.addChild("rAntler05a", ModelPartBuilder.create().uv(107, 0).cuboid(-0.5F, -3.5F, -0.4F, 1.0F, 3.0F, 1.0F), ModelTransform.of(0.0F, 0.4F, 4.8F, -0.5236F, 0.0F, 0.3665F));
        ModelPartData rAntler05b = rAntler05a.addChild("rAntler05b", ModelPartBuilder.create().uv(107, 0).cuboid(-0.5F, -3.5F, -0.9F, 1.0F, 3.0F, 1.0F), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        ModelPartData rAntler05c = rAntler05b.addChild("rAntler05c", ModelPartBuilder.create().uv(107, 0).cuboid(-0.5F, -3.5F, -0.5F, 1.0F, 4.0F, 1.0F), ModelTransform.of(-0.1F, -2.9F, -0.2F, 0.0F, 0.0F, -0.1745F));
        rAntler05c.addChild("rAntler05d", ModelPartBuilder.create().uv(107, 0).cuboid(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F), ModelTransform.of(0.1F, -3.1F, 0.0F, 0.0F, 0.0F, -0.3491F));
        ModelPartData rAntler10a = rAntler02a.addChild("rAntler10a", ModelPartBuilder.create().uv(107, 0).cuboid(-0.5F, -3.5F, -0.5F, 1.0F, 4.0F, 1.0F), ModelTransform.of(0.4F, -0.4F, 0.7F, 0.0F, 0.0F, 0.3665F));
        rAntler10a.addChild("rAntler10b", ModelPartBuilder.create().uv(107, 0).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F), ModelTransform.of(0.1F, -3.1F, 0.0F, 0.0F, 0.0F, -0.3142F));
        ModelPartData rAntler11a = rAntler02a.addChild("rAntler11a", ModelPartBuilder.create().uv(107, 0).cuboid(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F), ModelTransform.of(0.4F, 0.0F, -0.2F, 0.4363F, 0.0F, 0.4363F));
        rAntler11a.addChild("rAntler11b", ModelPartBuilder.create().uv(107, 0).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F), ModelTransform.of(-0.1F, -2.8F, 0.0F, 0.0F, 0.0F, -0.3142F));
        head.addChild("upperJaw00", ModelPartBuilder.create().uv(76, 8).cuboid(-2.5F, -0.7F, -3.7F, 5.0F, 2.0F, 5.0F), ModelTransform.of(0.0F, -2.0F, -4.1F, 0.0F, 0.0F, 0.0F));
        neck01.addChild("neckFur00", ModelPartBuilder.create().uv(78, 17).cuboid(-2.5F, 0.0F, -1.3F, 5.0F, 5.0F, 2.0F), ModelTransform.of(0.0F, -2.4F, -1.4F, -0.7679F, 0.0F, 0.0F));
        neck00.addChild("neckFur01", ModelPartBuilder.create().uv(93, 16).cuboid(-3.0F, 0.0F, -1.3F, 6.0F, 6.0F, 2.0F), ModelTransform.of(0.0F, -4.6F, -1.3F, -0.7854F, 0.0F, 0.0F));
        neck00.addChild("neckFur02", ModelPartBuilder.create().uv(110, 16).cuboid(-3.0F, 0.0F, -1.3F, 6.0F, 8.0F, 2.0F), ModelTransform.of(0.0F, -2.5F, -1.8F, -0.6981F, 0.0F, 0.0F));
        chest.addChild("mMantle", ModelPartBuilder.create().uv(75, 26).cuboid(-6.0F, 0.0F, -4.5F, 12.0F, 10.0F, 9.0F), ModelTransform.of(0.0F, -8.8F, -0.5F, 0.0F, 0.0F, 0.0F));
        ModelPartData spear01a = root.addChild("spear01a", ModelPartBuilder.create().uv(0, 65).cuboid(-0.2F, -8.0F, -0.5F, 1.0F, 32.0F, 1.0F), ModelTransform.of(-4.3F, -15.0F, -14.5F, 0.2618F, 0.0F, -0.6981F));
        spear01a.addChild("spear01b", ModelPartBuilder.create().uv(0, 65).cuboid(-0.8F, -8.0F, -0.5F, 1.0F, 32.0F, 1.0F), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        ModelPartData spear02 = spear01a.addChild("spear02", ModelPartBuilder.create().uv(0, 65).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 8.0F, 1.0F), ModelTransform.of(0.0F, 23.8F, 0.0F, 0.0F, 0.0F, 0.0F));
        spear02.addChild("spearTip03a", ModelPartBuilder.create().uv(6, 71).cuboid(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F), ModelTransform.of(0.0F, 10.3F, 0.0F, 0.0F, 0.0F, 0.7854F));
        spear02.addChild("spearTip02", ModelPartBuilder.create().uv(7, 65).cuboid(-1.0F, -0.7F, -0.5F, 2.0F, 2.0F, 1.0F), ModelTransform.of(0.0F, 8.5F, 0.0F, 0.0F, 0.0F, 0.0F));
        ModelPartData plith01 = root.addChild("plith01", ModelPartBuilder.create().uv(60, 84).cuboid(-8.5F, 0.0F, -8.5F, 17.0F, 5.0F, 17.0F), ModelTransform.of(0.0F, 19.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        plith01.addChild("plith02", ModelPartBuilder.create().uv(68, 64).cuboid(-7.5F, -2.7F, -7.5F, 15.0F, 3.0F, 15.0F), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(data, 128,128);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        chest.render(matrices, vertices, light, overlay);
        plith01.render(matrices, vertices, light, overlay);
    }
}
