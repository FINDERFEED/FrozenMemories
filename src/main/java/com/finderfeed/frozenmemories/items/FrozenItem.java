package com.finderfeed.frozenmemories.items;

import com.finderfeed.frozenmemories.client.FrozenMemoriesItemSpecialRenderer;
import com.finderfeed.frozenmemories.helpers.ClientHelpers;
import com.finderfeed.frozenmemories.misc.FrozenMemoriesItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.fml.util.thread.EffectiveSide;

import java.util.function.Consumer;

public class FrozenItem extends Item implements FrozenMemoriesItem {

    private final int playerLevel;

    public FrozenItem(Properties p_41383_, int playerlevel) {
        super(p_41383_);
        this.playerLevel = playerlevel;
    }


    @Override
    public int getNeededPlayerLevel() {
        return playerLevel;
    }

    @Override
    public Item getFrozenItem() {
        return this;
    }


    @Override
    public Component getName(ItemStack p_41458_) {
        if (EffectiveSide.get().isClient()){
            return ClientHelpers.getNameBasedOnNeededLevel(this);
        }
        return super.getName(p_41458_);
    }
}

