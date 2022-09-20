package io.github.hw9636.neverenderore.datagen;

import com.mojang.datafixers.util.Pair;
import io.github.hw9636.neverenderore.common.ModRegistration;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
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
    protected @NotNull List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        return List.of(Pair.of(BlockLoot::new, LootContextParamSets.BLOCK));
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, @NotNull ValidationContext vt) {
        for (Map.Entry<ResourceLocation, LootTable> entry : map.entrySet())
            LootTables.validate(vt, entry.getKey(), entry.getValue());
    }

    public static class BlockLoot extends net.minecraft.data.loot.BlockLoot {

        @Override
        protected @NotNull Iterable<Block> getKnownBlocks() {
            return List.of(ModRegistration.ORE_EXTRACTOR_BLOCK.get());
        }

        @Override
        protected void addTables() {
            dropSelf(ModRegistration.ORE_EXTRACTOR_BLOCK.get());
        }
    }
}
