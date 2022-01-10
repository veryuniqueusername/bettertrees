package io.github.veryuniqueusername.bettertrees.tree;

import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.world.gen.foliage.FoliagePlacerType;
import net.minecraft.world.gen.foliage.RandomSpreadFoliagePlacer;

import java.util.Random;

public class BetterJungleFoliagePlacer extends RandomSpreadFoliagePlacer {
	public BetterJungleFoliagePlacer(IntProvider radius, IntProvider offset, IntProvider foliageHeight, int leafPlacementAttempts) {
		super(radius, offset, foliageHeight, leafPlacementAttempts);
	}

	@Override
	protected FoliagePlacerType<?> getType() {
		return BetterTreesConfiguredFeatures.BETTER_JUNGLE_FOLIAGE_PLACER;
	}

	@Override
	protected boolean isInvalidForLeaves(Random random, int dx, int y, int dz, int radius, boolean giantTrunk) {
		return dx > 2;
	}
}
