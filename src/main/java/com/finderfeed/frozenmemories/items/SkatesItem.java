package com.finderfeed.frozenmemories.items;

import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.PlayerProgressionStage;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class SkatesItem extends FrozenArmorItem{
    public SkatesItem(ArmorMaterial p_40386_, EquipmentSlot p_40387_, Properties p_40388_, int playerStage) {
        super(p_40386_, p_40387_, p_40388_, playerStage);
    }


    @Override
    public void onArmorTick(ItemStack stack, Level world, Player player) {
        if (!world.isClientSide){
            if (world.getGameTime() % 10 == 0){
                BlockState state = world.getBlockState(player.getOnPos());
                if ((PlayerProgressionStage.getPlayerProgressionStage(player) >= getNeededPlayerLevel()) && state.is(Blocks.ICE) || state.is(Blocks.BLUE_ICE) || state.is(Blocks.PACKED_ICE) || state.is(Blocks.FROSTED_ICE)){
                    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,20,1));
                }
            }
        }
        super.onArmorTick(stack, world, player);
    }
}
