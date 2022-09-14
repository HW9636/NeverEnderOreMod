package io.github.hw9636.neverenderore.common.recipe;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class ExtractorRecipe implements Recipe<Container> {
    @Override
    public boolean matches(Container container, Level level) {
        return false;
    }

    @Override
    public ItemStack assemble(Container container) {
        return null;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getResultItem() {
        return null;
    }

    @Override
    public ResourceLocation getId() {
        return null;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return null;
    }

    @Override
    public RecipeType<?> getType() {
        return null;
    }
}
