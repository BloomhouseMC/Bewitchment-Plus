package dev.mrsterner.bewitchmentplus.client.renderer;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.common.block.MimicChestBlock;
import dev.mrsterner.bewitchmentplus.common.block.blockentity.GobletBlockEntity;
import dev.mrsterner.bewitchmentplus.common.block.blockentity.MimicChestBlockEntity;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.block.ChestAnimationProgress;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.ChestBlockEntityRenderer;
import net.minecraft.client.render.block.entity.LightmapCoordinatesRetriever;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;

import static net.minecraft.screen.PlayerScreenHandler.BLOCK_ATLAS_TEXTURE;

@Environment(value= EnvType.CLIENT)
public class MimicBlockEntityRenderer<T extends BlockEntity> implements BlockEntityRenderer<T> {
    public static final SpriteIdentifier MIMIC_SPRITE = new SpriteIdentifier(BLOCK_ATLAS_TEXTURE, new Identifier(BewitchmentPlus.MODID, "block/mimic"));
    public static final EntityModelLayer MIMIC_LAYER = new EntityModelLayer(new Identifier(BewitchmentPlus.MODID, "mimic"), "main");
    private final ModelPart chest;
    private final ModelPart lid;
    private final ModelPart tounge;
    private final ModelPart tounge2;
    private final ModelPart tounge3;
    private final ModelPart tounge4;
    private final ModelPart tounge5;
    public MimicBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        ModelPart modelPart = ctx.getLayerModelPart(MIMIC_LAYER);
        this.chest = modelPart.getChild("chest");
        this.lid = modelPart.getChild("lid");
        this.tounge = modelPart.getChild("tounge");
        this.tounge2 = tounge.getChild("tounge2");
        this.tounge3 = tounge2.getChild("tounge3");
        this.tounge4 = tounge3.getChild("tounge4");
        this.tounge5 = tounge4.getChild("tounge5");

    }

    @Override
    public void render(T entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        World world = entity.getWorld();
        boolean bl = world != null;
        BlockState blockState = bl ? entity.getCachedState() : Blocks.CHEST.getDefaultState().with(ChestBlock.FACING, Direction.SOUTH);
        Block block = blockState.getBlock();
        if (!(block instanceof AbstractChestBlock abstractChestBlock)) {
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
        DoubleBlockProperties.PropertySource<Object> propertySource = bl ? abstractChestBlock.getBlockEntitySource(blockState, world, entity.getPos(), true) : DoubleBlockProperties.PropertyRetriever::getFallback;
        var g = MimicChestBlock.getAnimationProgressRetriever((ChestAnimationProgress)entity).getFallback().get(tickDelta);

        g = 1.0f - g;
        g = 1.0f - g * g * g;
        int i = ((Int2IntFunction)propertySource.apply(new LightmapCoordinatesRetriever())).applyAsInt(light);
        VertexConsumer vertexConsumer = MIMIC_SPRITE.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);
        this.render(matrices, vertexConsumer, this.chest, this.lid, this.tounge, this.tounge2, this.tounge3, this.tounge4, this.tounge5, g, i, overlay);
        matrices.pop();
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData chest = root.addChild("chest", ModelPartBuilder.create().uv(0, 0).cuboid(-7.0F, -10.0F, -7.0F, 14.0F, 10.0F, 14.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        ModelPartData teethLower = chest.addChild("teethLower", ModelPartBuilder.create().uv(42, 5).cuboid(-5.4F, -12.0F, -6.0F, 11.0F, 3.0F, 0.0F, new Dilation(0.0F)).uv(30, 45).cuboid(6.0F, -12.0F, -6.0F, 0.0F, 2.0F, 12.0F, new Dilation(0.0F)).uv(0, 46).cuboid(-6.0F, -12.0F, -6.0F, 0.0F, 2.0F, 12.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData lid = root.addChild("lid", ModelPartBuilder.create().uv(0, 24).cuboid(-7.0F, -4.0F, -14.0F, 14.0F, 5.0F, 14.0F, new Dilation(-0.001F)).uv(8, 28).cuboid(-1.0F, -1.0F, -15.0F, 2.0F, 4.0F, 1.0F, new Dilation(-0.001F)), ModelTransform.of(0.0F, 14.0F, 7.0F, -0.9163F, 0.0F, 0.0F));
        ModelPartData eye = lid.addChild("eye", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)).uv(68, 13).cuboid(-2.5F, 0.0F, -2.5F, 5.0F, 5.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -10.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData teethUpper = lid.addChild("teethUpper", ModelPartBuilder.create().uv(27, 33).cuboid(6.1F, 1.0F, -13.0F, 0.0F, 2.0F, 11.0F, new Dilation(-0.001F)).uv(0, 33).cuboid(-6.1F, 1.0F, -13.0F, 0.0F, 2.0F, 11.0F, new Dilation(-0.001F)).uv(42, 0).cuboid(-5.4F, 1.0F, -13.1F, 11.0F, 3.0F, 0.0F, new Dilation(-0.001F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData tounge = root.addChild("tounge", ModelPartBuilder.create().uv(1, 62).cuboid(-5.0F, -4.0F, 0.0F, 4.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(3.0F, 15.0F, 2.0F, 1.0908F, 0.0F, 0.0F));
        ModelPartData tounge2 = tounge.addChild("tounge2", ModelPartBuilder.create().uv(1, 67).cuboid(-2.0F, -4.0F, -1.0F, 4.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-3.0F, -4.0F, 1.0F, 0.6981F, 0.0F, 0.0F));
        ModelPartData tounge3 = tounge2.addChild("tounge3", ModelPartBuilder.create().uv(1, 72).cuboid(-2.0F, -3.0F, -1.0F, 4.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -4.0F, 0.0F, 0.6981F, 0.0F, 0.0F));
        ModelPartData tounge4 = tounge3.addChild("tounge4", ModelPartBuilder.create().uv(1, 76).cuboid(-2.0F, -3.0F, -1.0F, 4.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -3.0F, 0.0F, 0.6981F, 0.0F, 0.0F));
        ModelPartData tounge5 = tounge4.addChild("tounge5", ModelPartBuilder.create().uv(1, 79).cuboid(-0.5F, -3.0F, -1.0F, 3.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, -3.0F, 0.0F, 0.6981F, 0.0F, 0.0F)); return TexturedModelData.of(data, 128, 128);
    }


    public float degToRad(float f){
        return f * (float) Math.PI / 180F;
    }

    public void render(MatrixStack matrices, VertexConsumer vertices, ModelPart base,ModelPart lid, ModelPart tounge, ModelPart tounge2, ModelPart tounge3, ModelPart tounge4, ModelPart tounge5, float openFactor, int light, int overlay) {
        lid.pitch = -(openFactor);
        tounge.pitch = degToRad(62.5F) - openFactor / 2.5F;
        tounge2.pitch =  degToRad(40)- openFactor / 2.5F;
        tounge3.pitch = degToRad(40)- openFactor / 2.5F;
        tounge4.pitch = degToRad(40)- openFactor / 2.5F;
        tounge5.pitch = degToRad(40)- openFactor / 2.5F;
        lid.render(matrices, vertices, light, overlay);
        tounge.render(matrices, vertices, light, overlay);
        base.render(matrices, vertices, light, overlay);
    }
}
