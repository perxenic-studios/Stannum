package dev.perxenic.stannic.registry.item;

import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.Tags;

import static dev.perxenic.stannic.Stannic.REGISTRATE;

public class StannicItems {
    public static ItemEntry<Item> TIN_INGOT = REGISTRATE.item("tin_ingot", Item::new)
            .tag(Tags.Items.INGOTS)
            .register();

    // Load class
    public static void register() {
    }
}
