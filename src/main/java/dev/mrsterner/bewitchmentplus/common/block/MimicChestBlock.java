package dev.mrsterner.bewitchmentplus.common.block;

import dev.mrsterner.bewitchmentplus.common.block.blockentity.MimicChestBlockEntity;
import dev.mrsterner.bewitchmentplus.common.registry.BWPBlockEntityTypes;
import moriyashiine.bewitchment.common.block.BWChestBlock;
import moriyashiine.bewitchment.common.block.entity.interfaces.TaglockHolder;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;


public class MimicChestBlock extends BWChestBlock {

    public MimicChestBlock(Settings settings, Supplier<BlockEntityType<? extends ChestBlockEntity>> supplier, boolean trapped) {
        super(settings, supplier, trapped);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new MimicChestBlockEntity(BWPBlockEntityTypes.MIMIC_CHEST_BLOCK_ENTITY, pos, state, MimicChestBlockEntity.BWPType.LEECH, trapped);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        TaglockHolder.onUse(world, pos, player);
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        if (!world.isClient && placer != null) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            TaglockHolder taglockHolder = (TaglockHolder) blockEntity;
            taglockHolder.setOwner(placer.getUuid());
            taglockHolder.syncTaglockHolder(blockEntity);
            blockEntity.markDirty();
        }
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!world.isClient && state.getBlock() != newState.getBlock() && world.getBlockEntity(pos) instanceof TaglockHolder taglockHolder) {
            ItemScatterer.spawn(world, pos, taglockHolder.getTaglockInventory());
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }
}