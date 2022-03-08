package dev.mrsterner.bewitchmentplus.common.block.yew;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;

public class YewStairsBlock extends StairsBlock {
    public YewStairsBlock(Block base, Settings settings) {
        super(base.getDefaultState(), settings);
    }
}
