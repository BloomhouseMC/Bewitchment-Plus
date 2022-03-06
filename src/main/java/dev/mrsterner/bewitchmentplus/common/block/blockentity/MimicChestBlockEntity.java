package dev.mrsterner.bewitchmentplus.common.block.blockentity;

import dev.mrsterner.bewitchmentplus.common.registry.BWPBlockEntityTypes;
import moriyashiine.bewitchment.common.block.entity.interfaces.TaglockHolder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class MimicChestBlockEntity extends ChestBlockEntity implements TaglockHolder {
    private final DefaultedList<ItemStack> taglockInventory = DefaultedList.ofSize(3, ItemStack.EMPTY);
    public float partial;
    public boolean hasPlayer;
    public float eyeRotation;
    public float flippity;
    public float floppity;
    private UUID owner = null;

    public final BWPType type;

    public MimicChestBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState, BWPType type) {
        super(blockEntityType, blockPos, blockState);
        this.type = type;
    }

    private final ChestLidAnimator lidAnimator = new ChestLidAnimator();

    public static void clientTick(World world, BlockPos pos, BlockState state, MimicChestBlockEntity blockEntity) {
        blockEntity.lidAnimator.step();
        tick(world, pos, state, blockEntity);
    }

    @Override
    public float getAnimationProgress(float tickDelta) {
        return this.lidAnimator.getProgress(tickDelta);
    }

    @Override
    public boolean onSyncedBlockEvent(int type, int data) {
        if (type == 1) {
            this.lidAnimator.setOpen(data > 0);
            return true;
        }
        return super.onSyncedBlockEvent(type, data);
    }

    public static void tick(World world, BlockPos pos, BlockState state, MimicChestBlockEntity blockEntity) {
        PlayerEntity playerEntity = world.getClosestPlayer((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, 3.0D, false);
        blockEntity.eyeRotation = blockEntity.floppity;
        if (playerEntity != null) {
            double d = playerEntity.getX() - ((double)pos.getX() + 0.5D);
            double e = playerEntity.getZ() - ((double)pos.getZ() + 0.5D);
            blockEntity.flippity = (float) MathHelper.atan2(e, d);
            blockEntity.hasPlayer = true;
        }else{
            blockEntity.hasPlayer = false;
        }
        while(blockEntity.floppity >= 3.1415927F) {
            blockEntity.floppity -= 6.2831855F;
        }

        while(blockEntity.floppity < -3.1415927F) {
            blockEntity.floppity += 6.2831855F;
        }

        while(blockEntity.flippity >= 3.1415927F) {
            blockEntity.flippity -= 6.2831855F;
        }

        while(blockEntity.flippity < -3.1415927F) {
            blockEntity.flippity += 6.2831855F;
        }

        float d;
        for(d = blockEntity.flippity - blockEntity.floppity; d >= 3.1415927F; d -= 6.2831855F) {
        }

        while(d < -3.1415927F) {
            d += 6.2831855F;
        }
        blockEntity.floppity += d * 0.4F;
    }

    public DefaultedList<ItemStack> getInventoryChest(){
        return this.getInvStackList();
    }

    public MimicChestBlockEntity(BlockPos blockPos, BlockState blockState) {
        this(BWPBlockEntityTypes.MIMIC_CHEST_BLOCK_ENTITY, blockPos, blockState, BWPType.LEECH);
    }

    protected void onInvOpenOrClose(World world, BlockPos pos, BlockState state, int oldViewerCount, int newViewerCount) {
        super.onInvOpenOrClose(world, pos, state, oldViewerCount, newViewerCount);


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