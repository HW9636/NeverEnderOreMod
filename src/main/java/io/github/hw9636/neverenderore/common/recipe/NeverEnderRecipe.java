package io.github.hw9636.neverenderore.common.recipe;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class NeverEnderRecipe implements Recipe<SimpleContainer> {

    private final ResourceLocation id;
    private final ItemStack result;
    private final Block validBlock;

    public NeverEnderRecipe(ResourceLocation id, ItemStack result, Block validBlock) {
        this.id = id;
        this.result = result;
        this.validBlock = validBlock;
    }

    public Block getValidBlock() {
        return validBlock;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        return false;
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer) {
        return null;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return result.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return NeverEnderOreSerializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return NeverEnderRecipeType.INSTANCE;
    }
}
