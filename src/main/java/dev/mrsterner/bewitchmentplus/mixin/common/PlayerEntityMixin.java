package dev.mrsterner.bewitchmentplus.mixin.common;


import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.common.interfaces.CrownOfForest;
import dev.mrsterner.bewitchmentplus.common.interfaces.Magical;
import dev.mrsterner.bewitchmentplus.common.registry.*;
import dev.mrsterner.bewitchmentplus.common.transformation.LeshonLogic;
import dev.mrsterner.bewitchmentplus.common.utils.BWPUtil;
import dev.mrsterner.bewitchmentplus.common.world.BWPWorldState;
import moriyashiine.bewitchment.client.network.packet.SpawnSmokeParticlesPacket;
import moriyashiine.bewitchment.common.misc.BWUtil;
import moriyashiine.bewitchment.common.network.packet.TransformationAbilityPacket;
import moriyashiine.bewitchment.common.registry.BWComponents;
import moriyashiine.bewitchment.common.registry.BWSoundEvents;
import moriyashiine.bewitchment.common.registry.BWTransformations;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;

import static dev.mrsterner.bewitchmentplus.common.transformation.LeshonLogic.LESHON_MOVEMENT_SPEED_MODIFIER;


@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements Magical, CrownOfForest {


    @Unique
    private HashMap<BlockPos, BlockState> parsedMap = new HashMap<>();
    @Unique
    private final Identifier nbt = new Identifier(BewitchmentPlus.MODID, "yew_tree_0");
    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @Shadow @Final private PlayerInventory inventory;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }


    @Inject(method = "tickMovement", at =@At("HEAD"))
    private void lechonAttribute(CallbackInfo ci){

        PlayerEntity player = (PlayerEntity)(Object)this;
        EntityAttributeInstance movementSpeedAttribute = player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        if(BWPUtil.isLeshon(player, true)){
            LeshonLogic.sprintHandler(player);
        }else if(movementSpeedAttribute != null && movementSpeedAttribute.hasModifier(LESHON_MOVEMENT_SPEED_MODIFIER)){
            movementSpeedAttribute.removeModifier(LESHON_MOVEMENT_SPEED_MODIFIER);
        }


    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void leshonTransform(CallbackInfo ci){

        if (!world.isClient) {
            PlayerEntity player = (PlayerEntity)(Object)this;
            if(player.getEquippedStack(EquipmentSlot.HEAD).getItem().equals(BWPObjects.LESHON_SKULL.asItem()) && !player.getEquippedStack(EquipmentSlot.HEAD).getOrCreateNbt().getBoolean("Broken")){
                if(BWComponents.TRANSFORMATION_COMPONENT.get(player).getTransformation() == BWTransformations.HUMAN){
                    BWComponents.TRANSFORMATION_COMPONENT.maybeGet(player).ifPresent(transformationComponent -> {
                        transformationComponent.getTransformation().onRemoved(player);
                        transformationComponent.setTransformation(BWPTransformations.LESHON);
                        transformationComponent.getTransformation().onAdded(player);
                        LeshonLogic.handleAttributes(player);
                        TransformationAbilityPacket.useAbility(player, true);
                        PlayerLookup.tracking(this).forEach(trackingPlayer -> SpawnSmokeParticlesPacket.send(trackingPlayer, this));
                        SpawnSmokeParticlesPacket.send(player, this);
                        world.playSound(null, getBlockPos(), BWSoundEvents.ENTITY_GENERIC_CURSE, getSoundCategory(), getSoundVolume(), getSoundPitch());
                    });
                }
            }else if(BWComponents.TRANSFORMATION_COMPONENT.get(player).getTransformation() == BWPTransformations.LESHON){
                BWComponents.TRANSFORMATION_COMPONENT.maybeGet(player).ifPresent(transformationComponent -> {
                    if (transformationComponent.isAlternateForm()) {
                        TransformationAbilityPacket.useAbility(player, true);
                    }
                    transformationComponent.getTransformation().onRemoved(player);
                    transformationComponent.setTransformation(BWTransformations.HUMAN);
                    transformationComponent.getTransformation().onAdded(player);
                    LeshonLogic.handleAttributes(player);
                    PlayerLookup.tracking(this).forEach(trackingPlayer -> SpawnSmokeParticlesPacket.send(trackingPlayer, this));
                    SpawnSmokeParticlesPacket.send(player, this);
                    world.playSound(null, getBlockPos(), BWSoundEvents.ENTITY_GENERIC_CURSE, getSoundCategory(), getSoundVolume(), getSoundPitch());
                });
            }
        }



    }
    @Inject(method = "tick", at = @At("HEAD"))
    private void halfLife(CallbackInfo ci){
        if(!world.isClient()) {
            PlayerEntity player = (PlayerEntity) (Object) this;
            if (player.hasStatusEffect(BWPStatusEffects.HALF_LIFE) && player.getStatusEffect(BWPStatusEffects.HALF_LIFE).getDuration() < 10 && !BWPComponents.EFFIGY_COMPONENT.get(player).getHasEffigy()) {
                player.damage(DamageSource.MAGIC, Float.MAX_VALUE);
            }
        }
    }
    @Inject(method = "tick", at = @At("TAIL"))
    private void addDeathPlayer(CallbackInfo ci){
        /*
        if(world instanceof ServerWorld serverWorld) {
            PlayerEntity player = (PlayerEntity) (Object) this;
            BWPWorldState worldState = BWPWorldState.get(serverWorld);
            if(BWUtil.getArmorPieces(player, (stack) -> stack.getItem() instanceof ArmorItem && ((ArmorItem)stack.getItem()).getMaterial() == BWPMaterials.DEATH_ARMOR) == 3){
                worldState.deathPlayer.add(new Pair<>(player.getUuid(), true));
            }else if(worldState.deathPlayer.stream().anyMatch(worldStates -> worldStates.getLeft().equals(player.getUuid()))){
                    if (worldState.deathPlayer.remove(player.getUuid())) {
                        worldState.markDirty();
                    }
            }
        }

         */
    }
    @Inject(method = "tick", at = @At("TAIL"))
    private void crownOfTheForest(CallbackInfo ci){
        /*
        if(world instanceof ServerWorld serverWorld){
            PlayerEntity player = (PlayerEntity) (Object) this;
            if(player.isSneaking()){
                List<Pair<SlotReference, ItemStack>> component = TrinketsApi.getTrinketComponent(player).get().getEquipped(BWPObjects.CROWN_OF_THE_FOREST);
                if(!component.isEmpty()){

                    parsedMap = WorldgenHelper.getStructureMap(nbt, serverWorld);
                    CrownOfForest.of(player).ifPresent(crownOfForest -> {
                        crownOfForest.setParsedMap(WorldgenHelper.getStructureMap(nbt, serverWorld));
                        System.out.println("ParsedMapSetCCC: "+crownOfForest.getParsedMap());
                    });
                }
            }
        }

         */
    }


    @Override
    public HashMap<BlockPos, BlockState> getParsedMap(){
        return parsedMap;
    }

    public void setParsedMap(HashMap<BlockPos, BlockState> parsedMap){
        this.parsedMap = parsedMap;
    }

    @Unique
    private boolean magical = false;

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void writeBWPData(NbtCompound compoundTag, CallbackInfo info) {
        NbtList nbtList = new NbtList();
        parsedMap.forEach((pos, state) -> {
            NbtCompound map = new NbtCompound();
            map.put("BlockPos", NbtHelper.fromBlockPos(pos));
            map.put("BlockState", NbtHelper.fromBlockState(state));
            nbtList.add(map);
        });
        compoundTag.put("StructureMap", nbtList);

        NbtCompound tag = new NbtCompound();
        tag.putBoolean("Magical", magical);
        compoundTag.put("MagicalCompound", tag);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void readBWPData(NbtCompound compoundTag, CallbackInfo info) {
        NbtList nbtList = compoundTag.getList("StructureMap", NbtElement.COMPOUND_TYPE);
        parsedMap.clear();
        for(int i = 0; i < nbtList.size(); i++) {
            NbtCompound map = nbtList.getCompound(i);
            parsedMap.put(
                    NbtHelper.toBlockPos(map.getCompound("BlockPos")),
                    NbtHelper.toBlockState(map.getCompound("BlockState"))
            );
        }

        NbtCompound tag = (NbtCompound) compoundTag.get("MagicalCompound");
        if (tag != null) {
            this.magical = tag.getBoolean("Magical");
        }
    }

    @Override
    public boolean hasMagical() {
        return magical;
    }

    @Override
    public void setMagical(boolean magical) {
        this.magical = magical;
    }
}
