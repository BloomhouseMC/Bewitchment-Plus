package dev.mrsterner.bewitchmentplus.common.block;

import dev.mrsterner.bewitchmentplus.common.item.GobletBlockItem;
import dev.mrsterner.bewitchmentplus.common.registry.BWPObjects;
import dev.mrsterner.bewitchmentplus.common.utils.RenderHelper;
import moriyashiine.bewitchment.common.misc.BWUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.GlassBottleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class UnicornPuddleBlock extends PuddleBlock{
    public UnicornPuddleBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(player.getStackInHand(hand).getItem() instanceof GlassBottleItem || player.getStackInHand(hand).getItem() instanceof GobletBlockItem){
            if(player.getStackInHand(hand).getItem() instanceof GobletBlockItem){
                NbtCompound compound = new NbtCompound();
                DefaultedList<ItemStack> slots = DefaultedList.ofSize(1, BWPObjects.UNICORN_BLOOD.getDefaultStack());
                Inventories.writeNbt(compound, slots);
                compound.putInt("Color", RenderHelper.UNICORN_BLOOD_COLOR);
                compound.put("Goblet", player.getOffHandStack().getItem().getDefaultStack().writeNbt(new NbtCompound()));
                if(player.getMainHandStack().getItem() instanceof GobletBlockItem){
                    player.getMainHandStack().getOrCreateNbt().put("BlockEntityTag", compound);
                }else{
                    player.getOffHandStack().getOrCreateNbt().put("BlockEntityTag", compound);
                }
            }else{
                BWUtil.addItemToInventoryAndConsume(player, hand, new ItemStack(BWPObjects.UNICORN_BLOOD));
            }
            world.playSound(null, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1,1);
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }
}
