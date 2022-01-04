package com.finderfeed.frozenmemories.client.shaderstuff;

import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.EffectInstance;

import java.util.Map;

public class ExtendedUniform {

    private final Map<String ,Object> uniforms;

    public ExtendedUniform(Map<String,Object> uniforms){
        this.uniforms = uniforms;
    }


    public void addAll(EffectInstance shader){
        uniforms.forEach((name,uniform)->{
            if (uniform instanceof Float a) {
                shader.safeGetUniform(name).set(a);
            }else if (uniform instanceof Matrix4f a){
                shader.safeGetUniform(name).set(a);
            }else if (uniform instanceof Vector3f a){
                shader.safeGetUniform(name).set(a);
            }
        });
    }

}
