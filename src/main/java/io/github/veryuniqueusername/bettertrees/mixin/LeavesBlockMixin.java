package io.github.veryuniqueusername.bettertrees.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
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
		this.setDefaultState(this.getStateManager().getDefaultState().with(DISTANCE, 7).with(PERSISTENT, false).with(EXPOSED, false));
	}

	@Override
	public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(DISTANCE, PERSISTENT, EXPOSED);
	}

	@Override
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		world.setBlockState(pos, updateState(state, world, pos), Block.NOTIFY_ALL);
	}

	@Override
	public boolean hasRandomTicks(BlockState state) {
		return (state.get(DISTANCE) == 7 && !state.get(PERSISTENT));
	}

	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (!state.get(PERSISTENT) && state.get(DISTANCE) == 7) {
			dropStacks(state, world, pos);
			world.removeBlock(pos, false);
		}
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return updateState(this.getDefaultState().with(PERSISTENT, true), ctx.getWorld(), ctx.getBlockPos());
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		int i = getDistanceFromLog(neighborState) + 1;
		if (i != 1 || state.get(DISTANCE) != i) {
			world.createAndScheduleBlockTick(pos, this, 1);
		}
		else if (getExposed(world, neighborPos) && state.get(EXPOSED)) {
			world.createAndScheduleBlockTick(pos, this, 1);
		}
		return state;
	}

	private static BlockState updateState(BlockState state, WorldAccess world, BlockPos pos) {
		int i = 7;
		boolean exposed = false;
		BlockPos.Mutable mutable = new BlockPos.Mutable();
		for (Direction direction: Direction.values()) {
			mutable.set(pos, direction);
			i = Math.min(i, getDistanceFromLog(world.getBlockState(mutable)) + 1);
			if (i == 1) break;
		}
		for (Direction direction: Direction.values()) {
			mutable.set(pos, direction);
			if (!(world.getBlockState(mutable).getBlock() instanceof LeavesBlock) && !world.getBlockState(mutable).isOpaque())
				exposed = true;
		}
		return state.with(DISTANCE, i).with(EXPOSED, exposed);
	}

	private static Boolean getExposed(WorldAccess world, BlockPos pos) {
		BlockPos.Mutable mutable = new BlockPos.Mutable();
		for (Direction direction: Direction.values()) {
			mutable.set(pos, direction);
			if (!(world.getBlockState(mutable).getBlock() instanceof LeavesBlock) && !world.getBlockState(mutable).isOpaque())
				return true;
		}
		return false;
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
	public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
		return false;
	}
}
