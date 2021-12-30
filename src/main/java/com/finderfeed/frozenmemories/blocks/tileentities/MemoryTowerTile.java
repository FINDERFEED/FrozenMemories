package com.finderfeed.frozenmemories.blocks.tileentities;

import com.finderfeed.frozenmemories.blocks.MemoryCrack;
import com.finderfeed.frozenmemories.events.ForgeEventHandler;
import com.finderfeed.frozenmemories.registries.TileEntitiesRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MemoryTowerTile extends BlockEntity {


    private List<UUID> ALREADY_USED_BY = new ArrayList<>();
    private int remainingHP = 20;

    public MemoryTowerTile( BlockPos p_155229_, BlockState p_155230_) {
        super(TileEntitiesRegistry.MEMORY_TOWER.get(), p_155229_, p_155230_);
    }


    public void addUser(Player player){
        UUID uuid = player.getUUID();
        if (!ALREADY_USED_BY.contains(uuid)){
            ALREADY_USED_BY.add(uuid);
        }
    }
    public void damage(){
        if (remainingHP >= 0){
            remainingHP--;
        }
        level.getEntitiesOfClass(Player.class, new AABB(-48, -48, -48, 48, 48, 48).move(this.getBlockPos())).forEach((player) -> {
            player.sendMessage(new TextComponent("TOWER IS UNDER ATTACK!").withStyle(ChatFormatting.RED),player.getUUID());
            player.sendMessage(new TextComponent("REMAINING HP: " + remainingHP).withStyle(ChatFormatting.RED),player.getUUID());
        });
    }

    public int getRemainingHP() {
        return remainingHP;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.putInt("size",ALREADY_USED_BY.size());
        for (int i = 0;i < ALREADY_USED_BY.size();i++){
            tag.putUUID("uuid"+i,ALREADY_USED_BY.get(i));
        }
        tag.putInt("remaininghp",remainingHP);

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
        this.remainingHP = tag.getInt("remaininghp");
        super.load(tag);
    }
}
