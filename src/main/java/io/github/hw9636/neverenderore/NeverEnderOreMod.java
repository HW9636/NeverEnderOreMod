package io.github.hw9636.neverenderore;

import com.mojang.logging.LogUtils;
import io.github.hw9636.neverenderore.common.ModRegistration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(NeverEnderOreMod.MODID)
public class NeverEnderOreMod
{
    public static final String MODID = "neverenderore";
    private static final Logger LOGGER = LogUtils.getLogger();

    public NeverEnderOreMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        // Register Registry
        ModRegistration.BLOCKS.register(modEventBus);
        ModRegistration.ITEMS.register(modEventBus);
        ModRegistration.BLOCK_ENTITIES.register(modEventBus);
        ModRegistration.CONTAINERS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }

}
