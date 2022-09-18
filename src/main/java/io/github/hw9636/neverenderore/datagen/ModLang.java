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

        HashMap<String, String> oreTrans = new HashMap<>();

        oreTrans.put("aluminium", "Never Ender Aluminium Ore");
        oreTrans.put("cinnabar", "Never Ender Cinnabar Ore");
        oreTrans.put("coal", "Never Ender Coal Ore");
        oreTrans.put("copper", "Never Ender Copper Ore");
        oreTrans.put("diamond", "Never Ender Diamond Ore");
        oreTrans.put("emerald", "Never Ender Emerald Ore");
        oreTrans.put("gold", "Never Ender Gold Ore");
        oreTrans.put("iron", "Never Ender Iron Ore");
        oreTrans.put("lapis", "Never Ender Lapis Ore");
        oreTrans.put("lead", "Never Ender Lead Ore");
        oreTrans.put("nickel", "Never Ender Nickel Ore");
        oreTrans.put("niter", "Never Ender Tier Ore");
        oreTrans.put("oil_sand", "Never Ender Oil Sand Ore");
        oreTrans.put("osmium_ore", "Never Ender Osmium Ore");
        oreTrans.put("quartz", "Never Ender Quartz Ore");
        oreTrans.put("redstone", "Never Ender Redstone Ore");
        oreTrans.put("ruby", "Never Ender Ruby Ore");
        oreTrans.put("sapphire", "Never Ender Sapphire Ore");
        oreTrans.put("silver", "Never Ender Silver ore");
        oreTrans.put("sulfur", "Never Ender Sulfur Ore");
        oreTrans.put("tin", "Never Ender Tin Ore");
        oreTrans.put("uraninite", "Never Ender Uraninite Ore");
        oreTrans.put("uranium", "Never Ender Uranium Ore");

        oreTrans.forEach((k, v) -> add("block.neverenderore.never_ender_" + k + "_ore", v));

        HashMap<String, String> rawTrans = new HashMap<>();

        rawTrans.put("aluminium", "Raw Aluminium");
        rawTrans.put("cinnabar", "Raw Cinnabar");
        rawTrans.put("coal", "Raw Coal");
        rawTrans.put("diamond", "Raw Diamond");
        rawTrans.put("emerald", "Raw Emerald");
        rawTrans.put("lapis", "Raw Lapis");
        rawTrans.put("lead", "Raw Lead");
        rawTrans.put("nickel", "Raw Nickel");
        rawTrans.put("niter", "Raw Niter");
        rawTrans.put("oil_sand", "Raw Oil Sand");
        rawTrans.put("osmium", "Raw Osmium");
        rawTrans.put("quartz", "Raw Quartz");
        rawTrans.put("redstone", "Raw Redstone");
        rawTrans.put("ruby", "Raw Ruby");
        rawTrans.put("sapphire", "Raw Sapphire");
        rawTrans.put("silver", "Raw Silver");
        rawTrans.put("sulfur", "Raw Sulfur");
        rawTrans.put("tin", "Raw Tin");
        rawTrans.put("uraninite", "Raw Uraninite");
        rawTrans.put("uranium", "Raw Uranium");

        rawTrans.forEach((k, v) -> add("item.neverenderore.raw_never_ender_" + k, v));

        add("container.neverenderore.ore_extractor", "Ore Extractor");
        add("tooltip.neverenderore.state_ok", "State: OK (Using %s FE per Mine)");
        add("tooltip.neverenderore.state_no_energy", "State: Not Enough Energy");
        add("tooltip.neverenderore.state_invalid_block", "State: Not Valid Block To Mine");
        add("tooltip.neverenderore.state_storage_full", "State: Not Enough Storage");
        add("title.never_ender_ore.jei_category", "Ore Extracting");
    }
}
