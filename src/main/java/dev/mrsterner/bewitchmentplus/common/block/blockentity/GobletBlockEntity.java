package dev.mrsterner.bewitchmentplus.common.block.blockentity;

import dev.mrsterner.bewitchmentplus.common.item.GobletBlockItem;
import dev.mrsterner.bewitchmentplus.common.registry.BWPBlockEntityTypes;
import dev.mrsterner.bewitchmentplus.common.registry.BWPObjects;
import dev.mrsterner.bewitchmentplus.common.registry.BWPTags;
import dev.mrsterner.bewitchmentplus.common.utils.RenderHelper;
import moriyashiine.bewitchment.common.misc.BWUtil;
import moriyashiine.bewitchment.common.registry.BWObjects;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.potion.PotionUtil;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static dev.mrsterner.bewitchmentplus.common.block.GobletBlock.LIQUID_STATE;


public class GobletBlockEntity extends BlockEntity implements Inventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    private GobletBlockItem goblet = (GobletBlockItem) BWPObjects.SILVER_GOBLET.asItem();
    public int color = 0x000000;
    private boolean vampireBlood = false;

    public GobletBlockEntity(BlockPos pos, BlockState state) {
        super(BWPBlockEntityTypes.GOBLET, pos, state);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        if (nbt.contains("Color")) {
            color = nbt.getInt("Color");
        }
        if (nbt.contains("VampireBlood")) {
            vampireBlood = nbt.getBoolean("VampireBlood");
        }
        NbtCompound nbtCompound = nbt.getCompound("Goblet");
        if (nbtCompound != null && !nbtCompound.isEmpty()) {
            ItemStack itemStack = ItemStack.fromNbt(nbtCompound);
            this.goblet = (GobletBlockItem) itemStack.getItem();
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("Color", color);
        nbt.putBoolean("VampireBlood", vampireBlood);
        nbt.put("Goblet", goblet.getDefaultStack().writeNbt(new NbtCompound()));
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        NbtCompound nbt = super.toInitialChunkDataNbt();
        writeNbt(nbt);
        return nbt;
    }

    public void sync() {
        if (world != null && !world.isClient) {
            world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_LISTENERS);
        }
    }

    @Override
    public int size() {
        return inventory.size();
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < size(); i++) {
            if (getStack(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getStack(int slot) {
        return inventory.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return Inventories.splitStack(inventory, slot, amount);
    }

    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(inventory, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        inventory.set(slot, stack);
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    @Override
    public void clear() {
        inventory.clear();
    }

    public void setColor(int color) {
        if (world != null) {
            this.color = color;
        }
    }
    public int getColor(){
        return this.color;
    }

    public void onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.getBlockEntity(pos) instanceof GobletBlockEntity gobletBlockEntity) {
            ItemStack stack = player.getStackInHand(hand);
            if(player.isSneaking() && stack.isEmpty()){
                ItemStack pickup = new ItemStack(goblet);
                if(!gobletBlockEntity.getStack(0).isEmpty() && gobletBlockEntity.getColor() != 0){
                    gobletBlockEntity.setStackNbt(pickup);
                }
                player.setStackInHand(hand, pickup);
                world.breakBlock(pos, false, player);

            }else if(stack.isIn(BWPTags.GOBLET_LIQUIDS)) {
                if(!world.isClient() && this.getStack(0).isEmpty()){
                    if(stack.getItem().equals(Items.HONEY_BOTTLE)){
                        setColor(RenderHelper.HONEY_COLOR);
                        world.setBlockState(pos, state.with(LIQUID_STATE, 2));
                    }else if(stack.getItem().equals(BWObjects.BOTTLE_OF_BLOOD)){
                        setColor(RenderHelper.BLOOD_COLOR);
                        world.setBlockState(pos, state.with(LIQUID_STATE, 3));
                    }else if(stack.getItem().equals(BWPObjects.UNICORN_BLOOD)){
                        setColor(RenderHelper.UNICORN_BLOOD_COLOR);
                        world.setBlockState(pos, state.with(LIQUID_STATE, 4));

                    }else if(stack.getItem().equals(Items.POTION)){
                        setColor(PotionUtil.getColor(stack));
                        world.setBlockState(pos, state.with(LIQUID_STATE, 1));
                    }
                    this.setStack(0, stack.split(1));
                    this.sync();
                }
                world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1,1);
            }else if(stack.getItem() == Items.GLASS_BOTTLE && !getStack(0).isEmpty()){
                ItemStack itemsStack = gobletBlockEntity.getStack(0);
                BWUtil.addItemToInventoryAndConsume(player, hand, itemsStack);
                world.setBlockState(pos, state.with(LIQUID_STATE, 0));
                this.setColor(0);
                world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1,1);
            }
        }
    }
    public GobletBlockItem getGoblet() {
        return goblet;
    }

    public void setGoblet(GobletBlockItem goblet) {
        this.goblet = goblet;
    }
}
