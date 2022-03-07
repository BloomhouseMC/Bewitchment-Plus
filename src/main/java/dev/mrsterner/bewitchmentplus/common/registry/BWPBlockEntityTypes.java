package dev.mrsterner.bewitchmentplus.common.registry;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.common.block.blockentity.*;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

import static dev.mrsterner.bewitchmentplus.common.registry.BWPObjects.*;

public class BWPBlockEntityTypes {
    private static final Map<BlockEntityType<?>, Identifier> BLOCK_ENTITY_TYPES = new LinkedHashMap<>();



    public static final BlockEntityType<GobletBlockEntity> GOBLET = register("goblet", FabricBlockEntityTypeBuilder.create(GobletBlockEntity::new, BWPObjects.GOLD_GOBLET, BWPObjects.NETHERITE_GOBLET, BWPObjects.SILVER_GOBLET).build(null));
    public static final BlockEntityType<PentacleBlockEntity> PENTACLE = register("pentacle", FabricBlockEntityTypeBuilder.create(PentacleBlockEntity::new, BWPObjects.PENTACLE).build(null));
    public static final BlockEntityType<MoonflowerBlockEntity> MOONFLOWER_BLOCK_ENTITY = register("moonflower_block_entity", FabricBlockEntityTypeBuilder.create(MoonflowerBlockEntity::new, MOONFLOWER).build(null));
    public static final BlockEntityType<MimicChestBlockEntity> MIMIC_CHEST_BLOCK_ENTITY = register("mimic_chest_block_entity", FabricBlockEntityTypeBuilder.create(MimicChestBlockEntity::new, MIMIC_CHEST).build(null));
    public static final BlockEntityType<StatueBlockEntity> STATUE_BLOCK_ENTITY = register("statue_block_entity", FabricBlockEntityTypeBuilder.create(StatueBlockEntity::new, LILITH_STATUE).build(null));


    private static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType<T> type) {
        BLOCK_ENTITY_TYPES.put(type, new Identifier(BewitchmentPlus.MODID, name));
        return type;
    }



    public static void init() {
        BLOCK_ENTITY_TYPES.keySet().forEach(blockEntityType -> Registry.register(Registry.BLOCK_ENTITY_TYPE, BLOCK_ENTITY_TYPES.get(blockEntityType), blockEntityType));
    }
}
