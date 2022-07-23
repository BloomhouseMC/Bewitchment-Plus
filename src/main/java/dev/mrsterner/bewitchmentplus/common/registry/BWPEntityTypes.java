package dev.mrsterner.bewitchmentplus.common.registry;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.common.entity.*;
import dev.mrsterner.bewitchmentplus.common.item.itementity.MutandisItemEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;

import java.util.LinkedHashMap;
import java.util.Map;

public class BWPEntityTypes {
	private static final Map<EntityType<?>, Identifier> ENTITY_TYPES = new LinkedHashMap<>();


	public static final EntityType<CambionEntity> CAMBION = register("cambion", FabricEntityTypeBuilder
			.<CambionEntity>createMob()
			.spawnGroup(SpawnGroup.MONSTER)
			.entityFactory(CambionEntity::new)
			.defaultAttributes(CambionEntity::createAttributes)
			.dimensions(EntityDimensions.changing(0.75f, 3f))
			.build());

	public static final EntityType<BlackDogEntity> BLACK_DOG = register("black_dog", FabricEntityTypeBuilder
			.<BlackDogEntity>createMob()
			.spawnGroup(SpawnGroup.MONSTER)
			.entityFactory(BlackDogEntity::new)
			.defaultAttributes(BlackDogEntity::createAttributes)
			.dimensions(EntityDimensions.changing(0.75f, 0.75f))
			.spawnRestriction(SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BlackDogEntity::spawnRestriction)
			.build());

	public static final EntityType<NifflerEntity> NIFFLER = register("niffler", FabricEntityTypeBuilder
			.<NifflerEntity>createMob()
			.spawnGroup(SpawnGroup.CREATURE)
			.entityFactory(NifflerEntity::new)
			.defaultAttributes(NifflerEntity::createAttributes)
			.dimensions(EntityDimensions.changing(0.5f, 0.5f))
			.build());

	public static final EntityType<PhoenixEntity> PHOENIX = register("phoenix",FabricEntityTypeBuilder
			.<PhoenixEntity>createMob()
			.spawnGroup(SpawnGroup.CREATURE)
			.entityFactory(PhoenixEntity::new)
			.defaultAttributes(PhoenixEntity::createAttributes)
			.dimensions(EntityDimensions.changing(0.5f, 0.5f))
			.build());

	public static final EntityType<UnicornEntity> UNICORN = register("unicorn", FabricEntityTypeBuilder
			.<UnicornEntity>createMob()
			.spawnGroup(SpawnGroup.CREATURE)
			.entityFactory(UnicornEntity::new)
			.defaultAttributes(UnicornEntity::createAttributes)
			.dimensions(EntityDimensions.changing(1f, 2f))
			.build());

	public static final EntityType<EffigyEntity> EFFIGY = register("effigy", FabricEntityTypeBuilder
			.<EffigyEntity>createLiving()
			.entityFactory(EffigyEntity::new)
			.dimensions(EntityDimensions.fixed(0.6F, 1.8F))
			.defaultAttributes(EffigyEntity::createAttributes)
			.build());

	public static final EntityType<LeshonEntity> LESHON = register("leshon", FabricEntityTypeBuilder
			.<LeshonEntity>createMob()
			.spawnGroup(SpawnGroup.MONSTER)
			.entityFactory(LeshonEntity::new)
			.defaultAttributes(LeshonEntity::createAttributes)
			.dimensions(EntityDimensions.fixed(1f, 3f))
			.build());

	public static final EntityType<YewBroomEntity> YEW_BROOM = register("yew_broom", FabricEntityTypeBuilder
			.<YewBroomEntity>create()
			.spawnGroup(SpawnGroup.MISC)
			.entityFactory(YewBroomEntity::new)
			.dimensions(EntityType.ARROW.getDimensions())
			.build());
/*
	public static final EntityType<DragonEntity> DRAGON = register("dragon", FabricEntityTypeBuilder
			.<DragonEntity>createMob()
			.spawnGroup(SpawnGroup.CREATURE)
			.entityFactory(DragonEntity::new)
			.defaultAttributes(DragonEntity::createAttributes)
			.dimensions(EntityDimensions.changing(0.5f, 0.5f))
			.build());

 */

	public static final EntityType<LilimEntity> LILIM = register("lilim", FabricEntityTypeBuilder
			.<LilimEntity>createMob()
			.spawnGroup(SpawnGroup.MONSTER)
			.entityFactory(LilimEntity::new)
			.defaultAttributes(LilimEntity::createAttributes)
			.dimensions(EntityDimensions.changing(0.75f, 3f))
			.build());

	public static final EntityType<RuneEntity> RUNE = register("rune", FabricEntityTypeBuilder
			.<RuneEntity>create().spawnGroup(SpawnGroup.MISC)
			.entityFactory(RuneEntity::new)
			.dimensions(EntityDimensions.fixed(0F, 0F))
			.build());

	/*
	public static final EntityType<DeathEntity> DEATH = register("death", FabricEntityTypeBuilder
			.<DeathEntity>createLiving()
			.entityFactory(DeathEntity::new)
			.dimensions(EntityDimensions.fixed(2F, 3F))
			.defaultAttributes(DeathEntity::createAttributes)
			.build());

	 */

	public static final EntityType<MutandisItemEntity> MUTANDIS_ENTITY_ENTITY_TYPE = Registry.register(Registry.ENTITY_TYPE, new Identifier(BewitchmentPlus.MODID, "mutanis_entity"),
	FabricEntityTypeBuilder.<MutandisItemEntity>create(SpawnGroup.MISC, MutandisItemEntity::new)
	.dimensions(EntityDimensions.fixed(0.25F, 0.25F)).trackRangeBlocks(4).trackedUpdateRate(10).build());



	private static <T extends Entity> EntityType<T> register(String name, EntityType<T> type) {
		ENTITY_TYPES.put(type, new Identifier(BewitchmentPlus.MODID, name));
		return type;
	}

	public static void init() {
		ENTITY_TYPES.keySet().forEach(entityType -> Registry.register(Registry.ENTITY_TYPE, ENTITY_TYPES.get(entityType), entityType));
	}
}
