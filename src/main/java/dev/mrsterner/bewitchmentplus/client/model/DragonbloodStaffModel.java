package dev.mrsterner.bewitchmentplus.client.model;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.common.item.DragonbloodStaffItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DragonbloodStaffModel extends AnimatedGeoModel<DragonbloodStaffItem> {
    @Override
    public Identifier getModelLocation(DragonbloodStaffItem object) {
        return new Identifier(BewitchmentPlus.MODID, "geo/enchanted_staff.geo.json");
    }

    @Override
    public Identifier getTextureLocation(DragonbloodStaffItem object) {
        return new Identifier(BewitchmentPlus.MODID, "textures/item/staffs.png");
    }

    @Override
    public Identifier getAnimationFileLocation(DragonbloodStaffItem animatable) {
        return new Identifier(BewitchmentPlus.MODID, "animations/enchanted_staff.animation.json");
    }
}