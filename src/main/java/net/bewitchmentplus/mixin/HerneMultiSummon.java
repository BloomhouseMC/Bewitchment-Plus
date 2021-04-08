package net.bewitchmentplus.mixin;

import moriyashiine.bewitchment.common.entity.living.HerneEntity;
import moriyashiine.bewitchment.common.entity.living.WerewolfEntity;
import moriyashiine.bewitchment.common.registry.BWEntityTypes;
import net.bewitchmentplus.common.entity.living.DrudenEntity;
import net.bewitchmentplus.common.registry.BWPEntityTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(HerneEntity.class)
public abstract class HerneMultiSummon extends HerneEntity {

	public HerneMultiSummon(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method = "getMinionType", at = @At("HEAD"), remap = false, cancellable = true)
	public void getMinionType(CallbackInfoReturnable<Boolean> callbackInfo) {
		Random random = new Random();
		if (random.nextBoolean()) {
			DrudenEntity entity = BWPEntityTypes.DRUDEN.create(world);
			callbackInfo.setReturnValue(true);
		} else {
			WerewolfEntity entity = BWEntityTypes.WEREWOLF.create(world);
			callbackInfo.setReturnValue(true);
		}
	}
}
