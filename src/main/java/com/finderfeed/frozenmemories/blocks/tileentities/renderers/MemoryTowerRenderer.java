package com.finderfeed.frozenmemories.blocks.tileentities.renderers;

import com.finderfeed.frozenmemories.FrozenMemories;
import com.finderfeed.frozenmemories.blocks.tileentities.MemoryTowerTile;
import com.finderfeed.frozenmemories.blocks.tileentities.renderers.models.MemoryTowerModel;
import com.finderfeed.frozenmemories.events.ClientModEventHandler;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class MemoryTowerRenderer implements BlockEntityRenderer<MemoryTowerTile> {

    private static final ResourceLocation TEXT = new ResourceLocation(FrozenMemories.MOD_ID,"textures/block/memory_tower.png");
    private final MemoryTowerModel MODEL;

    public MemoryTowerRenderer(BlockEntityRendererProvider.Context context){
        this.MODEL = new MemoryTowerModel(context.bakeLayer(MemoryTowerModel.LAYER_LOCATION));
    }

    @Override
    public void render(MemoryTowerTile memoryTower, float pticks, PoseStack matrices, MultiBufferSource buffer, int light1, int light2) {
        matrices.pushPose();
        VertexConsumer c = buffer.getBuffer(RenderType.text(TEXT));
        matrices.mulPose(Vector3f.ZP.rotationDegrees(180));
        matrices.translate(-0.5,-1.5,0.5);
        MODEL.renderToBuffer(matrices,c,pticks,light1,light2,1,1,1,1);
        matrices.popPose();
    }

    @Override
    public boolean shouldRenderOffScreen(MemoryTowerTile p_112306_) {
        return true;
    }
}
