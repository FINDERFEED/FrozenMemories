package com.finderfeed.frozenmemories.registries;

import com.finderfeed.frozenmemories.FrozenMemories;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BiomesRegistry {



    public static final DeferredRegister<Biome> DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.BIOMES, FrozenMemories.MOD_ID);

    public static final RegistryObject<Biome> MEMORY = DEFERRED_REGISTER.register("memory", OverworldBiomes::theVoid);

}
