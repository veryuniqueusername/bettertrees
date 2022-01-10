package io.github.veryuniqueusername.bettertrees.tree;

import com.google.common.collect.ImmutableList;
import io.github.veryuniqueusername.bettertrees.mixin.SimpleBlockStateProviderInvoker;
import io.github.veryuniqueusername.bettertrees.mixin.TrunkPlacerTypeInvoker;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.BiasedToBottomIntProvider;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.foliage.BushFoliagePlacer;
import net.minecraft.world.gen.foliage.LargeOakFoliagePlacer;
import net.minecraft.world.gen.foliage.RandomSpreadFoliagePlacer;
import net.minecraft.world.gen.treedecorator.BeehiveTreeDecorator;
import net.minecraft.world.gen.treedecorator.LeavesVineTreeDecorator;
import net.minecraft.world.gen.treedecorator.TrunkVineTreeDecorator;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

import java.util.List;

import static io.github.veryuniqueusername.bettertrees.BetterTrees.MOD_ID;


public class BetterTreesConfiguredFeatures {
	public static final TrunkPlacerType<BetterOakTrunkPlacer> BETTER_OAK_TRUNK_PLACER = TrunkPlacerTypeInvoker.callRegister("better_oak_trunk_placer", BetterOakTrunkPlacer.CODEC);
	public static final TrunkPlacerType<DeadLogTrunkPlacer> DEAD_OAK_TRUNK_PLACER = TrunkPlacerTypeInvoker.callRegister("dead_oak_trunk_placer", DeadLogTrunkPlacer.CODEC);

	public static final TrunkPlacerType<BetterTallBirchTrunkPlacer> BETTER_TALL_BIRCH_TRUNK_PLACER = TrunkPlacerTypeInvoker.callRegister("better_tall_birch_trunk_placer", BetterTallBirchTrunkPlacer.CODEC);
	public static final TrunkPlacerType<BetterShortBirchTrunkPlacer> BETTER_SHORT_BIRCH_TRUNK_PLACER = TrunkPlacerTypeInvoker.callRegister("better_short_birch_trunk_placer", BetterShortBirchTrunkPlacer.CODEC);

	public static final TrunkPlacerType<BetterJungleTrunkPlacer> BETTER_JUNGLE_TRUNK_PLACER = TrunkPlacerTypeInvoker.callRegister("better_jungle_trunk_placer", BetterJungleTrunkPlacer.CODEC);

	private static final BeehiveTreeDecorator BEES_RARE = new BeehiveTreeDecorator(0.002f);
	private static final BeehiveTreeDecorator BEES_REGULAR = new BeehiveTreeDecorator(0.02f);
	private static final BeehiveTreeDecorator BEES_COMMON = new BeehiveTreeDecorator(0.05f);
	// all our features
	public static final ConfiguredFeature<TreeFeatureConfig, ?> DEAD_OAK_LOG = Feature.TREE.configure(deadLogBuilder(Blocks.OAK_WOOD).decorators(ImmutableList.of(TrunkVineTreeDecorator.INSTANCE)).build());
	public static final ConfiguredFeature<TreeFeatureConfig, ?> DEAD_BIRCH_LOG = Feature.TREE.configure(deadLogBuilder(Blocks.BIRCH_WOOD).decorators(ImmutableList.of(TrunkVineTreeDecorator.INSTANCE)).build());

	public static final ConfiguredFeature<TreeFeatureConfig, ?> OAK_STUMP = Feature.TREE.configure(stumpBuilder(Blocks.OAK_WOOD).build());
	public static final ConfiguredFeature<TreeFeatureConfig, ?> BIRCH_STUMP = Feature.TREE.configure(stumpBuilder(Blocks.BIRCH_WOOD).build());

	public static final ConfiguredFeature<TreeFeatureConfig, ?> UNDERGROWTH_BUSH = Feature.TREE.configure(bushBuilder().build());

	public static final ConfiguredFeature<TreeFeatureConfig, ?> TREE_BETTER_OAK = Feature.TREE.configure(oakBuilder(false).build());

	public static final ConfiguredFeature<TreeFeatureConfig, ?> TREE_DEAD_OAK = Feature.TREE.configure(oakBuilder(true).decorators(ImmutableList.of(TrunkVineTreeDecorator.INSTANCE)).build());

	public static final ConfiguredFeature<TreeFeatureConfig, ?> TREE_BETTER_SWAMP_OAK = Feature.TREE.configure(oakBuilder(false).decorators(ImmutableList.of(LeavesVineTreeDecorator.INSTANCE)).build());

	public static final ConfiguredFeature<TreeFeatureConfig, ?> TREE_BETTER_OAK_RARE_BEES = Feature.TREE.configure(oakBuilder(false).decorators(ImmutableList.of(BEES_RARE)).build());
	public static final ConfiguredFeature<TreeFeatureConfig, ?> TREE_BETTER_OAK_REGULAR_BEES = Feature.TREE.configure(oakBuilder(false).decorators(ImmutableList.of(BEES_REGULAR)).build());
	public static final ConfiguredFeature<TreeFeatureConfig, ?> TREE_BETTER_OAK_MORE_BEES = Feature.TREE.configure(oakBuilder(false).decorators(ImmutableList.of(BEES_COMMON)).build());

	public static final ConfiguredFeature<TreeFeatureConfig, ?> TREE_BETTER_BIRCH = Feature.TREE.configure(birchBuilder(false, false).build());

	public static final ConfiguredFeature<TreeFeatureConfig, ?> TREE_DEAD_BIRCH = Feature.TREE.configure(birchBuilder(false, true).decorators(ImmutableList.of(TrunkVineTreeDecorator.INSTANCE)).build());
	public static final ConfiguredFeature<TreeFeatureConfig, ?> TREE_TALL_DEAD_BIRCH = Feature.TREE.configure(birchBuilder(true, true).decorators(ImmutableList.of(TrunkVineTreeDecorator.INSTANCE)).build());

	public static final ConfiguredFeature<TreeFeatureConfig, ?> TREE_BETTER_BIRCH_RARE_BEES = Feature.TREE.configure(birchBuilder(false, false).decorators(ImmutableList.of(BEES_RARE)).build());
	public static final ConfiguredFeature<TreeFeatureConfig, ?> TREE_TALL_BETTER_BIRCH_RARE_BEES = Feature.TREE.configure(birchBuilder(true, false).decorators(ImmutableList.of(BEES_RARE)).build());
	public static final ConfiguredFeature<TreeFeatureConfig, ?> TREE_BETTER_BIRCH_REGULAR_BEES = Feature.TREE.configure(birchBuilder(false, false).decorators(ImmutableList.of(BEES_REGULAR)).build());
	public static final ConfiguredFeature<TreeFeatureConfig, ?> TREE_BETTER_BIRCH_MORE_BEES = Feature.TREE.configure(birchBuilder(false, false).decorators(ImmutableList.of(BEES_COMMON)).build());

	public static final ConfiguredFeature<TreeFeatureConfig,?> TREE_BETTER_JUNGLE_NO_VINE = Feature.TREE.configure(jungleBuilder(false).build());

	public static final ConfiguredFeature<RandomFeatureConfig, ?> BETTER_FOREST_TREES = Feature.RANDOM_SELECTOR.configure(
		new RandomFeatureConfig(
			List.of(
				new RandomFeatureEntry(BetterTreesPlacedFeatures.TREE_BETTER_BIRCH_RARE_BEES, 0.1f),
				new RandomFeatureEntry(BetterTreesPlacedFeatures.DEAD_OAK_LOG, 0.25f),
				new RandomFeatureEntry(BetterTreesPlacedFeatures.DEAD_BIRCH_LOG, 0.2f),
				new RandomFeatureEntry(BetterTreesPlacedFeatures.TREE_DEAD_OAK, 0.01f),
				new RandomFeatureEntry(BetterTreesPlacedFeatures.TREE_DEAD_BIRCH, 0.005f),
				new RandomFeatureEntry(BetterTreesPlacedFeatures.UNDERGROWTH_BUSH, 0.5f)
			),
			BetterTreesPlacedFeatures.TREE_BETTER_OAK_RARE_BEES
		)
	);

	public static final ConfiguredFeature<RandomFeatureConfig, ?> BETTER_BIRCH_FOREST_TREES = Feature.RANDOM_SELECTOR.configure(
		new RandomFeatureConfig(
			List.of(
				new RandomFeatureEntry(BetterTreesPlacedFeatures.DEAD_BIRCH_LOG, 0.22f),
				new RandomFeatureEntry(BetterTreesPlacedFeatures.TREE_DEAD_BIRCH, 0.01f),
				new RandomFeatureEntry(BetterTreesPlacedFeatures.UNDERGROWTH_BUSH, 0.5f)
			),
			BetterTreesPlacedFeatures.TREE_BETTER_BIRCH_RARE_BEES
		)
	);

	public static final ConfiguredFeature<RandomFeatureConfig, ?> BETTER_TALL_BIRCH_FOREST_TREES = Feature.RANDOM_SELECTOR.configure(
		new RandomFeatureConfig(
			List.of(
				new RandomFeatureEntry(BetterTreesPlacedFeatures.DEAD_BIRCH_LOG, 0.22f),
				new RandomFeatureEntry(BetterTreesPlacedFeatures.TREE_TALL_DEAD_BIRCH, 0.01f),
				new RandomFeatureEntry(BetterTreesPlacedFeatures.UNDERGROWTH_BUSH, 0.6f)
			),
			BetterTreesPlacedFeatures.TREE_TALL_BETTER_BIRCH_RARE_BEES
		)
	);

	public static final ConfiguredFeature<RandomFeatureConfig, ?> BETTER_DARK_FOREST_VEGETATION_BROWN = Feature.RANDOM_SELECTOR.configure(
		new RandomFeatureConfig(
			List.of(
				new RandomFeatureEntry(TreeConfiguredFeatures.HUGE_BROWN_MUSHROOM.withPlacement(), 0.025f),
				new RandomFeatureEntry(TreeConfiguredFeatures.HUGE_RED_MUSHROOM.withPlacement(), 0.05f),
				new RandomFeatureEntry(TreePlacedFeatures.DARK_OAK_CHECKED, 2f / 3f),
				new RandomFeatureEntry(BetterTreesPlacedFeatures.TREE_BETTER_BIRCH, 0.2f)),
			BetterTreesPlacedFeatures.TREE_BETTER_OAK
		)
	);

	public static final ConfiguredFeature<RandomFeatureConfig, ?> BETTER_BAMBOO_JUNGLE_VEGETATION = Feature.RANDOM_SELECTOR.configure(
		new RandomFeatureConfig(
			List.of(
				new RandomFeatureEntry(BetterTreesPlacedFeatures.TREE_BETTER_OAK, 0.05f),
				new RandomFeatureEntry(TreePlacedFeatures.JUNGLE_BUSH, 0.15f),
				new RandomFeatureEntry(TreePlacedFeatures.MEGA_JUNGLE_TREE_CHECKED, 0.7f)
			),
			VegetationConfiguredFeatures.PATCH_GRASS_JUNGLE.withPlacement()
		)
	);

	public static final ConfiguredFeature<RandomFeatureConfig, ?> BETTER_JUNGLE_TREES = Feature.RANDOM_SELECTOR.configure(
		new RandomFeatureConfig(
			List.of(
				new RandomFeatureEntry(BetterTreesPlacedFeatures.TREE_BETTER_OAK, 0.1f),
				new RandomFeatureEntry(TreePlacedFeatures.JUNGLE_BUSH, 0.5f),
				new RandomFeatureEntry(TreePlacedFeatures.MEGA_JUNGLE_TREE_CHECKED, 1f / 3f)
			),
			TreePlacedFeatures.JUNGLE_TREE
		)
	);

	public static final ConfiguredFeature<RandomFeatureConfig, ?> BETTER_SPARSE_JUNGLE_TREES = Feature.RANDOM_SELECTOR.configure(
		new RandomFeatureConfig(
			List.of(
				new RandomFeatureEntry(BetterTreesPlacedFeatures.TREE_BETTER_OAK, 0.1f),
				new RandomFeatureEntry(TreePlacedFeatures.JUNGLE_BUSH, 0.5f)
			),
			TreePlacedFeatures.JUNGLE_TREE
		)
	);

	public static final ConfiguredFeature<RandomFeatureConfig, ?> BETTER_SAVANNAH_TREES = Feature.RANDOM_SELECTOR.configure(
		new RandomFeatureConfig(
			List.of(
				new RandomFeatureEntry(TreePlacedFeatures.ACACIA_CHECKED, 0.8f)
			),
			BetterTreesPlacedFeatures.TREE_BETTER_OAK
		)
	);

	public static final ConfiguredFeature<RandomFeatureConfig, ?> BETTER_MOUNTAIN_TREES = Feature.RANDOM_SELECTOR.configure(
		new RandomFeatureConfig(
			List.of(
				new RandomFeatureEntry(TreePlacedFeatures.SPRUCE_CHECKED, 2f / 3f)
			),
			BetterTreesPlacedFeatures.TREE_BETTER_OAK
		)
	);

	public static Identifier identify(String path) {
		return new Identifier(MOD_ID, path);
	}

	public static void registerConfiguredFeature(String path, ConfiguredFeature<?, ?> entry) {
		RegistryKey<ConfiguredFeature<?, ?>> feature = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, identify(path));
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, feature.getValue(), entry);
	}


	// Register all trees
	public static void registerAll() {
		registerConfiguredFeature("dead_oak_log", DEAD_OAK_LOG);
		registerConfiguredFeature("dead_birch_log", DEAD_BIRCH_LOG);
		registerConfiguredFeature("oak_stump", OAK_STUMP);
		registerConfiguredFeature("birch_stump", BIRCH_STUMP);
		registerConfiguredFeature("undergrowth_bush", UNDERGROWTH_BUSH);
		registerConfiguredFeature("better_oak", TREE_BETTER_OAK);
		registerConfiguredFeature("dead_oak", TREE_DEAD_OAK);
		registerConfiguredFeature("better_swamp_oak", TREE_BETTER_SWAMP_OAK);
		registerConfiguredFeature("better_oak_rare_bees", TREE_BETTER_OAK_RARE_BEES);
		registerConfiguredFeature("better_oak_regular_bees", TREE_BETTER_OAK_REGULAR_BEES);
		registerConfiguredFeature("better_oak_more_bees", TREE_BETTER_OAK_MORE_BEES);
		registerConfiguredFeature("better_birch", TREE_BETTER_BIRCH);
		registerConfiguredFeature("dead_birch", TREE_TALL_DEAD_BIRCH);
		registerConfiguredFeature("tall_dead_birch", TREE_DEAD_BIRCH);
		registerConfiguredFeature("better_birch_rare_bees", TREE_BETTER_BIRCH_RARE_BEES);
		registerConfiguredFeature("better_tall_birch_rare_bees", TREE_TALL_BETTER_BIRCH_RARE_BEES);
		registerConfiguredFeature("better_birch_regular_bees", TREE_BETTER_BIRCH_REGULAR_BEES);
		registerConfiguredFeature("better_birch_more_bees", TREE_BETTER_BIRCH_MORE_BEES);
		registerConfiguredFeature("better_forest_trees", BETTER_FOREST_TREES);
		registerConfiguredFeature("better_birch_forest_trees", BETTER_BIRCH_FOREST_TREES);
		registerConfiguredFeature("better_tall_birch_forest_trees", BETTER_TALL_BIRCH_FOREST_TREES);
		registerConfiguredFeature("better_dark_forest_vegetation_brown", BETTER_DARK_FOREST_VEGETATION_BROWN);
		registerConfiguredFeature("better_bamboo_jungle_vegetation", BETTER_BAMBOO_JUNGLE_VEGETATION);
		registerConfiguredFeature("better_jungle_trees", BETTER_JUNGLE_TREES);
		registerConfiguredFeature("better_sparse_jungle_trees", BETTER_SPARSE_JUNGLE_TREES);
		registerConfiguredFeature("better_savannah_trees", BETTER_SAVANNAH_TREES);
		registerConfiguredFeature("better_mountain_trees", BETTER_MOUNTAIN_TREES);
	}

	private static TreeFeatureConfig.Builder oakBuilder(boolean dead) {
		return new TreeFeatureConfig.Builder(
			SimpleBlockStateProviderInvoker.invokeCtor(Blocks.OAK_WOOD.getDefaultState()),
			new BetterOakTrunkPlacer(5, 5, 4, 1.50D, 5D, 3, 5, 0D, 0.4D, 0.9D, 1D),
			SimpleBlockStateProviderInvoker.invokeCtor((dead ? Blocks.AIR : Blocks.OAK_LEAVES).getDefaultState()),
			new RandomSpreadFoliagePlacer(BiasedToBottomIntProvider.create(2, 3), ConstantIntProvider.create(0), ConstantIntProvider.create(3), 50),
			new TwoLayersFeatureSize(5, 0, 5)
		);
	}

	private static TreeFeatureConfig.Builder birchBuilder(boolean tall, boolean dead) {
		if (tall) {
			return new TreeFeatureConfig.Builder(
				SimpleBlockStateProviderInvoker.invokeCtor(Blocks.BIRCH_WOOD.getDefaultState()),
				new BetterTallBirchTrunkPlacer(7, 10, 1, 0.5D, 2D, 2, 10, 0D, 0.5D, 1D, 1D),
				SimpleBlockStateProviderInvoker.invokeCtor((dead ? Blocks.AIR : Blocks.BIRCH_LEAVES).getDefaultState()),
				new RandomSpreadFoliagePlacer(BiasedToBottomIntProvider.create(1, 3), ConstantIntProvider.create(0), BiasedToBottomIntProvider.create(2, 5), 50),
				new TwoLayersFeatureSize(10, 0, 3)
			);
		}
		return new TreeFeatureConfig.Builder(
			SimpleBlockStateProviderInvoker.invokeCtor(Blocks.BIRCH_WOOD.getDefaultState()),
			new BetterShortBirchTrunkPlacer(3, 8, 1, 0.7D, 2D, 2, 7, 0D, 0.5D, 1D, 1D),
			SimpleBlockStateProviderInvoker.invokeCtor((dead ? Blocks.AIR : Blocks.BIRCH_LEAVES).getDefaultState()),
			new RandomSpreadFoliagePlacer(BiasedToBottomIntProvider.create(1, 3), ConstantIntProvider.create(0), BiasedToBottomIntProvider.create(2, 4), 30),
			new TwoLayersFeatureSize(6, 0, 2)
		);
	}

	private static TreeFeatureConfig.Builder jungleBuilder(boolean dead) {
		return new TreeFeatureConfig.Builder(
			SimpleBlockStateProviderInvoker.invokeCtor(Blocks.JUNGLE_WOOD.getDefaultState()),
			new BetterJungleTrunkPlacer(8, 20, 15, 0.2D),
			SimpleBlockStateProviderInvoker.invokeCtor((dead ? Blocks.AIR : Blocks.JUNGLE_LEAVES).getDefaultState()),
			new LargeOakFoliagePlacer(BiasedToBottomIntProvider.create(2, 3), ConstantIntProvider.create(0), 1),
			new TwoLayersFeatureSize(10, 0, 3)
		);
	}

	private static TreeFeatureConfig.Builder deadLogBuilder(Block block) {
		return new TreeFeatureConfig.Builder(
			SimpleBlockStateProviderInvoker.invokeCtor(block.getDefaultState()),
			new DeadLogTrunkPlacer(4, 6, 0),
			SimpleBlockStateProviderInvoker.invokeCtor(Blocks.AIR.getDefaultState()),
			new BlobFoliagePlacer(ConstantIntProvider.create(0), ConstantIntProvider.create(0), 0),
			new TwoLayersFeatureSize(1, 10, 10)
		);
	}

	private static TreeFeatureConfig.Builder stumpBuilder(Block block) {
		return new TreeFeatureConfig.Builder(
			SimpleBlockStateProviderInvoker.invokeCtor(block.getDefaultState()),
			new BetterOakTrunkPlacer(1, 2, 0),
			SimpleBlockStateProviderInvoker.invokeCtor(Blocks.AIR.getDefaultState()),
			new BlobFoliagePlacer(ConstantIntProvider.create(0), ConstantIntProvider.create(0), 0),
			new TwoLayersFeatureSize(1, 2, 1)
		);
	}

	private static TreeFeatureConfig.Builder bushBuilder() {
		return new TreeFeatureConfig.Builder(
			SimpleBlockStateProviderInvoker.invokeCtor(Blocks.OAK_WOOD.getDefaultState()),
			new StraightTrunkPlacer(1, 1, 0),
			SimpleBlockStateProviderInvoker.invokeCtor(Blocks.OAK_LEAVES.getDefaultState()),
			new BushFoliagePlacer(BiasedToBottomIntProvider.create(1, 2), ConstantIntProvider.create(1), 3),
			new TwoLayersFeatureSize(1, 2, 2)
		);
	}
}
