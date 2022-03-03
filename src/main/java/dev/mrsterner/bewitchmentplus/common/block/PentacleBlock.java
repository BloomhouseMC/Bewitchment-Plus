package dev.mrsterner.bewitchmentplus.common.block;


import static net.minecraft.state.property.Properties.IN_WALL;
import dev.mrsterner.bewitchmentplus.common.block.blockentity.PentacleBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class PentacleBlock extends HorizontalFacingBlock implements BlockEntityProvider {
    public static final VoxelShape GROUND_SHAPE = createCuboidShape(0, 0, 0, 16, 1, 16);

    public static final VoxelShape NORTH_SHAPE = createCuboidShape(0, 0, 15, 16, 16, 16);
    public static final VoxelShape EAST_SHAPE = createCuboidShape(0, 0, 0, 1, 16, 16);
    public static final VoxelShape SOUTH_SHAPE = createCuboidShape(0, 0, 0, 16, 16, 1);
    public static final VoxelShape WEST_SHAPE = createCuboidShape(15, 0, 0, 16, 16, 16);

    public PentacleBlock(Settings settings) {
        super(settings.hardness(1).resistance(3F).nonOpaque());
        setDefaultState(getDefaultState().with(FACING, Direction.NORTH).with(IN_WALL, false));
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return (tickerWorld, pos, tickerState, blockEntity) -> PentacleBlockEntity.tick(tickerWorld, pos, tickerState, (PentacleBlockEntity) blockEntity);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PentacleBlockEntity(pos, state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FACING, IN_WALL);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(IN_WALL)) {
            return switch (state.get(FACING)) {
                case EAST -> EAST_SHAPE;
                case WEST -> WEST_SHAPE;
                case SOUTH -> SOUTH_SHAPE;
                default -> NORTH_SHAPE;
            };
        }
        return GROUND_SHAPE;
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        if (isPlacementValid(ctx.getWorld(), ctx.getBlockPos(), ctx.getSide())) {
            if (ctx.getSide() != Direction.UP) {
                return this.getDefaultState().with(FACING, ctx.getSide()).with(IN_WALL, true);
            }
            return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite()).with(IN_WALL, false);
        }
        return null;
    }

    public boolean isPlacementValid(WorldView world, BlockPos pos, Direction direction) {
        return world.getBlockState(pos.offset(direction.getOpposite())).isSideSolidFullSquare(world, pos, direction);
    }

    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.DESTROY;
    }
}
