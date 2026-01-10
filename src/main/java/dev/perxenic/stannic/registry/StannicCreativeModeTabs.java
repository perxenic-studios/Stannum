package dev.perxenic.stannic.registry;

import com.simibubi.create.AllCreativeModeTabs;
import dev.perxenic.stannic.registry.item.StannicItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static dev.perxenic.stannic.Stannic.MOD_ID;


public class StannicCreativeModeTabs {
    public static DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    public static DeferredHolder<CreativeModeTab, CreativeModeTab> STANNIC_TAB = CREATIVE_MODE_TABS.register("base",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.%s.base".formatted(MOD_ID)))
                    .withTabsBefore(AllCreativeModeTabs.BASE_CREATIVE_TAB.getKey())
                    .icon(() -> StannicItems.TIN_INGOT.asStack()) //TODO: Replace with actual item from the mod
                    .build());

    public static void register(IEventBus modEventBus) {
        CREATIVE_MODE_TABS.register(modEventBus);
    }
}
