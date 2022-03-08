package dev.mrsterner.bewitchmentplus.client.renderer;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import moriyashiine.bewitchment.api.client.model.BroomEntityModel;
import moriyashiine.bewitchment.api.client.renderer.BroomEntityRenderer;
import moriyashiine.bewitchment.api.entity.BroomEntity;
import moriyashiine.bewitchment.client.BewitchmentClient;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

public class YewBroomEntityRenderer extends BroomEntityRenderer<BroomEntity> {
    private final BroomEntityModel model;
    private static final Identifier TEXTURE = new Identifier(BewitchmentPlus.MODID, "textures/entity/broom/yew.png");

    public YewBroomEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.model =  new BroomEntityModel(ctx.getPart(BewitchmentClient.BROOM_MODEL_LAYER));
    }

    @Override
    public Identifier getTexture(BroomEntity entity) {
        return TEXTURE;
    }
}
