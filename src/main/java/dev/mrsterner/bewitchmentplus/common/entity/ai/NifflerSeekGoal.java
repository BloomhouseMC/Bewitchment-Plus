package dev.mrsterner.bewitchmentplus.common.entity.ai;

import dev.mrsterner.bewitchmentplus.common.entity.NifflerEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SugarCaneBlock;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NifflerSeekGoal extends Goal {
    public final NifflerEntity niffler;

    public NifflerSeekGoal(NifflerEntity niffler) {
        this.niffler = niffler;
    }

    @Override
    public boolean canStart() {
        int rangeCheck = 8;
        BlockPos blockPos = niffler.getBlockPos();
        List<Block> blockList = new ArrayList<>();
        for(double x = -rangeCheck; x <= rangeCheck; ++x) {
            for (double y = -rangeCheck; y <= rangeCheck; ++y) {
                for (double z = -rangeCheck; z <= rangeCheck; ++z) {
                    BlockPos lootPos = new BlockPos(blockPos.getX() + x, blockPos.getY() + y, blockPos.getZ() + z);
                    blockList.add(niffler.world.getBlockState(lootPos).getBlock());

                }
            }
        }
        while (blockList.iterator().hasNext()){

        }

        //List<ChestBlockEntity> list = this.niffler.world.getBlo(ChestBlockEntity.class, this.niffler.getBoundingBox().expand(10,3,10));
        return false;
    }
}
