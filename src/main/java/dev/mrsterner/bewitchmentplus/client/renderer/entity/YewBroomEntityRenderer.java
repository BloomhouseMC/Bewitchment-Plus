package dev.mrsterner.bewitchmentplus.client.renderer.entity;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import moriyashiine.bewitchment.api.client.renderer.BroomEntityRenderer;
import moriyashiine.bewitchment.api.entity.BroomEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

public class YewBroomEntityRenderer extends BroomEntityRenderer<BroomEntity> {
    public YewBroomEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public Identifier getTexture(BroomEntity entity) {
        return new Identifier(BewitchmentPlus.MODID, "textures/entity/broom/yew.png");
    }
}
