package net.bewitchmentplus.mixin;

import com.google.common.collect.Maps;
import moriyashiine.bewitchment.common.entity.living.HerneEntity;
import moriyashiine.bewitchment.common.registry.BWEntityTypes;
import net.bewitchmentplus.common.registry.BWPEntityTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import java.util.Set;

@SuppressWarnings("ALL")
@Mixin(HerneEntity.class)
public class HerneSummonMixin {

	private static final Set<EntityType<net.minecraft.entity.Entity>> SUMMONS;

	static {
		SUMMONS = (Set<EntityType<net.minecraft.entity.Entity>>) Util.make(Maps.newHashMap(), (hashMap) -> {
			hashMap.put(BWPEntityTypes.DRUDEN, 1);
			hashMap.put(BWEntityTypes.WEREWOLF, 2);
		});
	}

	@Inject(method = "getMinionType", at = @At("TAIL"), remap = false)
	private Set<EntityType<Entity>> getMinionType() {
		return SUMMONS;
	}
}
