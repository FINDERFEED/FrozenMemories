package com.finderfeed.frozenmemories.helpers;

import com.finderfeed.frozenmemories.FrozenMemories;
import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.PlayerProgressionStage;
import com.finderfeed.frozenmemories.packet_handler.PacketHandler;
import com.finderfeed.frozenmemories.packet_handler.packets.ClientboundUpdatePlayerStatePacket;
import com.finderfeed.frozenmemories.registries.ParticlesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkDirection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Helpers {

    public static final Random RANDOM = new Random();
    public static final String TAG_INVENTORY = "player_saved_inventory";
    public static final String TAG_RETURN_BLOCKPOS = "return_block_pos";

    public static final BlockPos NULL_BLOCK_POS = new BlockPos(0,-100,0);

    public static Vec3 blockCenter(BlockPos pos){
        return new Vec3(pos.getX()+0.5,pos.getY()+0.5,pos.getZ()+0.5);
    }

    public static void writeBlockPos(String id, BlockPos vec, CompoundTag tag){
        tag.putInt(id+"1",vec.getX());
        tag.putInt(id+"2",vec.getY());
        tag.putInt(id+"3",vec.getZ());
    }

    public static BlockPos getBlockPos(String id,CompoundTag tag){
        return new BlockPos(tag.getInt(id+"1"),tag.getInt(id+"2"),tag.getInt(id+"3"));
    }

    public static List<LevelChunk> getChunksInRadius(Level level,BlockPos pos,int radius){
        List<LevelChunk> chunks = new ArrayList<>();
        for (int i = -radius;i <= radius;i++){
            for (int g = -radius;g <= radius;g++){
                    chunks.add(level.getChunkAt(pos.offset(i*16,0,g*16)));
            }
        }
        return chunks;
    }

    public static LevelChunk[] getSurroundingChunks(Level level, BlockPos worldPosition){
        return new LevelChunk[]{level.getChunkAt(worldPosition),level.getChunkAt(worldPosition.offset(16,0,0)),level.getChunkAt(worldPosition.offset(0,0,16)),
                level.getChunkAt(worldPosition.offset(-16,0,0)),level.getChunkAt(worldPosition.offset(0,0,-16)),level.getChunkAt(worldPosition.offset(16,0,16)),
                level.getChunkAt(worldPosition.offset(-16,0,-16)),level.getChunkAt(worldPosition.offset(16,0,-16)),level.getChunkAt(worldPosition.offset(-16,0,16))};
    }


    public static StructureTemplate getStageStructureTemplate(ServerLevel world,int stage){
        return world.getStructureManager().getOrCreate(new ResourceLocation("frozenmemories:stage_structures/stage_"+stage));
    }

    public static int randomPlusMinus(){
        return RANDOM.nextInt(2) == 0 ? 1 : -1;
    }


    public static void updatePlayerStageOnClient(Player player){
        if (player instanceof ServerPlayer serverPlayer){
            int stage = PlayerProgressionStage.getPlayerProgressionStage(player);

            PacketHandler.INSTANCE.sendTo(new ClientboundUpdatePlayerStatePacket(stage),serverPlayer.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
        }else{
            throw new RuntimeException("Not a server player");
        }
    }

    public static void createSnowflakeParticleExplosion(Level world,Vec3 position,int intensity,float speedFactor,float spawnDistanceFactor){
        for (int x = -intensity; x < intensity+1;x++){
            for (int y = -intensity; y < intensity+1;y++){
                for (int z = -intensity; z < intensity+1;z++){
                    Vec3 offset = new Vec3(x,y,z).normalize().multiply(spawnDistanceFactor,spawnDistanceFactor,spawnDistanceFactor);
                    Vec3 finalpos = position.add(offset);
                    world.addParticle(ParticlesRegistry.SNOWFLAKE_PARTICLE.get(),finalpos.x,finalpos.y,finalpos.z,offset.x*speedFactor,offset.y*speedFactor,offset.z*speedFactor);

                }
            }
        }
    }
}
