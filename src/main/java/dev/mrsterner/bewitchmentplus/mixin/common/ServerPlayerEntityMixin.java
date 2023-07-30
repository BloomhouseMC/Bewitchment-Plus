package dev.mrsterner.bewitchmentplus.mixin.common;

import com.mojang.authlib.GameProfile;
import dev.mrsterner.bewitchmentplus.common.entity.EffigyEntity;
import dev.mrsterner.bewitchmentplus.common.registry.BWPComponents;
import dev.mrsterner.bewitchmentplus.common.registry.BWPCurses;
import dev.mrsterner.bewitchmentplus.common.registry.BWPStatusEffects;
import moriyashiine.bewitchment.common.registry.BWComponents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.encryption.PlayerPublicKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity  {

    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @Inject(method = "onDeath", at = @At("HEAD"))
    private void removeHalfLife(CallbackInfo ci){
        if(BWComponents.CURSES_COMPONENT.get(this).hasCurse(BWPCurses.HALF_LIFE)){
            BWComponents.CURSES_COMPONENT.get(this).removeCurse(BWPCurses.HALF_LIFE);
        }
        if(this.hasStatusEffect(BWPStatusEffects.HALF_LIFE)){
            this.removeStatusEffect(BWPStatusEffects.HALF_LIFE);
        }
    }


    @Inject(method = "onDeath", at = @At(value = "HEAD", target = "Lnet/minecraft/server/network/ServerPlayerEntity;dropShoulderEntities()V"), cancellable = true)
    private void effigySavesTheDay(CallbackInfo ci) {
        if (!this.getWorld().isClient && BWPComponents.EFFIGY_COMPONENT.maybeGet(this).isPresent()) {
            Entity entity = ((ServerWorld) this.getWorld()).getEntity(BWPComponents.EFFIGY_COMPONENT.get(this).getEffigy());
            if(entity instanceof EffigyEntity effigyEntity){
                if(BWComponents.CURSES_COMPONENT.get(this).hasCurse(BWPCurses.HALF_LIFE)){
                    BWComponents.CURSES_COMPONENT.get(this).removeCurse(BWPCurses.HALF_LIFE);
                }
                if(this.hasStatusEffect(BWPStatusEffects.HALF_LIFE)){
                    this.removeStatusEffect(BWPStatusEffects.HALF_LIFE);
                }


                this.teleport(effigyEntity.getX(),effigyEntity.getY(),effigyEntity.getZ());
                this.setHealth(effigyEntity.getHealth());
                if(effigyEntity.isOnFire()){
                    this.setOnFireFor(effigyEntity.getFireTicks());
                }
                effigyEntity.playSound(SoundEvents.ITEM_TOTEM_USE, 1F,1F);
                effigyEntity.teleport(0,-256,0);
                effigyEntity.kill();
                ServerWorld serverWorld = (ServerWorld) effigyEntity.getWorld();
                serverWorld.setChunkForced(effigyEntity.getChunkPos().x,effigyEntity.getChunkPos().z,false);
                BWPComponents.EFFIGY_COMPONENT.get(this).setHasEffigy(false);
                BWPComponents.EFFIGY_COMPONENT.get(this).setEffigy(null);
                ci.cancel();
            }
        }
    }
}
