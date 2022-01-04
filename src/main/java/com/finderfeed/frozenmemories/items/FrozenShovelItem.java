package com.finderfeed.frozenmemories.items;

import com.finderfeed.frozenmemories.helpers.ClientHelpers;
import com.finderfeed.frozenmemories.misc.FrozenMemoriesItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;
import net.minecraftforge.fml.util.thread.EffectiveSide;

public class FrozenShovelItem extends ShovelItem implements FrozenMemoriesItem {

    private int playerlvl;

    public FrozenShovelItem(Tier p_42961_, float p_42962_, float p_42963_, Properties p_42964_, int playerlvl) {
        super(p_42961_, p_42962_, p_42963_, p_42964_);
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
