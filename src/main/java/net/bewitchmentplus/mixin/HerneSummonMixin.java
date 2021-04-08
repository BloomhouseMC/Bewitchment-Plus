package net.bewitchmentplus.mixin;

import moriyashiine.bewitchment.common.entity.living.HerneEntity;
import net.bewitchmentplus.common.registry.BWPEntityTypes;
import net.minecraft.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(HerneEntity.class)
public class HerneSummonMixin {

	@Inject(method = "getMinionType", at = @At("TAIL"), remap = false)
	public EntityType<?> getMinionType() {
		return BWPEntityTypes.DRUDEN;
	}
}
