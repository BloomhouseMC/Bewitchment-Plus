package dev.mrsterner.bewitchmentplus.client.renderer;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.client.model.GobletItemModel;
import dev.mrsterner.bewitchmentplus.common.block.blockentity.GobletBlockEntity;
import dev.mrsterner.bewitchmentplus.common.item.GobletBlockItem;
import dev.mrsterner.bewitchmentplus.common.registry.BWPObjects;
import dev.mrsterner.bewitchmentplus.common.utils.RenderHelper;
import moriyashiine.bewitchment.common.registry.BWObjects;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.renderer.v1.mesh.MeshBuilder;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.impl.client.indigo.renderer.helper.ColorHelper;
import net.fabricmc.fabric.impl.renderer.RendererAccessImpl;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3f;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.screen.PlayerScreenHandler.BLOCK_ATLAS_TEXTURE;

public class GobletBlockItemRenderer implements BlockEntityRenderer<GobletBlockEntity>, BuiltinItemRendererRegistry.DynamicItemRenderer {
    public static final SpriteIdentifier BLOOD = new SpriteIdentifier(BLOCK_ATLAS_TEXTURE, new Identifier(BewitchmentPlus.MODID, "block/goblet_fluid"));
    public static final SpriteIdentifier HONEY = new SpriteIdentifier(BLOCK_ATLAS_TEXTURE, new Identifier(BewitchmentPlus.MODID, "block/honey_fluid"));
    public static final SpriteIdentifier UNICORN = new SpriteIdentifier(BLOCK_ATLAS_TEXTURE, new Identifier(BewitchmentPlus.MODID, "block/goblet_fluid_unicorn"));
    private static final float EDGE_SIZE = 1f / 16f;
    private static final float INNER_SIZE = 1f - (EDGE_SIZE * 2f);
    public GobletItemModel gobletItemModel;

    public GobletBlockItemRenderer() {
        gobletItemModel = new GobletItemModel(GobletItemModel.getTexturedModelData().createModel());
    }

    public Identifier getGobletTexture(GobletBlockItem itemStack) {
        return new Identifier(BewitchmentPlus.MODID, BWPObjects.SILVER_GOBLET.asItem() == itemStack.asItem() ? "textures/block/silver_goblet.png" : BWPObjects.GOLD_GOBLET.asItem() == itemStack.asItem() ? "textures/block/gold_goblet.png" : "textures/block/netherite_goblet.png");
    }

    @Override
    public void render(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        matrices.translate(0F, 1.2F, 0F);
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180));
        VertexConsumer vertexConsumer = ItemRenderer.getItemGlintConsumer(vertexConsumers, this.gobletItemModel.getLayer(getGobletTexture((GobletBlockItem) stack.getItem())), false, stack.hasGlint());
        gobletItemModel.render(matrices, vertexConsumer, light, overlay, 1, 1, 1, 1);
        matrices.pop();
        if (stack.hasNbt()) {
            var nbt = stack.getNbt();
            if (nbt.contains("BlockEntityTag")) {
                matrices.push();
                matrices.scale(0.25f, 0.25f, 0.25f);
                matrices.translate(-0.5F, 0.75F, 0.5F);
                matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180));
                renderFluid(matrices, vertexConsumers, light, overlay, null, stack);
                matrices.pop();
            }
        }
    }

    @Override
    public void render(GobletBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (!entity.getStack(0).isEmpty()) {
            matrices.push();
            matrices.scale(0.25f, 0.25f, 0.25f);
            matrices.translate(1.5, 0.9, 1.5);
            renderFluid(matrices, vertexConsumers, light, overlay, entity, null);
            matrices.pop();
        }
        VertexConsumer vertexConsumer = ItemRenderer.getItemGlintConsumer(vertexConsumers, this.gobletItemModel.getLayer(getGobletTexture(entity.getGoblet())), false, false);
        matrices.translate(0.5, 1.5, 0.5);
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180));
        gobletItemModel.render(matrices, vertexConsumer, light, overlay, 1, 1, 1, 1);
    }

    @SuppressWarnings("ALL")
    private void renderFluid(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, @Nullable GobletBlockEntity entity, @Nullable ItemStack itemStack) {
        matrices.push();
        FluidVariant variant = FluidVariant.of(Fluids.WATER);
        Sprite sprite = FluidVariantRendering.getHandlerOrDefault(variant.getFluid()).getSprites(variant)[0];
        MeshBuilder builder = RendererAccessImpl.INSTANCE.getRenderer().meshBuilder();
        int newColor = ColorHelper.swapRedBlueIfNeeded(0x3f76e4);
        if (entity != null) {
            newColor = ColorHelper.swapRedBlueIfNeeded(entity.color);
            sprite = entity.color == RenderHelper.BLOOD_COLOR ? BLOOD.getSprite() : entity.color == RenderHelper.HONEY_COLOR ? HONEY.getSprite() : entity.color == RenderHelper.UNICORN_BLOOD_COLOR ? UNICORN.getSprite() : sprite;
        } else if (itemStack != null && itemStack.hasNbt()) {
            var nbt = itemStack.getNbt();
            if (nbt.contains("BlockEntityTag")) {
                var slots = DefaultedList.ofSize(1, ItemStack.EMPTY);
                Inventories.readNbt(nbt.getCompound("BlockEntityTag"), slots);
                NbtCompound nbtCompound = nbt.getCompound("BlockEntityTag");
                newColor = ColorHelper.swapRedBlueIfNeeded(nbtCompound.getInt("Color"));
                sprite = slots.get(0).getItem() == BWObjects.BOTTLE_OF_BLOOD ? BLOOD.getSprite() : slots.get(0).getItem() == Items.HONEY_BOTTLE ? HONEY.getSprite() : slots.get(0).getItem() == BWPObjects.UNICORN_BLOOD ? UNICORN.getSprite() : sprite;
            }
        }
        for(var direction :Direction.values()){
            RenderHelper.emitFluidFace(builder.getEmitter(), sprite, newColor, false, direction, 1f, 0f, EDGE_SIZE, INNER_SIZE);
        }
        int newLight = (light & 0xFFFF_0000) | (Math.max((light >> 4) & 0xF, variant.getFluid().getDefaultState().getBlockState().getLuminance()) << 4);
        RenderHelper.renderMesh(builder.build(), matrices, vertexConsumers.getBuffer(RenderLayer.getTranslucent()), newLight, overlay);
        matrices.pop();
    }
}
