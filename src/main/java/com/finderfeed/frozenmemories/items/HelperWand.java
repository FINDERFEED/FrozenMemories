package com.finderfeed.frozenmemories.items;

import com.finderfeed.frozenmemories.FrozenMemories;
import com.finderfeed.frozenmemories.blocks.tileentities.LoreTileEntity;
import com.finderfeed.frozenmemories.helpers.Helpers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class HelperWand extends Item {

    private static final String pos1tag = "pos1";

    public HelperWand() {
        super(new Properties().stacksTo(1).tab(FrozenMemories.FROZEN_MEMORIES));
    }


    @Override
    public InteractionResult useOn(UseOnContext context) {
        BlockPos pos = context.getClickedPos();
        Player pl = context.getPlayer();
        Level world = context.getLevel();
        ItemStack stack = context.getItemInHand();
        if (!world.isClientSide){
            if (!pl.isCrouching()) {
                if (world.getBlockEntity(pos) instanceof LoreTileEntity tile) {
                    putFirstPosition(stack, pos);
                }else{
                    calculateOffset(stack,pos);
                }
            }else{
                resetTag(stack);
            }
        }
        return super.useOn(context);
    }



    private void resetTag(ItemStack stack){
        Helpers.writeBlockPos("pos",Helpers.NULL_BLOCK_POS,getTag(stack));
    }

    private void putFirstPosition(ItemStack stack,BlockPos pos){
        Helpers.writeBlockPos("pos",pos,getTag(stack));
    }

    private BlockPos getFirstPosition(ItemStack stack){
        return Helpers.getBlockPos("pos",getTag(stack));
    }

    private void calculateOffset(ItemStack stack,BlockPos secondPos){
        BlockPos firstPos = getFirstPosition(stack);
        System.out.println("X offset: " + (secondPos.getX() - firstPos.getX()) );
        System.out.println("Y offset: " + (secondPos.getY() - firstPos.getY()) );
        System.out.println("Z offset: " + (secondPos.getZ() - firstPos.getZ()) );
    }

    private CompoundTag getTag(ItemStack stack){
        return stack.getOrCreateTagElement(pos1tag);
    }
}
