package io.github.veryuniqueusername.bettertrees.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LeavesBlock.class)
@Environment(EnvType.CLIENT)
public class LeavesBlockMixin extends Block {
	public LeavesBlockMixin(Settings settings) {
		super(settings.air());
	}

	@Override
	public boolean isSideInvisible(BlockState state, BlockState neighborState, Direction offset) {
		return neighborState.getBlock() instanceof LeavesBlock;
	}
}
