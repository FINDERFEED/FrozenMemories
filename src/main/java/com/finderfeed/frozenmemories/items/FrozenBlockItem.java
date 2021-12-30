package com.finderfeed.frozenmemories.items;

import com.finderfeed.frozenmemories.helpers.ClientHelpers;
import com.finderfeed.frozenmemories.misc.FrozenMemoriesItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.util.thread.EffectiveSide;

public class FrozenBlockItem extends BlockItem implements FrozenMemoriesItem {

    private final int playerStage;

    public FrozenBlockItem(Block p_40565_, Properties p_40566_,int playerStage) {
        super(p_40565_, p_40566_);
        this.playerStage = playerStage;
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
        return playerStage;
    }

    @Override
    public Item getFrozenItem() {
        return this;
    }
}
