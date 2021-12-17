package com.finderfeed.frozenmemories.registries;

import com.finderfeed.frozenmemories.FrozenMemories;
import com.finderfeed.frozenmemories.worldgen_features.FrozenZombieTrapsFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FeaturesRegistry {

    public static final DeferredRegister<Feature<?>>  FEATURES_REGISTRY = DeferredRegister.create(ForgeRegistries.FEATURES, FrozenMemories.MOD_ID);

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> FROZEN_ZOMBIE_TRAP_FEATURE = FEATURES_REGISTRY.register("frozen_zombie_traps",()->
            new FrozenZombieTrapsFeature(NoneFeatureConfiguration.CODEC));

}
