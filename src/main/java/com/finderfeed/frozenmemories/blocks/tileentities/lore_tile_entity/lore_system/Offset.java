package com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;

public class Offset {

    private int x;
    private int y;
    private int z;

    private Offset(int x,int y,int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }


    public static Offset of(int x,int y,int z){
        return new Offset(x,y,z);
    }

    public BlockPos apply(BlockPos pos){
        return pos.offset(x,y,z);
    }

    public AABB apply(AABB aabb){
        return aabb.move(x,y,z);
    }
}
