package io.github.veryuniqueusername.bettertrees;

import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;

public class BetterDefaultBiomeFeatures {
	public static void addJungleBushes(GenerationSettings.Builder builder) {
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, BetterTreesPlacedFeatures.BETTER_JUNGLE_BUSHES);
	}

	public static void addBushes(GenerationSettings.Builder builder) {
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, BetterTreesPlacedFeatures.BETTER_BUSHES);
	}
}
