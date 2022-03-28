package dev.mrsterner.bewitchmentplus.common.world.feature;

import com.mojang.serialization.Codec;
import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.common.utils.WorldgenHelper;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Optional;

public class LotusTreeFeature extends Feature<ProbabilityConfig> {
    public LotusTreeFeature(Codec<ProbabilityConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<ProbabilityConfig> context) {
        StructureManager structureManager = context.getWorld().toServerWorld().getStructureManager();
        //Try fetch the nbt with the structure manager
        Identifier nbtIdentifier = new Identifier(BewitchmentPlus.MODID, "lotus");
        Optional<Structure> structureOptional = structureManager.getStructure(nbtIdentifier);
        if (structureOptional.isEmpty()) {
            BewitchmentPlus.LOGGER.info("NBT " + nbtIdentifier + " does not exist!");
        }else if (WorldgenHelper.treeNearby(context.getWorld(), context.getOrigin(), structureOptional.get().getSize().getY(), 0) && context.getWorld().getRandom().nextFloat() < 0.25F) {//TODO add config
            //Unless structureOptional.isEmpty() not catches, get the structure from the optional
            Structure structure = structureOptional.get();
            //Change the origin from the corner of the structure to the middle of the structure
            BlockPos normalizeOrigin = context.getOrigin().subtract(new Vec3i(Math.floor((double) structure.getSize().getX() / 2),0,Math.floor((double) structure.getSize().getX() / 2)));
            if(WorldgenHelper.touchGrass(context.getWorld(), normalizeOrigin)){
                //Get basic placementData
                StructurePlacementData placementData = (new StructurePlacementData()).setMirror(BlockMirror.NONE).setRotation(BlockRotation.NONE).setIgnoreEntities(false);
                //Place the structure at the normalized origin
                structure.place(context.getWorld(), normalizeOrigin.down(), normalizeOrigin, placementData, context.getWorld().getRandom(), 2);
                WorldgenHelper.checkAir(context.getWorld(), normalizeOrigin);
                return  true;
            }else{
                return false;
            }
        }else{
            return false;
        }
        return false;
    }
}
