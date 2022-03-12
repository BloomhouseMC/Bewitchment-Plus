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
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class YewTreeFeature extends Feature<DefaultFeatureConfig> {
    public YewTreeFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureManager structureManager = context.getWorld().toServerWorld().getStructureManager();
        Optional<Structure> structureOptional = structureManager.getStructure(new Identifier(BewitchmentPlus.MODID, "features/trees/yew_tree1"));
        if (structureOptional.isEmpty()) {
            System.out.println("NBT does not exist!");
            return false;
        }
        Structure structure = structureOptional.get();
        BlockPos newOrigin = context.getOrigin().subtract(new Vec3i(Math.floor((double) structure.getSize().getX() / 2),0,Math.floor((double) structure.getSize().getX() / 2)));
        BlockPos top = context.getWorld().getTopPosition(Heightmap.Type.WORLD_SURFACE, newOrigin);
        if (context.getWorld().getBlockState(newOrigin.down()).getBlock() == Blocks.GRASS_BLOCK) {
            var placementsettings = (new StructurePlacementData()).setMirror(BlockMirror.NONE).setRotation(BlockRotation.NONE).setIgnoreEntities(false);
            structure.place(context.getWorld(), newOrigin, context.getOrigin(), placementsettings, context.getRandom(), 2);
            return true;
        }else{
            return false;
        }

    }
}