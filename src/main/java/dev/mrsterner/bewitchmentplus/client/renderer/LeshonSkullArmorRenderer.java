package dev.mrsterner.bewitchmentplus.client.renderer;

import dev.mrsterner.bewitchmentplus.client.model.LeshonSkullModel;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class LeshonSkullArmorRenderer implements ArmorRenderer {
    private static LeshonSkullModel<LivingEntity> armorModel;
    private final Identifier texture;
    private final Item hatItem;

    public LeshonSkullArmorRenderer(Identifier texture, @Nullable Item hatItem) {
        this.texture = texture;
        this.hatItem = hatItem;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {
        if (armorModel == null) {
            armorModel = new LeshonSkullModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(LeshonSkullModel.LAYER_LOCATION));
        }else{
            contextModel.setAttributes(armorModel);
            armorModel.setVisible(false);
            armorModel.head.visible = slot == EquipmentSlot.HEAD;
            ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, armorModel, texture);
        }
    }
}
