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
    private static int progress = 0;
    private static boolean startExpanding = false;
    private static double expansionTicks = 0;

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
                setExpansion(true);
            }
            if(getExpansion()){
                setExpansionTicks(getExpansionTick() + 1);
                if(getExpansionTick() > 20 * 10){
                    setExpansionTicks(0);
                }
            }
            //System.out.println(startExpanding + ", ExpansionTicks: "+expansionTicks);

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
        /*
        if(nbt.contains("Progress")){
            setProgress(nbt.getInt("Progress"));
        }
        if(nbt.contains("Expand")){
            setExpansion(nbt.getBoolean("Expand"));
        }
        if(nbt.contains("ExpansionTicks")){
            setExpansionTicks(nbt.getDouble("ExpansionTicks"));
        }

         */
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putInt("Progress", getProgress());
        nbt.putBoolean("Expand", getExpansion());
        nbt.putDouble("ExpansionTicks", getExpansionTick());
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public double getExpansionTick() {
        return expansionTicks;
    }

    public void setExpansionTicks(double expansionTicks) {
        this.expansionTicks = expansionTicks;
    }

    public boolean getExpansion() {
        return startExpanding;
    }

    public void setExpansion(boolean startExpanding) {
        this.startExpanding = startExpanding;
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
