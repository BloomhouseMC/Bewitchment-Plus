package dev.mrsterner.bewitchmentplus.common.block.blockentity;

import dev.mrsterner.bewitchmentplus.common.registry.BWPBlockEntityTypes;
import dev.mrsterner.bewitchmentplus.common.registry.BWPObjects;
import dev.mrsterner.bewitchmentplus.common.registry.BWPStatusEffects;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class YewLogBlockEntity extends BlockEntity {
    private int effectGiverCoolerDowner;
    public YewLogBlockEntity(BlockPos pos, BlockState state) {
        super(BWPBlockEntityTypes.YEW_LOG_BLOCK_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, YewLogBlockEntity blockEntity) {
        blockEntity.effectGiverCoolerDowner++;
        if(blockEntity.effectGiverCoolerDowner > 40){
            blockEntity.effectGiverCoolerDowner = 0;
            Box box = new Box(pos).expand(64);
            List<PlayerEntity> list = world.getNonSpectatingEntities(PlayerEntity.class, box);
            Iterator<PlayerEntity> var11 = list.iterator();
            PlayerEntity playerEntity;
            while(var11.hasNext()) {
                playerEntity = var11.next();
                if(playerEntity.getEquippedStack(EquipmentSlot.HEAD).getItem().equals(BWPObjects.LESHON_SKULL.asItem())){
                    playerEntity.addStatusEffect(new StatusEffectInstance(BWPStatusEffects.HOMESTEAD, 20 * 2, 1, true, true));
                }
            }
        }
    }
}
