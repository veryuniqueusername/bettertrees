package io.github.veryuniqueusername.bettertrees.tree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.veryuniqueusername.bettertrees.BetterTreesConfiguredFeatures;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

public class BetterOakTrunkPlacer extends TrunkPlacer {

	public static final Codec<BetterOakTrunkPlacer> CODEC = RecordCodecBuilder.create(instance -> fillTrunkPlacerFields(instance).apply(instance, BetterOakTrunkPlacer::new));

	public BetterOakTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
		super(baseHeight, firstRandomHeight, secondRandomHeight);
	}

	@Override
	protected TrunkPlacerType<?> getType() {
		return BetterTreesConfiguredFeatures.BETTER_OAK_TRUNK_PLACER;
	}

	@Override
	public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
		setToDirt(world, replacer, random, startPos.down(), config);

		// TRUNK
		Direction trunkBendDirection = Direction.byId(random.nextInt(2, 6));
		Branch trunk = new Branch(world, replacer, random, height, startPos, config, Direction.UP, trunkBendDirection, 0, 2, 0.8d, 0.4d, false);

		// ROOTS
		for (int i = 0; i < 4; ++i) {
			Direction dir = Direction.byId(i + 2);
			if (random.nextDouble() < 0.7D) {
				getAndSetState(world, replacer, random, startPos.offset(dir), config, blockState -> blockState.with(PillarBlock.AXIS, dir.getAxis()));
			}
		}

		// Also generates sub-branches recursively
		return trunk.generate();
	}

	protected class Branch {
		TestableWorld world;
		BiConsumer<BlockPos, BlockState> replacer;
		Random random;
		int length;
		BlockPos startPos;
		TreeFeatureConfig config;
		Direction direction;
		Direction bendDirection;
		int level;
		int maxLevel;
		double firstBendiness;
		double secondBendiness;
		boolean coveredWithLeaves;

		int noBranchesBelow;
		int noBranchesAbove;

		public Branch(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int length, BlockPos startPos, TreeFeatureConfig config, Direction direction, Direction bendDirection, int level, int maxLevel, double firstBendiness, double secondBendiness, boolean coveredWithLeaves) {
			this.world = world;
			this.replacer = replacer;
			this.random = random;
			this.length = length;
			this.startPos = startPos;
			this.config = config;
			this.direction = direction;
			this.bendDirection = bendDirection;
			this.level = level;
			this.maxLevel = maxLevel;
			this.firstBendiness = firstBendiness;
			this.secondBendiness = secondBendiness;
			this.coveredWithLeaves = coveredWithLeaves;

			if (level == 0) {
				this.noBranchesBelow = 4;
				this.noBranchesAbove = 0;
			} else {
				this.noBranchesBelow = 0;
				this.noBranchesAbove = 0;
			}
		}

		public List<FoliagePlacer.TreeNode> generate() {
			List<FoliagePlacer.TreeNode> list = new ArrayList<>();
			BlockPos currentPos = startPos;
			for (int i = 0; i < length; ++i) {
				if (level == 0) bendDirection = Direction.byId(random.nextInt(2, 6));

				if (level == 0 && i < 2) {
					currentPos = currentPos.up();
				} else {
					currentPos = newPos(currentPos, bendDirection);
				}
				BlockPos oppositeCurrentPos = currentPos.offset(direction.getOpposite(), 1);

				// PLACE LOGS
				if (i > 0) {
					setLog(oppositeCurrentPos, direction);
				} else {
					setLog(startPos, direction);
				}
				setLog(currentPos, direction);

				// PLACE LEAVES
				if (coveredWithLeaves) {
					list.add(new FoliagePlacer.TreeNode(currentPos.up(), random.nextInt(1, 2), false));
				} else if (level != 0 && i == length - 1) {
					list.add(new FoliagePlacer.TreeNode(currentPos.up(), random.nextInt(1, 2), false));
				}

				// BRANCHES
				if (level == 0 && level < maxLevel) {
					for (int j = 0; j < 2; ++j) {
						if (random.nextDouble() < getBranchProbability(i, length, noBranchesBelow, noBranchesAbove)) {
							int newLength = random.nextInt(2, 4);
							Direction newDirection = Direction.byId(random.nextInt(2, 6));
							Direction newBendDirection;
							newBendDirection = switch (newDirection) {
								case NORTH, SOUTH -> random.nextDouble() < 0.5 ? Direction.EAST : Direction.WEST;
								case WEST, EAST -> random.nextDouble() < 0.5 ? Direction.NORTH : Direction.SOUTH;
								case UP -> Direction.byId(random.nextInt(2, 6));
								default -> throw new IllegalStateException("Unexpected value: " + newDirection);
							};
							Branch branch = new Branch(world, replacer, random, newLength, currentPos, config, newDirection, newBendDirection, level + 1, maxLevel, random.nextDouble(), (0.5 * random.nextDouble() + 0.2), false);
							list.addAll(branch.generate());
						}
					}
				}
				// SUB-BRANCHES
				else if (level == 1) {
					for (int j = 0; j < 2; ++j) {
						if (random.nextDouble() < 0.5d) {
							int newLength = random.nextInt(1, 3);
							Direction newDirection = Direction.byId(random.nextInt(1, 6));
							Direction newBendDirection;
							newBendDirection = switch (newDirection) {
								case NORTH, SOUTH -> random.nextDouble() < 0.5 ? Direction.EAST : Direction.WEST;
								case WEST, EAST -> random.nextDouble() < 0.5 ? Direction.NORTH : Direction.SOUTH;
								case UP, DOWN -> Direction.byId(random.nextInt(2, 6));
							};
							Branch branch = new Branch(world, replacer, random, newLength, currentPos, config, newDirection, newBendDirection, level + 1, maxLevel, random.nextDouble(), (0.6 * random.nextDouble() + 0.5), true);
							list.addAll(branch.generate());
						}
					}
				}
			}
			return list;
		}

		private BlockPos newPos(BlockPos currentPos, Direction bendDirection) {
			Direction secondBendDirection = Direction.UP;
			if (direction == Direction.UP || direction == Direction.DOWN) {
				secondBendDirection = Direction.byId(random.nextInt(4) + 2);
				while (secondBendDirection == bendDirection) secondBendDirection = Direction.byId(random.nextInt(4) + 2);
			}
			return currentPos
				.offset(this.direction, 1)
				.offset(bendDirection, random.nextDouble() < firstBendiness ? 1 : 0)
				.offset(secondBendDirection, random.nextDouble() < secondBendiness ? 1 : 0);
		}

		private void setLog(BlockPos pos, Direction direction) {
			setLog(pos, direction.getAxis());
		}

		private void setLog(BlockPos pos, Direction.Axis axis) {
			getAndSetState(world, replacer, random, pos, config, blockState -> blockState.with(PillarBlock.AXIS, axis));
		}

		private double getBranchProbability(int height, int maxHeight, int clampBelow, int clampAbove) {
			// Get the probability of a branch generating at a particular point along the branch. Uses a normal distribution.
			if (height < clampBelow || height > maxHeight - clampAbove) return 0D;

			double normalizedHeight = (double) height / maxHeight;
			return gaussian(normalizedHeight, 2, 0.75D, 0.4D);
		}

		private double gaussian(double x, double multiplier, double b, double c) {
			return multiplier * Math.exp(-((Math.pow(x - b, 2)) / (2 * Math.pow(c, 2))));
		}
	}
}
