package dev.mrsterner.bewitchmentplus.common.block.yew;

import net.minecraft.block.BlockSetType;
import net.minecraft.block.PressurePlateBlock;

public class YewPressurePlateBlock extends PressurePlateBlock {
    public YewPressurePlateBlock(Settings settings) {
        super(ActivationRule.EVERYTHING, settings, BlockSetType.OAK);
    }
}
