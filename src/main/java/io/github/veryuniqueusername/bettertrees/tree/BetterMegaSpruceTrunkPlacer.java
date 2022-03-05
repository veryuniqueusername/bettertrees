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

import static io.github.veryuniqueusername.bettertrees.BetterTrees.MOD_LOGGER;

public class BetterMegaSpruceTrunkPlacer extends GiantTrunkPlacer {
	public static final Codec<BetterMegaSpruceTrunkPlacer> CODEC = RecordCodecBuilder.create(instance -> fillTrunkPlacerFields(instance).apply(instance, BetterMegaSpruceTrunkPlacer::new));

	public BetterMegaSpruceTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
		super(baseHeight, firstRandomHeight, secondRandomHeight);
	}

	@Override
	protected TrunkPlacerType<?> getType() {
		return BetterTreesConfiguredFeatures.BETTER_MEGA_SPRUCE_TRUNK_PLACER;
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
			if (random.nextDouble() < 0.4D) {
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
			if (random.nextDouble() < 0.40D) {
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
					list.add(new FoliagePlacer.TreeNode(currentPos.up(), random.nextInt(0, 2), false));
					list.add(new FoliagePlacer.TreeNode(currentPos, random.nextInt(1, 3), false));
					list.add(new FoliagePlacer.TreeNode(currentPos.down(), random.nextInt(0, 2), false));
				} else if (i == (length - 1)) {
					list.add(new FoliagePlacer.TreeNode(currentPos.up(), 2, true));
				}

				// STOP GROWTH IF REACHED CIRCLE
				int dx = currentPos.getX() - startPos.getX();
				int dz = currentPos.getZ() - startPos.getZ();
				if (dx * dx + dz * dz >= length * length) {
					list.add(new FoliagePlacer.TreeNode(currentPos, 1, false));
					break;
				}

				// BRANCHES
				if (level == 0 && i > 1 && i < length - 1) {
					for (int j = 0; j < 8; j++) {
						if (random.nextDouble() < 0.7d) {
							int newLength = length;
							if (i < 6) {
								newLength += (6 - i) * 2;
							}
							newLength -= 4 * i / 5;
							newLength *= 0.2;
							MOD_LOGGER.info("New branch length at i " + i + " of " + length + ": " + newLength);
							Direction newDirection = Direction.byId(random.nextInt(4) + 2);
							Direction newBendDirection;
							newBendDirection = switch (newDirection) {
								case NORTH, SOUTH -> random.nextDouble() < 0.5 ? Direction.EAST : Direction.WEST;
								case WEST, EAST -> random.nextDouble() < 0.5 ? Direction.NORTH : Direction.SOUTH;
								default -> throw new IllegalStateException("Unexpected value: " + newDirection);
							};
							BlockPos branchPos = getBranchPos(currentPos, newDirection);
							Branch branch = new Branch(world, replacer, random, branchPos, config, newDirection, newBendDirection, newLength, level + 1, maxLevel, random.nextDouble(), 0, true);
							list.addAll(branch.generate());
						}
					}
				}
				// SUB-BRANCHES
				else if (level == 1 && random.nextDouble() < getBranchProbability(i, length)) {
					int newLength = random.nextInt(1, 3);
					Direction newDirection = Direction.byId(random.nextInt(4) + 2);
					Direction newBendDirection;
					newBendDirection = switch (newDirection) {
						case NORTH, SOUTH -> random.nextDouble() < 0.5 ? Direction.EAST : Direction.WEST;
						case WEST, EAST -> random.nextDouble() < 0.5 ? Direction.NORTH : Direction.SOUTH;
						default -> throw new IllegalStateException("Unexpected value: " + newDirection);
					};
					BlockPos branchPos = getBranchPos(currentPos, newDirection);
					Branch branch = new Branch(world, replacer, random, branchPos, config, newDirection, newBendDirection, newLength, level + 1, maxLevel, random.nextDouble(), (0.2 * random.nextDouble()), true);
					list.addAll(branch.generate());
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

		private double getBranchProbability(int height, int maxHeight) {
			// NORMAL DISTRUBUTION
			double normalizedHeight = (double) height / maxHeight;
			return gaussian(normalizedHeight, 3, 0.75D, 0.4D);
		}

		private double gaussian(double x, double a, double b, double c) {
			return a * Math.exp(-((Math.pow(x - b, 2)) / (2 * Math.pow(c, 2))));
		}
	}
}
