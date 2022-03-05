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
import net.minecraft.world.gen.trunk.GiantTrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

public class BetterMegaPineTrunkPlacer extends GiantTrunkPlacer {
	private final double branchProbabilityModifier = 0.2D;

	public static final Codec<BetterMegaPineTrunkPlacer> CODEC = RecordCodecBuilder.create(instance -> fillTrunkPlacerFields(instance).apply(instance, BetterMegaPineTrunkPlacer::new));

	public BetterMegaPineTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
		super(baseHeight, firstRandomHeight, secondRandomHeight);
	}

	@Override
	protected TrunkPlacerType<?> getType() {
		return BetterTreesConfiguredFeatures.BETTER_MEGA_PINE_TRUNK_PLACER;
	}

	@Override
	public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
		// TRUNK
		Direction trunkBendDirection = Direction.byId(random.nextInt(2, 6));
		Branch mainTrunk = new Branch(world, replacer, random, startPos, config, Direction.UP, trunkBendDirection, height, 0, 2, 0, 0, false);

		// DIRT
		setToDirt(world, replacer, random, startPos.down(), config);
		setToDirt(world, replacer, random, startPos.down().east(), config);
		setToDirt(world, replacer, random, startPos.down().south(), config);
		setToDirt(world, replacer, random, startPos.down().south().east(), config);

		// ROOTS
		for (int i = 2; i < 6; ++i) {
			Direction direction = Direction.byId(i);
			if (random.nextDouble() < 0.25D) {
				startPos.offset(direction);
				BlockPos rootPos = startPos.offset(direction);
				rootPos = switch (i) {
					case 2 -> // NORTH
						rootPos.east();
					case 3 -> // SOUTH
						rootPos.south().east();
					case 4 -> // WEST
						rootPos.south();
					case 5 -> // EAST
						rootPos.east().south();
					default -> rootPos;
				};
				getAndSetState(world, replacer, random, rootPos, config, blockState -> blockState.with(PillarBlock.AXIS, direction.getAxis()));
			}
			if (random.nextDouble() < 0.25D) {
				BlockPos rootPos = startPos.offset(direction);
				rootPos = switch (i) {
					case 3 -> // SOUTH
						rootPos.south();
					case 5 -> // EAST
						rootPos.east();
					default -> rootPos;
				};
				getAndSetState(world, replacer, random, rootPos, config, blockState -> blockState.with(PillarBlock.AXIS, direction.getAxis()));
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
		TreeFeatureConfig config;
		Direction direction;
		Direction bendDirection;

		int level;
		int length;
		int maxLevel;
		double firstBendiness;
		double secondBendiness;
		boolean coveredWithLeaves;

		public Branch(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos startPos, TreeFeatureConfig config, Direction direction, Direction bendDirection, int length, int level, int maxLevel, double firstBendiness, double secondBendiness, boolean coveredWithLeaves) {
			this.world = world;
			this.replacer = replacer;
			this.random = random;
			this.startPos = startPos;
			this.config = config;
			this.direction = direction;
			this.length = length;
			this.bendDirection = bendDirection;
			this.level = level;
			this.maxLevel = maxLevel;
			this.firstBendiness = firstBendiness;
			this.secondBendiness = secondBendiness;
			this.coveredWithLeaves = coveredWithLeaves;
		}

		public List<FoliagePlacer.TreeNode> generate() {
			List<FoliagePlacer.TreeNode> list = new ArrayList<>();
			BlockPos currentPos = startPos;
			for (int i = 0; i < length; ++i) {
				if (level == 0) currentPos = currentPos.up();
				else currentPos = newPos(currentPos, bendDirection);
				BlockPos oppositeCurrentPos = currentPos.offset(direction.getOpposite(), 1);

				// PLACE LOGS
				if (level == 0) {
					setTrunk(world, replacer, random, config, currentPos);
				} else {
					if (i > 0) {
						setLog(oppositeCurrentPos, direction);
					} else {
						setLog(startPos, direction);
					}
					setLog(currentPos, direction);
				}

				// PLACE LEAVES
				if (coveredWithLeaves) {
					list.add(new FoliagePlacer.TreeNode(currentPos.up(), random.nextInt(1, 3), false));
				} else if (level != 0 && i == (length - 1)) {
					list.add(new FoliagePlacer.TreeNode(currentPos.up(), 1, false));
				} else if (i == (length - 1)) {
					list.add(new FoliagePlacer.TreeNode(currentPos.up(), 1, true));
				}

				// BRANCHES
				if (level == 0 && i > length - 12) {
					for (int j = 0; j < 8; j++) {
						if (random.nextDouble() < 0.6d) {
							int newLength = random.nextInt(2) + 3;
							if (i >= length - 4) {
								newLength = Math.max(newLength - (4 + i - length), 0);
							}
							Direction newDirection = Direction.byId(random.nextInt(4) + 2);
							Direction newBendDirection;
							newBendDirection = switch (newDirection) {
								case NORTH, SOUTH -> random.nextDouble() < 0.5 ? Direction.EAST : Direction.WEST;
								case WEST, EAST -> random.nextDouble() < 0.5 ? Direction.NORTH : Direction.SOUTH;
								default -> throw new IllegalStateException("Unexpected value: " + newDirection);
							};
							BlockPos branchPos = getBranchPos(currentPos, newDirection);
							Branch branch = new Branch(world, replacer, random, branchPos, config, newDirection, newBendDirection, newLength, level + 1, maxLevel, random.nextDouble(), 0.05d, false);
							list.addAll(branch.generate());
						}
					}
				}
			}
			return list;
		}

		private void setTrunk(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, TreeFeatureConfig config, BlockPos currentPos) {
			getAndSetState(world, replacer, random, currentPos, config, blockState -> blockState.with(PillarBlock.AXIS, direction.getAxis()));
			getAndSetState(world, replacer, random, currentPos.east(), config, blockState -> blockState.with(PillarBlock.AXIS, direction.getAxis()));
			getAndSetState(world, replacer, random, currentPos.south(), config, blockState -> blockState.with(PillarBlock.AXIS, direction.getAxis()));
			getAndSetState(world, replacer, random, currentPos.east().south(), config, blockState -> blockState.with(PillarBlock.AXIS, direction.getAxis()));
			getAndSetState(world, replacer, random, currentPos.down(), config, blockState -> blockState.with(PillarBlock.AXIS, direction.getAxis()));
			getAndSetState(world, replacer, random, currentPos.down().east(), config, blockState -> blockState.with(PillarBlock.AXIS, direction.getAxis()));
			getAndSetState(world, replacer, random, currentPos.down().south(), config, blockState -> blockState.with(PillarBlock.AXIS, direction.getAxis()));
			getAndSetState(world, replacer, random, currentPos.down().east().south(), config, blockState -> blockState.with(PillarBlock.AXIS, direction.getAxis()));
		}

		private BlockPos newPos(BlockPos currentPos, Direction bendDirection) {
			return currentPos.offset(this.direction, 1).offset(bendDirection, random.nextDouble() < firstBendiness ? 1 : 0).offset(switch (direction) {
				case NORTH, SOUTH, EAST, WEST -> Direction.UP;
				case UP -> Direction.SOUTH;
				case DOWN -> Direction.NORTH;
			}, random.nextDouble() < secondBendiness ? 1 : 0);
		}

		private BlockPos getBranchPos(BlockPos currentPos, Direction newDirection) {
			BlockPos branchPos = currentPos;
			switch (newDirection.getId()) {
				case 2: // NORTH
					if (random.nextDouble() < 0.5) branchPos = branchPos.east();
					break;
				case 3: // SOUTH
					branchPos = branchPos.south();
					if (random.nextDouble() < 0.5) branchPos = branchPos.east();
					break;
				case 4: // WEST
					if (random.nextDouble() < 0.5) branchPos = branchPos.south();
					break;
				case 5: // EAST
					branchPos = branchPos.east();
					if (random.nextDouble() < 0.5) branchPos = branchPos.south();
					break;
			}
			return branchPos;
		}

		private void setLog(BlockPos pos, Direction direction) {
			setLog(pos, direction.getAxis());
		}

		private void setLog(BlockPos pos, Direction.Axis axis) {
			getAndSetState(world, replacer, random, pos, config, blockState -> blockState.with(PillarBlock.AXIS, axis));
		}

		private double getBranchProbability(int height, int maxHeight, double modifier) {
			// NORMAL DISTRUBUTION
			double normalizedHeight = (double) height / maxHeight;
			return gaussian(normalizedHeight, modifier, 0.75D, 0.4D);
		}

		private double gaussian(double x, double a, double b, double c) {
			return a * Math.exp(-((Math.pow(x - b, 2)) / (2 * Math.pow(c, 2))));
		}
	}
}
