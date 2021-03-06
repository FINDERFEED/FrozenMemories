package com.finderfeed.frozenmemories.blocks;

import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.LoreTileEntity;
import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.LoreProgram;
import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.PlayerProgressionStage;
import com.finderfeed.frozenmemories.helpers.Helpers;
import com.finderfeed.frozenmemories.misc.MemoryTeleporter;
import com.finderfeed.frozenmemories.packet_handler.PacketHandler;
import com.finderfeed.frozenmemories.packet_handler.packets.TriggerOutOfMemoryDimShader;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkDirection;

import java.util.List;

public class MemoryCrack extends Block {

    private final VoxelShape shapeImpassable = Block.box(2,2,2,14,14,14);

    public MemoryCrack() {
        super(BlockBehaviour.Properties.copy(Blocks.BEDROCK));
    }

    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        if (entity instanceof ServerPlayer player){
//            if (player.canChangeDimensions() && hasPlayerCompletedEverything(world,pos,player)){
//                MemoryTeleporter teleporter = new MemoryTeleporter(BlockPos.ZERO);
//                if (world.getServer() != null) {
//                    ServerLevel destination = world.getServer().getLevel(Level.OVERWORLD);
//                    if (destination != null) {
////                        PlayerProgressionStage.incrementPlayerProgressionStage(player);
//                        player.getInventory().load(player.getPersistentData().getList(Helpers.TAG_INVENTORY,10));
//                        player.changeDimension(destination, teleporter);
//                    }
//                }
//            }
            teleportPlayerBack(player,false);
        }
        super.entityInside(state, world, pos, entity);
    }

    public static void teleportPlayerBack(ServerPlayer player,boolean fail){
        Level world = player.level;
        BlockPos pos = player.getOnPos();
        if (player.canChangeDimensions() && (fail || hasPlayerCompletedEverything(world,pos,player))){
            MemoryTeleporter teleporter = new MemoryTeleporter(BlockPos.ZERO);
            if (world.getServer() != null) {
                ServerLevel destination = world.getServer().getLevel(Level.OVERWORLD);
                if (destination != null) {
                    if (!fail) {
                        PlayerProgressionStage.incrementPlayerProgressionStage(player);
                    }
                    player.getInventory().load(player.getPersistentData().getList(Helpers.TAG_INVENTORY,10));
                    PacketHandler.INSTANCE.sendTo(new TriggerOutOfMemoryDimShader(),player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
                    player.changeDimension(destination, teleporter);

                }
            }
        }
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return shapeImpassable;
    }

    private static boolean hasPlayerCompletedEverything(Level world, BlockPos pos, Player pl){
        List<LevelChunk> chunks = Helpers.getChunksInRadius(world,pos,3);
        for (LevelChunk chunk : chunks){
            for (BlockEntity tile : chunk.getBlockEntities().values()){
                if (tile instanceof LoreTileEntity loreTile){
                    LoreProgram program = loreTile.getCurrentLoreProgram();
                    if (program != null){
                        return program.isCompleted();
                    }else{
                        pl.sendMessage(new TextComponent("Some strange error while trying to get current lore program"),pl.getUUID());
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
