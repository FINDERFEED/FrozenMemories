package com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system;

import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.LoreTileEntity;
import net.minecraft.nbt.CompoundTag;

public abstract class TickableObjective extends SaveableObjective{

    private int tick;

    public TickableObjective(String name, String saveid, LoreTileEntity tile) {
        super(name, saveid, tile);
    }


    public void tick(){
        tick++;
    }

    @Override
    public void save(CompoundTag tag) {
        tag.putInt(getSaveID()+"tick",tick);
    }

    public int getTick() {
        return tick;
    }

    @Override
    public void load(CompoundTag tag) {
        this.tick = tag.getInt(getSaveID()+"tick");
    }
}
