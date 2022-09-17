package io.github.hw9636.neverenderore.common;

import net.minecraft.world.item.Item;

public class RawNeverEnderItem extends Item {
    public RawNeverEnderItem() {
        super(new Item.Properties().fireResistant().tab(ModRegistration.MOD_TAB));
    }
}
