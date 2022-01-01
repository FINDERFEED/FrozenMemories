package com.finderfeed.frozenmemories.events;


import com.finderfeed.frozenmemories.FrozenMemories;
import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.PlayerProgressionStage;
import com.finderfeed.frozenmemories.blocks.tileentities.renderers.MemoryTowerRenderer;
import com.finderfeed.frozenmemories.blocks.tileentities.renderers.models.MemoryTowerModel;
import com.finderfeed.frozenmemories.entities.models.MeteoriteModel;
import com.finderfeed.frozenmemories.entities.renderers.FrostedZombieRenderer;
import com.finderfeed.frozenmemories.entities.renderers.FrozenMeteoriteRenderer;
import com.finderfeed.frozenmemories.entities.renderers.IcicleProjectileRenderer;
import com.finderfeed.frozenmemories.items.FrozenItem;
import com.finderfeed.frozenmemories.misc.FrozenMemoriesItem;
import com.finderfeed.frozenmemories.registries.EntitiesRegistry;
import com.finderfeed.frozenmemories.registries.ItemsRegistry;
import com.finderfeed.frozenmemories.registries.TileEntitiesRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = FrozenMemories.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class ClientModEventHandler {



    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(MemoryTowerModel.LAYER_LOCATION, MemoryTowerModel::createBodyLayer);
        event.registerLayerDefinition(MeteoriteModel.LOCATION,MeteoriteModel::createLayer);
    }

    @SubscribeEvent
    public static void registerEntityRendering(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(EntitiesRegistry.FROSTED_ZOMBIE.get(), FrostedZombieRenderer::new);
        event.registerEntityRenderer(EntitiesRegistry.FROZEN_ZOMBIE_METEORITE.get(), FrozenMeteoriteRenderer::new);
        event.registerEntityRenderer(EntitiesRegistry.ICICLE.get(), IcicleProjectileRenderer::new);
    }





    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event){
        event.enqueueWork(()->{
            registerDefaultUnknownItemProperty(ItemsRegistry.FROZEN_IRON_INGOT.get());
            registerDefaultUnknownItemProperty(ItemsRegistry.FROZEN_NETHERITE.get());
            registerDefaultUnknownItemProperty(ItemsRegistry.FROZEN_DIAMOND.get());
            registerDefaultUnknownItemProperty(ItemsRegistry.FROZEN_DIAMOND_SWORD.get());
            registerDefaultUnknownItemProperty(ItemsRegistry.FROZEN_IRON_SWORD.get());
            registerDefaultUnknownItemProperty(ItemsRegistry.FROZEN_NETHERITE_SWORD.get());
            registerDefaultUnknownItemProperty(ItemsRegistry.FROZEN_DIAMOND_HELMET.get());
            registerDefaultUnknownItemProperty(ItemsRegistry.FROZEN_DIAMOND_CHESTPLATE.get());
            registerDefaultUnknownItemProperty(ItemsRegistry.FROZEN_DIAMOND_BOOTS.get());
            registerDefaultUnknownItemProperty(ItemsRegistry.FROZEN_DIAMOND_LEGGINGS.get());
            registerDefaultUnknownItemProperty(ItemsRegistry.FROZEN_NETHERITE_HELMET.get());
            registerDefaultUnknownItemProperty(ItemsRegistry.FROZEN_NETHERITE_CHESTPLATE.get());
            registerDefaultUnknownItemProperty(ItemsRegistry.FROZEN_NETHERITE_BOOTS.get());
            registerDefaultUnknownItemProperty(ItemsRegistry.FROZEN_NETHERITE_LEGGINGS.get());
            registerDefaultUnknownItemProperty(ItemsRegistry.FROZEN_IRON_HELMET.get());
            registerDefaultUnknownItemProperty(ItemsRegistry.FROZEN_IRON_CHESTPLATE.get());
            registerDefaultUnknownItemProperty(ItemsRegistry.FROZEN_IRON_BOOTS.get());
            registerDefaultUnknownItemProperty(ItemsRegistry.FROZEN_IRON_LEGGINGS.get());
            registerDefaultUnknownItemProperty(ItemsRegistry.HOT_CHOCOLATE.get());
            registerDefaultUnknownItemProperty(ItemsRegistry.SKATES.get());
            registerDefaultUnknownItemProperty(ItemsRegistry.MAGIC_ICICLE.get());
            registerDefaultUnknownItemProperty(ItemsRegistry.ICICLE_STAFF.get());

            BlockEntityRenderers.register(TileEntitiesRegistry.MEMORY_TOWER.get(), MemoryTowerRenderer::new);
        });
    }


    public static void registerDefaultUnknownItemProperty(FrozenMemoriesItem item){
        ItemProperties.register(item.getFrozenItem(),new ResourceLocation(FrozenMemories.MOD_ID,"unlocked"),
                (stack,level,player,d)->{
                    int reqLvl = item.getNeededPlayerLevel();
                    if (player instanceof Player pl) {
                        if (pl.level.dimension() == ForgeEventHandler.MEMORY){
                            return 0;
                        }
                        int playerLvl = PlayerProgressionStage.getPlayerProgressionStage(pl);
                        return playerLvl >= reqLvl ? 0 : 1;
                    }else{
                        return 0;
                    }
                });
    }
}
