package dev.perxenic.stannum.registry.block.entity;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import dev.perxenic.stannum.registry.block.StannumBlocks;

import static dev.perxenic.stannum.Stannum.REGISTRATE;

public class StannumBlockEntities {

    public static final BlockEntityEntry<TapperBlockEntity> TAPPER = REGISTRATE
            .blockEntity("tapper", TapperBlockEntity::new)
            .validBlocks(StannumBlocks.TAPPER)
            .renderer(() -> TapperRenderer::new)
            .register();

    // Load class
    public static void register() {
    }
}
