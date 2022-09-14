package io.github.hw9636.neverenderore.common.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class NEOConfig {
    public static class Common
    {
        private static final int defaultMaxEnergyStored = 100_000;
        private static final int defaultTicksPerCraft = 20;
        private static final int defaultEnergyPerTick = 40;

        public final ForgeConfigSpec.ConfigValue<Integer> maxEnergyStored;
        public final ForgeConfigSpec.ConfigValue<Integer> ticksPerAction;
        public final ForgeConfigSpec.ConfigValue<Integer> energyPerAction;


        public Common(ForgeConfigSpec.Builder builder)
        {
            this.maxEnergyStored = builder.comment("Max Energy Stored, range 100 - 100,000,000, default 100,000")
                    .defineInRange("maxEnergyStored", defaultMaxEnergyStored, 100,100_000_000);

            this.ticksPerAction = builder.comment("Ticks per action in ticks, range 1 - 2048, default 20")
                    .defineInRange("ticksPerCraft", defaultTicksPerCraft, 1, 2048);

            this.energyPerAction = builder.comment("Energy per action, range 1 - 32000, default 200")
                    .defineInRange("energyPerTick", defaultEnergyPerTick, 1, 32000);
        }
    }

    public static final Common COMMON;
    public static final ForgeConfigSpec COMMON_SPEC;

    static //constructor
    {
        Pair<Common, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON = commonSpecPair.getLeft();
        COMMON_SPEC = commonSpecPair.getRight();
    }
}
