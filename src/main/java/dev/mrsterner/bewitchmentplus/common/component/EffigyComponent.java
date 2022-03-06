package dev.mrsterner.bewitchmentplus.common.component;

import dev.mrsterner.bewitchmentplus.common.entity.EffigyEntity;
import dev.mrsterner.bewitchmentplus.common.registry.BWPComponents;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;

import java.util.Map;
import java.util.UUID;

public class EffigyComponent implements AutoSyncedComponent, ServerTickingComponent {

    private UUID effigyEntity = null;
    private final PlayerEntity player;
    private boolean hasEffigy = false;

    public EffigyComponent(PlayerEntity player) {
        this.player = player;
    }

    @Override
    public void serverTick() {
        if (effigyEntity != null) {
            Entity effigyEntity = ((ServerWorld) player.world).getEntity(getEffigy());
            if(effigyEntity instanceof EffigyEntity effigyEntity1){
                if(!effigyEntity1.getStatusEffects().isEmpty()){
                    Map<StatusEffect, StatusEffectInstance> statusEffectsEffigy = effigyEntity1.getActiveStatusEffects();
                    statusEffectsEffigy.forEach((statusEffect, statusEffectInstance) -> player.addStatusEffect(statusEffectInstance));
                }
                ServerWorld serverWorld = (ServerWorld) effigyEntity.world;
                serverWorld.setChunkForced(effigyEntity.getChunkPos().x,effigyEntity.getChunkPos().z,true);
            }
        }
    }

    public void setHasEffigy(boolean hasEffigy){
        this.hasEffigy = hasEffigy;
    }

    public boolean getHasEffigy(){
        return hasEffigy;
    }

    public UUID getEffigy() {
        return effigyEntity;
    }

    public void setEffigy(UUID effigyEntity) {
        this.effigyEntity = effigyEntity;
        BWPComponents.EFFIGY_COMPONENT.sync(player);
    }


    @Override
    public void readFromNbt(NbtCompound tag) {
        setEffigy(tag.getString("effigyUUID").isEmpty() ? null : UUID.fromString(tag.getString("effigyUUID")));
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putString("effigyUUID", getEffigy() == null ? "" : getEffigy().toString());
    }
}
