package io.github.veryuniqueusername.bettertrees.tree;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CocoaBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

public class BetterCocoaBeansTreeDecorator extends TreeDecorator {
//	public static final BetterCocoaBeansTreeDecorator INSTANCE = new BetterCocoaBeansTreeDecorator(0.2f);
	public static final Codec<BetterCocoaBeansTreeDecorator> CODEC = (Codec.floatRange(0.0f, 1.0f).fieldOf("probability")).xmap(BetterCocoaBeansTreeDecorator::new, decorator -> decorator.probability).codec();
//	public static final Codec<BetterCocoaBeansTreeDecorator> CODEC = Codec.unit(() -> INSTANCE);
	private final float probability;

	public BetterCocoaBeansTreeDecorator(float probability) {
		this.probability = probability;
	}

	@Override
	protected TreeDecoratorType<?> getType() {
		return BetterTreesConfiguredFeatures.BETTER_COCOA_BEANS_DECORATOR;
	}

	@Override
	public void generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, List<BlockPos> logPositions, List<BlockPos> leavesPositions) {
		if (random.nextFloat() >= this.probability) {
			return;
		}
		int i = logPositions.get(0).getY();
		logPositions.stream().filter(pos -> pos.getY() - i <= 2).forEach(pos -> {
			for (Direction direction: Direction.Type.HORIZONTAL) {
				Direction direction2;
				BlockPos blockPos;
				if (!(random.nextFloat() <= 0.25f) || !Feature.isAir(world, blockPos = pos.add((direction2 = direction.getOpposite()).getOffsetX(), 0, direction2.getOffsetZ())))
					continue;
				replacer.accept(blockPos, Blocks.COCOA.getDefaultState().with(CocoaBlock.AGE, random.nextInt(3)).with(CocoaBlock.FACING, direction));
			}
		});
	}
}

