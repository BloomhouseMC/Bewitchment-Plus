package dev.mrsterner.bewitchmentplus.common.block;

import dev.mrsterner.bewitchmentplus.common.block.blockentity.MoonflowerBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class MoonflowerBlock extends BlockWithEntity {
    public MoonflowerBlock(Settings settings) {
        super(settings.nonOpaque());
    }

    private static final VoxelShape SHAPE = VoxelShapes.union(createCuboidShape(5, 0, 5, 16-5, 6, 16-5));

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new MoonflowerBlockEntity(pos, state);
    }
}