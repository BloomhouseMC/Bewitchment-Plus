package dev.mrsterner.bewitchmentplus.common.block.blockentity;

import moriyashiine.bewitchment.common.block.entity.interfaces.TaglockHolder;
import moriyashiine.bewitchment.common.registry.BWBlockEntityTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class MimicChestBlockEntity extends ChestBlockEntity implements TaglockHolder {
    private final DefaultedList<ItemStack> taglockInventory = DefaultedList.ofSize(3, ItemStack.EMPTY);
    private UUID owner = null;

    public final BWPType type;
    public final boolean trapped;

    public MimicChestBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState, BWPType type, boolean trapped) {
        super(blockEntityType, blockPos, blockState);
        this.type = type;
        this.trapped = trapped;
    }

    public DefaultedList<ItemStack> getInventoryChest(){
        return this.getInvStackList();
    }

    public MimicChestBlockEntity(BlockPos blockPos, BlockState blockState) {
        this(BWBlockEntityTypes.BW_CHEST, blockPos, blockState, BWPType.LEECH, false);
    }

    protected void onInvOpenOrClose(World world, BlockPos pos, BlockState state, int oldViewerCount, int newViewerCount) {
        super.onInvOpenOrClose(world, pos, state, oldViewerCount, newViewerCount);
        if (this.trapped && world != null) {
            world.updateNeighborsAlways(pos.down(), this.getCachedState().getBlock());
        }

    }
    /*
    public LeechChestBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState, BWPType type, boolean trapped) {
        super(blockEntityType, blockPos, blockState, type, trapped);
    }

    public LeechChestBlockEntity(BlockPos blockPos, BlockState blockState) {
        this(BWPBlockEntityTypes.LEECH_CHEST_BLOCK_ENTITY, blockPos, blockState, BWPType.LEECH, false);
    }

     */

    @Override
    public DefaultedList<ItemStack> getTaglockInventory() {
        return taglockInventory;
    }

    @Override
    public UUID getOwner() {
        return owner;
    }

    @Override
    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        NbtCompound nbt = super.toInitialChunkDataNbt();
        writeNbt(nbt);
        return nbt;
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        fromNbtTaglock(nbt);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        toNbtTaglock(nbt);
    }

    public void sync() {
        if (world != null && !world.isClient) {
            world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_LISTENERS);
        }
    }
    public static enum BWPType {
        LEECH;

        private BWPType() {
        }
    }
}