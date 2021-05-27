package net.bewitchmentplus.common.entity.living;

import moriyashiine.bewitchment.api.BewitchmentAPI;
import moriyashiine.bewitchment.common.entity.living.util.BWHostileEntity;
import moriyashiine.bewitchment.common.registry.BWObjects;
import net.bewitchmentplus.BewitchmentPlus;
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

	//Todo: Redo this to mirror logic used on 1.12.2. It was handled better there.
	@Override
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable CompoundTag entityTag) {
		dataTracker.set(MALE, random.nextBoolean());
		Random rand = new Random();
		int a = rand.nextInt(4);
		int c = rand.nextInt(4);
		if (a == 3) {
			this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
		}
		if (a == 2) {
			this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));
		}
		if (a == 1) {
			this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(BWObjects.ATHAME));
		}
		if (c == 1) {
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

	@Override
	public boolean canPickupItem(ItemStack stack) {
		return super.canPickupItem(stack);
	}

	//Todo: Add items to the cambion trade tag
	@Override
	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		Random rand = new Random();
		int i = rand.nextInt(64);
		int j = rand.nextInt(16);
		int k = rand.nextInt(32);
		int l = rand.nextInt(8);
		if (barterTimer == 0) {
			if (itemStack.getItem() == Items.GOLD_INGOT) {
				player.inventory.removeOne(itemStack);
				switch (rand.nextInt(22)) {
					default:
						ItemStack itemStack1 = new ItemStack(Items.GOLD_INGOT, j);
						player.inventory.insertStack(itemStack1);
						barterTimer = 1200; //Timer exists to avoid cheese
						player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_GOLD, SoundCategory.NEUTRAL, 1, 1);
						ActionResult.success(this.world.isClient);
						break;
					case 0:
						ItemStack itemStack2 = new ItemStack(Items.DIAMOND, j);
						player.inventory.insertStack(itemStack2);
						barterTimer = 1200; //Timer exists to avoid cheese
						player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_GOLD, SoundCategory.NEUTRAL, 1, 1);
						ActionResult.success(this.world.isClient);
						break;
					case 1:
						ItemStack itemStack3 = new ItemStack(Items.GOLDEN_HORSE_ARMOR);
						player.inventory.insertStack(itemStack3);
						barterTimer = 1200; //Timer exists to avoid cheese
						player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_GOLD, SoundCategory.NEUTRAL, 1, 1);
						ActionResult.success(this.world.isClient);
						break;
					case 2:
						ItemStack itemStack4 = new ItemStack(Items.GOLDEN_APPLE);
						player.inventory.insertStack(itemStack4);
						barterTimer = 4800; //Timer exists to avoid cheese. Golden apples are powerful, give them a longer cooldown.
						player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_GOLD, SoundCategory.NEUTRAL, 1, 1);
						ActionResult.success(this.world.isClient);
						break;
					case 3:
						ItemStack itemStack5 = new ItemStack(Items.SADDLE);
						player.inventory.insertStack(itemStack5);
						barterTimer = 1200; //Timer exists to avoid cheese
						player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_GOLD, SoundCategory.NEUTRAL, 1, 1);
						ActionResult.success(this.world.isClient);
						break;
					case 4:
						ItemStack itemStack6 = new ItemStack(BWObjects.DEMON_HORN, j);
						player.inventory.insertStack(itemStack6);
						barterTimer = 1200; //Timer exists to avoid cheese
						player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_GOLD, SoundCategory.NEUTRAL, 1, 1);
						ActionResult.success(this.world.isClient);
						break;
					case 5:
						ItemStack itemStack7 = new ItemStack(Items.BLAZE_POWDER, j);
						player.inventory.insertStack(itemStack7);
						barterTimer = 1200; //Timer exists to avoid cheese
						player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_GOLD, SoundCategory.NEUTRAL, 1, 1);
						ActionResult.success(this.world.isClient);
						break;
					case 6:
						ItemStack itemStack8 = new ItemStack(Items.REDSTONE, k);
						player.inventory.insertStack(itemStack8);
						barterTimer = 1200; //Timer exists to avoid cheese
						player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_GOLD, SoundCategory.NEUTRAL, 1, 1);
						ActionResult.success(this.world.isClient);
						break;
					case 7:
						ItemStack itemStack9 = new ItemStack(BWObjects.DEMON_HEART);
						player.inventory.insertStack(itemStack9);
						barterTimer = 4800; //Timer exists to avoid cheese
						player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_GOLD, SoundCategory.NEUTRAL, 1, 1);
						ActionResult.success(this.world.isClient);
						break;
					case 8:
						ItemStack itemStack10 = new ItemStack(BWObjects.SNAKE_TONGUE, i);
						player.inventory.insertStack(itemStack10);
						barterTimer = 1200; //Timer exists to avoid cheese
						player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_GOLD, SoundCategory.NEUTRAL, 1, 1);
						ActionResult.success(this.world.isClient);
						break;
					case 9:
						ItemStack itemStack11 = new ItemStack(Items.NETHER_WART, j);
						player.inventory.insertStack(itemStack11);
						barterTimer = 1200; //Timer exists to avoid cheese
						player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_GOLD, SoundCategory.NEUTRAL, 1, 1);
						ActionResult.success(this.world.isClient);
						break;
					case 10:
						ItemStack itemStack12 = new ItemStack(Items.LEATHER, k);
						player.inventory.insertStack(itemStack12);
						barterTimer = 1200; //Timer exists to avoid cheese
						player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_GOLD, SoundCategory.NEUTRAL, 1, 1);
						ActionResult.success(this.world.isClient);
						break;
					case 11:
						ItemStack itemStack13 = new ItemStack(Items.NAUTILUS_SHELL);
						player.inventory.insertStack(itemStack13);
						barterTimer = 4800; //Timer exists to avoid cheese
						player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_GOLD, SoundCategory.NEUTRAL, 1, 1);
						ActionResult.success(this.world.isClient);
						break;
					case 12:
						ItemStack itemStack14 = new ItemStack(Items.ENDER_PEARL, j);
						player.inventory.insertStack(itemStack14);
						barterTimer = 1200; //Timer exists to avoid cheese
						player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_GOLD, SoundCategory.NEUTRAL, 1, 1);
						ActionResult.success(this.world.isClient);
						break;
					case 13:
						ItemStack itemStack15 = new ItemStack(Items.IRON_INGOT, j);
						player.inventory.insertStack(itemStack15);
						barterTimer = 1200; //Timer exists to avoid cheese
						player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_GOLD, SoundCategory.NEUTRAL, 1, 1);
						ActionResult.success(this.world.isClient);
						break;
					case 14:
						ItemStack itemStack16 = new ItemStack(Items.ARROW, k);
						player.inventory.insertStack(itemStack16);
						barterTimer = 1200; //Timer exists to avoid cheese
						player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_GOLD, SoundCategory.NEUTRAL, 1, 1);
						ActionResult.success(this.world.isClient);
						break;
					case 15:
						ItemStack itemStack17 = new ItemStack(Items.QUARTZ, j);
						player.inventory.insertStack(itemStack17);
						barterTimer = 1200; //Timer exists to avoid cheese
						player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_GOLD, SoundCategory.NEUTRAL, 1, 1);
						ActionResult.success(this.world.isClient);
						break;
					case 16:
						ItemStack itemStack18 = new ItemStack(Items.LAPIS_LAZULI, j);
						player.inventory.insertStack(itemStack18);
						barterTimer = 1200; //Timer exists to avoid cheese
						player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_GOLD, SoundCategory.NEUTRAL, 1, 1);
						ActionResult.success(this.world.isClient);
						break;
					case 17:
						ItemStack itemStack19 = new ItemStack(Items.STRING, k);
						player.inventory.insertStack(itemStack19);
						barterTimer = 1200; //Timer exists to avoid cheese
						player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_GOLD, SoundCategory.NEUTRAL, 1, 1);
						ActionResult.success(this.world.isClient);
						break;
					case 18:
						ItemStack itemStack20 = new ItemStack(Items.GOLDEN_CHESTPLATE);
						player.inventory.insertStack(itemStack20);
						barterTimer = 1200; //Timer exists to avoid cheese
						player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_GOLD, SoundCategory.NEUTRAL, 1, 1);
						ActionResult.success(this.world.isClient);
						break;
					case 19:
						ItemStack itemStack21 = new ItemStack(BWObjects.DRAGONS_BLOOD_SAPLING);
						player.inventory.insertStack(itemStack21);
						barterTimer = 2400; //Timer exists to avoid cheese
						player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_GOLD, SoundCategory.NEUTRAL, 1, 1);
						ActionResult.success(this.world.isClient);
						break;
					case 20:
						ItemStack itemStack22 = new ItemStack(BWObjects.DRAGONS_BLOOD_RESIN, l);
						player.inventory.insertStack(itemStack22);
						barterTimer = 1200; //Timer exists to avoid cheese
						player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_GOLD, SoundCategory.NEUTRAL, 1, 1);
						ActionResult.success(this.world.isClient);
						break;
				}
			} else {
				return super.interactMob(player, hand);
			}
		}
		return super.interactMob(player, hand);
	}

	@Override
	protected boolean shouldDropLoot() {
		return super.shouldDropLoot();
	}

	@Override
	public int getVariants() {
		return 3;
	}
}
