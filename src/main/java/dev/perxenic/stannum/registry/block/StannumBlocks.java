package dev.perxenic.stannum.registry.block;

import com.simibubi.create.AllTags;
import com.tterrag.registrate.util.entry.BlockEntry;
import dev.perxenic.stannum.registry.StannumMetals;
import dev.perxenic.stannum.registry.item.StannumItems;
import net.minecraft.client.renderer.RenderType;
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

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;
import static com.simibubi.create.foundation.data.TagGen.tagBlockAndItem;
import static dev.perxenic.stannum.Stannum.REGISTRATE;

public class StannumBlocks {
    public static BlockEntry<Block> TIN_ORE;
    public static BlockEntry<Block> DEEPSLATE_TIN_ORE;
    public static BlockEntry<Block> RAW_TIN_BLOCK;
    public static BlockEntry<Block> TIN_BLOCK;

    public static BlockEntry<Block> BRONZE_BLOCK;

    public static BlockEntry<TapperBlock> TAPPER;

    public static void register() {
        // Tin is known to be natural so ores and raw storage blocks will never be null
        assert StannumMetals.TIN.natural;
        assert StannumMetals.TIN.ores != null;
        assert StannumMetals.TIN.rawStorageBlocks != null;

        TIN_ORE = REGISTRATE.block("tin_ore", Block::new)
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
                        StannumMetals.TIN.ores.blocks(), StannumMetals.TIN.ores.items(),
                        Tags.Blocks.ORES_IN_GROUND_STONE, Tags.Items.ORES_IN_GROUND_STONE
                )))
                .build()
                .register();

        DEEPSLATE_TIN_ORE = REGISTRATE.block("deepslate_tin_ore", Block::new)
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
                        StannumMetals.TIN.ores.blocks(), StannumMetals.TIN.ores.items(),
                        Tags.Blocks.ORES_IN_GROUND_DEEPSLATE, Tags.Items.ORES_IN_GROUND_DEEPSLATE
                )))
                .build()
                .register();

        RAW_TIN_BLOCK = REGISTRATE.block("raw_tin_block", Block::new)
                .initialProperties(() -> Blocks.RAW_COPPER_BLOCK)
                .properties(p -> p.mapColor(MapColor.METAL))
                .transform(pickaxeOnly())
                .lang("Block of Raw Tin")
                .tag(BlockTags.NEEDS_IRON_TOOL)
                .transform(tagBlockAndItem(Map.of(
                        StannumMetals.TIN.rawStorageBlocks.blocks(), StannumMetals.TIN.rawStorageBlocks.items()
                )))
                .build()
                .register();

        TIN_BLOCK = REGISTRATE.block("tin_block", Block::new)
                .initialProperties(() -> Blocks.COPPER_BLOCK)
                .properties(p -> p.mapColor(MapColor.METAL))
                .transform(pickaxeOnly())
                .lang("Block of Tin")
                .tag(BlockTags.NEEDS_IRON_TOOL)
                .transform(tagBlockAndItem(Map.of(
                        StannumMetals.TIN.storageBlocks.blocks(), StannumMetals.TIN.storageBlocks.items()
                )))
                .build()
                .register();

        BRONZE_BLOCK = REGISTRATE.block("bronze_block", Block::new)
                .initialProperties(() -> Blocks.IRON_BLOCK)
                .properties(p -> p.mapColor(MapColor.GOLD))
                .transform(pickaxeOnly())
                .lang("Block of Bronze")
                .tag(BlockTags.NEEDS_IRON_TOOL)
                .transform(tagBlockAndItem(Map.of(
                        StannumMetals.BRONZE.storageBlocks.blocks(), StannumMetals.BRONZE.storageBlocks.items()
                )))
                .build()
                .register();

        TAPPER = REGISTRATE.block("tapper", TapperBlock::new)
                .properties(p -> p.noOcclusion())
                .transform(pickaxeOnly())
                .blockstate(new TapperGenerator()::generate)
                .tag(BlockTags.NEEDS_STONE_TOOL)
                .tag(AllTags.AllBlockTags.WRENCH_PICKUP.tag)
                .item()
                .build()
                .register();
    }
}
