package com.kaimu.testmod.screen.custom;

import com.kaimu.testmod.TestMod;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import com.kaimu.testmod.block.entity.custom.DeepStorageBlockEntity;
import net.minecraft.world.item.ItemStack;

public class DeepStorageScreen extends AbstractContainerScreen<DeepStorageMenu> {
    private static final ResourceLocation GUI_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(TestMod.MOD_ID, "textures/gui/deep_storage.png");

    public DeepStorageScreen(DeepStorageMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        pGuiGraphics.blit(GUI_TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderLabels(guiGraphics, mouseX, mouseY);

        int guiWidth = this.imageWidth - 16; // 左右端を少し余裕持たせるためのマージン
        int xPosition = 8; // GUI内のテキスト開始位置(x軸)

        guiGraphics.drawString(font, "in", 48, 55, 0x404040, false);
        guiGraphics.drawString(font, "out", 116, 55, 0x404040, false);

        if (menu.blockEntity.storedItem.isEmpty() || menu.blockEntity.itemCount <= 0) {
            String emptyText = "アイテム未収納";
            int emptyTextWidth = font.width(emptyText);
            guiGraphics.drawString(font, emptyText, (this.imageWidth - emptyTextWidth) / 2, 20, 0x404040, false);
            return;
        }

        String fullItemName = menu.blockEntity.storedItem.getDisplayName().getString();
        String displayItemName = clipTextToWidth(fullItemName, guiWidth);

        // スタック数＋残り個数表示
        long itemCount = menu.blockEntity.itemCount;
        int maxStack = menu.blockEntity.storedItem.getMaxStackSize();
        long stacks = itemCount / maxStack;
        long remaining = itemCount % maxStack;
        String stackAndCountText = String.format("(%dスタック + %d個)", stacks, remaining);

        // 純粋な個数表示
        String totalCountText = String.format("合計: %d 個", itemCount);

        // 満杯なら警告を追加
        if (itemCount >= DeepStorageBlockEntity.MAX_CAPACITY) {
            stackAndCountText += " (満杯です)";
        }

        // Y座標調整：複数行表示のため
        int yBase = 16;
        int lineSpacing = 9;

        guiGraphics.drawString(font, displayItemName, xPosition, yBase, 0x404040, false);
        guiGraphics.drawString(font, totalCountText + " " + stackAndCountText, xPosition, yBase + lineSpacing, 0x404040, false);

    }

    //指定された幅を超えないようにテキストを切り詰めて、省略記号(…)をつけるUtilityメソッド
    private String clipTextToWidth(String text, int maxWidth) {
        if (font.width(text) <= maxWidth) {
            return text; // 幅に収まるならそのまま返す
        }

        String ellipsis = "…";
        int ellipsisWidth = font.width(ellipsis);

        StringBuilder clippedText = new StringBuilder();
        for (char c : text.toCharArray()) {
            int widthIfAdded = font.width(clippedText.toString() + c) + ellipsisWidth;
            if (widthIfAdded > maxWidth) break;
            clippedText.append(c);
        }
        clippedText.append(ellipsis); // 省略記号を追加して返す

        return clippedText.toString();
    }


}