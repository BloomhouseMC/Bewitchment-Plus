package dev.mrsterner.bewitchmentplus.common.registry;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import net.fabricmc.api.EnvType;

import java.util.*;

import net.fabricmc.api.Environment;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;

import static net.minecraft.client.render.TexturedRenderLayers.CHEST_ATLAS_TEXTURE;
import static net.minecraft.screen.PlayerScreenHandler.BLOCK_ATLAS_TEXTURE;

@Environment(EnvType.CLIENT)
public class BWPSpriteIdentifiers {
    public static final Map<SpriteIdentifier, Identifier> SPRITE_IDENTIFIER = new LinkedHashMap<>();

    public static final SpriteIdentifier BLOOD = new SpriteIdentifier(BLOCK_ATLAS_TEXTURE, new Identifier(BewitchmentPlus.MODID, "block/goblet_fluid"));
    public static final SpriteIdentifier HONEY = new SpriteIdentifier(BLOCK_ATLAS_TEXTURE, new Identifier(BewitchmentPlus.MODID, "block/honey_fluid"));
    public static final SpriteIdentifier UNICORN = new SpriteIdentifier(BLOCK_ATLAS_TEXTURE, new Identifier(BewitchmentPlus.MODID, "block/goblet_fluid_unicorn"));
    public static final SpriteIdentifier YEW_CHEST = new SpriteIdentifier(CHEST_ATLAS_TEXTURE, new Identifier(BewitchmentPlus.MODID, "entity/chest/yew"));
    public static final SpriteIdentifier TRAPPED_YEW_CHEST = new SpriteIdentifier(CHEST_ATLAS_TEXTURE, new Identifier(BewitchmentPlus.MODID, "entity/chest/trapped_yew"));
    public static final SpriteIdentifier YEW_CHEST_LEFT = new SpriteIdentifier(CHEST_ATLAS_TEXTURE, new Identifier(BewitchmentPlus.MODID, "entity/chest/yew_left"));
    public static final SpriteIdentifier TRAPPED_YEW_CHEST_LEFT = new SpriteIdentifier(CHEST_ATLAS_TEXTURE, new Identifier(BewitchmentPlus.MODID, "entity/chest/trapped_yew_left"));
    public static final SpriteIdentifier YEW_CHEST_RIGHT = new SpriteIdentifier(CHEST_ATLAS_TEXTURE, new Identifier(BewitchmentPlus.MODID, "entity/chest/yew_right"));
    public static final SpriteIdentifier TRAPPED_YEW_CHEST_RIGHT = new SpriteIdentifier(CHEST_ATLAS_TEXTURE, new Identifier(BewitchmentPlus.MODID, "entity/chest/trapped_yew_right"));
    public static final SpriteIdentifier LEECH_CHEST = new SpriteIdentifier(CHEST_ATLAS_TEXTURE, new Identifier(BewitchmentPlus.MODID, "entity/chest/leech"));
    public static final SpriteIdentifier MIMIC_SPRITE = new SpriteIdentifier(BLOCK_ATLAS_TEXTURE, new Identifier(BewitchmentPlus.MODID, "block/mimic"));


    public static final BWPSpriteIdentifiers INSTANCE = new BWPSpriteIdentifiers();
    private final List<SpriteIdentifier> identifiers = new ArrayList<>();


    public SpriteIdentifier addIdentifier(SpriteIdentifier sprite) {
        this.identifiers.add(sprite);
        return sprite;
    }

    public Collection<SpriteIdentifier> getIdentifiers() {
        return Collections.unmodifiableList(identifiers);
    }
}