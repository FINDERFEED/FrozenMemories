package com.finderfeed.frozenmemories.packet_handler;

import com.finderfeed.frozenmemories.FrozenMemories;
import com.finderfeed.frozenmemories.packet_handler.packets.ClientboundUpdatePlayerStatePacket;
import com.finderfeed.frozenmemories.packet_handler.packets.RequestPlayerStageUpdate;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class PacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(FrozenMemories.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );
    private static int ID = 0;
    public static int nextID() {
        return ID++;
    }

    public static void registerPackets(){
        INSTANCE.registerMessage(nextID(), ClientboundUpdatePlayerStatePacket.class,ClientboundUpdatePlayerStatePacket::toBytes,ClientboundUpdatePlayerStatePacket::new,ClientboundUpdatePlayerStatePacket::handle);
        INSTANCE.registerMessage(nextID(), RequestPlayerStageUpdate.class,RequestPlayerStageUpdate::toBytes,RequestPlayerStageUpdate::new,RequestPlayerStageUpdate::handle);

    }


}


//    private final int index;
//    public CastAbilityPacket(FriendlyByteBuf buf){
//
//        index = buf.readInt();
//
//    }
//    public CastAbilityPacket(int index){
//        this.index = index;
//    }
//    public void toBytes(FriendlyByteBuf buf){
//        buf.writeInt(index);
//    }
//    public void handle(Supplier<NetworkEvent.Context> ctx){
//        ctx.get().enqueueWork(()->{
//            ServerPlayer enti = ctx.get().getSender();
//            Player entity = (Player)enti;
//
//            SolarAbilities.castAbility(enti.getLevel(),enti,enti.getPersistentData().getString("solar_forge_ability_binded_"+Integer.toString(index)));
//        });
//        ctx.get().setPacketHandled(true);
//    }