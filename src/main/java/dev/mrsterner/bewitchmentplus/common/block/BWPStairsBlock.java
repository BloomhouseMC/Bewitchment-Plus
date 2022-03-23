package dev.mrsterner.bewitchmentplus.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.StairsBlock;

public class BWPStairsBlock extends StairsBlock {
    public BWPStairsBlock(Block base, Settings settings) {
        super(base.getDefaultState(), settings);
    }
}
