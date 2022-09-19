package io.github.hw9636.neverenderore.common;

import com.google.common.base.Suppliers;
import io.github.hw9636.neverenderore.NeverEnderOreMod;
import io.github.hw9636.neverenderore.common.oreextractor.OreExtractorBlock;
import io.github.hw9636.neverenderore.common.oreextractor.OreExtractorBlockEntity;
import io.github.hw9636.neverenderore.common.oreextractor.OreExtractorContainer;
import io.github.hw9636.neverenderore.common.recipe.NeverEnderOreSerializer;
import net.minecraft.core.Registry;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class ModRegistration {

    public static final String[] ORE_TYPES = { "never_ender_cinnabar", "never_ender_coal", "never_ender_copper",
            "never_ender_diamond", "never_ender_emerald", "never_ender_gold", "never_ender_iron",
            "never_ender_lapis", "never_ender_lead", "never_ender_nickel", "never_ender_niter",
            "never_ender_oil_sand", "never_ender_osmium", "never_ender_quartz", "never_ender_redstone",
            "never_ender_ruby", "never_ender_sapphire", "never_ender_sulfur", "never_ender_tin", "never_ender_uranium",
            "never_ender_aluminium", "never_ender_uraninite"};

    public static final List<String> EXCLUDED_RAW_TYPES = List.of("never_ender_gold", "never_ender_iron", "never_ender_copper");

    public static final CreativeModeTab MOD_TAB = new CreativeModeTab(NeverEnderOreMod.MODID) {
        @Override
        public @NotNull ItemStack makeIcon() {
            return ORE_EXTRACTOR_ITEM.get().getDefaultInstance();
        }
    };
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, NeverEnderOreMod.MODID);
    public static final RegistryObject<OreExtractorBlock> ORE_EXTRACTOR_BLOCK = BLOCKS.register("ore_extractor", OreExtractorBlock::new);

    public static final DeferredRegister<Block> ORES = DeferredRegister.create(ForgeRegistries.BLOCKS, NeverEnderOreMod.MODID);

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, NeverEnderOreMod.MODID);

    public static final DeferredRegister<Item> ORE_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, NeverEnderOreMod.MODID);

    public static final DeferredRegister<Item> RAW_ORE_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, NeverEnderOreMod.MODID);

    public static final RegistryObject<BlockItem> ORE_EXTRACTOR_ITEM = ITEMS.register("ore_extractor",
            () -> new BlockItem(ORE_EXTRACTOR_BLOCK.get(), new Item.Properties().tab(MOD_TAB)));

    public static final RegistryObject<Item> ORE_REMOVER = ITEMS.register("ore_remover",
            () -> new Item(new Item.Properties().tab(MOD_TAB)));

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, NeverEnderOreMod.MODID);
    @SuppressWarnings("ConstantConditions")
    public static final RegistryObject<BlockEntityType<OreExtractorBlockEntity>> ORE_EXTRACTOR_BE = BLOCK_ENTITIES.register("ore_extractor",
            () -> BlockEntityType.Builder.of(OreExtractorBlockEntity::new, ORE_EXTRACTOR_BLOCK.get()).build(null));

    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, NeverEnderOreMod.MODID);

    public static final RegistryObject<MenuType<OreExtractorContainer>> ORE_EXTRACTOR_CONTAINER = CONTAINERS.register("ore_extractor",
            () -> new MenuType<>(OreExtractorContainer::new));

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, NeverEnderOreMod.MODID);

    public static final RegistryObject<NeverEnderOreSerializer> NEVer_ENDER_ORE_RECIPE_SERIALIZER =
            RECIPE_SERIALIZERS.register("never_ender_ore_extracting", () -> NeverEnderOreSerializer.INSTANCE);

    public static final Supplier<List<OreConfiguration.TargetBlockState>> MOD_ORES;

    static {

        for (String type : ORE_TYPES) {
            RegistryObject<Block> block = ORES.register(type + "_ore", NeverEnderOre::new);
            ORE_ITEMS.register(type + "_ore",
                    () -> new BlockItem(block.get(), new Item.Properties().tab(MOD_TAB)));

            if (!EXCLUDED_RAW_TYPES.contains(type)) RAW_ORE_ITEMS.register("raw_" + type, RawNeverEnderItem::new);
        }

        MOD_ORES = Suppliers.memoize(() -> {
            List<OreConfiguration.TargetBlockState> targetBlockStates = new ArrayList<>();
            ORES.getEntries().stream().map(RegistryObject::get).forEach(b -> {
                targetBlockStates.add(OreConfiguration.target(new BlockMatchTest(Blocks.END_STONE), b.defaultBlockState()));
            });
            return targetBlockStates;
        });
    }

    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, NeverEnderOreMod.MODID);

    public static final int VEIN_SIZE = 9;
    public static final RegistryObject<ConfiguredFeature<?, ?>> END_ORES = CONFIGURED_FEATURES.register("never_ender_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(MOD_ORES.get(), VEIN_SIZE)));

    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES =
            DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, NeverEnderOreMod.MODID);

    public static final RegistryObject<PlacedFeature> NEVER_ENDER_ORE_FEATURE =
            PLACED_FEATURES.register("never_ender_ore_placed",
                    () -> new PlacedFeature(END_ORES.getHolder().get(),
                            List.of(CountPlacement.of(VEIN_SIZE), InSquarePlacement.spread(),
                                    HeightRangePlacement.uniform(VerticalAnchor.absolute(10),
                                            VerticalAnchor.absolute(180)))));
}
