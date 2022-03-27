package io.github.veryuniqueusername.bettertrees.tree;

import com.google.common.collect.ImmutableList;
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

public class DeadLogTrunkPlacer extends TrunkPlacer {
	public static final Codec<DeadLogTrunkPlacer> CODEC = RecordCodecBuilder.create(instance ->
		fillTrunkPlacerFields(instance).apply(instance, DeadLogTrunkPlacer::new));

	public DeadLogTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
		super(baseHeight, firstRandomHeight, secondRandomHeight);
	}

	@Override
	protected TrunkPlacerType<?> getType() {
		return BetterTreesConfiguredFeatures.DEAD_LOG_TRUNK_PLACER;
	}

	public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
		int directionId = random.nextInt(5) + 2;
		Direction direction = Direction.byId(directionId);
		Branch deadLog = new Branch(world, replacer, random, height, startPos, config, direction);
		deadLog.generate();
		return ImmutableList.of();
	}

	protected static class Branch {
		TestableWorld world;
		BiConsumer<BlockPos, BlockState> replacer;
		Random random;
		int length;
		BlockPos startPos;
		TreeFeatureConfig config;
		Direction direction;

		public Branch(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int length, BlockPos startPos, TreeFeatureConfig config, Direction direction) {
			this.world = world;
			this.replacer = replacer;
			this.random = random;
			this.length = length;
			this.startPos = startPos;
			this.config = config;
			this.direction = direction;
		}

		public List<FoliagePlacer.TreeNode> generate() {
			List<FoliagePlacer.TreeNode> list = new ArrayList<>();
			for (int i = 0; i < length; ++i) {
				setLog(startPos.offset(direction, i), direction);
			}
			return list;
		}

		private void setLog(BlockPos pos, Direction direction) {
			setLog(pos, direction.getAxis());
		}

		private void setLog(BlockPos pos, Direction.Axis axis) {
			getAndSetState(world, replacer, random, pos, config, blockState -> blockState.with(PillarBlock.AXIS, axis));
		}
	}
}
