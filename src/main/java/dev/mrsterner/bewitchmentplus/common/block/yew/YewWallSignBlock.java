package dev.mrsterner.bewitchmentplus.common.block.yew;

import net.minecraft.block.WallSignBlock;
import net.minecraft.util.Identifier;
import net.minecraft.util.SignType;

public class YewWallSignBlock extends WallSignBlock implements YewSign {
    private final Identifier texture;

    public YewWallSignBlock(Identifier texture, Settings settings) {
        super(settings, SignType.OAK);
        this.texture = texture;
    }

    @Override
    public Identifier getTexture() {
        return texture;
    }
}