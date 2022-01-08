package io.github.veryuniqueusername.bettertrees;


import io.github.veryuniqueusername.bettertrees.tree.BetterTreesConfiguredFeatures;
import io.github.veryuniqueusername.bettertrees.tree.BetterTreesPlacedFeatures;
import net.fabricmc.api.ModInitializer;


// whenever you see the word "better" in this mod put it in huge quote marks

public class BetterTrees implements ModInitializer {
    public static final String MOD_ID = "bettertrees";

    @Override
    public void onInitialize() {
        BetterTreesConfiguredFeatures.registerAll();
        BetterTreesPlacedFeatures.registerAll();
    }
}
