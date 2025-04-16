// DeepStorageMenu.java
package com.kaimu.testmod.screen.custom;

import com.kaimu.testmod.registry.TestBlocks;
import com.kaimu.testmod.block.entity.custom.DeepStorageBlockEntity;
import com.kaimu.testmod.screen.TestMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class DeepStorageMenu extends AbstractContainerMenu {
    public final DeepStorageBlockEntity blockEntity;
    private final Level level;

    public DeepStorageMenu(int id, Inventory inv, FriendlyByteBuf buf) {
        this(id, inv, inv.player.level().getBlockEntity(buf.readBlockPos()));
    }

    public DeepStorageMenu(int id, Inventory inv, BlockEntity be) {
        super(TestMenuTypes.DEEP_STORAGE_MENU.get(), id);
        this.blockEntity = (DeepStorageBlockEntity) be;
        this.level = inv.player.level();

        // プレイヤーインベントリ
        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        // 入力スロット：mayPlace をオーバーライドして必ず「入る」と認識させる
        this.addSlot(new SlotItemHandler(blockEntity.inputHandler, 0, 44, 35) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return blockEntity.storedItem.isEmpty()
                        || ItemStack.isSameItemSameComponents(stack, blockEntity.storedItem);
            }

            @Override
            public void setChanged() {
                // `getItem()` を保持して、再帰呼び出しを避ける
                ItemStack current = getItem().copy();
                super.setChanged();

                if (!current.isEmpty()) {
                    blockEntity.inputHandler.insertItem(0, current, false);
                    super.set(ItemStack.EMPTY); // 再帰を起こさないように super 経由で呼ぶ
                }
            }
        });

        // 出力スロット：extractItem をきちんと通すようにする
        this.addSlot(new SlotItemHandler(blockEntity.outputHandler, 0, 116, 35) {
            @Override
            public @NotNull ItemStack remove(int amount) {
                // extractItem を必ず通すようにオーバーライド
                return blockEntity.outputHandler.extractItem(0, amount, false);
            }

            @Override
            public boolean mayPlace(ItemStack stack) {
                return false; // 出力には入れさせない
            }
        });

    }

    @Override
    public boolean stillValid(Player p) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                p, TestBlocks.DEEP_STORAGE_BLOCK.get());
    }

    // Shift＋クリックでの移動も inputSlot のみを狙う例
    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = this.slots.get(index);
        if (!slot.hasItem()) return ItemStack.EMPTY;

        ItemStack original = slot.getItem();
        ItemStack copy = original.copy();

        int inputIndex = 36;
        int outputIndex = 37;
        int containerSlots = 38; // 36個がプレイヤーインベントリ+ホットバー、2個が独自スロット

        if (index == outputIndex) {
            // ストレージからアイテムを1スタック分取り出して…
            ItemStack extracted = blockEntity.outputHandler.extractItem(0, Integer.MAX_VALUE, false);

            // それをプレイヤーのインベントリに移そうとする
            if (!moveItemStackTo(extracted, 0, 36, false)) {
                // 移動に失敗したら、アイテムは返却されてないので、手動で戻す
                blockEntity.inputHandler.insertItem(0, extracted, false);
                return ItemStack.EMPTY;
            }

            // スロット内容を更新
            slot.setChanged();
            return extracted;
        }
        else if (index >= 0 && index < 36) {
            // プレイヤーインベントリ → 入力スロット（直接insertItem経由で）
            ItemStack remaining = blockEntity.inputHandler.insertItem(0, original, false);
            if (remaining.getCount() == original.getCount())
                return ItemStack.EMPTY;
            slot.set(remaining);
            slot.setChanged();

        } else if (index == inputIndex) {
            // 入力スロット → プレイヤーインベントリへ
            if (!moveItemStackTo(original, 0, 36, true))
                return ItemStack.EMPTY;
            slot.setChanged();
        }

        return copy;
    }

    private void addPlayerInventory(Inventory inv) {
        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 9; col++)
                this.addSlot(new Slot(inv, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
    }
    private void addPlayerHotbar(Inventory inv) {
        for (int i = 0; i < 9; i++)
            this.addSlot(new Slot(inv, i, 8 + i * 18, 142));
    }
}
