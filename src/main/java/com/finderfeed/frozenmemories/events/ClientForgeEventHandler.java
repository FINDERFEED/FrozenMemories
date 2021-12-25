package com.finderfeed.frozenmemories.events;


import com.finderfeed.frozenmemories.FrozenMemories;
import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.LoreTileEntity;
import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.*;
import com.finderfeed.frozenmemories.helpers.Helpers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = FrozenMemories.MOD_ID,bus = Mod.EventBusSubscriber.Bus.FORGE,value = Dist.CLIENT)
public class ClientForgeEventHandler {

    public static List<BlockPos> BORDER_RENDER_POSITIONS = new ArrayList<>();
    public static List<ClientObjective> ALL_OBJECTIVES = new ArrayList<>();


    @SubscribeEvent
    public static void renderObjectives(RenderGameOverlayEvent.Pre event){
        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT){
            int width = event.getWindow().getGuiScaledWidth();
            int height = event.getWindow().getGuiScaledHeight();
            PoseStack matrices = event.getMatrixStack();
            Font f = Minecraft.getInstance().font;
            if (!ALL_OBJECTIVES.isEmpty()) {
                Gui.drawString(matrices, f, new TextComponent("Objectives:").withStyle(ChatFormatting.GOLD), 10, 10, 16755200);
                for (int i = 0; i < ALL_OBJECTIVES.size();i++){
                    ClientObjective objective = ALL_OBJECTIVES.get(i);
                    MutableComponent c = objective.isCompleted() ? new TextComponent(objective.getName()).withStyle(ChatFormatting.STRIKETHROUGH) : new TextComponent(objective.getName());
                    Gui.drawString(matrices,f,c,10,20+9*i,0xffffff);
                    if (objective.getMaxProgress() != -1){
                        int offs = objective.getName().length() + 5;
                        MutableComponent d = objective.isCompleted() ? new TextComponent("Progress: " + objective.getMaxProgress() + "/" + objective.getCurrentProgress()).withStyle(ChatFormatting.STRIKETHROUGH) :
                                new TextComponent("Progress: " + objective.getCurrentProgress()+"/"+objective.getMaxProgress());
                        Gui.drawString(matrices,f,d,10 + offs,20+9*i,0xffffff);
                    }
                }
            }
        }
    }



    @SubscribeEvent
    public static void clientPlayerTickEvent(TickEvent.PlayerTickEvent event){
        if (event.phase == TickEvent.Phase.START && event.side == LogicalSide.CLIENT){
            Level world = event.player.level;
            if (world.dimension() == ForgeEventHandler.MEMORY) {
                if (world.getGameTime() % 20 == 0) {
                    List<LevelChunk> chunks = Helpers.getChunksInRadius(world, event.player.getOnPos(),3);
                    populateObjectivesList(chunks);
                }
            }
        }
    }


    private static void populateObjectivesList(List<LevelChunk> chunks){
        ALL_OBJECTIVES.clear();
        for (LevelChunk chunk : chunks){
            for (BlockEntity e : chunk.getBlockEntities().values()){
                if (e instanceof LoreTileEntity tile){
                    int state = tile.getPlayerProgressionState();
                    LoreProgram prog = ((state > -1) && (state < tile.getPrograms().length)) ? tile.getPrograms()[state] : null;
                    if (prog != null){
                        int currentStage = prog.getCurrentStage();
                        if (currentStage != -1){
                            LoreProgramStage stage = prog.getStages().get(currentStage);
                            if (!stage.isCompleted()) {
                                for (Objective objective : stage.getObjectives()) {
                                    ClientObjective obj = new ClientObjective(objective.getName(), objective.check());
                                    if (objective instanceof ObjectiveWithProgress owp) {
                                        obj.setProgress(owp.getMaxProgress(), owp.getCurrentProgress());
                                    }
                                    ALL_OBJECTIVES.add(obj);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
