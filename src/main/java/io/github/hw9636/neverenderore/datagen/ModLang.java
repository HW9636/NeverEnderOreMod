package io.github.hw9636.neverenderore.datagen;

import io.github.hw9636.neverenderore.NeverEnderOreMod;
import io.github.hw9636.neverenderore.common.ModRegistration;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.HashMap;

public class ModLang extends LanguageProvider {
    public ModLang(DataGenerator gen, String locale) {
        super(gen, NeverEnderOreMod.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        addBlock(ModRegistration.ORE_EXTRACTOR_BLOCK, "Ore Extractor");
        addItem(ModRegistration.ORE_REMOVER, "Ore Remover");

        HashMap<String, String> translations = new HashMap<>();

        translations.put("cinnabar", "Never Ender Cinnabar Ore");
        translations.put("coal", "Never Ender Coal Ore");
        translations.put("copper", "Never Ender Copper Ore");
        translations.put("diamond", "Never Ender Diamond Ore");
        translations.put("emerald", "Never Ender Emerald Ore");
        translations.put("gold", "Never Ender Gold Ore");
        translations.put("iron", "Never Ender Iron Ore");
        translations.put("lapis", "Never Ender Lapis Ore");
        translations.put("lead", "Never Ender Lead Ore");
        translations.put("nickel", "Never Ender Nickel Ore");
        translations.put("niter", "Never Ender Tier Ore");
        translations.put("oil_sand", "Never Ender Oil Sand Ore");
        translations.put("osmium_ore", "Never Ender Osmium Ore");
        translations.put("quartz", "Never Ender Quartz Ore");
        translations.put("redstone", "Never Ender Redstone Ore");
        translations.put("ruby", "Never Ender Ruby Ore");
        translations.put("sapphire", "Never Ender Sapphire Ore");
        translations.put("silver", "Never Ender Silver ore");
        translations.put("sulfur", "Never Ender Sulfur Ore");
        translations.put("tin", "Never Ender Tin Ore");
        translations.put("uranium", "Never Ender Uranium Ore");

        translations.forEach((k, v) -> add("block.neverenderore.never_ender_" + k + "_ore", v));
    }
}
