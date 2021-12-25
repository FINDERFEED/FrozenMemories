package com.finderfeed.frozenmemories.misc;

import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.Offset;
import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.objectives.PlayerInventoryCheck;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;

public enum ProgressionState {
    STAGE_0(Offset.of(5,2,10),
            blocks(Blocks.IRON_ORE.defaultBlockState()),
            items(ItemWithQuantity.of(Items.IRON_PICKAXE,1),ItemWithQuantity.of(Items.COOKED_BEEF,16))
    );



    public static final Map<Integer,ProgressionState> STATES = Map.of(
            0,STAGE_0
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
