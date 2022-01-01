package com.finderfeed.frozenmemories.misc;

import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.Offset;
import com.finderfeed.frozenmemories.registries.BlocksRegistry;
import com.finderfeed.frozenmemories.registries.ItemsRegistry;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;

public enum ProgressionState {
    STAGE_1(Offset.of(5,2,10),
            blocks(Blocks.IRON_ORE.defaultBlockState(), BlocksRegistry.HOT_CHOCOLATE_BLOCK.get().defaultBlockState()),
            items(ItemWithQuantity.of(Items.IRON_PICKAXE,1),ItemWithQuantity.of(Items.COOKED_BEEF,16))
    ),
    STAGE_2(Offset.of(8,18,12),
            blocks(),
            items(ItemWithQuantity.of(Items.IRON_PICKAXE,1),ItemWithQuantity.of(Items.COOKED_BEEF,16),ItemWithQuantity.of(Items.RAW_IRON,5))),
    STAGE_3(Offset.of(10,18,1),
            blocks(),
            items(ItemWithQuantity.of(Items.IRON_PICKAXE,1),ItemWithQuantity.of(Items.COOKED_BEEF,16),ItemWithQuantity.of(Items.RAW_IRON,5))),
    STAGE_4(Offset.of(8,18,13),
            blocks(),
            items(ItemWithQuantity.of(Items.IRON_PICKAXE,1),ItemWithQuantity.of(Items.COOKED_BEEF,16),
                    ItemWithQuantity.of(ItemsRegistry.FROZEN_IRON_INGOT.get(),5))),
    STAGE_5(Offset.of(10,18,1),
    blocks(Blocks.COBBLESTONE.defaultBlockState()),
    items(ItemWithQuantity.of(Items.IRON_PICKAXE,1),ItemWithQuantity.of(Items.COOKED_BEEF,16),
                    ItemWithQuantity.of(ItemsRegistry.FROZEN_IRON_SWORD.get(),1))),
    STAGE_6(Offset.of(4,8,1),
            blocks(),
            items(ItemWithQuantity.of(Items.IRON_PICKAXE,1),ItemWithQuantity.of(Items.COOKED_BEEF,16),
                    ItemWithQuantity.of(ItemsRegistry.FROZEN_IRON_SWORD.get(),1))),
    STAGE_7(Offset.of(1,2,8),
            blocks(),
            items(ItemWithQuantity.of(Items.IRON_PICKAXE,1),ItemWithQuantity.of(Items.COOKED_BEEF,16),
                    ItemWithQuantity.of(ItemsRegistry.FROZEN_IRON_SWORD.get(),1),ItemWithQuantity.of(ItemsRegistry.FROZEN_IRON_CHESTPLATE.get(),1))),
    STAGE_8(Offset.of(1,9,24),
            blocks(),
            items(ItemWithQuantity.of(Items.IRON_PICKAXE,1),ItemWithQuantity.of(Items.COOKED_BEEF,32),
                    ItemWithQuantity.of(ItemsRegistry.FROZEN_IRON_SWORD.get(),1),ItemWithQuantity.of(ItemsRegistry.FROZEN_IRON_CHESTPLATE.get(),1),
                    ItemWithQuantity.of(Items.GOLDEN_APPLE,4))),
    ;



    public static final int ONE = 1;
    public static final int TWO = 2;
    public static final int THREE = 3;
    public static final int FOUR = 4;
    public static final int FIVE = 5;
    public static final int SIX = 6;
    public static final int SEVEN = 7;
    public static final int EIGHT = 7;

    public static final Map<Integer,ProgressionState> STATES = Map.of(
            0, STAGE_1,
            1, STAGE_2,
            2, STAGE_3,
            3, STAGE_4,
            4, STAGE_5,
            5, STAGE_6,
            6, STAGE_7,
            7, STAGE_8
    );


    private Offset offset;
    private ItemWithQuantity[] items;
    private BlockState[] allowedBlocks;


    ProgressionState(Offset playerTpOffset,BlockState[] allowedBlocksToBreak,ItemWithQuantity[] itemsToGive){
        this.offset = playerTpOffset;
        this.items = itemsToGive;
        this.allowedBlocks = allowedBlocksToBreak;
    }



    public BlockState[] getAllowedBlocks() {
        return allowedBlocks;
    }

    public ItemWithQuantity[] getItems() {
        return items;
    }

    public Offset getOffset() {
        return offset;
    }

    private static ItemWithQuantity[] items(ItemWithQuantity... items){
        return items;
    }

    private static BlockState[] blocks(BlockState... state){
        return state;
    }
}
