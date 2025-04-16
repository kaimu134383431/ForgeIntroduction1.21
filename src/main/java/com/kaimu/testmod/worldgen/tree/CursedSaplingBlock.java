package com.kaimu.testmod.worldgen.tree;

import com.kaimu.testmod.worldgen.features.TestFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;

public class CursedSaplingBlock extends SaplingBlock {
    public CursedSaplingBlock(BlockBehaviour.Properties properties) {
        super(null, properties);
    }

    @Override
    public void advanceTree(ServerLevel level, BlockPos pos, BlockState state, RandomSource random) {
        // 低確率で成長を妨げる
        if (random.nextFloat() < 0.6) { // 60% の確率で成長失敗
            level.setBlock(pos, state.cycle(STAGE), 4);
            return;
        }

        growTree(level, level.getChunkSource().getGenerator(), pos, state, random);
    }

    private boolean growTree(ServerLevel level, ChunkGenerator chunkGenerator, BlockPos pos, BlockState state, RandomSource random) {
        ResourceKey<ConfiguredFeature<?, ?>> featureKey = TestFeatures.CURSED_TREE_KEY;

        ConfiguredFeature<?, ?> configuredFeature = level.registryAccess()
                .registryOrThrow(Registries.CONFIGURED_FEATURE)
                .getHolder(featureKey)
                .orElseThrow()
                .value();

        BlockState blockState = level.getBlockState(pos);
        level.removeBlock(pos, false);

        if (configuredFeature.place(level, chunkGenerator, random, pos)) {
            // 呪いの木が生えたら周囲を汚染
            corruptSurroundings(level, pos, random);

            // 成長時に不気味な音を鳴らす
            level.playSound(null, pos, SoundEvents.WARDEN_NEARBY_CLOSE,
                    net.minecraft.sounds.SoundSource.BLOCKS, 1.0f, 0.5f);
            return true;
        } else {
            level.setBlock(pos, blockState, 4);
            return false;
        }
    }

    private void corruptSurroundings(ServerLevel level, BlockPos pos, RandomSource random) {
        for (int dx = -2; dx <= 2; dx++) {
            for (int dz = -2; dz <= 2; dz++) {
                BlockPos newPos = pos.offset(dx, -1, dz);
                BlockState blockState = level.getBlockState(newPos);
                if (blockState.is(Blocks.GRASS_BLOCK)) {
                    // 50% の確率で Podzol に変える
                    if (random.nextFloat() < 0.5) {
                        level.setBlock(newPos, Blocks.PODZOL.defaultBlockState(), 3);
                    }
                }
            }
        }
    }
}
