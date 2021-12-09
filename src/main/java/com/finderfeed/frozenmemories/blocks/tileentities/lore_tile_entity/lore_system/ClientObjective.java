package com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system;

public class ClientObjective {
    private boolean isCompleted;
    private String name;
    private int maxProgress = -1;
    private int currentProgress = -1;
    public ClientObjective(String name, boolean isCompleted){
        this.isCompleted = isCompleted;
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public boolean isCompleted() {
        return isCompleted;
    }


    public void setProgress(int maxProgress,int currentProgress) {
        this.maxProgress = maxProgress;
        this.currentProgress = currentProgress;
    }

    public int getCurrentProgress() {
        return currentProgress;
    }

    public int getMaxProgress() {
        return maxProgress;
    }
}
