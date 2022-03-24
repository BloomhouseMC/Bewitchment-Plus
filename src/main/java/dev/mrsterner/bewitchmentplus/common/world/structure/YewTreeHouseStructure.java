package dev.mrsterner.bewitchmentplus.common.world.structure;

import net.minecraft.structure.*;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

import java.util.Optional;

public class YewTreeHouseStructure extends StructureFeature<StructurePoolFeatureConfig> {
    public YewTreeHouseStructure() {
        super(StructurePoolFeatureConfig.CODEC, YewTreeHouseStructure::createPiecesGenerator, PostPlacementProcessor.EMPTY);
    }

    private static boolean isFeatureChunk(StructureGeneratorFactory.Context<StructurePoolFeatureConfig> context) {
        return true;
    }

    public static Optional<StructurePiecesGenerator<StructurePoolFeatureConfig>> createPiecesGenerator(StructureGeneratorFactory.Context<StructurePoolFeatureConfig> context) {
        if (!YewTreeHouseStructure.isFeatureChunk(context)) {
            return Optional.empty();
        }
        BlockPos blockpos = context.chunkPos().getCenterAtY(0);
        return StructurePoolBasedGenerator.generate(context, PoolStructurePiece::new, new BlockPos(blockpos), false, true);
    }
}