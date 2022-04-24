package dev.mrsterner.bewitchmentplus.common.block.blockentity;

import dev.mrsterner.bewitchmentplus.common.item.StatueBlockItem;
import dev.mrsterner.bewitchmentplus.common.registry.BWPBlockEntityTypes;
import dev.mrsterner.bewitchmentplus.common.registry.BWPObjects;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class StatueBlockEntity extends BlockEntity {
    private StatueBlockItem statue = (StatueBlockItem) BWPObjects.LILITH_STATUE_BLACKSTONE.asItem();
    public StatueBlockEntity(BlockPos pos, BlockState state) {
        super(BWPBlockEntityTypes.STATUE_BLOCK_ENTITY, pos, state);
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


    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.put("Type", statue.getDefaultStack().writeNbt(new NbtCompound()));
        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        NbtCompound nbtCompound = nbt.getCompound("Type");
        if (nbtCompound != null && !nbtCompound.isEmpty()) {
            ItemStack itemStack = ItemStack.fromNbt(nbtCompound);
            this.statue = (StatueBlockItem) itemStack.getItem();
        }
        super.readNbt(nbt);
    }

    public StatueBlockItem getStatue() {
        return statue;
    }

    public void setStatue(StatueBlockItem statue) {
        this.statue = statue;
    }
}
