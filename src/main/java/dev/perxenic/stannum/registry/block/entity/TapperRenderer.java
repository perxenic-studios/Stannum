package dev.perxenic.stannum.registry.block.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import com.simibubi.create.foundation.blockEntity.renderer.SmartBlockEntityRenderer;
import dev.perxenic.stannum.registry.block.TapperBlock;
import net.createmod.catnip.platform.NeoForgeCatnipServices;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.util.Mth;
import net.neoforged.neoforge.fluids.FluidStack;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class TapperRenderer extends SmartBlockEntityRenderer<TapperBlockEntity> {
    public TapperRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(TapperBlockEntity tapper, float partialTicks, PoseStack poseStack, MultiBufferSource buffer,
                              int light, int overlay) {
        super.renderSafe(tapper, partialTicks, poseStack, buffer, light, overlay);

        poseStack.pushPose();

        poseStack.translate(8/16f,8/16f,8/16f);
        poseStack.mulPose(Axis.YP.rotationDegrees(180 - tapper.getBlockState().getValue(TapperBlock.FACING).toYRot()));
        poseStack.translate(-8/16f,-8/16f,-8/16f);

        SmartFluidTankBehaviour outputFluids = tapper.getBehaviour(SmartFluidTankBehaviour.OUTPUT);
        float totalUnits = tapper.getTotalFluidUnits(partialTicks);

        float fluidLevel = Mth.clamp(totalUnits / tapper.fluidCapability.getTankCapacity(0), 0, 1);

        final float xMin = 0.01f / 16f;
        final float xMax = 15.99f / 16f;
        final float yMin = 4.01f / 16f;
        final float yMax = yMin + 5.98f / 16f * fluidLevel;
        final float zMin = 10.01f / 16f;
        final float zMax = 15.99f / 16f;

        SmartFluidTankBehaviour.TankSegment tankSegment = outputFluids.getPrimaryTank();

        FluidStack renderedFluid = tankSegment.getRenderedFluid();
        if (renderedFluid.isEmpty() || tankSegment.getTotalUnits(partialTicks) < 1) {
            poseStack.popPose();
            return;
        }

        NeoForgeCatnipServices.FLUID_RENDERER.renderFluidBox(renderedFluid, xMin, yMin, zMin, xMax, yMax, zMax,
                buffer, poseStack, light, false, false);

        poseStack.popPose();
    }

    @Override
    public int getViewDistance() {
        return 32;
    }
}
