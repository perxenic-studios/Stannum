package dev.perxenic.stannum.registry.block;

import com.simibubi.create.foundation.data.recipe.CommonMetal;
import com.tterrag.registrate.util.entry.BlockEntry;
import dev.perxenic.stannum.registry.item.StannumItems;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.neoforged.neoforge.common.Tags;

import java.util.Map;

import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;
import static com.simibubi.create.foundation.data.TagGen.tagBlockAndItem;
import static dev.perxenic.stannum.Stannum.REGISTRATE;

public class StannumBlocks {
    public static BlockEntry<Block> TIN_ORE = REGISTRATE.block("tin_ore", Block::new)
            .initialProperties(() -> Blocks.COPPER_ORE)
            .transform(pickaxeOnly())
            .loot((lt, b) -> {
                HolderLookup.RegistryLookup<Enchantment> enchantLookup = lt.getRegistries().lookupOrThrow(Registries.ENCHANTMENT);
                Holder<Enchantment> fortune = enchantLookup.getOrThrow(Enchantments.FORTUNE);

                lt.add(b,
                        lt.createSilkTouchDispatchTable(b,
                                lt.applyExplosionDecay(b, LootItem.lootTableItem(StannumItems.RAW_TIN))
                                        .apply(ApplyBonusCount.addOreBonusCount(fortune))
                        )
                );
            })
            .tag(BlockTags.NEEDS_IRON_TOOL)
            .transform(tagBlockAndItem(Map.of(
                    Tags.Blocks.ORES, Tags.Items.ORES,
                    CommonMetal.TIN.ores.blocks(), CommonMetal.TIN.ores.items(),
                    Tags.Blocks.ORES_IN_GROUND_STONE, Tags.Items.ORES_IN_GROUND_STONE
            )))
            .build()
            .register();

    public static BlockEntry<Block> DEEPSLATE_TIN_ORE = REGISTRATE.block("deepslate_tin_ore", Block::new)
            .initialProperties(() -> Blocks.DEEPSLATE_COPPER_ORE)
            .transform(pickaxeOnly())
            .loot((lt, b) -> {
                HolderLookup.RegistryLookup<Enchantment> enchantLookup = lt.getRegistries().lookupOrThrow(Registries.ENCHANTMENT);
                Holder<Enchantment> fortune = enchantLookup.getOrThrow(Enchantments.FORTUNE);

                lt.add(b,
                        lt.createSilkTouchDispatchTable(b,
                                lt.applyExplosionDecay(b, LootItem.lootTableItem(StannumItems.RAW_TIN))
                                        .apply(ApplyBonusCount.addOreBonusCount(fortune))
                        )
                );
            })
            .tag(BlockTags.NEEDS_IRON_TOOL)
            .transform(tagBlockAndItem(Map.of(
                    Tags.Blocks.ORES, Tags.Items.ORES,
                    CommonMetal.TIN.ores.blocks(), CommonMetal.TIN.ores.items(),
                    Tags.Blocks.ORES_IN_GROUND_DEEPSLATE, Tags.Items.ORES_IN_GROUND_DEEPSLATE
            )))
            .build()
            .register();

    public static BlockEntry<Block> RAW_TIN_BLOCK = REGISTRATE.block("raw_tin_block", Block::new)
            .initialProperties(() -> Blocks.RAW_COPPER_BLOCK)
            .properties(p -> p.mapColor(MapColor.METAL))
            .transform(pickaxeOnly())
            .lang("Block of Raw Tin")
            .tag(BlockTags.NEEDS_IRON_TOOL)
            .transform(tagBlockAndItem(Map.of(
                    Tags.Blocks.STORAGE_BLOCKS, Tags.Items.STORAGE_BLOCKS,
                    CommonMetal.TIN.rawStorageBlocks.blocks(), CommonMetal.TIN.rawStorageBlocks.items()
            )))
            .build()
            .register();

    public static BlockEntry<Block> TIN_BLOCK = REGISTRATE.block("tin_block", Block::new)
            .initialProperties(() -> Blocks.COPPER_BLOCK)
            .properties(p -> p.mapColor(MapColor.METAL))
            .transform(pickaxeOnly())
            .lang("Block of Tin")
            .tag(BlockTags.NEEDS_IRON_TOOL)
            .transform(tagBlockAndItem(Map.of(
                    Tags.Blocks.STORAGE_BLOCKS, Tags.Items.STORAGE_BLOCKS,
                    CommonMetal.TIN.storageBlocks.blocks(), CommonMetal.TIN.storageBlocks.items()
            )))
            .build()
            .register();

    // Load class
    public static void register() {
    }
}
