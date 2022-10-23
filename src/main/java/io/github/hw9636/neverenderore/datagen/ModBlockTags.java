package io.github.hw9636.neverenderore.datagen;

import io.github.hw9636.neverenderore.NeverEnderOreMod;
import io.github.hw9636.neverenderore.common.ModRegistration;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

public class ModBlockTags extends BlockTagsProvider {

    public ModBlockTags(DataGenerator generator, ExistingFileHelper helper) {
        super(generator, NeverEnderOreMod.MODID, helper);
    }

    @Override
    protected void addTags() {
        Block[] ores = ModRegistration.ORES.getEntries().stream().map(RegistryObject::get).toArray(Block[]::new);

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModRegistration.ORE_EXTRACTOR_BLOCK.get())
                .add(ores);
        tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ores);
        tag(blockTag("neverenderore:neo")).add(ores);

        for (int i = 0;i<ModRegistration.ORE_TYPES.length;i++) {
            String type = ModRegistration.ORE_TYPES[i];
            tag(blockTag("neverenderore:neo/" + type.replace("never_ender_", ""))).add(ores[i]);
        }
    }

    @SuppressWarnings("deprecation")
    private TagKey<Block> blockTag(String tag) {
        return TagKey.create(Registry.BLOCK.key(), new ResourceLocation(tag));
    }

    @Override
    public @NotNull String getName() {
        return "NeverEnderOre Tags";
    }
}
