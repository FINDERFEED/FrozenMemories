package com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.objectives;

import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.LoreTileEntity;
import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.TickableObjective;

public class SurviveForTicksObjective extends TickableObjective {

    private final int surviveTime;

    public SurviveForTicksObjective(String name, String saveid, LoreTileEntity tile,int time) {
        super(name, saveid, tile);
        this.surviveTime = time;
    }

    @Override
    public boolean check() {
        return getTick() >= surviveTime;
    }
}
