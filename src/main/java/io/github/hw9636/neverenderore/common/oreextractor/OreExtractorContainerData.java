package io.github.hw9636.neverenderore.common.oreextractor;

import net.minecraft.world.inventory.SimpleContainerData;

public class OreExtractorContainerData extends SimpleContainerData {

    private final OreExtractorBlockEntity be;

    public OreExtractorContainerData(OreExtractorBlockEntity be, int size) {
        super(size);
        this.be = be;
    }

    @Override
    public int get(int pIndex) {
        return be.data.get(pIndex);
    }
}
