package io.github.hw9636.neverenderore.common;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.NotNull;

public class NeverEnderOre extends Block {
    public NeverEnderOre() {
        super(BlockBehaviour.Properties.copy(Blocks.STONE));
    }

    @Override
    public void destroy(@NotNull LevelAccessor pLevel, BlockPos pPos, BlockState pState) {

        if (pState.is(this)) {
            pLevel.setBlock(pPos, pState, 1);
        }
        boolean creative = pLevel.players().get(0).isCreative();
        boolean hasCorrectItem = pLevel.players().get(0).getMainHandItem().is(ModRegistration.ORE_REMOVER.get());

        if (pState.is(this) && creative || hasCorrectItem) {
            pLevel.setBlock(pPos, Blocks.AIR.defaultBlockState(),1);
        }
    }
}



