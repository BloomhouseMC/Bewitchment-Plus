package net.bewitchmentplus.common.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SugarCaneBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class ThornyBrambleBlock extends SugarCaneBlock {
	public ThornyBrambleBlock(Settings settings) {
		super(settings);
		this.shouldDropItemsOnExplosion(null);
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
}
