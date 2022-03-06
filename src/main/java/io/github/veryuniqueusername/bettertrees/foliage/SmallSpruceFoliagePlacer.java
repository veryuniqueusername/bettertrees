package io.github.veryuniqueusername.bettertrees.foliage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.veryuniqueusername.bettertrees.BetterTreesConfiguredFeatures;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

import java.util.Random;
import java.util.function.BiConsumer;

public class SmallSpruceFoliagePlacer extends FoliagePlacer {
	public static final Codec<SmallSpruceFoliagePlacer> CODEC = RecordCodecBuilder.create(instance -> SmallSpruceFoliagePlacer.fillFoliagePlacerFields(instance).apply(instance, SmallSpruceFoliagePlacer::new));

	public SmallSpruceFoliagePlacer(IntProvider radius, IntProvider offset) {
		super(radius, offset);
	}

	@Override
	protected FoliagePlacerType<?> getType() {
		return BetterTreesConfiguredFeatures.SMALL_SPRUCE_FOLIAGE_PLACER;
	}

	/**
	 * This is the main method used to generate foliage.
	 */
	@Override
	protected void generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, TreeFeatureConfig config, int trunkHeight, TreeNode treeNode, int foliageHeight, int radius, int offset) {
		BlockPos.Mutable mutable = new BlockPos.Mutable();
		for (int i = 0; i < 4; i++) {
			Direction direction = Direction.byId(i + 2);
//		Direction direction = Direction.NORTH;
			mutable.set(treeNode.getCenter().down());
			placeFoliageBlock(world, replacer, random, config, mutable);

			mutable.set(mutable.down().offset(direction));
			placeFoliageBlock(world, replacer, random, config, mutable);

			mutable.set(mutable.down());
			placeFoliageBlock(world, replacer, random, config, mutable);

			mutable.set(mutable.down());
			placeFoliageBlock(world, replacer, random, config, mutable);
			mutable.set(mutable.offset(direction));
			placeFoliageBlock(world, replacer, random, config, mutable);
			mutable.set(mutable.offset(direction.getOpposite()).offset(direction.rotateClockwise(Direction.Axis.Y)));
			placeFoliageBlock(world, replacer, random, config, mutable);

			mutable.set(mutable.down());
			placeFoliageBlock(world, replacer, random, config, mutable);
			mutable.set(mutable.offset(direction.rotateClockwise(Direction.Axis.Y)));
			placeFoliageBlock(world, replacer, random, config, mutable);
			mutable.set(mutable.offset(direction.rotateCounterclockwise(Direction.Axis.Y)).offset(direction));
			placeFoliageBlock(world, replacer, random, config, mutable);
			mutable.set(mutable.offset(direction.rotateCounterclockwise(Direction.Axis.Y)));
			placeFoliageBlock(world, replacer, random, config, mutable);
			mutable.set(mutable.offset(direction.getOpposite()));
			placeFoliageBlock(world, replacer, random, config, mutable);
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
		return false;
	}
}
