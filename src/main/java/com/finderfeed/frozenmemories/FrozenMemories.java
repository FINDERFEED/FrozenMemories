package com.finderfeed.frozenmemories;

import com.finderfeed.frozenmemories.packet_handler.PacketHandler;
import com.finderfeed.frozenmemories.registries.*;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(FrozenMemories.MOD_ID)
public class FrozenMemories
{

    public static final CreativeModeTab FROZEN_MEMORIES = new CreativeModeTab("frozen.memories") {
        @Override
        public ItemStack makeIcon() {
            return ItemsRegistry.FROZEN_IRON_INGOT.get().getDefaultInstance();
        }
    };

    public static final String MOD_ID = "frozenmemories";

    private static final Logger LOGGER = LogManager.getLogger();

    public FrozenMemories() {
        MinecraftForge.EVENT_BUS.register(this);

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        BlocksRegistry.BLOCKS.register(bus);
        ItemsRegistry.ITEMS.register(bus);
        TileEntitiesRegistry.TILE_ENTITIES.register(bus);
        ParticlesRegistry.PARTICLES_REGISTRY.register(bus);
        EntitiesRegistry.DEFERRED_REGISTER.register(bus);
        FeaturesRegistry.FEATURES_REGISTRY.register(bus);

        BiomesRegistry.DEFERRED_REGISTER.register(bus);
        PacketHandler.registerPackets();

    }




}
