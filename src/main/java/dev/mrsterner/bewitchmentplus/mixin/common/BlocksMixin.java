package dev.mrsterner.bewitchmentplus.mixin.common;

import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.sound.BlockSoundGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Blocks.class)
public interface BlocksMixin {
    @Invoker
    static LeavesBlock callCreateLeavesBlock(BlockSoundGroup blockSoundGroup) {
        throw new UnsupportedOperationException();
    }
}