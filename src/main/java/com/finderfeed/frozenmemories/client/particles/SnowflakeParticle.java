package com.finderfeed.frozenmemories.client.particles;

import com.finderfeed.frozenmemories.FrozenMemories;
import com.finderfeed.frozenmemories.helpers.Helpers;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

public class SnowflakeParticle extends TextureSheetParticle {

    private float baseQuadSize;

    protected SnowflakeParticle(ClientLevel p_108328_, double p_108329_, double p_108330_, double p_108331_, double x, double y, double z) {
        super(p_108328_, p_108329_, p_108330_, p_108331_, x, y, z);
        setParticleSpeed(x,y,z);
        this.rCol = (Helpers.RANDOM.nextInt(25) + 163) /255f;
        this.gCol = 223/255f;
        this.bCol = 255/255f;
        this.alpha = 1;
        this.lifetime = 40;
        this.quadSize = 0.5f;
        this.baseQuadSize = quadSize;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return TRANSLUCENT;
    }

    @Override
    public void tick() {
        this.quadSize-=baseQuadSize/getLifetime();
        super.tick();
    }

    public static class Factory implements ParticleProvider<SimpleParticleType>{

        private SpriteSet set;

        public Factory(SpriteSet set){
            this.set = set;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType p_107421_, ClientLevel level, double x, double y, double z, double x1, double y1, double z1) {
            SnowflakeParticle particle = new SnowflakeParticle(level,x,y,z,x1,y1,z1);
            particle.pickSprite(set);
            return particle;
        }
    }

    private static final ParticleRenderType TRANSLUCENT = new ParticleRenderType() {
        @Override
        public void begin(BufferBuilder bufferBuilder, TextureManager textureManager) {

            RenderSystem.depthMask(false);
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
//            GL11.glAlphaFunc(GL11.GL_GREATER,0.003921569F);

//            GL11.glDisable(GL11.GL_LIGHTING);
            RenderSystem.setShaderTexture(0,TextureAtlas.LOCATION_PARTICLES);

            textureManager.getTexture(TextureAtlas.LOCATION_PARTICLES).setBlurMipmap(true, false);
            bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);

        }

        @Override
        public void end(Tesselator tessellator) {
            tessellator.end();

            Minecraft.getInstance().textureManager.getTexture(TextureAtlas.LOCATION_PARTICLES).restoreLastBlurMipmap();
//            GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
            RenderSystem.disableBlend();
            RenderSystem.depthMask(true);


        }

        @Override
        public String toString() {
            return FrozenMemories.MOD_ID + ":translucent";
        }
    };
}
