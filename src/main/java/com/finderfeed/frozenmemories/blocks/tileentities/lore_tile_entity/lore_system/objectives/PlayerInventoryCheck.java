package com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.objectives;

import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.LoreTileEntity;
import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.Objective;
import com.finderfeed.frozenmemories.misc.ItemWithQuantity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.util.List;

public class PlayerInventoryCheck extends Objective {

    private ItemWithQuantity[] items;

    public PlayerInventoryCheck(String name, LoreTileEntity tile,ItemWithQuantity... items) {
        super(name, tile);
        this.items = items;
    }

    @Override
    public boolean check() {

        Player pl = getTile().getPlayer();
        for (ItemWithQuantity item : items){

            if (pl != null){
                if (pl.getInventory().countItem(item.getItem()) < item.getQuantity()){
                    return false;
                }
            }else{
                return false;
            }
        }

        return true;
    }


}
