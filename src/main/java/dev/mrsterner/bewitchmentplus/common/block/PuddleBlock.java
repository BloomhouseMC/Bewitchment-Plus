package dev.mrsterner.bewitchmentplus.common.block;

import dev.mrsterner.bewitchmentplus.common.registry.BWPObjects;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import java.util.Random;

public class PuddleBlock extends Block {
    public PuddleBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!world.isRaining() && random.nextInt(1000) == 0) {
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
        }
        this.scheduledTick(state, world, pos, random);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(0,0,0, 16, 1, 16);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

    @Override
    public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        return VoxelShapes.empty();
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        if (world.getBlockState(pos) == Blocks.AIR.getDefaultState() || world.getBlockState(pos) == BWPObjects.UNICORN_BLOOD_PUDDLE.getDefaultState()) {
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
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        return !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
    }
}