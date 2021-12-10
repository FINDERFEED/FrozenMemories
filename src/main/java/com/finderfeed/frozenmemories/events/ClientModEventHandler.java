package com.finderfeed.frozenmemories.events;


import com.finderfeed.frozenmemories.FrozenMemories;
import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.PlayerProgressionStage;
import com.finderfeed.frozenmemories.items.FrozenMemoriesItem;
import com.finderfeed.frozenmemories.registries.ItemsRegistry;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = FrozenMemories.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class ClientModEventHandler {




    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event){
        event.enqueueWork(()->{
            registerDefaultUnknownItemProperty(ItemsRegistry.TEST_ITEM.get());
        });
    }


    public static void registerDefaultUnknownItemProperty(FrozenMemoriesItem item){
        ItemProperties.register(item,new ResourceLocation(FrozenMemories.MOD_ID,"unlocked"),
                (stack,level,player,d)->{
                    int reqLvl = item.getNeededPlayerLevel();
                    if (player instanceof Player pl) {
                        int playerLvl = PlayerProgressionStage.getPlayerProgressionStage(pl);
                        return playerLvl >= reqLvl ? 0 : 1;
                    }else{
                        return 0;
                    }
                });
    }
}
