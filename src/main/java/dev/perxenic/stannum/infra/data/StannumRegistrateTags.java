package dev.perxenic.stannum.infra.data;

import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
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

        MetalsStore.forEachMetal((location, metal) -> {
            prov.tag(Tags.Blocks.STORAGE_BLOCKS)
                    .addOptionalTag(metal.storageBlocks.blocks());

            if (metal.natural) {
                assert metal.ores != null;
                prov.tag(Tags.Blocks.ORES)
                        .addOptionalTag(metal.ores.blocks());

                assert metal.rawStorageBlocks != null;
                prov.tag(Tags.Blocks.ORES)
                        .addOptionalTag(metal.rawStorageBlocks.blocks());
            }
        });
    }

    private static void genItemTags(RegistrateTagsProvider<Item> provIn) {
        TagGen.CreateTagsProvider<Item> prov = new TagGen.CreateTagsProvider<>(provIn, Item::builtInRegistryHolder);

        MetalsStore.forEachMetal((location, metal) -> {
            prov.tag(Tags.Items.INGOTS)
                    .addOptionalTag(metal.ingots);

            prov.tag(Tags.Items.NUGGETS)
                    .addOptionalTag(metal.nuggets);

            prov.tag(Tags.Items.STORAGE_BLOCKS)
                    .addOptionalTag(metal.storageBlocks.items());

            if (metal.natural) {
                assert metal.ores != null;
                prov.tag(Tags.Items.ORES)
                        .addOptionalTag(metal.ores.items());

                assert metal.rawMetals != null;
                prov.tag(Tags.Items.RAW_MATERIALS)
                        .addOptionalTag(metal.rawMetals);

                assert metal.rawStorageBlocks != null;
                prov.tag(Tags.Items.ORES)
                        .addOptionalTag(metal.rawStorageBlocks.items());
            }
        });
    }
}
