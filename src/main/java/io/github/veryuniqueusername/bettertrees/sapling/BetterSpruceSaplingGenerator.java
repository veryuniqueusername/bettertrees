package io.github.veryuniqueusername.bettertrees.sapling;

import io.github.veryuniqueusername.bettertrees.BetterTreesConfiguredFeatures;
import net.minecraft.block.sapling.LargeTreeSaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class BetterSpruceSaplingGenerator extends LargeTreeSaplingGenerator {
	@Nullable
	@Override
	protected ConfiguredFeature<?, ?> getTreeFeature(Random random, boolean bees) {
		return random.nextBoolean() ? BetterTreesConfiguredFeatures.TREE_BETTER_SPRUCE : BetterTreesConfiguredFeatures.TREE_BETTER_PINE;
	}

	@Nullable
	@Override
	protected ConfiguredFeature<?, ?> getLargeTreeFeature(Random random) {
		return random.nextBoolean() ? BetterTreesConfiguredFeatures.TREE_BETTER_MEGA_SPRUCE : BetterTreesConfiguredFeatures.TREE_BETTER_MEGA_PINE;
	}
}
