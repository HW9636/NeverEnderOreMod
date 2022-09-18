package io.github.hw9636.neverenderore.common.integration;

import io.github.hw9636.neverenderore.NeverEnderOreMod;
import io.github.hw9636.neverenderore.common.config.NEOConfig;
import io.github.hw9636.neverenderore.common.oreextractor.OreExtractorBlock;
import io.github.hw9636.neverenderore.common.oreextractor.OreExtractorBlockEntity;
import mcjty.theoneprobe.api.*;
import mcjty.theoneprobe.apiimpl.styles.ProgressStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Function;

public class TOPProvider implements IProbeInfoProvider, Function<ITheOneProbe, Void> {

    private static final ResourceLocation ID = new ResourceLocation(NeverEnderOreMod.MODID, "top_provider");

    private final ProgressStyle style;

    public TOPProvider() {
        style = new ProgressStyle();
        style.showText(false);
    }

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData iProbeHitData) {
        Block block = blockState.getBlock();

        if (block instanceof OreExtractorBlock) {
            if (level.getBlockEntity(iProbeHitData.getPos()) instanceof OreExtractorBlockEntity be) { // Sanity Check should always be OreExtractorBE
                iProbeInfo.progress(be.getProgress(), NEOConfig.COMMON.ticksPerAction.get(), style);

                switch (be.getState()) {
                    case OreExtractorBlockEntity.STATE_OK -> iProbeInfo.text(Component.translatable("tooltip.neverenderore.state_ok", NEOConfig.COMMON.ticksPerAction.get()));
                    case OreExtractorBlockEntity.STATE_INVALID_BLOCk -> iProbeInfo.text(Component.translatable("tooltip.neverenderore.state_invalid_block"));
                    case OreExtractorBlockEntity.STATE_STORAGE_FULL -> iProbeInfo.text(Component.translatable("tooltip.neverenderore.state_storage_full"));
                    case OreExtractorBlockEntity.STATE_NO_ENERGY -> iProbeInfo.text(Component.translatable("tooltip.neverenderore.state_no_energy"));
                }
            }
        }
    }

    @Override
    public Void apply(ITheOneProbe iTheOneProbe) {
        iTheOneProbe.registerProvider(this);

        return null;
    }
}
