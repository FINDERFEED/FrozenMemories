package com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity;

import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.LoreProgram;
import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.LoreProgramStage;
import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.Offset;
import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.objectives.AllEntitesKilledObjective;
import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.objectives.HarvestBlocksObjective;
import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.objectives.PlayerInventoryCheck;
import com.finderfeed.frozenmemories.helpers.Helpers;
import com.finderfeed.frozenmemories.registries.TileEntitiesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;

import java.util.List;

import static com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.LoreProgramStage.ScheduledTask;
import static com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.objectives.PlayerInventoryCheck.ItemWithQuantity;

public class LoreTileEntity extends BlockEntity {

    public static final AABB PLAYER_SEEK_AABB = new AABB(-50,-50,-50,50,50,50);
    private int playerProgressionState = 0;
    private LoreProgram currentLoreProgram = null;

    private LoreProgram ZERO_PROGRAM = LoreProgram.Builder.start("zero",this)
            .addStage(LoreProgramStage.Builder.start("stage1",this)
                    .addMessages("[You]What is this strange place?","[You]Wait what?!")
                    .addObjectives(new AllEntitesKilledObjective("Kill all zombies",this,Zombie.class),
                            new HarvestBlocksObjective("Dig dirt block",this,Blocks.DIRT,Offset.of(-5,2,0)),
                            new PlayerInventoryCheck("Get 2 rotten flesh",this, ItemWithQuantity.of(Items.ROTTEN_FLESH,2)))
                    .addScheduledTask(ScheduledTask.create(ScheduledTask.Type.SINGLE,LoreProgramStage.MESSAGE_SEND_TIME-1,(tile)->{
                        BlockPos[] p = {
                                Offset.of(-6,2,1).apply(tile.getBlockPos()),
                                Offset.of(-6,2,-1).apply(tile.getBlockPos()),
                                Offset.of(-4,2,1).apply(tile.getBlockPos()),
                                Offset.of(-4,2,-1).apply(tile.getBlockPos())
                        };
                        for (BlockPos pos : p){
                            Zombie zombie = new Zombie(tile.level);
                            zombie.setPos(Helpers.blockCenter(pos));
                            tile.level.addFreshEntity(zombie);
                        }
                    }))
                    .build())
            .addStage(LoreProgramStage.Builder.start("stage2",this)
                    .addMessages("[You]This zombies wasn't ready for a minecraft professional!","[You]*Insert Obama medal meme*","[You]Oh diamonds!")
                    .build())
            .build();

    private LoreProgram[] PROGRAMS = new LoreProgram[]{
        ZERO_PROGRAM
    };

    public LoreTileEntity(BlockPos pos, BlockState state) {
        super(TileEntitiesRegistry.LORE_TILE_ENTITY.get(), pos, state);
    }

    public static void tick(Level world,BlockState state,BlockPos pos,LoreTileEntity tile){
        if (!world.isClientSide){
            if (tile.getPlayerProgressionState() < tile.PROGRAMS.length) {
                tile.currentLoreProgram = tile.PROGRAMS[tile.getPlayerProgressionState()];
                tile.currentLoreProgram.tick();
            }
        }
    }

    @Nullable
    public LoreProgram getCurrentLoreProgram() {
        return currentLoreProgram;
    }

    public LoreProgram[] getPrograms() {
        return PROGRAMS;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag);
        return ClientboundBlockEntityDataPacket.create(this,(tile)->tag);
    }


    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        this.load(pkt.getTag());
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        for (LoreProgram prog : PROGRAMS){
            prog.save(tag);
        }
        tag.putInt("playerProgState",playerProgressionState);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        for (LoreProgram prog : PROGRAMS){
            prog.load(tag);
        }
        this.playerProgressionState = tag.getInt("playerProgState");
        super.load(tag);
    }


    public void setPlayerProgressionState(int playerProgressionState) {
        this.playerProgressionState = playerProgressionState;
    }

    public int getPlayerProgressionState() {
        return playerProgressionState;
    }

    @Nullable
    public Player getPlayer(){
        List<Player> player = level.getEntitiesOfClass(Player.class,PLAYER_SEEK_AABB.move(worldPosition));
        return player.isEmpty() ? null : player.get(0);
    }
}
