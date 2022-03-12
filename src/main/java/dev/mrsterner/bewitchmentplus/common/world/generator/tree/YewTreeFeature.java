package dev.mrsterner.bewitchmentplus.common.world.generator.tree;

import com.mojang.serialization.Codec;

import java.util.Optional;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import net.minecraft.block.Blocks;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

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
        if (context.getWorld().getBlockState(context.getOrigin().down()).getBlock() == Blocks.GRASS_BLOCK) {//TODO maybe check if the area is actually clear for placement
            //Unless structureOptional.isEmpty() not catches, get the structure from the optional
            Structure structure = structureOptional.get();
            //Change the origin from the corner of the structure to the middle of the structure
            BlockPos normalizeOrigin = context.getOrigin().subtract(new Vec3i(Math.floor((double) structure.getSize().getX() / 2),0,Math.floor((double) structure.getSize().getX() / 2)));
            //Get basic placementData
            var placementData = (new StructurePlacementData()).setMirror(BlockMirror.NONE).setRotation(BlockRotation.NONE).setIgnoreEntities(false);
            //Place the structure at the normalized origin
            structure.place(context.getWorld(), normalizeOrigin, normalizeOrigin, placementData, context.getRandom(), 2);
            return true;
        }else{
            return false;
        }

    }
}