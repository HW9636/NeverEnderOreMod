package io.github.hw9636.neverenderore.common.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class NEOConfig {
    public static class Common
    {
        private static final int defaultMaxEnergyStored = 100_000;

        public final ForgeConfigSpec.ConfigValue<Integer> maxEnergyStored;


        public Common(ForgeConfigSpec.Builder builder)
        {
            this.maxEnergyStored = builder.comment("Max Energy Stored, range 100 - 100,000,000, default 100,000")
                    .defineInRange("maxEnergyStored", defaultMaxEnergyStored, 100,100_000_000);
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
