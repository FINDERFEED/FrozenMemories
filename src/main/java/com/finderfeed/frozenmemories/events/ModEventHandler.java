package com.finderfeed.frozenmemories.events;


import com.finderfeed.frozenmemories.FrozenMemories;
import com.finderfeed.frozenmemories.entities.FrostedZombie;
import com.finderfeed.frozenmemories.registries.BlocksRegistry;
import com.finderfeed.frozenmemories.registries.EntitiesRegistry;
import com.finderfeed.frozenmemories.registries.FeaturesRegistry;
import com.finderfeed.frozenmemories.worldgen_features.FrozenZombieTrapsFeature;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = FrozenMemories.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventHandler {



    @SubscribeEvent
    public static void entityAttributes(EntityAttributeCreationEvent event) {
        event.put(EntitiesRegistry.FROSTED_ZOMBIE.get(), FrostedZombie.createAttributes().build());
    }


    @SubscribeEvent
    public static void registerConfiguredAndPlacedFeatures(FMLCommonSetupEvent event){
        event.enqueueWork(()->{
            ConfiguredFeatures.FROZEN_ZOMBIE_TRAPS = FeaturesRegistry.FROZEN_ZOMBIE_TRAP_FEATURE.get().configured(NoneFeatureConfiguration.INSTANCE);
            registerConfiguredFeature(ConfiguredFeatures.FROZEN_ZOMBIE_TRAPS,"frozen_zombie_traps");

            PlacedFeatures.FROZEN_ZOMBIE_TRAPS = ConfiguredFeatures.FROZEN_ZOMBIE_TRAPS.placed(
                    RarityFilter.onAverageOnceEvery(50),
                    CountPlacement.of(UniformInt.of(3,6)),
                    InSquarePlacement.spread()
            );
            registerPlacedFeature(PlacedFeatures.FROZEN_ZOMBIE_TRAPS,"frozen_zombie_traps");

            ConfiguredFeatures.MEMORY_TOWER = Feature.SIMPLE_BLOCK.configured(new SimpleBlockConfiguration(BlockStateProvider.simple(BlocksRegistry.MEMORY_TOWER.get())));
            registerConfiguredFeature(ConfiguredFeatures.MEMORY_TOWER,"memory_tower");
            PlacedFeatures.MEMORY_TOWER = ConfiguredFeatures.MEMORY_TOWER.placed(
                    RarityFilter.onAverageOnceEvery(150),InSquarePlacement.spread(),HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG)
            );
            registerPlacedFeature(PlacedFeatures.MEMORY_TOWER,"memory_tower");
        });
    }



    private static void registerConfiguredFeature(ConfiguredFeature<?,?> feature,String name){
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation(FrozenMemories.MOD_ID,name),feature);
    }
    private static void registerPlacedFeature(PlacedFeature feature, String name){
        Registry.register(BuiltinRegistries.PLACED_FEATURE,new ResourceLocation(FrozenMemories.MOD_ID,name),feature);
    }


    public static class ConfiguredFeatures{
        public static ConfiguredFeature<?,?> FROZEN_ZOMBIE_TRAPS;
        public static ConfiguredFeature<?,?> MEMORY_TOWER;
    }
    public static class PlacedFeatures{
        public static PlacedFeature FROZEN_ZOMBIE_TRAPS;
        public static PlacedFeature MEMORY_TOWER;
    }
}


