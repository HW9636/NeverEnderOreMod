package io.github.hw9636.neverenderore.common;

import io.github.hw9636.neverenderore.NeverEnderOreMod;
import io.github.hw9636.neverenderore.common.oreextractor.OreExtractorBlock;
import io.github.hw9636.neverenderore.common.oreextractor.OreExtractorBlockEntity;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("unused")
public class ModRegistration {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, NeverEnderOreMod.MODID);
    public static final RegistryObject<OreExtractorBlock> ORE_EXTRACTOR_BLOCK = BLOCKS.register("ore_extractor", OreExtractorBlock::new);

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, NeverEnderOreMod.MODID);
    public static final RegistryObject<BlockItem> ORE_EXTRACTOR_ITEM = ITEMS.register("ore_extractor",
            () -> new BlockItem(ORE_EXTRACTOR_BLOCK.get(), new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, NeverEnderOreMod.MODID);
    @SuppressWarnings("ConstantConditions")
    public static final RegistryObject<BlockEntityType<OreExtractorBlockEntity>> ORE_EXTRACTOR_BE = BLOCK_ENTITIES.register("ore_extractor",
            () -> BlockEntityType.Builder.of(OreExtractorBlockEntity::new, ORE_EXTRACTOR_BLOCK.get()).build(null));

    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, NeverEnderOreMod.MODID);
}
