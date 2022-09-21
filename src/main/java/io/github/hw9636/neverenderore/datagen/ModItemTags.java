package io.github.hw9636.neverenderore.datagen;

import io.github.hw9636.neverenderore.NeverEnderOreMod;
import io.github.hw9636.neverenderore.common.ModRegistration;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class ModItemTags extends ItemTagsProvider {

    public ModItemTags(DataGenerator generator, BlockTagsProvider blockTags, ExistingFileHelper helper) {
        super(generator, blockTags, NeverEnderOreMod.MODID, helper);
    }


    @Override
    protected void addTags() {
        Item[] ores = ModRegistration.ORE_ITEMS.getEntries().stream().map(RegistryObject::get).toArray(Item[]::new);
        Item[] rawOres = ModRegistration.RAW_ORE_ITEMS.getEntries().stream().map(RegistryObject::get).toArray(Item[]::new);
        HashMap<String, Item> rawOresMapped = new HashMap<>();
        ModRegistration.RAW_ORE_ITEMS.getEntries().forEach(r -> rawOresMapped.put(r.getId().getPath(), r.get()));

        tag(itemTag("forge:ores/neverenderore")).add(ores);
        tag(itemTag("forge:raw_materials")).add(rawOres);

        for (int i = 0;i<ModRegistration.ORE_TYPES.length;i++) {
            String type = ModRegistration.ORE_TYPES[i];
            tag(itemTag("forge:ores/neverenderore/" + type.replace("never_ender_", ""))).add(ores[i]);
            tag(itemTag("forge:raw_materials/" + type.replace("never_ender_", "")))
                    .add(rawOresMapped.get("raw_" + type));
        }
    }

    @SuppressWarnings("deprecation")
    private TagKey<Item> itemTag(String tag) {
        return TagKey.create(Registry.ITEM.key(), new ResourceLocation(tag));
    }

    @Override
    public @NotNull String getName() {
        return "NeverEnderOre Tags";
    }
}
