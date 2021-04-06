package net.bewitchmentplus.mixin;

import moriyashiine.bewitchment.api.BewitchmentAPI;
import moriyashiine.bewitchment.common.entity.projectile.SilverArrowEntity;
import moriyashiine.bewitchment.common.item.AthameItem;
import net.bewitchmentplus.common.registry.BWPTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@SuppressWarnings("ConstantConditions")
@Mixin(BewitchmentAPI.class)
public class SilverMixin {

	@Inject(method = "isHoldingSilver", at = @At("TAIL"))
	private static boolean isHoldingSilver(LivingEntity livingEntity, Hand hand) {
		return BWPTags.SILVER_TOOLS.contains(livingEntity.getStackInHand(hand).getItem());
	}

	@Inject(method = "isSourceFromSilver", at = @At("TAIL"))
	private static boolean isSourceFromSilver(DamageSource source) {
		Entity attacker = source.getSource();
		if (source instanceof EntityDamageSource && ((EntityDamageSource) source).isThorns()) {
			return false;
		}
		return (attacker instanceof LivingEntity && isHoldingSilver((LivingEntity) attacker, Hand.MAIN_HAND)) || attacker instanceof SilverArrowEntity;
	}
}
