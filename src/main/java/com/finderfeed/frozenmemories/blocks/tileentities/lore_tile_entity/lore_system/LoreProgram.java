package com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system;

import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.LoreTileEntity;
import net.minecraft.nbt.CompoundTag;

import java.util.ArrayList;
import java.util.List;

public class LoreProgram {

    private List<LoreProgramStage> STAGES = new ArrayList<>();
    private int currentStage = -1;
    private LoreTileEntity tileEntity;
    private String id;
    private boolean isStageInProgress = false;
    private boolean completed = false;
    private LoreProgram(Builder builder){
        this.tileEntity = builder.tileEntity;
        this.STAGES = builder.STAGES;
        this.id = builder.id;
    }

    public void tick(){
        if (currentStage != -1) {
            if (currentStage < STAGES.size()) {
                LoreProgramStage stage = STAGES.get(currentStage);
                if (stage.isCompleted()) {
                    isStageInProgress = false;
                } else {
                    isStageInProgress = true;
                    stage.tick();
                }
            }else{
                this.completed = true;
            }
        }
    }

    public List<LoreProgramStage> getStages() {
        return STAGES;
    }

    public int getCurrentStage() {
        return currentStage;
    }

    public boolean isStageRunning() {
        return isStageInProgress;
    }

    public void nextStage(){
//        if (currentStage < STAGES.size()-1 ){
            currentStage++;
//        }
    }

    public boolean isCompleted() {
        return completed;
    }

    public void save(CompoundTag tag){
        for (LoreProgramStage stage : getStages()){
            stage.save(tag);
        }
        tag.putBoolean(id+"completed",completed);
        tag.putBoolean(id+"stageRunning",isStageInProgress);
        tag.putInt(id+"currentStage",currentStage);
    }

    public void load(CompoundTag tag){
        for (LoreProgramStage stage : getStages()){
            stage.load(tag);
        }
        completed = tag.getBoolean(id+"completed");
        currentStage = tag.getInt(id+"currentStage");
        isStageInProgress = tag.getBoolean(id+"stageRunning");
    }

    public static class Builder{

        private List<LoreProgramStage> STAGES = new ArrayList<>();
        private LoreTileEntity tileEntity;
        private String id;
        private Builder(String id,LoreTileEntity tile){
            this.tileEntity = tile;
            this.id = id;
        }

        public static Builder start(String id,LoreTileEntity tileEntity){
            return new Builder(id,tileEntity);
        }

        public Builder addStage(LoreProgramStage stage){
            this.STAGES.add(stage);
            return this;
        }

        public LoreProgram build(){
            return new LoreProgram(this);
        }


    }
}
