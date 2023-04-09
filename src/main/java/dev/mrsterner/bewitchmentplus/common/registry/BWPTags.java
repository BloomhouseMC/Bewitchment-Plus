package dev.mrsterner.bewitchmentplus.common.registry;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BWPTags {
    public static final TagKey<Item> GOBLET_LIQUIDS = TagKey.of(Registry.ITEM_KEY, new Identifier(BewitchmentPlus.MODID, "goblet_liquids"));
    public static final TagKey<Item> NIFFLER = TagKey.of(Registry.ITEM_KEY, new Identifier(BewitchmentPlus.MODID, "niffler"));
    public static final TagKey<EntityType<?>> SPECTRAL_FAMILIAR = TagKey.of(Registry.ENTITY_TYPE_KEY, new Identifier(BewitchmentPlus.MODID, "spectral_familiar"));
    public static final TagKey<EntityType<?>> UNHOLY = TagKey.of(Registry.ENTITY_TYPE_KEY, new Identifier(BewitchmentPlus.MODID, "unholy"));
    public static final TagKey<EntityType<?>> SACRIFICES = TagKey.of(Registry.ENTITY_TYPE_KEY, new Identifier(BewitchmentPlus.MODID, "sacrifices"));

    public static final TagKey<Block> MUTANDIS = TagKey.of(Registry.BLOCK_KEY, new Identifier(BewitchmentPlus.MODID, "mutandis"));
    public static final TagKey<Block> FLEECE = TagKey.of(Registry.BLOCK_KEY, new Identifier(BewitchmentPlus.MODID, "fleece"));


}
