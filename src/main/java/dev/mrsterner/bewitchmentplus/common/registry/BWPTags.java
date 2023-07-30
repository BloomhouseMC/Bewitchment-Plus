package dev.mrsterner.bewitchmentplus.common.registry;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class BWPTags {
    public static final TagKey<Item> GOBLET_LIQUIDS = TagKey.of(RegistryKeys.ITEM, new Identifier(BewitchmentPlus.MODID, "goblet_liquids"));
    public static final TagKey<Item> NIFFLER = TagKey.of(RegistryKeys.ITEM, new Identifier(BewitchmentPlus.MODID, "niffler"));
    public static final TagKey<EntityType<?>> SPECTRAL_FAMILIAR = TagKey.of(RegistryKeys.ENTITY_TYPE, new Identifier(BewitchmentPlus.MODID, "spectral_familiar"));
    public static final TagKey<EntityType<?>> UNHOLY = TagKey.of(RegistryKeys.ENTITY_TYPE, new Identifier(BewitchmentPlus.MODID, "unholy"));
    public static final TagKey<EntityType<?>> SACRIFICES = TagKey.of(RegistryKeys.ENTITY_TYPE, new Identifier(BewitchmentPlus.MODID, "sacrifices"));

    public static final TagKey<Block> MUTANDIS = TagKey.of(RegistryKeys.BLOCK, new Identifier(BewitchmentPlus.MODID, "mutandis"));
    public static final TagKey<Block> FLEECE = TagKey.of(RegistryKeys.BLOCK, new Identifier(BewitchmentPlus.MODID, "fleece"));


}
