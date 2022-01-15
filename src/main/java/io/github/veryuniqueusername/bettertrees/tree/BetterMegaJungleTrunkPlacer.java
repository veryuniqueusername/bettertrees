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
		// The trunk is a branch
		Direction trunkBendDirection = Direction.byId(random.nextInt(2, 6));
		Branch mainTrunk = new Branch(world, replacer, random, startPos, config, Direction.UP, trunkBendDirection, height, 0, 1, 0.05d);
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
		double bendiness;

		int clampBelow;

		public Branch(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos startPos, TreeFeatureConfig config, Direction direction, Direction bendDirection, int length, int level, int maxLevel, double bendiness) {
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
			this.bendiness = bendiness;

			// Don't spawn branches below 5 blocks along the branch if the branch is level 0 (i.e. the trunk)
			if (level == 0) {
				this.clampBelow = 10;
			} else {
				this.clampBelow = 0;
			}
		}

		public List<FoliagePlacer.TreeNode> generate() {
			List<FoliagePlacer.TreeNode> list = new ArrayList<>();
			BlockPos currentPos = startPos;
			for (int i = 0; i < length; ++i) {
				bendDirection = Direction.byId(random.nextInt(2, 6));
				if (i >= 2 && level == 0) currentPos = newPos(currentPos, bendDirection);
				else if (level == 0) currentPos = currentPos.up();
				BlockPos oppositeCurrentPos = currentPos.offset(direction.getOpposite(), 1);
				//				BlockPos bendedPos = bendPos(startPos, i, bendDirection);
				if (level == 0) {
					setTrunk(world, replacer, random, config, currentPos);
				} else { // makes branches look more joined up
					if (i > 0)
						getAndSetState(world, replacer, random, oppositeCurrentPos, config, blockState -> blockState.with(PillarBlock.AXIS, direction.getAxis()));
					else
						getAndSetState(world, replacer, random, startPos, config, blockState -> blockState.with(PillarBlock.AXIS, direction.getAxis()));
					// set the block
					getAndSetState(world, replacer, random, currentPos, config, blockState -> blockState.with(PillarBlock.AXIS, direction.getAxis()));
				} // add foliage nodes
				if (level != 0 && i == (length - 1)) {
					list.add(new FoliagePlacer.TreeNode(currentPos.up(), 0, false));
				}
				// generate more leaves at the top of the trunk
				else if (level == 0 && i == (length - 1)) {
					list.add(new FoliagePlacer.TreeNode(currentPos.up(), random.nextInt(1, 3), true));
				}
				// generates a branch
				if (((random.nextDouble() < getBranchProbability(i, length, branchProbabilityModifier, clampBelow) && (level < maxLevel) && i < length - 5)) || (level == 0 && i == length - 1)) {
					int newLength = random.nextInt(3) + 2;
					Direction newDirection = Direction.byId(random.nextInt(4) + 2);
					Direction newBendDirection = Direction.byId(random.nextInt(2, 6));
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
					Branch branch = new Branch(world, replacer, random, branchPos, config, newDirection, newBendDirection, newLength, level + 1, maxLevel, (0.6 * random.nextDouble()) + 0);
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
			return currentPos.offset(this.direction, 1).offset(bendDirection, random.nextDouble() < bendiness ? 1 : 0).offset(switch (direction) {
				case NORTH, SOUTH, EAST, WEST -> Direction.UP;
				case UP -> Direction.SOUTH;
				case DOWN -> Direction.NORTH;
			}, random.nextDouble() < bendiness ? 1 : 0);
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
	}
}
