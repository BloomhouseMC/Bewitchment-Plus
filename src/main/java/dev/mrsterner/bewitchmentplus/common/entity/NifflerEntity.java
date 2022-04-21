package dev.mrsterner.bewitchmentplus.common.entity;

import dev.mrsterner.bewitchmentplus.common.entity.ai.*;
import dev.mrsterner.bewitchmentplus.common.registry.BWPEntityTypes;
import dev.mrsterner.bewitchmentplus.common.registry.BWPObjects;
import dev.mrsterner.bewitchmentplus.common.registry.BWPTags;
import dev.mrsterner.bewitchmentplus.mixin.common.MobEntityAccessor;
import moriyashiine.bewitchment.common.entity.living.util.BWTameableEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NifflerEntity extends BWTameableEntity implements IAnimatable, InventoryOwner {
    public SimpleInventory nifflerInventory = new SimpleInventory(6);
    public List<Long> blocksChecked = new ArrayList<>();
    private static final TrackedData<Boolean> NIFFLING = DataTracker.registerData(NifflerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> SITTING = DataTracker.registerData(NifflerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Boolean> SLEEPING = DataTracker.registerData(NifflerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private final AnimationFactory factory = new AnimationFactory(this);
    public NifflerEntity(EntityType<? extends TameableEntity> type, World world) {
        super(type, world);
        this.getNavigation().setCanSwim(true);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
        .add(EntityAttributes.GENERIC_MAX_HEALTH, 8.0D)
        .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1.0D)
        .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D)
        .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.6D);
    }


    public ItemStack getPickedResult(HitResult target) {
        return new ItemStack(BWPObjects.NIFFLER_SPAWN_EGG.asItem());
    }

    private <E extends IAnimatable> PlayState devMovement(AnimationEvent<E> animationEvent) {
        final AnimationController animationController = animationEvent.getController();
        AnimationBuilder builder = new AnimationBuilder();
        Vec3d motionCalc = new Vec3d(this.getX()-this.prevX, this.getY()-this.prevY,this.getZ()-this.prevZ);
        boolean isMovingHorizontal = Math.sqrt(Math.pow(motionCalc.x, 2) + Math.pow(motionCalc.z, 2)) > 0.005;
        if(this.dataTracker.get(SLEEPING)){
            builder.addAnimation("animation.niffler.sleep", true);
        }else if (!this.isOnGround() && motionCalc.getY() < 0){
            //TODO create falling animation
        }else if(isMovingHorizontal || animationEvent.isMoving()){
            animationController.setAnimationSpeed(2);
            if(this.dataTracker.get(NIFFLING)){
                builder.addAnimation("animation.niffler.walk_sniff", true);
            }else{
                builder.addAnimation("animation.niffler.walk", true);
            }
        }else if(this.dataTracker.get(SITTING)){
            animationController.setAnimationSpeed(1);
            builder.addAnimation("animation.niffler.niffle", true);
        }

        if(animationEvent.getController().getCurrentAnimation() == null || builder.getRawAnimationList().size() <= 0){
            animationController.setAnimationSpeed(1);
            builder.addAnimation( "animation.niffler.idle", true);
        }
        animationController.setAnimation(builder);
        return PlayState.CONTINUE;
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        NbtList blockList = nbt.getList("BlockPositions", 10);
        for(int k = 0; k < blockList.size(); ++k){
            NbtCompound compoundnbtBlock = blockList.getCompound(k);
            this.blocksChecked.add(compoundnbtBlock.getLong("Block"));
        }

        NbtList listnbt = nbt.getList("Inventory", 10);
        for (int i = 0; i < listnbt.size(); ++i) {
            NbtCompound compoundnbt = listnbt.getCompound(i);
            int j = compoundnbt.getByte("Slot") & 255;
            this.nifflerInventory.setStack(j, ItemStack.fromNbt(compoundnbt));
        }
        if (nbt.contains("HandItems", 9)) {
            NbtList handItems = nbt.getList("HandItems", 10);
            for (int i = 0; i < ((MobEntityAccessor)this).handItems().size(); ++i) {
                int handSlot = i == 0 ? 5 : 4;
                this.nifflerInventory.setStack(handSlot, ItemStack.fromNbt(handItems.getCompound(i)));
            }
        }
    }
    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        NbtList listnbtBlockPos = new NbtList();
        for(long pos : this.blocksChecked){
            NbtCompound compound = new NbtCompound();
            compound.putLong("Block", pos);
        }
        nbt.put("BlockPositions", listnbtBlockPos);

        NbtList listnbtInventory = new NbtList();
        for (int i = 0; i < this.nifflerInventory.size(); ++i) {
            ItemStack itemstack = this.nifflerInventory.getStack(i);
            NbtCompound compoundnbt = new NbtCompound();
            compoundnbt.putByte("Slot", (byte) i);
            itemstack.writeNbt(compoundnbt);
            listnbtInventory.add(compoundnbt);
        }
        nbt.put("Inventory", listnbtInventory);
    }

    @Override
    public boolean canMoveVoluntarily() {
        return super.canMoveVoluntarily() && !this.dataTracker.get(SLEEPING);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new SitGoal(this));
        this.goalSelector.add(1, new NifflerSleepGoal(this));
        this.goalSelector.add(1, new NifflerWakeGoal(this));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.add(2, new NifflerSeekGoal(this));
        this.goalSelector.add(3, new NifflerTemptGoal(this, 1.25, false));
        this.goalSelector.add(3, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.add(4, new AnimalMateGoal(this, 1.0D));
        this.goalSelector.add(4, new NifflerForgetContainerGoal(this));
        this.goalSelector.add(4, new FleeEntityGoal<>(this, WolfEntity.class, 8.0f, 1.6, 1.4));
        this.goalSelector.add(4, new FleeEntityGoal<>(this, PolarBearEntity.class, 8.0f, 1.6, 1.4));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.goalSelector.add(7, new NifflerWalkToInventoryGoal(this));
        this.goalSelector.add(9, new GoToVillageGoal(this, 200));

        this.targetSelector.add(0, new TrackOwnerAttackerGoal(this));
        this.targetSelector.add(1, new AttackWithOwnerGoal(this));
        this.targetSelector.add(2, new RevengeGoal(this));
    }

    public void setTamed(boolean tamed) {
        super.setTamed(tamed);
        EntityAttributeInstance maxHealth = this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        EntityAttributeInstance attackDamage = this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        if (tamed) {
            maxHealth.setBaseValue(20.0D);
            attackDamage.setBaseValue(3.0D);
            this.setHealth(this.getMaxHealth());
        } else {
            maxHealth.setBaseValue(8.0D);
            attackDamage.setBaseValue(1.0D);
        }

    }

    @Override
    protected void loot(ItemEntity item) {
        ItemStack itemStack = item.getStack();
        if (this.canPickupItem(itemStack)) {
            this.triggerItemPickedUpByEntityCriteria(item);
            this.equipStack(EquipmentSlot.MAINHAND, itemStack.split(1));
            this.handDropChances[EquipmentSlot.MAINHAND.getEntitySlotId()] = 2.0f;
            this.sendPickup(item, itemStack.getCount());
            item.discard();
        }
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (!this.world.isClient && this.canPickUpLoot() && this.isAlive() && !this.dead && this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
            List<ItemEntity> list = this.world.getNonSpectatingEntities(ItemEntity.class, this.getBoundingBox().expand(1.0, 0.0, 1.0));
            for (ItemEntity itemEntity : list) {
                if (itemEntity.isRemoved() || itemEntity.getStack().isEmpty() || itemEntity.cannotPickup() || !this.canGather(itemEntity.getStack())) continue;
                this.loot(itemEntity);
            }
        }
    }

    @Override
    public boolean canPickUpLoot() {
        ItemStack itemStack = this.getEquippedStack(EquipmentSlot.MAINHAND);
        return itemStack.isEmpty();
    }

    @Override
    public boolean canPickupItem(ItemStack stack) {
        ItemStack itemStack = this.getEquippedStack(EquipmentSlot.MAINHAND);
        return itemStack.isEmpty();
    }

    private void dropItem(ItemStack stack) {
        ItemEntity itemEntity = new ItemEntity(this.world, this.getX(), this.getY(), this.getZ(), stack);
        this.world.spawnEntity(itemEntity);
    }




    @Override
    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(NIFFLING, false);
        this.dataTracker.startTracking(SITTING, false);
        this.dataTracker.startTracking(SLEEPING, false);
        super.initDataTracker();
    }

    @Override
    protected boolean hasShiny() {
        return true;
    }

    @Override
    public int getVariants() {
        return 1;
    }

    @Override
    protected boolean isTamingItem(ItemStack itemStack) {
        return itemStack.isIn(BWPTags.NIFFLER);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        NifflerEntity child = BWPEntityTypes.NIFFLER.create(world);
        if (child != null) {
            child.initialize(world, world.getLocalDifficulty(this.getBlockPos()), SpawnReason.BREEDING, null, null);
            UUID owner = this.getOwnerUuid();
            if (owner != null) {
                child.setOwnerUuid(owner);
                child.setTamed(true);
            }

            if (entity instanceof NifflerEntity && this.random.nextFloat() < 0.95F) {
                child.dataTracker.set(VARIANT, this.random.nextBoolean() ? this.dataTracker.get(VARIANT) : entity.getDataTracker().get(VARIANT));
            }
        }

        return child;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "DevMovement", 2, this::devMovement));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public Inventory getInventory() {
        return this.nifflerInventory;
    }
}
