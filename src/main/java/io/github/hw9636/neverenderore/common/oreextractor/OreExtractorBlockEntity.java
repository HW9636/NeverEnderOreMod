package io.github.hw9636.neverenderore.common.oreextractor;

import io.github.hw9636.neverenderore.common.ModRegistration;
import io.github.hw9636.neverenderore.common.config.NEOConfig;
import io.github.hw9636.neverenderore.common.recipe.NeverEnderRecipe;
import io.github.hw9636.neverenderore.common.recipe.NeverEnderRecipeType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
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

import java.util.Optional;

public class OreExtractorBlockEntity extends BlockEntity implements IEnergyStorage {

    // States
    public static final int STATE_OK = 0;
    public static final int STATE_INVALID_BLOCk = 1;
    public static final int STATE_NO_ENERGY = 2;
    public static final int STATE_STORAGE_FULL = 3;

    private int energyStored, progress, state;
    private boolean canInsert;
    ItemStackHandler itemHandler;
    private LazyOptional<IItemHandler> itemHandlerLazy;
    final ContainerData data;

    public OreExtractorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModRegistration.ORE_EXTRACTOR_BE.get(), pPos, pBlockState);

        this.itemHandler = createInventory();
        this.itemHandlerLazy = LazyOptional.of(() -> itemHandler);
        this.energyStored = 0;
        this.progress = 0;
        this.canInsert = false;
        this.data = getData();
        this.state = STATE_INVALID_BLOCk;
    }

    public void tickServer() {
        if (level == null || level.isClientSide) return; // Only server-side ticks

        if (this.energyStored <= 0) return; // Reduce calculations when no energy

        if (++this.progress >= NEOConfig.COMMON.ticksPerAction.get()) {
            this.progress = 0;

            if (this.energyStored >= NEOConfig.COMMON.energyPerAction.get()) {
                BlockState blockBelow = level.getBlockState(worldPosition.below());
                Optional<NeverEnderRecipe> recipe = level.getRecipeManager().getAllRecipesFor(NeverEnderRecipeType.INSTANCE).stream()
                        .filter(r -> blockBelow.is(r.getValidBlock())).findAny();

                if (recipe.isPresent()) {
                    this.state = STATE_OK;
                    this.energyStored -= NEOConfig.COMMON.ticksPerAction.get();
                     if (!insertItem(recipe.get().getResultItem()).isEmpty())
                         this.state = STATE_STORAGE_FULL;
                }
                else this.state = STATE_INVALID_BLOCk;
            }
            else this.state = STATE_NO_ENERGY;
        }
    }


    // Data Sync

    private ContainerData getData() {
        return new ContainerData() {
            @Override
            public int get(int index) {

                // 0 and 1 are both energy
                return switch (index) {
                    case 0 -> energyStored >> 16;
                    case 1 -> energyStored & 0xffff;
                    case 2 -> progress;
                    case 3 -> state;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> energyStored = energyStored & 0xffff | (value << 16);
                    case 1 -> energyStored = (energyStored >> 16 << 16) | value;
                    case 2 -> progress = value;
                    case 3 -> state = value;
                }
            }

            @Override
            public int getCount() {
                return 4;
            }
        };
    }

    // Inventory

    private ItemStack insertItem(ItemStack item) {
        if (itemHandlerLazy.isPresent()) {
            IItemHandler inv = itemHandlerLazy.orElse(null);
            for (int i = 0;i<inv.getSlots();i++) {
                canInsert = true;
                item = inv.insertItem(i, item.copy(), false);
                if (item.isEmpty()) return ItemStack.EMPTY;
            }
        }

        return item;
    }

    private ItemStackHandler createInventory() {
        return new ItemStackHandler(4) {
            @Override
            public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
                if (!canInsert) return stack; // No inserting unless specified

                if (!simulate) OreExtractorBlockEntity.this.setChanged();
                return super.insertItem(slot, stack, simulate);
            }

            @Override
            public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
                if (!simulate) OreExtractorBlockEntity.this.setChanged();
                return super.extractItem(slot, amount, simulate);
            }
        };
    }

    // Loading, Saving

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);

        energyStored = tag.getInt("EnergyStored");
        progress = tag.getInt("Progress");
        itemHandler.deserializeNBT(tag.getCompound("Inventory"));
    }

    @Override
    public void onLoad() {
        super.onLoad();

        itemHandlerLazy = LazyOptional.of(() -> itemHandler);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        return serializeNBT();
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
        load(tag);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        handleUpdateTag(pkt.getTag());
    }


    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);

        tag.putInt("EnergyStored", this.energyStored);
        tag.putInt("Progress", this.progress);
        tag.put("Inventory", this.itemHandler.serializeNBT());
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
        return this.energyStored;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return maxExtract;
    }

    @Override
    public int getMaxEnergyStored() {
        return NEOConfig.COMMON.maxEnergyStored.get();
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int toReceive = Math.min(NEOConfig.COMMON.maxEnergyStored.get() - energyStored, maxReceive);
        if (!simulate) {
            this.energyStored += toReceive;
            this.requestModelDataUpdate();
        }

        return toReceive;
    }

    @Override
    public boolean canExtract() {
        return false;
    }

    @Override
    public boolean canReceive() {
        return this.energyStored < NEOConfig.COMMON.maxEnergyStored.get();
    }

    public int getProgress() {
        return progress;
    }

    public int getState() {
        return state;
    }
}
