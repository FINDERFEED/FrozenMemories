package com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.objectives;

import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.LoreTileEntity;
import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.Objective;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class AllEntitesKilledObjective extends Objective {

    private Class<? extends Entity> clazz;

    public AllEntitesKilledObjective(String name,LoreTileEntity tile, Class<? extends Entity> entityClass) {
        super(name,tile);
        this.clazz = entityClass;
    }

    @Override
    public boolean check() {
        Level world = this.getWorld();
        boolean a = world.getEntitiesOfClass(clazz, LoreTileEntity.PLAYER_SEEK_AABB.move(getTilePos())).isEmpty();
        return a;
    }
}
