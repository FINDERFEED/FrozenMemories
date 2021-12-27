package com.finderfeed.frozenmemories.helpers;

import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.PlayerProgressionStage;
import com.finderfeed.frozenmemories.items.FrozenItem;
import com.finderfeed.frozenmemories.items.FrozenMemoriesSword;
import com.finderfeed.frozenmemories.misc.FrozenMemoriesItem;
import com.finderfeed.frozenmemories.registries.ItemsRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ClientHelpers {


    public static Player clientPlayer(){
        return Minecraft.getInstance().player;
    }






    public static Component getNameBasedOnNeededLevel(FrozenMemoriesItem item){
        if (clientPlayer() != null) {
            int playerLvl = PlayerProgressionStage.getPlayerProgressionStage(clientPlayer());
            if (playerLvl >= item.getNeededPlayerLevel()) {
                return new TranslatableComponent(item.getFrozenItem().getDescriptionId());
            } else {
                return ItemsRegistry.PLACEHOLDER_UNKNOWN_ITEM.get().getDefaultInstance().getHoverName();
            }
        }else{
            return ItemsRegistry.PLACEHOLDER_UNKNOWN_ITEM.get().getDefaultInstance().getHoverName();
        }
    }


    public static void renderWithoutCustomRenderer(ItemStack item, ItemTransforms.TransformType transform, boolean idk, PoseStack matrices, MultiBufferSource buffer, int light, int overlay, BakedModel model) {

    }
}
