package dev.mrsterner.bewitchmentplus.client.model.entity;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.common.entity.EffigyEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class EffigyEntityModel extends DefaultedEntityGeoModel<EffigyEntity> {

    public EffigyEntityModel() {
        super(new Identifier(BewitchmentPlus.MODID, "effigy"));
    }
}