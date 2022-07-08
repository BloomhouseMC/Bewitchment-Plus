package dev.mrsterner.bewitchmentplus.common.utils;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.common.registry.BWPObjects;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.StructureTemplateManager;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.structure.Structure;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;


public class WorldgenHelper {
    private int offsetX, offsetZ;

    /** This method places and nbt structure at a given origin
     * @author - MrSterner
     * @param nbtLocation the location of the structure file about to be placed
     * @param world the structures world access
     * @param origin the blockpos of the structure corner
     * @param chance the probability of a successful placement
     * @return true if the placement of the structure was successfull
     */
    public static boolean generateNbtFeature(Identifier nbtLocation, StructureWorldAccess world, BlockPos origin, float chance){
        StructureTemplateManager templateManager = world.getServer().getStructureTemplateManager();
        //Try fetch the nbt with the structure manager
        Optional<StructureTemplate> structureOptional = templateManager.getTemplate(nbtLocation);
        if (!structureOptional.isPresent()) {
            BewitchmentPlus.LOGGER.info("NBT " + nbtLocation + " does not exist!");
        }else if (touchGrass(world, origin, world.getRandom(), chance, structureOptional.get().getSize().getY())) {
            //Unless structureOptional.isEmpty() not catches, get the structure from the optional
            StructureTemplate structure = structureOptional.get();
            //Change the origin from the corner of the structure to the middle of the structure
            BlockPos normalizeOrigin = origin.subtract(new Vec3i(Math.floor((double) structure.getSize().getX() / 2), 0, Math.floor((double) structure.getSize().getX() / 2)));
            //Get basic placementData
            StructurePlacementData placementData = (new StructurePlacementData()).setMirror(BlockMirror.NONE).setRotation(BlockRotation.NONE).setIgnoreEntities(false);
            //Place the structure at the normalized origin
            structure.place(world, normalizeOrigin, normalizeOrigin, placementData, world.getRandom(), 2);
            WorldgenHelper.checkAir(world, normalizeOrigin);
            return true;
        }
        return false;
    }

    /**
     * @author - Safro
     * Yew Tree has a max trunk width of 4x4, height of 9, and foliage width of ~13
     * @param world
     * @param pos
     * @param random
     * @return if the area is a valid generation point for the tree
     */
    public static boolean touchGrass(StructureWorldAccess world, BlockPos pos, Random random, float chance, int structureHeight) {
        // Random check to see if the tree should place (used to control spawn weight)
        if (random.nextFloat() < (1.0F / chance)) {
            // Checks if there is valid dirt and if the tree isn't too high up
            if (world.getBlockState(pos.down()).isIn(BlockTags.DIRT) && pos.getY() + structureHeight < world.getTopY()) {
                return treeNearby(world, pos, structureHeight, 0);
            }
        }
        return false;
    }

    /**
     * @author - Safro
     * Checks the blocks around the area for other trees (prevents trees generating very close)
     * @param world
     * @param pos
     * @param treeHeight
     * @param distance
     * @return
     */
    public static boolean treeNearby(StructureWorldAccess world, BlockPos pos, int treeHeight, int distance) {
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
     * @author - Safro
     * @param world
     * @param center
     */
    public static void checkAir(StructureWorldAccess world, BlockPos center) {
        for (BlockPos pos : BlockPos.iterateOutwards(center.down(), 4, 0, 4)) {
            if (world.getBlockState(pos).isAir() && world.getBlockState(pos.up()).isOf(BWPObjects.YEW_LOG)) {
                world.setBlockState(pos, Blocks.DIRT.getDefaultState(), 3);
            }
        }
    }
}
