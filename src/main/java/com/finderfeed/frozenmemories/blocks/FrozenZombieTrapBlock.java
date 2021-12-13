package com.finderfeed.frozenmemories.blocks;

import com.finderfeed.frozenmemories.blocks.tileentities.FrozenZombieTile;
import com.finderfeed.frozenmemories.registries.TileEntitiesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class FrozenZombieTrapBlock extends Block implements EntityBlock {

    public FrozenZombieTrapBlock(Properties p_49795_) {
        super(p_49795_);
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return TileEntitiesRegistry.FROZEN_ZOMBIE_TRAP.get().create(pos,state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState kakayatostate, BlockEntityType<T> tip) {
        return (level,pos,state,tile)->{
            FrozenZombieTile.tick(level,pos,state,(FrozenZombieTile) tile);
        };
    }
}
