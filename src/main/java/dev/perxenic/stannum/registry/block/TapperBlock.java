package dev.perxenic.stannum.registry.block;

import com.mojang.serialization.MapCodec;
import com.simibubi.create.foundation.block.IBE;
import dev.perxenic.stannum.registry.block.entity.StannumBlockEntities;
import dev.perxenic.stannum.registry.block.entity.TapperBlockEntity;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TapperBlock extends BaseEntityBlock implements IBE<TapperBlockEntity> {
    public static final MapCodec<TapperBlock> CODEC = simpleCodec(TapperBlock::new);

    public TapperBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public Class<TapperBlockEntity> getBlockEntityClass() {
        return TapperBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends TapperBlockEntity> getBlockEntityType() {
        return StannumBlockEntities.TAPPER.get();
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof TapperBlockEntity tapperBlockEntity) {
                tapperBlockEntity.drops();
            }
        }

        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        // Add code to allow for interactions with held containers

        if (!level.isClientSide) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof TapperBlockEntity tapperBlockEntity) {
                player.openMenu(new SimpleMenuProvider(tapperBlockEntity, Component.translatable("container.stannum.tapper")), pos);
            } else {
                throw new IllegalStateException("Container provider is missing!");
            }
        }

        return ItemInteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (level.isClientSide) return null;

        return createTickerHelper(blockEntityType, StannumBlockEntities.TAPPER.get(), (tickerLevel, blockPos, blockState, blockEntity) -> {
            blockEntity.tick(tickerLevel, blockPos, blockState);
        });
    }
}
