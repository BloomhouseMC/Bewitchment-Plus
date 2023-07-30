package dev.mrsterner.bewitchmentplus.common.block.yew;

import dev.mrsterner.bewitchmentplus.common.registry.BWPBlockEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class YewChestBlockEntity extends ChestBlockEntity {
    public final Type type;
    public final boolean trapped;

    public YewChestBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState, Type type, boolean trapped) {
        super(blockEntityType, blockPos, blockState);
        this.type = type;
        this.trapped = trapped;
    }

    public YewChestBlockEntity(BlockPos blockPos, BlockState blockState) {
        this(BWPBlockEntityTypes.YEW_CHEST_BLOCK_ENTITY, blockPos, blockState, Type.YEW, false);
    }

    @Override
    protected void onViewerCountUpdate(World world, BlockPos pos, BlockState state, int oldViewerCount, int newViewerCount) {
        super.onViewerCountUpdate(world, pos, state, oldViewerCount, newViewerCount);
        if (trapped && world != null) {
            world.updateNeighborsAlways(pos.down(), getCachedState().getBlock());
        }
    }

    public enum Type {
        YEW
    }
}