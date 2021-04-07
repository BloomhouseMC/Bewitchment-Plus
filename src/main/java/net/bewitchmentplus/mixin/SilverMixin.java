package net.bewitchmentplus.mixin;

import moriyashiine.bewitchment.api.BewitchmentAPI;
import net.bewitchmentplus.common.registry.BWPTags;
import net.minecraft.entity.LivingEntity;
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
}
