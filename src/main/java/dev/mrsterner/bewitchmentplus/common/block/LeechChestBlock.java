package dev.mrsterner.bewitchmentplus.common.block;

import dev.mrsterner.bewitchmentplus.common.block.blockentity.LeechChestBlockEntity;
import dev.mrsterner.bewitchmentplus.common.registry.BWPBlockEntityTypes;
import dev.mrsterner.bewitchmentplus.common.registry.BWPDamageSources;
import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import moriyashiine.bewitchment.common.item.TaglockItem;
import moriyashiine.bewitchment.common.registry.BWObjects;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
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
import java.util.UUID;

public class LeechChestBlock extends AbstractChestBlock<LeechChestBlockEntity> implements Waterloggable {
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    protected static final VoxelShape SHAPE = Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 14.0D, 15.0D);

    public LeechChestBlock(Settings settings) {
        super(settings, () -> BWPBlockEntityTypes.LEECH_CHEST_BLOCK_ENTITY);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(WATERLOGGED, false));
    }

    public DoubleBlockProperties.PropertySource<? extends LeechChestBlockEntity> getBlockEntitySource(BlockState state, World world, BlockPos pos, boolean ignoreBlocked) {
        return DoubleBlockProperties.PropertyRetriever::getFallback;
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            if (player.isSneaking() && player.getStackInHand(hand).isOf(BWObjects.TAGLOCK)) {
                if (TaglockItem.hasTaglock(player.getStackInHand(hand))) {
                    this.whitelistEntity(world, pos, player, player.getStackInHand(hand), TaglockItem.getTaglockUUID(player.getStackInHand(hand)));
                } else {
                    this.grantTaglock(world, pos, player, hand);
                }
                return ActionResult.SUCCESS;
            } else {
                NamedScreenHandlerFactory namedScreenHandlerFactory = this.createScreenHandlerFactory(state, world, pos);
                if (namedScreenHandlerFactory != null) {
                    player.openHandledScreen(namedScreenHandlerFactory);
                    player.incrementStat(Stats.CUSTOM.getOrCreateStat(Stats.OPEN_CHEST));
                    this.leechAndDamage(world, pos, player);
                }
                return ActionResult.CONSUME;
            }
        }
        return ActionResult.PASS;
    }

    public void grantTaglock(World world, BlockPos pos, PlayerEntity player, Hand hand) {
        if (world.getBlockEntity(pos) instanceof LeechChestBlockEntity be) {
            if (be.getLeechedId() != 0) {
                if (world.getEntityById(be.getLeechedId()) instanceof LivingEntity entity) {
                    TaglockItem.useTaglock(player, entity, hand, true, false);
                    be.setLeechedId(0);
                } else {
                    player.sendMessage(Text.translatable("text.bwplus.no_taglock").formatted(Formatting.RED), true);
                }
            } else {
                player.sendMessage(Text.translatable("text.bwplus.no_taglock").formatted(Formatting.RED), true);
            }
        }
    }

    public void whitelistEntity(World world, BlockPos pos, PlayerEntity player, ItemStack stack, UUID id) {
        if (world.getBlockEntity(pos) instanceof LeechChestBlockEntity be) {
            if (be.getOwnerId() == player.getId()) {
                if (!be.getWhitelisted().contains(id)) {
                    be.getWhitelisted().add(id);
                    stack.decrement(1);
                    be.markDirty();
                    player.sendMessage(Text.translatable("text.bwplus.whitelist").formatted(Formatting.GREEN), true);
                }
            }
        }
    }

    public void leechAndDamage(World world, BlockPos pos, PlayerEntity player) {
        if (world.getBlockEntity(pos) instanceof LeechChestBlockEntity be) {
            if (be.getOwnerId() != player.getId() && !be.getWhitelisted().contains(player.getUuid())) {
                player.damage(BWPDamageSources.LEECH, 3.0F);
                be.setLeechedId(player.getId());
            }
        }
    }

    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        if (placer != null && !world.isClient) {
            if (world.getBlockEntity(pos) instanceof LeechChestBlockEntity be) {
                be.setOwnerId(placer.getId());
                be.markDirty();
            }
        }
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new LeechChestBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite()).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
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
        if (blockEntity instanceof LeechChestBlockEntity) {
            ((LeechChestBlockEntity)blockEntity).onScheduledTick();
        }

    }


    public static DoubleBlockProperties.PropertyRetriever<ChestBlockEntity, Float2FloatFunction> getAnimationProgressRetriever(final LidOpenable progress) {
        return new DoubleBlockProperties.PropertyRetriever<ChestBlockEntity, Float2FloatFunction>() {
            public Float2FloatFunction getFromBoth(ChestBlockEntity chestBlockEntity, ChestBlockEntity chestBlockEntity2) {
                return (tickDelta) -> {
                    return Math.max(chestBlockEntity.getAnimationProgress(tickDelta), chestBlockEntity2.getAnimationProgress(tickDelta));
                };
            }

            public Float2FloatFunction getFrom(ChestBlockEntity chestBlockEntity) {
                Objects.requireNonNull(chestBlockEntity);
                return chestBlockEntity::getAnimationProgress;
            }

            public Float2FloatFunction getFallback() {
                LidOpenable var10000 = progress;
                Objects.requireNonNull(var10000);
                return var10000::getAnimationProgress;
            }
        };
    }

    public BlockEntityType<? extends LeechChestBlockEntity> getExpectedEntityType() {
        return this.entityTypeRetriever.get();
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? checkType(type, this.getExpectedEntityType(), LeechChestBlockEntity::clientTick) : null;
    }
}
