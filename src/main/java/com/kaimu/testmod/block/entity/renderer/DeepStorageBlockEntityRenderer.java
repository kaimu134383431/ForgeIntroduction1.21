package com.kaimu.testmod.block.entity.renderer;

import com.kaimu.testmod.block.entity.custom.DeepStorageBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;

public class DeepStorageBlockEntityRenderer implements BlockEntityRenderer<DeepStorageBlockEntity> {
    public DeepStorageBlockEntityRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(DeepStorageBlockEntity blockEntity, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack stack = blockEntity.storedItem;

        // 表示するアイテムが存在するかチェック
        if (stack.isEmpty()) return;

        poseStack.pushPose();
        poseStack.translate(0.5f, 0.5f, 0.5f); // 中心に移動
        poseStack.scale(1f, 1f, 1f);      // 小さめに表示
        poseStack.mulPose(Axis.YP.rotationDegrees(blockEntity.getRenderingRotation())); // 回転させる

        // 描画
        itemRenderer.renderStatic(
                stack,
                ItemDisplayContext.FIXED,
                getLightLevel(blockEntity.getLevel(), blockEntity.getBlockPos()),
                OverlayTexture.NO_OVERLAY,
                poseStack,
                bufferSource,
                blockEntity.getLevel(),
                0
        );

        poseStack.popPose();
    }


    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }
}