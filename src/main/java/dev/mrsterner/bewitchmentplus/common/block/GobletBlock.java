package dev.mrsterner.bewitchmentplus.common.block;

import dev.mrsterner.bewitchmentplus.common.block.blockentity.GobletBlockEntity;
import dev.mrsterner.bewitchmentplus.common.item.GobletBlockItem;
import dev.mrsterner.bewitchmentplus.common.registry.BWPBlockEntityTypes;
import dev.mrsterner.bewitchmentplus.common.registry.BWPObjects;
import dev.mrsterner.bewitchmentplus.common.utils.RenderHelper;
import moriyashiine.bewitchment.common.registry.BWObjects;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.potion.PotionUtil;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GobletBlock extends Block implements BlockEntityProvider, Waterloggable {
    public static final IntProperty LIQUID_STATE = IntProperty.of("liquid_state", 0,4);
    private static final VoxelShape SHAPE;
    public Item dropItem;

    public GobletBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(Properties.WATERLOGGED, false).with(LIQUID_STATE, 0));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            ((GobletBlockEntity) world.getBlockEntity(pos)).onUse(state, world, pos, player, hand, hit);
        }
        return ActionResult.success(world.isClient);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof GobletBlockEntity gobletBlockEntity) {
            if (!world.isClient && player.isCreative() && !gobletBlockEntity.isEmpty() && gobletBlockEntity.getColor() != 0) {
                ItemStack itemStack = new ItemStack(dropItem);
                blockEntity.setStackNbt(itemStack);
                ItemEntity itemEntity = new ItemEntity(world, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, itemStack);
                itemEntity.setToDefaultPickupDelay();
                world.spawnEntity(itemEntity);
            }
        }
        super.onBreak(world, pos, state, player);
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        ItemStack itemStack = super.getPickStack(world, pos, state);
        world.getBlockEntity(pos, BWPBlockEntityTypes.GOBLET).ifPresent((blockEntity) -> {
            if(blockEntity.getColor() != 0)
            blockEntity.setStackNbt(itemStack);
        });
        return itemStack;
    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder) {
        BlockEntity blockEntity = builder.get(LootContextParameters.BLOCK_ENTITY);
        if (blockEntity instanceof GobletBlockEntity jarBlockEntity) {
            builder = builder.addDynamicDrop(ShulkerBoxBlock.CONTENTS_DYNAMIC_DROP_ID, (consumer) -> {
                for(int i = 0; i < jarBlockEntity.size(); ++i) {
                    consumer.accept(jarBlockEntity.getStack(i));
                }
            });
        }
        return super.getDroppedStacks(state, builder);
    }



    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(Properties.WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @SuppressWarnings("ConstantConditions")
    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return super.getPlacementState(ctx).with(LIQUID_STATE, 0).with(Properties.WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if(world.getBlockEntity(pos) instanceof GobletBlockEntity gobletBlock){
            gobletBlock.setGoblet((GobletBlockItem) itemStack.getItem());
            world.setBlockState(pos, state.with(LIQUID_STATE, (gobletBlock.getStack(0).getItem()) == Items.HONEY_BOTTLE ? 2 : (gobletBlock.getStack(0).getItem()) == BWObjects.BOTTLE_OF_BLOOD ? 3 : (gobletBlock.getStack(0).getItem()) == Items.POTION ? 1 : (gobletBlock.getStack(0).getItem()) == BWPObjects.UNICORN_BLOOD ? 4 : 0));
            gobletBlock.setColor((gobletBlock.getStack(0).getItem()) == Items.HONEY_BOTTLE ? RenderHelper.HONEY_COLOR : (gobletBlock.getStack(0).getItem()) == BWObjects.BOTTLE_OF_BLOOD ? RenderHelper.BLOOD_COLOR : (gobletBlock.getStack(0).getItem()) == Items.POTION ? PotionUtil.getColor(gobletBlock.getStack(0)) : (gobletBlock.getStack(0).getItem()) == BWPObjects.UNICORN_BLOOD ? RenderHelper.UNICORN_BLOOD_COLOR : 0);
        }
        super.onPlaced(world, pos, state, placer, itemStack);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(Properties.WATERLOGGED, LIQUID_STATE);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    static {
        SHAPE = VoxelShapes.union(createCuboidShape(6, 0, 6, 10, 1, 10), createCuboidShape(7, 1, 7, 9, 3, 9), createCuboidShape(5.5, 3, 5.5, 10.5, 8, 6.5), createCuboidShape(5.5, 3, 6.5, 6.5, 8, 9.5), createCuboidShape(9.5, 3, 6.5, 10.5, 8, 9.5), createCuboidShape(5.5, 3, 9.5, 10.5, 8, 10.5), createCuboidShape(6.5, 3, 6.5, 9.5, 8, 9.5));

    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new GobletBlockEntity(pos, state);
    }
}
