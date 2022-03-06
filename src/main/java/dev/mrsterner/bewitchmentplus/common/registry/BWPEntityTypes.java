package dev.mrsterner.bewitchmentplus.common.registry;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.common.entity.*;
import dev.mrsterner.bewitchmentplus.common.item.itementity.MutandisItemEntity;
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

	public static final EntityType<CambionEntity> CAMBION = create("cambion", CambionEntity.createAttributes(), FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, CambionEntity::new).dimensions(EntityDimensions.changing(0.75f, 3f)).build());
	public static final EntityType<BlackDogEntity> BLACK_DOG = create("black_dog", BlackDogEntity.createAttributes(), FabricEntityTypeBuilder.createMob().spawnGroup(SpawnGroup.MONSTER).entityFactory(BlackDogEntity::new).spawnRestriction(SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BlackDogEntity::spawnRestriction).dimensions(EntityDimensions.changing(0.75f, 0.75f)).build());
	public static final EntityType<NifflerEntity> NIFFLER = create("niffler", NifflerEntity.createAttributes(), FabricEntityTypeBuilder.createMob().spawnGroup(SpawnGroup.CREATURE).entityFactory(NifflerEntity::new).dimensions(EntityDimensions.changing(0.5f, 0.5f)).build());
	public static final EntityType<PhoenixEntity> PHOENIX = create("phoenix", PhoenixEntity.createAttributes(), FabricEntityTypeBuilder.createMob().spawnGroup(SpawnGroup.CREATURE).entityFactory(PhoenixEntity::new).dimensions(EntityDimensions.changing(0.5f, 0.5f)).build());
	public static final EntityType<EffigyEntity> EFFIGY = create("effigy", EffigyEntity.createAttributes(), FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, EffigyEntity::new).dimensions(EntityDimensions.fixed(0.6F, 1.8F)).build());

	public static final EntityType<RuneEntity> RUNE = create("rune", FabricEntityTypeBuilder.create(SpawnGroup.MISC, RuneEntity::new).dimensions(EntityDimensions.fixed(0F, 0F)).build());

	public static final EntityType<MutandisItemEntity> MUTANDIS_ENTITY_ENTITY_TYPE = Registry.register(Registry.ENTITY_TYPE, new Identifier(BewitchmentPlus.MODID, "mutanis_entity"),
	FabricEntityTypeBuilder.<MutandisItemEntity>create(SpawnGroup.MISC, MutandisItemEntity::new)
	.dimensions(EntityDimensions.fixed(0.25F, 0.25F)).trackRangeBlocks(4).trackedUpdateRate(10).build()
	);

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
