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

public class BetterPineTrunkPlacer extends TrunkPlacer {
	public static final Codec<BetterPineTrunkPlacer> CODEC = RecordCodecBuilder.create(instance -> fillTrunkPlacerFields(instance).apply(instance, BetterPineTrunkPlacer::new));

	public BetterPineTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
		super(baseHeight, firstRandomHeight, secondRandomHeight);
	}

	@Override
	protected TrunkPlacerType<?> getType() {
		return BetterTreesConfiguredFeatures.BETTER_PINE_TRUNK_PLACER;
	}

	@Override
	public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
		setToDirt(world, replacer, random, startPos.down(), config);

		Direction trunkBendDirection = Direction.byId(random.nextInt(2, 6));
		Branch trunk = new Branch(world, replacer, random, height, startPos, config, Direction.UP, trunkBendDirection, 0, 1, 0d, 0d, false);

		// roots
		//		for (int i = 0; i < 4; ++i) {
		//			Direction dir = Direction.byId(i + 2);
		//			if (random.nextDouble() < 0.7D) {
		//				getAndSetState(world, replacer, random, rootPos.offset(dir), config, blockState -> blockState.with(PillarBlock.AXIS, dir.getAxis()));
		//			}
		//		}

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
				this.noBranchesBelow = 2;
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
				currentPos = newPos(currentPos, bendDirection);
				BlockPos oppositeCurrentPos = currentPos.offset(direction.getOpposite(), 1);

				// PLACE LOGS
				if (i > 0) {
					setLog(oppositeCurrentPos, direction);
				} else {
					setLog(startPos, direction);
				}
				setLog(currentPos, direction);

				// PLACE LEAVES
				if (i == length - 1) {
					list.add(new FoliagePlacer.TreeNode(currentPos.up(), 0, false));
				}

				// BRANCHES
				if (level == 0 && i > length - 8) {
					for (int dir = 0; dir < 4; ++dir) {
						int newLength = random.nextInt(2) + 1;
						if (i >= length - 2) {
							newLength = Math.max(newLength - length + i, 0);
						}
						Direction newDirection = Direction.byId(dir + 2);
						Direction newBendDirection;
						newBendDirection = switch (newDirection) {
							case NORTH, SOUTH -> random.nextDouble() < 0.5 ? Direction.EAST : Direction.WEST;
							case WEST, EAST -> random.nextDouble() < 0.5 ? Direction.NORTH : Direction.SOUTH;
							default -> throw new IllegalStateException("Unexpected value at newBendDirection: " + newDirection);
						};
						Branch branch = new Branch(world, replacer, random, newLength, currentPos, config, newDirection, newBendDirection, level + 1, maxLevel, random.nextDouble(), 0, false);
						list.addAll(branch.generate());
					}
				}
			}
			return list;
		}

		private BlockPos newPos(BlockPos currentPos, Direction bendDirection, double directionProbability) {
			return currentPos.offset(this.direction, random.nextDouble() < directionProbability ? 1 : 0).offset(bendDirection, (random.nextDouble() < firstBendiness && random.nextDouble() < directionProbability) ? 1 : 0).offset(switch (direction) {
				case NORTH, SOUTH, EAST, WEST -> Direction.UP;
				case UP -> Direction.byId(random.nextInt(4) + 2);
				case DOWN -> Direction.NORTH;
			}, random.nextDouble() < secondBendiness ? 1 : 0);
		}

		private BlockPos newPos(BlockPos currentPos, Direction bendDirection) {
			return newPos(currentPos, bendDirection, 1d);
		}

		private void setLog(BlockPos pos, Direction direction) {
			setLog(pos, direction.getAxis());
		}

		private void setLog(BlockPos pos, Direction.Axis axis) {
			getAndSetState(world, replacer, random, pos, config, blockState -> blockState.with(PillarBlock.AXIS, axis));
		}
	}
}
