package com.finderfeed.frozenmemories.blocks.tileentities;

import com.finderfeed.frozenmemories.registries.TileEntitiesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MemoryTowerTile extends BlockEntity {


    private List<UUID> ALREADY_USED_BY = new ArrayList<>();

    public MemoryTowerTile( BlockPos p_155229_, BlockState p_155230_) {
        super(TileEntitiesRegistry.MEMORY_TOWER.get(), p_155229_, p_155230_);
    }


    public void addUser(Player player){
        UUID uuid = player.getUUID();
        if (!ALREADY_USED_BY.contains(uuid)){
            ALREADY_USED_BY.add(uuid);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.putInt("size",ALREADY_USED_BY.size());
        for (int i = 0;i < ALREADY_USED_BY.size();i++){
            tag.putUUID("uuid"+i,ALREADY_USED_BY.get(i));
        }

        super.saveAdditional(tag);
    }


    @Override
    public void load(CompoundTag tag) {
        int size = tag.getInt("size");
        for (int i = 0;i < size;i++){
            UUID uuid = tag.getUUID("uuid"+i);
            if (!ALREADY_USED_BY.contains(uuid)){
                ALREADY_USED_BY.add(uuid);
            }
        }
        super.load(tag);
    }
}
