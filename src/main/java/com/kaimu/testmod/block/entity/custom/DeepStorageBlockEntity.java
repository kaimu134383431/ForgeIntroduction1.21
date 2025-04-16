// DeepStorageBlockEntity.java
package com.kaimu.testmod.block.entity.custom;

import com.kaimu.testmod.block.entity.TestBlockEntities;
import com.kaimu.testmod.screen.custom.DeepStorageMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.minecraftforge.items.ItemStackHandler;

public class DeepStorageBlockEntity extends BlockEntity implements MenuProvider {
    // 種類・個数を管理するフィールド
    public ItemStack storedItem = ItemStack.EMPTY;
    public long itemCount = 0;

    public static final long MAX_CAPACITY = 2_147_483_647L; // 約2.1G (intの最大値)

    // 入力ハンドラ
    public final ItemStackHandler inputHandler = new ItemStackHandler(1) {
        @Override
        protected int getStackLimit(int slot, @NotNull ItemStack stack) {
            return stack.getMaxStackSize();
        }

        @Override
        @NotNull
        public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            if (stack.isEmpty()) return stack;

            long current = itemCount;
            long incoming = stack.getCount();

            if (storedItem.isEmpty()) {
                if (simulate) return ItemStack.EMPTY;
                if (incoming > MAX_CAPACITY) incoming = MAX_CAPACITY;

                storedItem = stack.copyWithCount(1);
                itemCount = incoming;
                setChanged();
                fillOutputSlot();
                markUpdated();
                return ItemStack.EMPTY;
            }

            if (ItemStack.isSameItemSameComponents(storedItem, stack)) {
                if (current >= MAX_CAPACITY) return stack; // もうこれ以上入らない！

                long accepted = Math.min(incoming, MAX_CAPACITY - current);

                if (!simulate) {
                    itemCount += accepted;
                    setChanged();
                    fillOutputSlot();
                    markUpdated();
                }

                return accepted == incoming ? ItemStack.EMPTY : stack.copyWithCount((int)(incoming - accepted));
            }

            return stack; // 別アイテムは拒否
        }

    };

    // 出力ハンドラ
    public final ItemStackHandler outputHandler = new ItemStackHandler(1) {
        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return false;
        }

        @NotNull
        @Override
        public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            return stack;
        }

        @NotNull
        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            if (storedItem.isEmpty() || itemCount <= 0) return ItemStack.EMPTY;
            int max = storedItem.getMaxStackSize();
            int take = (int)Math.min(amount, Math.min(max, itemCount));
            ItemStack result = storedItem.copyWithCount(take);
            if (!simulate) {
                itemCount -= take;
                if (itemCount == 0) storedItem = ItemStack.EMPTY;
                setChanged();
                System.out.println("[DeepStorage] 取り出し: " + result + " 残り=" + itemCount);
            }
            fillOutputSlot();
            markUpdated(); // ← 同期メソッドを呼ぶ！
            return result;
        }
    };

    private LazyOptional<ItemStackHandler> inputLazy = LazyOptional.of(() -> inputHandler);
    private LazyOptional<ItemStackHandler> outputLazy = LazyOptional.of(() -> outputHandler);

    /** outputHandler を正しく埋める */
    public void fillOutputSlot() {
        if (!storedItem.isEmpty() && itemCount > 0) {
            int size = (int)Math.min(storedItem.getMaxStackSize(), itemCount);
            outputHandler.setStackInSlot(0, storedItem.copyWithCount(size));
        } else {
            outputHandler.setStackInSlot(0, ItemStack.EMPTY);
        }
    }

    public DeepStorageBlockEntity(BlockPos pos, BlockState state) {
        super(TestBlockEntities.DEEP_STORAGE_BE.get(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider regs) {
        super.saveAdditional(tag, regs);
        if (!storedItem.isEmpty() && itemCount > 0) { // itemCountチェックを追加
            tag.put("StoredItem", storedItem.save(regs, new CompoundTag()));
            tag.putLong("ItemCount", itemCount);
        } else {
            tag.remove("StoredItem"); // 空状態なら削除（明示的な消去）
            tag.remove("ItemCount");
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider regs) {
        super.loadAdditional(tag, regs);
        if (tag.contains("StoredItem")) {
            storedItem = ItemStack.parse(regs, tag.getCompound("StoredItem")).orElse(ItemStack.EMPTY);
            itemCount = tag.getLong("ItemCount");
        } else {
            storedItem = ItemStack.EMPTY;
            itemCount = 0;
        }
        fillOutputSlot();
    }

    /**
     * クライアントとの情報同期用メソッド
     */
    public void markUpdated() {
        if (level != null && !level.isClientSide) {
            setChanged();
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
    }

    @Override
    public void onLoad() {
        super.onLoad();
        fillOutputSlot(); // 再ロード時、必ず最新の状態を保証
        markUpdated(); // クライアントを強制同期させる（推奨）
    }


    @Override
    public Component getDisplayName() {
        return Component.literal("Deep Storage");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
        return new DeepStorageMenu(id, inv, this);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider regs) {
        return saveWithoutMetadata(regs);
    }

    private float rotation;

    public float getRenderingRotation() {
        rotation += 0.5f;
        if(rotation >= 360f) rotation = 0;
        return rotation;
    }



    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (!this.remove && cap == ForgeCapabilities.ITEM_HANDLER) {
            if (side == Direction.DOWN) {
                return outputLazy.cast(); // 下からは取り出し専用
            } else {
                return inputLazy.cast(); // それ以外は搬入専用
            }
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        inputLazy.invalidate();
        outputLazy.invalidate();
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        inputLazy = LazyOptional.of(() -> inputHandler);
        outputLazy = LazyOptional.of(() -> outputHandler);
    }
}
