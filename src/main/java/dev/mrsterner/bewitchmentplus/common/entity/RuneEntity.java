package dev.mrsterner.bewitchmentplus.common.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.UUID;

public class RuneEntity extends Entity {
    private static final TrackedData<Boolean> EXPANDING = DataTracker.registerData(RuneEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Float> EXPANSION_TICKS = DataTracker.registerData(RuneEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Integer> PROGRESS = DataTracker.registerData(RuneEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Optional<UUID>> OWNER = DataTracker.registerData(RuneEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    public ItemStack stack;


    public RuneEntity(EntityType<?> type, World world) {
        super(type, world);
        this.setNoGravity(true);
        this.stack = ItemStack.EMPTY;
    }


    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this, 0);
    }

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(EXPANDING, false);
        this.dataTracker.startTracking(EXPANSION_TICKS, 0.0F);
        this.dataTracker.startTracking(PROGRESS, 0);
        this.dataTracker.startTracking(OWNER, null);
    }

    @Override
    public void tick() {
        if(!world.isClient()) {
            if(getProgress()>= 0){
                setProgress(getProgress() + 1);
            }
            if(getProgress() >= 20 * 10){
                world.playSound(null, this.getBlockPos(), SoundEvents.ENTITY_WITHER_SPAWN, SoundCategory.BLOCKS, 0.5f, 0.5f);
                setProgress(-1);
                setExpansion(true);
            }
            if(getExpansion()){
                setExpansionTicks(getExpansionTick() + 1);
                if(getExpansionTick() > 20 * 10){
                    setExpansionTicks(0);
                }
            }
        }
        super.tick();
    }

    public void killRune(){
        this.kill();
    }

    @Override
    public boolean shouldRender(double cameraX, double cameraY, double cameraZ) {
        return true;
    }

    @Override
    public boolean shouldRender(double distance) {
        return true;
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
    }

    public int getProgress() {
        return this.dataTracker.get(PROGRESS);
    }

    public void setProgress(int progress) {
        this.dataTracker.set(PROGRESS, progress);
    }

    public float getExpansionTick() {
        return this.dataTracker.get(EXPANSION_TICKS);
    }

    public void setExpansionTicks(float expansionTicks) {
        this.dataTracker.set(EXPANSION_TICKS, expansionTicks);
    }

    public boolean getExpansion() {
        return this.dataTracker.get(EXPANDING);
    }

    public void setExpansion(boolean startExpanding) {
        this.dataTracker.set(EXPANDING, startExpanding);
    }

    @Override
    public boolean collidesWith(Entity other) {
        return false;
    }

    @Override
    public boolean collides() {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
    }


}
