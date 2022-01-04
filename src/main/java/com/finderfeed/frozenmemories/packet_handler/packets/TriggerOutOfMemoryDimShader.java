package com.finderfeed.frozenmemories.packet_handler.packets;

import com.finderfeed.frozenmemories.helpers.ClientHelpers;
import com.finderfeed.frozenmemories.helpers.Helpers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class TriggerOutOfMemoryDimShader {

    public TriggerOutOfMemoryDimShader(){

    }

    public TriggerOutOfMemoryDimShader(FriendlyByteBuf buf){

    }

    public void toBytes(FriendlyByteBuf buf){

    }

    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(ClientHelpers::triggerOutOfDimPortalShader);
        ctx.get().setPacketHandled(true);
    }
}
