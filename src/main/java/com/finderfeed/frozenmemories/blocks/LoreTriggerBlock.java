package com.finderfeed.frozenmemories.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LoreTriggerBlock extends Block {
    public LoreTriggerBlock(Properties p_49795_) {
        super(p_49795_);
    }


    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        if (world instanceof ServerLevel serverWorld){

        }
        return super.getCollisionShape(state, world, pos, ctx);
    }
}
