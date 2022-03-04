package dev.mrsterner.bewitchmentplus.common.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RuneEntity extends Entity {
    public ItemStack stack;
    private int progress = 0;

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
            }

        }
        super.tick();
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
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public boolean collidesWith(Entity other) {
        return false;
    }

    public boolean collides() {
        return false;
    }

    public boolean isPushable() {
        return false;
    }

    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
    }


}
