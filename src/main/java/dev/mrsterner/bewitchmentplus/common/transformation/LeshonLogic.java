package dev.mrsterner.bewitchmentplus.common.transformation;

import moriyashiine.bewitchment.common.registry.BWComponents;
import moriyashiine.bewitchment.common.registry.BWEntityTypes;
import moriyashiine.bewitchment.common.registry.BWScaleTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import virtuoel.pehkui.api.ScaleData;

import java.util.Objects;
import java.util.UUID;


public class LeshonLogic {
    public static final EntityAttributeModifier LESHON_MOVEMENT_SPEED_MODIFIER = new EntityAttributeModifier(UUID.fromString("718104a6-aa19-4b53-bad9-2f9edd46d38a"), "Transformation modifier", 0.16, EntityAttributeModifier.Operation.ADDITION);
    private static final EntityAttributeModifier LESHON_REACH_MODIFIER = new EntityAttributeModifier(UUID.fromString("4c6d90ab-41ad-4d8a-b77a-6329361d3a7b"), "Transformation modifier", 1, EntityAttributeModifier.Operation.ADDITION);
    private static final EntityAttributeModifier LESHON_STEP_HEIGHT_MODIFIER = new EntityAttributeModifier(UUID.fromString("af386c1c-b4fc-429d-87b6-b2559826fa9d"), "Transformation modifier", 0.4, EntityAttributeModifier.Operation.ADDITION);
    private static final EntityAttributeModifier LESHON_ATTACK_RANGE_MODIFIER = new EntityAttributeModifier(UUID.fromString("ae0e4c0a-971f-4629-89ad-60c115112c1d"), "Transformation modifier", 1, EntityAttributeModifier.Operation.ADDITION);
    private static final EntityAttributeModifier LESHON_ATTACK_SPEED_MODIFIER = new EntityAttributeModifier(UUID.fromString("db2512a4-654d-4843-8b06-619748a33954"), "Transformation modifier", -2, EntityAttributeModifier.Operation.ADDITION);



    public static void sprintHandler(PlayerEntity player){
        EntityAttributeInstance movementSpeedAttribute = player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        if(player.isSprinting() && movementSpeedAttribute != null && !movementSpeedAttribute.hasModifier(LESHON_MOVEMENT_SPEED_MODIFIER)){
            movementSpeedAttribute.addPersistentModifier(LESHON_MOVEMENT_SPEED_MODIFIER);
        }else {
            if(!player.isSprinting()) {
                if (movementSpeedAttribute != null && movementSpeedAttribute.hasModifier(LESHON_MOVEMENT_SPEED_MODIFIER)) {
                    movementSpeedAttribute.removeModifier(LESHON_MOVEMENT_SPEED_MODIFIER);
                }
            }
        }
    }
}
