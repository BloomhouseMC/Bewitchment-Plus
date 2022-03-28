package dev.mrsterner.bewitchmentplus.common.entity;

import dev.mrsterner.bewitchmentplus.common.registry.BWPEntityTypes;
import dev.mrsterner.bewitchmentplus.common.registry.BWPObjects;
import moriyashiine.bewitchment.common.entity.living.OwlEntity;
import moriyashiine.bewitchment.common.entity.living.util.BWTameableEntity;
import moriyashiine.bewitchment.common.registry.BWEntityTypes;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
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

public class DragonEntity extends BWTameableEntity implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);
    public DragonEntity(EntityType<? extends TameableEntity> type, World world) {
        super(type, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
        .add(EntityAttributes.GENERIC_MAX_HEALTH, 8.0D)
        .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1.0D)
        .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D)
        .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.6D);
    }

    @Override
    protected boolean hasShiny() {
        return false;
    }

    @Override
    public int getVariants() {
        return 0;
    }

    @Override
    protected boolean isTamingItem(ItemStack itemStack) {
        return itemStack.getItem() == BWPObjects.DRAGONFRUIT;
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        DragonEntity child = BWPEntityTypes.DRAGON.create(world);
        if (child != null) {
            child.initialize(world, world.getLocalDifficulty(this.getBlockPos()), SpawnReason.BREEDING, null, null);
            UUID owner = this.getOwnerUuid();
            if (owner != null) {
                child.setOwnerUuid(owner);
                child.setTamed(true);
            }

            if (entity instanceof DragonEntity && this.random.nextFloat() < 0.95F) {
                child.dataTracker.set(VARIANT, this.random.nextBoolean() ? this.dataTracker.get(VARIANT) : entity.getDataTracker().get(VARIANT));
            }
        }

        return child;
    }

    private <E extends IAnimatable> PlayState basicMovement(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.dragon.idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "BasicMovement", 0, this::basicMovement));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
