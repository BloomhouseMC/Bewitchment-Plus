package dev.mrsterner.bewitchmentplus.common.world;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class BWPWorldState extends PersistentState {
    public final List<Long> mimicChests = new ArrayList<>();

    public static BWPWorldState readNbt(NbtCompound nbt) {
        BWPWorldState worldState = new BWPWorldState();
        NbtList mimicChestsList = nbt.getList("MimicChests", NbtType.COMPOUND);
        for (int i = 0; i < mimicChestsList.size(); i++) {
            NbtCompound posCompound = mimicChestsList.getCompound(i);
            worldState.mimicChests.add(posCompound.getLong("Pos"));
        }
        return worldState;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        NbtList potentialCandelabrasList = new NbtList();
        for (long pos : this.mimicChests) {
            NbtCompound posCompound = new NbtCompound();
            posCompound.putLong("Pos", pos);
            potentialCandelabrasList.add(posCompound);
        }
        nbt.put("PotentialCandelabras", potentialCandelabrasList);

        return nbt;
    }

    public static BWPWorldState get(World world) {
        return ((ServerWorld) world).getPersistentStateManager().getOrCreate(BWPWorldState::readNbt, BWPWorldState::new, BewitchmentPlus.MODID + "_universal");
    }
}
