package com.finderfeed.frozenmemories.registries;

import com.finderfeed.frozenmemories.FrozenMemories;
import com.finderfeed.frozenmemories.client.particles.SnowflakeParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class ParticlesRegistry {

    public static final DeferredRegister<ParticleType<?>> PARTICLES_REGISTRY = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, FrozenMemories.MOD_ID);

    public static final RegistryObject<SimpleParticleType> SNOWFLAKE_PARTICLE = PARTICLES_REGISTRY.register("snowflake_particle",()->new SimpleParticleType(true)) ;


}

@Mod.EventBusSubscriber(modid = FrozenMemories.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
class ParticleFactoryRegistry{

    @SubscribeEvent
    public static void registerFactory(ParticleFactoryRegisterEvent event){
        ParticleEngine engine = Minecraft.getInstance().particleEngine;
        engine.register(ParticlesRegistry.SNOWFLAKE_PARTICLE.get(),SnowflakeParticle.Factory::new);
    }

}
