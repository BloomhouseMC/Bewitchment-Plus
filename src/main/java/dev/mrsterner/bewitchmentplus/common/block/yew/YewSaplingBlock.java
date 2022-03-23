package dev.mrsterner.bewitchmentplus.common.block.yew;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.common.registry.BWPObjects;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.chunk.ChunkGenerator;

import java.util.Optional;
import java.util.Random;

public class YewSaplingBlock extends PlantBlock implements Fertilizable {
    public static final IntProperty STAGE = Properties.STAGE;
    protected static final VoxelShape SHAPE = Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 12.0, 14.0);

    public YewSaplingBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(STAGE, Integer.valueOf(0)));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.getLightLevel(pos.up()) >= 9 && random.nextInt(7) == 0) {
            this.generate(world, pos, state, random);
        }

    }

    public void generate(ServerWorld world, BlockPos pos, BlockState state, Random random) {
        if (state.get(STAGE) == 0) {
            world.setBlockState(pos, state.cycle(STAGE), Block.NO_REDRAW);
        } else {
            generateYewTree(world, world.getChunkManager().getChunkGenerator(), pos, state, random);
        }

    }

    private void generateYewTree(ServerWorld world, ChunkGenerator chunkGenerator, BlockPos pos, BlockState state, Random random) {
        StructureManager structureManager = world.toServerWorld().getStructureManager();
        Identifier nbtLocation = new Identifier(BewitchmentPlus.MODID, "features/trees/yew_tree1");
        //Try fetch the nbt with the structure manager
        Optional<Structure> structureOptional = structureManager.getStructure(nbtLocation);
        if (structureOptional.isEmpty()) {
            BewitchmentPlus.LOGGER.info("NBT " + nbtLocation + " does not exist!");
        }else if (canGenerate(world, pos, random)) {
            //Unless structureOptional.isEmpty() not catches, get the structure from the optional
            Structure structure = structureOptional.get();
            //Change the origin from the corner of the structure to the middle of the structure
            BlockPos normalizeOrigin = pos.subtract(new Vec3i(Math.floor((double) structure.getSize().getX() / 2),0,Math.floor((double) structure.getSize().getX() / 2)));
            //Get basic placementData
            var placementData = (new StructurePlacementData()).setMirror(BlockMirror.NONE).setRotation(BlockRotation.NONE).setIgnoreEntities(false);
            //Place the structure at the normalized origin
            structure.place(world, normalizeOrigin, normalizeOrigin, placementData, random, 2);
            checkAir(world, normalizeOrigin);
        }
    }

    /**
     * Yew Tree has a max trunk width of 4x4, height of 9, and foliage width of ~13
     * @param world
     * @param pos
     * @param random
     * @return if the area is a valid generation point for the tree
     */
    public boolean canGenerate(StructureWorldAccess world, BlockPos pos, Random random) {
        // Random check to see if the tree should place (used to control spawn weight)
        if (random.nextFloat() < (1.0F / (float) BewitchmentPlus.config.world.yewTreeWeight)) {
            // Checks if there is valid dirt and if the tree isn't too high up
            if (world.getBlockState(pos.down()).isIn(BlockTags.DIRT) && pos.getY() + 10 < world.getTopY()) {
                return treeNearby(world, pos, 13, 0);
            }
        }
        return false;
    }

    /**
     * Checks the blocks around the area for other trees (prevents trees generating very close)
     * @param world
     * @param pos
     * @param treeHeight
     * @param distance
     * @return
     */
    public boolean treeNearby(StructureWorldAccess world, BlockPos pos, int treeHeight, int distance) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        for (int yOffset = 0; yOffset <= treeHeight + 1; ++yOffset) {
            for (int xOffset = -distance; xOffset <= distance; ++xOffset) {
                for (int zOffset = -distance; zOffset <= distance; ++zOffset) {
                    BlockPos test = mutable.set(x + xOffset, y + yOffset, z + zOffset);
                    if (world.getBlockState(test).isIn(BlockTags.LOGS) || world.getBlockState(test).isIn(BlockTags.LEAVES)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     *
     * @param world
     * @param center
     */
    public void checkAir(StructureWorldAccess world, BlockPos center) {
        for (BlockPos pos : BlockPos.iterateOutwards(center.down(), 4, 0, 4)) {
            if (world.getBlockState(pos).isAir() && world.getBlockState(pos.up()).isOf(BWPObjects.YEW_LOG)) {
                world.setBlockState(pos, Blocks.DIRT.getDefaultState(), 3);
            }
        }
    }

    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return (double)world.random.nextFloat() < 0.45;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        this.generate(world, pos, state, random);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(STAGE);
    }
}
