package com.finderfeed.frozenmemories.packet_handler.packets;

import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.Objective;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class TransferObjectivesToClientPacket {

    private List<Boolean> states;
    private List<String> messages;

    public TransferObjectivesToClientPacket(Boolean[] state,Objective... objectives){
        states = Arrays.asList(state);
        messages = new ArrayList<>();
        for (Objective objective : objectives){
            messages.add(objective.getName());
        }
    }


    public TransferObjectivesToClientPacket(FriendlyByteBuf buf){
        int size = buf.readInt();
        states = new ArrayList<>();
        messages = new ArrayList<>();

        for (int i = 0;i < size;i++){
            messages.add(buf.readUtf());
            states.add(buf.readBoolean());
        }
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeInt(messages.size());
        for (int i = 0; i < messages.size();i++){
            buf.writeUtf(messages.get(i));
            buf.writeBoolean(states.get(i));
        }
    }
    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{

        });
        ctx.get().setPacketHandled(true);
    }




}
