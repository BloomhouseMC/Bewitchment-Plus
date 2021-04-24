package net.bewitchmentplus.common.entity.living;

import moriyashiine.bewitchment.api.BewitchmentAPI;
import moriyashiine.bewitchment.common.entity.living.util.BWHostileEntity;
import net.bewitchmentplus.BewitchmentPlus;
import net.bewitchmentplus.common.entity.util.CambionBrain;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.StructureFeature;

public class CambionEntity extends BWHostileEntity {
	private final SimpleInventory inventory = new SimpleInventory(8);

	protected CambionEntity(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
	}

	public EntityGroup getGroup() {
		return BewitchmentAPI.DEMON;
	}

	protected boolean hasShiny() {
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
			return (nearestVillage != null && Math.sqrt(nearestVillage.getSquaredDistance(getBlockPos())) < 128);
		}
		return false;
	}

	protected void equipToMainHand(ItemStack stack) {
		this.equipLootStack(EquipmentSlot.MAINHAND, stack);
	}

	protected void equipToOffHand(ItemStack stack) {
		if (stack.getItem() == CambionBrain.BARTERING_ITEM) {
			this.equipStack(EquipmentSlot.OFFHAND, stack);
			this.updateDropChances(EquipmentSlot.OFFHAND);
		} else {
			this.equipLootStack(EquipmentSlot.OFFHAND, stack);
		}

	}

	public boolean isAdult() {
		return !this.isBaby();
	}

	protected ItemStack addItem(ItemStack stack) {
		return this.inventory.addStack(stack);
	}

	public boolean canGather(ItemStack stack) {
		return this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING) && this.canPickUpLoot() && CambionBrain.canGather(this, stack);
	}

	@Override
	public int getVariants() {
		return 6;
	}
}
