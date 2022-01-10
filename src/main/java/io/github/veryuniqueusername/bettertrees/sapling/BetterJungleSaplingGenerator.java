package io.github.veryuniqueusername.bettertrees.sapling;

import io.github.veryuniqueusername.bettertrees.tree.BetterTreesConfiguredFeatures;
import net.minecraft.block.sapling.LargeTreeSaplingGenerator;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeConfiguredFeatures;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class BetterJungleSaplingGenerator extends LargeTreeSaplingGenerator {
    @Nullable
    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random random, boolean bees) {
        return BetterTreesConfiguredFeatures.TREE_BETTER_JUNGLE_NO_VINE;
    }

    @Nullable
    @Override
    protected ConfiguredFeature<?, ?> getLargeTreeFeature(Random random) {
        return TreeConfiguredFeatures.MEGA_JUNGLE_TREE;
    }
}
