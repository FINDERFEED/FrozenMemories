package com.finderfeed.frozenmemories.items;

import com.finderfeed.frozenmemories.FrozenMemories;
import com.finderfeed.frozenmemories.helpers.Helpers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class BuildingHelperWand extends Item {

    private static final String MAIN = "main";
    private static final String TAG_POS1 = "pos1";
    private static final String TAG_BLOCK = "block";

    public BuildingHelperWand() {
        super(new Properties().stacksTo(1).tab(FrozenMemories.FROZEN_MEMORIES));
    }


    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        Level world = ctx.getLevel();
        Player pl = ctx.getPlayer();
        BlockState state = world.getBlockState(ctx.getClickedPos());
        ItemStack stack = ctx.getItemInHand();

            if (!pl.isCrouching()){
                BlockPos pos = getPos(stack);
                BlockPos clickedPos = ctx.getClickedPos();
                if (!pos.equals(BlockPos.ZERO)){
                    BlockState state1 = getBlockState(stack);
                    int xm = Math.min(pos.getX(), clickedPos.getX());
                    int ym = Math.min(pos.getY(), clickedPos.getY());
                    int zm = Math.min(pos.getZ(), clickedPos.getZ());
                    int xmax = Math.max(pos.getX(), clickedPos.getX());
                    int ymax = Math.max(pos.getY(), clickedPos.getY());
                    int zmax = Math.max(pos.getZ(), clickedPos.getZ());
                    for (int i = xm;i <= xmax;i++){
                        for (int g = ym;g <= ymax;g++){
                            for (int k = zm;k <= zmax;k++){
                                world.setBlock(new BlockPos(i,g,k),state1,Block.UPDATE_NEIGHBORS);
                            }
                        }
                    }
                    writePos1(stack,BlockPos.ZERO);
                }else{
                    writePos1(stack,clickedPos);
                }
            }else{
                writePos1(stack,BlockPos.ZERO);
                if (state.getBlock() == Blocks.BEDROCK){
                    writeBlock(stack,Blocks.AIR.defaultBlockState());
                }else{
                    writeBlock(stack,state);
                }

            }


        return super.useOn(ctx);
    }


    private void writeBlock(ItemStack stack, BlockState state){
        CompoundTag tag = getTag(stack);
        tag.putInt(TAG_BLOCK, Block.getId(state));
    }

    private void writePos1(ItemStack stack, BlockPos pos){
        Helpers.writeBlockPos(TAG_POS1,pos,getTag(stack));
    }

    private BlockPos getPos(ItemStack stack){
        return Helpers.getBlockPos(TAG_POS1,getTag(stack));
    }

    private BlockState getBlockState(ItemStack stack){
        return Block.stateById(getTag(stack).getInt(TAG_BLOCK));
    }

    private CompoundTag getTag(ItemStack stack){
        return stack.getOrCreateTagElement(MAIN);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}
