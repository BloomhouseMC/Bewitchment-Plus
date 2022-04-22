package dev.mrsterner.bewitchmentplus.mixin.common;

import dev.mrsterner.bewitchmentplus.common.registry.BWPTags;
import moriyashiine.bewitchment.api.registry.RitualFunction;
import moriyashiine.bewitchment.common.registry.BWComponents;
import moriyashiine.bewitchment.common.ritualfunction.BindFamiliarRitualFunction;
import moriyashiine.bewitchment.common.world.BWUniversalWorldState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.function.Predicate;

@Mixin(value = BindFamiliarRitualFunction.class, remap = false)
public abstract class BindFamiliarRitualFunctionMixin extends RitualFunction {
    public BindFamiliarRitualFunctionMixin(ParticleType<?> startParticle, Predicate<LivingEntity> sacrifice) {
        super(startParticle, sacrifice);
    }

    @Inject(method = "start", at = @At(value = "INVOKE_ASSIGN", target = "net/minecraft/entity/LivingEntity.saveSelfNbt(Lnet/minecraft/nbt/NbtCompound;)Z", remap = true), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    private void start(ServerWorld world, BlockPos glyphPos, BlockPos effectivePos, Inventory inventory, boolean catFamiliar, CallbackInfo ci, boolean succeeded, ItemStack taglock, LivingEntity livingEntity, PlayerEntity closestPlayer, NbtCompound entityTag){
        if (livingEntity.getType().isIn(BWPTags.SPECTRAL_FAMILIAR)){
            BWComponents.FAMILIAR_COMPONENT.get(livingEntity).setFamiliar(true);
            BWUniversalWorldState worldState = BWUniversalWorldState.get(world);
            NbtCompound familiarTag = new NbtCompound();
            familiarTag.putUuid("UUID", entityTag.getUuid("UUID"));
            familiarTag.putString("id", Registry.ENTITY_TYPE.getId(livingEntity.getType()).toString());
            worldState.familiars.add(new Pair<>(closestPlayer.getUuid(), familiarTag));
            worldState.markDirty();
            super.start(world, glyphPos, effectivePos, inventory, catFamiliar);
            ci.cancel();
        }
    }
}
