package com.finderfeed.frozenmemories.items;

import com.finderfeed.frozenmemories.helpers.ClientHelpers;
import com.finderfeed.frozenmemories.misc.FrozenMemoriesItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraftforge.fml.util.thread.EffectiveSide;

public class FrozenAxeItem extends AxeItem implements FrozenMemoriesItem {

    private int playerlvl;

    public FrozenAxeItem(Tier p_40521_, float p_40522_, float p_40523_, Properties p_40524_,int playerlvl) {
        super(p_40521_, p_40522_, p_40523_, p_40524_);
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
