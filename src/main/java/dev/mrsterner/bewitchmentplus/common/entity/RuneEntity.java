package dev.mrsterner.bewitchmentplus.common.entity;

import dev.mrsterner.besmirchment.common.Besmirchment;
import dev.mrsterner.besmirchment.common.registry.BSMTransformations;
import dev.mrsterner.besmirchment.common.transformation.LichLogic;
import dev.mrsterner.besmirchment.common.transformation.LichTransformation;
import dev.mrsterner.bewitchmentplus.common.registry.BWPTags;
import moriyashiine.bewitchment.api.BewitchmentAPI;
import moriyashiine.bewitchment.common.entity.living.VampireEntity;
import moriyashiine.bewitchment.common.registry.BWComponents;
import moriyashiine.bewitchment.common.registry.BWDamageSources;
import moriyashiine.bewitchment.common.registry.BWTags;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.Tag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.entity.EntityLookup;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RuneEntity extends Entity {
    //Animation
    private static final TrackedData<Boolean> EXPANDING = DataTracker.registerData(RuneEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Float> EXPANSION_TICKS = DataTracker.registerData(RuneEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Integer> PROGRESS = DataTracker.registerData(RuneEntity.class, TrackedDataHandlerRegistry.INTEGER);

    //RITUAL
    private static final TrackedData<Optional<UUID>> OWNER = DataTracker.registerData(RuneEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    private static final TrackedData<Optional<UUID>> ENTITY = DataTracker.registerData(RuneEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    private static final TrackedData<Integer> WORKING_TICKS = DataTracker.registerData(RuneEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> ACTIVE_RANGE = DataTracker.registerData(RuneEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public ItemStack stack;
    private int workingTicks = 0;

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
        this.dataTracker.startTracking(ENTITY, null);
        this.dataTracker.startTracking(WORKING_TICKS, 0);
        this.dataTracker.startTracking(ACTIVE_RANGE, 32);
    }

    private void runeTick(RuneEntity runeEntity, World world, BlockPos blockPos) {
        if(!world.isClient()){
            var entityList = world.getNonSpectatingEntities(LivingEntity.class, new Box(blockPos).expand(runeEntity.dataTracker.get(ACTIVE_RANGE), runeEntity.dataTracker.get(ACTIVE_RANGE), runeEntity.dataTracker.get(ACTIVE_RANGE)));
            if (!entityList.isEmpty()) {
                for (LivingEntity mob : entityList) {
                    boolean bl = FabricLoader.getInstance().isModLoaded("besmirchment");
                    boolean werewolf = mob instanceof PlayerEntity player && (BewitchmentAPI.isWerewolf(player, true) || BewitchmentAPI.isWerewolf(player, false));
                    boolean vampire = mob instanceof PlayerEntity player && (BewitchmentAPI.isVampire(player, true) || BewitchmentAPI.isVampire(player, false));
                    boolean isLich = bl && mob instanceof PlayerEntity player && (BSMTransformations.isLich(player, true) || BSMTransformations.isLich(player, false));//TODO test if this doesnt crash if besmirchment isnt loaded
                    boolean isWerepyre = bl && mob instanceof PlayerEntity player && (BSMTransformations.isWerepyre(player, true) || BSMTransformations.isWerepyre(player, false));
                    if ((BWPTags.UNHOLY.contains(mob.getType()) || werewolf || vampire || isLich || isWerepyre)) {
                        double distanceX = blockPos.getX() - mob.getX();
                        double distanceZ = blockPos.getZ() - mob.getZ();
                        double max = MathHelper.absMax(distanceX, distanceZ);
                        if (max >= 0) {
                            max = MathHelper.sqrt((float) max);
                            distanceX /= max;
                            distanceZ /= max;
                            distanceX *= Math.min(1, 1 / max);
                            distanceZ *= Math.min(1, 1 / max);
                            distanceX /= 2;
                            distanceZ /= 2;
                            mob.addVelocity(-distanceX, 0, -distanceZ);
                            mob.damage(BWDamageSources.MAGIC_COPY, 1);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void tick() {
        if(!world.isClient()) {
            if(this.workingTicks < 20){
                this.workingTicks++;
            }else{
                runeTick(this, this.world, this.getBlockPos());
                this.workingTicks = 0;
            }

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
