package com.finderfeed.frozenmemories.blocks;

import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.PlayerProgressionStage;
import com.finderfeed.frozenmemories.events.ForgeEventHandler;
import com.finderfeed.frozenmemories.helpers.Helpers;
import com.finderfeed.frozenmemories.misc.MemoryTeleporter;
import com.finderfeed.frozenmemories.misc.ProgressionState;
import com.finderfeed.frozenmemories.misc.ServerWorldTask;
import com.finderfeed.frozenmemories.registries.BlocksRegistry;
import com.finderfeed.frozenmemories.registries.TileEntitiesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.TextComponent;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.TicketType;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class MemoryTowerBlock extends Block implements EntityBlock {
    public MemoryTowerBlock(Properties p_49795_) {
        super(p_49795_);
    }


    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult ctx) {
        if (world instanceof  ServerLevel level && hand == InteractionHand.MAIN_HAND){
            int stage = PlayerProgressionStage.getPlayerProgressionStage(player);
            if (stage <= 7){
                BlockPos randomPos = new BlockPos(
                        Helpers.randomPlusMinus()*world.random.nextInt(10000000),120,
                        Helpers.randomPlusMinus()*world.random.nextInt(1000000));
                MemoryTeleporter teleporter = new MemoryTeleporter(randomPos);
                if (world.getServer() != null){
                    ServerLevel destination = world.getServer().getLevel(ForgeEventHandler.MEMORY);
                    if (destination != null){
                        StructureTemplate template = Helpers.getStageStructureTemplate(level,stage);
                        Helpers.writeBlockPos(Helpers.TAG_RETURN_BLOCKPOS,player.getOnPos(),player.getPersistentData());
                        ListTag tag = new ListTag();
                        player.getInventory().save(tag);
                        player.getInventory().clearContent();
                        player.getPersistentData().put(Helpers.TAG_INVENTORY,tag);


                        destination.getChunkSource().addRegionTicket(TicketType.PORTAL, new ChunkPos(randomPos), 3,randomPos);
                        StructurePlaceSettings set = new StructurePlaceSettings().addProcessor(BlockIgnoreProcessor.AIR).setRandom(destination.random).setRotation(Rotation.NONE).setBoundingBox(BoundingBox.infinite());
                        player.changeDimension(destination, teleporter);
                        BlockPos position = ProgressionState.STATES.get(stage).getOffset().apply(randomPos);
                        setBoxAround(destination,position);
                        ForgeEventHandler.addServerTask(new ServerWorldTask(60,ForgeEventHandler.MEMORY,()->{
                            clearBoxAround(destination,position);
                            template.placeInWorld(destination,randomPos,randomPos,set,destination.random,Block.UPDATE_CLIENTS);
                        }));
                        //template.placeInWorld(destination,randomPos,randomPos,set,destination.random,4);
                    }
                }
            }else{
                player.sendMessage(new TextComponent("You have seen everything."),player.getUUID());
            }
        }
        return super.use(state, world, pos, player, hand, ctx);
    }

    private void setBoxAround(ServerLevel level,BlockPos pos){
        BlockState state = BlocksRegistry.MEMORY_WALL.get().defaultBlockState();
        level.setBlock(pos.below(),state,3);
        level.setBlock(pos.above(2),state,3);

        level.setBlock(pos.above().north(),state,3);
        level.setBlock(pos.north(),state,3);

        level.setBlock(pos.above().south(),state,3);
        level.setBlock(pos.south(),state,3);

        level.setBlock(pos.above().west(),state,3);
        level.setBlock(pos.west(),state,3);

        level.setBlock(pos.above().east(),state,3);
        level.setBlock(pos.east(),state,3);
    }
    private void clearBoxAround(ServerLevel level,BlockPos pos){
        BlockState state = Blocks.AIR.defaultBlockState();
        level.setBlock(pos.below(),state,3);
        level.setBlock(pos.above(2),state,3);

        level.setBlock(pos.above().north(),state,3);
        level.setBlock(pos.north(),state,3);

        level.setBlock(pos.above().south(),state,3);
        level.setBlock(pos.south(),state,3);

        level.setBlock(pos.above().west(),state,3);
        level.setBlock(pos.west(),state,3);

        level.setBlock(pos.above().east(),state,3);
        level.setBlock(pos.east(),state,3);
    }

    @Override
    public RenderShape getRenderShape(BlockState p_60550_) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return TileEntitiesRegistry.MEMORY_TOWER.get().create(pos,state);
    }
}
