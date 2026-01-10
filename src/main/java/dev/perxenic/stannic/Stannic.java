package dev.perxenic.stannic;

import dev.perxenic.stannic.registry.StannicTabs;
import dev.perxenic.stannic.registry.item.StannicItems;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;

@Mod(Stannic.MOD_ID)
public class Stannic {
    public static final String MOD_ID = "stannic";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MOD_ID)
            .defaultCreativeTab(StannicTabs.STANNIC_TAB.getKey());

    public Stannic(IEventBus modEventBus, ModContainer modContainer) {
        REGISTRATE.registerEventListeners(modEventBus);

        StannicItems.register();
        StannicTabs.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
}
