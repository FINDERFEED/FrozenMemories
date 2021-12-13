package com.finderfeed.frozenmemories.blocks.tileentities;

import com.finderfeed.frozenmemories.entities.FrostedZombie;
import com.finderfeed.frozenmemories.helpers.Helpers;
import com.finderfeed.frozenmemories.registries.EntitiesRegistry;
import com.finderfeed.frozenmemories.registries.TileEntitiesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.function.Predicate;

public class FrozenZombieTile extends BlockEntity {

    private boolean ACTIVATED = false;
    private int TICKS_SINCE_ACTIVATED = 0;
    private static final AABB BOX = new AABB(-7,-7,-7,7,7,7);

    public FrozenZombieTile(BlockPos p_155229_, BlockState p_155230_) {
        super(TileEntitiesRegistry.FROZEN_ZOMBIE_TRAP.get(), p_155229_, p_155230_);
    }

    public static void tick(Level world,BlockPos pos,BlockState state,FrozenZombieTile tile){
        if (world.getGameTime() % 20 == 0){
            if (!tile.ACTIVATED) {
                if (!world.getEntitiesOfClass(Player.class, BOX.move(tile.worldPosition), player -> !player.isCreative() && !player.isSpectator()).isEmpty()) {
                    FrostedZombie zombie = new FrostedZombie(EntitiesRegistry.FROSTED_ZOMBIE.get(),world,true);
                    zombie.setPos(Helpers.blockCenter(pos).add(0,-0.8,0));
                    world.addFreshEntity(zombie);
                    tile.ACTIVATED = true;
                }
            }
        }
        if (tile.ACTIVATED){
            tile.TICKS_SINCE_ACTIVATED++;
            if (tile.TICKS_SINCE_ACTIVATED >= FrostedZombie.MAX_ERUPTING_TIME){
                world.setBlock(pos, Blocks.ICE.defaultBlockState(),3);
            }else{
                if (tile.TICKS_SINCE_ACTIVATED % 4 == 0) {
                    world.levelEvent(null, 2001, pos, Block.getId(state));
                }
            }
        }
    }


    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.putBoolean("activated",ACTIVATED);
        tag.putInt("ticks",TICKS_SINCE_ACTIVATED);
        super.saveAdditional(tag);
    }


    @Override
    public void load(CompoundTag tag) {
        this.ACTIVATED = tag.getBoolean("activated");
        this.TICKS_SINCE_ACTIVATED = tag.getInt("ticks");
        super.load(tag);
    }
}
