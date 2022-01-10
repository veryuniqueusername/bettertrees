package io.github.veryuniqueusername.bettertrees.mixin;

import io.github.veryuniqueusername.bettertrees.sapling.BetterBirchSaplingGenerator;
import io.github.veryuniqueusername.bettertrees.sapling.BetterJungleSaplingGenerator;
import io.github.veryuniqueusername.bettertrees.sapling.BetterOakSaplingGenerator;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.sapling.SaplingGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Blocks.class)
public class BlocksMixin {
    @Redirect(method = "<clinit>", at = @At(target = "net/minecraft/block/SaplingBlock", value = "NEW", ordinal = 0))
    private static SaplingBlock returnBetterOakSaplingBlock(SaplingGenerator generator, AbstractBlock.Settings settings) {
        return SaplingBlockInvoker.invokeCtor(new BetterOakSaplingGenerator(), settings);
    }

    @Redirect(method = "<clinit>", at = @At(target = "net/minecraft/block/SaplingBlock", value = "NEW", ordinal = 2))
    private static SaplingBlock returnBetterBirchSaplingBlock(SaplingGenerator generator, AbstractBlock.Settings settings) {
        return SaplingBlockInvoker.invokeCtor(new BetterBirchSaplingGenerator(), settings);
    }

    @Redirect(method = "<clinit>", at = @At(target = "net/minecraft/block/SaplingBlock", value = "NEW", ordinal = 3))
    private static SaplingBlock returnBetterJungleSaplingBlock(SaplingGenerator generator, AbstractBlock.Settings settings) {
        return SaplingBlockInvoker.invokeCtor(new BetterJungleSaplingGenerator(), settings);
    }
}
