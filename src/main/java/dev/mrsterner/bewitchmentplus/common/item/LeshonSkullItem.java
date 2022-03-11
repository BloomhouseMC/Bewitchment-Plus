package dev.mrsterner.bewitchmentplus.common.item;

import net.minecraft.item.Item;

public class LeshonSkullItem extends Item {
    public LeshonSkullItem(Settings settings) {
        super(settings.maxDamage(100).maxCount(1));
    }
}
