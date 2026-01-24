package dev.perxenic.stannum.registry.block.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class TapperBER implements BlockEntityRenderer<TapperBlockEntity> {
    public TapperBER(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(TapperBlockEntity tapperBlockEntity, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1) {

    }
}
