package io.github.veryuniqueusername.bettertrees.foliage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.veryuniqueusername.bettertrees.BetterTreesConfiguredFeatures;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

import java.util.Random;
import java.util.function.BiConsumer;

public class CircleFoliagePlacer extends FoliagePlacer {
	public static final Codec<CircleFoliagePlacer> CODEC = RecordCodecBuilder.create(instance -> CircleFoliagePlacer.fillFoliagePlacerFields(instance).and((Codec.doubleRange(0, 1).fieldOf("chance")).forGetter(placer -> placer.chance)).apply(instance, CircleFoliagePlacer::new));
	private final double chance;

	public CircleFoliagePlacer(IntProvider radius, IntProvider offset, double chance) {
		super(radius, offset);
		this.chance = chance;
	}

	@Override
	protected FoliagePlacerType<?> getType() {
		return BetterTreesConfiguredFeatures.CIRCLE_FOLIAGE_PLACER;
	}

	/**
	 * This is the main method used to generate foliage.
	 */
	@Override
	protected void generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, TreeFeatureConfig config, int trunkHeight, TreeNode treeNode, int foliageHeight, int radius, int offset) {
		for (int i = offset; i >= offset - foliageHeight; --i) {
			this.generateSquare(world, replacer, random, config, treeNode.getCenter(), radius + treeNode.getFoliageRadius(), i, treeNode.isGiantTrunk());
		}
	}

	@Override
	public int getRandomHeight(Random random, int trunkHeight, TreeFeatureConfig config) {
		return 0;
	}

	/**
	 * Used to exclude certain positions such as corners when creating a square of leaves.
	 */
	@Override
	protected boolean isInvalidForLeaves(Random random, int dx, int y, int dz, int radius, boolean giantTrunk) {
		if (random.nextDouble() > this.chance) {
			return true;
		}
		return dx * dx + dz * dz > radius * radius;
	}
}
