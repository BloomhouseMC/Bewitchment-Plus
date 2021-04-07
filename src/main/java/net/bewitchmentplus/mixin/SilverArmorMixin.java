package net.bewitchmentplus.mixin;

import moriyashiine.bewitchment.api.BewitchmentAPI;
import moriyashiine.bewitchment.common.item.AthameItem;
import moriyashiine.bewitchment.common.misc.BWUtil;
import net.bewitchmentplus.api.BWPAPI;
import net.bewitchmentplus.common.registry.BWPTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class SilverArmorMixin extends Entity {

	public SilverArmorMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@Inject(method = "tick", at = @At("TAIL"))
	private void tick(CallbackInfo callbackInfo) {
		if (!world.isClient) {
			LivingEntity livingEntity = (LivingEntity) (Object) this;
			int damage = 0;
			if (BewitchmentAPI.isWeakToSilver(livingEntity)) {
				damage += BWUtil.getArmorPieces(livingEntity, stack -> BWPTags.SILVER_ARMOR.contains(stack.getItem()));
				if (!(livingEntity.getMainHandStack().getItem() instanceof AthameItem) && BWPAPI.isHoldingSilver(livingEntity, Hand.MAIN_HAND)) {
					damage++;
				}
				if (!(livingEntity.getOffHandStack().getItem() instanceof AthameItem) && BWPAPI.isHoldingSilver(livingEntity, Hand.OFF_HAND)) {
					damage++;
				}
			}
		}
	}
}
