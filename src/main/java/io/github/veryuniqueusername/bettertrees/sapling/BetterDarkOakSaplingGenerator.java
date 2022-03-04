package io.github.veryuniqueusername.bettertrees.sapling;

import io.github.veryuniqueusername.bettertrees.BetterTreesConfiguredFeatures;
import net.minecraft.block.sapling.LargeTreeSaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class BetterDarkOakSaplingGenerator extends LargeTreeSaplingGenerator {
	@Nullable
	@Override
	protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random random, boolean bees) {
		return null;

	}

	@Nullable
	@Override
	protected ConfiguredFeature<?, ?> getLargeTreeFeature(Random random) {
		return BetterTreesConfiguredFeatures.TREE_BETTER_DARK_OAK;

	}
}
