package net.bewitchmentplus.common.registry;

import net.bewitchmentplus.BewitchmentPlus;
import net.bewitchmentplus.common.entity.living.BlackDogEntity;
import net.bewitchmentplus.common.entity.living.DrudenEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;

import java.util.LinkedHashMap;
import java.util.Map;

public class BWPEntityTypes {
	private static final Map<EntityType<?>, Identifier> ENTITY_TYPES = new LinkedHashMap<>();

	public static final EntityType<DrudenEntity> DRUDEN = create("druden", DrudenEntity.createAttributes(), FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, DrudenEntity::new).dimensions(EntityDimensions.changing(0.75f, 3f)).build());
	public static final EntityType<BlackDogEntity> BLACK_DOG = create("black_dog", BlackDogEntity.createAttributes(), FabricEntityTypeBuilder.createMob().spawnGroup(SpawnGroup.MONSTER).entityFactory(BlackDogEntity::new).spawnRestriction(SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BlackDogEntity::spawnRestriction).dimensions(EntityDimensions.changing(0.75f, 3f)).build());

	private static <T extends LivingEntity> EntityType<T> create(String name, DefaultAttributeContainer.Builder attributes, EntityType<T> type) {
		FabricDefaultAttributeRegistry.register(type, attributes);
		ENTITY_TYPES.put(type, new Identifier(BewitchmentPlus.MODID, name));
		return type;
	}

	private static <T extends Entity> EntityType<T> create(String name, EntityType<T> type) {
		ENTITY_TYPES.put(type, new Identifier(BewitchmentPlus.MODID, name));
		return type;
	}

	public static void init() {
		ENTITY_TYPES.keySet().forEach(entityType -> Registry.register(Registry.ENTITY_TYPE, ENTITY_TYPES.get(entityType), entityType));
	}
}
