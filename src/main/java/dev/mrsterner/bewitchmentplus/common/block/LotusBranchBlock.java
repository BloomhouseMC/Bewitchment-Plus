package dev.mrsterner.bewitchmentplus.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class LotusBranchBlock extends HorizontalFacingBlock {
    public static final VoxelShape NORTH_SHAPE = VoxelShapes.union(createCuboidShape(5, 8, 5, 11, 14, 16), createCuboidShape(5, 14, 5, 11, 16, 11));
    public static final VoxelShape EAST_SHAPE =  VoxelShapes.union(createCuboidShape(0, 8, 5, 11, 14, 11), createCuboidShape(5, 14, 5, 11, 16, 11));
    public static final VoxelShape SOUTH_SHAPE = VoxelShapes.union(createCuboidShape(5, 8, 0, 11, 14, 11), createCuboidShape(5, 14, 5, 11, 16, 11));
    public static final VoxelShape WEST_SHAPE = VoxelShapes.union(createCuboidShape(5, 8, 5, 16, 14, 11), createCuboidShape(5, 14, 5, 11, 16, 11));
    public LotusBranchBlock(Settings settings) {
        super(settings.nonOpaque());
        setDefaultState(this.stateManager.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH));
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(Properties.HORIZONTAL_FACING);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(Properties.HORIZONTAL_FACING)) {
            case EAST -> EAST_SHAPE;
            case WEST -> WEST_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            default -> NORTH_SHAPE;
        };
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(Properties.HORIZONTAL_FACING, ctx.getPlayerFacing().getOpposite());
    }
}
