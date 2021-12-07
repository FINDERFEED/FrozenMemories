package com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system;

import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.LoreTileEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoreProgramStage {

    private static final int MESSAGE_SEND_TIME = 20;
    private LoreTileEntity tileEntity;
    private List<String> MESSAGES;
    private List<Objective> OBJECTIVES;
    private List<ScheduledTask> SCHEDULED_TASKS;
    private String id;
    private int tick = 0;
    private int messagesTime;
    private boolean completed = false;

    private int currentMessage = 0;

    private LoreProgramStage(Builder builder){
        OBJECTIVES = builder.OBJECTIVES;
        MESSAGES = builder.MESSAGES;
        SCHEDULED_TASKS = builder.SCHEDULED_TASKS;
        id = builder.id;
        messagesTime = (MESSAGES.size()-1)*MESSAGE_SEND_TIME;
        this.tileEntity = builder.tile;
    }

    public void tick(){
        Level world = tileEntity.getLevel();
        AABB box = LoreTileEntity.PLAYER_SEEK_AABB.move(tileEntity.getBlockPos());
        List<Player> players = world.getEntitiesOfClass(Player.class,box);
        if (!players.isEmpty()) {
            if (tick <= messagesTime) {
                if (tick % MESSAGE_SEND_TIME == 0) {
                    players.forEach((player)->{
                        player.sendMessage(new TextComponent(MESSAGES.get(currentMessage)),player.getUUID());
                    });
                    currentMessage++;
                }

            }else{
                if (world.getGameTime() % 20 == 0){
                    boolean shouldBeCompleted = true;
                    for (Objective objective : OBJECTIVES){
                        if (!objective.check()){
                            shouldBeCompleted = false;
                        }
                        break;
                    }
                    completed = shouldBeCompleted;
                }
            }
        }
        for (ScheduledTask task : SCHEDULED_TASKS){
            if (task.getType() == ScheduledTask.Type.SINGLE){
                if (task.getTick() == tick){
                    task.getRunnable().run(tileEntity);
                }
            }else if (task.getType() == ScheduledTask.Type.REPETITIVE){

                if ((tick != 0) && (tick % task.getTick() == 0)){
                    task.getRunnable().run(tileEntity);
                }
            }
        }
        tick++;
    }



    public boolean isCompleted() {
        return completed;
    }

    public void save(CompoundTag tag){
        tag.putInt(id+"tick",tick);
        tag.putInt(id+"currentMessage",currentMessage);
        tag.putBoolean(id+"completed",completed);
    }

    public void load(CompoundTag tag){
        this.tick = tag.getInt(id+"tick");
        this.currentMessage = tag.getInt(id+"currentMessage");
        this.completed = tag.getBoolean(id+"completed");
    }

    public static class Builder{

        private List<String> MESSAGES = new ArrayList<>();
        private List<Objective> OBJECTIVES = new ArrayList<>();
        private List<ScheduledTask> SCHEDULED_TASKS = new ArrayList<>();

        private String id;
        private LoreTileEntity tile;
        private Builder(){}

        public static Builder start(String id,LoreTileEntity tile){
            Builder b = new Builder();
            b.tile = tile;
            b.id = id;
            return b;
        }

        public Builder addScheduledTask(ScheduledTask task){
            SCHEDULED_TASKS.add(task);
            return this;
        }

        public Builder addMessages(String... mess){
            MESSAGES.addAll(Arrays.asList(mess));
            return this;
        }

        public Builder addObjectives(Objective... objs){
            OBJECTIVES.addAll(Arrays.asList(objs));
            return this;
        }

        public LoreProgramStage build(){
            return new LoreProgramStage(this);
        }

    }


    public static class ScheduledTask{

        private int tick;
        private ScheduledRunnable runnable;
        private Type type;

        private ScheduledTask(int tick,ScheduledRunnable runnable,Type type){
            this.tick = tick;
            this.runnable = runnable;
            this.type = type;
        }

        public static ScheduledTask create(Type type,int tick,ScheduledRunnable runnable){
            return new ScheduledTask(tick,runnable,type);
        }


        public Type getType() {
            return type;
        }

        public int getTick() {
            return tick;
        }

        public ScheduledRunnable getRunnable() {
            return runnable;
        }

        public enum Type{
            SINGLE,
            REPETITIVE
        }

    }

    @FunctionalInterface
    public interface ScheduledRunnable{
        void run(LoreTileEntity tile);
    }
}
