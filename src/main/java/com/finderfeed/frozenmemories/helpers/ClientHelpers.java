package com.finderfeed.frozenmemories.helpers;

import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.PlayerProgressionStage;
import com.finderfeed.frozenmemories.items.FrozenMemoriesItem;
import com.finderfeed.frozenmemories.registries.ItemsRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.StainedGlassPaneBlock;

public class ClientHelpers {


    public static Player clientPlayer(){
        return Minecraft.getInstance().player;
    }

    public static Component getNameBasedOnNeededLevel(FrozenMemoriesItem item){
        if (clientPlayer() != null) {
            int playerLvl = PlayerProgressionStage.getPlayerProgressionStage(clientPlayer());
            if (playerLvl >= item.getNeededPlayerLevel()) {
                return new TranslatableComponent(item.getDescriptionId());
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
