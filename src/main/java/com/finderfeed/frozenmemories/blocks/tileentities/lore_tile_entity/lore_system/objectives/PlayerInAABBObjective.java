package com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.objectives;

import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.LoreTileEntity;
import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.Objective;
import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.Offset;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

public class PlayerInAABBObjective extends Objective {

    private AABB box;
    private Offset offset;

    public PlayerInAABBObjective(String name, LoreTileEntity tile, AABB box, Offset offset) {
        super(name, tile);
        this.box = box;
        this.offset = offset;
    }

    @Override
    public boolean check() {
        return !getWorld().getEntitiesOfClass(Player.class,offset.apply(box.move(getTilePos()))).isEmpty();
    }
}
