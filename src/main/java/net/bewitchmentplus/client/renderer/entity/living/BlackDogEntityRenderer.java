package net.bewitchmentplus.client.renderer.entity.living;

import moriyashiine.bewitchment.common.entity.living.util.BWHostileEntity;
import net.bewitchmentplus.BewitchmentPlus;
import net.bewitchmentplus.client.model.entity.living.BlackDogEntityModel;
import net.bewitchmentplus.common.entity.living.BlackDogEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class BlackDogEntityRenderer extends MobEntityRenderer<BlackDogEntity, BlackDogEntityModel<BlackDogEntity>> {
    private static Identifier[] TEXTURES;

    public BlackDogEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new BlackDogEntityModel<>(), 0.5f);
    }

    @Override
    public Identifier getTexture(BlackDogEntity entity) {
        if (TEXTURES == null) {
            int variants = entity.getVariants();
            TEXTURES = new Identifier[variants];
            for (int i = 0; i < variants; i++) {
                TEXTURES[i] = new Identifier(BewitchmentPlus.MODID, "textures/entity/living/black_dog/" + i + ".png");
            }
        }
        return TEXTURES[entity.getDataTracker().get(BWHostileEntity.VARIANT)];
    }
}
