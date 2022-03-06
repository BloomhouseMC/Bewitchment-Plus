package dev.mrsterner.bewitchmentplus.common.world;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Pair;
import net.minecraft.world.PersistentState;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BWPWorldState extends PersistentState {
    public final List<Pair<UUID, Long>> mimicChestsPair = new ArrayList<>();

    public static BWPWorldState readNbt(NbtCompound nbt) {
        BWPWorldState worldState = new BWPWorldState();
        NbtList mimicChestsList = nbt.getList("MimicChests", NbtType.COMPOUND);
        for (int i = 0; i < mimicChestsList.size(); i++) {
            NbtCompound mimicCompound = mimicChestsList.getCompound(i);
            worldState.mimicChestsPair.add(new Pair<>(mimicCompound.getUuid("Player"), mimicCompound.getLong("Pos")));
        }
        return worldState;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        NbtList mimicList = new NbtList();
        for (Pair<UUID, Long> pair : this.mimicChestsPair) {
            NbtCompound mimicCompound = new NbtCompound();
            mimicCompound.putUuid("Player", pair.getLeft());
            mimicCompound.putLong("Pos", pair.getRight());
            mimicList.add(mimicCompound);
        }
        nbt.put("MimicChests", mimicList);
        return nbt;
    }

    public static BWPWorldState get(World world) {
        return world.getServer().getOverworld().getPersistentStateManager().getOrCreate(BWPWorldState::readNbt, BWPWorldState::new, BewitchmentPlus.MODID);
    }
}
