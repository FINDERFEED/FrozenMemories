package com.finderfeed.frozenmemories.misc;

import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.objectives.PlayerInventoryCheck;
import net.minecraft.world.item.Item;

public class ItemWithQuantity {
    private Item item;
    private int quantity;


    private ItemWithQuantity(int count,Item item){
        this.item = item;
        this.quantity = count;
    }

    public static ItemWithQuantity of(Item item, int count){
        return new ItemWithQuantity(count,item);
    }

    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }
}
