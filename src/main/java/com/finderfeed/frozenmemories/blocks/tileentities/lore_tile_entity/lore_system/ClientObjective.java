package com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system;

public class ClientObjective {
    private boolean isCompleted;
    private String name;
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
}
