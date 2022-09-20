package io.github.hw9636.neverenderore.datagen;

import io.github.hw9636.neverenderore.NeverEnderOreMod;
import io.github.hw9636.neverenderore.common.ModRegistration;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStates extends BlockStateProvider {

    public ModBlockStates(DataGenerator gen, ExistingFileHelper helper) {
        super(gen, NeverEnderOreMod.MODID, helper);
    }

    @Override
    protected void registerStatesAndModels() {
        for (RegistryObject<Block> block : ModRegistration.ORES.getEntries()) {
            Block b = block.get();
            ModelFile file = models().cubeAll(block.getId().getPath(),
                    new ResourceLocation(NeverEnderOreMod.MODID, "ore/" + block.getId().getPath()));
            simpleBlock(b, file);
        }

        ResourceLocation loc = ModRegistration.ORE_EXTRACTOR_BLOCK.getId();
        simpleBlock(ModRegistration.ORE_EXTRACTOR_BLOCK.get(), models().cubeBottomTop(loc.getPath(),
                new ResourceLocation(NeverEnderOreMod.MODID, "block/ore_extractor_side"),
                new ResourceLocation(NeverEnderOreMod.MODID, "block/ore_extractor_bottom"),
                new ResourceLocation(NeverEnderOreMod.MODID, "block/ore_extractor_top")));
    }
}
