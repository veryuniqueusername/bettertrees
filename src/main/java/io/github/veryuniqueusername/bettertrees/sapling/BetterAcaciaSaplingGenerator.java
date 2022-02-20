package io.github.veryuniqueusername.bettertrees.sapling;

import io.github.veryuniqueusername.bettertrees.tree.BetterTreesConfiguredFeatures;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class BetterAcaciaSaplingGenerator extends SaplingGenerator {
    @Nullable
    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random random, boolean bees) {
        return BetterTreesConfiguredFeatures.TREE_BETTER_ACACIA;
    }
}
