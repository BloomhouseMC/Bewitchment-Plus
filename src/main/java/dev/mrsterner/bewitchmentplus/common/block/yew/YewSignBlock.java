package dev.mrsterner.bewitchmentplus.common.block.yew;

import net.minecraft.block.SignBlock;
import net.minecraft.util.Identifier;
import net.minecraft.util.SignType;

public class YewSignBlock extends SignBlock implements YewSign {
    private final Identifier texture;

    public YewSignBlock(Identifier texture, Settings settings) {
        super(settings, SignType.OAK);
        this.texture = texture;
    }

    @Override
    public Identifier getTexture() {
        return texture;
    }
}