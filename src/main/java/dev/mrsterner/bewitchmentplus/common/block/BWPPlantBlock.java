package dev.mrsterner.bewitchmentplus.common.block;

import dev.mrsterner.bewitchmentplus.common.registry.BWPObjects;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

@SuppressWarnings("deprecation")
public class BWPPlantBlock extends PlantBlock {
    public BWPPlantBlock(Settings settings) {
        super(settings.noCollision());
    }
    protected static final VoxelShape SHAPE = Block.createCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 10.0D, 11.0D);

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        super.onEntityCollision(state, world, pos, entity);
        if(world.getBlockState(pos).getBlock().equals(BWPObjects.EMBERGRASS)){
            entity.setOnFireFor(2);
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Vec3d vec3d = state.getModelOffset(world, pos);
        return SHAPE.offset(vec3d.x, vec3d.y, vec3d.z);
    }
    @Override
    public OffsetType getOffsetType() {
        return OffsetType.XZ;
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);
        if(world.getBlockState(pos).getBlock().equals(BWPObjects.EMBERGRASS)){
            world.addParticle(ParticleTypes.FLAME, pos.getX() + 0.5 + world.getRandom().nextGaussian()/4,pos.getY() + 0.5 + world.getRandom().nextGaussian()/4, pos.getZ() + 0.5 + world.getRandom().nextGaussian()/4, 0, 0, 0);

        }
    }
}