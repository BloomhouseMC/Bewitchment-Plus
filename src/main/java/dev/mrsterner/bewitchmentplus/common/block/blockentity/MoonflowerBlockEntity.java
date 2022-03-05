package dev.mrsterner.bewitchmentplus.common.block.blockentity;

import dev.mrsterner.bewitchmentplus.common.registry.BWPBlockEntityTypes;
import dev.mrsterner.bewitchmentplus.common.registry.BWPObjects;
import moriyashiine.bewitchment.api.BewitchmentAPI;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class MoonflowerBlockEntity extends BlockEntity implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);

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


    private <E extends BlockEntity & IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.moonflower."+getMoon(), true));
        return PlayState.CONTINUE;
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
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}