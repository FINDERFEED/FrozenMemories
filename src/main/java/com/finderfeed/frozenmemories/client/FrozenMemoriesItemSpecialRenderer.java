package com.finderfeed.frozenmemories.client;

import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.PlayerProgressionStage;
import com.finderfeed.frozenmemories.helpers.ClientHelpers;
import com.finderfeed.frozenmemories.items.FrozenItem;
import com.finderfeed.frozenmemories.registries.ItemsRegistry;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

@Deprecated
public class FrozenMemoriesItemSpecialRenderer extends BlockEntityWithoutLevelRenderer {
    public FrozenMemoriesItemSpecialRenderer(BlockEntityRenderDispatcher p_172550_, EntityModelSet p_172551_) {
        super(p_172550_, p_172551_);
    }


    @Override
    public void renderByItem(ItemStack item, ItemTransforms.TransformType transform, PoseStack matrices, MultiBufferSource buffer, int light, int overlay) {
        if (item.getItem() instanceof FrozenItem fitem){
            int level = fitem.getNeededPlayerLevel();
            Player pl = Minecraft.getInstance().player;
            if (pl != null) {
                int currentLvl = PlayerProgressionStage.getPlayerProgressionStage(pl);
                ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
                matrices.pushPose();
                matrices.translate(0.5,0.5,0.5);
                if (currentLvl >= level){
                    if (transform == ItemTransforms.TransformType.GUI){
                        Lighting.setupForFlatItems();
                    }
                    ClientHelpers.renderWithoutCustomRenderer(item,transform,false,matrices,buffer,light,overlay,
                            renderer.getModel(item,null,null,0));
                }else{
                    renderer.render(ItemsRegistry.PLACEHOLDER_UNKNOWN_ITEM.get().getDefaultInstance(),
                            transform,false,matrices,buffer,light,overlay,renderer.getModel(
                                    ItemsRegistry.PLACEHOLDER_UNKNOWN_ITEM.get().getDefaultInstance(),null,null,0
                            ));
                }
                matrices.popPose();
            }
        }
    }
}
