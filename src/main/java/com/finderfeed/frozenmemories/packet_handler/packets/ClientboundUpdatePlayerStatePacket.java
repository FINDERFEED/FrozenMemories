package com.finderfeed.frozenmemories.packet_handler.packets;

import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.Objective;
import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.PlayerProgressionStage;
import com.finderfeed.frozenmemories.helpers.ClientHelpers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Supplier;

public class ClientboundUpdatePlayerStatePacket {

    private int state;

    public ClientboundUpdatePlayerStatePacket(int state){
        this.state = state;
    }


    public ClientboundUpdatePlayerStatePacket(FriendlyByteBuf buf){
        this.state = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeInt(state);
    }
    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{
            PlayerProgressionStage.setPlayerProgressionStage(ClientHelpers.clientPlayer(),state);
        });
        ctx.get().setPacketHandled(true);
    }


}
