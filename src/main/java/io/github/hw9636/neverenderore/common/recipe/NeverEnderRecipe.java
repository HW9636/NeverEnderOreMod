package io.github.hw9636.neverenderore.common.recipe;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class NeverEnderRecipe implements Recipe<SimpleContainer> {

    private final ResourceLocation id;
    private final ItemStack result;
    private final Block validBlock;
    private final int ticks, energy;

    public NeverEnderRecipe(ResourceLocation id, ItemStack result, Block validBlock, int ticks, int energy) {
        this.id = id;
        this.result = result;
        this.validBlock = validBlock;
        this.ticks = ticks;
        this.energy = energy;
    }

    public int getTicks() {
        return ticks;
    }

    public int getEnergy() {
        return energy;
    }

    public Block getValidBlock() {
        return validBlock;
    }

    @Deprecated
    @Override
    public boolean matches(@NotNull SimpleContainer pContainer, @NotNull Level pLevel) {
        return false;
    }

    @Deprecated
    @Override
    public ItemStack assemble(@NotNull SimpleContainer pContainer) {
        return null;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem() {
        return result.copy();
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return NeverEnderOreSerializer.INSTANCE;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return NeverEnderRecipeType.INSTANCE;
    }
}
