package dev.mrsterner.bewitchmentplus.common.entity.ai;

import dev.mrsterner.bewitchmentplus.common.entity.NifflerEntity;
import dev.mrsterner.bewitchmentplus.common.registry.BWPTags;
import dev.mrsterner.bewitchmentplus.common.utils.RandomPermuteIterator;
import net.minecraft.block.*;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class NifflerSeekGoal extends Goal {
    public final NifflerEntity niffler;
    List<BlockPos> blockList = new ArrayList<>();
    private int niffleCooldown = -200;
    private int chestOpenAnimationCooldown = -20;
    private boolean shouldCloseChest = false;
    private BlockPos blockPos = null;

    public NifflerSeekGoal(NifflerEntity niffler) {
        this.niffler = niffler;
    }

    public void lootChest(){
        try {
            RandomPermuteIterator randomPermuteIterator = new RandomPermuteIterator(blockList.size());
            while (randomPermuteIterator.hasMoreElements()){
                BlockPos chestPos = blockList.get(randomPermuteIterator.nextElement());
                Inventory inventory = getInventoryAt(niffler.world, chestPos.getX(), chestPos.getY(), chestPos.getZ());
                List<Pair<ItemStack, Integer>> itemStacks = new ArrayList<>();
                for(int i = 0; i < inventory.size(); i++){
                    if(BWPTags.NIFFLER.contains(inventory.getStack(i).getItem())){
                        itemStacks.add(new Pair<>(inventory.getStack(i), i));
                    }
                }
                if(itemStacks.size() > 0){
                    try{
                        RandomPermuteIterator pickItemAtRandom = new RandomPermuteIterator(itemStacks.size());
                        int k = pickItemAtRandom.nextElement();
                        ItemStack itemStack = itemStacks.get(k).getLeft();
                        if(niffler.world.getBlockEntity(chestPos) instanceof ChestBlockEntity){
                            niffler.world.emitGameEvent(null, GameEvent.CONTAINER_OPEN, chestPos);
                            this.blockPos = chestPos;
                            niffler.world.addSyncedBlockEvent(chestPos, niffler.world.getBlockState(chestPos).getBlock(), 1, 1);
                            niffler.world.playSound(null, chestPos, SoundEvents.BLOCK_CHEST_OPEN, SoundCategory.BLOCKS, 1,1);
                        }
                        if(niffler.nifflerInventory.canInsert(itemStack)){
                            for(int i = 0; i < niffler.nifflerInventory.size(); i++){
                                if(niffler.nifflerInventory.getStack(i).getItem().equals(itemStack.getItem()) || niffler.nifflerInventory.getStack(i).getItem().equals(Items.AIR)){
                                    niffler.nifflerInventory.setStack(i, itemStack.split(1));
                                    this.niffleCooldown = -200;
                                    this.chestOpenAnimationCooldown = -20;
                                    this.shouldCloseChest = true;
                                    break;
                                }
                            }
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void tick() {
        if(this.chestOpenAnimationCooldown < 0){
            this.chestOpenAnimationCooldown++;
        }
        if(this.niffleCooldown < 0){
            this.niffleCooldown++;
        }
        if(this.niffleCooldown == 0){
            lootChest();
        }
        if(chestOpenAnimationCooldown == 0 && blockPos != null && this.shouldCloseChest){
            niffler.world.addSyncedBlockEvent(blockPos, niffler.world.getBlockState(blockPos).getBlock(), 1, 0);
            niffler.world.emitGameEvent(null, GameEvent.CONTAINER_CLOSE, blockPos);
            niffler.world.playSound(null, blockPos, SoundEvents.BLOCK_CHEST_CLOSE, SoundCategory.BLOCKS, 1,1);
            this.shouldCloseChest = false;
        }
        super.tick();
    }


    @Override
    public boolean shouldContinue() {
        return true;
    }

    @Override
    public boolean canStart() {
        int rangeCheck = 8;
        int yRangeCheck = 4;
        BlockPos blockPos = niffler.getBlockPos();
        for(double x = -rangeCheck; x <= rangeCheck; ++x) {
            for (double y = -yRangeCheck; y <= yRangeCheck; ++y) {
                for (double z = -rangeCheck; z <= rangeCheck; ++z) {
                    BlockPos lootPos = new BlockPos(blockPos.getX() + x, blockPos.getY() + y, blockPos.getZ() + z);
                    if(niffler.world.getBlockEntity(lootPos) instanceof ChestBlockEntity chestBlockEntity){
                        blockList.add(chestBlockEntity.getPos());
                    }
                }
            }
        }
        return !blockList.isEmpty();
    }

    @Nullable
    private static Inventory getInventoryAt(World world, double x, double y, double z) {
        Object blockEntity;
        Inventory inventory = null;
        BlockPos blockPos = new BlockPos(x, y, z);
        BlockState blockState = world.getBlockState(blockPos);
        Block block = blockState.getBlock();
        if (block instanceof InventoryProvider) {
            inventory = ((InventoryProvider)(block)).getInventory(blockState, world, blockPos);
        } else if (blockState.hasBlockEntity() && (blockEntity = world.getBlockEntity(blockPos)) instanceof Inventory && (inventory = (Inventory)blockEntity) instanceof ChestBlockEntity && block instanceof ChestBlock) {
            inventory = ChestBlock.getInventory((ChestBlock)block, blockState, world, blockPos, true);
        }
        return inventory;
    }
}
