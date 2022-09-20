package io.github.hw9636.neverenderore.common.oreextractor;

import io.github.hw9636.neverenderore.common.ModRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class OreExtractorContainer extends AbstractContainerMenu {

    public static final int INVENTORY_SLOTS_START = 0;
    public static final int INVENTORY_SLOTS_END = 26;

    public static final int HOTBAR_SLOTS_START = 27;
    public static final int HOTBAR_SLOTS_END = 35;

    public OreExtractorContainer(int id, Inventory playerInv) {
        this(id, playerInv, BlockPos.ZERO, new SimpleContainerData(6), new ItemStackHandler(4));
    }

    private final ContainerLevelAccess containerAccess;
    public final ContainerData data;

    public OreExtractorContainer(int id, Inventory playerInv, BlockPos pos, ContainerData data, IItemHandler slots) {
        super(ModRegistration.ORE_EXTRACTOR_CONTAINER.get(), id);

        this.containerAccess = ContainerLevelAccess.create(playerInv.player.level, pos);
        this.data = data;

        addDataSlots(this.data);

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInv, k, 8 + k * 18, 142));
        }

        addSlot(new SlotItemHandler(slots, 0, 38, 26) { @Override public boolean mayPlace(@NotNull ItemStack stack) { return false; } });
        addSlot(new SlotItemHandler(slots, 1, 59, 26) { @Override public boolean mayPlace(@NotNull ItemStack stack) { return false; } });
        addSlot(new SlotItemHandler(slots, 2, 101, 26) { @Override public boolean mayPlace(@NotNull ItemStack stack) { return false; } });
        addSlot(new SlotItemHandler(slots, 3, 122, 26) { @Override public boolean mayPlace(@NotNull ItemStack stack) { return false; } });

        Optional<Block> currentlyMining = containerAccess.evaluate((level, blockPos) -> level.getBlockState(blockPos.below()).getBlock());

        if (currentlyMining.isPresent()) {
            SimpleContainer container = new SimpleContainer(new ItemStack(currentlyMining.get().asItem()));
            addSlot(new Slot(container, 0, 80, 26) {
                @Override
                public boolean mayPlace(@NotNull ItemStack pStack) {
                    return false;
                }

                @Override
                public boolean mayPickup(@NotNull Player pPlayer) {
                    return false;
                }
            });
        }
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        ItemStack returnStack = ItemStack.EMPTY;

        final Slot slot = getSlot(index);
        if (slot.hasItem()) {
            final ItemStack item = slot.getItem();
            returnStack = item.copy();
            if (index <= 35) {

                // Inventory slots
                if (index <= INVENTORY_SLOTS_END) {
                    if (!moveItemStackTo(item, HOTBAR_SLOTS_START, HOTBAR_SLOTS_END + 1, true))
                        return ItemStack.EMPTY;
                }
                else {
                    if (!moveItemStackTo(item, INVENTORY_SLOTS_START, INVENTORY_SLOTS_END + 1, false))
                        return ItemStack.EMPTY;
                }
            }
            else { // From Container
                if (!moveItemStackTo(item, INVENTORY_SLOTS_START, HOTBAR_SLOTS_END + 1, true))
                    return ItemStack.EMPTY;
            }

            if (item.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return returnStack;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return stillValid(containerAccess, player, ModRegistration.ORE_EXTRACTOR_BLOCK.get());
    }

    public static MenuConstructor getServerContainer(OreExtractorBlockEntity entity, BlockPos pos) {
        return (id, playerInv, player) -> new OreExtractorContainer(id, playerInv, pos,
                new OreExtractorContainerData(entity, 6), entity.itemHandler);
    }
}
