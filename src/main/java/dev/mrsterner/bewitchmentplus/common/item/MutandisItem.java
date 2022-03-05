package dev.mrsterner.bewitchmentplus.common.item;

import dev.mrsterner.bewitchmentplus.common.registry.BWPTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.Waterloggable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;

public class MutandisItem extends Item {
    public static final BooleanProperty WATERLOGGED = BooleanProperty.of("waterlogged");
    public MutandisItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if(BWPTags.MUTANDIS.contains(context.getWorld().getBlockState(context.getBlockPos()).getBlock())){
            BlockState blockState = BWPTags.MUTANDIS.getRandom(context.getWorld().random).getDefaultState();
            if(blockState.getBlock() instanceof Waterloggable){
                context.getWorld().setBlockState(context.getBlockPos(),blockState.with(WATERLOGGED, false));
            }else{
                context.getWorld().setBlockState(context.getBlockPos(),blockState);
            }
            context.getPlayer().getStackInHand(context.getHand()).decrement(1);

        }
        return super.useOnBlock(context);
    }
}