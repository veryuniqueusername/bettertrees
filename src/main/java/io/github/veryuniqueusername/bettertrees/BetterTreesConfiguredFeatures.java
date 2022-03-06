package io.github.veryuniqueusername.bettertrees;

import com.google.common.collect.ImmutableList;
import io.github.veryuniqueusername.bettertrees.foliage.CircleFoliagePlacer;
import io.github.veryuniqueusername.bettertrees.foliage.SmallSpruceFoliagePlacer;
import io.github.veryuniqueusername.bettertrees.mixin.FoliagePlacerTypeInvoker;
import io.github.veryuniqueusername.bettertrees.mixin.SimpleBlockStateProviderInvoker;
import io.github.veryuniqueusername.bettertrees.mixin.TrunkPlacerTypeInvoker;
import io.github.veryuniqueusername.bettertrees.tree.*;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.intprovider.BiasedToBottomIntProvider;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.*;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.treedecorator.BeehiveTreeDecorator;
import net.minecraft.world.gen.treedecorator.CocoaBeansTreeDecorator;
import net.minecraft.world.gen.treedecorator.LeavesVineTreeDecorator;
import net.minecraft.world.gen.treedecorator.TrunkVineTreeDecorator;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

import java.util.List;

import static io.github.veryuniqueusername.bettertrees.BetterTrees.MOD_ID;


public class BetterTreesConfiguredFeatures {
	public static final FoliagePlacerType<CircleFoliagePlacer> CIRCLE_FOLIAGE_PLACER = FoliagePlacerTypeInvoker.callRegister("circle_foliage_placer", CircleFoliagePlacer.CODEC);
	public static final FoliagePlacerType<SmallSpruceFoliagePlacer> SMALL_SPRUCE_FOLIAGE_PLACER = FoliagePlacerTypeInvoker.callRegister("small_spruce_foliage_placer", SmallSpruceFoliagePlacer.CODEC);

	public static final TrunkPlacerType<DeadLogTrunkPlacer> DEAD_LOG_TRUNK_PLACER = TrunkPlacerTypeInvoker.callRegister("dead_log_trunk_placer", DeadLogTrunkPlacer.CODEC);

	public static final TrunkPlacerType<BetterOakTrunkPlacer> BETTER_OAK_TRUNK_PLACER = TrunkPlacerTypeInvoker.callRegister("better_oak_trunk_placer", BetterOakTrunkPlacer.CODEC);

	public static final TrunkPlacerType<BetterSmallSpruceTrunkPlacer> BETTER_SMALL_SPRUCE_TRUNK_PLACER = TrunkPlacerTypeInvoker.callRegister("better_small_spruce_trunk_placer", BetterSmallSpruceTrunkPlacer.CODEC);
	public static final TrunkPlacerType<BetterSpruceTrunkPlacer> BETTER_SPRUCE_TRUNK_PLACER = TrunkPlacerTypeInvoker.callRegister("better_spruce_trunk_placer", BetterSpruceTrunkPlacer.CODEC);
	public static final TrunkPlacerType<BetterPineTrunkPlacer> BETTER_PINE_TRUNK_PLACER = TrunkPlacerTypeInvoker.callRegister("better_pine_trunk_placer", BetterPineTrunkPlacer.CODEC);
	public static final TrunkPlacerType<BetterMegaSpruceTrunkPlacer> BETTER_MEGA_SPRUCE_TRUNK_PLACER = TrunkPlacerTypeInvoker.callRegister("better_mega_spruce_trunk_placer", BetterMegaSpruceTrunkPlacer.CODEC);
	public static final TrunkPlacerType<BetterMegaPineTrunkPlacer> BETTER_MEGA_PINE_TRUNK_PLACER = TrunkPlacerTypeInvoker.callRegister("better_mega_pine_trunk_placer", BetterMegaPineTrunkPlacer.CODEC);

	public static final TrunkPlacerType<BetterTallBirchTrunkPlacer> BETTER_TALL_BIRCH_TRUNK_PLACER = TrunkPlacerTypeInvoker.callRegister("better_tall_birch_trunk_placer", BetterTallBirchTrunkPlacer.CODEC);
	public static final TrunkPlacerType<BetterShortBirchTrunkPlacer> BETTER_SHORT_BIRCH_TRUNK_PLACER = TrunkPlacerTypeInvoker.callRegister("better_short_birch_trunk_placer", BetterShortBirchTrunkPlacer.CODEC);

	public static final TrunkPlacerType<BetterJungleTrunkPlacer> BETTER_JUNGLE_TRUNK_PLACER = TrunkPlacerTypeInvoker.callRegister("better_jungle_trunk_placer", BetterJungleTrunkPlacer.CODEC);
	public static final TrunkPlacerType<BetterMegaJungleTrunkPlacer> BETTER_MEGA_JUNGLE_TRUNK_PLACER = TrunkPlacerTypeInvoker.callRegister("better_mega_jungle_trunk_placer", BetterMegaJungleTrunkPlacer.CODEC);

	public static final TrunkPlacerType<BetterAcaciaTrunkPlacer> BETTER_ACACIA_TRUNK_PLACER = TrunkPlacerTypeInvoker.callRegister("better_acacia_trunk_placer", BetterAcaciaTrunkPlacer.CODEC);

	public static final TrunkPlacerType<BetterDarkOakTrunkPlacer> BETTER_DARK_OAK_TRUNK_PLACER = TrunkPlacerTypeInvoker.callRegister("better_dark_oak_trunk_placer", BetterDarkOakTrunkPlacer.CODEC);

	public static final TrunkPlacerType<BetterAzaleaTrunkPlacer> BETTER_AZALEA_TRUNK_PLACER = TrunkPlacerTypeInvoker.callRegister("better_azalea_trunk_placer", BetterAzaleaTrunkPlacer.CODEC);


	private static final BeehiveTreeDecorator BEES_RARE = new BeehiveTreeDecorator(0.002f);
	private static final BeehiveTreeDecorator BEES_REGULAR = new BeehiveTreeDecorator(0.02f);
	private static final BeehiveTreeDecorator BEES_COMMON = new BeehiveTreeDecorator(0.05f);
	// all our features
	public static final ConfiguredFeature<TreeFeatureConfig, ?> DEAD_OAK_LOG = Feature.TREE.configure(deadLogBuilder(Blocks.OAK_LOG).decorators(ImmutableList.of(TrunkVineTreeDecorator.INSTANCE)).build());
	public static final ConfiguredFeature<TreeFeatureConfig, ?> DEAD_SPRUCE_LOG = Feature.TREE.configure(deadLogBuilder(Blocks.SPRUCE_LOG).decorators(ImmutableList.of(TrunkVineTreeDecorator.INSTANCE)).build());
	public static final ConfiguredFeature<TreeFeatureConfig, ?> DEAD_BIRCH_LOG = Feature.TREE.configure(deadLogBuilder(Blocks.BIRCH_LOG).decorators(ImmutableList.of(TrunkVineTreeDecorator.INSTANCE)).build());
	public static final ConfiguredFeature<TreeFeatureConfig, ?> DEAD_JUNGLE_LOG = Feature.TREE.configure(deadLogBuilder(Blocks.JUNGLE_LOG).decorators(ImmutableList.of(TrunkVineTreeDecorator.INSTANCE)).build());
	public static final ConfiguredFeature<TreeFeatureConfig, ?> DEAD_ACACIA_LOG = Feature.TREE.configure(deadLogBuilder(Blocks.ACACIA_LOG).build());

	public static final ConfiguredFeature<TreeFeatureConfig, ?> OAK_STUMP = Feature.TREE.configure(stumpBuilder(Blocks.OAK_LOG).build());
	public static final ConfiguredFeature<TreeFeatureConfig, ?> BIRCH_STUMP = Feature.TREE.configure(stumpBuilder(Blocks.BIRCH_LOG).build());
	public static final ConfiguredFeature<TreeFeatureConfig, ?> UNDERGROWTH_BUSH = Feature.TREE.configure(bushBuilder().build());

	public static final ConfiguredFeature<TreeFeatureConfig, ?> TREE_BETTER_OAK = Feature.TREE.configure(oakBuilder(false).build());
	public static final ConfiguredFeature<TreeFeatureConfig, ?> TREE_DEAD_OAK = Feature.TREE.configure(oakBuilder(true).decorators(ImmutableList.of(TrunkVineTreeDecorator.INSTANCE)).build());
	public static final ConfiguredFeature<TreeFeatureConfig, ?> TREE_BETTER_SWAMP_OAK = Feature.TREE.configure(oakBuilder(false).decorators(ImmutableList.of(LeavesVineTreeDecorator.INSTANCE)).build());
	public static final ConfiguredFeature<TreeFeatureConfig, ?> TREE_BETTER_OAK_RARE_BEES = Feature.TREE.configure(oakBuilder(false).decorators(ImmutableList.of(BEES_RARE)).build());
	public static final ConfiguredFeature<TreeFeatureConfig, ?> TREE_BETTER_OAK_REGULAR_BEES = Feature.TREE.configure(oakBuilder(false).decorators(ImmutableList.of(BEES_REGULAR)).build());
	public static final ConfiguredFeature<TreeFeatureConfig, ?> TREE_BETTER_OAK_MORE_BEES = Feature.TREE.configure(oakBuilder(false).decorators(ImmutableList.of(BEES_COMMON)).build());

	public static final ConfiguredFeature<TreeFeatureConfig, ?> TREE_BETTER_SMALL_SPRUCE = Feature.TREE.configure(smallSpruceBuilder().build());
	public static final ConfiguredFeature<TreeFeatureConfig, ?> TREE_BETTER_SPRUCE = Feature.TREE.configure(spruceBuilder(false, false).build());
	public static final ConfiguredFeature<TreeFeatureConfig, ?> TREE_DEAD_SPRUCE = Feature.TREE.configure(spruceBuilder(false, true).build());
	public static final ConfiguredFeature<TreeFeatureConfig, ?> TREE_BETTER_PINE = Feature.TREE.configure(pineBuilder(false, false).build());
	public static final ConfiguredFeature<TreeFeatureConfig, ?> TREE_DEAD_PINE = Feature.TREE.configure(pineBuilder(false, true).build());
	public static final ConfiguredFeature<TreeFeatureConfig, ?> TREE_BETTER_MEGA_SPRUCE = Feature.TREE.configure(spruceBuilder(true, false).build());
	public static final ConfiguredFeature<TreeFeatureConfig, ?> TREE_BETTER_MEGA_PINE = Feature.TREE.configure(pineBuilder(true, false).build());

	public static final ConfiguredFeature<TreeFeatureConfig, ?> TREE_BETTER_BIRCH = Feature.TREE.configure(birchBuilder(false, false).build());
	public static final ConfiguredFeature<TreeFeatureConfig, ?> TREE_DEAD_BIRCH = Feature.TREE.configure(birchBuilder(false, true).decorators(ImmutableList.of(TrunkVineTreeDecorator.INSTANCE)).build());
	public static final ConfiguredFeature<TreeFeatureConfig, ?> TREE_TALL_DEAD_BIRCH = Feature.TREE.configure(birchBuilder(true, true).decorators(ImmutableList.of(TrunkVineTreeDecorator.INSTANCE)).build());
	public static final ConfiguredFeature<TreeFeatureConfig, ?> TREE_BETTER_BIRCH_RARE_BEES = Feature.TREE.configure(birchBuilder(false, false).decorators(ImmutableList.of(BEES_RARE)).build());
	public static final ConfiguredFeature<TreeFeatureConfig, ?> TREE_TALL_BETTER_BIRCH_RARE_BEES = Feature.TREE.configure(birchBuilder(true, false).decorators(ImmutableList.of(BEES_RARE)).build());
	public static final ConfiguredFeature<TreeFeatureConfig, ?> TREE_BETTER_BIRCH_REGULAR_BEES = Feature.TREE.configure(birchBuilder(false, false).decorators(ImmutableList.of(BEES_REGULAR)).build());
	public static final ConfiguredFeature<TreeFeatureConfig, ?> TREE_BETTER_BIRCH_MORE_BEES = Feature.TREE.configure(birchBuilder(false, false).decorators(ImmutableList.of(BEES_COMMON)).build());

	public static final ConfiguredFeature<TreeFeatureConfig, ?> TREE_BETTER_JUNGLE = Feature.TREE.configure(jungleBuilder(true, false).decorators(ImmutableList.of(new CocoaBeansTreeDecorator(0.2f), TrunkVineTreeDecorator.INSTANCE, LeavesVineTreeDecorator.INSTANCE)).build());
	public static final ConfiguredFeature<TreeFeatureConfig, ?> TREE_SHORT_JUNGLE = Feature.TREE.configure(jungleBuilder(false, false).decorators(ImmutableList.of(new CocoaBeansTreeDecorator(0.2f), TrunkVineTreeDecorator.INSTANCE, LeavesVineTreeDecorator.INSTANCE)).build());
	public static final ConfiguredFeature<TreeFeatureConfig, ?> TREE_BETTER_JUNGLE_NO_VINE = Feature.TREE.configure(jungleBuilder(true, false).build());
	public static final ConfiguredFeature<TreeFeatureConfig, ?> TREE_DEAD_JUNGLE = Feature.TREE.configure(jungleBuilder(true, true).decorators(ImmutableList.of(TrunkVineTreeDecorator.INSTANCE)).build());
	public static final ConfiguredFeature<TreeFeatureConfig, ?> TREE_BETTER_MEGA_JUNGLE = Feature.TREE.configure(megaJungleBuilder().decorators(ImmutableList.of(TrunkVineTreeDecorator.INSTANCE, LeavesVineTreeDecorator.INSTANCE)).build());
	public static final ConfiguredFeature<TreeFeatureConfig, ?> TREE_BETTER_MEGA_JUNGLE_NO_VINE = Feature.TREE.configure(megaJungleBuilder().build());

	public static final ConfiguredFeature<TreeFeatureConfig, ?> TREE_BETTER_ACACIA = Feature.TREE.configure(acaciaBuilder(false).build());
	public static final ConfiguredFeature<TreeFeatureConfig, ?> TREE_DEAD_ACACIA = Feature.TREE.configure(acaciaBuilder(true).build());

	public static final ConfiguredFeature<TreeFeatureConfig, ?> TREE_BETTER_DARK_OAK = Feature.TREE.configure(darkOakBuilder().build());

	public static final ConfiguredFeature<TreeFeatureConfig, ?> TREE_BETTER_AZALEA = Feature.TREE.configure(azaleaBuilder().build());



	public static final ConfiguredFeature<RandomFeatureConfig, ?> BETTER_BUSHES = Feature.RANDOM_SELECTOR.configure(
		new RandomFeatureConfig(
			List.of(
				new RandomFeatureEntry(BetterTreesPlacedFeatures.UNDERGROWTH_BUSH, 1f)
			),
			BetterTreesPlacedFeatures.UNDERGROWTH_BUSH
		)
	);

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
				new RandomFeatureEntry(BetterTreesPlacedFeatures.TREE_DEAD_BIRCH, 0.005f),
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
				new RandomFeatureEntry(BetterTreesPlacedFeatures.TREE_BETTER_DARK_OAK, 2f / 3f),
				new RandomFeatureEntry(BetterTreesPlacedFeatures.TREE_BETTER_BIRCH, 0.2f)),
			BetterTreesPlacedFeatures.TREE_BETTER_OAK
		)
	);

	public static final ConfiguredFeature<RandomFeatureConfig, ?> BETTER_BAMBOO_JUNGLE_VEGETATION = Feature.RANDOM_SELECTOR.configure(
		new RandomFeatureConfig(
			List.of(
				new RandomFeatureEntry(BetterTreesPlacedFeatures.TREE_BETTER_OAK, 0.02f),
				new RandomFeatureEntry(TreePlacedFeatures.JUNGLE_BUSH, 0.15f),
				new RandomFeatureEntry(BetterTreesPlacedFeatures.TREE_BETTER_MEGA_JUNGLE, 0.7f),
				new RandomFeatureEntry(BetterTreesPlacedFeatures.TREE_BETTER_JUNGLE, 0.08f)
			),
			VegetationConfiguredFeatures.PATCH_GRASS_JUNGLE.withPlacement()
		)
	);

	public static final ConfiguredFeature<RandomFeatureConfig, ?> BETTER_JUNGLE_TREES = Feature.RANDOM_SELECTOR.configure(
		new RandomFeatureConfig(
			List.of(
				new RandomFeatureEntry(BetterTreesPlacedFeatures.TREE_SHORT_JUNGLE, 0.1f),
				new RandomFeatureEntry(BetterTreesPlacedFeatures.TREE_DEAD_JUNGLE, 0.015f),
				new RandomFeatureEntry(BetterTreesPlacedFeatures.DEAD_JUNGLE_LOG, 0.15f),
				new RandomFeatureEntry(BetterTreesPlacedFeatures.TREE_BETTER_JUNGLE, 0.3f)
			),
			BetterTreesPlacedFeatures.TREE_BETTER_MEGA_JUNGLE
		)
	);

	public static final ConfiguredFeature<RandomFeatureConfig, ?> BETTER_JUNGLE_BUSHES = Feature.RANDOM_SELECTOR.configure(
		new RandomFeatureConfig(
			List.of(
				new RandomFeatureEntry(TreePlacedFeatures.JUNGLE_BUSH, 1f)
			),
			TreePlacedFeatures.JUNGLE_BUSH
		)
	);

	public static final ConfiguredFeature<RandomFeatureConfig, ?> BETTER_SPARSE_JUNGLE_TREES = Feature.RANDOM_SELECTOR.configure(
		new RandomFeatureConfig(
			List.of(
				new RandomFeatureEntry(BetterTreesPlacedFeatures.TREE_BETTER_OAK, 0.1f),
				new RandomFeatureEntry(TreePlacedFeatures.JUNGLE_BUSH, 0.7f),
				new RandomFeatureEntry(BetterTreesPlacedFeatures.DEAD_JUNGLE_LOG, 0.05f)
			),
			BetterTreesPlacedFeatures.TREE_SHORT_JUNGLE
		)
	);

	public static final ConfiguredFeature<RandomFeatureConfig, ?> BETTER_SAVANNAH_TREES = Feature.RANDOM_SELECTOR.configure(
		new RandomFeatureConfig(
			List.of(
				new RandomFeatureEntry(BetterTreesPlacedFeatures.TREE_BETTER_ACACIA, 0.8f),
				new RandomFeatureEntry(BetterTreesPlacedFeatures.TREE_DEAD_ACACIA, 0.02f),
				new RandomFeatureEntry(BetterTreesPlacedFeatures.DEAD_ACACIA_LOG, 0.08f)
			),
			BetterTreesPlacedFeatures.TREE_BETTER_OAK
		)
	);

	public static final ConfiguredFeature<RandomFeatureConfig, ?> BETTER_MOUNTAIN_TREES = Feature.RANDOM_SELECTOR.configure(
		new RandomFeatureConfig(
			List.of(
				new RandomFeatureEntry(BetterTreesPlacedFeatures.TREE_BETTER_SPRUCE, 2f / 3f),
				new RandomFeatureEntry(BetterTreesPlacedFeatures.TREE_BETTER_SMALL_SPRUCE, 0.2f)
			),
			BetterTreesPlacedFeatures.TREE_BETTER_OAK
		)
	);

	public static final ConfiguredFeature<RandomFeatureConfig, ?> BETTER_TAIGA_TREES = Feature.RANDOM_SELECTOR.configure(
		new RandomFeatureConfig(
			List.of(
				new RandomFeatureEntry(BetterTreesPlacedFeatures.TREE_BETTER_PINE, 0.2f),
				new RandomFeatureEntry(BetterTreesPlacedFeatures.TREE_DEAD_SPRUCE, 0.01f),
				new RandomFeatureEntry(BetterTreesPlacedFeatures.TREE_DEAD_PINE, 0.01f),
				new RandomFeatureEntry(BetterTreesPlacedFeatures.DEAD_SPRUCE_LOG, 0.02f),
				new RandomFeatureEntry(BetterTreesPlacedFeatures.TREE_BETTER_SMALL_SPRUCE, 0.4f)
			),
			BetterTreesPlacedFeatures.TREE_BETTER_SPRUCE
		)
	);

	public static final ConfiguredFeature<RandomFeatureConfig, ?> BETTER_GROVE_TREES = Feature.RANDOM_SELECTOR.configure(
		new RandomFeatureConfig(
			List.of(
				new RandomFeatureEntry(BetterTreesPlacedFeatures.TREE_BETTER_SNOWY_PINE, 0.3f),
				new RandomFeatureEntry(BetterTreesPlacedFeatures.TREE_BETTER_SMALL_SPRUCE, 0.4f)
			),
			BetterTreesPlacedFeatures.TREE_BETTER_SNOWY_SPRUCE
		)
	);

	public static final ConfiguredFeature<RandomFeatureConfig, ?> BETTER_OLD_GROWTH_SPRUCE_TAIGA_TREES = Feature.RANDOM_SELECTOR.configure(
		new RandomFeatureConfig(
			List.of(
				new RandomFeatureEntry(BetterTreesPlacedFeatures.TREE_BETTER_MEGA_SPRUCE, 0.5f),
				new RandomFeatureEntry(BetterTreesPlacedFeatures.TREE_BETTER_PINE, 0.1f),
				new RandomFeatureEntry(BetterTreesPlacedFeatures.TREE_BETTER_SMALL_SPRUCE, 0.4f),
				new RandomFeatureEntry(BetterTreesPlacedFeatures.TREE_DEAD_SPRUCE, 0.01f),
				new RandomFeatureEntry(BetterTreesPlacedFeatures.TREE_DEAD_PINE, 0.01f),
				new RandomFeatureEntry(BetterTreesPlacedFeatures.DEAD_SPRUCE_LOG, 0.02f)

			),
			BetterTreesPlacedFeatures.TREE_BETTER_SPRUCE
		)
	);

	public static final ConfiguredFeature<RandomFeatureConfig, ?> BETTER_OLD_GROWTH_PINE_TAIGA_TREES = Feature.RANDOM_SELECTOR.configure(
		new RandomFeatureConfig(
			List.of(
				new RandomFeatureEntry(BetterTreesPlacedFeatures.TREE_BETTER_MEGA_PINE, 0.6f),
				new RandomFeatureEntry(BetterTreesPlacedFeatures.TREE_BETTER_MEGA_SPRUCE, 0.1f),
				new RandomFeatureEntry(BetterTreesPlacedFeatures.TREE_BETTER_PINE, 0.4f),
				new RandomFeatureEntry(BetterTreesPlacedFeatures.TREE_BETTER_SMALL_SPRUCE, 0.4f),
				new RandomFeatureEntry(BetterTreesPlacedFeatures.TREE_DEAD_SPRUCE, 0.01f),
				new RandomFeatureEntry(BetterTreesPlacedFeatures.TREE_DEAD_PINE, 0.01f),
				new RandomFeatureEntry(BetterTreesPlacedFeatures.DEAD_SPRUCE_LOG, 0.02f)

			),
			BetterTreesPlacedFeatures.TREE_BETTER_SPRUCE
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
		registerConfiguredFeature("dead_spruce_log", DEAD_SPRUCE_LOG);
		registerConfiguredFeature("dead_birch_log", DEAD_BIRCH_LOG);
		registerConfiguredFeature("dead_jungle_log", DEAD_JUNGLE_LOG);
		registerConfiguredFeature("dead_acacia_log", DEAD_ACACIA_LOG);

		registerConfiguredFeature("oak_stump", OAK_STUMP);
		registerConfiguredFeature("birch_stump", BIRCH_STUMP);
		registerConfiguredFeature("undergrowth_bush", UNDERGROWTH_BUSH);

		registerConfiguredFeature("better_oak", TREE_BETTER_OAK);
		registerConfiguredFeature("dead_oak", TREE_DEAD_OAK);
		registerConfiguredFeature("better_swamp_oak", TREE_BETTER_SWAMP_OAK);
		registerConfiguredFeature("better_oak_rare_bees", TREE_BETTER_OAK_RARE_BEES);
		registerConfiguredFeature("better_oak_regular_bees", TREE_BETTER_OAK_REGULAR_BEES);
		registerConfiguredFeature("better_oak_more_bees", TREE_BETTER_OAK_MORE_BEES);

		registerConfiguredFeature("better_small_spruce", TREE_BETTER_SMALL_SPRUCE);
		registerConfiguredFeature("better_spruce", TREE_BETTER_SPRUCE);
		registerConfiguredFeature("dead_spruce", TREE_DEAD_SPRUCE);
		registerConfiguredFeature("better_pine", TREE_BETTER_PINE);
		registerConfiguredFeature("dead_pine", TREE_DEAD_PINE);
		registerConfiguredFeature("better_mega_spruce", TREE_BETTER_MEGA_SPRUCE);
		registerConfiguredFeature("better_mega_pine", TREE_BETTER_MEGA_PINE);

		registerConfiguredFeature("better_birch", TREE_BETTER_BIRCH);
		registerConfiguredFeature("dead_birch", TREE_TALL_DEAD_BIRCH);
		registerConfiguredFeature("tall_dead_birch", TREE_DEAD_BIRCH);
		registerConfiguredFeature("better_birch_rare_bees", TREE_BETTER_BIRCH_RARE_BEES);
		registerConfiguredFeature("better_tall_birch_rare_bees", TREE_TALL_BETTER_BIRCH_RARE_BEES);
		registerConfiguredFeature("better_birch_regular_bees", TREE_BETTER_BIRCH_REGULAR_BEES);
		registerConfiguredFeature("better_birch_more_bees", TREE_BETTER_BIRCH_MORE_BEES);

		registerConfiguredFeature("better_jungle", TREE_BETTER_JUNGLE);
		registerConfiguredFeature("short_jungle", TREE_SHORT_JUNGLE);
		registerConfiguredFeature("better_jungle_no_vine", TREE_BETTER_JUNGLE_NO_VINE);
		registerConfiguredFeature("dead_jungle", TREE_DEAD_JUNGLE);
		registerConfiguredFeature("better_mega_jungle", TREE_BETTER_MEGA_JUNGLE);
		registerConfiguredFeature("better_mega_jungle_no_vine", TREE_BETTER_MEGA_JUNGLE_NO_VINE);

		registerConfiguredFeature("better_acacia", TREE_BETTER_ACACIA);
		registerConfiguredFeature("dead_acacia", TREE_DEAD_ACACIA);

		registerConfiguredFeature("better_dark_oak", TREE_BETTER_DARK_OAK);

		registerConfiguredFeature("better_azalea", TREE_BETTER_AZALEA);


		registerConfiguredFeature("better_forest_trees", BETTER_FOREST_TREES);
		registerConfiguredFeature("better_bushes", BETTER_BUSHES);
		registerConfiguredFeature("better_birch_forest_trees", BETTER_BIRCH_FOREST_TREES);
		registerConfiguredFeature("better_tall_birch_forest_trees", BETTER_TALL_BIRCH_FOREST_TREES);
		registerConfiguredFeature("better_dark_forest_vegetation_brown", BETTER_DARK_FOREST_VEGETATION_BROWN);
		registerConfiguredFeature("better_bamboo_jungle_vegetation", BETTER_BAMBOO_JUNGLE_VEGETATION);
		registerConfiguredFeature("better_jungle_trees", BETTER_JUNGLE_TREES);
		registerConfiguredFeature("better_jungle_bushes", BETTER_JUNGLE_BUSHES);
		registerConfiguredFeature("better_sparse_jungle_trees", BETTER_SPARSE_JUNGLE_TREES);
		registerConfiguredFeature("better_savannah_trees", BETTER_SAVANNAH_TREES);

		registerConfiguredFeature("better_mountain_trees", BETTER_MOUNTAIN_TREES);
		registerConfiguredFeature("better_taiga_trees", BETTER_TAIGA_TREES);
		registerConfiguredFeature("better_grove_trees", BETTER_GROVE_TREES);
		registerConfiguredFeature("better_old_growth_spruce_taiga_trees", BETTER_OLD_GROWTH_SPRUCE_TAIGA_TREES);
		registerConfiguredFeature("better_old_growth_pine_taiga_trees", BETTER_OLD_GROWTH_PINE_TAIGA_TREES);
	}

	private static TreeFeatureConfig.Builder oakBuilder(boolean dead) {
		return new TreeFeatureConfig.Builder(
			SimpleBlockStateProviderInvoker.invokeCtor(Blocks.OAK_WOOD.getDefaultState()),
			new BetterOakTrunkPlacer(5, 5, 4, 2.50D, 3D, 2, 4, 0D, 1D, 0.6D, 1D),
			SimpleBlockStateProviderInvoker.invokeCtor((dead ? Blocks.AIR : Blocks.OAK_LEAVES).getDefaultState()),
			new RandomSpreadFoliagePlacer(BiasedToBottomIntProvider.create(2, 4), ConstantIntProvider.create(0), BiasedToBottomIntProvider.create(3, 4), 40),
			new TwoLayersFeatureSize(5, 0, 5)
		);
	}

	private static TreeFeatureConfig.Builder smallSpruceBuilder() {
		return new TreeFeatureConfig.Builder(
			SimpleBlockStateProviderInvoker.invokeCtor(Blocks.SPRUCE_WOOD.getDefaultState()),
			new BetterSmallSpruceTrunkPlacer(4, 0, 0),
			SimpleBlockStateProviderInvoker.invokeCtor((Blocks.SPRUCE_LEAVES).getDefaultState()),
			new SmallSpruceFoliagePlacer(ConstantIntProvider.create(0), ConstantIntProvider.create(0)),
			new TwoLayersFeatureSize(0, 0, 2)
		);
	}

	private static TreeFeatureConfig.Builder spruceBuilder(boolean mega, boolean dead) {
		if (mega) {
			return new TreeFeatureConfig.Builder(
				SimpleBlockStateProviderInvoker.invokeCtor(Blocks.SPRUCE_WOOD.getDefaultState()),
				new BetterMegaSpruceTrunkPlacer(22, 5, 5),
				SimpleBlockStateProviderInvoker.invokeCtor((Blocks.SPRUCE_LEAVES).getDefaultState()),
				new CircleFoliagePlacer(ConstantIntProvider.create(0), ConstantIntProvider.create(0), 0.5d),
				new TwoLayersFeatureSize(3, 0, 7)
			);
		}
		return new TreeFeatureConfig.Builder(
			SimpleBlockStateProviderInvoker.invokeCtor(Blocks.SPRUCE_WOOD.getDefaultState()),
			new BetterSpruceTrunkPlacer(12, 3, 0),
			SimpleBlockStateProviderInvoker.invokeCtor((dead ? Blocks.AIR : Blocks.SPRUCE_LEAVES).getDefaultState()),
			new CircleFoliagePlacer(ConstantIntProvider.create(0), ConstantIntProvider.create(0), 0.8d),
			new TwoLayersFeatureSize(2, 0, 4)
		);
	}

	private static TreeFeatureConfig.Builder pineBuilder(boolean mega, boolean dead) {
		if (mega) {
			return new TreeFeatureConfig.Builder(
				SimpleBlockStateProviderInvoker.invokeCtor(Blocks.SPRUCE_WOOD.getDefaultState()),
				new BetterMegaPineTrunkPlacer(32, 10, 3),
				SimpleBlockStateProviderInvoker.invokeCtor((Blocks.SPRUCE_LEAVES).getDefaultState()),
				new RandomSpreadFoliagePlacer(UniformIntProvider.create(2, 3), ConstantIntProvider.create(0), ConstantIntProvider.create(3), 40),
				new TwoLayersFeatureSize(20, 0, 5)
			);
		}
		return new TreeFeatureConfig.Builder(
			SimpleBlockStateProviderInvoker.invokeCtor(Blocks.SPRUCE_WOOD.getDefaultState()),
			new BetterPineTrunkPlacer(20, 6, 3),
			SimpleBlockStateProviderInvoker.invokeCtor((dead ? Blocks.AIR : Blocks.SPRUCE_LEAVES).getDefaultState()),
			new PineFoliagePlacer(UniformIntProvider.create(1, 2), ConstantIntProvider.create(0), ConstantIntProvider.create(2)),
			new TwoLayersFeatureSize(9, 0, 3)
		);
	}

	private static TreeFeatureConfig.Builder birchBuilder(boolean tall, boolean dead) {
		if (tall) {
			return new TreeFeatureConfig.Builder(
				SimpleBlockStateProviderInvoker.invokeCtor(Blocks.BIRCH_WOOD.getDefaultState()),
				new BetterTallBirchTrunkPlacer(7, 10, 1, 0.5D, 2D, 2, 10, 0D, 1D, 1D, 1D),
				SimpleBlockStateProviderInvoker.invokeCtor((dead ? Blocks.AIR : Blocks.BIRCH_LEAVES).getDefaultState()),
				new RandomSpreadFoliagePlacer(BiasedToBottomIntProvider.create(2, 4), ConstantIntProvider.create(0), BiasedToBottomIntProvider.create(2, 5), 40),
				new TwoLayersFeatureSize(10, 0, 3)
			);
		}
		return new TreeFeatureConfig.Builder(
			SimpleBlockStateProviderInvoker.invokeCtor(Blocks.BIRCH_WOOD.getDefaultState()),
			new BetterShortBirchTrunkPlacer(4, 7, 3, 2D, 2D, 2, 2, 0D, 1D, 1D, 1D),
			SimpleBlockStateProviderInvoker.invokeCtor((dead ? Blocks.AIR : Blocks.BIRCH_LEAVES).getDefaultState()),
			new RandomSpreadFoliagePlacer(BiasedToBottomIntProvider.create(2, 3), ConstantIntProvider.create(0), BiasedToBottomIntProvider.create(2, 4), 20),
			new TwoLayersFeatureSize(6, 0, 2)
		);
	}

	private static TreeFeatureConfig.Builder jungleBuilder(boolean tall, boolean dead) {
		if (tall) {
			return new TreeFeatureConfig.Builder(
				SimpleBlockStateProviderInvoker.invokeCtor(Blocks.JUNGLE_WOOD.getDefaultState()),
				new BetterJungleTrunkPlacer(8, 10, 18, 0.2D),
				SimpleBlockStateProviderInvoker.invokeCtor((dead ? Blocks.AIR : Blocks.JUNGLE_LEAVES).getDefaultState()),
				new JungleFoliagePlacer(BiasedToBottomIntProvider.create(0, 0), ConstantIntProvider.create(0), 1),
				new TwoLayersFeatureSize(10, 0, 2)
			);
		}
		return new TreeFeatureConfig.Builder(
			SimpleBlockStateProviderInvoker.invokeCtor(Blocks.JUNGLE_WOOD.getDefaultState()),
			new BetterJungleTrunkPlacer(6, 4, 8, 0.2D),
			SimpleBlockStateProviderInvoker.invokeCtor((dead ? Blocks.AIR : Blocks.JUNGLE_LEAVES).getDefaultState()),
			new JungleFoliagePlacer(BiasedToBottomIntProvider.create(0, 0), ConstantIntProvider.create(0), 1),
			new TwoLayersFeatureSize(8, 0, 2)
		);
	}

	private static TreeFeatureConfig.Builder megaJungleBuilder() {
		return new TreeFeatureConfig.Builder(
			SimpleBlockStateProviderInvoker.invokeCtor(Blocks.JUNGLE_WOOD.getDefaultState()),
			new BetterMegaJungleTrunkPlacer(16, 14, 10),
			SimpleBlockStateProviderInvoker.invokeCtor(Blocks.JUNGLE_LEAVES.getDefaultState()),
			new JungleFoliagePlacer(BiasedToBottomIntProvider.create(0, 0), ConstantIntProvider.create(0), 1),
			new TwoLayersFeatureSize(28, 1, 3)
		);
	}

	private static TreeFeatureConfig.Builder acaciaBuilder(boolean dead) {
		return new TreeFeatureConfig.Builder(
			SimpleBlockStateProviderInvoker.invokeCtor(Blocks.ACACIA_WOOD.getDefaultState()),
			new BetterAcaciaTrunkPlacer(3, 2, 0),
			SimpleBlockStateProviderInvoker.invokeCtor((dead ? Blocks.AIR : Blocks.ACACIA_LEAVES).getDefaultState()),
			new AcaciaFoliagePlacer(BiasedToBottomIntProvider.create(1, 2), ConstantIntProvider.create(0)),
			new TwoLayersFeatureSize(3, 0, 3)
		);
	}

	private static TreeFeatureConfig.Builder darkOakBuilder() {
		return new TreeFeatureConfig.Builder(
			SimpleBlockStateProviderInvoker.invokeCtor(Blocks.DARK_OAK_WOOD.getDefaultState()),
			new BetterDarkOakTrunkPlacer(6, 8, 4),
			SimpleBlockStateProviderInvoker.invokeCtor(Blocks.DARK_OAK_LEAVES.getDefaultState()),
			new RandomSpreadFoliagePlacer(BiasedToBottomIntProvider.create(2, 4), ConstantIntProvider.create(0), BiasedToBottomIntProvider.create(2, 4), 60),
			new TwoLayersFeatureSize(8, 1, 9)
		);
	}

	private static TreeFeatureConfig.Builder azaleaBuilder() {
		return new TreeFeatureConfig.Builder(
			SimpleBlockStateProviderInvoker.invokeCtor(Blocks.OAK_WOOD.getDefaultState()),
			new BetterAzaleaTrunkPlacer(4, 2, 0),
			// CASTING IS NECESSARY
			new WeightedBlockStateProvider((DataPool.Builder)DataPool.builder().add(Blocks.AZALEA_LEAVES.getDefaultState(), 3).add(Blocks.FLOWERING_AZALEA_LEAVES.getDefaultState(), 1)),
			new RandomSpreadFoliagePlacer(BiasedToBottomIntProvider.create(2, 3), ConstantIntProvider.create(0), UniformIntProvider.create(2, 4), 80),
			new TwoLayersFeatureSize(4, 0, 3)
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
