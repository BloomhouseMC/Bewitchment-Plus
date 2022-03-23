package dev.mrsterner.bewitchmentplus.common.item;

import dev.mrsterner.bewitchmentplus.common.registry.BWPTags;
import net.minecraft.block.Block;
import net.minecraft.block.Waterloggable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.registry.Registry;

import java.util.stream.Stream;

public class MutandisItem extends Item {
    public static final BooleanProperty WATERLOGGED = BooleanProperty.of("waterlogged");
    public MutandisItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if(context.getWorld().getBlockState(context.getBlockPos()).isIn(BWPTags.MUTANDIS)){
            Stream<Block> blockState = Registry.BLOCK.stream().filter(block -> block.getDefaultState().isIn(BWPTags.MUTANDIS));
            Block mutandisBlock = blockState.skip(context.getWorld().random.nextInt((int)blockState.count())).findFirst().get();
            if(mutandisBlock instanceof Waterloggable){
                context.getWorld().setBlockState(context.getBlockPos(),mutandisBlock.getDefaultState().with(WATERLOGGED, false));
            }else{
                context.getWorld().setBlockState(context.getBlockPos(),mutandisBlock.getDefaultState());
            }
            context.getPlayer().getStackInHand(context.getHand()).decrement(1);

        }
        return super.useOnBlock(context);
    }
}