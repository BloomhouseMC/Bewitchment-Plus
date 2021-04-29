package net.bewitchmentplus.common.entity.living;

import moriyashiine.bewitchment.api.BewitchmentAPI;
import moriyashiine.bewitchment.common.entity.living.util.BWHostileEntity;
import net.bewitchmentplus.BewitchmentPlus;
import net.bewitchmentplus.common.registry.BWPTags;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.StructureFeature;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

//Todo: Make this not targeted by golems
public class CambionEntity extends BWHostileEntity {
	public static final TrackedData<Boolean> MALE = DataTracker.registerData(CambionEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	public int attackTick = 0;
	int barterTimer = 0;

	public CambionEntity(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 20.00D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D).add(EntityAttributes.GENERIC_ARMOR, 2.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.35D).add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.35D).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 24.0D);
	}

	public EntityGroup getGroup() {
		return BewitchmentAPI.DEMON;
	}

	protected boolean hasShiny() {
		return true;
	}

	@Override
	public boolean canUsePortals() {
		return true;
	}

	@Override
	public boolean canSpawn(WorldAccess world, SpawnReason spawnReason) {
		boolean flag = super.canSpawn(world, spawnReason);
		if (flag && (spawnReason == SpawnReason.SPAWNER || spawnReason == SpawnReason.STRUCTURE || spawnReason == SpawnReason.MOB_SUMMONED || spawnReason == SpawnReason.SPAWN_EGG || spawnReason == SpawnReason.COMMAND || spawnReason == SpawnReason.DISPENSER || spawnReason == SpawnReason.NATURAL)) {
			return true;
		}
		if (world instanceof ServerWorld && BewitchmentPlus.config.cambionStructureSpawn) {
			BlockPos nearestVillage = ((ServerWorld) world).locateStructure(StructureFeature.VILLAGE, getBlockPos(), 3, false);
			BlockPos nearestBastion = ((ServerWorld) world).locateStructure(StructureFeature.BASTION_REMNANT, getBlockPos(), 3, false);
			BlockPos nearestFortress = ((ServerWorld) world).locateStructure(StructureFeature.FORTRESS, getBlockPos(), 3, false);
			return (nearestVillage != null && Math.sqrt(nearestVillage.getSquaredDistance(getBlockPos())) < 128);
		}
		return false;
	}

	@Override
	public void tick() {
		super.tick();
		if (barterTimer > 0) barterTimer--;
	}

	@Override
	public boolean tryAttack(Entity target) {
		boolean flag = super.tryAttack(target);
		Random rand = new Random();
		int i = rand.nextInt(100);
		if (i <= 5) {
			toggleAttack(false);
			if (target instanceof LivingEntity) {
				target.setOnFireFor(40);
			}
		} else if (i <= 7) {
			toggleAttack(false);
			if (target instanceof LivingEntity) {
				((LivingEntity) target).addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 100));
			}
		}
		return flag;
	}

	public void toggleAttack(boolean attacking) {
		if (attacking) {
			attackTick = 11;
			world.sendEntityStatus(this, (byte) 4);
		} else {
			attackTick = 2;
			world.sendEntityStatus(this, (byte) 5);
		}
	}

	@Override
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable CompoundTag entityTag) {
		dataTracker.set(MALE, random.nextBoolean());
		Random rand = new Random();
		int i = rand.nextInt(100);
		int j = rand.nextInt(100);
		int k = rand.nextInt(100);
		if (i <= 45) {
			this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
		}
		if (i <= 25) {
			this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));
		}
		if (j <= 25) {
			this.equipStack(EquipmentSlot.CHEST, new ItemStack(Items.CHAINMAIL_CHESTPLATE));
		}
		if (j <= 15) {
			this.equipStack(EquipmentSlot.CHEST, new ItemStack(Items.GOLDEN_CHESTPLATE));
		}
		if (j <= 45) {
			this.equipStack(EquipmentSlot.CHEST, new ItemStack(Items.LEATHER_CHESTPLATE));
		}
		if (k <= 15) {
			this.equipStack(EquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
		}
		return super.initialize(world, difficulty, spawnReason, entityData, entityTag);
	}

	@Override
	public void readCustomDataFromTag(CompoundTag tag) {
		super.readCustomDataFromTag(tag);
		dataTracker.set(MALE, tag.getBoolean("Male"));
	}

	@Override
	public void writeCustomDataToTag(CompoundTag tag) {
		super.writeCustomDataToTag(tag);
		tag.putBoolean("Male", dataTracker.get(MALE));
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		dataTracker.startTracking(MALE, true);
	}

	@Override
	protected void initGoals() {
		goalSelector.add(0, new SwimGoal(this));
		goalSelector.add(1, new MeleeAttackGoal(this, 1, true));
		goalSelector.add(2, new WanderAroundFarGoal(this, 1));
		goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8));
		goalSelector.add(3, new LookAroundGoal(this));
		targetSelector.add(0, new RevengeGoal(this));
	}

	//Todo: Grab from a loot table. Also set up the timer fully.
	//Todo: Add items to the cambion trade tag
	@Override
	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (barterTimer == 0) {
			if (itemStack.getItem() == BWPTags.CAMBION_TRADE) {
				player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_GOLD, SoundCategory.NEUTRAL, 1, 1);
				//ItemStack itemStack2 = ItemUsage.method_30012(itemStack, player, getBarteredItem()); //Fixme: Pick up on a resource table, and give a player a random item, or a random amount of one specific item at a time.
				//player.setStackInHand(hand, itemStack2);
				barterTimer = 1200; //Timer exists to avoid cheese
				return ActionResult.success(this.world.isClient);
			} else {
				return super.interactMob(player, hand);
			}
		}
		return null;
	}

	@Override
	public int getVariants() {
		return 6;
	}
}
