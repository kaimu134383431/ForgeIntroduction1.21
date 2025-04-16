package com.kaimu.testmod.registry;

import com.kaimu.testmod.TestMod;
import com.kaimu.testmod.block.custom.DeepStorageBlock;
import com.kaimu.testmod.block.custom.TestLeavesBlock;
import com.kaimu.testmod.block.custom.TestLogBlock;
import com.kaimu.testmod.block.custom.TestStrippableLogBlock;
import com.kaimu.testmod.worldgen.tree.CursedSaplingBlock;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class TestBlocks {
    //レジストリを作成
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, TestMod.MOD_ID);
    //ブロックを作成&レジストリに登録
    public static final RegistryObject<Block> SALT_BLOCK = registerBlockWithItem("salt_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.COAL_BLOCK).sound(SoundType.AMETHYST)));

    public static final RegistryObject<Block> ORIHALCON_BLOCK = registerBlockWithItem("orihalcon_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_BLOCK).sound(SoundType.ANVIL)));

    public static final RegistryObject<Block> RAW_ORIHALCON_BLOCK = registerBlockWithItem("raw_orihalcon_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_BLOCK).sound(SoundType.ANVIL)));

    public static final RegistryObject<Block> ORIHALCON_ORE = registerBlockWithItem("orihalcon_ore",
            () -> new DropExperienceBlock(UniformInt.of(3, 7),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_ORE)));

    public static final RegistryObject<Block> DEEPSLATE_ORIHALCON_ORE = registerBlockWithItem("deepslate_orihalcon_ore",
            () -> new DropExperienceBlock(UniformInt.of(3, 7),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_DIAMOND_ORE)));

    public static final RegistryObject<Block> STRIPPED_CURSED_LOG = registerBlockWithItem("stripped_cursed_log",
            () -> new TestLogBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG).sound(SoundType.BONE_BLOCK)));

    public static final RegistryObject<Block> STRIPPED_CURSED_WOOD = registerBlockWithItem("stripped_cursed_wood",
            () -> new TestLogBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD).sound(SoundType.BONE_BLOCK)));

    public static final RegistryObject<Block> CURSED_LOG = registerBlockWithItem("cursed_log",
            () -> new TestStrippableLogBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).sound(SoundType.BONE_BLOCK),
                    STRIPPED_CURSED_LOG));

    public static final RegistryObject<Block> CURSED_WOOD = registerBlockWithItem("cursed_wood",
            () -> new TestStrippableLogBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD).sound(SoundType.BONE_BLOCK),
                    STRIPPED_CURSED_WOOD));

    public static final RegistryObject<Block> CURSED_LEAVES = registerBlockWithItem("cursed_leaves",
            () -> new TestLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)));


    // 板材
    public static final RegistryObject<Block> CURSED_PLANKS = registerBlockItem(
            "cursed_planks",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
    // ハーフブロック
    public static final RegistryObject<Block> CURSED_SLAB = registerBlockItem(
            "cursed_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
    // 階段
    public static final RegistryObject<Block> CURSED_STAIRS = registerBlockItem(
            "cursed_stairs",
            () -> new StairBlock(
                    TestBlocks.CURSED_PLANKS.get().defaultBlockState(),  // 直接 BlockState を渡す
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)
            )
    );


    // フェンス
    public static final RegistryObject<Block> CURSED_FENCE = registerBlockItem(
            "cursed_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));

    // フェンスゲート
    public static final RegistryObject<Block> CURSED_FENCE_GATE = registerBlockItem(
            "cursed_fence_gate",
            () -> new FenceGateBlock(WoodType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));

    // ドア
    public static final RegistryObject<Block> CURSED_DOOR = registerBlockItem(
            "cursed_door",
            () -> new DoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));

    // トラップドア
    public static final RegistryObject<Block> CURSED_TRAPDOOR = registerBlockItem(
            "cursed_trapdoor",
            () -> new TrapDoorBlock(BlockSetType.OAK,
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).noOcclusion()));

    // ボタン
    public static final RegistryObject<Block> CURSED_BUTTON = registerBlockItem(
            "cursed_button",
            () -> new ButtonBlock(BlockSetType.OAK, 30, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));

    // 感圧板
    public static final RegistryObject<Block> CURSED_PRESSURE_PLATE = registerBlockItem(
            "cursed_pressure_plate",
            () -> new PressurePlateBlock(BlockSetType.OAK,
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));

    // 苗木
    public static final RegistryObject<Block> CURSED_SAPLING = registerBlockItem(
            "cursed_sapling",
            () -> new CursedSaplingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)));


    public static final RegistryObject<Block> DEEP_STORAGE_BLOCK = registerBlockItem("deep_storage_block",
            () -> new DeepStorageBlock(BlockBehaviour.Properties.of().strength(1.0F, 3600000.0F)
                    .noOcclusion()
                    .sound(SoundType.GLASS)
                    .isSuffocating((state, reader, pos) -> false) // プレイヤーが窒息しない
                    .isViewBlocking((state, reader, pos) -> false) // 向こう側が見える
                    .lightLevel(state -> 0) // 明るさ変化を無効
                     ));

    /* ブロックアイテム作成用メソッド */
    private static <T extends Block> RegistryObject<T> registerBlockItem(String name,
                                                                         Supplier<T> supplier) {
        // レジストリにブロックを追加
        RegistryObject<T> block = BLOCKS.register(name, supplier);
        // ブロックアイテムをアイテムレジストリに追加
        TestItems.ITEMS.register(name,
                () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }


    private static <T extends Block> RegistryObject<T> registerBlockWithItem(String name, Supplier<T> supplier){
        //ブロックレジストリにブロックを登録
        RegistryObject<T> block = BLOCKS.register(name, supplier);
        //アイテムレジストリにBlockItemを登録
        TestItems.ITEMS.register(name,
                () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    //イベントバスに登録
    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}