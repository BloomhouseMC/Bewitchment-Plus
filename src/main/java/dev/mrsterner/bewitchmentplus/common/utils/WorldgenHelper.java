package dev.mrsterner.bewitchmentplus.common.utils;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.common.registry.BWPObjects;
import net.minecraft.block.Blocks;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.StructureWorldAccess;

import java.util.Optional;
import java.util.Random;

public class WorldgenHelper {

    /**
     * @author - MrSterner
     * @param nbtLocation the location of the structure file about to be placed
     * @param world the structures world access
     * @param origin the blockpos of the structure corner
     * @param chance the probability of a successful placement
     * @return true if the placement of the structure was successfull
     */
    public static boolean generateNbtFeature(Identifier nbtLocation,StructureWorldAccess world, BlockPos origin, float chance){
        StructureManager structureManager = world.toServerWorld().getStructureManager();
        //Try fetch the nbt with the structure manager
        Optional<Structure> structureOptional = structureManager.getStructure(nbtLocation);
        if (structureOptional.isEmpty()) {
            BewitchmentPlus.LOGGER.info("NBT " + nbtLocation + " does not exist!");
        }else if (touchGrass(world, origin,world.getRandom(), chance, structureOptional.get().getSize().getY())) {
            //Unless structureOptional.isEmpty() not catches, get the structure from the optional
            Structure structure = structureOptional.get();
            //Change the origin from the corner of the structure to the middle of the structure
            BlockPos normalizeOrigin = origin.subtract(new Vec3i(Math.floor((double) structure.getSize().getX() / 2), 0, Math.floor((double) structure.getSize().getX() / 2)));
            //Get basic placementData
            StructurePlacementData placementData = (new StructurePlacementData()).setMirror(BlockMirror.NONE).setRotation(BlockRotation.NONE).setIgnoreEntities(false);
            //Place the structure at the normalized origin
            BewitchmentPlus.LOGGER.info("NBT " + normalizeOrigin + " generated");
            structure.place(world, normalizeOrigin.down(), normalizeOrigin, placementData, world.getRandom(), 2);
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

    /**
     * @author - MrSterner
     * @param world
     * @param normalizeOrigin
     * @return
     */
    public static boolean touchGrass(StructureWorldAccess world, BlockPos normalizeOrigin) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int yOffset = 0; yOffset <= 1; ++yOffset) {
            for (int xOffset = -1; xOffset <= 1; ++xOffset) {
                for (int zOffset = -1; zOffset <= 1; ++zOffset) {
                    BlockPos test = mutable.set(normalizeOrigin.getX() + xOffset, normalizeOrigin.getY() + yOffset, normalizeOrigin.getZ() + zOffset);
                    if (world.getBlockState(test).isIn(BlockTags.BASE_STONE_OVERWORLD)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
