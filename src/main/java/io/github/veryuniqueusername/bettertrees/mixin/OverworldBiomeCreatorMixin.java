package io.github.veryuniqueusername.bettertrees.mixin;

import io.github.veryuniqueusername.bettertrees.BetterTreesPlacedFeatures;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.OverworldBiomeCreator;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import static io.github.veryuniqueusername.bettertrees.BetterDefaultBiomeFeatures.addBushes;
import static io.github.veryuniqueusername.bettertrees.BetterDefaultBiomeFeatures.addJungleBushes;

@Mixin(OverworldBiomeCreator.class)
public class OverworldBiomeCreatorMixin {
	// more making our trees generate instead of vanilla trees
	@Redirect(method = "createDarkForest", at = @At(value = "INVOKE", target = "net/minecraft/world/biome/GenerationSettings$Builder.feature (Lnet/minecraft/world/gen/GenerationStep$Feature;Lnet/minecraft/world/gen/feature/PlacedFeature;)Lnet/minecraft/world/biome/GenerationSettings$Builder;", ordinal = 0))
	private static GenerationSettings.Builder returnBetterDarkForestVegetation(GenerationSettings.Builder instance, GenerationStep.Feature featureStep, PlacedFeature feature) {
		addBushes(instance);
		return instance.feature(featureStep, BetterTreesPlacedFeatures.BETTER_DARK_FOREST_VEGETATION);
	}

	@Redirect(method = "createNormalForest", at = @At(value = "INVOKE", target = "net/minecraft/world/biome/GenerationSettings$Builder.feature (Lnet/minecraft/world/gen/GenerationStep$Feature;Lnet/minecraft/world/gen/feature/PlacedFeature;)Lnet/minecraft/world/biome/GenerationSettings$Builder;", ordinal = 1))
	private static GenerationSettings.Builder returnBetterFlowerForestTrees(GenerationSettings.Builder instance, GenerationStep.Feature featureStep, PlacedFeature feature) {
		return instance.feature(featureStep, BetterTreesPlacedFeatures.BETTER_FLOWER_FOREST_TREES);
	}

	private static boolean spruceTrees;

	@Inject(method = "createOldGrowthTaiga", at = @At("HEAD"))
	private static void setSpruce(boolean spruce, CallbackInfoReturnable<Biome> cir) {
		spruceTrees = spruce;
	}
	@Redirect(method = "createOldGrowthTaiga", at = @At(value = "INVOKE", target = "net/minecraft/world/biome/GenerationSettings$Builder.feature (Lnet/minecraft/world/gen/GenerationStep$Feature;Lnet/minecraft/world/gen/feature/PlacedFeature;)Lnet/minecraft/world/biome/GenerationSettings$Builder;", ordinal = 0))
	private static GenerationSettings.Builder returnBetterOldGrowthTaigaTrees(GenerationSettings.Builder instance, GenerationStep.Feature featureStep, PlacedFeature feature) {
		return instance.feature(featureStep, spruceTrees ? BetterTreesPlacedFeatures.BETTER_OLD_GROWTH_SPRUCE_TAIGA_TREES : BetterTreesPlacedFeatures.BETTER_OLD_GROWTH_PINE_TAIGA_TREES);
	}

	@Inject(method = "createJungleFeatures", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/feature/DefaultBiomeFeatures;addJungleTrees(Lnet/minecraft/world/biome/GenerationSettings$Builder;)V"), locals = LocalCapture.CAPTURE_FAILHARD)
	private static void injectAddJungleBushes(float depth, boolean bamboo, boolean sparse, boolean unmodified, SpawnSettings.Builder spawnSettings, CallbackInfoReturnable<Biome> cir, GenerationSettings.Builder builder) {
		addJungleBushes(builder);
	}
}
