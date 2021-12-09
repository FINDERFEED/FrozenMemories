package com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system;

import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.LoreTileEntity;
import net.minecraft.nbt.CompoundTag;

public abstract class ObjectiveWithProgress extends SaveableObjective{

    private int maxProgress;
    private int currentProgress;

    public ObjectiveWithProgress(String name, String saveid, LoreTileEntity tile,int maxProgress) {
        super(name, saveid, tile);
        this.currentProgress = 0;
        this.maxProgress = maxProgress;
    }


    public void setCurrentProgress(int currentProgress) {
        this.currentProgress = currentProgress;
    }

    public int getMaxProgress() {
        return maxProgress;
    }


    public int getCurrentProgress() {
        return currentProgress;
    }

    @Override
    public void save(CompoundTag tag) {
        tag.putInt(getSaveID()+"progress",currentProgress);
    }

    @Override
    public void load(CompoundTag tag) {
        this.currentProgress = tag.getInt(getSaveID()+"progress");
    }
}
