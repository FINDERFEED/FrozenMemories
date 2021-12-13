package com.finderfeed.frozenmemories.events;


import com.finderfeed.frozenmemories.FrozenMemories;
import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.PlayerProgressionStage;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;



@Mod.EventBusSubscriber(modid = FrozenMemories.MOD_ID,bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEventHandler {

    public static final ResourceKey<Level> MEMORY = ResourceKey.create(Registry.DIMENSION_REGISTRY,new ResourceLocation(FrozenMemories.MOD_ID,"memory"));
    public static final ResourceKey<Biome> MEMORY_BIOME = ResourceKey.create(Registry.BIOME_REGISTRY,new ResourceLocation(FrozenMemories.MOD_ID,"memory"));

    @SubscribeEvent
    public static void clone(PlayerEvent.Clone event){
        PlayerProgressionStage.handleCloneEvent(event);
    }


}
