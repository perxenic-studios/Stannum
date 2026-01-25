package dev.perxenic.stannum.registry.block.entity;


import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import com.simibubi.create.foundation.item.ItemHelper;
import com.simibubi.create.foundation.item.SmartInventory;
import com.simibubi.create.foundation.utility.CreateLang;
import dev.perxenic.stannum.util.StannumLang;
import net.createmod.catnip.data.Couple;
import net.createmod.catnip.lang.LangBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.wrapper.CombinedInvWrapper;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class TapperBlockEntity extends SmartBlockEntity implements MenuProvider, IHaveGoggleInformation {
    public final ItemStackHandler itemHandler = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            if (level == null) return;
            setChanged();
            if(!level.isClientSide) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    public SmartInventory inputInventory;
    protected SmartInventory outputInventory;
    protected SmartFluidTankBehaviour outputTank;
    private boolean contentsChanged;

    private Couple<SmartInventory> invs;

    protected IItemHandlerModifiable itemCapability;
    protected IFluidHandler fluidCapability;

    public TapperBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
        inputInventory = new SmartInventory(1, this);
        inputInventory.whenContentsChanged($ -> contentsChanged = true);
        outputInventory = new SmartInventory(1, this).forbidInsertion()
                .withMaxStackSize(64);

        itemCapability = new CombinedInvWrapper(inputInventory, outputInventory);
        contentsChanged = true; // Default contents changed to true to force update

        invs = Couple.create(inputInventory, outputInventory);
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                StannumBlockEntities.TAPPER.get(),
                (be, context) -> be.itemCapability
        );
        event.registerBlockEntity(
                Capabilities.FluidHandler.BLOCK,
                StannumBlockEntities.TAPPER.get(),
                (be, context) -> be.fluidCapability
        );
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        outputTank = new SmartFluidTankBehaviour(SmartFluidTankBehaviour.OUTPUT, this, 1, 4000, true)
                .whenFluidUpdates(() -> contentsChanged = true)
                .forbidInsertion();
        behaviours.add(outputTank);

        fluidCapability = outputTank.getCapability();
    }

    @Override
    protected void read(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(compound, registries, clientPacket);
        inputInventory.deserializeNBT(registries, compound.getCompound("InputItems"));
        outputInventory.deserializeNBT(registries, compound.getCompound("OutputItems"));
    }

    @Override
    public void write(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(compound, registries, clientPacket);
        compound.put("InputItems", inputInventory.serializeNBT(registries));
        compound.put("OutputItems", outputInventory.serializeNBT(registries));
    }

    @Override
    public void destroy() {
        super.destroy();
        ItemHelper.dropContents(level, worldPosition, inputInventory);
        ItemHelper.dropContents(level, worldPosition, outputInventory);
    }

    @Override
    public void invalidate() {
        super.invalidate();
        invalidateCapabilities();
    }

    @Override
    public void lazyTick() {
        super.lazyTick();

        if (level == null) return;

        if (!level.isClientSide) {
            notifyChangeOfContents();
        }
    }

    public boolean isEmpty() {
        return inputInventory.isEmpty() && outputInventory.isEmpty() && outputTank.isEmpty();
    }

    public void notifyChangeOfContents() {
        contentsChanged = true;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.stannum.tapper");
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        StannumLang.translate("gui.goggles.tapper_contents")
                .forGoggles(tooltip);

        if (itemCapability == null)
            itemCapability = new ItemStackHandler();
        if (fluidCapability == null)
            fluidCapability = new FluidTank(0);

        boolean isEmpty = true;

        for (int i = 0; i < itemCapability.getSlots(); i++) {
            ItemStack stackInSlot = itemCapability.getStackInSlot(i);
            if (stackInSlot.isEmpty())
                continue;
            StannumLang.text("")
                    .add(Component.translatable(stackInSlot.getDescriptionId())
                            .withStyle(ChatFormatting.GRAY))
                    .add(StannumLang.text(" x" + stackInSlot.getCount())
                            .style(ChatFormatting.GREEN))
                    .forGoggles(tooltip, 1);
            isEmpty = false;
        }

        LangBuilder mb = CreateLang.translate("generic.unit.millibuckets");
        for (int i = 0; i < fluidCapability.getTanks(); i++) {
            FluidStack fluidStack = fluidCapability.getFluidInTank(i);
            if (fluidStack.isEmpty())
                continue;
            StannumLang.text("")
                    .add(StannumLang.fluidName(fluidStack)
                            .add(StannumLang.text(" "))
                            .style(ChatFormatting.GRAY)
                            .add(StannumLang.number(fluidStack.getAmount())
                                    .add(mb)
                                    .style(ChatFormatting.BLUE)))
                    .forGoggles(tooltip, 1);
            isEmpty = false;
        }

        if (isEmpty)
            tooltip.remove(0);

        return true;
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return null;
    }

    public void drops() {
        if (level == null) return;

        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(level, worldPosition, inventory);
    }
}
