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

public class BetterTallBirchTrunkPlacer extends TrunkPlacer {
	private int branchLengthModifier; // gets subtracted from the length of each new Branch (parent branch level > 0)
	private int initialBranchLengthModifier; // gets subtracted from the length of each new Branch generated off the trunk
	private double branchProbabilityModifier;
	private double subBranchProbabilityDivisor;
	private double minLeftBias;
	private double maxLeftBias;
	private double minUpBias;
	private double maxUpBias;

	public static final Codec<BetterTallBirchTrunkPlacer> CODEC = RecordCodecBuilder.create(instance -> fillTrunkPlacerFields(instance).apply(instance, BetterTallBirchTrunkPlacer::new));

	public BetterTallBirchTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
		this(baseHeight, firstRandomHeight, secondRandomHeight, 0.5D, 2D, 0, 0, 0D, 1D, 0D, 1D);
	}

	public BetterTallBirchTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight, double branchProbabilityModifier, double subBranchProbabilityDivisor, int branchLengthModifier, int initialBranchLengthModifier, double minLeftBias, double maxLeftBias, double minUpBias, double maxUpBias) {
		super(baseHeight, firstRandomHeight, secondRandomHeight);
		this.branchLengthModifier = branchLengthModifier;
		this.initialBranchLengthModifier = initialBranchLengthModifier;
		this.branchProbabilityModifier = branchProbabilityModifier;
		this.subBranchProbabilityDivisor = subBranchProbabilityDivisor;
		this.minLeftBias = minLeftBias;
		this.maxLeftBias = maxLeftBias;
		this.minUpBias = minUpBias;
		this.maxUpBias = maxUpBias;
	}

	@Override
	protected TrunkPlacerType<?> getType() {
		return BetterTreesConfiguredFeatures.BETTER_TALL_BIRCH_TRUNK_PLACER;
	}

	@Override
	public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
		setToDirt(world, replacer, random, startPos.down(), config);
		// The trunk is a branch
		Branch mainTrunk = new Branch(world, replacer, random, startPos, startPos, config, Direction.UP, height, 0, 3, 0d, 0d, 0.05d, false);
		// generate roots
		for (int i = 2; i < 6; ++i) {
			if (random.nextDouble() < 0.4D) {
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
				this.clampBelow = 5;
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
				if ((i > (length - 3) || i > 5) && level == 0) // generate more leaves at the top of the branch
					list.add(new FoliagePlacer.TreeNode(bendPos(startPos, i).up(), 2, false));
				else if (i > (length - 8) && level != 0)
					list.add(new FoliagePlacer.TreeNode(bendPos(startPos, i).up(), 2, false));
				updateBend();
				//                 generates a sub-branch
				if ((random.nextDouble() < getBranchProbability(i, length, branchProbabilityModifier, clampBelow)) && (level < maxLevel)) {
					int newLength = length - (random.nextInt(2) + 1);
					if (level == 0) newLength = newLength - initialBranchLengthModifier;
					else newLength = newLength - branchLengthModifier;
					Direction newDirection = Direction.UP;
					BlockPos newEndPos = bendPos(startPos, i).offset(newDirection, newLength);
					int newBranchHeight = newEndPos.getY() - rootPos.getY();
					if (newLength > 0 && (newEndPos.getManhattanDistance(rootPos) < (15 + newBranchHeight / 2))) { // restrict distance branches can be from the trunk
						Branch branch = new Branch(world, replacer, random, bendPos(startPos, i), rootPos, config, newDirection, newLength, level + 1, maxLevel, getDoubleInRange(minLeftBias, maxLeftBias), getDoubleInRange(minUpBias, maxUpBias), (0.2 * random.nextDouble()) + 0, true);
						list.addAll(branch.generate());
					}
				}
			}
			return list;
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
				return gaussian(normalizedHeight, modifier, 0.75D, 0.2D);
			} else return modifier / subBranchProbabilityDivisor;
		}

		private double gaussian(double x, double a, double b, double c) {
			return a * Math.exp(-((Math.pow(x - b, 2)) / (2 * Math.pow(c, 2))));
		}

		private double getDoubleInRange(double min, double max) {
			return ((max - min) * random.nextDouble()) + min;
		}
	}
}
