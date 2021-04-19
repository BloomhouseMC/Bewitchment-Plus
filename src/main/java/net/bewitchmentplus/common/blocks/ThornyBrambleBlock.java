package net.bewitchmentplus.common.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SugarCaneBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import java.util.Random;

public class ThornyBrambleBlock extends SugarCaneBlock {
	public static final IntProperty AGE;

	public ThornyBrambleBlock(Settings settings) {
		super(settings);
		this.shouldDropItemsOnExplosion(null);
		this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(AGE, 0));
	}

	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		super.randomTick(state, world, pos, random);
	}

	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		BlockState downState = world.getBlockState(pos.down());
		return downState.isOf(Blocks.GRASS_BLOCK) || downState.isOf(Blocks.DIRT) || downState.isOf(Blocks.COARSE_DIRT) || downState.isOf(Blocks.PODZOL) || downState.isOf(Blocks.SAND) || downState.isOf(Blocks.RED_SAND);
	}

	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		if (!world.isClient && entity instanceof LivingEntity) {
			LivingEntity livingEntity = (LivingEntity) entity;
			livingEntity.damage(DamageSource.CACTUS, 2);
		}
	}

	@Environment(EnvType.CLIENT)
	public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
		return ItemStack.EMPTY;
	}

	public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
		if (block == this && world.removeBlock(pos, false)) {
			world.removeBlock(pos, false);
		}

		super.neighborUpdate(state, world, pos, block, fromPos, notify);
	}

	private boolean increaseAge(BlockState state, World world, BlockPos pos) {
		int i = (Integer)state.get(AGE);
		if (i < 3) {
			world.setBlockState(pos, (BlockState)state.with(AGE, i + 1), 2);
			return false;
		} else {
			world.removeBlock(pos, false);
			return true;
		}
	}

	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(AGE);
	}

	static {
		AGE = Properties.AGE_3;
	}	
}
