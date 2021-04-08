package net.bewitchmentplus.mixin;

import moriyashiine.bewitchment.common.entity.living.HerneEntity;
import moriyashiine.bewitchment.common.entity.living.util.BWHostileEntity;
import moriyashiine.bewitchment.common.registry.BWEntityTypes;
import net.bewitchmentplus.common.registry.BWPEntityTypes;
import net.minecraft.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(HerneEntity.class)
public class HerneSummonMixin {

	@Unique
	boolean summonDemon;

	@Inject(method = "getMinionType", at = @At("RETURN"), remap = false)
	private EntityType<? extends BWHostileEntity> getMinionType() {
		if (Math.random() < 0.5) {
			summonDemon = false;
			return BWEntityTypes.WEREWOLF;
		} else {
			summonDemon = true;
			return BWPEntityTypes.DRUDEN;
		}
	}
}

