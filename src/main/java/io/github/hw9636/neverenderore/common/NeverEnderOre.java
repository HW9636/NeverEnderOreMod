package io.github.hw9636.neverenderore.common;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NeverEnderOre extends Block {
    public NeverEnderOre() {
        super(BlockBehaviour.Properties.copy(Blocks.STONE));
    }

    @Override
    public void playerDestroy(@NotNull Level level, @NotNull Player player, @NotNull BlockPos pos,
                              @NotNull BlockState state, @Nullable BlockEntity pBlockEntity, @NotNull ItemStack tool) {
        super.playerDestroy(level, player, pos, state, pBlockEntity, tool);

        if (!player.isCreative() && !tool.is(ModRegistration.ORE_REMOVER.get()) ) {
            if (state.is(this)) level.setBlockAndUpdate(pos, state);
        }
    }
}



