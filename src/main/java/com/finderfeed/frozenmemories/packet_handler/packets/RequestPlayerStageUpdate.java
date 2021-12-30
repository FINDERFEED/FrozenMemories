package com.finderfeed.frozenmemories.packet_handler.packets;

import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.PlayerProgressionStage;
import com.finderfeed.frozenmemories.helpers.ClientHelpers;
import com.finderfeed.frozenmemories.helpers.Helpers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RequestPlayerStageUpdate {


    public RequestPlayerStageUpdate(){

    }

    public RequestPlayerStageUpdate(FriendlyByteBuf buf){

    }

    public void toBytes(FriendlyByteBuf buf){

    }

    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{
            ServerPlayer player = ctx.get().getSender();
            Helpers.updatePlayerStageOnClient(player);
        });
        ctx.get().setPacketHandled(true);
    }


}
