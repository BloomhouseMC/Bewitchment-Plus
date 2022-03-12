package dev.mrsterner.bewitchmentplus.common.world.generator.tree;

import com.mojang.serialization.Codec;
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
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Optional;
import java.util.Random;

public class YewTreeFeature extends Feature<DefaultFeatureConfig> {
    public YewTreeFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        //Get structure manager from World, the world we want to place the structure in
        StructureManager structureManager = context.getWorld().toServerWorld().getStructureManager();
        //Try fetch the nbt with the structure manager
        Identifier nbtLocation = new Identifier(BewitchmentPlus.MODID, "features/trees/yew_tree1");
        Optional<Structure> structureOptional = structureManager.getStructure(nbtLocation);
        if (structureOptional.isEmpty()) {
            BewitchmentPlus.LOGGER.info("NBT " + nbtLocation + " does not exist!");
            return false;
        }
        //Check if the normalizedOrigin is in fact on top of a grass block
        if (canGenerate(context.getWorld(), context.getOrigin(), context.getRandom())) {
            //Unless structureOptional.isEmpty() not catches, get the structure from the optional
            Structure structure = structureOptional.get();
            //Change the origin from the corner of the structure to the middle of the structure
            BlockPos normalizeOrigin = context.getOrigin().subtract(new Vec3i(Math.floor((double) structure.getSize().getX() / 2),0,Math.floor((double) structure.getSize().getX() / 2)));
            //Get basic placementData
            var placementData = (new StructurePlacementData()).setMirror(BlockMirror.NONE).setRotation(BlockRotation.NONE).setIgnoreEntities(false);
            //Place the structure at the normalized origin
            structure.place(context.getWorld(), normalizeOrigin, normalizeOrigin, placementData, context.getRandom(), 2);
            checkAir(context.getWorld(), normalizeOrigin);
            return true;
        }else{
            return false;
        }

    }

    // Returns if the area is a valid generation point for the tree
    // NOTE: Yew Tree has a max trunk width of 4x4, height of 9, and foliage width of ~13
    public boolean canGenerate(StructureWorldAccess world, BlockPos pos, Random random) {
        // Random check to see if the tree should place (used to control spawn weight)
        if (random.nextFloat() < 1.0F / (float) BewitchmentPlus.config.world.yewTreeWeight) {
            // Checks if there is valid dirt and if the tree isn't too high up
            if (world.getBlockState(pos.down()).isIn(BlockTags.DIRT) && pos.getY() + 10 < world.getTopY()) {
                return treeNearby(world, pos, 13, 0);
            }
        }
        return false;
    }

    // Checks the blocks around the area for other trees (prevents trees generating very close)
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

    public void checkAir(StructureWorldAccess world, BlockPos center) {
        for (BlockPos pos : BlockPos.iterateOutwards(center.down(), 4, 0, 4)) {
            if (world.getBlockState(pos).isAir() && world.getBlockState(pos.up()).isOf(BWPObjects.YEW_LOG)) {
                world.setBlockState(pos, Blocks.DIRT.getDefaultState(), 3);
            }
        }
    }
}