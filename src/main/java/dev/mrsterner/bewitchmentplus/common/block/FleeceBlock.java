package dev.mrsterner.bewitchmentplus.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class FleeceBlock extends Block {
    private final DyeColor color;
    private final boolean isCarpet;
    public FleeceBlock(DyeColor color,Settings settings, boolean carpet) {
        super(settings);
        this.color = color;
        this.isCarpet = carpet;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return isCarpet ? Block.createCuboidShape(0,0,0, 16, 1, 16) : VoxelShapes.fullCube();
    }

    public DyeColor getColor() {
        return this.color;
    }
}
