package io.github.hw9636.neverenderore.common.recipe;

import net.minecraft.world.item.crafting.RecipeType;

public class NeverEnderRecipeType implements RecipeType<NeverEnderRecipe> {
    private NeverEnderRecipeType() { }

    public static final NeverEnderRecipeType INSTANCE = new NeverEnderRecipeType();
}
