package dev.perxenic.stannum.infra.data;

import com.simibubi.create.Create;
import com.simibubi.create.foundation.data.recipe.CommonMetal;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import static dev.perxenic.stannum.util.TagHelper.*;

public class MetalsStore {
    private static final Map<ResourceLocation, Metal> metalMap = new HashMap<>();

    public static Metal registerMetal(Metal metal) {
        metalMap.put(metal.location, metal);
        return metal;
    }

    public static Metal registerMetal(ResourceLocation location) {
        return registerMetal(new Metal(location));
    }

    public static void addCreateMetals() {
        for (CommonMetal createMetal : EnumSet.allOf(CommonMetal.class)) registerMetal(new Metal(createMetal));
    }

    public static Metal get(ResourceLocation location) {
        return metalMap.get(location);
    }

    public static class Metal {
        public final ResourceLocation location;

        public TagKey<Item> ingots;
        public TagKey<Item> nuggets;
        public TagKey<Item> plates;
        public CommonMetal.ItemLikeTag storageBlocks;

        public boolean natural = false;

        public @Nullable CommonMetal.ItemLikeTag ores;
        public @Nullable TagKey<Item> rawMetals;
        public @Nullable CommonMetal.ItemLikeTag rawStorageBlocks;

        public Metal(ResourceLocation location) {
            this.location = location;

            this.ingots = itemTagPrefix("ingots/", location);
            this.nuggets = itemTagPrefix("nuggets/", location);
            this.plates = itemTagPrefix("plates/", location);
            this.storageBlocks = itemLikeTagPrefix("storage_blocks/", location);
        }

        Metal(CommonMetal createMetal) {
            this.location = ResourceLocation.fromNamespaceAndPath(Create.ID, createMetal.name);

            this.ingots = createMetal.ingots;
            this.nuggets = createMetal.nuggets;
            this.plates = createMetal.plates;
            this.storageBlocks = createMetal.storageBlocks;

            if (createMetal.isNatural) makeNatural(
                    createMetal.ores,
                    createMetal.rawOres,
                    createMetal.rawStorageBlocks
            );
        }

        public void makeNatural(
                CommonMetal.ItemLikeTag ores,
                TagKey<Item> rawMetals,
                CommonMetal.ItemLikeTag rawStorageBlocks
        ) {
            this.natural = true;

            this.ores = ores;
            this.rawMetals = rawMetals;
            this.rawStorageBlocks = rawStorageBlocks;
        }

        public void makeNatural() {
            this.natural = true;
            makeNatural(
                    itemLikeTagPrefix("ores/", location),
                    itemTagPrefix("raw_materials/", location),
                    itemLikeTagPrefix("storage_blocks/raw_", location)
            );
        }
    }
}
