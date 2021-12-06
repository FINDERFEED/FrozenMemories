package com.finderfeed.frozenmemories;

import com.finderfeed.frozenmemories.registries.BlocksRegistry;
import com.finderfeed.frozenmemories.registries.ItemsRegistry;
import com.finderfeed.frozenmemories.registries.ParticlesRegistry;
import com.finderfeed.frozenmemories.registries.TileEntitiesRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("frozenmemories")
public class FrozenMemories
{

    public static final CreativeModeTab FROZEN_MEMORIES = new CreativeModeTab("frozen.memories") {
        @Override
        public ItemStack makeIcon() {
            return Items.BOW.getDefaultInstance();
        }
    };

    public static final String MOD_ID = "frozenmemories";

    private static final Logger LOGGER = LogManager.getLogger();

    public FrozenMemories() {

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ItemsRegistry.ITEMS.register(bus);
        BlocksRegistry.BLOCKS.register(bus);
        TileEntitiesRegistry.TILE_ENTITIES.register(bus);
        ParticlesRegistry.PARTICLES_REGISTRY.register(bus);


        MinecraftForge.EVENT_BUS.register(this);
    }




}
