package dev.mrsterner.bewitchmentplus.mixin.common;

import dev.mrsterner.bewitchmentplus.common.registry.BWPWorldGenerators;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DefaultBiomeFeatures.class)
public class DefaultBiomeFeaturesMixin {
    @Inject(method = "addDesertVegetation", at = @At("HEAD"))
    private static void addDesertVegetation(GenerationSettings.Builder builder, CallbackInfo ci) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, BWPWorldGenerators.PATCH_BLOODROOT_DESERT);
    }

    @Inject(method = "addBadlandsVegetation", at = @At("HEAD"))
    private static void addBadlandsVegetation(GenerationSettings.Builder builder, CallbackInfo ci) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, BWPWorldGenerators.PATCH_BLOODROOT_DESERT);
    }

    @Inject(method = "addWindsweptHillsTrees", at = @At("HEAD"))
    private static void addHillsVegetation(GenerationSettings.Builder builder, CallbackInfo ci) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, BWPWorldGenerators.YEW_LIGHT);
    }

    @Inject(method = "addMeadowFlowers", at = @At("HEAD"))
    private static void addMeadowVegetation(GenerationSettings.Builder builder, CallbackInfo ci) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, BWPWorldGenerators.YEW_LIGHT);
    }

    @Inject(method = "addJungleTrees", at = @At("HEAD"))
    private static void addJungleVegetation(GenerationSettings.Builder builder, CallbackInfo ci) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, BWPWorldGenerators.LOTUS_LIGHT);
    }
}