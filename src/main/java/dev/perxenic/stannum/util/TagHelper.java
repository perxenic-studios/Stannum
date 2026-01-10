package dev.perxenic.stannum.util;

import com.simibubi.create.foundation.data.recipe.CommonMetal;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class TagHelper {
    public static TagKey<Item> itemTagPrefix(String dir, String path) {
        return ItemTags.create(cLoc(dir + path));
    }

    public static TagKey<Item> itemTagPrefix(String dir, ResourceLocation location) {
        return itemTagPrefix(dir, location.getPath());
    }

    public static TagKey<Block> blockTagPrefix(String dir, String path) {
        return BlockTags.create(cLoc(dir + path));
    }

    public static TagKey<Block> blockTagPrefix(String dir, ResourceLocation location) {
        return blockTagPrefix(dir, location.getPath());
    }

    public static CommonMetal.ItemLikeTag itemLikeTagPrefix(String dir, String path) {
        return new CommonMetal.ItemLikeTag(
                itemTagPrefix(dir, path),
                blockTagPrefix(dir, path)
        );
    }

    public static CommonMetal.ItemLikeTag itemLikeTagPrefix(String dir, ResourceLocation location) {
        return itemLikeTagPrefix(dir, location.getPath());
    }

    public static ResourceLocation cLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath("c", path);
    }
}
