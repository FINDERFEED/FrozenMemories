package com.finderfeed.frozenmemories.registries;

import com.finderfeed.frozenmemories.FrozenMemories;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;



public class ParticlesRegistry {

    public static final DeferredRegister<ParticleType<?>> PARTICLES_REGISTRY = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, FrozenMemories.MOD_ID);



}

@Mod.EventBusSubscriber(modid = FrozenMemories.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
class ParticleFactoryRegistry{

    @SubscribeEvent
    public static void registerFactory(ParticleFactoryRegisterEvent event){
        ParticleEngine engine = Minecraft.getInstance().particleEngine;

    }

}
