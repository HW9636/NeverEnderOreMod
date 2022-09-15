package io.github.hw9636.neverenderore.datagen;

import io.github.hw9636.neverenderore.NeverEnderOreMod;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NeverEnderOreMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        generator.addProvider(event.includeServer(), new ModRecipes(generator));
        generator.addProvider(event.includeServer(), new ModLootTables(generator));
        ModBlockTags blockTags = new ModBlockTags(generator, event.getExistingFileHelper());
        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(), new ModItemTags(generator, blockTags, event.getExistingFileHelper()));
        generator.addProvider(event.includeClient(), new ModBlockStates(generator, event.getExistingFileHelper()));
        generator.addProvider(event.includeClient(), new ModItemModels(generator, event.getExistingFileHelper()));
        generator.addProvider(event.includeClient(), new ModLang(generator, "en_us"));
    }
}