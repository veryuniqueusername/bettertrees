package io.github.veryuniqueusername.bettertrees;


import io.github.veryuniqueusername.bettertrees.tree.BetterTreesConfiguredFeatures;
import io.github.veryuniqueusername.bettertrees.tree.BetterTreesPlacedFeatures;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



// whenever you see the word "better" in this mod put it in huge quote marks

public class BetterTrees implements ModInitializer {
	public static final String MOD_ID = "bettertrees";
  public static final Logger MOD_LOGGER = LogManager.getLogger(MOD_ID);

	public static int amount = 0;


	@Override
	public void onInitialize() {
		BetterTreesConfiguredFeatures.registerAll();
		BetterTreesPlacedFeatures.registerAll();
	}
}
