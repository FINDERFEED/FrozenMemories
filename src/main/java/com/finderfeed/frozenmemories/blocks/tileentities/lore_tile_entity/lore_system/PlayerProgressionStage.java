package com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class PlayerProgressionStage {

    public static final String TAG = "frozen_memories_player_progression_stage";

    public static void setPlayerProgressionStage(Player player,int stage){
        player.getPersistentData().putInt(TAG,stage);
    }

    public static int getPlayerProgressionStage(Player player){
        return player.getPersistentData().getInt(TAG);
    }

    public static void incrementPlayerProgressionStage(Player player){
        setPlayerProgressionStage(player,getPlayerProgressionStage(player)+1);
    }
    public static void decrementPlayerProgressionStage(Player player){
        setPlayerProgressionStage(player,getPlayerProgressionStage(player)-1);
    }


    public static void handleCloneEvent(PlayerEvent.Clone event){
        Player oldPlayer = event.getOriginal();
        Player newPlayer = event.getPlayer();
        setPlayerProgressionStage(newPlayer,getPlayerProgressionStage(oldPlayer));
    }



}
