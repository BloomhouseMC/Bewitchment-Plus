package dev.mrsterner.bewitchmentplus.common.block.yew;


import net.minecraft.block.BlockSetType;
import net.minecraft.block.Blocks;
import net.minecraft.block.ButtonBlock;

import static net.minecraft.util.collection.DefaultedList.copyOf;

public class YewButtonBlock extends ButtonBlock {
    public YewButtonBlock(Settings settings) {
        super(settings, BlockSetType.OAK, 30, true);
    }
}
