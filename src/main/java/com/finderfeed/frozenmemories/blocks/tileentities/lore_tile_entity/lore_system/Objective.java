package com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system;

import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.LoreTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public abstract class Objective {

    private LoreTileEntity tile;

    public Objective(LoreTileEntity tile){
        this.tile = tile;
    }

    public abstract boolean check();


    public LoreTileEntity getTile() {
        return tile;
    }


    public Level getWorld(){
        return tile.getLevel();
    }


    public BlockPos getTilePos(){
        return tile.getBlockPos();
    }
}
