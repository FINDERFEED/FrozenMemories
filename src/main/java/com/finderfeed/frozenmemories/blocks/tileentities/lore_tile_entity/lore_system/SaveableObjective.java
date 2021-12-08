package com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system;

import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.LoreTileEntity;
import net.minecraft.nbt.CompoundTag;

public abstract class SaveableObjective extends Objective{

    private String saveID;

    public SaveableObjective(String name,String saveid, LoreTileEntity tile) {
        super(name, tile);
        this.saveID = saveid;
    }


    public String getSaveID() {
        return saveID;
    }

    public abstract void save(CompoundTag tag);
    public abstract void load(CompoundTag tag);
}
