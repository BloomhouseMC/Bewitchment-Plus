package dev.mrsterner.bewitchmentplus.common.block;

import dev.mrsterner.bewitchmentplus.common.block.blockentity.StatueBlockEntity;
import dev.mrsterner.bewitchmentplus.common.item.StatueBlockItem;
import dev.mrsterner.bewitchmentplus.common.registry.BWPObjects;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class LilithStatueBlock extends Block implements BlockEntityProvider {

    public LilithStatueBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }


    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new StatueBlockEntity(pos, state);
    }
}
