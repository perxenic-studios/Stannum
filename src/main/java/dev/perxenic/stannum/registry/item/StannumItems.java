package dev.perxenic.stannum.registry.item;

import com.tterrag.registrate.util.entry.ItemEntry;
import dev.perxenic.stannum.registry.StannumMetals;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;

import static dev.perxenic.stannum.Stannum.REGISTRATE;

public class StannumItems {
    public static ItemEntry<Item> RAW_TIN = REGISTRATE.item("raw_tin", Item::new)
            .tag(StannumMetals.TIN.rawMetals)
            .register();

    public static ItemEntry<Item> TIN_INGOT = REGISTRATE.item("tin_ingot", Item::new)
            .tag(StannumMetals.TIN.ingots)
            .tag(ItemTags.BEACON_PAYMENT_ITEMS)
            .register();

    public static ItemEntry<Item> TIN_NUGGET = REGISTRATE.item("tin_nugget", Item::new)
            .tag(StannumMetals.TIN.nuggets)
            .register();

    public static ItemEntry<Item> BRONZE_INGOT = REGISTRATE.item("bronze_ingot", Item::new)
            .tag(StannumMetals.BRONZE.ingots)
            .tag(ItemTags.BEACON_PAYMENT_ITEMS)
            .register();

    public static ItemEntry<Item> BRONZE_NUGGET = REGISTRATE.item("bronze_nugget", Item::new)
            .tag(StannumMetals.BRONZE.nuggets)
            .register();

    // Load class
    public static void register() {
    }
}
