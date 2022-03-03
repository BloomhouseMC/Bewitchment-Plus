package dev.mrsterner.bewitchmentplus.common.block.blockentity;

import dev.mrsterner.bewitchmentplus.common.registry.BWPBlockEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PentacleBlockEntity extends BlockEntity {

    public PentacleBlockEntity(BlockPos pos, BlockState state) {
        super(BWPBlockEntityTypes.PENTACLE, pos, state);
    }


    public static void tick(World world, BlockPos pos, BlockState state, PentacleBlockEntity blockEntity) {

    }
}
