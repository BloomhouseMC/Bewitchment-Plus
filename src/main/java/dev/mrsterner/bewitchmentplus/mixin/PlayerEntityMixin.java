package dev.mrsterner.bewitchmentplus.mixin;


import dev.mrsterner.bewitchmentplus.api.Magical;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements Magical {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Unique
    private boolean magical = false;

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void writeMiskData(NbtCompound compoundTag, CallbackInfo info) {
        NbtCompound tag = new NbtCompound();
        tag.putBoolean("Magical", magical);
        compoundTag.put("MagicalCompound", tag);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    public void readMiskData(NbtCompound compoundTag, CallbackInfo info) {
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
