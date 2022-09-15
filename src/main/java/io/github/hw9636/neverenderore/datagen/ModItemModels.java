package io.github.hw9636.neverenderore.datagen;

import io.github.hw9636.neverenderore.NeverEnderOreMod;
import io.github.hw9636.neverenderore.common.ModRegistration;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModels extends ItemModelProvider {

    public ModItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, NeverEnderOreMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ModRegistration.ORE_ITEMS.getEntries().forEach((r) -> {
            withExistingParent(r.getId().getPath(), modLoc("block/" + r.getId().getPath()));
        });
    }
}
