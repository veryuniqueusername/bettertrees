package io.github.veryuniqueusername.bettertrees.tree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.GiantTrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

public class BetterMegaJungleTrunkPlacer extends GiantTrunkPlacer {
	private final double branchProbabilityModifier = 0.2D;

	public static final Codec<BetterMegaJungleTrunkPlacer> CODEC = RecordCodecBuilder.create(instance -> fillTrunkPlacerFields(instance).apply(instance, BetterMegaJungleTrunkPlacer::new));

	public BetterMegaJungleTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
		super(baseHeight, firstRandomHeight, secondRandomHeight);
	}

	@Override
	protected TrunkPlacerType<?> getType() {
		return BetterTreesConfiguredFeatures.BETTER_MEGA_JUNGLE_TRUNK_PLACER;
	}

	@Override
	public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
		setToDirt(world, replacer, random, startPos.down(), config);
		setToDirt(world, replacer, random, startPos.down().east(), config);
		setToDirt(world, replacer, random, startPos.down().south(), config);
		setToDirt(world, replacer, random, startPos.down().south().east(), config);
		// The trunk is a branch
		Branch mainTrunk = new Branch(world, replacer, random, startPos, startPos, config, Direction.UP, height, 0, 1, 0d, 0d, 0.1d, false);
		// generate roots
		for (int i = 2; i < 6; ++i) {
			if (random.nextDouble() < 0.5D) {
				int finalI = i;
				getAndSetState(world, replacer, random, startPos.offset(Direction.byId(i)), config, blockState -> blockState.with(PillarBlock.AXIS, Direction.byId(finalI).getAxis()));
			}
		}
		// Also generates sub-branches recursively
		return mainTrunk.generate();
	}

	protected class Branch {
		TestableWorld world;
		BiConsumer<BlockPos, BlockState> replacer;
		Random random;
		BlockPos startPos;
		BlockPos rootPos;
		TreeFeatureConfig config;
		Direction direction;

		int clampBelow;
		int level;
		int length;
		int maxLevel;
		double leftBias;
		double upBias;
		double bendiness;
		boolean nodesAllAlong;

		int bendLeft = 0;
		int bendUp = 0;

		public Branch(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos startPos, BlockPos rootPos, TreeFeatureConfig config, Direction direction, int length, int level, int maxLevel, double leftBias, double upBias, double bendiness, boolean nodesAllAlong) {
			this.world = world;
			this.replacer = replacer;
			this.random = random;
			this.startPos = startPos;
			this.rootPos = rootPos;
			this.config = config;
			this.length = length;
			this.direction = direction;
			this.level = level;
			this.maxLevel = maxLevel;
			this.leftBias = leftBias;
			if (this.direction.getAxis() == Direction.Axis.Y)
				this.upBias = this.leftBias; // If the branch is generating up or down, all directions use the leftBias
			else this.upBias = upBias;
			this.bendiness = bendiness;
			this.nodesAllAlong = nodesAllAlong;

			// Don't spawn branches below 5 blocks along the branch if the branch is level 0 (i.e. the trunk)
			if (level == 0) {
				this.clampBelow = 10;
			} else {
				this.clampBelow = 0;
			}
		}

		public List<FoliagePlacer.TreeNode> generate() {
			List<FoliagePlacer.TreeNode> list = new ArrayList<>();
			for (int i = 0; i < length; ++i) {
				// makes branches look more joined up
				if (i > 0)
					getAndSetState(world, replacer, random, bendPos(startPos, i - 1), config, blockState -> blockState.with(PillarBlock.AXIS, direction.getAxis()));
				// set the block
				getAndSetState(world, replacer, random, bendPos(startPos, i), config, blockState -> blockState.with(PillarBlock.AXIS, direction.getAxis()));
				// add foliage nodes
				if (length < 6 && i == (length - 1)) {
					list.add(new FoliagePlacer.TreeNode(bendPos(startPos, i).up(), 0, false));
				}
				// generate more leaves at the top of the trunk
				else if (length >= 6 && i == (length - 1)) {
					list.add(new FoliagePlacer.TreeNode(bendPos(startPos, i).up(), random.nextInt(1, 3), false));
				}
				updateBend();
				// generates a branch
				if ((random.nextDouble() < getBranchProbability(i, length, branchProbabilityModifier, clampBelow)) && (level < maxLevel) && i < length - 5) {
					int newLength = random.nextInt(3) + 2;
					Direction newDirection = Direction.byId(random.nextInt(4) + 2);
					Branch branch = new Branch(world, replacer, random, bendPos(startPos, i), rootPos, config, newDirection, newLength, level + 1, maxLevel, getDoubleInRange(0d, 1d), getDoubleInRange(0d, 1d), (0.6 * random.nextDouble()) + 0, false);
					list.addAll(branch.generate());
				}
			}
			return list;
		}

		private static void setTrunk(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos.Mutable pos, TreeFeatureConfig config, BlockPos startPos, int x, int y, int z) {
			pos.set(startPos, 0, y, 0);
			pos.set(startPos, 1, y, 0);
			pos.set(startPos, 1, y, 1);
			pos.set(startPos, 0, y, 1);
			BetterMegaJungleTrunkPlacer.trySetState(world, replacer, random, pos, config);
		}

		private void updateBend() {
			if (random.nextDouble() < bendiness) {
				if (random.nextDouble() < leftBias) bendLeft++;
				else bendLeft--;
			}
			if (random.nextDouble() < bendiness) {
				if (random.nextDouble() < upBias) bendUp++;
				else bendUp--;
			}
		}

		private BlockPos bendPos(BlockPos startPos, int i, Direction direction) {
			return startPos.offset(direction, i).offset(switch (direction) {
				case NORTH, DOWN -> Direction.WEST;
				case EAST -> Direction.NORTH;
				case SOUTH, UP -> Direction.EAST;
				case WEST -> Direction.SOUTH;
			}, bendLeft).offset(switch (direction) {
				case NORTH, SOUTH, EAST, WEST -> Direction.UP;
				case UP -> Direction.SOUTH;
				case DOWN -> Direction.NORTH;
			}, bendUp);
		}

		private BlockPos bendPos(BlockPos startPos, int i) {
			return bendPos(startPos, i, this.direction);
		}

		private double getBranchProbability(int height, int maxHeight, double modifier, int clampBelow) {
			// Get the probability of a branch generating at a particular point along the branch. If the branch is level 0, uses a normal distribution, else just uses half the branch probability modifier
			if (height < clampBelow) return 0D;
			if (this.level == 0) {
				double normalizedHeight = (double) height / maxHeight;
				return gaussian(normalizedHeight, modifier, 0.75D, 0.4D);
			} else return modifier / 2d;
		}

		private double gaussian(double x, double a, double b, double c) {
			return a * Math.exp(-((Math.pow(x - b, 2)) / (2 * Math.pow(c, 2))));
		}

		private double getDoubleInRange(double min, double max) {
			return ((max - min) * random.nextDouble()) + min;
		}
	}
}
