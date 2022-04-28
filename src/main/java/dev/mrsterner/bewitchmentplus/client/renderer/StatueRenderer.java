package dev.mrsterner.bewitchmentplus.client.renderer;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.client.model.statue.BaphometStatueModel;
import dev.mrsterner.bewitchmentplus.client.model.statue.HerneStatueModel;
import dev.mrsterner.bewitchmentplus.client.model.statue.LeonardStatueModel;
import dev.mrsterner.bewitchmentplus.client.model.statue.LilithStatueModel;
import dev.mrsterner.bewitchmentplus.common.block.blockentity.StatueBlockEntity;
import dev.mrsterner.bewitchmentplus.common.item.StatueBlockItem;
import dev.mrsterner.bewitchmentplus.common.registry.BWPObjects;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;


public class StatueRenderer implements BlockEntityRenderer<StatueBlockEntity>, BuiltinItemRendererRegistry.DynamicItemRenderer{
    public LilithStatueModel lilithStatueModel;
    public HerneStatueModel herneStatueModel;
    public LeonardStatueModel leonardStatueModel;
    public BaphometStatueModel baphometStatueModel;

    public StatueRenderer() {
        lilithStatueModel = new LilithStatueModel(LilithStatueModel.create().createModel());
        herneStatueModel = new HerneStatueModel(HerneStatueModel.create().createModel());
        leonardStatueModel = new LeonardStatueModel(LeonardStatueModel.create().createModel());
        baphometStatueModel = new BaphometStatueModel(BaphometStatueModel.create().createModel());
    }

    public Identifier getStatueTexture(StatueBlockItem itemStack) {
        String string = Registry.ITEM.getKey(itemStack.asItem()).get().getValue().getPath();
        return new Identifier(BewitchmentPlus.MODID,  "textures/block/statues/"+ string +".png");
    }

    @Override
    public void render(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180));
        String string = Registry.ITEM.getKey(stack.getItem()).get().getValue().getPath();
        if(string.contains("lilith")){
            VertexConsumer ivertexbuilder1 = ItemRenderer.getItemGlintConsumer(vertexConsumers, this.lilithStatueModel.getLayer(getStatueTexture((StatueBlockItem) stack.getItem())), false, stack.hasGlint());
            lilithStatueModel.render(matrices, ivertexbuilder1, light, overlay, 1, 1, 1, 1);
        }else if(string.contains("herne")){
            VertexConsumer ivertexbuilder1 = ItemRenderer.getItemGlintConsumer(vertexConsumers, this.herneStatueModel.getLayer(getStatueTexture((StatueBlockItem) stack.getItem())), false, stack.hasGlint());
            herneStatueModel.render(matrices, ivertexbuilder1, light, overlay, 1, 1, 1, 1);
        }else if(string.contains("leonard")){
            VertexConsumer ivertexbuilder1 = ItemRenderer.getItemGlintConsumer(vertexConsumers, this.leonardStatueModel.getLayer(getStatueTexture((StatueBlockItem) stack.getItem())), false, stack.hasGlint());
            leonardStatueModel.render(matrices, ivertexbuilder1, light, overlay, 1, 1, 1, 1);
        }else if(string.contains("baphomet")){
            VertexConsumer ivertexbuilder1 = ItemRenderer.getItemGlintConsumer(vertexConsumers, this.baphometStatueModel.getLayer(getStatueTexture((StatueBlockItem) stack.getItem())), false, stack.hasGlint());
            baphometStatueModel.render(matrices, ivertexbuilder1, light, overlay, 1, 1, 1, 1);
        }
        matrices.pop();
    }

    @Override
    public void render(StatueBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        World world = entity.getWorld();
        boolean bl = world != null;
        BlockState blockState = bl ? entity.getCachedState() : BWPObjects.LILITH_STATUE_BLACKSTONE.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.SOUTH);
        matrices.push();
        matrices.translate(0.5, 1.5, 0.5);
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180));
        float f = blockState.get(Properties.HORIZONTAL_FACING).asRotation();
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(f));
        String s = Registry.BLOCK.getKey(entity.getStatue().getBlock()).get().getValue().getPath();
        if(s.contains("lilith")){
            VertexConsumer ivertexbuilder1 = ItemRenderer.getItemGlintConsumer(vertexConsumers, this.lilithStatueModel.getLayer(getStatueTexture(entity.getStatue())), false, false);
            lilithStatueModel.render(matrices, ivertexbuilder1, light, overlay, 1, 1, 1, 1);
        }else if(s.contains("herne")){
            VertexConsumer ivertexbuilder1 = ItemRenderer.getItemGlintConsumer(vertexConsumers, this.herneStatueModel.getLayer(getStatueTexture(entity.getStatue())), false, false);
            herneStatueModel.render(matrices, ivertexbuilder1, light, overlay, 1, 1, 1, 1);
        }else if(s.contains("leonard")){
            VertexConsumer ivertexbuilder1 = ItemRenderer.getItemGlintConsumer(vertexConsumers, this.leonardStatueModel.getLayer(getStatueTexture(entity.getStatue())), false, false);
            leonardStatueModel.render(matrices, ivertexbuilder1, light, overlay, 1, 1, 1, 1);
            //System.out.println(getStatueTexture(entity.getStatue()));
        }else if(s.contains("baphomet")){
            VertexConsumer ivertexbuilder1 = ItemRenderer.getItemGlintConsumer(vertexConsumers, this.baphometStatueModel.getLayer(getStatueTexture(entity.getStatue())), false, false);
            baphometStatueModel.render(matrices, ivertexbuilder1, light, overlay, 1, 1, 1, 1);
        }
        matrices.pop();
    }
}
