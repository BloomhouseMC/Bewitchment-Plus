package dev.mrsterner.bewitchmentplus.common.registry;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.common.block.blockentity.*;
import dev.mrsterner.bewitchmentplus.common.block.yew.YewChestBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

import static dev.mrsterner.bewitchmentplus.common.registry.BWPObjects.*;

public class BWPBlockEntityTypes {
    private static final Map<BlockEntityType<?>, Identifier> BLOCK_ENTITY_TYPES = new LinkedHashMap<>();



    public static final BlockEntityType<GobletBlockEntity> GOBLET = register("goblet", FabricBlockEntityTypeBuilder.create(GobletBlockEntity::new, BWPObjects.GOLD_GOBLET, BWPObjects.NETHERITE_GOBLET, BWPObjects.SILVER_GOBLET).build(null));
    //public static final BlockEntityType<PentacleBlockEntity> PENTACLE = register("pentacle", FabricBlockEntityTypeBuilder.create(PentacleBlockEntity::new, BWPObjects.PENTACLE).build(null));
    public static final BlockEntityType<MoonflowerBlockEntity> MOONFLOWER_BLOCK_ENTITY = register("moonflower_block_entity", FabricBlockEntityTypeBuilder.create(MoonflowerBlockEntity::new, MOONFLOWER).build(null));
    public static final BlockEntityType<MimicChestBlockEntity> MIMIC_CHEST_BLOCK_ENTITY = register("mimic_chest_block_entity", FabricBlockEntityTypeBuilder.create(MimicChestBlockEntity::new, MIMIC_CHEST).build(null));
    public static final BlockEntityType<StatueBlockEntity> STATUE_BLOCK_ENTITY = register("statue_block_entity", FabricBlockEntityTypeBuilder.create(StatueBlockEntity::new, LILITH_STATUE_BLACKSTONE, LILITH_STATUE_GOLD, LILITH_STATUE_NETHERBRICK, HERNE_STATUE_BLACKSTONE, HERNE_STATUE_GOLD, HERNE_STATUE_NETHERBRICK, LEONARD_STATUE_BLACKSTONE, LEONARD_STATUE_GOLD,LEONARD_STATUE_NETHERBRICK, BAPHOMET_STATUE_BLACKSTONE, BAPHOMET_STATUE_GOLD, BAPHOMET_STATUE_NETHERBRICK).build(null));
    public static final BlockEntityType<YewChestBlockEntity> YEW_CHEST_BLOCK_ENTITY = register("yew_chest", FabricBlockEntityTypeBuilder.create(YewChestBlockEntity::new, BWPObjects.YEW_CHEST, BWPObjects.TRAPPED_YEW_CHEST).build(null));
    public static final BlockEntityType<LeechChestBlockEntity> LEECH_CHEST_BLOCK_ENTITY = register("leech_chest", FabricBlockEntityTypeBuilder.create(LeechChestBlockEntity::new, LEECH_CHEST).build(null));
    public static final BlockEntityType<YewLogBlockEntity> YEW_LOG_BLOCK_ENTITY = register("yew_log", FabricBlockEntityTypeBuilder.create(YewLogBlockEntity::new, YEW_CUT_LOG).build(null));


    private static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType<T> type) {
        BLOCK_ENTITY_TYPES.put(type, new Identifier(BewitchmentPlus.MODID, name));
        return type;
    }



    public static void init() {
        BLOCK_ENTITY_TYPES.keySet().forEach(blockEntityType -> Registry.register(Registries.BLOCK_ENTITY_TYPE, BLOCK_ENTITY_TYPES.get(blockEntityType), blockEntityType));
    }
}
