package io.github.veryuniqueusername.bettertrees;

import com.google.common.collect.ImmutableList;
import io.github.veryuniqueusername.bettertrees.BetterTreesConfiguredFeatures;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.decorator.*;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;

import static io.github.veryuniqueusername.bettertrees.BetterTrees.MOD_ID;
import static net.minecraft.world.gen.feature.TreePlacedFeatures.ON_SNOW_PREDICATE;

public class BetterTreesPlacedFeatures {
	public static final PlacedFeature DEAD_OAK_LOG = new PlacedFeature(() -> BetterTreesConfiguredFeatures.DEAD_OAK_LOG, ImmutableList.of(BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.getDefaultState(), BlockPos.ORIGIN))));

	public static final PlacedFeature DEAD_SPRUCE_LOG = new PlacedFeature(() -> BetterTreesConfiguredFeatures.DEAD_SPRUCE_LOG, ImmutableList.of(BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(Blocks.SPRUCE_SAPLING.getDefaultState(), BlockPos.ORIGIN))));

	public static final PlacedFeature DEAD_BIRCH_LOG = new PlacedFeature(() -> BetterTreesConfiguredFeatures.DEAD_BIRCH_LOG, ImmutableList.of(BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(Blocks.BIRCH_SAPLING.getDefaultState(), BlockPos.ORIGIN))));

	public static final PlacedFeature DEAD_JUNGLE_LOG = new PlacedFeature(() -> BetterTreesConfiguredFeatures.DEAD_JUNGLE_LOG, ImmutableList.of(BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(Blocks.JUNGLE_SAPLING.getDefaultState(), BlockPos.ORIGIN))));

	public static final PlacedFeature DEAD_ACACIA_LOG = new PlacedFeature(() -> BetterTreesConfiguredFeatures.DEAD_ACACIA_LOG, ImmutableList.of(BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(Blocks.ACACIA_SAPLING.getDefaultState(), BlockPos.ORIGIN))));

	public static final PlacedFeature OAK_STUMP = new PlacedFeature(() -> BetterTreesConfiguredFeatures.OAK_STUMP, ImmutableList.of(BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.getDefaultState(), BlockPos.ORIGIN))));

	public static final PlacedFeature BIRCH_STUMP = new PlacedFeature(() -> BetterTreesConfiguredFeatures.BIRCH_STUMP, ImmutableList.of(BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(Blocks.BIRCH_SAPLING.getDefaultState(), BlockPos.ORIGIN))));

	public static final PlacedFeature UNDERGROWTH_BUSH = new PlacedFeature(() -> BetterTreesConfiguredFeatures.UNDERGROWTH_BUSH, ImmutableList.of(BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.getDefaultState(), BlockPos.ORIGIN))));

	public static final PlacedFeature TREE_BETTER_OAK = new PlacedFeature(() -> BetterTreesConfiguredFeatures.TREE_BETTER_OAK, ImmutableList.of(BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.getDefaultState(), BlockPos.ORIGIN))));

	public static final PlacedFeature TREE_DEAD_OAK = new PlacedFeature(() -> BetterTreesConfiguredFeatures.TREE_DEAD_OAK, ImmutableList.of(BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.getDefaultState(), BlockPos.ORIGIN))));

	public static final PlacedFeature TREE_BETTER_OAK_RARE_BEES = new PlacedFeature(() -> BetterTreesConfiguredFeatures.TREE_BETTER_OAK_RARE_BEES, ImmutableList.of(BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.getDefaultState(), BlockPos.ORIGIN))));

	public static final PlacedFeature TREE_BETTER_OAK_REGULAR_BEES = new PlacedFeature(() -> BetterTreesConfiguredFeatures.TREE_BETTER_OAK_REGULAR_BEES, ImmutableList.of(BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.getDefaultState(), BlockPos.ORIGIN))));

	public static final PlacedFeature TREE_BETTER_OAK_MORE_BEES = new PlacedFeature(() -> BetterTreesConfiguredFeatures.TREE_BETTER_OAK_MORE_BEES, ImmutableList.of(BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.getDefaultState(), BlockPos.ORIGIN))));

	public static final PlacedFeature TREE_BETTER_SWAMP_OAK = new PlacedFeature(() -> BetterTreesConfiguredFeatures.TREE_BETTER_SWAMP_OAK, ImmutableList.of(BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.getDefaultState(), BlockPos.ORIGIN))));

	public static final PlacedFeature TREE_BETTER_BIRCH = new PlacedFeature(() -> BetterTreesConfiguredFeatures.TREE_BETTER_BIRCH, ImmutableList.of(BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(Blocks.BIRCH_SAPLING.getDefaultState(), BlockPos.ORIGIN))));

	public static final PlacedFeature TREE_BETTER_SMALL_SPRUCE = new PlacedFeature(() -> BetterTreesConfiguredFeatures.TREE_BETTER_SMALL_SPRUCE, ImmutableList.of(BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(Blocks.SPRUCE_SAPLING.getDefaultState(), BlockPos.ORIGIN))));

	public static final PlacedFeature TREE_BETTER_SPRUCE = new PlacedFeature(() -> BetterTreesConfiguredFeatures.TREE_BETTER_SPRUCE, ImmutableList.of(BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(Blocks.SPRUCE_SAPLING.getDefaultState(), BlockPos.ORIGIN))));

	public static final PlacedFeature TREE_DEAD_SPRUCE = new PlacedFeature(() -> BetterTreesConfiguredFeatures.TREE_DEAD_SPRUCE, ImmutableList.of(BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(Blocks.SPRUCE_SAPLING.getDefaultState(), BlockPos.ORIGIN))));

	public static final PlacedFeature TREE_BETTER_PINE = new PlacedFeature(() -> BetterTreesConfiguredFeatures.TREE_BETTER_PINE, ImmutableList.of(BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(Blocks.SPRUCE_SAPLING.getDefaultState(), BlockPos.ORIGIN))));

	public static final PlacedFeature TREE_DEAD_PINE = new PlacedFeature(() -> BetterTreesConfiguredFeatures.TREE_DEAD_PINE, ImmutableList.of(BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(Blocks.SPRUCE_SAPLING.getDefaultState(), BlockPos.ORIGIN))));


	public static final PlacedFeature TREE_BETTER_SNOWY_SPRUCE = new PlacedFeature(
		() -> BetterTreesConfiguredFeatures.TREE_BETTER_SPRUCE,
		ImmutableList.of(
			BlockFilterPlacementModifier.of(
				BlockPredicate.wouldSurvive(
					Blocks.SPRUCE_SAPLING.getDefaultState(),
					BlockPos.ORIGIN)
			),
			EnvironmentScanPlacementModifier.of(
				Direction.UP, BlockPredicate.not(
					BlockPredicate.matchingBlock(Blocks.POWDER_SNOW, BlockPos.ORIGIN)), 8),
			BlockFilterPlacementModifier.of(ON_SNOW_PREDICATE))
	);

	public static final PlacedFeature TREE_BETTER_SNOWY_PINE = new PlacedFeature(
		() -> BetterTreesConfiguredFeatures.TREE_BETTER_PINE,
		ImmutableList.of(
			BlockFilterPlacementModifier.of(
				BlockPredicate.wouldSurvive(
					Blocks.SPRUCE_SAPLING.getDefaultState(),
					BlockPos.ORIGIN)
			),
			EnvironmentScanPlacementModifier.of(
				Direction.UP, BlockPredicate.not(
					BlockPredicate.matchingBlock(Blocks.POWDER_SNOW, BlockPos.ORIGIN)), 8),
			BlockFilterPlacementModifier.of(ON_SNOW_PREDICATE))
	);

	public static final PlacedFeature TREE_BETTER_MEGA_SPRUCE = new PlacedFeature(() -> BetterTreesConfiguredFeatures.TREE_BETTER_MEGA_SPRUCE, ImmutableList.of(BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(Blocks.SPRUCE_SAPLING.getDefaultState(), BlockPos.ORIGIN))));

	public static final PlacedFeature TREE_BETTER_MEGA_PINE = new PlacedFeature(() -> BetterTreesConfiguredFeatures.TREE_BETTER_MEGA_PINE, ImmutableList.of(BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(Blocks.SPRUCE_SAPLING.getDefaultState(), BlockPos.ORIGIN))));

	public static final PlacedFeature TREE_DEAD_BIRCH = new PlacedFeature(() -> BetterTreesConfiguredFeatures.TREE_DEAD_BIRCH, ImmutableList.of(BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(Blocks.BIRCH_SAPLING.getDefaultState(), BlockPos.ORIGIN))));

	public static final PlacedFeature TREE_TALL_DEAD_BIRCH = new PlacedFeature(() -> BetterTreesConfiguredFeatures.TREE_TALL_DEAD_BIRCH, ImmutableList.of(BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(Blocks.BIRCH_SAPLING.getDefaultState(), BlockPos.ORIGIN))));

	public static final PlacedFeature TREE_BETTER_BIRCH_RARE_BEES = new PlacedFeature(() -> BetterTreesConfiguredFeatures.TREE_BETTER_BIRCH_RARE_BEES, ImmutableList.of(BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(Blocks.BIRCH_SAPLING.getDefaultState(), BlockPos.ORIGIN))));

	public static final PlacedFeature TREE_TALL_BETTER_BIRCH_RARE_BEES = new PlacedFeature(() -> BetterTreesConfiguredFeatures.TREE_TALL_BETTER_BIRCH_RARE_BEES, ImmutableList.of(BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(Blocks.BIRCH_SAPLING.getDefaultState(), BlockPos.ORIGIN))));

	public static final PlacedFeature TREE_BETTER_BIRCH_REGULAR_BEES = new PlacedFeature(() -> BetterTreesConfiguredFeatures.TREE_BETTER_BIRCH_REGULAR_BEES, ImmutableList.of(BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(Blocks.BIRCH_SAPLING.getDefaultState(), BlockPos.ORIGIN))));

	public static final PlacedFeature TREE_BETTER_BIRCH_MORE_BEES = new PlacedFeature(() -> BetterTreesConfiguredFeatures.TREE_BETTER_BIRCH_MORE_BEES, ImmutableList.of(BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(Blocks.BIRCH_SAPLING.getDefaultState(), BlockPos.ORIGIN))));

	public static final PlacedFeature TREE_BETTER_JUNGLE = new PlacedFeature(() -> BetterTreesConfiguredFeatures.TREE_BETTER_JUNGLE, ImmutableList.of(BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(Blocks.JUNGLE_SAPLING.getDefaultState(), BlockPos.ORIGIN))));

	public static final PlacedFeature TREE_SHORT_JUNGLE = new PlacedFeature(() -> BetterTreesConfiguredFeatures.TREE_SHORT_JUNGLE, ImmutableList.of(BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(Blocks.JUNGLE_SAPLING.getDefaultState(), BlockPos.ORIGIN))));

	public static final PlacedFeature TREE_BETTER_JUNGLE_NO_VINE = new PlacedFeature(() -> BetterTreesConfiguredFeatures.TREE_BETTER_JUNGLE_NO_VINE, ImmutableList.of(BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(Blocks.JUNGLE_SAPLING.getDefaultState(), BlockPos.ORIGIN))));

	public static final PlacedFeature TREE_DEAD_JUNGLE = new PlacedFeature(() -> BetterTreesConfiguredFeatures.TREE_DEAD_JUNGLE, ImmutableList.of(BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(Blocks.JUNGLE_SAPLING.getDefaultState(), BlockPos.ORIGIN))));

	public static final PlacedFeature TREE_BETTER_MEGA_JUNGLE = new PlacedFeature(() -> BetterTreesConfiguredFeatures.TREE_BETTER_MEGA_JUNGLE, ImmutableList.of(BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(Blocks.JUNGLE_SAPLING.getDefaultState(), BlockPos.ORIGIN))));

	public static final PlacedFeature TREE_BETTER_MEGA_JUNGLE_NO_VINE = new PlacedFeature(() -> BetterTreesConfiguredFeatures.TREE_BETTER_MEGA_JUNGLE_NO_VINE, ImmutableList.of(BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(Blocks.JUNGLE_SAPLING.getDefaultState(), BlockPos.ORIGIN))));

	public static final PlacedFeature TREE_BETTER_ACACIA = new PlacedFeature(() -> BetterTreesConfiguredFeatures.TREE_BETTER_ACACIA, ImmutableList.of(BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(Blocks.ACACIA_SAPLING.getDefaultState(), BlockPos.ORIGIN))));

	public static final PlacedFeature TREE_DEAD_ACACIA = new PlacedFeature(() -> BetterTreesConfiguredFeatures.TREE_DEAD_ACACIA, ImmutableList.of(BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(Blocks.ACACIA_SAPLING.getDefaultState(), BlockPos.ORIGIN))));

	public static final PlacedFeature TREE_BETTER_DARK_OAK = new PlacedFeature(() -> BetterTreesConfiguredFeatures.TREE_BETTER_DARK_OAK, ImmutableList.of(BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(Blocks.DARK_OAK_SAPLING.getDefaultState(), BlockPos.ORIGIN))));


	public static final PlacedFeature BETTER_FOREST_TREES = BetterTreesConfiguredFeatures.BETTER_FOREST_TREES.withPlacement(VegetationPlacedFeatures.modifiers(PlacedFeatures.createCountExtraModifier(8, 0.1f, 1)));

	public static final PlacedFeature BETTER_FLOWER_FOREST_TREES = BetterTreesConfiguredFeatures.BETTER_FOREST_TREES.withPlacement(VegetationPlacedFeatures.modifiers(PlacedFeatures.createCountExtraModifier(3, 0.1f, 1)));

	public static final PlacedFeature BETTER_BIRCH_FOREST_TREES = BetterTreesConfiguredFeatures.BETTER_BIRCH_FOREST_TREES.withPlacement(VegetationPlacedFeatures.modifiers(PlacedFeatures.createCountExtraModifier(8, 0.1f, 1)));

	public static final PlacedFeature BETTER_TALL_BIRCH_FOREST_TREES = BetterTreesConfiguredFeatures.BETTER_TALL_BIRCH_FOREST_TREES.withPlacement(VegetationPlacedFeatures.modifiers(PlacedFeatures.createCountExtraModifier(8, 0.1f, 1)));

	public static final PlacedFeature BETTER_PLAINS_TREES = BetterTreesConfiguredFeatures.TREE_BETTER_OAK_REGULAR_BEES.withPlacement(PlacedFeatures.createCountExtraModifier(0, 0.05f, 1), SquarePlacementModifier.of(), SurfaceWaterDepthFilterPlacementModifier.of(0), PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP, BiomePlacementModifier.of());

	public static final PlacedFeature BETTER_WATER_BIOME_TREES = BetterTreesConfiguredFeatures.TREE_BETTER_OAK.withPlacement(VegetationPlacedFeatures.modifiers(PlacedFeatures.createCountExtraModifier(0, 0.1f, 1)));

	public static final PlacedFeature BETTER_DARK_FOREST_VEGETATION = BetterTreesConfiguredFeatures.BETTER_DARK_FOREST_VEGETATION_BROWN.withPlacement(CountPlacementModifier.of(16), SquarePlacementModifier.of(), SurfaceWaterDepthFilterPlacementModifier.of(0), PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP, BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.getDefaultState(), BlockPos.ORIGIN)), BiomePlacementModifier.of());

	public static final PlacedFeature BETTER_BAMBOO_JUNGLE_VEGETATION = BetterTreesConfiguredFeatures.BETTER_BAMBOO_JUNGLE_VEGETATION.withPlacement(VegetationPlacedFeatures.modifiers(PlacedFeatures.createCountExtraModifier(30, 0.1f, 1)));

	public static final PlacedFeature BETTER_JUNGLE_TREES = BetterTreesConfiguredFeatures.BETTER_JUNGLE_TREES.withPlacement(VegetationPlacedFeatures.modifiers(PlacedFeatures.createCountExtraModifier(100, 0.1f, 1)));

	public static final PlacedFeature BETTER_SPARSE_JUNGLE_TREES = BetterTreesConfiguredFeatures.BETTER_SPARSE_JUNGLE_TREES.withPlacement(VegetationPlacedFeatures.modifiers(PlacedFeatures.createCountExtraModifier(2, 0.1f, 1)));

	public static final PlacedFeature BETTER_SAVANNAH_TREES = BetterTreesConfiguredFeatures.BETTER_SAVANNAH_TREES.withPlacement(VegetationPlacedFeatures.modifiers(PlacedFeatures.createCountExtraModifier(1, 0.1f, 1)));

	public static final PlacedFeature BETTER_WINDSWEPT_SAVANNAH_TREES = BetterTreesConfiguredFeatures.BETTER_SAVANNAH_TREES.withPlacement(VegetationPlacedFeatures.modifiers(PlacedFeatures.createCountExtraModifier(2, 0.1f, 1)));

	public static final PlacedFeature BETTER_WINDSWEPT_HILLS_TREES = BetterTreesConfiguredFeatures.BETTER_MOUNTAIN_TREES.withPlacement(VegetationPlacedFeatures.modifiers(PlacedFeatures.createCountExtraModifier(0, 0.1f, 1)));

	public static final PlacedFeature BETTER_WINDSWEPT_FOREST_TREES = BetterTreesConfiguredFeatures.BETTER_MOUNTAIN_TREES.withPlacement(VegetationPlacedFeatures.modifiers(PlacedFeatures.createCountExtraModifier(2, 0.1f, 1)));

	public static final PlacedFeature BETTER_SWAMP_TREES = BetterTreesConfiguredFeatures.TREE_BETTER_SWAMP_OAK.withPlacement(PlacedFeatures.createCountExtraModifier(2, 0.1f, 1), SquarePlacementModifier.of(), SurfaceWaterDepthFilterPlacementModifier.of(2), PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP, BiomePlacementModifier.of(), BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.getDefaultState(), BlockPos.ORIGIN)));

	public static final PlacedFeature BETTER_BADLANDS_TREES = BetterTreesConfiguredFeatures.TREE_BETTER_OAK.withPlacement(VegetationPlacedFeatures.modifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(5, 0.1f, 1), Blocks.OAK_SAPLING));


	public static final PlacedFeature BETTER_SNOWY_SPRUCE_TREES = BetterTreesConfiguredFeatures.TREE_BETTER_SPRUCE.withPlacement(VegetationPlacedFeatures.modifiers(PlacedFeatures.createCountExtraModifier(0, 0.1f, 1)));

	public static final PlacedFeature BETTER_TAIGA_TREES = BetterTreesConfiguredFeatures.BETTER_TAIGA_TREES.withPlacement(VegetationPlacedFeatures.modifiers(PlacedFeatures.createCountExtraModifier(10, 0.1f, 1)));

	public static final PlacedFeature BETTER_SNOWY_TAIGA_TREES = BetterTreesConfiguredFeatures.BETTER_TAIGA_TREES.withPlacement(VegetationPlacedFeatures.modifiers(PlacedFeatures.createCountExtraModifier(10, 0.1f, 1)));

	public static final PlacedFeature BETTER_SNOWY_PLAINS_TREES = BetterTreesConfiguredFeatures.BETTER_TAIGA_TREES.withPlacement(VegetationPlacedFeatures.modifiers(PlacedFeatures.createCountExtraModifier(1, 0.1f, 1)));

	public static final PlacedFeature BETTER_GROVE_TREES = BetterTreesConfiguredFeatures.BETTER_GROVE_TREES.withPlacement(VegetationPlacedFeatures.modifiers(PlacedFeatures.createCountExtraModifier(100, 0.1f, 1)));

	public static final PlacedFeature BETTER_OLD_GROWTH_SPRUCE_TAIGA_TREES = BetterTreesConfiguredFeatures.BETTER_OLD_GROWTH_SPRUCE_TAIGA_TREES.withPlacement(VegetationPlacedFeatures.modifiers(PlacedFeatures.createCountExtraModifier(10, 0.1f, 1)));

	public static final PlacedFeature BETTER_OLD_GROWTH_PINE_TAIGA_TREES = BetterTreesConfiguredFeatures.BETTER_OLD_GROWTH_PINE_TAIGA_TREES.withPlacement(VegetationPlacedFeatures.modifiers(PlacedFeatures.createCountExtraModifier(10, 0.1f, 1)));

	public static Identifier identify(String path) {
		return new Identifier(MOD_ID, path);
	}

	public static void registerPlacedFeature(String path, PlacedFeature entry) {
		RegistryKey<PlacedFeature> feature = RegistryKey.of(Registry.PLACED_FEATURE_KEY, identify(path));
		Registry.register(BuiltinRegistries.PLACED_FEATURE, feature.getValue(), entry);
	}

	public static void registerAll() {
		registerPlacedFeature("dead_oak_log", DEAD_OAK_LOG);
		registerPlacedFeature("dead_birch_log", DEAD_BIRCH_LOG);
		registerPlacedFeature("dead_acacia_log", DEAD_ACACIA_LOG);

		registerPlacedFeature("oak_stump", OAK_STUMP);
		registerPlacedFeature("birch_stump", BIRCH_STUMP);
		registerPlacedFeature("undergrowth_bush", UNDERGROWTH_BUSH);

		registerPlacedFeature("better_oak", TREE_BETTER_OAK);
		registerPlacedFeature("dead_oak", TREE_DEAD_OAK);
		registerPlacedFeature("better_oak_rare_bees", TREE_BETTER_OAK_RARE_BEES);
		registerPlacedFeature("better_oak_regular_bees", TREE_BETTER_OAK_REGULAR_BEES);
		registerPlacedFeature("better_oak_more_bees", TREE_BETTER_OAK_MORE_BEES);
		registerPlacedFeature("better_swamp_oak", TREE_BETTER_SWAMP_OAK);

		registerPlacedFeature("better_small_spruce", TREE_BETTER_SMALL_SPRUCE);
		registerPlacedFeature("better_spruce", TREE_BETTER_SPRUCE);
		registerPlacedFeature("dead_spruce", TREE_DEAD_SPRUCE);
		registerPlacedFeature("better_pine", TREE_BETTER_PINE);
		registerPlacedFeature("dead_pine", TREE_DEAD_PINE);
		registerPlacedFeature("better_snowy_spruce", TREE_BETTER_SNOWY_SPRUCE);
		registerPlacedFeature("better_snowy_pine", TREE_BETTER_SNOWY_PINE);
		registerPlacedFeature("better_mega_spruce", TREE_BETTER_MEGA_SPRUCE);
		registerPlacedFeature("better_mega_pine", TREE_BETTER_MEGA_PINE);

		registerPlacedFeature("better_birch", TREE_BETTER_BIRCH);
		registerPlacedFeature("dead_birch", TREE_DEAD_BIRCH);
		registerPlacedFeature("tall_dead_birch", TREE_TALL_DEAD_BIRCH);
		registerPlacedFeature("better_birch_rare_bees", TREE_BETTER_BIRCH_RARE_BEES);
		registerPlacedFeature("tall_better_birch_rare_bees", TREE_TALL_BETTER_BIRCH_RARE_BEES);
		registerPlacedFeature("better_birch_regular_bees", TREE_BETTER_BIRCH_REGULAR_BEES);
		registerPlacedFeature("better_birch_more_bees", TREE_BETTER_BIRCH_MORE_BEES);

		registerPlacedFeature("better_jungle", TREE_BETTER_JUNGLE);
		registerPlacedFeature("short_jungle", TREE_SHORT_JUNGLE);
		registerPlacedFeature("better_jungle_no_vine", TREE_BETTER_JUNGLE_NO_VINE);
		registerPlacedFeature("dead_jungle", TREE_DEAD_JUNGLE);
		registerPlacedFeature("better_mega_jungle", TREE_BETTER_MEGA_JUNGLE);
		registerPlacedFeature("better_mega_jungle_no_vine", TREE_BETTER_MEGA_JUNGLE_NO_VINE);

		registerPlacedFeature("better_acacia", TREE_BETTER_ACACIA);
		registerPlacedFeature("dead_acacia", TREE_DEAD_ACACIA);

		registerPlacedFeature("better_dark_oak", TREE_BETTER_DARK_OAK);


		registerPlacedFeature("better_forest_trees", BETTER_FOREST_TREES);
		registerPlacedFeature("better_flower_forest_trees", BETTER_FLOWER_FOREST_TREES);
		registerPlacedFeature("better_birch_forest_trees", BETTER_BIRCH_FOREST_TREES);
		registerPlacedFeature("better_tall_birch_forest_trees", BETTER_TALL_BIRCH_FOREST_TREES);
		registerPlacedFeature("better_plains_trees", BETTER_PLAINS_TREES);
		registerPlacedFeature("better_water_biome_trees", BETTER_WATER_BIOME_TREES);
		registerPlacedFeature("better_dark_forest_vegetation", BETTER_DARK_FOREST_VEGETATION);
		registerPlacedFeature("better_bamboo_jungle_vegetation", BETTER_BAMBOO_JUNGLE_VEGETATION);
		registerPlacedFeature("better_jungle_trees", BETTER_JUNGLE_TREES);
		registerPlacedFeature("better_sparse_jungle_trees", BETTER_SPARSE_JUNGLE_TREES);
		registerPlacedFeature("better_savannah_trees", BETTER_SAVANNAH_TREES);
		registerPlacedFeature("better_windswept_savannah_trees", BETTER_WINDSWEPT_SAVANNAH_TREES);
		registerPlacedFeature("better_windswept_hills_trees", BETTER_WINDSWEPT_HILLS_TREES);
		registerPlacedFeature("better_windswept_forest_trees", BETTER_WINDSWEPT_FOREST_TREES);
		registerPlacedFeature("better_swamp_trees", BETTER_SWAMP_TREES);
		registerPlacedFeature("better_badlands_trees", BETTER_BADLANDS_TREES);

		registerPlacedFeature("better_snowy_spruce_trees", BETTER_SNOWY_SPRUCE_TREES);
		registerPlacedFeature("better_taiga_trees", BETTER_TAIGA_TREES);
		registerPlacedFeature("better_snowy_taiga_trees", BETTER_SNOWY_TAIGA_TREES);
		registerPlacedFeature("better_snowy_plains_trees", BETTER_SNOWY_PLAINS_TREES);
		registerPlacedFeature("better_grove_trees", BETTER_GROVE_TREES);
		registerPlacedFeature("better_old_growth_spruce_taiga_trees", BETTER_OLD_GROWTH_SPRUCE_TAIGA_TREES);
		registerPlacedFeature("better_old_growth_pine_taiga_trees", BETTER_OLD_GROWTH_PINE_TAIGA_TREES);
	}
}
