package com.kaimu.testmod.block.custom;

import com.kaimu.testmod.block.entity.custom.DeepStorageBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class DeepStorageBlock extends BaseEntityBlock {
    public static final VoxelShape SHAPE = Shapes.block();
    public static final MapCodec<DeepStorageBlock> CODEC = simpleCodec(DeepStorageBlock::new);

    public DeepStorageBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public float getDestroyProgress(BlockState state, Player player, BlockGetter level, BlockPos pos) {
        BlockEntity entity = level.getBlockEntity(pos);

        if (entity instanceof DeepStorageBlockEntity dsbe) {
            // アイテムが収納されている場合は破壊できないように
            if (!dsbe.storedItem.isEmpty() && dsbe.itemCount > 0) {
                return 0.0f; // 0を返すと、採掘が進まない（破壊不可能になる）
                // または、0.0001fのような小さい値を返すときわめて遅くなります。
            }
        }

        // 収納が空の場合は通常の破壊速度（元の挙動を利用）
        return super.getDestroyProgress(state, player, level, pos);
    }

    @Override
    public void attack(BlockState state, Level level, BlockPos pos, Player player) {
        if (!level.isClientSide) {
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof DeepStorageBlockEntity deepStorage) {
                if (!deepStorage.storedItem.isEmpty() && deepStorage.itemCount > 0) {
                    player.displayClientMessage(
                            Component.translatable("message.deepstorage.cannot_destroy_not_empty"), true);
                }
            }
        }
        super.attack(state, level, pos, player);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof DeepStorageBlockEntity storage) {
            long count = storage.itemCount;
            if (count == 0) return 0;
            return Math.min(15, (int) Math.ceil(15.0 * count / Long.MAX_VALUE));
        }
        return 0;
    }



    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.BLOCK; // ピストンで動かない
    }

    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return true; // 太陽光などの光を通すようにする
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new DeepStorageBlockEntity(pPos, pState);
    }

    @Override
    protected void onRemove(BlockState pState, Level pLevel, BlockPos pPos,
                            BlockState pNewState, boolean pMovedByPiston) {
        if(pState.getBlock() != pNewState.getBlock()) {
            if(pLevel.getBlockEntity(pPos) instanceof DeepStorageBlockEntity deepStorageBlockEntity) {
                pLevel.updateNeighbourForOutputSignal(pPos, this);
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel,
                                              BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
        if(pLevel.getBlockEntity(pPos) instanceof DeepStorageBlockEntity deepStorageBlockEntity) {
            if(pPlayer.isCrouching()){
                return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
            }

            if(!pLevel.isClientSide()) {
                ((ServerPlayer) pPlayer).openMenu(new SimpleMenuProvider(deepStorageBlockEntity, Component.literal("Deep Storage")), pPos);
                return ItemInteractionResult.SUCCESS;
            }


        }

        return ItemInteractionResult.SUCCESS;
    }
}