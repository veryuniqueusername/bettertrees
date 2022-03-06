package io.github.veryuniqueusername.bettertrees.mixin;

import io.github.veryuniqueusername.bettertrees.sapling.BetterAzaleaSaplingGenerator;
import net.minecraft.block.AzaleaBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.sapling.AzaleaSaplingGenerator;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

import java.util.Random;

@Mixin(AzaleaBlock.class)
public class AzaleaBlockMixin {
	@Redirect(method = "grow", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/sapling/AzaleaSaplingGenerator;generate(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/world/gen/chunk/ChunkGenerator;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Ljava/util/Random;)Z"))
	private boolean returnBetterAzaleaSaplingGenerator(AzaleaSaplingGenerator instance, ServerWorld serverWorld, ChunkGenerator chunkGenerator, BlockPos blockPos, BlockState blockState, Random random) {
		return new BetterAzaleaSaplingGenerator().generate(serverWorld, chunkGenerator, blockPos, blockState, random);
	}
}
