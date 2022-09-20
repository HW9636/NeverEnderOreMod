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

import java.util.Collections;
import java.util.List;

public class JEICategory implements IRecipeCategory<NeverEnderRecipe> {

    private static final Component TITLE = Component.translatable("title.never_ender_ore.jei_category");
    private static final ResourceLocation BACKGROUND_RL = new ResourceLocation(NeverEnderOreMod.MODID, "textures/gui/jei_category.png");

    static final RecipeType<NeverEnderRecipe> RECIPE_TYPE = RecipeType.create(NeverEnderOreMod.MODID, "never_ender_recipe",
            NeverEnderRecipe.class);
    private final IDrawable background, icon;
//    private final IDrawableStatic staticProgress;
//    private IDrawableAnimated arrowAnimated;
//    private int mapped;
//    private IDrawableStatic energyBar;
//    private final IGuiHelper helper;

    public JEICategory(IGuiHelper helper) {
        this.background = helper.createDrawable(BACKGROUND_RL, 0,0,176,92);
        this.icon = helper.createDrawableItemStack(ModRegistration.ORE_EXTRACTOR_ITEM.get().getDefaultInstance());
//        this.helper = helper;
//        this.staticProgress = helper.createDrawable(BACKGROUND_RL, 189, 12, 198 - 189, 23 - 12);
//        this.energyBar = helper.createBlankDrawable(185 - 182, 83 - 12);
    }

    @Override
    public void draw(@NotNull NeverEnderRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView,
                     @NotNull PoseStack stack, double mouseX, double mouseY) {

//        this.arrowAnimated.draw(stack, 84, 28);
//        this.energyBar.draw(stack, 165, 12 + this.mapped);
    }

    @Override
    public @NotNull List<Component> getTooltipStrings(@NotNull NeverEnderRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView,
                                                      double mouseX, double mouseY) {
        if (mouseX >= 165 && mouseX <= 168 && mouseY >= 12 && mouseY <= 83) {
            return List.of(Component.literal(recipe.getEnergy() + " FE"));
        }

        return Collections.emptyList();
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


        //this.mapped = recipe.getEnergy() * (83 - 12) / NEOConfig.COMMON.maxEnergyStored.get();
        //this.energyBar = helper.createDrawable(BACKGROUND_RL, 182, 12 + mapped, 185 - 182, mapped);
        //this.arrowAnimated = this.helper.createAnimatedDrawable(this.staticProgress, recipe.getTicks(),
        //IDrawableAnimated.StartDirection.BOTTOM, false);
    }
}
