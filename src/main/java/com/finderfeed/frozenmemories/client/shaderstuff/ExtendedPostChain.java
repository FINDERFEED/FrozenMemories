package com.finderfeed.frozenmemories.client.shaderstuff;

import com.google.gson.JsonSyntaxException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.PostPass;
import net.minecraft.resources.ResourceLocation;

import java.io.IOException;

public class ExtendedPostChain extends PostChain {

    private ExtendedUniform uniforms;

    public ExtendedPostChain(ResourceLocation loc, ExtendedUniform uniform) throws IOException, JsonSyntaxException {
        super(Minecraft.getInstance().textureManager,
                Minecraft.getInstance().getResourceManager(),
                Minecraft.getInstance().getMainRenderTarget(),loc);
        this.uniforms = uniform;
    }

    public void updateUniforms(ExtendedUniform uniforms){
        this.uniforms = uniforms;
    }

    @Override
    public void process(float p_110024_) {
        if (p_110024_ < this.lastStamp) {
            this.time += 1.0F - this.lastStamp;
            this.time += p_110024_;
        } else {
            this.time += p_110024_ - this.lastStamp;
        }
        for(this.lastStamp = p_110024_; this.time > 20.0F; this.time -= 20.0F) {
        }
        for(PostPass postpass : this.passes) {

            if(uniforms != null){
                uniforms.addAll(postpass.getEffect());
            }
            postpass.process(this.time / 20.0F);
        }
    }
}
