package dev.mrsterner.bewitchmentplus.common.item;

import dev.mrsterner.bewitchmentplus.common.registry.BWPTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Waterloggable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntryList;

import java.util.stream.Stream;

public class MutandisItem extends Item {
    public static final BooleanProperty WATERLOGGED = BooleanProperty.of("waterlogged");
    public MutandisItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if(context.getWorld().getBlockState(context.getBlockPos()).isIn(BWPTags.MUTANDIS)){
            BlockState blockState = context.getWorld().getBlockState(context.getBlockPos());
            blockState = Registry.BLOCK.getEntryList(BWPTags.MUTANDIS).flatMap((blocks) -> blocks.getRandom(context.getWorld().random)).map((blockEntry) -> (blockEntry.value()).getDefaultState()).orElse(blockState);
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