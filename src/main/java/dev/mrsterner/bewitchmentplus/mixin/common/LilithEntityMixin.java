package dev.mrsterner.bewitchmentplus.mixin.common;

import dev.mrsterner.bewitchmentplus.common.entity.LilimEntity;
import dev.mrsterner.bewitchmentplus.common.registry.BWPEntityTypes;
import moriyashiine.bewitchment.common.entity.living.LilithEntity;
import moriyashiine.bewitchment.common.misc.BWUtil;
import net.minecraft.entity.SpawnReason;
import net.minecraft.util.math.Box;
import net.minecraft.world.ServerWorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LilithEntity.class)
public abstract class LilithEntityMixin {

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lmoriyashiine/bewitchment/common/entity/living/LilithEntity;summonMinions(Lnet/minecraft/entity/mob/MobEntity;)V"))
    private void bwplus$injectLilimSpawn(CallbackInfo ci){
        LilithEntity lilithEntity = (LilithEntity) (Object)this;
        boolean bl = lilithEntity.world.getEntitiesByClass(LilimEntity.class, new Box(lilithEntity.getBlockPos()).expand(32.0), (entity) -> true).stream().findAny().isEmpty();
        if(bl){
            LilimEntity lilimEntity = BWPEntityTypes.LILIM.create(lilithEntity.getWorld());
            BWUtil.attemptTeleport(lilimEntity, lilithEntity.getBlockPos().up(), 3, true);
            lilimEntity.initialize((ServerWorldAccess)lilithEntity.world, lilithEntity.world.getLocalDifficulty(lilithEntity.getBlockPos()), SpawnReason.EVENT, null, null);
            lilimEntity.setPitch(lilithEntity.getRandom().nextFloat() * 360.0F);
            lilimEntity.setTarget(lilithEntity.getTarget());
            lilimEntity.setPersistent();
            lilithEntity.world.spawnEntity(lilimEntity);
        }
    }
}
