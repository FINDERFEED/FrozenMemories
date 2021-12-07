package com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.objectives;

import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.LoreTileEntity;
import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.Objective;
import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.Offset;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class HarvestBlocksObjective extends Objective {

    private Offset[] offsets;
    private Block blockToCheck;

    public HarvestBlocksObjective(LoreTileEntity tile, Block block, Offset... offsets) {
        super(tile);
        this.offsets = offsets;
        this.blockToCheck = block;
    }

    @Override
    public boolean check() {
        for (Offset offset : offsets) {
            Level world = getTile().getLevel();
            BlockPos pos = offset.apply(getTile().getBlockPos());
            if (world.getBlockState(pos).getBlock() == blockToCheck) {
                return false;
            }
        }
        return true;
    }


    public Offset[] getOffsets() {
        return offsets;
    }

    public Block getBlockToCheck() {
        return blockToCheck;
    }
}
