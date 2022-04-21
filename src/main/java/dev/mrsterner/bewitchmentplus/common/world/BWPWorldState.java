package dev.mrsterner.bewitchmentplus.common.world;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.PersistentState;

import java.util.*;

public class BWPWorldState extends PersistentState {
    public final List<Pair<UUID, Long>> mimicChestsPair = new ArrayList<>();
    public static final Map<UUID, BlockPos> homeStead = new HashMap<>();

    public static BWPWorldState readNbt(NbtCompound nbt) {
        BWPWorldState worldState = new BWPWorldState();
        NbtList homeSteadList = nbt.getList("HomeStead", 10);
        for (NbtElement tag : homeSteadList) {
            NbtCompound homeSteadTag = (NbtCompound) tag;
            homeStead.put(homeSteadTag.getUuid("Player"), NbtHelper.toBlockPos(homeSteadTag.getCompound("Pos")));
        }
        NbtList mimicChestsList = nbt.getList("MimicChests", NbtType.COMPOUND);
        for (int i = 0; i < mimicChestsList.size(); i++) {
            NbtCompound mimicCompound = mimicChestsList.getCompound(i);
            worldState.mimicChestsPair.add(new Pair<>(mimicCompound.getUuid("Player"), mimicCompound.getLong("Pos")));
        }
        return worldState;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        NbtList homeSteadList = new NbtList();
        homeStead.forEach(((uuid, blockPos) -> {
            NbtCompound homeSteadTag = new NbtCompound();
            homeSteadTag.putUuid("Player", uuid);
            homeSteadTag.put("Pos", NbtHelper.fromBlockPos(blockPos));
            homeSteadList.add(homeSteadTag);
        }));
        nbt.put("HomeStead", homeSteadList);
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

    public void addHomeStead(UUID uuid, BlockPos pos){
        homeStead.put(uuid, pos);
        markDirty();
    }

    public void removeHomeStead(UUID uuid){
        homeStead.remove(uuid);
        markDirty();
    }

    public static BWPWorldState get(ServerWorld world) {
        return world.getPersistentStateManager().getOrCreate(BWPWorldState::readNbt, BWPWorldState::new, BewitchmentPlus.MODID + "_universal");
    }
}
