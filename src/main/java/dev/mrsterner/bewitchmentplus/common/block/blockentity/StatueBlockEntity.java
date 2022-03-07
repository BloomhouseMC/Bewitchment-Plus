package dev.mrsterner.bewitchmentplus.common.block.blockentity;

import dev.mrsterner.bewitchmentplus.common.item.StatueBlockItem;
import dev.mrsterner.bewitchmentplus.common.registry.BWPBlockEntityTypes;
import dev.mrsterner.bewitchmentplus.common.registry.BWPEntityTypes;
import dev.mrsterner.bewitchmentplus.common.registry.BWPObjects;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public class StatueBlockEntity extends BlockEntity {
    public StatueBlockEntity(BlockPos pos, BlockState state) {
        super(BWPBlockEntityTypes.STATUE_BLOCK_ENTITY, pos, state);
    }
    private StatueBlockItem statue = (StatueBlockItem) BWPObjects.LILITH_STATUE.asItem();
    public StatueBlockItem getStatue() {
        return statue;
    }

    public void setStatue(StatueBlockItem statue) {
        this.statue = statue;
    }
}
