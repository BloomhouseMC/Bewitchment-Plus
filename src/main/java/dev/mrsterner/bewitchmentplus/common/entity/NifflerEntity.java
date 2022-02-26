package dev.mrsterner.bewitchmentplus.common.entity;

import dev.mrsterner.bewitchmentplus.common.registry.BWPEntityTypes;
import dev.mrsterner.bewitchmentplus.common.registry.BWPTags;
import dev.mrsterner.bewitchmentplus.mixin.MobEntityAccessor;
import moriyashiine.bewitchment.common.entity.living.util.BWTameableEntity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.InventoryOwner;
import net.minecraft.entity.SpawnReason;
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
import net.minecraft.util.collection.DefaultedList;
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

import java.util.UUID;

public class NifflerEntity extends BWTameableEntity implements IAnimatable, InventoryOwner {
    public SimpleInventory nifflerInventory = new SimpleInventory(6);
    private static final TrackedData<Boolean> NIFFLING = DataTracker.registerData(NifflerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    final AnimationFactory factory = new AnimationFactory(this);
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

    private <E extends IAnimatable> PlayState devMovement(AnimationEvent<E> animationEvent) {
        final AnimationController animationController = animationEvent.getController();
        AnimationBuilder builder = new AnimationBuilder();



        if(animationEvent.getController().getCurrentAnimation() == null || builder.getRawAnimationList().size() <= 0){
            builder.addAnimation( "animation.niffler.idle", true);
        }
        animationController.setAnimation(builder);
        return PlayState.CONTINUE;
    }
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
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
        NbtList listnbt = new NbtList();
        for (int i = 0; i < this.nifflerInventory.size(); ++i) {
            ItemStack itemstack = this.nifflerInventory.getStack(i);
            NbtCompound compoundnbt = new NbtCompound();
            compoundnbt.putByte("Slot", (byte) i);
            itemstack.writeNbt(compoundnbt);
            listnbt.add(compoundnbt);
        }
        nbt.put("Inventory", listnbt);
    }


    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new SitGoal(this));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.add(3, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.add(4, new AnimalMateGoal(this, 1.0D));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.targetSelector.add(0, new TrackOwnerAttackerGoal(this));
        this.targetSelector.add(1, new AttackWithOwnerGoal(this));
        this.targetSelector.add(2, new RevengeGoal(this, new Class[0]));
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
    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(NIFFLING, false);
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
        return BWPTags.NIFFLER.contains(itemStack.getItem());
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
        return null;
    }
}
