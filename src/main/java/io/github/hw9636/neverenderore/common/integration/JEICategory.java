package io.github.hw9636.neverenderore.common.integration;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.hw9636.neverenderore.NeverEnderOreMod;
import io.github.hw9636.neverenderore.common.ModRegistration;
import io.github.hw9636.neverenderore.common.recipe.NeverEnderRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class JEICategory implements IRecipeCategory<NeverEnderRecipe> {

    private static final Component TITLE = Component.translatable("title.never_ender_ore.jei_category");
    private static final ResourceLocation BACKGROUND_RL = new ResourceLocation(NeverEnderOreMod.MODID, "textures/gui/jei_category.png");

    static final RecipeType<NeverEnderRecipe> RECIPE_TYPE = RecipeType.create(NeverEnderOreMod.MODID, "never_ender_recipe",
            NeverEnderRecipe.class);
    private final IDrawable background, icon;

    public JEICategory(IGuiHelper helper) {
        this.background = helper.createDrawable(BACKGROUND_RL, 0,0,176,92);
        this.icon = helper.createDrawableItemStack(ModRegistration.ORE_EXTRACTOR_ITEM.get().getDefaultInstance());
    }

    @Override
    public void draw(NeverEnderRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, recipeSlotsView, stack, mouseX, mouseY);
    }

    @Override
    public @NotNull RecipeType<NeverEnderRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return TITLE;
    }

    @Override
    public @NotNull IDrawable getBackground() {
        return background;
    }

    @Override
    public @NotNull IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull NeverEnderRecipe recipe, @NotNull IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.OUTPUT, 80, 9).addItemStack(recipe.getResultItem());
        builder.addSlot(RecipeIngredientRole.CATALYST, 80, 41).addItemStack(new ItemStack(ModRegistration.ORE_EXTRACTOR_ITEM.get()));
        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 80, 59).addItemStack(new ItemStack(recipe.getValidBlock().asItem()));
    }
}
