package dev.mrsterner.bewitchmentplus.common.item;

import dev.mrsterner.bewitchmentplus.common.network.packet.S2CBloodParticlesPacket;
import moriyashiine.bewitchment.api.BewitchmentAPI;
import moriyashiine.bewitchment.common.registry.BWTags;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;

public class VamireKnifeItem extends SwordItem {
    public VamireKnifeItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if(target.getType().isIn(BWTags.HAS_BLOOD) || target instanceof AnimalEntity || target instanceof WitherSkeletonEntity){
            PlayerLookup.tracking(target).forEach(trackingPlayer -> S2CBloodParticlesPacket.send(trackingPlayer, target));
        }
        return super.postHit(stack, target, attacker);
    }
}
