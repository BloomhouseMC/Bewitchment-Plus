package dev.mrsterner.bewitchmentplus.common.interfaces;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.Optional;

public interface CrownOfForest {
    static Optional<CrownOfForest> of(Object context) {
        if (context instanceof CrownOfForest) {
            return Optional.of(((CrownOfForest) context));
        }
        return Optional.empty();
    }

    HashMap<BlockPos, BlockState> getParsedMap();

    void setParsedMap(HashMap<BlockPos, BlockState> parsedMap);
}
