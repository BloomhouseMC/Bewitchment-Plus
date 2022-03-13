package dev.mrsterner.bewitchmentplus.common.block.blockentity;

import dev.mrsterner.bewitchmentplus.api.BewitchmentPlusAPI;
import dev.mrsterner.bewitchmentplus.common.registry.BWPBlockEntityTypes;
import dev.mrsterner.bewitchmentplus.common.registry.BWPStatusEffects;
import moriyashiine.bewitchment.common.registry.BWProperties;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class YewLogBlockEntity extends BlockEntity {
    public YewLogBlockEntity(BlockPos pos, BlockState state) {
        super(BWPBlockEntityTypes.YEW_LOG_BLOCK_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, YewLogBlockEntity blockEntity) {
        if(state.get(BWProperties.CUT) && world.getBlockEntity(pos) instanceof YewLogBlockEntity){
            Box box = new Box(pos).expand(10);
            List<PlayerEntity> list = world.getNonSpectatingEntities(PlayerEntity.class, box);
            Iterator<PlayerEntity> var11 = list.iterator();
            PlayerEntity playerEntity;
            while(var11.hasNext()) {
                playerEntity = var11.next();
                if(BewitchmentPlusAPI.isLeshon(playerEntity, true)){
                    playerEntity.addStatusEffect(new StatusEffectInstance(BWPStatusEffects.HOMESTEAD, 20*2, 1, true, true));
                }
            }
        }
    }
}
