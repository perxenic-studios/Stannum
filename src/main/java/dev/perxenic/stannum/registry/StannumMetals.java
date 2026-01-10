package dev.perxenic.stannum.registry;

import dev.perxenic.stannum.infra.data.MetalsStore;
import net.minecraft.resources.ResourceLocation;

import static dev.perxenic.stannum.Stannum.snLoc;

public class StannumMetals {
    public static MetalsStore.Metal TIN;
    public static MetalsStore.Metal BRONZE = MetalsStore.registerMetal(snLoc("bronze"));

    public static void register() {
        MetalsStore.addCreateMetals();
        TIN = MetalsStore.get(ResourceLocation.fromNamespaceAndPath("create", "tin"));
    }
}
