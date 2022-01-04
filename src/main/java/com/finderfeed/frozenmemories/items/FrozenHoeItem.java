package com.finderfeed.frozenmemories.items;

import com.finderfeed.frozenmemories.helpers.ClientHelpers;
import com.finderfeed.frozenmemories.misc.FrozenMemoriesItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraftforge.fml.util.thread.EffectiveSide;

public class FrozenHoeItem extends HoeItem implements FrozenMemoriesItem {


    private int playerlvl;

    public FrozenHoeItem(Tier p_41336_, int p_41337_, float p_41338_, Properties p_41339_,int playerlvl) {
        super(p_41336_, p_41337_, p_41338_, p_41339_);
        this.playerlvl = playerlvl;
    }

    @Override
    public Component getName(ItemStack p_41458_) {
        if (EffectiveSide.get().isClient()){

            return ClientHelpers.getNameBasedOnNeededLevel(this);
        }
        return super.getName(p_41458_);
    }
    @Override
    public int getNeededPlayerLevel() {
        return playerlvl;
    }

    @Override
    public Item getFrozenItem() {
        return this;
    }
}
