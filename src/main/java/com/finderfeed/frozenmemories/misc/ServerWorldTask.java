package com.finderfeed.frozenmemories.misc;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public class ServerWorldTask {


    private int remainingTicks;
    private Runnable task;
    private ResourceKey<Level> dimension;

    public ServerWorldTask(int ticks, ResourceKey<Level> dim,Runnable task){
        this.remainingTicks = ticks;
        this.task = task;
        this.dimension = dim;
    }

    public ResourceKey<Level> getDimension() {
        return dimension;
    }

    public int getRemainingTicks() {
        return remainingTicks;
    }

    public Runnable getTask() {
        return task;
    }

    public boolean tick(){
        return remainingTicks-- <= 0;
    }
}
