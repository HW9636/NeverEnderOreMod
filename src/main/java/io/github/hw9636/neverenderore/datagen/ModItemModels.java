package io.github.hw9636.neverenderore.datagen;

import io.github.hw9636.neverenderore.NeverEnderOreMod;
import io.github.hw9636.neverenderore.common.ModRegistration;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItemModels extends ItemModelProvider {

    public ModItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, NeverEnderOreMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ModRegistration.ORE_ITEMS.getEntries().forEach((r) -> {
            withExistingParent(r.getId().getPath(), modLoc("block/" + r.getId().getPath()));
        });

        ModRegistration.RAW_ORE_ITEMS.getEntries().forEach((r) -> {
            getBuilder(r.get().toString())
                    .parent(new ModelFile.UncheckedModelFile("item/generated"))
                    .texture("layer0", new ResourceLocation(NeverEnderOreMod.MODID, "raw/" + r.getId().getPath()));
        });
    }
}
