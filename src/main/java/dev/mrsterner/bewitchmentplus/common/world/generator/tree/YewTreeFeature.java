package dev.mrsterner.bewitchmentplus.common.world.generator.tree;

import com.mojang.serialization.Codec;

import java.util.Optional;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class YewTreeFeature extends Feature<DefaultFeatureConfig> {
    public YewTreeFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {

        StructureManager templatemanager = context.getWorld().toServerWorld().getStructureManager();
        Optional<Structure> structureTemplate = templatemanager.getStructure(new Identifier(BewitchmentPlus.MODID, "features/trees/yew_tree1"));

        if (structureTemplate.isEmpty()) {
            System.out.println("NBT does not exist!");
            return false;
        }
        Structure template = structureTemplate.get();

        var placementsettings = (new StructurePlacementData()).setMirror(BlockMirror.NONE).setRotation(BlockRotation.NONE).setIgnoreEntities(false);
        template.place( context.getWorld(), context.getOrigin(), context.getOrigin(), placementsettings, context.getRandom(), 2);
        return true; //TODO not generate without conditions
    }
}