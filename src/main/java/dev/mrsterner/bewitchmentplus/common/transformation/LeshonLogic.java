package dev.mrsterner.bewitchmentplus.common.transformation;

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import dev.emi.stepheightentityattribute.StepHeightEntityAttributeMain;
import dev.mrsterner.bewitchmentplus.common.utils.BWPUtil;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import java.util.UUID;


public class LeshonLogic {
    public static final EntityAttributeModifier LESHON_MOVEMENT_SPEED_MODIFIER = new EntityAttributeModifier(UUID.fromString("718104a6-aa19-4b53-bad9-2f9edd46d38b"), "Transformation modifier", 0.16, EntityAttributeModifier.Operation.ADDITION);
    private static final EntityAttributeModifier LESHON_REACH_MODIFIER = new EntityAttributeModifier(UUID.fromString("4c6d90ab-41ad-4d8a-b77a-6329361d3a7c"), "Transformation modifier", 1, EntityAttributeModifier.Operation.ADDITION);
    private static final EntityAttributeModifier LESHON_STEP_HEIGHT_MODIFIER = new EntityAttributeModifier(UUID.fromString("af386c1c-b4fc-429d-87b6-b2559826fa9e"), "Transformation modifier", 0.4, EntityAttributeModifier.Operation.ADDITION);
    private static final EntityAttributeModifier LESHON_ATTACK_RANGE_MODIFIER = new EntityAttributeModifier(UUID.fromString("ae0e4c0a-971f-4629-89ad-60c115112c1e"), "Transformation modifier", 1, EntityAttributeModifier.Operation.ADDITION);
    private static final EntityAttributeModifier LESHON_ATTACK_SPEED_MODIFIER = new EntityAttributeModifier(UUID.fromString("db2512a4-654d-4843-8b06-619748a33955"), "Transformation modifier", -2, EntityAttributeModifier.Operation.ADDITION);
    private static final EntityAttributeModifier LESHON_ARMOR_MODIFIER = new EntityAttributeModifier(UUID.fromString("f00b1b0f-8ad6-4a2f-bdf5-6c337ffee56f"), "Transformation modifier", 16, EntityAttributeModifier.Operation.ADDITION);
    private static final EntityAttributeModifier LESHON_ATTACK_DAMAGE_MODIFIER_1 = new EntityAttributeModifier(UUID.fromString("12c0bedf-bde5-4cae-8acc-90b1204731de"), "Transformation modifier", 30, EntityAttributeModifier.Operation.ADDITION);
    private static final EntityAttributeModifier LESHON_ARMOR_TOUGHNESS_MODIFIER_1 = new EntityAttributeModifier(UUID.fromString("edfd078d-e25c-4e27-ad91-c2b32037c8bf"), "Transformation modifier", 16, EntityAttributeModifier.Operation.ADDITION);


    public static void handleAttributes(PlayerEntity player){
        boolean isLeshon = BWPUtil.isLeshon(player, false);
        EntityAttributeInstance attackDamageAttribute = player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        EntityAttributeInstance attackSpeedAttribute = player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_SPEED);
        EntityAttributeInstance armorAttribute = player.getAttributeInstance(EntityAttributes.GENERIC_ARMOR);
        EntityAttributeInstance armorToughnessAttribute = player.getAttributeInstance(EntityAttributes.GENERIC_ARMOR_TOUGHNESS);
        EntityAttributeInstance attackRange = player.getAttributeInstance(ReachEntityAttributes.ATTACK_RANGE);
        EntityAttributeInstance reach = player.getAttributeInstance(ReachEntityAttributes.REACH);
        EntityAttributeInstance stepHeight = player.getAttributeInstance(StepHeightEntityAttributeMain.STEP_HEIGHT);

        if (isLeshon && !attackSpeedAttribute.hasModifier(LESHON_ATTACK_SPEED_MODIFIER)) {
            attackSpeedAttribute.addPersistentModifier(LESHON_ATTACK_SPEED_MODIFIER);
            armorAttribute.addPersistentModifier(LESHON_ARMOR_MODIFIER);
            attackRange.addPersistentModifier(LESHON_ATTACK_RANGE_MODIFIER);
            reach.addPersistentModifier(LESHON_REACH_MODIFIER);
            stepHeight.addPersistentModifier(LESHON_STEP_HEIGHT_MODIFIER);
            attackDamageAttribute.addPersistentModifier(LESHON_ATTACK_DAMAGE_MODIFIER_1);
            armorToughnessAttribute.addPersistentModifier(LESHON_ARMOR_TOUGHNESS_MODIFIER_1);

        } else if (!isLeshon && attackSpeedAttribute.hasModifier(LESHON_ATTACK_SPEED_MODIFIER)) {
            attackSpeedAttribute.removeModifier(LESHON_ATTACK_SPEED_MODIFIER);
            armorAttribute.removeModifier(LESHON_ARMOR_MODIFIER);
            attackRange.removeModifier(LESHON_ATTACK_RANGE_MODIFIER);
            reach.removeModifier(LESHON_REACH_MODIFIER);
            stepHeight.removeModifier(LESHON_STEP_HEIGHT_MODIFIER);
            attackDamageAttribute.removeModifier(LESHON_ATTACK_DAMAGE_MODIFIER_1);
            armorToughnessAttribute.removeModifier(LESHON_ARMOR_TOUGHNESS_MODIFIER_1);
        }
    }

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
