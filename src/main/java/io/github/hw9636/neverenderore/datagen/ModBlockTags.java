package io.github.hw9636.neverenderore.datagen;

import io.github.hw9636.neverenderore.NeverEnderOreMod;
import io.github.hw9636.neverenderore.common.ModRegistration;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
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
    }

    @Override
    public @NotNull String getName() {
        return "NeverEnderOre Tags";
    }
}
