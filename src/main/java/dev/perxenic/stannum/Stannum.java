package dev.perxenic.stannum;

import dev.perxenic.stannum.registry.StannumMetals;
import dev.perxenic.stannum.registry.StannumCreativeModeTabs;
import dev.perxenic.stannum.registry.block.StannumBlocks;
import dev.perxenic.stannum.registry.block.entity.StannumBlockEntities;
import dev.perxenic.stannum.registry.item.StannumItems;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;

@Mod(Stannum.MOD_ID)
public class Stannum {
    public static final String MOD_ID = "stannum";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MOD_ID)
            .defaultCreativeTab(StannumCreativeModeTabs.BASE.getKey());

    public Stannum(IEventBus modEventBus, ModContainer modContainer) {
        REGISTRATE.registerEventListeners(modEventBus);

        StannumMetals.register();

        StannumBlocks.register();
        StannumBlockEntities.register();
        StannumItems.register();
        StannumCreativeModeTabs.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    public static ResourceLocation snLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
