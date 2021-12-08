package com.finderfeed.frozenmemories.helpers;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.Vec3;

public class Helpers {

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

    public static LevelChunk[] getSurroundingChunks(Level level, BlockPos worldPosition){
        return new LevelChunk[]{level.getChunkAt(worldPosition),level.getChunkAt(worldPosition.offset(16,0,0)),level.getChunkAt(worldPosition.offset(0,0,16)),
                level.getChunkAt(worldPosition.offset(-16,0,0)),level.getChunkAt(worldPosition.offset(0,0,-16)),level.getChunkAt(worldPosition.offset(16,0,16)),
                level.getChunkAt(worldPosition.offset(-16,0,-16)),level.getChunkAt(worldPosition.offset(16,0,-16)),level.getChunkAt(worldPosition.offset(-16,0,16))};
    }



}
