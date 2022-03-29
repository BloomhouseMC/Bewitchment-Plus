package dev.mrsterner.bewitchmentplus.client.renderer;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.BewitchmentPlusClient;
import dev.mrsterner.bewitchmentplus.common.block.MimicChestBlock;
import dev.mrsterner.bewitchmentplus.common.block.blockentity.MimicChestBlockEntity;
import dev.mrsterner.bewitchmentplus.common.registry.BWPSpriteIdentifiers;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.block.ChestAnimationProgress;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.*;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;

@Environment(value= EnvType.CLIENT)
public class MimicBlockEntityRenderer<T extends BlockEntity> implements BlockEntityRenderer<T> {

    public static final EntityModelLayer MIMIC_LAYER = new EntityModelLayer(new Identifier(BewitchmentPlus.MODID, "mimic"), "main");
    private final ModelPart chest;
    private final ModelPart lid;
    private final ModelPart tounge;
    private final ModelPart tounge2;
    private final ModelPart tounge3;
    private final ModelPart tounge4;
    private final ModelPart tounge5;
    private final ModelPart eye;


    public MimicBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        ModelPart modelPart = ctx.getLayerModelPart(MIMIC_LAYER);
        this.chest = modelPart.getChild("chest");
        this.lid = modelPart.getChild("lid");
        this.tounge = modelPart.getChild("tounge");
        this.tounge2 = tounge.getChild("tounge2");
        this.tounge3 = tounge2.getChild("tounge3");
        this.tounge4 = tounge3.getChild("tounge4");
        this.tounge5 = tounge4.getChild("tounge5");
        this.eye = modelPart.getChild("eye");
    }

    @Override
    public void render(T entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        World world = entity.getWorld();
        boolean bl = world != null;
        BlockState blockState = bl ? entity.getCachedState() : Blocks.CHEST.getDefaultState().with(ChestBlock.FACING, Direction.SOUTH);
        Block block = blockState.getBlock();
        if (!(block instanceof AbstractChestBlock abstractChestBlock) || !bl) {
            return;
        }
        matrices.push();
        float f = blockState.get(ChestBlock.FACING).asRotation();
        matrices.translate(0.5, 0.5, 0.5);
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-f));
        matrices.translate(-0.5, -0.5, -0.5);
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180));
        matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(180));
        matrices.translate(0.5,-1.5,-0.5);
        DoubleBlockProperties.PropertySource<Object> propertySource = (DoubleBlockProperties.PropertySource<Object>) abstractChestBlock.getBlockEntitySource(blockState, world, entity.getPos(), true);
        var g = MimicChestBlock.getAnimationProgressRetriever((ChestAnimationProgress)entity).getFallback().get(tickDelta);
        if(!world.isClient())return;
        g = 1.0f - g;
        g = 1.0f - g * g * g;
        float h;
        float e = 0;
        MimicChestBlockEntity mimicChestBlockEntity = (MimicChestBlockEntity) world.getBlockEntity(entity.getPos());
        if(mimicChestBlockEntity != null){
            for(h = mimicChestBlockEntity.floppity - mimicChestBlockEntity.eyeRotation; h >= 3.1415927F; h -= 6.2831855F) {
            }
            while(h < -3.1415927F) {
                h += 6.2831855F;
            }
            e = mimicChestBlockEntity.eyeRotation + h * mimicChestBlockEntity.partial;
        }
        int i = ((Int2IntFunction)propertySource.apply(new LightmapCoordinatesRetriever())).applyAsInt(light);
        VertexConsumer vertexConsumer = BWPSpriteIdentifiers.MIMIC_SPRITE.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);
        this.render(matrices, vertexConsumer, this.chest, this.lid, this.tounge, this.tounge2, this.tounge3, this.tounge4, this.tounge5, this.eye, e, g, i, overlay, blockState, tickDelta);
        matrices.pop();
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();
        ModelPartData chest = root.addChild("chest", ModelPartBuilder.create().uv(0, 0).cuboid(-7.0F, -10.0F, -7.0F, 14.0F, 10.0F, 14.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        chest.addChild("teethLower", ModelPartBuilder.create().uv(42, 24).cuboid(-5.4F, -12.0F, -6.0F, 11.0F, 3.0F, 0.0F, new Dilation(0.0F)).uv(24, 31).cuboid(6.0F, -12.0F, -6.0F, 0.0F, 2.0F, 12.0F, new Dilation(0.0F)).uv(0, 31).cuboid(-6.0F, -12.0F, -6.0F, 0.0F, 2.0F, 12.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData lid = root.addChild("lid", ModelPartBuilder.create().uv(0, 24).cuboid(-7.0F, -4.0F, -14.0F, 14.0F, 5.0F, 14.0F, new Dilation(-0.001F)).uv(8, 28).cuboid(-1.0F, -1.0F, -15.0F, 2.0F, 4.0F, 1.0F, new Dilation(-0.001F)), ModelTransform.pivot(0.0F, 14.0F, 7.0F));
        lid.addChild("teethUpper", ModelPartBuilder.create().uv(22, 34).cuboid(6.1F, 1.0F, -13.0F, 0.0F, 2.0F, 11.0F, new Dilation(-0.001F)).uv(0, 34).cuboid(-6.1F, 1.0F, -13.0F, 0.0F, 2.0F, 11.0F, new Dilation(-0.001F)).uv(42, 10).cuboid(-5.4F, 1.0F, -13.1F, 11.0F, 3.0F, 0.0F, new Dilation(-0.001F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData tounge = root.addChild("tounge", ModelPartBuilder.create().uv(0, 5).cuboid(-5.0F, -4.0F, 0.0F, 4.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(3.0F, 15.0F, 2.0F, 1.0908F, 0.0F, 0.0F));
        ModelPartData tounge2 = tounge.addChild("tounge2", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -4.0F, -1.0F, 4.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-3.0F, -4.0F, 1.0F, 0.6981F, 0.0F, 0.0F));
        ModelPartData tounge3 = tounge2.addChild("tounge3", ModelPartBuilder.create().uv(0, 24).cuboid(-2.0F, -3.0F, -1.0F, 4.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -4.0F, 0.0F, 0.6981F, 0.0F, 0.0F));
        ModelPartData tounge4 = tounge3.addChild("tounge4", ModelPartBuilder.create().uv(0, 10).cuboid(-2.0F, -3.0F, -1.0F, 4.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -3.0F, 0.0F, 0.6981F, 0.0F, 0.0F));
        tounge4.addChild("tounge5", ModelPartBuilder.create().uv(0, 28).cuboid(-0.5F, -3.0F, -1.0F, 3.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, -3.0F, 0.0F, 0.6981F, 0.0F, 0.0F));
        root.addChild("eye", ModelPartBuilder.create().uv(42, 0).cuboid(-2.5F, -1.25F, -2.5F, 5.0F, 5.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 13.25F, 0.0F, 0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(data, 64, 64);
    }


    public float degToRad(float f){
        return f * (float) Math.PI / 180F;
    }

    public void render(MatrixStack matrices, VertexConsumer vertices, ModelPart base,ModelPart lid, ModelPart tounge, ModelPart tounge2, ModelPart tounge3, ModelPart tounge4, ModelPart tounge5, ModelPart eye, float eyeturn, float openFactor, int light, int overlay, BlockState blockState, float tickDelta) {
        double ticks = (BewitchmentPlusClient.ClientTickHandler.ticksInGame + tickDelta) * 0.5;
        lid.pitch = -(openFactor);
        tounge.pitch = degToRad(62.5F) - openFactor / 3F;
        tounge2.pitch =  degToRad(40) - openFactor / 3F;
        tounge3.pitch = degToRad(40) - openFactor / 3F;
        tounge4.pitch = degToRad(40) - openFactor / 3F;
        tounge5.pitch = degToRad(40) - openFactor / 3F;
        eye.yaw = eyeturn;

        matrices.push();
        matrices.translate(0, -openFactor/2.5 + Math.sin(ticks/20)/20, 0);
        float f = blockState.get(ChestBlock.FACING).asRotation();
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion( - 90 - f));
        eye.render(matrices, vertices, light, overlay);
        matrices.pop();

        lid.render(matrices, vertices, light, overlay);
        tounge.render(matrices, vertices, light, overlay);
        base.render(matrices, vertices, light, overlay);
    }
}
