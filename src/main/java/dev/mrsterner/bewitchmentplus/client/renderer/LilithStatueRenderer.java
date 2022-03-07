package dev.mrsterner.bewitchmentplus.client.renderer;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.client.model.GobletItemModel;
import dev.mrsterner.bewitchmentplus.client.model.statue.LilithStatueModel;
import dev.mrsterner.bewitchmentplus.common.block.LilithStatueBlock;
import dev.mrsterner.bewitchmentplus.common.block.blockentity.GobletBlockEntity;
import dev.mrsterner.bewitchmentplus.common.block.blockentity.StatueBlockEntity;
import dev.mrsterner.bewitchmentplus.common.item.GobletBlockItem;
import dev.mrsterner.bewitchmentplus.common.item.StatueBlockItem;
import dev.mrsterner.bewitchmentplus.common.registry.BWPObjects;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;

public class LilithStatueRenderer implements BlockEntityRenderer<StatueBlockEntity>, BuiltinItemRendererRegistry.DynamicItemRenderer{
    public LilithStatueModel lilithStatueModel;

    public LilithStatueRenderer() {
        lilithStatueModel = new LilithStatueModel(LilithStatueModel.create().createModel());
    }

    public Identifier getGobletTexture(StatueBlockItem itemStack) {
        return new Identifier(BewitchmentPlus.MODID,  "textures/block/lilith_statue.png");
    }

    @Override
    public void render(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        VertexConsumer ivertexbuilder1 = ItemRenderer.getItemGlintConsumer(vertexConsumers, this.lilithStatueModel.getLayer(getGobletTexture((StatueBlockItem) stack.getItem())), false, stack.hasGlint());
        lilithStatueModel.render(matrices, ivertexbuilder1, light, overlay, 1, 1, 1, 1);
        matrices.pop();
    }

    @Override
    public void render(StatueBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        VertexConsumer ivertexbuilder1 = ItemRenderer.getItemGlintConsumer(vertexConsumers, this.lilithStatueModel.getLayer(getGobletTexture(entity.getStatue())), false, false);
        matrices.translate(0.5, 1.5, 0.5);
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180));
        lilithStatueModel.render(matrices, ivertexbuilder1, light, overlay, 1, 1, 1, 1);
        matrices.pop();
    }
}
