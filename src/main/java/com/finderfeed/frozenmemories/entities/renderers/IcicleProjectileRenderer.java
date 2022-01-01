package com.finderfeed.frozenmemories.entities.renderers;

import com.finderfeed.frozenmemories.entities.IcicleProjectile;
import com.finderfeed.frozenmemories.helpers.ClientHelpers;
import com.finderfeed.frozenmemories.helpers.Helpers;
import com.finderfeed.frozenmemories.registries.ItemsRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class IcicleProjectileRenderer extends EntityRenderer<IcicleProjectile> {
    public IcicleProjectileRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }


    @Override
    public void render(IcicleProjectile projectile, float pticks, float hz, PoseStack matrices, MultiBufferSource buffer, int light) {
        matrices.pushPose();
        ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();


        ClientHelpers.applyMovementMatrixRotations(matrices,projectile.getDeltaMovement().normalize());
        matrices.mulPose(Vector3f.ZP.rotationDegrees(135));

        renderer.render(ItemsRegistry.MAGIC_ICICLE.get().getDefaultInstance(),
                ItemTransforms.TransformType.FIXED,false,matrices,buffer, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY,
                renderer.getModel(ItemsRegistry.MAGIC_ICICLE.get().getDefaultInstance(),Minecraft.getInstance().level,Minecraft.getInstance().player,0));

        matrices.popPose();

        super.render(projectile, pticks, hz, matrices, buffer, light);
    }

    @Override
    public ResourceLocation getTextureLocation(IcicleProjectile p_114482_) {
        return null;
    }
}
