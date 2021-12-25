package com.finderfeed.frozenmemories.blocks;

import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.LoreTileEntity;
import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.LoreProgram;
import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.Offset;
import com.finderfeed.frozenmemories.helpers.Helpers;
import com.finderfeed.frozenmemories.registries.BlocksRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.FurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.Tags;

import java.util.ArrayList;
import java.util.List;

public class LoreTriggerBlock extends Block {

    private static final BooleanProperty PASSABLE = BooleanProperty.create("passable");
    private final VoxelShape shape = Block.box(0,0,0,0,0,0);
    private final VoxelShape shapeImpassable = Block.box(2,2,2,14,14,14);
    public LoreTriggerBlock(Properties p_49795_) {
        super(p_49795_);
        registerDefaultState(this.getStateDefinition().any().setValue(PASSABLE,false));
    }


    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        if (world instanceof ServerLevel serverWorld && entity instanceof Player pl && !pl.isCreative()){
            List<LevelChunk> chunks = Helpers.getChunksInRadius(serverWorld,pos,3);
            for (LevelChunk chunk : chunks) {
                for (BlockEntity e : chunk.getBlockEntities().values()) {
                    if (e instanceof LoreTileEntity tile) {
                        LoreProgram program = tile.getCurrentLoreProgram();
                        if (program != null) {
                            if (program.isStageRunning()) {
                                serverWorld.setBlock(pos,state.setValue(PASSABLE,false),3);
                            } else {
                                deleteStatesAround(serverWorld, pos);
                                program.nextStage();
                                List<BlockPos> posList = new ArrayList<>();
                                populateList(world,pos,posList,0);
                                posList.add(pos);

                            }
                        }else{
                            serverWorld.setBlock(pos,state.setValue(PASSABLE,false),3);
                        }
                    }
                }
            }
        }
        super.entityInside(state, world, pos, entity);
    }



    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        if (state.getValue(PASSABLE)){
            return shape;
        }
        return shapeImpassable;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        super.createBlockStateDefinition(p_49915_);
        p_49915_.add(PASSABLE);
    }

    @Override
    public RenderShape getRenderShape(BlockState p_60550_) {
        return RenderShape.INVISIBLE;
    }

    private void deleteStatesAround(Level world, BlockPos pos){
        if (!world.isClientSide){
            List<BlockPos> positons = new ArrayList<>();
            populateList(world,pos,positons,0);
            positons.add(pos);
            for (BlockPos pos1 : positons){
                world.setBlock(pos1, Blocks.AIR.defaultBlockState(),3);
            }
        }
    }

    private void populateList(Level world, BlockPos pos, List<BlockPos> posList,int depth){
        if (depth != 20) {
            List<BlockPos> poslis = new ArrayList<>();
            poslis.add(pos.above());
            poslis.add(pos.below());
            poslis.add(pos.south());
            poslis.add(pos.east());
            poslis.add(pos.north());
            poslis.add(pos.west());
            posList.add(pos);
            poslis.removeIf(posList::contains);
            for (BlockPos a : poslis) {
                BlockState state = world.getBlockState(a);
                if (state.getBlock() == BlocksRegistry.LORE_TILE_TRIGGER_BLOCK.get()){
                    posList.add(a);
                    populateList(world,a,posList,depth+1);
                }
            }
        }
    }

}
