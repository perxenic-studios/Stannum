package dev.perxenic.stannum.infra.data;

import com.simibubi.create.foundation.data.TagGen;
import com.simibubi.create.foundation.data.recipe.CommonMetal;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;

import static dev.perxenic.stannum.Stannum.REGISTRATE;

@SuppressWarnings("deprecation") //Built in registry holder is used by many mods, warning is not needed
public class StannumRegistrateTags {
    public static void addGenerators() {
        REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, StannumRegistrateTags::genBlockTags);
        REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, StannumRegistrateTags::genItemTags);
    }

    private static void genBlockTags(RegistrateTagsProvider<Block> provIn) {
        TagGen.CreateTagsProvider<Block> prov = new TagGen.CreateTagsProvider<>(provIn, Block::builtInRegistryHolder);
    }

    private static void genItemTags(RegistrateTagsProvider<Item> provIn) {
        TagGen.CreateTagsProvider<Item> prov = new TagGen.CreateTagsProvider<>(provIn, Item::builtInRegistryHolder);

        prov.tag(Tags.Items.RAW_MATERIALS)
                .addTag(CommonMetal.TIN.rawOres);

        prov.tag(Tags.Items.INGOTS)
                .addTag(CommonMetal.TIN.ingots);

        prov.tag(Tags.Items.NUGGETS)
                .addTag(CommonMetal.TIN.nuggets);

        prov.tag(ItemTags.BEACON_PAYMENT_ITEMS)
                .addTag(CommonMetal.TIN.ingots);
    }
}
