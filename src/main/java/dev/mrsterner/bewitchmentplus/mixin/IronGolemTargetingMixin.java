package dev.mrsterner.bewitchmentplus.mixin;

import dev.mrsterner.bewitchmentplus.common.entity.CambionEntity;
import dev.mrsterner.bewitchmentplus.common.registry.BWPEntityTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.IronGolemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(IronGolemEntity.class)
public class IronGolemTargetingMixin {
	@Inject(method = "pushAway", at = @At(value = "HEAD"))
	private void init(Entity entity, CallbackInfo cir) {
		if (entity instanceof Monster && !(entity instanceof CreeperEntity) && !(entity instanceof CambionEntity) && ((IronGolemEntity) (Object) this).getRandom().nextInt(20) == 0) {
			((IronGolemEntity) (Object) this).setTarget((LivingEntity) entity);
		}
	}

	@Inject(method = "canTarget", at = @At(value = "HEAD"), cancellable = true)
	private void canTargetInject(EntityType<?> type, CallbackInfoReturnable<Boolean> cir) {
		if (((IronGolemEntity) (Object) this).isPlayerCreated() && type == EntityType.PLAYER) {
			cir.setReturnValue(false);
		} else if (type == EntityType.CREEPER || type == BWPEntityTypes.CAMBION) {
			cir.setReturnValue(false);
		}
	}
}