package net.bewitchmentplus.mixin;

import moriyashiine.bewitchment.api.BewitchmentAPI;
import moriyashiine.bewitchment.common.entity.projectile.SilverArrowEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import static net.bewitchmentplus.api.BWPAPI.isHoldingSilver;

@Mixin(BewitchmentAPI.class)
public class SilverMixin {
	@Inject(method = "isSourceFromSilver", at = @At("TAIL"), remap = false)
	private static boolean isSourceFromSilver(DamageSource source) {
		Entity attacker = source.getSource();
		if (source instanceof EntityDamageSource && ((EntityDamageSource) source).isThorns()) {
			return false;
		}
		return (attacker instanceof LivingEntity && isHoldingSilver((LivingEntity) attacker, Hand.MAIN_HAND)) || attacker instanceof SilverArrowEntity;
	}
}
