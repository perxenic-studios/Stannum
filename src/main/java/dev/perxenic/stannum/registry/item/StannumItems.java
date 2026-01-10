package dev.perxenic.stannum.registry.item;

import com.simibubi.create.foundation.data.recipe.CommonMetal;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.Item;

import static dev.perxenic.stannum.Stannum.REGISTRATE;

public class StannumItems {
    public static ItemEntry<Item> RAW_TIN = REGISTRATE.item("raw_tin", Item::new)
            .tag(CommonMetal.TIN.rawOres)
            .register();

    public static ItemEntry<Item> TIN_INGOT = REGISTRATE.item("tin_ingot", Item::new)
            .tag(CommonMetal.TIN.ingots)
            .register();

    public static ItemEntry<Item> TIN_NUGGET = REGISTRATE.item("tin_nugget", Item::new)
            .tag(CommonMetal.TIN.nuggets)
            .register();

    // Load class
    public static void register() {
    }
}
