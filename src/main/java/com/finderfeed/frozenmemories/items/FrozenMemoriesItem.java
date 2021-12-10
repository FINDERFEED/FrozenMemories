package com.finderfeed.frozenmemories.items;

import com.finderfeed.frozenmemories.client.FrozenMemoriesItemSpecialRenderer;
import com.finderfeed.frozenmemories.helpers.ClientHelpers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.fml.util.thread.EffectiveSide;

import java.util.function.Consumer;

public class FrozenMemoriesItem extends Item {

    private final int playerLevel;

    public FrozenMemoriesItem(Properties p_41383_,int playerlevel) {
        super(p_41383_);
        this.playerLevel = playerlevel;
    }


    public int getNeededPlayerLevel() {
        return playerLevel;
    }


    @Override
    public Component getName(ItemStack p_41458_) {
        if (EffectiveSide.get().isClient()){
            return ClientHelpers.getNameBasedOnNeededLevel(this);
        }
        return super.getName(p_41458_);
    }
}

