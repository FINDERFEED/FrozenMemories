package com.finderfeed.frozenmemories.items;

import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.PlayerProgressionStage;
import com.finderfeed.frozenmemories.helpers.ClientHelpers;
import com.finderfeed.frozenmemories.misc.FrozenMemoriesItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.util.thread.EffectiveSide;

public class FrozenArmorItem extends ArmorItem implements FrozenMemoriesItem {

    private final int playerStage;

    public FrozenArmorItem(ArmorMaterial p_40386_, EquipmentSlot p_40387_, Properties p_40388_,int playerStage) {
        super(p_40386_, p_40387_, p_40388_);
        this.playerStage = playerStage;
    }


    @Override
    public boolean canEquip(ItemStack stack, EquipmentSlot armorType, Entity entity) {

        if (entity instanceof Player player && !player.level.isClientSide){
            if (PlayerProgressionStage.getPlayerProgressionStage(player) < getNeededPlayerLevel()){
                return false;
            }
        }

        return super.canEquip(stack, armorType, entity);
    }

    @Override
    public int getNeededPlayerLevel() {
        return playerStage;
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