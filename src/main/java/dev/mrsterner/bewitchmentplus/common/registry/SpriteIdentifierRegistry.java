package dev.mrsterner.bewitchmentplus.common.registry;

import net.minecraft.client.util.SpriteIdentifier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SpriteIdentifierRegistry {
    public static final SpriteIdentifierRegistry INSTANCE = new SpriteIdentifierRegistry();
    private final List<SpriteIdentifier> identifiers = new ArrayList<>();

    public SpriteIdentifier addIdentifier(SpriteIdentifier sprite) {
        this.identifiers.add(sprite);
        return sprite;
    }

    public Collection<SpriteIdentifier> getIdentifiers() {
        return Collections.unmodifiableList(identifiers);
    }
}