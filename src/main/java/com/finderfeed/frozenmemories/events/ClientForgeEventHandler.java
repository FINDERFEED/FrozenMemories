package com.finderfeed.frozenmemories.events;


import com.finderfeed.frozenmemories.FrozenMemories;
import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.LoreTileEntity;
import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.*;
import com.finderfeed.frozenmemories.client.shaderstuff.ExtendedPostChain;
import com.finderfeed.frozenmemories.client.shaderstuff.ExtendedUniform;
import com.finderfeed.frozenmemories.helpers.ClientHelpers;
import com.finderfeed.frozenmemories.helpers.Helpers;
import com.finderfeed.frozenmemories.registries.BlocksRegistry;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLevelLastEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = FrozenMemories.MOD_ID,bus = Mod.EventBusSubscriber.Bus.FORGE,value = Dist.CLIENT)
public class ClientForgeEventHandler {

    public static final ResourceLocation LOW_HP_OVERLAY = new ResourceLocation(FrozenMemories.MOD_ID,"textures/misc/low_hp_overlay.png");
    public static final ResourceLocation LOW_HP_OVERLAY1 = new ResourceLocation(FrozenMemories.MOD_ID,"textures/misc/low_hp_overlay1.png");

    public static List<BlockPos> BORDER_RENDER_POSITIONS = new ArrayList<>();
    public static List<ClientObjective> ALL_OBJECTIVES = new ArrayList<>();

    public static float sineShaderTime = 0;

    @SubscribeEvent
    public static void renderObjectives(RenderGameOverlayEvent.Pre event){
        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT){
            if (Minecraft.getInstance().level != null && Minecraft.getInstance().level.dimension() != ForgeEventHandler.MEMORY){
                ALL_OBJECTIVES.clear();
            }
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


            Player player = ClientHelpers.clientPlayer();
            if (player.getHealth() <= 10 && (player.level.dimension() == ForgeEventHandler.MEMORY) ){
                float percent = (10-player.getHealth()) / 10;
//                RenderSystem.setShaderTexture(0,LOW_HP_OVERLAY);
//                Window window = event.getWindow();
//                matrices.pushPose();
                RenderSystem.enableBlend();
                Gui gui = Minecraft.getInstance().gui;
//
                gui.renderTextureOverlay(LOW_HP_OVERLAY,percent);
//                RenderSystem.setShaderColor(1,1,1,percent);
//                Gui.blit(matrices,0,0,0,0,window.getGuiScaledWidth(),window.getGuiScaledHeight(),480,270);
//                matrices.popPose();
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
                    populateObjectivesListAndBorders(chunks);
                }
            }
        }
    }


    private static void populateObjectivesListAndBorders(List<LevelChunk> chunks){
        ALL_OBJECTIVES.clear();
        BORDER_RENDER_POSITIONS.clear();
        for (LevelChunk chunk : chunks){
            for (BlockEntity e : chunk.getBlockEntities().values()){
                if (e instanceof LoreTileEntity tile){
                    int state = tile.getPlayerProgressionState();
                    LoreProgram prog = ((state > -1) && (state-1 < tile.getPrograms().length)) ? tile.getPrograms()[state-1] : null;
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
                                List<BlockPos> p = scanForTriggerBlocks();
                                BORDER_RENDER_POSITIONS.addAll(p);
                            }
                        }
                    }
                }
            }
        }
    }

    private static List<BlockPos> scanForTriggerBlocks(){
        BlockPos playerpos = Minecraft.getInstance().player.getOnPos().above();
        List<BlockPos> r = new ArrayList<>();
        Level world = Minecraft.getInstance().level;
        int rad = 3;
        for (int x = -rad;x <= rad;x++){
            for (int y = -rad;y <= rad;y++){
                for (int z = -rad;z <= rad;z++){
                    BlockPos pos = playerpos.offset(x,y,z);
                    if (world.getBlockState(pos).is(BlocksRegistry.LORE_TILE_TRIGGER_BLOCK.get())){
                        r.add(pos);
                    }
                }
            }
        }
        return r;
    }

    private static final ResourceLocation FORCEFIELD_LOCATION = new ResourceLocation("textures/misc/forcefield.png");

    @SubscribeEvent
    public static void renderBorders(RenderLevelLastEvent event){
        if (!BORDER_RENDER_POSITIONS.isEmpty()) {
            BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
            RenderSystem.enableBlend();
            RenderSystem.enableDepthTest();
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            RenderSystem.setShaderTexture(0, FORCEFIELD_LOCATION);
            RenderSystem.depthMask(Minecraft.useShaderTransparency());
            PoseStack posestack = RenderSystem.getModelViewStack();
            posestack.pushPose();
            posestack.mulPoseMatrix(event.getPoseStack().last().pose());
            RenderSystem.applyModelViewMatrix();
            RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
            RenderSystem.polygonOffset(-3.0F, -3.0F);
            RenderSystem.enablePolygonOffset();
            RenderSystem.disableCull();
            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
            //start render here

            Camera cam = Minecraft.getInstance().gameRenderer.getMainCamera();
            for (BlockPos pos : BORDER_RENDER_POSITIONS){
                double xRenderPos = pos.getX() - cam.getPosition().x;
                double yRenderPos = pos.getY() - cam.getPosition().y;
                double zRenderPos = pos.getZ() - cam.getPosition().z;
                renderBox(bufferbuilder,xRenderPos,yRenderPos,zRenderPos);
            }
            //end render here
            bufferbuilder.end();
            BufferUploader.end(bufferbuilder);
            RenderSystem.enableCull();
            RenderSystem.polygonOffset(0.0F, 0.0F);
            RenderSystem.disablePolygonOffset();
            RenderSystem.disableBlend();
            posestack.popPose();
            RenderSystem.applyModelViewMatrix();
            RenderSystem.depthMask(true);
        }

    }

    public static void renderBox(BufferBuilder builder, double posx, double posy, double posz){
        float time;
        if (Minecraft.getInstance().level != null){
            time = ( (Minecraft.getInstance().level.getGameTime()/4f) % 16) / 16f;
        }else{
            time = 0;
        }
        int r = 200/255;
        int g = 255/255;
        int b = 255/255;
        float a  = 0.35f;

        builder.vertex(0+posx,0+posy,0+posz).uv(time,time).color(r,g,b,a).endVertex();
        builder.vertex(0+posx,1+posy,0+posz).uv(time,1+time).color(r,g,b,a).endVertex();
        builder.vertex(1+posx,1+posy,0+posz).uv(1+time,1+time).color(r,g,b,a).endVertex();
        builder.vertex(1+posx,0+posy,0+posz).uv(1+time,time).color(r,g,b,a).endVertex();

        builder.vertex(0+posx,0+posy,0+posz).uv(time,time).color(r,g,b,a).endVertex();
        builder.vertex(0+posx,1+posy,0+posz).uv(time,1+time).color(r,g,b,a).endVertex();
        builder.vertex(0+posx,1+posy,1+posz).uv(1+time,1+time).color(r,g,b,a).endVertex();
        builder.vertex(0+posx,0+posy,1+posz).uv(1+time,time).color(r,g,b,a).endVertex();

        builder.vertex(0+posx,0+posy,0+posz).uv(time,time).color(r,g,b,a).endVertex();
        builder.vertex(1+posx,0+posy,0+posz).uv(time,1+time).color(r,g,b,a).endVertex();
        builder.vertex(1+posx,0+posy,1+posz).uv(1+time,1+time).color(r,g,b,a).endVertex();
        builder.vertex(0+posx,0+posy,1+posz).uv(1+time,time).color(r,g,b,a).endVertex();

        builder.vertex(0+posx,1+posy,0+posz).uv(time,time).color(r,g,b,a).endVertex();
        builder.vertex(1+posx,1+posy,0+posz).uv(time,1+time).color(r,g,b,a).endVertex();
        builder.vertex(1+posx,1+posy,1+posz).uv(1+time,1+time).color(r,g,b,a).endVertex();
        builder.vertex(0+posx,1+posy,1+posz).uv(1+time,time).color(r,g,b,a).endVertex();

        builder.vertex(0+posx,0+posy,1+posz).uv(time,time).color(r,g,b,a).endVertex();
        builder.vertex(0+posx,1+posy,1+posz).uv(time,1+time).color(r,g,b,a).endVertex();
        builder.vertex(1+posx,1+posy,1+posz).uv(1+time,1+time).color(r,g,b,a).endVertex();
        builder.vertex(1+posx,0+posy,1+posz).uv(1+time,time).color(r,g,b,a).endVertex();

        builder.vertex(1+posx,0+posy,0+posz).uv(time,time).color(r,g,b,a).endVertex();
        builder.vertex(1+posx,1+posy,0+posz).uv(time,1+time).color(r,g,b,a).endVertex();
        builder.vertex(1+posx,1+posy,1+posz).uv(1+time,1+time).color(r,g,b,a).endVertex();
        builder.vertex(1+posx,0+posy,1+posz).uv(1+time,time).color(r,g,b,a).endVertex();

    }




    public static void triggerProgressionShader(){
        sineShaderTime = 3;
    }
    private static ExtendedPostChain SINE_SHADER = null;
    private static final ResourceLocation SINE_SHADER_LOC = new ResourceLocation("frozenmemories","shaders/post/wave.json");
    private static ExtendedPostChain loadProgressionShader(ResourceLocation name, ExtendedUniform uniformPlusPlus)
    {
        Minecraft mc = Minecraft.getInstance();
        try
        {
            ExtendedPostChain shader = new ExtendedPostChain(name,uniformPlusPlus);
            shader.resize(mc.getWindow().getWidth(), mc.getWindow().getHeight());
            return shader;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @SubscribeEvent
    public static void renderWorld(RenderLevelLastEvent event){
        if ((Minecraft.getInstance().getWindow().getScreenWidth() != 0) && (Minecraft.getInstance().getWindow().getScreenHeight() != 0)) {
            if ((sineShaderTime > 0)  ) {
                ClientHelpers.renderHandManually(event.getPoseStack(),event.getPartialTick());

                float time =Minecraft.getInstance().level.getGameTime();
                ExtendedUniform uniforms = new ExtendedUniform(Map.of(
                        "intensity",sineShaderTime,
                        "timeModifier",3f,
                        "time",time
                ));
                if (SINE_SHADER == null){
                    SINE_SHADER = loadProgressionShader(SINE_SHADER_LOC, uniforms);
                }else{
                    SINE_SHADER.updateUniforms(uniforms);
                    SINE_SHADER.resize(Minecraft.getInstance().getWindow().getWidth(), Minecraft.getInstance().getWindow().getHeight());
                }
                RenderSystem.disableBlend();
                RenderSystem.disableDepthTest();
                RenderSystem.enableTexture();
                RenderSystem.resetTextureMatrix();
                if (SINE_SHADER != null) {
                    SINE_SHADER.process(Minecraft.getInstance().getFrameTime());
                }
            }
        }
    }

    @SubscribeEvent
    public static void clientTickEvent(TickEvent.ClientTickEvent event){
        if ((sineShaderTime > 0) && (event.phase == TickEvent.Phase.START) ){

            sineShaderTime-=0.03;
        }
    }

}
