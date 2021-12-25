package com.finderfeed.frozenmemories.items;

import com.finderfeed.frozenmemories.FrozenMemories;
import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.LoreTileEntity;
import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.PlayerProgressionStage;
import com.finderfeed.frozenmemories.events.ForgeEventHandler;
import com.finderfeed.frozenmemories.helpers.Helpers;
import com.finderfeed.frozenmemories.misc.ServerWorldTask;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.apache.logging.log4j.core.jmx.Server;

public class HelperWand extends Item {

    private static final String pos1tag = "pos1";

    public HelperWand() {
        super(new Properties().stacksTo(1).tab(FrozenMemories.FROZEN_MEMORIES));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
//        if (world.isClientSide && hand == InteractionHand.MAIN_HAND){
//            int lvl = PlayerProgressionStage.getPlayerProgressionStage(player);
//            if (lvl == 0){
//                PlayerProgressionStage.setPlayerProgressionStage(player,1);
//                System.out.println("set to 1");
//            }else{
//                PlayerProgressionStage.setPlayerProgressionStage(player,0);
//                System.out.println("set to 0");
//            }
//        }
//        if (world instanceof ServerLevel serverLevel){
//            System.out.println(serverLevel.getStructureManager().get(new ResourceLocation(FrozenMemories.MOD_ID,"stage_structures/stage_0")));
//        }
//        if (world instanceof ServerLevel level){
//            MinecraftServer server = level.getServer();
//            server.tell(new TickTask(server.getTickCount() + 200,()->{world.setBlock(player.getOnPos(), Blocks.DAMAGED_ANVIL.defaultBlockState(),3);}));
//        }
//        if (world instanceof ServerLevel level){
//            ForgeEventHandler.addServerTask(new ServerWorldTask(200,Level.OVERWORLD,()-> world.setBlock(player.getOnPos(), Blocks.DAMAGED_ANVIL.defaultBlockState(),3)));
//        }
        if (world.isClientSide){
            Minecraft.getInstance().levelRenderer.allChanged();
        }
        return super.use(world, player, hand);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        BlockPos pos = context.getClickedPos();
        Player pl = context.getPlayer();
        Level world = context.getLevel();
        ItemStack stack = context.getItemInHand();
        if (!world.isClientSide){
            if (!pl.isCrouching()) {
                if (getFirstPosition(stack).equals(BlockPos.ZERO) || getFirstPosition(stack).equals(Helpers.NULL_BLOCK_POS)) {
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
