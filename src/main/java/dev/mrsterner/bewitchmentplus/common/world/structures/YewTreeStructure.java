package dev.mrsterner.bewitchmentplus.common.world.structures;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import net.minecraft.structure.*;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

import java.util.Optional;

public class YewTreeStructure extends StructureFeature<StructurePoolFeatureConfig> {
    public YewTreeStructure() {
        super(StructurePoolFeatureConfig.CODEC, YewTreeStructure::createPiecesGenerator, PostPlacementProcessor.EMPTY);
    }

    private static boolean isFeatureChunk(StructureGeneratorFactory.Context<StructurePoolFeatureConfig> context) {
        return true;
    }

    public static Optional<StructurePiecesGenerator<StructurePoolFeatureConfig>> createPiecesGenerator(StructureGeneratorFactory.Context<StructurePoolFeatureConfig> context) {
        if (!YewTreeStructure.isFeatureChunk(context)) {
            return Optional.empty();
        }
        BlockPos blockpos = context.chunkPos().getCenterAtY(0);
        Optional<StructurePiecesGenerator<StructurePoolFeatureConfig>> structurePiecesGenerator =
        StructurePoolBasedGenerator.generate(context, PoolStructurePiece::new, new BlockPos(blockpos), false, true);
        if(structurePiecesGenerator.isPresent()) {
            BewitchmentPlus.LOGGER.info("Yew at {}", blockpos);
        }
        return structurePiecesGenerator;
    }
}
