package dev.mrsterner.bewitchmentplus.common.world.structures;

import com.mojang.serialization.Codec;
import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import net.minecraft.structure.*;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;
import org.apache.logging.log4j.Level;

import java.util.Optional;

public class YewTreeStructure extends StructureFeature<StructurePoolFeatureConfig> {
    public YewTreeStructure() {
        super(StructurePoolFeatureConfig.CODEC, YewTreeStructure::createPiecesGenerator, PostPlacementProcessor.EMPTY);
    }

    private static boolean isFeatureChunk(StructureGeneratorFactory.Context<StructurePoolFeatureConfig> context) {
        ChunkPos chunkpos = context.chunkPos();
        return !context.chunkGenerator().method_41053(StructureSetKeys.OCEAN_MONUMENTS, context.seed(), chunkpos.x, chunkpos.z, 10);
    }

    public static Optional<StructurePiecesGenerator<StructurePoolFeatureConfig>> createPiecesGenerator(StructureGeneratorFactory.Context<StructurePoolFeatureConfig> context) {
        if (!YewTreeStructure.isFeatureChunk(context)) {
            return Optional.empty();
        }
        BlockPos blockpos = context.chunkPos().getCenterAtY(0);
        int topLandY = context.chunkGenerator().getHeightOnGround(blockpos.getX(), blockpos.getZ(), Heightmap.Type.WORLD_SURFACE_WG, context.world());
        blockpos = blockpos.up(topLandY + 10);

        Optional<StructurePiecesGenerator<StructurePoolFeatureConfig>> structurePiecesGenerator =
        StructurePoolBasedGenerator.generate(context, PoolStructurePiece::new, blockpos, false, false);
        if(structurePiecesGenerator.isPresent()) {
            BewitchmentPlus.LOGGER.info("Yew at {}", blockpos);
        }
        return structurePiecesGenerator;
    }
}
