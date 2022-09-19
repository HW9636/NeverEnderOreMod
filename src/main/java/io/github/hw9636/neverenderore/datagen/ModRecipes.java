package io.github.hw9636.neverenderore.datagen;

import com.mojang.logging.LogUtils;
import io.github.hw9636.neverenderore.NeverEnderOreMod;
import io.github.hw9636.neverenderore.common.ModRegistration;
import io.github.hw9636.neverenderore.common.recipe.NeverEnderRecipeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.function.Consumer;

@SuppressWarnings("deprecated")
public class ModRecipes extends RecipeProvider {

    private static final Logger logger = LogUtils.getLogger();
    public ModRecipes(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> finishedRecipeConsumer) {
        HashMap<Block, ItemStack> extractingRecipes = new HashMap<>();
        HashMap<String, Block> mappedBlocks = new HashMap<>();
        HashMap<String, Item> mappedItems = new HashMap<>();

        ModRegistration.ORES.getEntries().forEach(o -> mappedBlocks.put(o.getId().getPath(), o.get()));
        ModRegistration.RAW_ORE_ITEMS.getEntries().forEach(o -> mappedItems.put(o.getId().getPath(), o.get()));

        for (String type : ModRegistration.ORE_TYPES) {
            if (ModRegistration.EXCLUDED_RAW_TYPES.contains(type)) continue;

            Block block = mappedBlocks.get(type + "_ore");
            Item item = mappedItems.get("raw_" + type);

            if (block.equals(Blocks.AIR) || item.equals(Items.AIR)) {
                logger.warn("Empty Block or Result of recipe: " + type);
                continue;
            }
            extractingRecipes.put(block, item.getDefaultInstance());
        }


        extractingRecipes.forEach((k, v) -> new NeverEnderRecipeBuilder().setBlock(k).setResult(v).save(finishedRecipeConsumer));

        // Excluded Recipes
        for (String excluded : ModRegistration.EXCLUDED_RAW_TYPES) {
            new NeverEnderRecipeBuilder()
                    .setBlock(mappedBlocks.get( excluded + "_ore"))
                    .setResult(Registry.ITEM.get(new ResourceLocation("minecraft","raw" + excluded.replace("never_ender",""))).getDefaultInstance())
                    .save(finishedRecipeConsumer, new ResourceLocation(NeverEnderOreMod.MODID, "raw_" + excluded));
        }
    }
}
