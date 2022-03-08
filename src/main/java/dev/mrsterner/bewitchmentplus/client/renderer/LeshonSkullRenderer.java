package dev.mrsterner.bewitchmentplus.client.renderer;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.client.model.GobletItemModel;
import dev.mrsterner.bewitchmentplus.client.model.LeshonSkullModel;
import dev.mrsterner.bewitchmentplus.common.item.GobletBlockItem;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;

public class LeshonSkullRenderer implements  BuiltinItemRendererRegistry.DynamicItemRenderer {

    public LeshonSkullModel leshonSkullModel;

    public LeshonSkullRenderer() {
        leshonSkullModel = new LeshonSkullModel(LeshonSkullModel.getTexturedModelData().createModel());
    }

    @Override
    public void render(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180F));
        VertexConsumer ivertexbuilder1 = ItemRenderer.getItemGlintConsumer(vertexConsumers, this.leshonSkullModel.getLayer(new Identifier(BewitchmentPlus.MODID, "textures/item/skull.png")), false, stack.hasGlint());
        leshonSkullModel.render(matrices, ivertexbuilder1, light, overlay, 1, 1, 1, 1);
        matrices.pop();
    }
}
