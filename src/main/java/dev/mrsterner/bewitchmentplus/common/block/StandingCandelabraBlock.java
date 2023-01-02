package dev.mrsterner.bewitchmentplus.common.block;

import moriyashiine.bewitchment.api.block.CandelabraBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class StandingCandelabraBlock extends Block implements Waterloggable {
    public static final EnumProperty<DoubleBlockHalf> HALF = Properties.DOUBLE_BLOCK_HALF;
    public StandingCandelabraBlock(Settings settings) {
        super(settings.nonOpaque().luminance(((blockState) -> (Boolean)blockState.get(Properties.LIT) ? 15 : 0)));
        this.setDefaultState(this.stateManager.getDefaultState().with(HALF, DoubleBlockHalf.LOWER).with(Properties.WATERLOGGED, false).with(Properties.LIT, true));

    }



    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(4,0,4, 12, 16, 12);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(Properties.WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        DoubleBlockHalf doubleBlockHalf = state.get(HALF);
        if (state.get(Properties.WATERLOGGED)) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
            if (state.get(Properties.LIT)) {
                world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 2.0F);
                state = state.with(Properties.LIT, false);
            }
        }

        if (direction.getAxis() == Direction.Axis.Y && doubleBlockHalf == DoubleBlockHalf.LOWER == (direction == Direction.UP)) {
            return neighborState.isOf(this) && neighborState.get(HALF) != doubleBlockHalf ? state : Blocks.AIR.getDefaultState();
        } else {
            return doubleBlockHalf == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
        }
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient && player.isCreative()) {
            DoubleBlockHalf doubleBlockHalf = state.get(HALF);
            if (doubleBlockHalf == DoubleBlockHalf.UPPER) {
                BlockPos blockPos = pos.down();
                BlockState blockState = world.getBlockState(blockPos);
                if (blockState.isOf(state.getBlock()) && blockState.get(HALF) == DoubleBlockHalf.LOWER) {
                    BlockState blockState2 = blockState.contains(Properties.WATERLOGGED) && blockState.get(Properties.WATERLOGGED)
                            ? Blocks.WATER.getDefaultState()
                            : Blocks.AIR.getDefaultState();
                    world.setBlockState(blockPos, blockState2, Block.NOTIFY_ALL | Block.SKIP_DROPS);
                    world.syncWorldEvent(player, WorldEvents.BLOCK_BROKEN, blockPos, Block.getRawIdFromState(blockState));
                }
            }
        }

        super.onBreak(world, pos, state, player);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        boolean client = world.isClient;
        if (!(Boolean)state.get(Properties.WATERLOGGED) && state.get(HALF) == DoubleBlockHalf.UPPER) {
            ItemStack stack = player.getStackInHand(hand);
            if (stack.getItem() instanceof FlintAndSteelItem && !(Boolean)state.get(Properties.LIT)) {
                if (!client) {
                    world.setBlockState(pos, state.with(Properties.LIT, true));
                    world.playSound(null, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    stack.damage(1, player, (stackUser) -> {
                        stackUser.sendToolBreakStatus(hand);
                    });
                }

                return ActionResult.success(client);
            }

            if (stack.isEmpty() && state.get(Properties.LIT)) {
                if (!client) {
                    world.setBlockState(pos, state.with(Properties.LIT, false));
                    world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 2.0F);
                }

                return ActionResult.success(client);
            }
        }

        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        World world = ctx.getWorld();
        if (blockPos.getY() < world.getTopY() - 1 && world.getBlockState(blockPos.up()).canReplace(ctx)) {
            return this.getDefaultState().with(HALF, DoubleBlockHalf.LOWER).with(Properties.WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER).with(Properties.LIT, false);
        } else {
            return null;
        }
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        world.setBlockState(pos.up(), state.with(HALF, DoubleBlockHalf.UPPER), 3);
    }

    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (!this.getDefaultState().isOf(block)) {
            world.setBlockState(pos, state, 2);
        }
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        BlockState blockState = world.getBlockState(blockPos);
        return state.get(HALF) == DoubleBlockHalf.LOWER ? blockState.isSideSolidFullSquare(world, blockPos, Direction.UP) : blockState.isOf(this);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, net.minecraft.util.math.random.Random random) {
        if (state.get(Properties.LIT) && state.get(HALF) == DoubleBlockHalf.UPPER) {
            world.addParticle(ParticleTypes.FLAME, (double)pos.getX() + 0.5D, (double)pos.getY() + 1D, (double)pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
            world.addParticle(ParticleTypes.FLAME, (double)pos.getX() + 0.125D, (double)pos.getY() + 1.075D, (double)pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
            world.addParticle(ParticleTypes.FLAME, (double)pos.getX() + 0.875D, (double)pos.getY() + 1.075D, (double)pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
            world.addParticle(ParticleTypes.FLAME, (double)pos.getX() + 0.5D, (double)pos.getY() + 1.075D, (double)pos.getZ() + 0.875D, 0.0D, 0.0D, 0.0D);
            world.addParticle(ParticleTypes.FLAME, (double)pos.getX() + 0.5D, (double)pos.getY() + 1.075D, (double)pos.getZ() + 0.125D, 0.0D, 0.0D, 0.0D);
        }
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HALF, Properties.WATERLOGGED, Properties.LIT);
    }

}
