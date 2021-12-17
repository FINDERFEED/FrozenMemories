package com.finderfeed.frozenmemories.blocks.tileentities;

import com.finderfeed.frozenmemories.registries.TileEntitiesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class MemoryTowerTile extends BlockEntity {
    public MemoryTowerTile( BlockPos p_155229_, BlockState p_155230_) {
        super(TileEntitiesRegistry.MEMORY_TOWER.get(), p_155229_, p_155230_);
    }




}
