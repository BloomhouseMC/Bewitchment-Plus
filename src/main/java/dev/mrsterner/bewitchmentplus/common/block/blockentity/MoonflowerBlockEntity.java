package dev.mrsterner.bewitchmentplus.common.block.blockentity;

import dev.mrsterner.bewitchmentplus.common.registry.BWPBlockEntityTypes;
import moriyashiine.bewitchment.api.BewitchmentAPI;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class MoonflowerBlockEntity extends BlockEntity implements GeoBlockEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public MoonflowerBlockEntity(BlockPos pos, BlockState state) {
        super(BWPBlockEntityTypes.MOONFLOWER_BLOCK_ENTITY, pos, state);
    }

    public String getMoon(){
        if(this.world != null){
            return switch (BewitchmentAPI.getMoonPhase(this.world)) {
                case 1 -> "waningGibbous";
                case 2 -> "thirdQuarter";
                case 3 -> "waningCrescent";
                case 4 -> "newMoon";
                case 5 -> "waxingCrescent";
                case 6 -> "firstQuarter";
                case 7 -> "waxingGibbous";
                default -> "fullMoon";
            };
        }else{
            return "fullMoon";
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controller) {
        controller.add(new AnimationController<>(this, "c", 0, this::predicate));
    }

    private PlayState predicate(AnimationState<MoonflowerBlockEntity> state) {
        state.getController().setAnimation(RawAnimation.begin().thenLoop("animation.moonflower."+getMoon()));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}