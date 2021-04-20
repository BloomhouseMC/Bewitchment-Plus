package net.bewitchmentplus.common.statuseffects;

import moriyashiine.bewitchment.common.statuseffect.EmptyStatusEffect;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GrowthStatusEffect extends EmptyStatusEffect {

	public GrowthStatusEffect(StatusEffectType type, int color) {
		super(type, color);
	}

	@Override
	public boolean isInstant() {
		return true;
	}

	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier) {
		return true;
	}

	@Override
	public void applyUpdateEffect(LivingEntity entity, int amplifier) {
		int radius = amplifier + 1;
		World world = entity.world;
		BlockPos initialPosition = entity.getBlockPos();
		for (BlockPos position : BlockPos.iterate(initialPosition.add(-radius, -radius, -radius), initialPosition.add(radius, radius, radius))) {
			BlockState blockState = entity.world.getBlockState(position);
			{
				if (blockState.getBlock() instanceof Fertilizable) {
					Fertilizable fertilizable = (Fertilizable) blockState.getBlock();
					if (fertilizable.isFertilizable(world, position, entity.world.getBlockState(position), false)) {
						if (fertilizable.canGrow(world, world.random, position, entity.world.getBlockState(position))) {
							BoneMealItem.useOnFertilizable(new ItemStack(Items.BONE_MEAL), entity.world, position);
							BoneMealItem.useOnGround(new ItemStack(Items.BONE_MEAL), entity.world, position, null);
							fertilizable.grow((ServerWorld) entity.world, world.random, position, entity.world.getBlockState(position));
						}
					}
				}
			}
		}
	}
}