package com.finderfeed.frozenmemories.events;


import com.finderfeed.frozenmemories.FrozenMemories;
import com.finderfeed.frozenmemories.entities.FrostedZombie;
import com.finderfeed.frozenmemories.registries.EntitiesRegistry;
import net.minecraft.world.entity.monster.Monster;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FrozenMemories.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventHandler {



    @SubscribeEvent
    public static void entityAttributes(EntityAttributeCreationEvent event) {
        event.put(EntitiesRegistry.FROSTED_ZOMBIE.get(), FrostedZombie.createAttributes().build());
    }

}
