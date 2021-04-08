package net.bewitchmentplus.mixin;

import moriyashiine.bewitchment.common.entity.living.HerneEntity;
import moriyashiine.bewitchment.common.registry.BWEntityTypes;
import net.bewitchmentplus.common.registry.BWPEntityTypes;
import net.minecraft.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import java.util.Random;

@Mixin(HerneEntity.class)
public class HerneMultiSummon {

	@Inject(method = "getMinionType", at = @At("RETURN"), remap = false)
	public EntityType<?> getMinionType() {
		Random random = new Random();
		if (random.nextBoolean()) {
			return BWEntityTypes.WEREWOLF;
		} else return BWPEntityTypes.DRUDEN;
	}
}