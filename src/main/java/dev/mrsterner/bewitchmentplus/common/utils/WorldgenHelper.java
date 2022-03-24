package dev.mrsterner.bewitchmentplus.common.utils;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.common.registry.BWPObjects;
import net.minecraft.block.Blocks;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;

import java.util.Random;

public class WorldgenHelper {
    /**
     * Yew Tree has a max trunk width of 4x4, height of 9, and foliage width of ~13
     * @param world
     * @param pos
     * @param random
     * @return if the area is a valid generation point for the tree
     */
    public static boolean canGenerate(StructureWorldAccess world, BlockPos pos, Random random) {
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
     *
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
