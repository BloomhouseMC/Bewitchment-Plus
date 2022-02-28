package dev.mrsterner.bewitchmentplus.common.item;

import dev.mrsterner.bewitchmentplus.common.block.blockentity.GobletBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static dev.mrsterner.bewitchmentplus.common.block.GobletBlock.LIQUID_STATE;

public class GobletItem extends BlockItem {
    public GobletItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.isSneaking()) {
            return TypedActionResult.success(user.getStackInHand(hand));
        }
        return TypedActionResult.pass(user.getStackInHand(hand));
    }

    @Override
    public ActionResult place(ItemPlacementContext context) {
        if (context.getStack().getNbt() != null) {
            NbtCompound nbtCompound = context.getStack().getNbt().getCompound("Liquid");
            System.out.println(nbtCompound);
            if (nbtCompound != null && !nbtCompound.isEmpty()) {
                ItemStack itemStack = ItemStack.fromNbt(nbtCompound);
                System.out.println(itemStack);
                if(context.getWorld().getBlockEntity(context.getBlockPos()) instanceof GobletBlockEntity gobletBlock){
                    gobletBlock.setStack(0, itemStack);
                }
            }
        }


        return super.place(context);
    }

    @Override
    protected boolean postPlacement(BlockPos pos, World world, @Nullable PlayerEntity player, ItemStack stack, BlockState state) {
        return super.postPlacement(pos, world, player, stack, state);
    }

    @Nullable
    @Override
    protected BlockState getPlacementState(ItemPlacementContext context) {


        return super.getPlacementState(context);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (stack.hasNbt() && stack.getOrCreateNbt().contains("Liquid")) {
            tooltip.add(new TranslatableText("liquid." + stack.getOrCreateNbt().getString("Liquid").replace(":", ".")).formatted(Formatting.DARK_RED));
        }
    }


    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (context.getPlayer().isSneaking()) {
            return super.useOnBlock(context);
        }
        return ActionResult.PASS;
    }
}
