package dev.mrsterner.bewitchmentplus.common.item;

import dev.mrsterner.bewitchmentplus.common.network.packet.S2CBloodParticlesPacket;
import moriyashiine.bewitchment.common.registry.BWComponents;
import moriyashiine.bewitchment.common.registry.BWTransformations;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.world.World;

public class VamireKnifeItem extends SwordItem {
    private int timer = 0;


    public VamireKnifeItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(timer < -1){
            timer++;
        }else if(timer == -1){
            BWComponents.BLOOD_COMPONENT.maybeGet(entity).ifPresent(bloodComponent -> {
                bloodComponent.fillBlood(1, false);
            });
            timer = 0;
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        BWComponents.BLOOD_COMPONENT.maybeGet(target).ifPresent(bloodComponent -> {
            if(BWComponents.TRANSFORMATION_COMPONENT.get(attacker).getTransformation() == BWTransformations.VAMPIRE){
                PlayerLookup.tracking(target).forEach(trackingPlayer -> S2CBloodParticlesPacket.send(trackingPlayer, target));
                timer = -15;
            }
        });
        return super.postHit(stack, target, attacker);
    }
}
