package dev.perxenic.stannum.registry.block;

import com.mojang.serialization.MapCodec;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.fluids.transfer.GenericItemEmptying;
import com.simibubi.create.content.fluids.transfer.GenericItemFilling;
import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.fluid.FluidHelper;
import com.simibubi.create.foundation.item.ItemHelper;
import dev.perxenic.stannum.Stannum;
import dev.perxenic.stannum.registry.block.entity.StannumBlockEntities;
import dev.perxenic.stannum.registry.block.entity.TapperBlockEntity;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TapperBlock extends BaseEntityBlock implements IBE<TapperBlockEntity>, IWrenchable {
    public static final MapCodec<TapperBlock> CODEC = simpleCodec(TapperBlock::new);

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public TapperBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_206840_1_) {
        super.createBlockStateDefinition(p_206840_1_.add(FACING));
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
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level worldIn, BlockPos pos) {
        return getBlockEntityOptional(worldIn, pos).map(TapperBlockEntity::getInputInventory)
                .map(ItemHelper::calcRedstoneFromInventory)
                .orElse(0);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        return onBlockEntityUseItemOn(level, pos, be -> {
            if (!stack.isEmpty()) {
                if (FluidHelper.tryFillItemFromBE(level, player, hand, stack, be)) {
                    Stannum.LOGGER.info("Tried to fill item!");
                    return ItemInteractionResult.SUCCESS;
                }

                if (GenericItemFilling.canItemBeFilled(level, stack)) {
                    Stannum.LOGGER.info("Item can be filled!");
                    return ItemInteractionResult.SUCCESS;
                }
                if (stack.getItem().equals(Items.SPONGE)) {
                    Stannum.LOGGER.info("Item is a sponge!");
                    IFluidHandler fluidHandler = level.getCapability(Capabilities.FluidHandler.BLOCK, pos, null);
                    if (fluidHandler != null) {
                        FluidStack drained = fluidHandler.drain(Integer.MAX_VALUE, IFluidHandler.FluidAction.EXECUTE);
                        if (!drained.isEmpty()) {
                            return ItemInteractionResult.SUCCESS;
                        }
                    }
                }
                return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
            }

            if (!level.isClientSide) {
                BlockEntity blockEntity = level.getBlockEntity(pos);
                if (blockEntity instanceof TapperBlockEntity tapperBlockEntity) {
                    player.openMenu(new SimpleMenuProvider(tapperBlockEntity, Component.translatable("block.stannum.tapper")), pos);
                } else {
                    throw new IllegalStateException("Container provider is missing!");
                }
            }

            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        });
    }
}
