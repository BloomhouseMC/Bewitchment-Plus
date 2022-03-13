package dev.mrsterner.bewitchmentplus.client.renderer.entity;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.BewitchmentPlusClient;
import dev.mrsterner.bewitchmentplus.client.model.UnicornEntityModel;
import dev.mrsterner.bewitchmentplus.client.model.entity.BlackDogEntityModel;
import dev.mrsterner.bewitchmentplus.common.entity.UnicornEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.HorseBaseEntityRenderer;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.HorseEntityModel;
import net.minecraft.util.Identifier;

public class UnicornEntityRenderer extends MobEntityRenderer<UnicornEntity, UnicornEntityModel<UnicornEntity>> {
    public UnicornEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new UnicornEntityModel<>(context.getPart(BewitchmentPlusClient.UNICORN_MODEL_LAYER)), 0.5f);
    }

    @Override
    public Identifier getTexture(UnicornEntity entity) {
        return new Identifier(BewitchmentPlus.MODID, "textures/entity/unicorn/0.png");
    }
}
