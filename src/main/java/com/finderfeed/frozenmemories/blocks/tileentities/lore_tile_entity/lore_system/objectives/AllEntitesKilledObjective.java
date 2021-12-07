package com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.objectives;

import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.LoreTileEntity;
import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.Objective;
import net.minecraft.world.entity.Entity;

public class AllEntitesKilledObjective extends Objective {

    private Class<Entity> clazz;

    public AllEntitesKilledObjective(LoreTileEntity tile, Class<Entity> entityClass) {
        super(tile);
        this.clazz = entityClass;
    }

    @Override
    public boolean check() {

        return false;
    }
}
