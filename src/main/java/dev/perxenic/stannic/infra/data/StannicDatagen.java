package dev.perxenic.stannic.infra.data;

import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber
public class StannicDatagen {
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void gatherDataHighPriority(GatherDataEvent event) {
        addExtraRegistrateData();
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        // Nothing atm
    }

    private static void addExtraRegistrateData() {
        StannicRegistrateTags.addGenerators();
    }
}
