package com.finderfeed.frozenmemories.helpers;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;

public class Helpers {

    public static final BlockPos NULL_BLOCK_POS = new BlockPos(0,-100,0);

    public static void writeBlockPos(String id, BlockPos vec, CompoundTag tag){
        tag.putInt(id+"1",vec.getX());
        tag.putInt(id+"2",vec.getY());
        tag.putInt(id+"3",vec.getZ());
    }

    public static BlockPos getBlockPos(String id,CompoundTag tag){
        return new BlockPos(tag.getInt(id+"1"),tag.getInt(id+"2"),tag.getInt(id+"3"));
    }



}
