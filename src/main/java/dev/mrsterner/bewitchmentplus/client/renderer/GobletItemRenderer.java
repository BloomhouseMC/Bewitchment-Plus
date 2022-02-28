package dev.mrsterner.bewitchmentplus.client.renderer;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.client.model.GobletItemLiquidModel;
import dev.mrsterner.bewitchmentplus.client.model.GobletItemModel;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;

public class GobletItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {
   public GobletItemModel gobletItemModel;
   public GobletItemLiquidModel liquidModel;
   public static Identifier TEXTURE = new Identifier(BewitchmentPlus.MODID, "textures/block/silver_goblet.png");
   public static Identifier TEXTURE2 = new Identifier(BewitchmentPlus.MODID, "textures/block/liquid_blood.png");

   public GobletItemRenderer(){
       gobletItemModel = new GobletItemModel(GobletItemModel.getTexturedModelData().createModel());
       liquidModel = new GobletItemLiquidModel(GobletItemLiquidModel.getTexturedModelData().createModel());
   }

    @Override
    public void render(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        matrices.translate(0.5F,1.7,0.5F);
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180));
        VertexConsumer ivertexbuilder1 = ItemRenderer.getItemGlintConsumer(vertexConsumers, this.gobletItemModel.getLayer(TEXTURE), false, stack.hasGlint());
        gobletItemModel.render(matrices, ivertexbuilder1, light,overlay,1,1,1,1);
        matrices.pop();

        matrices.push();
        matrices.translate(0.5F,1.7,0.5F);
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180));
        VertexConsumer ivertexbuilder2 = ItemRenderer.getItemGlintConsumer(vertexConsumers, this.liquidModel.getLayer(TEXTURE2), false, stack.hasGlint());
        liquidModel.render(matrices, ivertexbuilder2, light,overlay,1,1,1,1);
        matrices.pop();
    }
}
