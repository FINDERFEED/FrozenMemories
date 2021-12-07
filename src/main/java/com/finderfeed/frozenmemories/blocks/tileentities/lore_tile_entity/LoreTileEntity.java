package com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity;

import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.LoreProgram;
import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.LoreProgramStage;
import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.Offset;
import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.objectives.HarvestBlocksObjective;
import com.finderfeed.frozenmemories.helpers.Helpers;
import com.finderfeed.frozenmemories.registries.TileEntitiesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import static com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.LoreProgramStage.ScheduledTask;

public class LoreTileEntity extends BlockEntity {

    public static final AABB PLAYER_SEEK_AABB = new AABB(-50,-50,-50,50,50,50);
    private int playerProgressionState = 0;

    private LoreProgram ZERO_PROGRAM = LoreProgram.Builder.start("zero",this)
            .addStage(LoreProgramStage.Builder.start("lore_program_first",this)
            .addMessages("So you came...","The worst mistake in your life!","Die insect!")
                    .addObjectives(new HarvestBlocksObjective(this, Blocks.BLACK_TERRACOTTA,Offset.of(0,4,0)))
                    .addScheduledTask(ScheduledTask.create(ScheduledTask.Type.REPETITIVE,60,(tile)->{
                        BlockPos[] pos1 = new BlockPos[]{
                                Offset.of(5,5,0).apply(tile.getBlockPos()),
                                Offset.of(0,5,5).apply(tile.getBlockPos()),
                                Offset.of(-5,5,0).apply(tile.getBlockPos())
                        };
                        for (BlockPos pos : pos1){
                            Zombie zombie = new Zombie(tile.level);
                            zombie.setPos(Helpers.blockCenter(pos));
                            tile.level.addFreshEntity(zombie);
                        }
                    })).build()

            ).build();

    private LoreProgram[] PROGRAMS = new LoreProgram[]{
        ZERO_PROGRAM
    };

    public LoreTileEntity(BlockPos pos, BlockState state) {
        super(TileEntitiesRegistry.LORE_TILE_ENTITY.get(), pos, state);
    }

    public static void tick(Level world,BlockState state,BlockPos pos,LoreTileEntity tile){
        if (!world.isClientSide){
            if (tile.getPlayerProgressionState() < tile.PROGRAMS.length) {
                tile.PROGRAMS[tile.getPlayerProgressionState()].tick();
            }
        }
    }



    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.putInt("playerProgState",playerProgressionState);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        this.playerProgressionState = tag.getInt("playerProgState");
        super.load(tag);
    }


    public void setPlayerProgressionState(int playerProgressionState) {
        this.playerProgressionState = playerProgressionState;
    }

    public int getPlayerProgressionState() {
        return playerProgressionState;
    }
}
