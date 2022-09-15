package io.github.hw9636.neverenderore.datagen;

import com.mojang.datafixers.util.Pair;
import io.github.hw9636.neverenderore.common.ModRegistration;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ModLootTables extends LootTableProvider {
    public ModLootTables(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        return List.of();
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext vt) {
        for (Map.Entry<ResourceLocation, LootTable> entry : map.entrySet())
            LootTables.validate(vt, entry.getKey(), entry.getValue());
    }

    public static class BlockLoot extends net.minecraft.data.loot.BlockLoot {

        @Override
        protected @NotNull Iterable<Block> getKnownBlocks() {
            List<Block> validBlocks = new java.util.ArrayList<>(ModRegistration.BLOCKS.getEntries().stream().map(RegistryObject::get).toList());
            validBlocks.addAll(ModRegistration.ORES.getEntries().stream().map(RegistryObject::get).toList());
            return validBlocks;
        }

        @Override
        protected void addTables() {
            dropSelf(ModRegistration.ORE_EXTRACTOR_BLOCK.get());

            ModRegistration.ORES.getEntries().stream().map(RegistryObject::get).forEach(this::dropSelf);
        }
    }
}
