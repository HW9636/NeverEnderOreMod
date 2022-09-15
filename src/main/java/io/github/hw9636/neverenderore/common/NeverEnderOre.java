package io.github.hw9636.neverenderore.common;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class NeverEnderOre extends Block {
    public NeverEnderOre() {
        super(BlockBehaviour.Properties.copy(Blocks.OBSIDIAN));
    }
}
