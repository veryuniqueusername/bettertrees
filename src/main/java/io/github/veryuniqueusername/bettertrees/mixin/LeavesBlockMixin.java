package io.github.veryuniqueusername.bettertrees.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(LeavesBlock.class)
public class LeavesBlockMixin extends Block {
	private static final IntProperty DISTANCE = Properties.DISTANCE_1_7;
	private static final BooleanProperty PERSISTENT = Properties.PERSISTENT;
	private static final BooleanProperty EXPOSED = BooleanProperty.of("exposed");

	public LeavesBlockMixin(Settings properties) {
		super(properties);
		this.setDefaultState(this.getStateManager().getDefaultState().with(DISTANCE, 7).with(PERSISTENT, false).with(EXPOSED, true));
	}

	@Override
	public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(DISTANCE, PERSISTENT, EXPOSED);
	}

	@Override
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		world.setBlockState(pos, getExposed(updateDistanceFromLogs(state, world, pos), world, pos), Block.NOTIFY_ALL);
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		WorldAccess world = ctx.getWorld();
		BlockPos pos = ctx.getBlockPos();
		return updateDistanceFromLogs(this.getDefaultState().with(PERSISTENT, true).with(EXPOSED, true), world, pos);
	}

	private static BlockState getExposed(BlockState state, WorldAccess world, BlockPos pos) {
		BlockPos.Mutable mutable = new BlockPos.Mutable();
		for (Direction direction: Direction.values()) {
			mutable.set(pos, direction);
//			if (!(world.getBlockState(mutable).getBlock() instanceof LeavesBlock)) return state.with(EXPOSED, true);
			if (!world.getBlockState(mutable).isOpaque()) return state.with(EXPOSED, true);
		}
		return state.with(EXPOSED, false);
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		int i = getDistanceFromLog(neighborState) + 1;
		if (i != 1 || state.get(DISTANCE) != i) {
			world.createAndScheduleBlockTick(pos, this, 1);
		}
		return state;
	}

	private static BlockState updateDistanceFromLogs(BlockState state, WorldAccess world, BlockPos pos) {
		int i = 7;
		BlockPos.Mutable mutable = new BlockPos.Mutable();
		for (Direction direction: Direction.values()) {
			mutable.set(pos, direction);
			i = Math.min(i, getDistanceFromLog(world.getBlockState(mutable)) + 1);
			if (i == 1) break;
		}
		return state.with(DISTANCE, i);
	}

	private static int getDistanceFromLog(BlockState state) {
		if (state.isIn(BlockTags.LOGS)) {
			return 0;
		}
		if (state.getBlock() instanceof LeavesBlock) {
			return state.get(DISTANCE);
		}
		return 7;
	}

	@Override
	public VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
		return VoxelShapes.empty();
	}

	@Override
	public boolean hasRandomTicks(BlockState state) {
		return state.get(DISTANCE) == 7 && !state.get(PERSISTENT);
	}

	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (!state.get(PERSISTENT) && state.get(DISTANCE) == 7) {
			dropStacks(state, world, pos);
			world.removeBlock(pos, false);
		}
	}

	@Override
	public int getOpacity(BlockState state, BlockView world, BlockPos pos) {
		return 0;
	}

	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		if (!world.hasRain(pos.up())) {
			return;
		}
		if (random.nextInt(15) != 1) {
			return;
		}
		BlockPos blockPos = pos.down();
		BlockState blockState = world.getBlockState(blockPos);
		if (blockState.isOpaque() && blockState.isSideSolidFullSquare(world, blockPos, Direction.UP)) {
			return;
		}
		double d = (double) pos.getX() + random.nextDouble();
		double e = (double) pos.getY() - 0.05;
		double f = (double) pos.getZ() + random.nextDouble();
		world.addParticle(ParticleTypes.DRIPPING_WATER, d, e, f, 0.0, 0.0, 0.0);
	}
}
