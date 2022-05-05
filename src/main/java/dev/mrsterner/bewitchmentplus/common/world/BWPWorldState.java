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
    public final List<Pair<UUID, BlockPos>> homeStead = new ArrayList<>();
    public final List<Pair<UUID, Boolean>> deathPlayer = new ArrayList<>();


    public static BWPWorldState readNbt(NbtCompound nbt) {
        BWPWorldState worldState = new BWPWorldState();
        NbtList homeSteadList = nbt.getList("HomeStead", 10);
        for (NbtElement tag : homeSteadList) {
            NbtCompound homeSteadTag = (NbtCompound) tag;
            worldState.homeStead.add(new Pair<>(homeSteadTag.getUuid("Player"), NbtHelper.toBlockPos(homeSteadTag.getCompound("Pos"))));
        }
        NbtList mimicChestsList = nbt.getList("MimicChests", NbtType.COMPOUND);
        for (int i = 0; i < mimicChestsList.size(); i++) {
            NbtCompound mimicCompound = mimicChestsList.getCompound(i);
            worldState.mimicChestsPair.add(new Pair<>(mimicCompound.getUuid("Player"), mimicCompound.getLong("Pos")));
        }
        NbtList deathPlayerList = nbt.getList("DeathPlayer", NbtType.COMPOUND);
        for (int i = 0; i < deathPlayerList.size(); i++) {
            NbtCompound deathCompound = deathPlayerList.getCompound(i);
            worldState.deathPlayer.add(new Pair<>(deathCompound.getUuid("Player"), deathCompound.getBoolean("Bool")));
        }
        return worldState;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        //DeathPlayer
        NbtList deathPlayerList = new NbtList();
        for (Pair<UUID, Boolean> pair : this.deathPlayer) {
            NbtCompound deathCompound = new NbtCompound();
            deathCompound.putUuid("Player", pair.getLeft());
            deathCompound.putBoolean("Bool", pair.getRight());
            deathPlayerList.add(deathCompound);
        }
        nbt.put("DeathPlayer", deathPlayerList);

        //Mimic
        NbtList mimicList = new NbtList();
        for (Pair<UUID, Long> pair : this.mimicChestsPair) {
            NbtCompound mimicCompound = new NbtCompound();
            mimicCompound.putUuid("Player", pair.getLeft());
            mimicCompound.putLong("Pos", pair.getRight());
            mimicList.add(mimicCompound);
        }
        nbt.put("MimicChests", mimicList);

        //HomeStead
        NbtList homeSteadList = new NbtList();
        for (Pair<UUID, BlockPos> pair : this.homeStead) {
            NbtCompound homeSteadCompound = new NbtCompound();
            homeSteadCompound.putUuid("Player", pair.getLeft());
            homeSteadCompound.put("Pos", NbtHelper.fromBlockPos(pair.getRight()));
            mimicList.add(homeSteadCompound);
        }
        nbt.put("HomeStead", homeSteadList);

        return nbt;
    }


    public static BWPWorldState get(ServerWorld world) {
        return world.getPersistentStateManager().getOrCreate(BWPWorldState::readNbt, BWPWorldState::new, BewitchmentPlus.MODID + "_universal");
    }
}
