package dev.mrsterner.bewitchmentplus.mixin;

import com.mojang.authlib.GameProfile;
import dev.mrsterner.bewitchmentplus.common.entity.EffigyEntity;
import dev.mrsterner.bewitchmentplus.common.registry.BWPComponents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity  {
    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
        super(world, pos, yaw, profile);
    }



    @Inject(method = "onDeath", at = @At("HEAD"), cancellable = true)
    private void transferItems(CallbackInfo ci) {
        if (!this.world.isClient && BWPComponents.EFFIGY_COMPONENT.maybeGet(this).isPresent()) {
            Entity entity = ((ServerWorld) this.world).getEntity(BWPComponents.EFFIGY_COMPONENT.get(this).getEffigy());
            if(entity instanceof EffigyEntity effigyEntity){
                this.teleport(effigyEntity.getX(),effigyEntity.getY(),effigyEntity.getZ());
                this.setHealth(effigyEntity.getHealth());
                if(effigyEntity.isOnFire()){
                    this.setOnFireFor(5);
                }
                effigyEntity.playSound(SoundEvents.ITEM_TOTEM_USE, 1F,1F);
                effigyEntity.teleport(0,-256,0);
                effigyEntity.kill();
                ServerWorld serverWorld = (ServerWorld) effigyEntity.world;
                serverWorld.setChunkForced(effigyEntity.getChunkPos().x,effigyEntity.getChunkPos().z,false);
                BWPComponents.EFFIGY_COMPONENT.get(this).setHasEffigy(false);
                ci.cancel();
            }
        }
    }
}
