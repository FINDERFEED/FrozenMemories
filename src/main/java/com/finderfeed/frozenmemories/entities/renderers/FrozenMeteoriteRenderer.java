package com.finderfeed.frozenmemories.entities.renderers;

import com.finderfeed.frozenmemories.FrozenMemories;
import com.finderfeed.frozenmemories.entities.FrozenZombieMeteorite;
import com.finderfeed.frozenmemories.entities.models.MeteoriteModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class FrozenMeteoriteRenderer extends EntityRenderer<FrozenZombieMeteorite> {

    private final MeteoriteModel model;
    private final ResourceLocation LOC = new ResourceLocation(FrozenMemories.MOD_ID,"textures/entities/frozen_meteorite.png");

    public FrozenMeteoriteRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
        model = new MeteoriteModel(ctx.bakeLayer(MeteoriteModel.LOCATION));
    }


    @Override
    public void render(FrozenZombieMeteorite meteor, float pticks, float idk, PoseStack matrices, MultiBufferSource buffer, int light) {
        model.renderToBuffer(matrices,buffer.getBuffer(RenderType.text(LOC)),light, OverlayTexture.NO_OVERLAY,1,1,1,1);
        super.render(meteor, pticks, idk, matrices, buffer, light);
    }

    @Override
    public ResourceLocation getTextureLocation(FrozenZombieMeteorite p_114482_) {
        return null;
    }
}
