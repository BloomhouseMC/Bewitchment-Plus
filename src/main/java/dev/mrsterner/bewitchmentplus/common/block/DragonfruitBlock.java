package dev.mrsterner.bewitchmentplus.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class DragonfruitBlock extends Block {
    public DragonfruitBlock(Settings settings) {
        super(settings.nonOpaque().breakInstantly());
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.union(createCuboidShape(5, 0, 5, 11, 4, 11), createCuboidShape(6, 4, 6, 10, 7, 10));
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        if (world.getBlockState(pos) == Blocks.AIR.getDefaultState()) {
            int i;
            for (i = 2; i < 6; ++i) {
                BlockPos blockPos = pos.up();
                BlockPos blockPos1 = blockPos.offset(Direction.byId(i));
                if (!world.getFluidState(blockPos.offset(Direction.byId(i))).isEmpty()) {
                    return false;
                }
                if (!world.getFluidState(blockPos1.offset(Direction.byId(i).rotateYClockwise())).isEmpty()) {
                    return false;
                }
            }
            for (i = 2; i < 6; ++i) {
                BlockPos pos1 = pos.down();
                BlockPos pos2 = pos1.offset(Direction.byId(i));
                if (!world.getFluidState(pos1.offset(Direction.byId(i))).isEmpty()) {
                    return false;
                }
                if (!world.getFluidState(pos2.offset(Direction.byId(i).rotateYClockwise())).isEmpty()) {
                    return false;
                }
            }
            return world.getBlockState(pos.down()).isSideSolidFullSquare(world, pos, Direction.UP);
        }
        else return false;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        return !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
    }
}
