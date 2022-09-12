package io.github.hw9636.neverenderore.common;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class OreExtractorBlockEntity extends BlockEntity implements IEnergyStorage {

    private int energyStored;
    private ItemStackHandler itemHandler;
    private LazyOptional<IItemHandler> itemHandlerLazy;

    public OreExtractorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModRegistration.ORE_EXTRACTOR_BE.get(), pPos, pBlockState);

        this.itemHandler = createInventory();
        this.itemHandlerLazy = LazyOptional.of(() -> itemHandler);
        this.energyStored = 0;
    }

    public void tickServer() {
        
    }

    // Inventory

    private ItemStackHandler createInventory() {
        return new ItemStackHandler(4);
    }

    // Loading, Saving

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);

        itemHandler.deserializeNBT(tag.getCompound("Inventory"));
    }

    @Override
    public void onLoad() {
        super.onLoad();

        itemHandlerLazy = LazyOptional.of(() -> itemHandler);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);

        tag.put("Inventory", itemHandler.serializeNBT());
    }

    // Capabilities


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {

        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return itemHandlerLazy.cast();
        }

        if (cap == ForgeCapabilities.ENERGY) {
            return LazyOptional.of(() -> this).cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public int getEnergyStored() {
        return energyStored;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return 0;
    }

    @Override
    public int getMaxEnergyStored() {
        return 0;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        return 0;
    }

    @Override
    public boolean canExtract() {
        return false;
    }

    @Override
    public boolean canReceive() {
        return false;
    }
}
