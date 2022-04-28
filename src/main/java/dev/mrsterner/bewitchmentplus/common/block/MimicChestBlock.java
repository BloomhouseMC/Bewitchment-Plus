package dev.mrsterner.bewitchmentplus.common.block;

import dev.mrsterner.bewitchmentplus.common.block.blockentity.MimicChestBlockEntity;
import dev.mrsterner.bewitchmentplus.common.registry.BWPBlockEntityTypes;
import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import moriyashiine.bewitchment.common.item.TaglockItem;
import moriyashiine.bewitchment.common.registry.BWObjects;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.block.ChestAnimationProgress;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.*;
import net.minecraft.text.LiteralText;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Random;

public class MimicChestBlock extends AbstractChestBlock<MimicChestBlockEntity> implements Waterloggable {
    public static final DirectionProperty  FACING = HorizontalFacingBlock.FACING;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    protected static final VoxelShape SHAPE = Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 14.0D, 15.0D);

    public static final EnumProperty<ChestType> CHEST_TYPE = Properties.CHEST_TYPE;
    private PlayerEntity leechedPlayer;

    public MimicChestBlock(Settings settings) {
        super(settings, () -> BWPBlockEntityTypes.MIMIC_CHEST_BLOCK_ENTITY);
        this.setDefaultState(((this.stateManager.getDefaultState()).with(FACING, Direction.NORTH)).with(WATERLOGGED, false).with(CHEST_TYPE, ChestType.SINGLE));
    }

    public DoubleBlockProperties.PropertySource<? extends MimicChestBlockEntity> getBlockEntitySource(BlockState state, World world, BlockPos pos, boolean ignoreBlocked) {
        return DoubleBlockProperties.PropertyRetriever::getFallback;
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            if (player.isSneaking() && player.getStackInHand(hand).isOf(BWObjects.TAGLOCK)) {
                //System.out.println(this.getLeechedPlayer());
                if (this.getLeechedPlayer() != null) {
                    TaglockItem.useTaglock(player, this.getLeechedPlayer(), hand, true, false);
                    this.setLeechedPlayer(null);
                } else {
                    player.sendMessage(new LiteralText("No Blood Sample Found").formatted(Formatting.GREEN), false);
                }
            } else {
                NamedScreenHandlerFactory namedScreenHandlerFactory = this.createScreenHandlerFactory(state, world, pos);
                if (namedScreenHandlerFactory != null) {
                    player.openHandledScreen(namedScreenHandlerFactory);
                    player.incrementStat(Stats.CUSTOM.getOrCreateStat(Stats.OPEN_CHEST));
                    if (!player.isCreative()) {
                        this.leechedPlayer = player;
                    }
                }
                return ActionResult.CONSUME;
            }
        }
        return ActionResult.PASS;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, this.getExpectedEntityType(), MimicChestBlockEntity::clientTick);
    }


    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getClientTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? checkType(type, this.getExpectedEntityType(), MimicChestBlockEntity::clientTick) : null;
    }

    public PlayerEntity getLeechedPlayer() {
        return leechedPlayer;
    }

    public void setLeechedPlayer(PlayerEntity player) {
        leechedPlayer = player;
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new MimicChestBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        return (this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite())).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER).with(CHEST_TYPE, ChestType.SINGLE);
    }

    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING))).with(CHEST_TYPE, ChestType.SINGLE);
    }

    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING))).with(CHEST_TYPE, ChestType.SINGLE);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, CHEST_TYPE);
        super.appendProperties(builder);
    }

    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof MimicChestBlockEntity mimicChestBlockEntity) {
            mimicChestBlockEntity.onScheduledTick();
        }

    }

    public static DoubleBlockProperties.PropertyRetriever<MimicChestBlockEntity, Float2FloatFunction> getAnimationProgressRetriever(ChestAnimationProgress chestAnimationProgress) {
        return new DoubleBlockProperties.PropertyRetriever<>() {
            public Float2FloatFunction getFromBoth(MimicChestBlockEntity chestBlockEntity, MimicChestBlockEntity chestBlockEntity2) {
                return (f) -> Math.max(chestBlockEntity.getAnimationProgress(f), chestBlockEntity2.getAnimationProgress(f));
            }

            public Float2FloatFunction getFrom(MimicChestBlockEntity chestBlockEntity) {
                Objects.requireNonNull(chestBlockEntity);
                return chestBlockEntity::getAnimationProgress;
            }

            public Float2FloatFunction getFallback() {
                Objects.requireNonNull(chestAnimationProgress);
                return chestAnimationProgress::getAnimationProgress;
            }
        };
    }

    public BlockEntityType<? extends MimicChestBlockEntity> getExpectedEntityType() {
        return this.entityTypeRetriever.get();
    }
}