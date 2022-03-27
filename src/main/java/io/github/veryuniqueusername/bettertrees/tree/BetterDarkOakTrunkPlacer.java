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

public class BetterDarkOakTrunkPlacer extends GiantTrunkPlacer {
	private final double branchProbabilityModifier = 1.2D;

	public static final Codec<BetterDarkOakTrunkPlacer> CODEC = RecordCodecBuilder.create(instance -> fillTrunkPlacerFields(instance).apply(instance, BetterDarkOakTrunkPlacer::new));

	public BetterDarkOakTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
		super(baseHeight, firstRandomHeight, secondRandomHeight);
	}

	@Override
	protected TrunkPlacerType<?> getType() {
		return BetterTreesConfiguredFeatures.BETTER_DARK_OAK_TRUNK_PLACER;
	}

	@Override
	public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
		// The trunk is a branch
		Direction trunkBendDirection = Direction.byId(random.nextInt(2, 6));
		Branch mainTrunk = new Branch(world, replacer, random, startPos, config, Direction.UP, trunkBendDirection, height, 0, 2, 0.5d, 0.5d, false);
		// set dirt
		setToDirt(world, replacer, random, startPos.down(), config);
		setToDirt(world, replacer, random, startPos.down().east(), config);
		setToDirt(world, replacer, random, startPos.down().south(), config);
		setToDirt(world, replacer, random, startPos.down().south().east(), config);
		// generate roots
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

		int noBranchesBelow;
		int noBranchesAbove;

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

			// Don't spawn branches below 5 blocks along the branch if the branch is level 0 (i.e. the trunk)
			if (level == 0) {
				this.noBranchesBelow = 5;
				this.noBranchesAbove = 0;
			} else {
				this.noBranchesBelow = 0;
				this.noBranchesAbove = 0;
			}
		}

		public List<FoliagePlacer.TreeNode> generate() {
			List<FoliagePlacer.TreeNode> list = new ArrayList<>();
			BlockPos currentPos = startPos;
			double directionProbability = 2 - secondBendiness + (random.nextDouble() * 0.3);
			for (int i = 0; i < length; ++i) {
				int dx = currentPos.getX() - startPos.getX();
				int dy = currentPos.getY() - startPos.getY();
				int dz = currentPos.getZ() - startPos.getZ();
				if (level != 0 && dx * dx + dy * dy + dz * dz >= length * length) {
					i = length - 1;
				}

				if (level == 0) bendDirection = Direction.byId(random.nextInt(2, 6));
				if (i < 2 && level == 0) currentPos = currentPos.up();
				else if (level != 0) currentPos = newPos(currentPos, bendDirection, directionProbability);
				else currentPos = newPos(currentPos, bendDirection);
				BlockPos oppositeCurrentPos = currentPos.offset(direction.getOpposite(), 1);
				if (level == 0) {
					setTrunk(currentPos);
				} else { // makes branches look more joined up
					if (i > 0)
						setLog(oppositeCurrentPos, direction);
					else
						setLog(startPos, direction);
					// set the block
					setLog(currentPos, direction);
				}

				// add foliage nodes
				if (coveredWithLeaves) {
					list.add(new FoliagePlacer.TreeNode(currentPos.up(), random.nextInt(2, 4), false));
				} else if (level != 0 && i == (length - 1)) {
					list.add(new FoliagePlacer.TreeNode(currentPos.up(), 2, false));
				}

				// branches
				if (level == 0) {
					for (int dir = 0; dir < 4; dir++) {
						if (random.nextDouble() < getBranchProbability(i, length, noBranchesBelow, noBranchesAbove) || i > length - 2) {
							int newLength = random.nextInt(3) + length - (i / 2) - 4;
							Direction newDirection = Direction.byId(dir + 2);
							Direction newBendDirection;
							newBendDirection = switch (newDirection) {
								case NORTH, SOUTH -> random.nextDouble() < 0.5 ? Direction.EAST : Direction.WEST;
								case WEST, EAST -> random.nextDouble() < 0.5 ? Direction.NORTH : Direction.SOUTH;
								default -> throw new IllegalStateException("Unexpected value: " + newDirection);
							};
							BlockPos branchPos = getBranchPos(currentPos, newDirection);
							Branch branch = new Branch(world, replacer, random, branchPos, config, newDirection, newBendDirection, newLength, level + 1, maxLevel, random.nextDouble(), (0.3 * random.nextDouble()) + 0.1 + 0.10 * (i - 5), false);
							list.addAll(branch.generate());
						}
					}
				}
				// subbranches
				else {
					for (int dir = 0; dir < 4; dir++) {
						if (random.nextDouble() < getBranchProbability(i, length, noBranchesBelow, noBranchesAbove) && level == 1 && i > length - 3) {
							int newLength = random.nextInt(2, 4);
							Direction newDirection = Direction.byId(dir + 2);
							if (directionProbability > 0.5d) {
								newDirection = switch (dir) {
									case 0 -> Direction.UP;
									case 1 -> Direction.DOWN;
									case 2 -> direction.rotateClockwise(direction.getAxis());
									case 3 -> direction.rotateCounterclockwise(direction.getAxis());
									default -> throw new IllegalStateException("Unexpected value: " + dir);
								};
							}
							Direction newBendDirection;
							newBendDirection = switch (newDirection) {
								case NORTH, SOUTH -> random.nextDouble() < 0.5 ? Direction.EAST : Direction.WEST;
								case WEST, EAST -> random.nextDouble() < 0.5 ? Direction.NORTH : Direction.SOUTH;
								case UP, DOWN -> Direction.byId(random.nextInt(4) + 2);
							};
							BlockPos branchPos = getBranchPos(currentPos, newDirection);
							Branch branch = new Branch(world, replacer, random, branchPos, config, newDirection, newBendDirection, newLength, level + 1, maxLevel, random.nextDouble(), random.nextDouble(), true);
							list.addAll(branch.generate());
						}
					}
				}
			}
			return list;
		}

		private void setTrunk(BlockPos currentPos) {
			setLog(currentPos, direction);
			setLog(currentPos.east(), direction);
			setLog(currentPos.south(), direction);
			setLog(currentPos.east().south(), direction);
			setLog(currentPos.down(), direction);
			setLog(currentPos.down().east(), direction);
			setLog(currentPos.down().south(), direction);
			setLog(currentPos.down().east().south(), direction);
		}

		private BlockPos newPos(BlockPos currentPos, Direction bendDirection, double directionProbability) {
			Direction secondBendDirection = Direction.UP;
			if (direction == Direction.UP || direction == Direction.DOWN) {
				secondBendDirection = Direction.byId(random.nextInt(4) + 2);
				while (secondBendDirection == bendDirection) secondBendDirection = Direction.byId(random.nextInt(4) + 2);
			}
			return currentPos
				.offset(this.direction, random.nextDouble() < directionProbability ? 1 : 0)
				.offset(bendDirection, (random.nextDouble() < firstBendiness && random.nextDouble() < directionProbability) ? 1 : 0)
				.offset(secondBendDirection, random.nextDouble() < secondBendiness ? 1 : 0);
		}

		private BlockPos newPos(BlockPos currentPos, Direction bendDirection) {
			return newPos(currentPos, bendDirection, 1d);
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

		private double getBranchProbability(int height, int maxHeight, int clampBelow, int clampAbove) {
			// Get the probability of a branch generating at a particular point along the branch. If the branch is level 0, uses a normal distribution, else just uses half the branch probability modifier
			if (height < clampBelow || height > maxHeight - clampAbove) return 0D;
			if (this.level == 0) {
				double normalizedHeight = (double) height / maxHeight;
				return gaussian(normalizedHeight, 1.2, 0.75D, 0.4D);
			} else return 1.2 / 0.2d;
		}

		private double gaussian(double x, double a, double b, double c) {
			return a * Math.exp(-((Math.pow(x - b, 2)) / (2 * Math.pow(c, 2))));
		}
	}
}
