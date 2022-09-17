package io.github.hw9636.neverenderore.common.recipe;

import com.google.gson.JsonObject;
import io.github.hw9636.neverenderore.NeverEnderOreMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.Charset;

public class NeverEnderOreSerializer implements RecipeSerializer<NeverEnderRecipe> {
    public static final NeverEnderOreSerializer INSTANCE = new NeverEnderOreSerializer();
    public static final ResourceLocation ID = new ResourceLocation(NeverEnderOreMod.MODID, NeverEnderRecipeType.id);


    @Override
    public NeverEnderRecipe fromJson(ResourceLocation recipeId, JsonObject serializedRecipe) {
        ItemStack result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(serializedRecipe, "result"));
        Block validBlock = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(serializedRecipe.get("block").getAsString()));

        return new NeverEnderRecipe(recipeId, result, validBlock);
    }

    @Override
    public @Nullable NeverEnderRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {

        int length = pBuffer.readInt();
        Block validBlock = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(pBuffer.readCharSequence(length, Charset.defaultCharset()).toString()));
        ItemStack result = pBuffer.readItem();

        return new NeverEnderRecipe(pRecipeId, result, validBlock);
    }

    @Override
    public void toNetwork(FriendlyByteBuf pBuffer, NeverEnderRecipe pRecipe) {
        ResourceLocation rl = ForgeRegistries.BLOCKS.getKey(pRecipe.getValidBlock());
        String stringRl = rl != null ? rl.toString() : "minecraft:air";
        pBuffer.writeInt(stringRl.length());
        pBuffer.writeCharSequence(stringRl, Charset.defaultCharset());
        pBuffer.writeItemStack(pRecipe.getResultItem(), false);
    }
}
